package studiranje.ip.model;

import java.io.File;
import java.io.Serializable;

/**
 * Класа за додатне податке о кориснику, као што су профилна и корисничка слика. 
 * Ту су о информације, како ће апликација, слати обавјештења корисницима. 
 * @author mirko
 * @version 1.0
 */
public class UserRequisit implements Serializable{
	private static final long serialVersionUID = -8207174241456962621L;
	private String username = "";
	private String telephone = "";
	private String city = "";
	private String country = "";
	private boolean emailNotifications = false; 
	private boolean webappNotifications = false; 
	private File profilePicture;
	private File countryFlagPicture;
	private File userPicture;
	
	public UserRequisit() {}
	
	public UserRequisit(String username) {
		if(username==null) throw new NullPointerException("NULL"); 
		if(username.trim().length()==0) throw new NullPointerException("BLANK");
		this.username = username; 
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username; 
	}
	public boolean isEmailNotifications() {
		return emailNotifications;
	}
	public void setEmailNotifications(boolean emailNotifications) {
		this.emailNotifications = emailNotifications;
	}
	public boolean isWebappNotifications() {
		return webappNotifications;
	}
	public void setWebappNotifications(boolean webappNotifications) {
		this.webappNotifications = webappNotifications;
	}
	public File getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(File profilePicture) {
		this.profilePicture = profilePicture;
	}
	public File getCountryFlagPicture() {
		return countryFlagPicture;
	}
	public void setCountryFlagPicture(File countryFlagPicture) {
		this.countryFlagPicture = countryFlagPicture;
	}
	public File getUserPicture() {
		return userPicture;
	}
	public void setUserPicture(File userPicture) {
		this.userPicture = userPicture;
	}

	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
}
