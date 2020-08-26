package studiranje.ip.database.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * Мапа база података које су доступме и неке пречице за уграђене базе подататака, 
 * које су обавезне и подразумијеване. 
 * @author mirko
 * @version 1.0
 */
public class DatabaseMapController {
	public static final String DEFAULT_DATABASE_NAME = "databaseuser.yi"; 
	public static final String DEFAULT_DATABASE_ADDRESS = "http://root:root@localhost:3306/yi";
	
	private HashMap<String, String> databasesMap = new HashMap<>(); 
	
	public DatabaseMapController() {
		try {
			reload(); 
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}
	}
	
	public synchronized void reload() throws IOException {
		databasesMap.clear(); 
		Properties properties = new Properties(); 
		properties.load(getClass().getResourceAsStream("/studiranje/ip/database/configuration/user.database.map.properties"));
		for(Entry<Object, Object> entry:  properties.entrySet()) {
			String key 	 = (String) entry.getKey();
			String value = (String) entry.getValue();
			databasesMap.put(key, value); 
		}
		databasesMap.put(DEFAULT_DATABASE_NAME, DEFAULT_DATABASE_ADDRESS); 
	}
	
	public synchronized Map<String, String> getMap(){
		return new HashMap<>(databasesMap); 
	}
}
