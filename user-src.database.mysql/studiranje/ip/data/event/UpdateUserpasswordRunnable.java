package studiranje.ip.data.event;

/**
 * Окружење извршне активности када је у питању промјена лозинке. 
 * @author mirko
 * @version 1.0
 */
public abstract class UpdateUserpasswordRunnable implements Runnable{
	private String username = ""; 
	private String neoPassword = ""; 
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		if(username==null) username = ""; 
		this.username = username;
	}
	

	public String getNeoPassword() {
		return neoPassword;
	}

	public void setNeoPassword(String neoPassword) {
		if(neoPassword==null) neoPassword = ""; 
		this.neoPassword = neoPassword;
	}

	@Override
	public abstract void run(); 	
}
