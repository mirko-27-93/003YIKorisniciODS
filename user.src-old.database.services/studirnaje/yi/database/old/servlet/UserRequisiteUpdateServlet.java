package studirnaje.yi.database.old.servlet;

import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import studiranje.ip.controller.UserGeneralController;
import studiranje.ip.model.UserRequisit;

/**
 * Постављање реквизита.
 * @author mirko
 * @version 1.0
 */

@WebServlet("/UserRequisiteUpdateServlet")
public class UserRequisiteUpdateServlet extends HttpServlet{
	private static final long serialVersionUID = -3036765759152199953L;
	
	public static final String ATTR_SESSION_LOGIN = "status.logged";
	private transient UserGeneralController controller = UserGeneralController.getInstance(); 
	public static final boolean ERROR_REMIX = true; 
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		
		String username = (String)req.getSession().getAttribute(ATTR_SESSION_LOGIN); 
		if(username==null) {resp.sendError(404, "No logged user.");}
			
		try {
			Gson gson = new Gson();
			UserRequisit requ = null; 
			String json = new JsonParser().parse(new InputStreamReader(req.getInputStream(), "UTF-8")).toString(); 
			
			try{requ = gson.fromJson(json, UserRequisit.class);}
			catch(Exception ex) {if(ERROR_REMIX) ex.printStackTrace();}
			if(requ==null) requ = new UserRequisit();	
			controller.getRegistrator(req.getSession()).getUserDataLink().updateRequisite(username, requ);
			JsonObject root = new JsonObject();
			root.addProperty("success", true);
			root.addProperty("message", "");
			resp.getWriter().println(root.toString());
		}catch(Exception ex) {
			JsonObject root = new JsonObject();
			root.addProperty("success", false);
			root.addProperty("message", ex.getMessage());
			resp.getWriter().println(root.toString());
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
	
}
