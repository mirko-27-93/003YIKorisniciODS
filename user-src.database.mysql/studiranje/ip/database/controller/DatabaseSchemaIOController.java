package studiranje.ip.database.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import studiranje.ip.data.DBRootDAO;
import studiranje.ip.database.io.DatabaseInfoSerializaer;
import studiranje.ip.database.model.DBRecordInfo;
import studiranje.ip.database.model.DBTableInfo;
import studiranje.ip.database.model.DBUserData;
import studiranje.ip.database.util.DatabaseNotFoundException;

/**
 * Схема базе података. Очитавање из базе података, и манервисање датотекама.
 * @author mirko
 * @version 1.0
 */
public class DatabaseSchemaIOController{
	private DatabaseInfoSerializaer databaseInfoObj = new DatabaseInfoSerializaer();
	private DBRootDAO dao; 
	
	public DatabaseSchemaIOController(DBRootDAO dao) {
		this.dao = dao; 
	}
	public DatabaseInfoSerializaer getDatabaseInfoObj() {
		return databaseInfoObj;
	}
	
	public void setDatabaseInfoObj(DatabaseInfoSerializaer databaseInfoObj) {
		this.databaseInfoObj = databaseInfoObj;
	}
	
	public DBRootDAO getDao() {
		return dao;
	}
	public void setDao(DBRootDAO dao) {
		this.dao = dao;
	}
	
	public List<String> getDatabase() throws SQLException{
		return new ArrayList<>(dao.getDatabases()); 
	} 
	
	public List<DBTableInfo> getDatabase(String database) throws SQLException{
		List<DBTableInfo> list = new ArrayList<>();
		for(String table: dao.getTables(database)) 
			list.add(dao.getTableInfo(database, table));
		return list; 
	}
	
	public List<DBUserData> getUsers(String database) throws SQLException{
		return new ArrayList<>(dao.getUsers());
	}
	
	
	
	public String describeDatabaseInJSON(String databasename) throws SQLException {
		List<String> databases = dao.getDatabases();  databaseInfoObj.reset();
		if(!databases.contains(databasename)) throw new DatabaseNotFoundException();
		databaseInfoObj.setDatabaseName(databasename);
		List<DBUserData> users = dao.getUsers();
		JsonObject root = new JsonObject();
		
		root.addProperty("database", databasename);
		
		boolean found = false; 
		for(String database: databases) {
			if(databasename.contentEquals(database))
				found = true; 
		}
		if(!found) throw new DatabaseNotFoundException();
		for(DBUserData u: users) {
			databaseInfoObj.add(u);
		}
		
		List<DBTableInfo> tables = this.getDatabase(databasename);
		for(DBTableInfo t: tables) {
			databaseInfoObj.add(t);
		}
		
		databaseInfoObj.setDatabaseName(databasename);
		return databaseInfoObj.toJSON(); 
	}
	
	
	
	
	
	
	public void describeDatabaseInJSON(String databasename, OutputStream os) throws SQLException, UnsupportedEncodingException {
		List<String> databases = dao.getDatabases();  databaseInfoObj.reset();
		if(!databases.contains(databasename)) throw new DatabaseNotFoundException();
		databaseInfoObj.setDatabaseName(databasename);
		List<DBUserData> users = dao.getUsers();
		JsonObject root = new JsonObject();
		
		root.addProperty("database", databasename);
		
		boolean found = false; 
		for(String database: databases) {
			if(databasename.contentEquals(database))
				found = true; 
		}
		if(!found) throw new DatabaseNotFoundException();
		for(DBUserData u: users) {
			databaseInfoObj.add(u);
		}
		
		List<DBTableInfo> tables = this.getDatabase(databasename);
		for(DBTableInfo t: tables) {
			databaseInfoObj.add(t);
		}
		
		databaseInfoObj.setDatabaseName(databasename);
		databaseInfoObj.toJSON(os);
	}
	
	
	
	
	public void describeDatabaseInJSON(String databasename, File file) throws SQLException, FileNotFoundException, IOException {
		List<String> databases = dao.getDatabases();  databaseInfoObj.reset();
		if(!databases.contains(databasename)) throw new DatabaseNotFoundException();
		databaseInfoObj.setDatabaseName(databasename);
		List<DBUserData> users = dao.getUsers();
		JsonObject root = new JsonObject();
		
		root.addProperty("database", databasename);
		
		boolean found = false; 
		for(String database: databases) {
			if(databasename.contentEquals(database))
				found = true; 
		}
		if(!found) throw new DatabaseNotFoundException();
		for(DBUserData u: users) {
			databaseInfoObj.add(u);
		}
		
		List<DBTableInfo> tables = this.getDatabase(databasename);
		for(DBTableInfo t: tables) {
			databaseInfoObj.add(t);
		}
		
		databaseInfoObj.setDatabaseName(databasename);
		databaseInfoObj.toJSON(file);
	}
	
	
	
