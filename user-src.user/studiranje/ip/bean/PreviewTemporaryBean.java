package studiranje.ip.bean;

import java.io.Serializable;

/**
 * Зрно за привремени приказ слика за корисника. 
 * @author mirko
 * @version 1.0
 */
public class PreviewTemporaryBean implements Serializable{
	private static final long serialVersionUID = -3505938651751894866L;
	private byte[] profilePicture; 
	private byte[] userPicture;
	

	public byte[] getProfilePicture() {
		try {
			return this.profilePicture;
		} finally {
			this.profilePicture = null; 
		}
	}
	public void setProfilePicture(byte[] profilePicture) {
		this.profilePicture = profilePicture;
	}
	public byte[] getUserPicture() {
		try {
			return this.userPicture;
		}finally {
			this.userPicture = null; 
		}
	}
	public void setUserPicture(byte[] userPicture) {
		this.userPicture = userPicture;
	}
	
	public boolean existsProfilePicture() {
		return profilePicture != null; 
	}
	
	public boolean existsUserPicture() {
		return userPicture != null; 
	}
}
