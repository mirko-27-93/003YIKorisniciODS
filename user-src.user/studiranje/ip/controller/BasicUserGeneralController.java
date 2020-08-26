package studiranje.ip.controller;

import java.io.InputStream;

import javax.servlet.http.HttpSession;

import studiranje.ip.bean.UserBean;
import studiranje.ip.exception.InvalidPasswordException;
import studiranje.ip.exception.UserDuplicateEmailException;
import studiranje.ip.exception.UserDuplicationException;
import studiranje.ip.model.UserPassword;
import studiranje.ip.model.UserRequisit;
import studiranje.ip.util.UserChanging;
import studiranje.ip.util.UserDeletion;

/**
 * Уопштени контролер за потребе апликације. 
 * @author mirko
 * @version 1.0
 */
public class BasicUserGeneralController {
	private static BasicUserGeneralController defaultController; 
	
	public static BasicUserGeneralController getDefault() {
		if(defaultController==null) defaultController = new BasicUserGeneralController(); 
		return defaultController; 
	}
	
	public static BasicUserGeneralController getInstance() {
		BasicUserGeneralController defaultCtrl = getDefault(); 
		BasicUserGeneralController instanceCtrl =  new BasicUserGeneralController(false);
		instanceCtrl.sessions = defaultCtrl.sessions; 
		instanceCtrl.registrator = new UserRegistrationController((UserRegistrationController) defaultCtrl.registrator);
		instanceCtrl.deletion =  new UserDeletionController(defaultCtrl.deletion);
		instanceCtrl.changing =  new UserChangingController(defaultCtrl.changing);
		return instanceCtrl; 
	}
	
	private AbstractUserRegsistrationController registrator;
	private UserSessionController sessions; 
	private UserMessageController messages; 
	
	public void initModificationsReaction() {
		deletion.setDeletion(new UserDeletion(){
			private static final long serialVersionUID = 415933728243124740L;

			@Override
			public void delete(String username) {
				deleteProfileImage(username);
				deleteUserImage(username);
				deleteCountryFlagImage(username);
			}

			@Override
			public UserDeletion clone() throws CloneNotSupportedException {
				return (UserDeletion)super.clone();
			}
		});
		changing.setChanging(new UserChanging() {
			private static final long serialVersionUID = -1528409059089057593L;

			@Override
			public void change(String oldUsername, String neoUsername) {
				
			}

			@Override
			public UserChanging clone() throws CloneNotSupportedException {
				return (UserChanging) super.clone();
			}
			
		});
	}
	
	
	private BasicUserGeneralController() {
		registrator = new UserRegistrationController(); 
		sessions = new UserSessionController();
		messages = new UserMessageController(); 
		deletion = new UserDeletionController();
		changing = new UserChangingController();
		this.initModificationsReaction();
	}
	
	private BasicUserGeneralController(boolean clasic) {
		if(clasic) {
			registrator = new UserRegistrationController(); 
			sessions = new UserSessionController();
			messages = new UserMessageController();
			deletion = new UserDeletionController();
			changing = new UserChangingController();
		}else {
			messages = new UserMessageController(); 
		}
	}
	
	public AbstractUserRegsistrationController getRegistrator() {
		return registrator;
	}
	
	public BasicUserGeneralController setRegistrator() {
		BasicUserGeneralController defaultCtrl = getDefault(); 
		registrator = new UserRegistrationController((UserRegistrationController)defaultCtrl.registrator);
		return this;
	}

	public BasicUserGeneralController setRegistrator(String databaseAddress, String dbName) {
		registrator = new UserRegistrationDBController(databaseAddress, dbName);
		return this;
	}
	
	public UserSessionController getSessions() {
		return sessions;
	}

	public UserMessageController getMessages() {
		return messages;
	}
	
