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

import studiranje.ip.database.model.DBRecordInfo;

/**
 * Серијализација описа колоне у различите формате и десеријализација. 
 * @author mirko
 * @version 1.0
 */
public class DBRecordInfoSerializer {
	public String toJSON(DBRecordInfo info) {
		JsonObject root = new JsonObject(); 
		root.addProperty(info.getFieldName(), info.getField());
		root.addProperty(info.getClassicName(), info.getClassic());
		root.addProperty(info.getExtraName(), info.getExtra());
		root.addProperty(info.getKeyName(), info.getKey());
		root.addProperty(info.getTypeName(), info.getType());
		root.addProperty(info.getZeroName(), info.getZero());
		return root.toString(); 
	}
	public DBRecordInfo fromJSON(String json) {
		DBRecordInfo info = new DBRecordInfo();
		JsonParser parser = new JsonParser(); 
		JsonObject root = parser.parse(json).getAsJsonObject();
		info.setField(root.get(info.getFieldName()).getAsString());
		info.setExtra(root.get(info.getExtraName()).getAsString());
		info.setZero(root.get(info.getZeroName()).getAsString());
		info.setClassic(root.get(info.getClassicName()).getAsString());
		info.setType(root.get(info.getTypeName()).getAsString());
		info.setKey(root.get(info.getKeyName()).getAsString());
		return info;
	} 
	public DBRecordInfo fromJSON(JsonObject root) {
		DBRecordInfo info = new DBRecordInfo();
		info.setField(root.get(info.getFieldName()).getAsString());
		info.setExtra(root.get(info.getExtraName()).getAsString());
		info.setZero(root.get(info.getZeroName()).getAsString());
		info.setClassic(root.get(info.getClassicName()).getAsString());
		info.setType(root.get(info.getTypeName()).getAsString());
		info.setKey(root.get(info.getKeyName()).getAsString());
		return info;
	} 
	public void toJSON(DBRecordInfo info, OutputStream os) throws UnsupportedEncodingException {
		JsonObject root = new JsonObject(); 
		root.addProperty(info.getFieldName(), info.getField());
		root.addProperty(info.getClassicName(), info.getClassic());
		root.addProperty(info.getExtraName(), info.getExtra());
		root.addProperty(info.getKeyName(), info.getKey());
		root.addProperty(info.getTypeName(), info.getType());
		root.addProperty(info.getZeroName(), info.getZero());
		new PrintWriter(new OutputStreamWriter(os,"UTF-8")).println(root.getAsString()); 
	}
	public void toJSON(DBRecordInfo info, File file) throws FileNotFoundException, IOException {
		if(!file.getParentFile().exists()) file.getParentFile().mkdirs(); 
		try(FileOutputStream fos = new FileOutputStream(file)){
			toJSON(info, fos);
		}
	}
	public DBRecordInfo fromJSON(InputStream is) throws JsonIOException, JsonSyntaxException, UnsupportedEncodingException {
		DBRecordInfo info = new DBRecordInfo();
		JsonParser parser = new JsonParser(); 
		JsonObject root = parser.parse(new InputStreamReader(is,"UTF-8")).getAsJsonObject();
		info.setField(root.get(info.getFieldName()).getAsString());
		info.setExtra(root.get(info.getExtraName()).getAsString());
		info.setZero(root.get(info.getZeroName()).getAsString());
		info.setClassic(root.get(info.getClassicName()).getAsString());
		info.setType(root.get(info.getTypeName()).getAsString());
		info.setKey(root.get(info.getKeyName()).getAsString());
		return info;
	} 
	
	public DBRecordInfo fromJSON(File file) throws JsonIOException, JsonSyntaxException, FileNotFoundException, IOException {
		try(FileInputStream fis = new FileInputStream(file)){
			return fromJSON(fis);
		}
	} 
	
	public Properties toProperties(DBRecordInfo info) {
		Properties properties = new Properties();
		properties.setProperty(info.getFieldName(), info.getField()); 
		properties.setProperty(info.getTypeName(), info.getType());
		properties.setProperty(info.getKeyName(), info.getKey());
		properties.setProperty(info.getZeroName(), info.getZero());
		properties.setProperty(info.getClassicName(), info.getClassic());
		properties.setProperty(info.getExtraName(), info.getExtra());
		return properties; 
	}
	
	public DBRecordInfo fromProperties(Properties properties) {
		DBRecordInfo info = new DBRecordInfo();
		info.setField(properties.getProperty(info.getFieldName()));
		info.setClassic(properties.getProperty(info.getClassicName()));
		info.setExtra(properties.getProperty(info.getExtraName()));
		info.setKey(properties.getProperty(info.getKeyName()));
		info.setZero(properties.getProperty(info.getZeroName()));
		return info;
	}
}
