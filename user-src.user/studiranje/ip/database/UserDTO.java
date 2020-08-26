package studiranje.ip.database;

import java.io.Serializable;

import studiranje.ip.model.UserInfo;
import studiranje.ip.model.UserPassword;
import studiranje.ip.model.UserRequisit;

/**
 * Класа са свим основним информацијама о кориснику, укључив 
 * и DTO. 
 * @author mirko
 * @version 1.0
 */
public final class UserDTO implements Serializable{
	private static final long serialVersionUID = 5977460004805958764L;
	private UserInfo user; 
	private UserPassword password;
	private UserRequisit requisit; 
	private String description; 
	
	public UserDTO(UserInfo user, UserPassword password, UserRequisit requisite) {
		this.user = user;
		this.password = password; 
		this.requisit = requisite;
	}
	
	public UserInfo getUser() {
		return user;
	}
	public UserPassword getPassword() {
		return password;
	} 
	
	public boolean userEquals(UserDTO dto) {
		return user.equals(dto.getUser());
	}
	
	public boolean userDataEquals(UserDTO dto) {
		return user.fullEquals(dto.getUser());
	}
	
	public boolean passwordEquals(UserDTO dto) {
		return password.equals(dto.getPassword());
	}
	
	
	public boolean credentialsEquals(UserDTO dto) {
		return userEquals(dto) && passwordEquals(dto);
	}
	
	public boolean totalDataEquals(UserDTO dto) {
		return userDataEquals(dto) && passwordEquals(dto); 
	}
	
	@Override
	public boolean equals(Object object) {
		if(object instanceof UserDTO) {
			return totalDataEquals((UserDTO) object);
		}
		return false;
	}

	public UserRequisit getRequisit() {
		return requisit;
	}

	public void setRequisit(UserRequisit requisit) {
		this.requisit = requisit;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