	public void login(UserBean user, HttpSession session) throws InvalidPasswordException{
		UserPassword password = registrator.getPassword(user.getUsername()); 
		boolean authenticated = false; 
		try{ authenticated = password.checkPassword(user.getPassword());} 
		catch(Exception ex) {throw new RuntimeException(ex);}
		if(!authenticated) throw new InvalidPasswordException("Login failure.");
		sessions.login(user.getUsername(), session);
		user.setPassword("");
	}
	
	private UserDeletionController deletion;
	private UserChangingController changing;

	public UserDeletionController getDeletion() {
		return deletion;
	}

	public UserChangingController getChanging() {
		return changing;
	} 
	
	public void delete(String username, String passwd, HttpSession session) {
		UserPassword password = registrator.getPassword(username); 
		boolean authenticated = false; 
		try{ authenticated = password.checkPassword(passwd);} 
		catch(Exception ex) {throw new RuntimeException(ex);}
		if(!authenticated) throw new InvalidPasswordException("Delete failure.");
		deletion.getDeletion().delete(username);
		sessions.logout(username);
		registrator.unregister(username);
	}
	
	public void change(String username, String passwd, HttpSession session, UserBean data) {
		UserPassword password = registrator.getPassword(username); 
		boolean authenticated = false; 
		try{ authenticated = password.checkPassword(passwd);} 
		catch(Exception ex) {throw new RuntimeException(ex);}
		if(!authenticated) throw new InvalidPasswordException("Update failure.");
		try {
			String oldEmail = registrator.getUserDataLink().getEmail(username);
			if(registrator.get(data.getUsername())!=null && !data.getUsername().contentEquals(username)) throw new UserDuplicationException("Update failure. New user exists.");
			if(registrator.getUserDataLink().existsEmail(data.getEmail()) && !data.getEmail().contentEquals(oldEmail)) throw new UserDuplicateEmailException("Update failure. New email exists.");
		}catch(UserDuplicationException|UserDuplicateEmailException ex) {
			throw ex;
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
		changing.getChanging().change(username, data.getUsername());
		sessions.logout(username);
		try {
			registrator.getUserDataLink().update(username, data.getAllInfo());
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		sessions.login(data.getUsername(), session);
	}
	
	
	public void deleteProfileImage(String username) {
		try {
			UserRequisit requisit = registrator.getUserDataLink().getRequisit(username);
			if(requisit.getProfilePicture()==null) return;
			registrator.getUserDataLink().updateProfilePicture(username, null);
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	public void deleteUserImage(String username) {
		try {
			UserRequisit requisit = registrator.getUserDataLink().getRequisit(username);
			if(requisit.getUserPicture()==null) return; 
			registrator.getUserDataLink().updateUserPicture(username, null);
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	public void deleteCountryFlagImage(String username) {
		try {
			UserRequisit requisit = registrator.getUserDataLink().getRequisit(username);
			if(requisit.getCountryFlagPicture()==null) return; 
			registrator.getUserDataLink().updateCountryFlagPicture(username, null);
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	public void archiveProfileImage(String username, String filename, InputStream src) {
		try {
			String fname = filename;
			
			UserRequisit requisit = registrator.getUserDataLink().getRequisit(username);
			if(requisit!=null && requisit.getProfilePicture()!=null) deleteProfileImage(username);
			
			registrator.getUserDataLink().updateProfilePicture(username, fname);
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	public void archiveUserImage(String username, String filename, InputStream src) {
		try {
			String fname = filename;
			
			UserRequisit requisit = registrator.getUserDataLink().getRequisit(username);
			if(requisit!=null && requisit.getUserPicture()!=null) deleteUserImage(username);
			
			registrator.getUserDataLink().updateUserPicture(username, fname);
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	public void archiveCountryFlagImage(String username, String filename, InputStream src) {
		try {
			String fname = filename;
			
			UserRequisit requisit = registrator.getUserDataLink().getRequisit(username);
			if(requisit!=null && requisit.getCountryFlagPicture()!=null) deleteUserImage(username);
			
			registrator.getUserDataLink().updateCountryFlagPicture(username, fname);
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
