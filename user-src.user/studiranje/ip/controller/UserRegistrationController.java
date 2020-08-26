package studiranje.ip.controller;

import studiranje.ip.database.UserDAO;
import studiranje.ip.database.UserDTO;
import studiranje.ip.exception.UserDuplicateEmailException;
import studiranje.ip.exception.UserDuplicationException;
import studiranje.ip.model.UserInfo;
import studiranje.ip.model.UserPassword;

/**
 * Контрола за процесе регистрације и брисања из регистра. 
 * @author mirko
 * @version 1.0
 */
public class UserRegistrationController extends AbstractUserRegsistrationController{
	private UserDAO userDataLink; 
	
	public UserRegistrationController() {
		 userDataLink = new UserDAO();
	}
	
	public UserRegistrationController(UserDAO dao) {
		 userDataLink = dao;
	}
	
	public UserRegistrationController(UserRegistrationController ctrl) {
		 userDataLink = ctrl.userDataLink;
	}
	
	@Override
	public void register(UserDTO  user) {
		try {  
			if(user==null) throw new NullPointerException(); 
			if(userDataLink.existsUser(user.getUser().getUsername())) throw new UserDuplicationException(); 
			if(userDataLink.existsEmail(user.getUser().getEmail()))   throw new UserDuplicateEmailException(); 
			userDataLink.insert(user);
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	@Override
	public void unregister(String username) {
		try {
			userDataLink.delete(username);
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	@Override
	public UserDTO get(String username) {
		try {
			return userDataLink.get(username);
		}catch(Exception ex) {
			throw new RuntimeException(username);
		}
	}
	
	@Override
	public UserInfo getInfo(String username) {
		try {
			return userDataLink.getUser(username);
		}catch(Exception ex) {
			throw new RuntimeException(username);
		}
	}
	
	@Override
	public UserPassword getPassword(String username) {
		try {
			return userDataLink.getPassword(username);
		}catch(Exception ex) {
			throw new RuntimeException(username);
		}
	}

	@Override
	public UserDAO getUserDataLink() {
		return userDataLink;
	}
}
