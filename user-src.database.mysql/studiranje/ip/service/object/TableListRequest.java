package studiranje.ip.service.object;

/**
 * Захтијев за табела са сервисираних старих верзија база података. 
 * @author mirko
 * @version 1.0
 */
public class TableListRequest {
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
}
