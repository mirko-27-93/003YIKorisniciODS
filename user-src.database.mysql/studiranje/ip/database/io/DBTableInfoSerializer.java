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

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import studiranje.ip.database.model.DBRecordInfo;
import studiranje.ip.database.model.DBTableInfo;

/**
 * Серијализер када је у питању једна табела базе података. 
 * @author mirko
 * @version 1.0
 */
public class DBTableInfoSerializer{
	public String toJson(DBTableInfo info) {
		JsonObject root = new JsonObject();
		JsonParser parser = new JsonParser(); 
		JsonArray columns = new JsonArray(); 
		DBRecordInfoSerializer columnMarshall = new DBRecordInfoSerializer(); 
		root.addProperty("table", info.getTableName());
		for(DBRecordInfo record: info.getTableCoulumnsSchema().values()) {
			String recordJson = columnMarshall.toJSON(record); 
			JsonObject recordObj = parser.parse(recordJson).getAsJsonObject(); 
			columns.add(recordObj);
		}
		root.add("fields", columns);
		return root.toString();
	}
	
	public DBTableInfo fromJson(String json) {
		DBTableInfo info = new DBTableInfo();
		JsonParser parser = new JsonParser(); 
		JsonObject root = parser.parse(json).getAsJsonObject(); 
		info.setTableName(root.get("table").getAsString());
		JsonArray array = root.get("fields").getAsJsonArray(); 
		DBRecordInfoSerializer columnUnmarshall = new DBRecordInfoSerializer(); 
		for(int i=0; i<array.size(); i++) {
			JsonObject object = array.get(i).getAsJsonObject(); 
			DBRecordInfo record = columnUnmarshall.fromJSON(object); 
			info.add(record.getFieldName(), record);
		}
		return info; 
	}
	
	public DBTableInfo fromJson(JsonObject root) {
		DBTableInfo info = new DBTableInfo(); 
		info.setTableName(root.get("table").getAsString());
		JsonArray array = root.get("fields").getAsJsonArray(); 
		DBRecordInfoSerializer columnUnmarshall = new DBRecordInfoSerializer(); 
		for(int i=0; i<array.size(); i++) {
			JsonObject object = array.get(i).getAsJsonObject(); 
			DBRecordInfo record = columnUnmarshall.fromJSON(object); 
			info.add(record.getFieldName(), record);
		}
		return info; 
	}
	
	public void toJson(DBTableInfo info, OutputStream os) throws UnsupportedEncodingException {
		JsonObject root = new JsonObject();
		JsonParser parser = new JsonParser(); 
		JsonArray columns = new JsonArray(); 
		DBRecordInfoSerializer columnMarshall = new DBRecordInfoSerializer(); 
		root.addProperty("table", info.getTableName());
		for(DBRecordInfo record: info.getTableCoulumnsSchema().values()) {
			String recordJson = columnMarshall.toJSON(record); 
			JsonObject recordObj = parser.parse(recordJson).getAsJsonObject(); 
			columns.add(recordObj);
		}
		root.add("fields", columns);
		new PrintWriter(new OutputStreamWriter(os,"UTF-8")).println(root.toString());
	}
	
	public void toJson(DBTableInfo info, File file) throws FileNotFoundException, IOException {
		if(!file.getParentFile().exists()) file.getParentFile().mkdirs(); 
		try(FileOutputStream fos = new FileOutputStream(file)){
			toJson(info, fos);
		}
	}
	
	public DBTableInfo fromJson(InputStream json) throws JsonIOException, JsonSyntaxException, UnsupportedEncodingException {
		DBTableInfo info = new DBTableInfo();
		JsonParser parser = new JsonParser(); 
		JsonObject root = parser.parse(new InputStreamReader(json, "UTF-8")).getAsJsonObject(); 
		info.setTableName(root.get("table").getAsString());
		JsonArray array = root.get("fields").getAsJsonArray(); 
		DBRecordInfoSerializer columnUnmarshall = new DBRecordInfoSerializer(); 
		for(int i=0; i<array.size(); i++) {
			JsonObject object = array.get(i).getAsJsonObject(); 
			DBRecordInfo record = columnUnmarshall.fromJSON(object); 
			info.add(record.getFieldName(), record);
		}
		return info; 
	}
	
	public DBTableInfo fromJson(File json) throws JsonIOException, JsonSyntaxException, FileNotFoundException, IOException {
		try(FileInputStream fis = new FileInputStream(json)){
			return fromJson(json);
		}
	}
}
