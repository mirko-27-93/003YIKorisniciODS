package programiranje.yi.database;

import java.io.Serializable;

/**
 * Објекти података о кориснику по базама података, а 
 * када је у питању за могућност корисничког манервисања подацима.  
 * @author mirko
 * @version 1.0
 */
public class MySQLUserDTO implements Serializable{
	private static final long serialVersionUID = -7971372482467177524L;
	private String username = ""; 
	private boolean databaseUser = false;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		if(username==null) username = ""; 
		this.username = username;
	}
	public boolean isDatabaseUser() {
		return databaseUser;
	}
	public void setDatabaseUser(boolean databaseUser) {
		this.databaseUser = databaseUser;
	} 
}
