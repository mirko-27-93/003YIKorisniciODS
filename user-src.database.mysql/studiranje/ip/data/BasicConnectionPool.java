package studiranje.ip.data;

import java.net.URI;
import java.sql.*;
import java.util.*;

/**
 * Конекционе пуле чије се посебне инстанце вежу за посебне базе података, 
 * које су присутне на систему за управљање истим. 
 * @author mirko
 * @version 1.0
 */
public class BasicConnectionPool extends AbstractConnectionPool implements DBFullURLSwappable{
  private static HashMap<String, BasicConnectionPool> dbPoolsMap = new HashMap<>();  
  
  public synchronized static BasicConnectionPool getConnectionPool(String databaseFullURL, String databaseName) {
    URI uri = URI.create(databaseFullURL+"/"+databaseName); 
	BasicConnectionPool pool = dbPoolsMap.get(uri.toString());
    if(pool!=null) return pool; 
    pool = load(databaseFullURL, databaseName);
    dbPoolsMap.put(uri.toString(), pool); 
    return pool;
  }

  static {
	  String driver = "com.mysql.jdbc.Driver";
	  try {
        Class.forName(driver);
	  } catch (Exception ex) {
	    ex.printStackTrace();
	  }
  }
  
  private static BasicConnectionPool load(String databaseFullURL, String dbName) {
    URI uri = URI.create(databaseFullURL+"/"+dbName); 
    String host = uri.getHost(); 
    int port = uri.getPort(); 
    String userInfo = uri.getUserInfo(); 
    
    if(host==null) throw new NullPointerException(); 
    if(userInfo==null) throw new NullPointerException(); 
    if(userInfo.split(":").length!=2) throw new IndexOutOfBoundsException("DB_USER_INFO");
    
    String user = userInfo.split(":")[0]; 
    String passwd = userInfo.split(":")[1];
    String origin = host+":"+port; 
    
	String jdbcURL = "jdbc:mysql://"+origin+"/"+dbName+"?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&characterSetResults=utf8&connectionCollation=utf8_general_ci";
    String username = user;
    String password = passwd;
    
    int preconnectCount = 0;
    int maxIdleConnections = 10;
    int maxConnections = 10;
    try {
      return new BasicConnectionPool(
        jdbcURL, username, password,
        preconnectCount, maxIdleConnections,
        maxConnections, uri);
    } catch (Exception ex) {
       throw new RuntimeException(ex); 
    }
  }

  protected BasicConnectionPool(String aJdbcURL, String aUsername,
    String aPassword, int aPreconnectCount,
    int aMaxIdleConnections,
    int aMaxConnections, URI idURI)
    throws ClassNotFoundException, SQLException {

    freeConnections = new Vector<Connection>();
    usedConnections = new Vector<Connection>();
    jdbcURL = aJdbcURL;
    username = aUsername;
    password = aPassword;
    preconnectCount = aPreconnectCount;
    maxIdleConnections = aMaxIdleConnections;
    maxConnections = aMaxConnections;
    this.idURI = idURI;

    for (int i = 0; i < preconnectCount; i++) {
      Connection conn = DriverManager.getConnection(
        jdbcURL, username, password);
      conn.setAutoCommit(true);
      freeConnections.addElement(conn);
    }
    connectCount = preconnectCount;
  }
  
  @Override
  public synchronized Connection checkOut()
    throws SQLException {

    Connection conn = null;
    if (freeConnections.size() > 0) {
      conn = (Connection)freeConnections.elementAt(0);
      freeConnections.removeElementAt(0);
      usedConnections.addElement(conn);
    } else {
      if (connectCount < maxConnections) {
        conn = DriverManager.getConnection(
          jdbcURL, username, password);
        usedConnections.addElement(conn);
        connectCount++;
      } else {
        try {
          wait();
          conn = (Connection)freeConnections.elementAt(0);
          freeConnections.removeElementAt(0);
          usedConnections.addElement(conn);
        } catch (InterruptedException ex) {
          ex.printStackTrace();
        }
      }
    }
    return conn;
  }

  @Override
  public synchronized void checkIn(Connection aConn) {
    if (aConn ==  null)
      return;
    if (usedConnections.removeElement(aConn)) {
      freeConnections.addElement(aConn);
      while (freeConnections.size() > maxIdleConnections) {
        int lastOne = freeConnections.size() - 1;
        Connection conn = (Connection)
          freeConnections.elementAt(lastOne);
        try { conn.close(); } catch (SQLException ex) { }
        freeConnections.removeElementAt(lastOne);
      }
      notify();
    }
  }
  
  private URI idURI; 
  private String jdbcURL;
  private String username;
  private String password;
  private int preconnectCount;
  private int connectCount;
  private int maxIdleConnections;
  private int maxConnections;
  private Vector<Connection> usedConnections;
  private Vector<Connection> freeConnections;

	@Override
	public String getDbName() {
		return idURI.getPath().substring(1);
	}
	
	@Override
	public void setDbName(String dbName) {
		throw new UnsupportedOperationException(); 
	}
	
	@Override
	public String geDBtHostURL() {
		return idURI.getHost()+":"+idURI.getPort();
	}
	
	@Override
	public void setDBHostURL(String dbHostURL) {
		throw new UnsupportedOperationException(); 
	}
	
	@Override
	public String getDatabaseUser() {
		return idURI.getUserInfo().split(":")[0];
	}
	
	@Override
	public void setDatabaseUser(String dbRootUser) {
		throw new UnsupportedOperationException(); 
	}
	
	@Override
	public String getDatabasePassword() {
		return idURI.getUserInfo().split(":")[1];
	}
	
	@Override
	public void setDatabasePassword(String dbRootPasswd) {
		throw new UnsupportedOperationException(); 
	}
	
	@Override
	public String getFullDatabaseInclassURI() {
		return idURI.toString();
	}
	
	@Override
	public  URI getFullDatabaseAccessURI() {
		URI uri = URI.create(jdbcURL);
		return uri; 
	}
}
