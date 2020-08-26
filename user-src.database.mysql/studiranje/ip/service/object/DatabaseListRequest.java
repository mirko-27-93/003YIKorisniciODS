package studiranje.ip.service.object;

import java.io.Serializable;

/**
 * Објеката захтјева сервису за удаљену базу података. 
 * @author mirko
 * @versuion 1.0
 */
public class DatabaseListRequest implements Serializable{
	private static final long serialVersionUID = 5184485040428615114L;
	
	private String databaseAPIAddress = "";
	
	public String getDatabaseAPIAddress() {
		return databaseAPIAddress;
	}
	public void setDatabaseAPIAddress(String databaseAPIAddress) {
		if(databaseAPIAddress == null) databaseAPIAddress = ""; 
		this.databaseAPIAddress = databaseAPIAddress;
	} 
}
