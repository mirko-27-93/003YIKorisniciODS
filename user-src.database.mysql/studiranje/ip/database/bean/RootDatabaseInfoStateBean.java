package studiranje.ip.database.bean;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import studiranje.ip.database.controller.DatabaseSwitchController;
import studiranje.ip.database.controller.DatabaseSwitchState;

/**
 * Стања и базе подазака када је у питању измјењивост и избор базе података.
 * Као и листа понуђених база података. 
 * @author mirko
 * @version 1.0
 */
public class RootDatabaseInfoStateBean implements Serializable{
	private static final long serialVersionUID = -1296279114624796358L;
	
	private DatabaseSwitchState state = DatabaseSwitchState.DEFAULT_STATE;
	private transient DatabaseSwitchController controller = new DatabaseSwitchController(); 
	
	private String choosedDatabase = ""; 
	private HashMap<String, String> databaseMap = new HashMap<>();
	
	
	public DatabaseSwitchState getState() {
		return state;
	}

	public void setState(DatabaseSwitchState state) {
		if(state==null) state = DatabaseSwitchState.DEFAULT_STATE; 
		this.state = state;
	}

	public String getChoosedDatabase() {
		return choosedDatabase;
	}

	public void setChoosedDatabase(String choosedDatabase) {
		if(choosedDatabase==null) choosedDatabase = this.choosedDatabase;  
		this.choosedDatabase = choosedDatabase;
	}

	public synchronized Map<String, String> getDatabaseMap() {
		return new HashMap<>(databaseMap);
	}

	public synchronized void setDatabaseMap(Map<String, String> databaseMap) {
		if(databaseMap==null) databaseMap = new HashMap<>();
		this.databaseMap = new HashMap<>(databaseMap);
	} 
	
	public List<String> getDatabaseNames(){
		ArrayList<String> names = new ArrayList<>(databaseMap.keySet());
		Collections.sort(names);
		return names; 
	}
	
	public String getDatabaseAddress(String name) {
		return databaseMap.get(name); 
	}
	
	public String getCurrentAddress() {
		return databaseMap.get(choosedDatabase);
	}
	
	public synchronized String nameByValue(String value) {
		for(Map.Entry<String, String> me: databaseMap.entrySet()) {
			String key = me.getKey(); 
			String val = me.getValue(); 
			if(val.contentEquals(value)) return key;
		}
		return ""; 
	}
	
	public synchronized Map<String, String> getDatabaseAdrressSplitedByValue(String value) {
		return getDatabaseAdrressSplited(nameByValue(value));
	}
	
	public synchronized Map<String, String> getDatabaseAdrressSplited(String name) {
		HashMap<String, String> map = new HashMap<>(); 
		URI uri = URI.create(databaseMap.get(name));  
		map.put("user", uri.getUserInfo().split(":")[0]); 
		map.put("password", uri.getUserInfo().split(":")[1]); 
		map.put("host", uri.getHost()); 
		map.put("port", Integer.toString(uri.getPort()));
		map.put("database", uri.getPath().substring(1).split("/")[0]);
		map.put("name", name);
		map.put("address", databaseMap.get(name));
		return map; 
	}
	
	public synchronized String apply(RootDatabaseInfoStateBean database) {
		if(database==null) state = DatabaseSwitchState.DEFAULT_STATE; 
		if(database==null) databaseMap = new HashMap<>();
		if(database==null) choosedDatabase = "";
		if(database==null) return ""; 
		state = database.state; 
		setDatabaseMap(database.getDatabaseMap());
		choosedDatabase = database.choosedDatabase;
		return ""; 
	}
	
	public synchronized String applyBlank() {
		if(controller==null) controller = new DatabaseSwitchController();
		setDatabaseMap(controller.getMapController().getMap());
		state = DatabaseSwitchState.DEFAULT_STATE;
		choosedDatabase = controller.defaultDBName();
		return "";
	}
	
	public synchronized String applyGeneral() {
		if(controller==null) controller = new DatabaseSwitchController();
		setDatabaseMap(controller.getMapController().getMap());
		state = controller.getController().getState(); 
		choosedDatabase = controller.defaultDBName();
		return "";
	}
	
	public synchronized String apply() {
		if(controller==null) controller = new DatabaseSwitchController();
		setDatabaseMap(controller.getMapController().getMap());
		state = controller.getController().getState(); 
		if(choosedDatabase.trim().length()==0)
			choosedDatabase = controller.defaultDBName();
		return "";
	}

	public DatabaseSwitchController getController() {
		if(controller==null) controller = new DatabaseSwitchController();
		return controller;
	}

	public void setController(DatabaseSwitchController controller) {
		this.controller = controller;
	}
	
	public String getJDBCMysqlAddress(String name) {
		Map<String, String> map = getDatabaseAdrressSplited(name); 
		String uri = "jdbc:mysql://"+map.get("host")+":"+map.get("port")+"/"+map.get("database")+
				"?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&characterSetResults=utf8&connectionCollation=utf8_general_ci";
		return uri; 
	}
}
