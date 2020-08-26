package studiranje.ip.database.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Опис записа у табели базе података. Схема за ред у табели.
 * @author mirko
 * @version 1.0
 */
public final class DBTableInfo implements Serializable, Cloneable,  Comparable<DBTableInfo>{
	private static final long serialVersionUID = -6435038507809660634L;
	private String tableName = ""; 
	private HashMap<String, DBRecordInfo> tableCoulumnsSchema = new HashMap<String, DBRecordInfo>();
	
	public String getTableName() {
		if(tableName==null) tableName=""; 
		return tableName;
	}
	public void setTableName(String tableName) {
		if(tableName==null) tableName = ""; 
		this.tableName = tableName;
	}
	public HashMap<String, DBRecordInfo> getTableCoulumnsSchema() {
		return tableCoulumnsSchema;
	}
	public void setTableCoulumnsSchema(HashMap<String, DBRecordInfo> tableCoulumnsSchema) {
		this.tableCoulumnsSchema = tableCoulumnsSchema;
	}
	
	public final String originalString() {
		return super.toString(); 
	}
	
	public final int originalCode() {
		return super.hashCode(); 
	}
	
	@Override
	public String toString() {
		return tableName; 
	}
	
	@Override
	public int hashCode() {
		return tableName.hashCode();
	}
	
	public boolean nameEquals(DBTableInfo info) {
		return tableName.contentEquals(info.tableName);
	}
	
	public boolean fullEquals(DBTableInfo info) {
		if(!tableName.contentEquals(info.tableName)) return false; 
		if(!tableCoulumnsSchema.equals(info.tableCoulumnsSchema)) return false; 
		return true; 
	}
	
	@Override 
	public boolean equals(Object obj) {
		if(obj instanceof DBTableInfo) 
			return fullEquals((DBTableInfo) obj);
		return  false; 
	}
	
	@Override
	public int compareTo(DBTableInfo o) {
		return tableName.compareTo(o.tableName);
	} 

	
	public synchronized DBTableInfo clone() {
		DBTableInfo info = new DBTableInfo(); 
		info.tableName = tableName; 
		info.tableCoulumnsSchema = new HashMap<>(); 
		for(Map.Entry<String, DBRecordInfo> me: tableCoulumnsSchema.entrySet()) {
			info.tableCoulumnsSchema.put(me.getKey(), me.getValue().clone());
		}
		return info; 
	}
	
	public synchronized void add(String id, DBRecordInfo info) {
		tableCoulumnsSchema.put(id, info); 
	}
	
	public synchronized void remove(String id) {
		tableCoulumnsSchema.remove(id); 
	}
}
