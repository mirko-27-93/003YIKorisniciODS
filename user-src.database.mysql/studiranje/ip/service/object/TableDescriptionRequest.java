package studiranje.ip.service.object;

import java.io.Serializable;

/**
 * Захтијев за информације о табелама. 
 * @author mirko
 * @version 1.0
 */
public class TableDescriptionRequest implements Serializable{
	private static final long serialVersionUID = 5087263393960120824L;
	
	private String databaseAPIAddress = "";
	public String getDatabaseAPIAddress() {
		return databaseAPIAddress;
	}
	public void setDatabaseAPIAddress(String databaseAPIAddress) {
		if(databaseAPIAddress == null) databaseAPIAddress = ""; 
		this.databaseAPIAddress = databaseAPIAddress;
	} 
	
	private String databaseName = "";
	public String getDatabaseName() {
		return databaseName;
	}
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	} 
	
	private String tableName = "";
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	} 
	
}
