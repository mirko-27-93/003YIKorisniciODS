package studiranje.ip.database.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import studiranje.ip.database.model.DBTableInfo;
import studiranje.ip.database.model.DBUserData;

/**
 * Серијализација низа табела, предвиђемп из базе података. 
 * @author mirko
 * @version 1.0
 */
public class DatabaseInfoSerializaer {
	private String databaseName = "";
	private List<DBTableInfo> tables = new ArrayList<>();
	private List<DBUserData>  users  = new ArrayList<>(); 
	
	public String getDatabaseName() {
		return databaseName;
	}
	public void setDatabaseName(String databaseName) {
		if(databaseName.length()==0) databaseName = ""; 
		this.databaseName = databaseName;
	}
	
	public synchronized List<DBTableInfo> getTables() {
		return new ArrayList<>(tables);
	}
	public synchronized void add(DBTableInfo table) {
		tables.add(table);
	}
	public synchronized void remove(DBTableInfo table) {
		tables.remove(table);
	}
	
	public synchronized List<DBUserData> getUsers() {
		return new ArrayList<>(users);
	}
	public synchronized void add(DBUserData user) {
		users.add(user);
	}
	public synchronized void remove(DBUserData user) {
		users.remove(user);
	}
	
	public String toJSON() {
		DBTableInfoSerializer tableSerializer = new DBTableInfoSerializer(); 
		JsonObject root = new JsonObject();
		root.addProperty("database", databaseName);
		JsonArray tables = new JsonArray();
		List<DBTableInfo> tablesMap = getTables(); 
		for(DBTableInfo info: tablesMap) {
			String tableJson = tableSerializer.toJson(info);
			JsonParser parser = new JsonParser();
			JsonObject tableInfoObj = parser.parse(tableJson).getAsJsonObject();
			tables.add(tableInfoObj);
		}
		JsonArray users = new JsonArray(); 
		List<DBUserData> usersMap = getUsers(); 
		for(DBUserData user: usersMap) {
			DBUserDataSerializer userSerializer = new DBUserDataSerializer(); 
			String userJSON = userSerializer.toJSON(user); 
			JsonParser parser = new JsonParser(); 
			JsonObject object = parser.parse(userJSON).getAsJsonObject(); 
			users.add(object);
		}
		root.add("tables", tables);
		root.add("users", users);
		return root.toString();
	}
	
	public void toJSON(OutputStream os) throws UnsupportedEncodingException {
		DBTableInfoSerializer tableSerializer = new DBTableInfoSerializer(); 
		DBUserDataSerializer userSerializer = new DBUserDataSerializer(); 
		JsonObject root = new JsonObject();
		root.addProperty("database", databaseName);
		JsonArray tables = new JsonArray();
		List<DBTableInfo> tablesMap = getTables(); 
		for(DBTableInfo info: tablesMap) {
			String tableJson = tableSerializer.toJson(info);
			JsonParser parser = new JsonParser();
			JsonObject tableInfoObj = parser.parse(tableJson).getAsJsonObject();
			tables.add(tableInfoObj);
		}
		JsonArray users = new JsonArray(); 
		List<DBUserData> usersMap = getUsers(); 
		for(DBUserData user: usersMap) {
			String userJSON = userSerializer.toJSON(user); 
			JsonParser parser = new JsonParser(); 
			JsonObject object = parser.parse(userJSON).getAsJsonObject(); 
			users.add(object);
		}
		root.add("tables", tables);
		root.add("users", users); 
		new PrintWriter(new OutputStreamWriter(os, "UTF-8"), true).println(root.toString()); 
	}
	
	public void toJSON(File file) throws FileNotFoundException, IOException {
		if(!file.getParentFile().exists()) file.getParentFile().mkdirs(); 
		try(FileOutputStream fos = new FileOutputStream(file)){
			toJSON(fos); 
		}
	}
	
	public void fromJSON(String json) {
		JsonParser parser = new JsonParser();
		JsonObject root = parser.parse(json).getAsJsonObject(); 
		DBTableInfoSerializer tableDeserializer = new DBTableInfoSerializer(); 
		DBUserDataSerializer userDeserializer = new DBUserDataSerializer(); 
		databaseName = root.get("database").getAsString();
		tables.clear(); 
		users.clear();
		JsonArray tablesJSON = root.getAsJsonArray("tables");
		JsonArray usersJSON  = root.getAsJsonArray("users");
		for(int i=0; i<tablesJSON.size(); i++) 
			tables.add(tableDeserializer.fromJson(tablesJSON.get(i).getAsJsonObject()));
		for(int i=0; i<usersJSON.size(); i++)
			users.add(userDeserializer.fromJSON(usersJSON.get(i).getAsJsonObject()));
	}
	
	public void fromJSON(InputStream json) {
		JsonParser parser = new JsonParser();
		JsonObject root = parser.parse(new InputStreamReader(json)).getAsJsonObject(); 
		DBTableInfoSerializer tableDeserializer = new DBTableInfoSerializer(); 
		DBUserDataSerializer userDeserializer = new DBUserDataSerializer(); 
		databaseName = root.get("database").getAsString();
		tables.clear(); 
		users.clear();
		JsonArray tablesJSON = root.getAsJsonArray("tables");
		JsonArray usersJSON  = root.getAsJsonArray("users");
		for(int i=0; i<tablesJSON.size(); i++) 
			tables.add(tableDeserializer.fromJson(tablesJSON.get(i).getAsJsonObject()));
		for(int i=0; i<usersJSON.size(); i++)
			users.add(userDeserializer.fromJSON(usersJSON.get(i).getAsJsonObject()));
	}
	
	public void fromJSON(File json) throws FileNotFoundException, IOException {
		try(FileInputStream fis =  new FileInputStream(json)){
			fromJSON(fis); 
		}
	}
	
	public void reset() {
		databaseName = "";
		tables.clear();
		users.clear();
	}
}
