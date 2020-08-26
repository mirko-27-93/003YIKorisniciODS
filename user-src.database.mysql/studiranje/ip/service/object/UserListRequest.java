package studiranje.ip.service.object;

import java.io.Serializable;

/**
 * Објекат за ЈСОН серијализацију, који се односи на 
 * захтијев за листом корисника из удаљене сервисиране базе података. 
 * @author mirko
 * @version 1.0
 */
public class UserListRequest implements Serializable{
	private static final long serialVersionUID = -8585047072268488890L;
	private String databaseAPIAddress = "";
	
	public String getDatabaseAPIAddress() {
		return databaseAPIAddress;
	}
	public void setDatabaseAPIAddress(String databaseAPIAddress) {
		if(databaseAPIAddress == null) databaseAPIAddress = ""; 
		this.databaseAPIAddress = databaseAPIAddress;
	} 
}