	@SuppressWarnings("unused")
	public boolean checkDatababaseInSchema(String jsonSchema, String forDatabasename) throws SQLException {
		String databasename = forDatabasename; 
		
		JsonObject schemaRoot = new JsonParser().parse(jsonSchema).getAsJsonObject(); 
		String schemaDatabaseName = schemaRoot.get("database").getAsString();  
		
		List<String> databases = dao.getDatabases();  databaseInfoObj.reset();
		if(!databases.contains(databasename)) return false;
		
		databaseInfoObj.setDatabaseName(databasename);
		if(!databasename.contentEquals(schemaDatabaseName)) return false;
		
		boolean found = false; 
		for(String database: databases) {
			if(databasename.contentEquals(database))
				found = true; 
		}
		if(!found) return false;
	
		
		List<DBTableInfo> tables = getDatabase(databasename);
		
		JsonArray shTables = schemaRoot.get("tables").getAsJsonArray(); 
		x: for(int i=0; i<shTables.size(); i++) {
			String tablename = shTables.get(i).getAsJsonObject().get("table").getAsString();
			JsonArray fields = shTables.get(i).getAsJsonObject().get("fields").getAsJsonArray(); 
			DBTableInfo neo = new DBTableInfo(); 
			neo.setTableName(tablename);
			y: for(DBTableInfo table: tables) {
				if(table.getTableName().contentEquals(tablename)) {
					z: for(int j=0; j<fields.size(); j++) {
						String field = fields.get(j).getAsJsonObject().get("field").getAsString();
						String extra = fields.get(j).getAsJsonObject().get("extra").getAsString();
						String classic = fields.get(j).getAsJsonObject().get("default").getAsString();
						String zero = fields.get(j).getAsJsonObject().get("null").getAsString();
						String key = fields.get(j).getAsJsonObject().get("key").getAsString();
						String type = fields.get(j).getAsJsonObject().get("type").getAsString();
						w: for(DBRecordInfo col : table.getTableCoulumnsSchema().values()) {
							 if(!col.getField().contentEquals(field)) continue;
							 if(!col.getExtra().contentEquals(extra)) continue;
							 if(!col.getClassic().contentEquals(classic)) continue;
							 if(!col.getZero().contentEquals(zero)) continue;
							 if(!col.getKey().contentEquals(key)) continue;
							 if(!col.getType().contentEquals(type)) continue;
							 continue x; 
						}
						return false; 
					}
				}
				return false; 
			}
			return false; 
		}
		return true;
	}
	
	@SuppressWarnings("unused")
	public boolean checkTablesInSchema(String jsonSchema, String forDatabasename) throws SQLException {
		String databasename = forDatabasename; 
		
		JsonObject schemaRoot = new JsonParser().parse(jsonSchema).getAsJsonObject(); 
		
		List<String> databases = dao.getDatabases();  databaseInfoObj.reset();
		if(!databases.contains(databasename)) return false;
		
		databaseInfoObj.setDatabaseName(databasename);
		
		boolean found = false; 
		for(String database: databases) {
			if(databasename.contentEquals(database))
				found = true; 
		}
		if(!found) return false;
	
		
		List<DBTableInfo> tables = getDatabase(databasename);
		
		JsonArray shTables = schemaRoot.get("tables").getAsJsonArray(); 
		x: for(int i=0; i<shTables.size(); i++) {
			String tablename = shTables.get(i).getAsJsonObject().get("table").getAsString();
			JsonArray fields = shTables.get(i).getAsJsonObject().get("fields").getAsJsonArray(); 
			DBTableInfo neo = new DBTableInfo(); 
			neo.setTableName(tablename);
			y: for(DBTableInfo table: tables) {
				if(table.getTableName().contentEquals(tablename)) {
					z: for(int j=0; j<fields.size(); j++) {
						String field = fields.get(j).getAsJsonObject().get("field").getAsString();
						String extra = fields.get(j).getAsJsonObject().get("extra").getAsString();
						String classic = fields.get(j).getAsJsonObject().get("default").getAsString();
						String zero = fields.get(j).getAsJsonObject().get("null").getAsString();
						String key = fields.get(j).getAsJsonObject().get("key").getAsString();
						String type = fields.get(j).getAsJsonObject().get("type").getAsString();
						w: for(DBRecordInfo col : table.getTableCoulumnsSchema().values()) {
							 if(!col.getField().contentEquals(field)) continue;
							 if(!col.getExtra().contentEquals(extra)) continue;
							 if(!col.getClassic().contentEquals(classic)) continue;
							 if(!col.getZero().contentEquals(zero)) continue;
							 if(!col.getKey().contentEquals(key)) continue;
							 if(!col.getType().contentEquals(type)) continue;
							 continue x; 
						}
						return false; 
					}
				}
				return false; 
			}
			return false; 
		}
		return true;
	}
	
	public boolean checkDatabaseInSchema(InputStream jsonSchema, String forDatabasename) throws SQLException {
		String jsonContent = ""; 
		try(Scanner scanner = new Scanner(jsonSchema,"UTF-8")){
			while(scanner.hasNextLine()) {
				jsonContent += scanner.nextLine() + "\n"; 
			}
			return checkDatababaseInSchema(jsonContent, forDatabasename);
		}
	}
	
	public boolean checkTablesInSchema(InputStream jsonSchema, String forDatabasename) throws SQLException {
		String jsonContent = ""; 
		try(Scanner scanner = new Scanner(jsonSchema,"UTF-8")){
			while(scanner.hasNextLine()) {
				jsonContent += scanner.nextLine() + "\n"; 
			}
			return checkTablesInSchema(jsonContent, forDatabasename);
		}
	}
}
