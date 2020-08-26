package studiranje.ip.bean;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import studiranje.ip.controller.UserGeneralController;
import studiranje.ip.model.UserFlags;

/**
 * Зрно на фронтенду, за флегове о корисницима, односно поставкама корисника у 
 * бекенду и даље у бази података, за кључне поставке (које се чувају тамо, 
 * било у основној табели података и поставки/инфомација о кориснику, било у 
 * специјалозованим базама података и табелама, повезаним или не односно у 
 * датотекама или другим структурама и/или базама података). 
 * @author mirko
 * @version 1.0
 */
public class UserConfigurationBean implements Serializable{
	private static final long serialVersionUID = -1728291661165776914L;
	private transient UserGeneralController controller = UserGeneralController.getInstance();
	private String lastUsername = ""; 
	private HttpSession session; 
	
	public static final String USER_WEBAPP_NOTIFFICATION_SUPPORT = "user.notification.webapp.support"; 
	public static final String USER_EMAIL_NOTIFFICATION_SUPPORT = "user.notification.email.support"; 
	public static final String USER_SESSION_CONTROL_SUPPORT = "user.session.control.support"; 
	
	private UserFlags userConfigurations;

	public UserFlags getUserConfigurations() {
		if(userConfigurations==null) return UserFlags.DEFAULT_BLANK_USER_FLAGS;
		return userConfigurations;
	}

	public void setUserConfigurations(UserFlags userConfigurations) {
		this.userConfigurations = userConfigurations;
	} 
	
	public Properties getAsProperties(){
		Properties properties = new Properties();
		properties.setProperty(USER_WEBAPP_NOTIFFICATION_SUPPORT, Boolean.toString(getUserConfigurations().isWebNotifications())); 
		properties.setProperty(USER_EMAIL_NOTIFFICATION_SUPPORT, Boolean.toString(getUserConfigurations().isEmailNotifications())); 
		properties.setProperty(USER_SESSION_CONTROL_SUPPORT, Boolean.toString(getUserConfigurations().isUserSessionsControl())); 
		return properties; 
	}
	
	public void loadFromProperties(Properties properties) {
		String uwns = properties.getProperty(USER_WEBAPP_NOTIFFICATION_SUPPORT); 
		String uens = properties.getProperty(USER_EMAIL_NOTIFFICATION_SUPPORT); 
		String uscs = properties.getProperty(USER_SESSION_CONTROL_SUPPORT);
		
		if(uwns!=null) {
			try {userConfigurations.setWebNotifications(Boolean.parseBoolean(uwns));} catch(Exception ex) {}
		}
		if(uens!=null) {
			try {userConfigurations.setEmailNotifications(Boolean.parseBoolean(uens));} catch(Exception ex) {}
		}
		if(uscs!=null) {
			try {userConfigurations.setUserSessionsControl(Boolean.parseBoolean(uscs));} catch(Exception ex) {}
		}
	}
	
	public String reload(String username) throws SQLException {
		if(lastUsername.contentEquals(username)) return ""; 
		this.setUserConfigurations(controller.getRegistrator(session).getUserDataLink().getUserFlags(username).getConfigurations()); 
		lastUsername=username; 
		return ""; 
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}
}
