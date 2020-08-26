package studiranje.ip.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;

import studiranje.ip.bean.SessionBean;
import studiranje.ip.controller.UserGeneralController;
import studiranje.ip.error.SessionOwnerException;
import studiranje.ip.model.SessionInfo;

/**
 * Севисни сервлет са ЈСОН подацима о сесији пријављеног корисника, чији је идентификатор
 * прослеђен преко параметра. 
 * @author mirko
 * @version 1.0
 */
@WebServlet("/SessionGetInfoServlet")
public class SessionGetInfoServlet extends HttpServlet{
	private static final long serialVersionUID = 8197733075211442570L;
	
	public static final String ATTR_SESSION_LOGIN = "status.logged"; 
	private transient UserGeneralController controller = UserGeneralController.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		
		String username = (String) req.getSession().getAttribute(ATTR_SESSION_LOGIN); 
		if(username==null) {
			resp.sendError(404, "NOT LOGGED USER.");
			return; 
		}
		
		boolean self = false; 
		String sessionId = req.getParameter("session"); 
		if(sessionId == null) self = true;
		if(sessionId == null) sessionId = req.getSession().getId();
		if(sessionId.contentEquals(req.getSession().getId())) self=true;
		
		resp.setHeader("Content-Disposition", "inline;filename*=UTF-8''session_"+sessionId+"_"+username+".json");
		List<HttpSession> sessions = controller.getSessions().getSessionsFor(username);
		
		for(HttpSession session: sessions) {
			if(sessionId.equals(session.getId())) {
				SessionInfo info = controller.getSessions().getSession(session); 
				if(info==null) {
					SessionBean beanFactory = new SessionBean(); 
					beanFactory.loadFromId(session.getId()); 
					info = beanFactory.getSessionData();
				}
				JsonObject root = new JsonObject();
				root.addProperty("username", username);
				root.addProperty("session_id", info.getSessionId());
				root.addProperty("system_id", info.getSystemId());
				root.addProperty("platform_id", info.getPlatformId());
				root.addProperty("application_id", info.getApplicationId());
				root.addProperty("user_id", info.getUserId());
				root.addProperty("basic_id", info.getBasicId());
				root.addProperty("part_id", info.getPartId());
				root.addProperty("description", info.getDescription());
				root.addProperty("data", info.getOtherData());
				root.addProperty("app_type", info.getAppTypeLine());
				JsonObject parameters = new JsonObject();
				parameters.addProperty("self", self);
				root.add("params", parameters);
				resp.getWriter().print(root.toString());
				return; 
			}
		}
		
		throw new SessionOwnerException();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
