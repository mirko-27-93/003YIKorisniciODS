package studiranje.ip.controller;

import java.io.InputStream;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import studiranje.ip.bean.UserBean;
import studiranje.ip.data.RootConnectionPool;
import studiranje.ip.database.UserDTO;
import studiranje.ip.exception.InvalidPasswordException;
import studiranje.ip.exception.UserDuplicateEmailException;
import studiranje.ip.exception.UserDuplicationException;
import studiranje.ip.model.UserPassword;
import studiranje.ip.model.UserRequisit;
import studiranje.ip.util.UserChangingSessionable;
import studiranje.ip.util.UserDeletionSessionable;

/**
 * Уопштени контролер за потребе апликације. 
 * @author mirko
 * @version 1.0
 */
public class UserGeneralController {
	private static UserGeneralController defaultController;
	
	public static UserGeneralController getDefault() {
		if(defaultController==null) defaultController = new UserGeneralController(); 
		return defaultController; 
	}
	
	public static UserGeneralController getInstance() {
		UserGeneralController defaultCtrl = getDefault(); 
		UserGeneralController instanceCtrl =  new UserGeneralController(false);
		instanceCtrl.registratorMap = defaultCtrl.registratorMap; 
		instanceCtrl.sessions = defaultCtrl.sessions; 
		instanceCtrl.deletion =  new UserDeletionSessionableController(defaultCtrl.deletion);
		instanceCtrl.changing =  new UserChangingSessionableController(defaultCtrl.changing);
		return instanceCtrl; 
	}
	
	private HashMap<HttpSession, AbstractUserRegsistrationController> registratorMap = new HashMap<>();
	private UserSessionController sessions; 
	private UserMessageController messages; 
	
	public void initModificationsReaction() {
		deletion.setDeletion(new UserDeletionSessionable(){
			private static final long serialVersionUID = 415933728243124740L;

			@Override
			public void delete(String username, HttpSession session) {
				deleteProfileImage(username, session);
				deleteUserImage(username, session);
			}

			@Override
			public UserDeletionSessionable clone() throws CloneNotSupportedException {
				return (UserDeletionSessionable)super.clone();
			}
		});
		changing.setChanging(new UserChangingSessionable() {
			private static final long serialVersionUID = -1528409059089057593L;

			@Override
			public void change(String oldUsername, String neoUsername, HttpSession session) {
				
			}

			@Override
			public UserChangingSessionable clone() throws CloneNotSupportedException {
				return (UserChangingSessionable) super.clone();
			}
			
		});
	}
	
	
	private UserGeneralController() {
		sessions = new UserSessionController();
		messages = new UserMessageController(); 
		deletion = new UserDeletionSessionableController();
		changing = new UserChangingSessionableController();
		this.initModificationsReaction();
	}
	
	private UserGeneralController(boolean clasic) {
		if(clasic) {
			sessions = new UserSessionController();
			messages = new UserMessageController();
			deletion = new UserDeletionSessionableController();
			changing = new UserChangingSessionableController();
		}else {
			messages = new UserMessageController(); 
		}
	}
	
	public AbstractUserRegsistrationController getRegistrator(HttpSession session) {
		return registratorMap.get(session);
	}
	
	public UserGeneralController setRegistrator(HttpSession session) {
		registratorMap.put(session, new UserRegistrationController((UserRegistrationController)BasicUserGeneralController.getDefault().getRegistrator()));
		return this;
	}

	public UserGeneralController setRegistrator(HttpSession session, String databaseAddress, String dbName) {
		UserRegistrationDBController registration = new UserRegistrationDBController(databaseAddress, dbName); 
		registration.getUserDataLink().setConnections(RootConnectionPool.getConnectionPool(databaseAddress));
		registratorMap.put(session, registration);
		return this;
	}
	
	public UserGeneralController removeRegistrartor(HttpSession session) {
		registratorMap.remove(session); 
		return this; 
	}
	
	public UserSessionController getSessions() {
		return sessions;
	}

	public UserMessageController getMessages() {
		return messages;
	}
	
	public void login(UserBean user, HttpSession session) throws InvalidPasswordException{
		UserPassword password = registratorMap.get(session).getPassword(user.getUsername()); 
		boolean authenticated = false; 
		try{ authenticated = password.checkPassword(user.getPassword());} 
		catch(Exception ex) {throw new RuntimeException(ex);}
		if(!authenticated) throw new InvalidPasswordException("Login failure.");
		sessions.login(user.getUsername(), session);
		user.setPassword("");
	}
	
