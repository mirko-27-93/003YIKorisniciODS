package studiranje.ip.database.model;

import java.io.Serializable;

/**
 * Репрезентација корисника чији су подаци и записи у бази података. 
 * @author mirko 
 * @version 1.0
 */
public final class DBUserData implements Serializable, Comparable<DBUserData>, Cloneable{
	private static final long serialVersionUID = 360505297206017743L;
	
	private String hostName = ""; 
	private String userName = ""; 
	private String authenticationString = "";
	
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		if(hostName==null) hostName = ""; 
		this.hostName = hostName;
	}
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		if(userName==null) userName = "";
		this.userName = userName;
	}
	
	
	public String getAuthenticationString() {
		return authenticationString;
	}
	public void setAuthenticationString(String authenticationString) {
		if(authenticationString==null) authenticationString = ""; 
		this.authenticationString = authenticationString;
	}

	
	public final String originalString() {
		return super.toString(); 
	}
	public final int originalCode() {
		return super.hashCode(); 
	}
	
	public String definer() {
		return userName+"@"+hostName; 
	}
	
	@Override
	public String toString() {
		return definer();
	}
	@Override
	public int hashCode() {
		return definer().hashCode(); 
	}
	
	public boolean idEquals(DBUserData user) {
		return definer().equals(user.definer());
	}
	
	public boolean fullEquals(DBUserData user) {
		if(!hostName.contains(hostName)) return false; 
		if(!userName.contains(userName)) return false; 
		if(!authenticationString.contains(authenticationString)) return false; 
		return true; 
	}
	
	@Override
	public boolean equals(Object object) {
		if(object instanceof DBUserData) {
			idEquals((DBUserData) object);
		}
		return false; 
	}
	
	@Override
	public int compareTo(DBUserData o) {
		return definer().compareTo(o.definer());
	}
	
	@Override
	public DBUserData clone() {
		DBUserData data = new DBUserData();
		data.authenticationString = authenticationString; 
		data.hostName = hostName;
		data.userName = userName; 
		return data;  
	}
}
