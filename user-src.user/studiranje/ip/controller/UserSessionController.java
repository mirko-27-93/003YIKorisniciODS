package studiranje.ip.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import studiranje.ip.lang.UserSessionConstantes;
import studiranje.ip.model.SessionInfo;

/**
 * Контроле и меморијска база података, када је у питању
 * вођење података и информација пријављених корисника. 
 * Корисно за управљање вишеструким сесијама. 
 * @author mirko
 * @version 1.0
 */
public class UserSessionController {
	public static final String ATTR_SESSION_LOGIN = "status.logged"; 
	private HashMap<String, List<HttpSession>> sessions = new HashMap<>(); 
	private HashMap<HttpSession, SessionInfo> sessionInformations = new HashMap<>(); 
	private List<HttpSession> active = new ArrayList<>(); 
	
	public synchronized void login(String username, HttpSession session) {
		if(sessions.get(username)==null) sessions.put(username, new ArrayList<>());
		if(!sessions.get(username).contains(session))sessions.get(username).add(session); 
		session.setAttribute(UserSessionConstantes.USER_LOGON, username);
		if(!active.contains(session)) active.add(session);
	}
	public synchronized void logout(String username, HttpSession session) {
		session.removeAttribute(UserSessionConstantes.USER_LOGON);
		if(sessions.containsKey(username)) {
			for(HttpSession sess: new ArrayList<>(sessions.get(username))) 
				if(sess.getId().equals(session.getId())) {
					sessions.get(username).remove(session); 
					sess.removeAttribute(ATTR_SESSION_LOGIN);
					sess.removeAttribute(UserSessionConstantes.PREVIEW_TEMP_BEAN);
					sessionInformations.remove(sess); 
					active.remove(sess); 
				}
			if(sessions.get(username).size()==0) sessions.remove(username); 
		}
	}
	public synchronized void logout(String username) {
		if(sessions.containsKey(username)) {
			for(HttpSession sess: new ArrayList<>(sessions.get(username))) {
				sess.removeAttribute(UserSessionConstantes.USER_LOGON);
				sess.removeAttribute(ATTR_SESSION_LOGIN);
				sess.removeAttribute(UserSessionConstantes.PREVIEW_TEMP_BEAN);
				sessionInformations.remove(sess); 
				active.remove(sess); 
			}
			sessions.remove(username);
		}
	}
	public synchronized void logoutAll() {
		for(String username: sessions.keySet())
			logout(username);
	}
	
	public synchronized List<String> getLoggedUsers(){
		return new ArrayList<>(sessions.keySet());
	}
	
	public synchronized List<HttpSession> getSessionsFor(String username) {
		List<HttpSession> seses = sessions.get(username);
		if(seses==null) return new ArrayList<>(); 
		return new ArrayList<>(seses); 
	}
	
	public synchronized HttpSession getSessionsFor(String username, String id) {
		List<HttpSession> sesses = sessions.get(username);
		if(sesses==null) return null;
		for(HttpSession sess: sesses) {
			if(id.contentEquals(sess.getId())) return sess; 
		}
		return null; 
	}
	
	public synchronized Map<HttpSession, SessionInfo> getSessionsInfoFor(String username){
		TreeMap<HttpSession, SessionInfo> map = new TreeMap<>(); 
		List<HttpSession> seses = sessions.get(username);
		if(seses==null) return new TreeMap<>(); 
		for(HttpSession session: seses) 
			map.put(session, sessionInformations.get(session)); 
		return map; 
	}
	
	public synchronized void apsorbeSessionInfo(HttpServletRequest request) {
		HttpSession session = request.getSession(); 
		if(session==null) throw new NullPointerException();
		if(!active.contains(session)) throw new RuntimeException("INVALID SESSION.");
		SessionInfo info = new SessionInfo(session.getId());
		String systemId = request.getParameter("session.system.id"); 
		String platformId = request.getParameter("session.platform.id");
		String applicationId =  request.getParameter("session.application.id"); 
		String machineId =  request.getParameter("session.machine.id");
		String basicId = request.getParameter("session.basic.id"); 
		String userId = request.getParameter("session.user.id"); 
		String partId = request.getParameter("session.part.id"); 
		String description = request.getParameter("session.description");
		String otherData = request.getParameter("session.data"); 
		if(otherData!=null) otherData = otherData.trim();  
		info.setSystemId(systemId);
		info.setApplicationId(applicationId);
		info.setBasicId(basicId);
		info.setDescription(description);
		info.setMachineId(machineId);
		info.setUserId(userId);
		info.setOtherData(otherData);
		info.setPartId(partId);
		info.setPlatformId(platformId);
		sessionInformations.put(session, info); 
	}
	
	public synchronized HttpSession getSession(String sessionId) {
		if(sessionId==null) return null;
		for(HttpSession session: active) {
			if(sessionId.contentEquals(session.getId())) return session; 
		}
		return null;
	}
	
	public synchronized SessionInfo getSession(HttpSession session) {
		return sessionInformations.get(session); 
	}
	
	public synchronized void setSessionInfo(HttpSession session) {
		if(getSession(session.getId())==null) return;
		SessionInfo info = new SessionInfo(session.getId());
		info.setOtherData("text/plain\nWEB_APPLICATION");
		this.sessionInformations.put(session, info);
	}
}
