package studiranje.ip.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import studiranje.ip.controller.UserGeneralController;

/**
 * Сервисни сервлет за листу сесијиа која се добија од корисника. 
 * Може бити потпуна што је потребно експлицитним флег параметром назначити 
 * или остраничена, а параметри страничења су прослеђени. 
 * @author mirko
 * @version 1.0
 */
@WebServlet("/SessionGetListServlet")
public class SessionGetListServlet extends HttpServlet{
	private static final long serialVersionUID = -261306549939426813L;
	
	public static final String ATTR_SESSION_LOGIN = "status.logged"; 
	private transient UserGeneralController controller = UserGeneralController.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		
		String username = (String) req.getSession().getAttribute(ATTR_SESSION_LOGIN);
		if(username==null) {
			resp.sendError(404, "USER NOT FOUND.");
			return; 
		}
		
		String ctrl = req.getParameter("ctrl");
		
		resp.setHeader("Content-Disposition", "inline; filename*=UTF-8''sessions_"+username+".json");
		
		if(ctrl!=null && ctrl.contentEquals("true")) {
			List<HttpSession> sessions = controller.getSessions().getSessionsFor(username); 
			JsonObject root = new JsonObject(); 
			JsonArray sesses = new JsonArray(); 
			root.addProperty("username", username);
			root.addProperty("session", req.getSession().getId());
			
			for(HttpSession sess: sessions) 
				sesses.add(sess.getId());
			
			root.add("sessions", sesses);
			resp.getWriter().print(root.toString());
		}else {
			List<HttpSession> sessions = controller.getSessions().getSessionsFor(username); 
			String pageNoStr = req.getParameter("page_no");
			String pageSizeStr = req.getParameter("page_size"); 
			try {
				int pageNo = Integer.parseInt(pageNoStr); 
				int pageSize = Integer.parseInt(pageSizeStr); 
				if(pageNo<1) pageNo = 0; 
				if(pageSize<1) pageSize = 1; 
				if(pageNo>0) {
					pageNo--; 
					int a = pageNo*pageSize; 
					int b = pageNo*pageSize+pageSize;
					a = Math.min(a, sessions.size()); 
					b = Math.min(b, sessions.size()); 
					
					
					
					JsonObject root = new JsonObject(); 
					JsonArray sesses = new JsonArray(); 
					root.addProperty("username", username);
					root.addProperty("session", req.getSession().getId());
					root.addProperty("page_no", pageNo+1);
					root.addProperty("page_size", pageSize);
					for(int i=a; i<b; i++) 
						sesses.add(sessions.get(i).getId());
					root.add("sessions", sesses);
					resp.getWriter().print(root.toString()); 
				}else {
					JsonObject root = new JsonObject(); 
					JsonArray sesses = new JsonArray(); 
					root.addProperty("username", username);
					root.addProperty("session", req.getSession().getId());
					root.addProperty("page_no", pageNo);
					root.addProperty("page_size", pageSize);
					root.add("sessions", sesses);
					resp.getWriter().print(root.toString()); 
				}
			}catch(Exception ex) {
				resp.sendError(404, "PARAMETAR INVALID OR NOT FOUND.");
				return; 
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
