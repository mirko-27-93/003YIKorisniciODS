package studiranje.ip.bean;

import java.io.Serializable;

import javax.servlet.http.HttpSession;

import studiranje.ip.controller.UserGeneralController;
import studiranje.ip.model.SessionInfo;

/**
 * Зрно којим се означавају пропозиције тренутне корисниничке сесије. 
 * @author mirko
 * @version 1.0
 */
public class SessionBean implements Serializable{
	private static final long serialVersionUID = 8912340800767126016L;
	private transient UserGeneralController controller = UserGeneralController.getInstance();
	
	private SessionInfo sessionData;
	
	public SessionInfo getSessionData() {
		if(sessionData==null) return new SessionInfo("");
		return sessionData;
	}

	public void setSessionData(SessionInfo sessionData) {
		this.sessionData = sessionData;
	} 
	
	public boolean exists() {
		return sessionData!=null; 
	}
	
	public String loadFromId(String id) {
		HttpSession session = controller.getSessions().getSession(id);
		if(session==null) return "";
		sessionData = controller.getSessions().getSession(session); 
		if(sessionData==null) {
			controller.getSessions().setSessionInfo(controller.getSessions().getSession(id));
			sessionData = controller.getSessions().getSession(session);
			return "";
		}
		return "";
	}
}
