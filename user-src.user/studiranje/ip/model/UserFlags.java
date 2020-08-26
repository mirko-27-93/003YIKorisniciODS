package studiranje.ip.model;

import java.io.Serializable;

/**
 * Користи се као објекат основних информационих обиљежја за корисника.
 * Нпр. Начини размјена порука, дозвољено преко веба или не, однсно преко емаил-а.
 * Даље контрола корисничких сесија, дозвољена или не. 
 * @author mirko
 * @version 1.0
 */
public class UserFlags implements Serializable{
	private static final long serialVersionUID = 6446101188466882477L;
	
	public final static UserFlags DEFAULT_BLANK_USER_FLAGS = new UserFlags(); 
	
	private String username = ""; 
	private boolean webNotifications; 
	private boolean emailNotifications; 
	private boolean userSessionsControl; 
	
	private UserFlags() {}
	
	public UserFlags(String username) {
		if(username==null) throw new NullPointerException("ZERO");
		if(username.trim().isEmpty()) throw new NullPointerException("BLANK");
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isWebNotifications() {
		return webNotifications;
	}

	public void setWebNotifications(boolean webNotifications) {
		this.webNotifications = webNotifications;
	}
	
	public void setWebNotifications() {
		this.webNotifications = true;
	}
	
	public void unsetWebNotifications() {
		this.webNotifications = false;
	}
	
	public void resetWebNotifications() {
		this.webNotifications = false;
	}
	
	public boolean isEmailNotifications() {
		return emailNotifications;
	}

	public void setEmailNotifications(boolean emailNotifications) {
		this.emailNotifications = emailNotifications;
	}

	public void setEmailNotifications() {
		this.emailNotifications = true;
	}
	
	public void unsetEmailNotifications() {
		this.emailNotifications = false;
	}
	
	public void resetEmailNotifications() {
		this.emailNotifications = false;
	}
	
	public boolean isUserSessionsControl() {
		return userSessionsControl;
	}

	public void setUserSessionsControl(boolean userSessionsControl) {
		this.userSessionsControl = userSessionsControl;
	}
}
