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
import java.util.Properties;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import studiranje.ip.database.model.DBUserData;

/**
 * Серијализација и десеријализација података о кориснику. 
 * @author mirko
 * @version 1.0
 */
public class DBUserDataSerializer {
	public String toJSON(DBUserData data) {
		JsonObject root = new JsonObject(); 
		root.addProperty("user", data.getUserName());
		root.addProperty("host", data.getHostName());
		root.addProperty("password_record", data.getAuthenticationString());
		return root.toString(); 
	}
	public DBUserData fromJSON(String json) {
		DBUserData data = new DBUserData();
		JsonParser parser = new JsonParser();
		JsonObject root = parser.parse(json).getAsJsonObject(); 
		data.setUserName(root.get("user").getAsString());
		data.setHostName(root.get("host").getAsString());
		data.setAuthenticationString(root.get("password_record").getAsString());
		return data; 
	}
	public DBUserData fromJSON(JsonObject root) {
		DBUserData data = new DBUserData();
		data.setUserName(root.get("user").getAsString());
		data.setHostName(root.get("host").getAsString());
		data.setAuthenticationString(root.get("password_record").getAsString());
		return data; 
	}
	public void toJSON(DBUserData data, OutputStream os) throws UnsupportedEncodingException {
		JsonObject root = new JsonObject(); 
		root.addProperty("user", data.getUserName());
		root.addProperty("host", data.getHostName());
		root.addProperty("password_record", data.getAuthenticationString());
		new PrintWriter(new OutputStreamWriter(os,"UTF-8")).println(root.toString()); 
	}
	
	public void toJSON(DBUserData data, File file) throws FileNotFoundException, IOException {
		if(!file.getParentFile().exists()) file.getParentFile().mkdir(); 
		try(FileOutputStream fos= new FileOutputStream(file)){
			toJSON(data, fos);
		}
	}
	public DBUserData fromJSON(InputStream json) throws JsonIOException, JsonSyntaxException, UnsupportedEncodingException {
		DBUserData data = new DBUserData();
		JsonParser parser = new JsonParser();
		JsonObject root = parser.parse(new InputStreamReader(json, "UTF-8")).getAsJsonObject(); 
		data.setUserName(root.get("user").getAsString());
		data.setHostName(root.get("host").getAsString());
		data.setAuthenticationString(root.get("password_record").getAsString());
		return data;
	}
	public DBUserData fromJSON(File json) throws JsonIOException, JsonSyntaxException, FileNotFoundException, IOException {
		try(FileInputStream fis  = new FileInputStream(json)){
			return fromJSON(json);
		}
	}
	
	public Properties toProperties(DBUserData data) {
		Properties properties = new Properties(); 
		properties.put("password_record", data.getAuthenticationString());
		properties.put("user", data.getUserName()); 
		properties.put("host", data.getHostName()); 
		return properties; 
	}
	
	public DBUserData fromProperties(Properties properties) {
		DBUserData data = new DBUserData();
		data.setAuthenticationString(properties.getProperty("password_record"));
		data.setUserName(properties.getProperty("user"));
		data.setHostName(properties.getProperty("host"));
		return data; 
	}
}
