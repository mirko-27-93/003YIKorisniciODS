package studiranje.ip.database.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Контрола измјењивост безе података и поставки када је исто у питању. 
 * @author mirko
 * @version 1.0
 */
public class DatabaseSwitchController {
	private DatabaseSwitchConfigurationController controller = new DatabaseSwitchConfigurationController();
	private DatabaseMapController mapController = new DatabaseMapController();

	public DatabaseSwitchConfigurationController getController() {
		return controller;
	}

	public DatabaseMapController getMapController() {
		return mapController;
	}
	
	@SuppressWarnings("static-access")
	public String defaultDBName() {
		return mapController.DEFAULT_DATABASE_NAME; 
	}
	
	@SuppressWarnings("static-access")
	public String defaultDBAddress() {
		return mapController.DEFAULT_DATABASE_ADDRESS; 
	}
	
	public List<String> getDatabaseNames(){
		ArrayList<String> names = new ArrayList<>(mapController.getMap().keySet());
		Collections.sort(names);
		return names; 
	}
	
	public String getDatabaseAddress(String name) {
		return mapController.getMap().get(name); 
	}
	
	public Map<String, String> getDatabaseAdrressSplited(String name) {
		HashMap<String, String> map = new HashMap<>(); 
		URI uri = URI.create(mapController.getMap().get(name));  
		map.put("user", uri.getUserInfo().split(":")[0]); 
		map.put("password", uri.getUserInfo().split(":")[1]); 
		map.put("host", uri.getHost()); 
		map.put("port", Integer.toString(uri.getPort()));
		map.put("database", uri.getPath().substring(1).split("/")[0]);
		return map; 
	}
}
