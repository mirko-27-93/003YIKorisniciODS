package studiranje.ip.database.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Подаци о табелама и корисницима у бази података. 
 * @author mirko
 * @version 1.0
 */
public final class DatabaseMetadata implements Serializable, Comparable<DatabaseMetadata>, Cloneable{
	private static final long serialVersionUID = 1003827645071071294L;
	
	private String database = "";
	private List<DBTableInfo> tables = new ArrayList<>(); 
	private List<DBUserData>  users  = new ArrayList<>();
	
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		if(database.length()==0) return;
		this.database = database;
	}
	
	public synchronized List<DBTableInfo> getTables() {
		return new ArrayList<>(tables);
	}
	public synchronized List<DBUserData> getUsers() {
		return new ArrayList<>(users);
	}
	
	public synchronized void add(DBTableInfo tableInfo) {
		tables.add(tableInfo);
	}
	
	public synchronized void remove(DBTableInfo tableInfo) {
		tables.remove(tableInfo); 
	}
	
	public synchronized void add(DBUserData user) {
		users.add(user); 
	}
	
	public synchronized void remove(DBUserData user) {
		users.remove(user); 
	}
	
	public final synchronized String originalString() {
		return database; 
	} 
	
	public final synchronized int originalCode() {
		return database.hashCode();
	}
	
	public boolean nameEquals(DatabaseMetadata o) {
		return database.contentEquals(o.database); 
	}
	
	public synchronized boolean fullEquals(DatabaseMetadata o) {
		if(!database.contentEquals(o.database)) return false;
		if(!tables.equals(o.tables)) return false;
		if(!users.equals(o.users))   return false; 
		return true;
	}
	
	@Override
	public boolean equals(Object object) {
		if(object instanceof DatabaseMetadata) {
			DatabaseMetadata database = new DatabaseMetadata();
			return this.database.equals(database.database);
		}
		return false; 
	}
	
	@Override
	public int compareTo(DatabaseMetadata o) {
		return database.compareTo(o.database); 
	}
	
	@Override 
	public DatabaseMetadata clone() {
		DatabaseMetadata copy = new DatabaseMetadata(); 
		copy.database = database;
		copy.tables = new ArrayList<>();
		copy.users  = new ArrayList<>();
		for(DBTableInfo table: tables) 
			tables.add(table.clone()); 
		for(DBUserData user: users)
			users.add(user.clone()); 
		return copy; 
	}
}
