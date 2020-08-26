package studiranje.ip.bean;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import studiranje.ip.controller.UserGeneralController;
import studiranje.ip.database.UserDTO;
import studiranje.ip.model.UserInfo;
import studiranje.ip.model.UserPassword;
import studiranje.ip.model.UserRequisit;

/**
 * Зрно које се користи за основне податке корисника.
 * @author mirko
 * @version 1.0
 */
public class UserBean implements Serializable{
	private static final long serialVersionUID = -2265548918562701844L;
	private transient UserGeneralController controller = UserGeneralController.getInstance();
	
	private String username = ""; 
	private String firstname = "";
	private String secondname = ""; 
	private String email = ""; 
	private String password = "";
	private UserRequisit requisit = new UserRequisit();
	private HttpSession session;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		if(username==null) username = "";
		this.username = username;
		requisit.setUsername(username);
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		if(firstname==null) firstname = ""; 
		this.firstname = firstname;
	}
	public String getSecondname() {
		return secondname;
	}
	public void setSecondname(String secondname) {
		if(secondname==null) secondname = "";
		this.secondname = secondname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	} 
	
	public UserInfo getUserInfo() {
		UserInfo ui = new UserInfo(username, firstname, secondname, email);
		return ui;
	}
	
	public UserPassword getPasswordInfo() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		UserPassword up = new UserPassword(password);
		up.setPassword(password);
		return up; 
	}
	
	public UserDTO getAllInfo() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		UserRequisit ur = new UserRequisit();
		UserDTO dto = new UserDTO(getUserInfo(), getPasswordInfo(), ur);
		return dto;
	}
	
	public void reset() {
		username = ""; 
		password = ""; 
		email = "";
		password = ""; 
		secondname = ""; 
		firstname = "";
		requisit.setUsername("");
		requisit.setCountryFlagPicture(null);
		requisit.setProfilePicture(null);
		requisit.setUserPicture(null);
		requisit.setWebappNotifications(false);
		requisit.setEmailNotifications(false);
	}
	
	public void read(UserDTO dto) {
		reset();
		if(dto==null) return; 
		if(dto.getUser()==null) return; 
		username = dto.getUser().getUsername(); 
		firstname = dto.getUser().getFirstname(); 
		secondname = dto.getUser().getSecondname(); 
		email = dto.getUser().getEmail(); 
	}
	
	/**
	 * Корисничко име није препоручљиво мијењати сетером и сетовањем. 
	 * @return Објекат за додатне податке који се вежу за корисника. 
	 */
	public UserRequisit getRequisit() {
		return requisit;
	}
	
	public String getPasswordRecord() throws UnsupportedEncodingException, SQLException {
		return controller.getRegistrator(session).getUserDataLink().getPassword(username).getToPasswordRecord(); 
	}
	
	public HttpSession getSession() {
		return session;
	}
	public void setSession(HttpSession session) {
		this.session = session;
	}
}