	private UserDeletionSessionableController deletion;
	private UserChangingSessionableController changing;

	public UserDeletionSessionableController getDeletion() {
		return deletion;
	}

	public UserChangingSessionableController getChanging() {
		return changing;
	} 
	
	public void delete(String username, String passwd, HttpSession session) {
		UserPassword password = registratorMap.get(session).getPassword(username); 
		boolean authenticated = false; 
		try{ authenticated = password.checkPassword(passwd);} 
		catch(Exception ex) {throw new RuntimeException(ex);}
		if(!authenticated) throw new InvalidPasswordException("Delete failure.");
		deletion.getDeletion().delete(username, session);
		sessions.logout(username);
		registratorMap.get(session).unregister(username);
	}
	
	public void change(String username, String passwd, HttpSession session, UserBean data) {
		UserPassword password = registratorMap.get(session).getPassword(username); 
		boolean authenticated = false; 
		try{ authenticated = password.checkPassword(passwd);} 
		catch(Exception ex) {throw new RuntimeException(ex);}
		if(!authenticated) throw new InvalidPasswordException("Update failure.");
		try {
			String oldEmail = registratorMap.get(session).getUserDataLink().getEmail(username);
			if(registratorMap.get(session).get(data.getUsername())!=null && !data.getUsername().contentEquals(username)) throw new UserDuplicationException("Update failure. New user exists.");
			if(registratorMap.get(session).getUserDataLink().existsEmail(data.getEmail()) && !data.getEmail().contentEquals(oldEmail)) throw new UserDuplicateEmailException("Update failure. New email exists.");
		}catch(UserDuplicationException|UserDuplicateEmailException ex) {
			throw ex;
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
		changing.getChanging().change(username, data.getUsername(), session);
		sessions.logout(username);
		sessions.login(data.getUsername(), session);
		try {
			UserDTO dto = data.getAllInfo(); 
			dto.getPassword().setPlainPassword(data.getPassword());
			registratorMap.get(session).getUserDataLink().update(username, dto);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	public void deleteProfileImage(String username, HttpSession current) {
		try {
			UserRequisit requisit = registratorMap.get(current).getUserDataLink().getRequisit(username);
			if(requisit.getProfilePicture()==null) return;
			registratorMap.get(current).getUserDataLink().updateProfilePicture(username, null);
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	public void deleteUserImage(String username, HttpSession current) {
		try {
			UserRequisit requisit = registratorMap.get(current).getUserDataLink().getRequisit(username);
			if(requisit.getUserPicture()==null) return; 
			registratorMap.get(current).getUserDataLink().updateUserPicture(username, null);
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	public void deleteCountryFlagImage(String username, HttpSession current) {
		try {
			UserRequisit requisit = registratorMap.get(current).getUserDataLink().getRequisit(username);
			if(requisit.getCountryFlagPicture()==null) return; 
			registratorMap.get(current).getUserDataLink().updateCountryFlagPicture(username, null);
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	public void archiveProfileImage(String username, String filename, InputStream src, HttpSession current) {
		try {
			String fname = filename;
			
			UserRequisit requisit = registratorMap.get(current).getUserDataLink().getRequisit(username);
			if(requisit!=null && requisit.getProfilePicture()!=null) deleteProfileImage(username, current);
			
			registratorMap.get(current).getUserDataLink().updateProfilePicture(username, fname);
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	public void archiveUserImage(String username, String filename, InputStream src, HttpSession current) {
		try {
			String fname = filename;
			
			UserRequisit requisit = registratorMap.get(current).getUserDataLink().getRequisit(username);
			if(requisit!=null && requisit.getUserPicture()!=null) deleteUserImage(username, current);
			
			registratorMap.get(current).getUserDataLink().updateUserPicture(username, fname);
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	public void archiveCountryFlagImage(String username, String filename, InputStream src, HttpSession current) {
		try {
			String fname = filename;
			
			UserRequisit requisit = registratorMap.get(current).getUserDataLink().getRequisit(username);
			if(requisit!=null && requisit.getCountryFlagPicture()!=null) deleteUserImage(username, current);
			
			registratorMap.get(current).getUserDataLink().updateCountryFlagPicture(username, fname);
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
