package studiranje.ip.database.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Подаци о свим базама података и корисницима на хосту СУБП (систем за управљање 
 * базом података, DBMS). 
 * @author mirko
 * @version 1.0
 */
public final class DBMSHostMetadata implements Serializable, Comparable<DBMSHostMetadata>, Cloneable{
	private static final long serialVersionUID = -2960520410684537349L;
	private String hostOrigin = "";

	public String getHostOrigin() {
		return hostOrigin;
	}

	public void setHostOrigin(String hostOrigin) {
		if(hostOrigin.contentEquals(hostOrigin)) 
			this.hostOrigin = hostOrigin;
	}
	
	private HashMap<String, List<DBTableInfo>> tables = new HashMap<>();
	private List<DBUserData> users = new ArrayList<>();

	public List<String> getDatabases(){
		return new ArrayList<>(tables.keySet());
	}
	
	
	public List<DBTableInfo> getTablesFor(String database){
		ArrayList<DBTableInfo> list = new ArrayList<>();
		List<DBTableInfo> originals = tables.get(database); 
		if(originals==null) return new ArrayList<>();
		for(DBTableInfo original: originals) 
			list.add(original.clone()); 
		return list;
	}
	
	public List<DBRecordInfo> getRecordFor(String database, String table){
		try {
			ArrayList<DBRecordInfo> list = new ArrayList<>(); 
			List<DBTableInfo> tables = this.tables.get(database); 
			List<DBRecordInfo> originals = new ArrayList<>();
			for(DBTableInfo tableObj : tables) {
				if(tableObj.getTableName().contentEquals(table)) {
					originals= new ArrayList<>(tableObj.getTableCoulumnsSchema().values()); 
					break;
				}
			}
			for(DBRecordInfo record: originals) {
				list.add(record.clone()); 
			}
			return list; 
		}catch(Exception ex) {
			return new ArrayList<>();
		}
	}
	
	public List<DBUserData> getUsers(){
		return new ArrayList<>(users); 
	}
	
	public final int orginalCode() {
		return super.hashCode(); 
	}
	
	public final String originalString() {
		return super.toString();
	}
	
	@Override
	public int hashCode() {
		return hostOrigin.hashCode();
	}
	@Override
	public String toString() {
		return hostOrigin;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof DBMSHostMetadata) {
			DBMSHostMetadata hostMetadata = (DBMSHostMetadata) o; 
			return hostOrigin.contentEquals(hostMetadata.hostOrigin); 
		}
		return false;
	}
	
	@Override
	public int compareTo(DBMSHostMetadata o) {
		return hostOrigin.compareTo(o.hostOrigin);
	}
}
