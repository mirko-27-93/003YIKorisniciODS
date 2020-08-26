package studiranje.ip.database.controller;

import java.util.Properties;

/**
 * Управљање конфигурацијама, када је у питању избор базе података. 
 * @author mirko
 * @version 1.0
 */
public class DatabaseSwitchConfigurationController {
	private Properties properties = new Properties();

	public Properties getProperties() {
		return properties;
	} 
	
	public void load() {
		try {
			properties.load(getClass().getResourceAsStream("/studiranje/ip/database/configuration/user.database.state.properties"));
		}catch(Exception ex) {
			throw new RuntimeException(ex);  
		}
	}
	
	{load();}
	
	public DatabaseSwitchState getState() {
		try {
			return DatabaseSwitchState.valueOf(properties.getProperty("user.database.state")); 
		}catch(Exception ex) {
			return null; 
		}
	}
}
