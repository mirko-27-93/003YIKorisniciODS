package studiranje.ip.data.event;

/**
 * Извршне активности при брисању. 
 * @author mirko
 * @version 1.0
 */
public abstract class DeleteUserRunnable implements Runnable{
	private String username = "";
	
	@Override
	public abstract void run();

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		if(username==null) username = "";
		this.username = username;
	} 
}
