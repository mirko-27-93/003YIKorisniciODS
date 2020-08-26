package studiranje.ip.service.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import studiranje.ip.database.model.DBTableInfo;

/**
 * Одговор на захтијев за листу података. 
 * @author mirko
 * @version 1.0
 */
public class TableListResponse implements Serializable{
	private static final long serialVersionUID = 8727550748110760346L;
	
	private List<DBTableInfo> tables = new ArrayList<>();

	public List<DBTableInfo> getTableInfo() {
		return tables;
	}
	
	public List<DBTableInfo> liveTableInfo(){
		return tables;
	}

	public List<DBTableInfo> copyTableInfo(){
		return new ArrayList<>(tables); 
	}
	
	public synchronized List<DBTableInfo> cloneTableInfo(){
		ArrayList<DBTableInfo> tables = new ArrayList<>();
		for(DBTableInfo table: tables) 
			tables.add(table.clone());
		
		return tables;
	}
	
	public void setTableInfo(List<DBTableInfo> tableInfo) {
		this.tables = tableInfo;
	} 
	
	public void aliasTableInfo(List<DBTableInfo> tableInfo) {
		this.tables = tableInfo;
	}
	
	public void applyTableInfo(List<DBTableInfo> tableInfo) {
		this.tables = new ArrayList<>(tableInfo); 
	}
	
	public synchronized void flatTableInfo(List<DBTableInfo> tables) {
		this.tables = new ArrayList<>(); 
		for(DBTableInfo table: tables) {
			this.tables.add(table.clone()); 
		}
	}
}
