package studiranje.ip.data.event;

/**
 * Извршна јединица при измјени корисничког имена. 
 * @author mirko
 * @version 1.0
 */
public abstract class UpdateUsernameRunnable implements Runnable{
	private String oldUsername = ""; 
	private String neoUsername = ""; 
	
	public String getOldUsername() {
		return oldUsername;
	}
	public void setOldUsername(String oldUsername) {
		if(oldUsername == null) oldUsername = ""; 
		this.oldUsername = oldUsername;
	}
	
	public String getNeoUsername() {
		return neoUsername;
	}
	public void setNeoUsername(String neoUsername) {
		if(neoUsername==null) neoUsername = ""; 
		this.neoUsername = neoUsername;
	}

	@Override
	public abstract void run();  
	
}
