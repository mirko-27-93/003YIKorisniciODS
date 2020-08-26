package studiranje.ip.database.bean;

import java.io.Serializable;
import java.util.Map;

/**
 * Информацион зрно базе подататака. 
 * @author mirko
 * @version 1.0
 */
public class RootDatabaseInfoBean implements Serializable{
	private static final long serialVersionUID = -3738893496360109383L;
	
	private String host = ""; 
	private String port = ""; 
	private String user = ""; 
	private String password = ""; 
	private String database = "";
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		if(host==null) host = ""; 
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		if(port==null) port = ""; 
		this.port = port;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		if(user==null) user = ""; 
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		if(password==null) password = "";
		this.password = password;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		if(database==null) database = ""; 
		this.database = database;
	} 
	
	public String apply(RootDatabaseInfoBean bean) {
		if(bean == null) setUser("");
		if(bean == null) setPassword("");
		if(bean == null) setHost("");
		if(bean == null) setDatabase("");
		if(bean == null) setPort("");
		if(bean == null) return ""; 
		setUser(bean.getUser());
		setPassword(bean.getPassword());
		setHost(bean.getHost());
		setDatabase(bean.getDatabase()); 
		setPort(bean.getPort());
		return ""; 
	}
	
	public String apply(RootDatabaseInfoStateBean bean) {
		if(bean == null) setUser("");
		if(bean == null) setPassword("");
		if(bean == null) setHost("");
		if(bean == null) setDatabase("");
		if(bean == null) setPort("");
		if(bean == null) return "";
		Map<String, String> dbInfo = bean.getDatabaseAdrressSplited(bean.getChoosedDatabase()); 
		setUser(dbInfo.get("user"));
		setPassword(dbInfo.get("password"));
		setHost(dbInfo.get("host"));
		setDatabase(dbInfo.get("database"));
		setPort(dbInfo.get("port"));
		return "";
	}
}
