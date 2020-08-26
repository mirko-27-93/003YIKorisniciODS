package studiranje.ip.database;

import java.sql.*;
import java.util.*;

import studiranje.ip.data.AbstractConnectionPool;

/**
 * Конекциона пула која је сконцетрисана на једну конкретну фиксирану,
 * класом за инстанце увијек исту базу података. 
 * @author mirko
 * @version 1.0
 */
public class ConnectionPool extends AbstractConnectionPool{

  public static ConnectionPool getConnectionPool() {
    return connectionPool;
  }

  private static ConnectionPool connectionPool;

  static {
    String jdbcURL = "jdbc:mysql://localhost:3306/yi?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&characterSetResults=utf8&connectionCollation=utf8_general_ci";
    String username = "root";
    String password = "root";
    String driver = "com.mysql.cj.jdbc.Driver";
    int preconnectCount = 0;
    int maxIdleConnections = 10;
    int maxConnections = 10;
    try {
        Class.forName(driver);
    } catch (Exception ex) {
    	ex.printStackTrace();
    }
    try {
      connectionPool = new ConnectionPool(
        jdbcURL, username, password,
        preconnectCount, maxIdleConnections,
        maxConnections);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  protected ConnectionPool(String aJdbcURL, String aUsername,
    String aPassword, int aPreconnectCount,
    int aMaxIdleConnections,
    int aMaxConnections)
    throws ClassNotFoundException, SQLException {

    freeConnections = new Vector<Connection>();
    usedConnections = new Vector<Connection>();
    jdbcURL = aJdbcURL;
    username = aUsername;
    password = aPassword;
    preconnectCount = aPreconnectCount;
    maxIdleConnections = aMaxIdleConnections;
    maxConnections = aMaxConnections;

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

  private String jdbcURL;
  private String username;
  private String password;
  private int preconnectCount;
  private int connectCount;
  private int maxIdleConnections;
  private int maxConnections;
  private Vector<Connection> usedConnections;
  private Vector<Connection> freeConnections;

}
