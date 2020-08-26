package studirnaje.yi.database.old.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import studiranje.ip.controller.UserGeneralController;

/**
 * Сервлет за брисање податка о сллици за корисника из базе података. 
 */
@WebServlet("/DeleteUserImageServlet")
public class DeleteUserImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public static final String ATTR_SESSION_LOGIN = "status.logged";
	private transient UserGeneralController controller = UserGeneralController.getInstance(); 
	
    public DeleteUserImageServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		
		String username = (String) request.getSession().getAttribute(ATTR_SESSION_LOGIN); 
		if(username==null) {response.sendError(404, "USER NOT LOGGED"); return;}
		
		try {
			controller.getRegistrator(request.getSession()).getUserDataLink().updateUserPicture(username, null); 
			JsonObject object = new JsonObject(); 
			object.addProperty("success", true);
			object.addProperty("message", "");
			response.getWriter().println(object.toString());
		}catch(Exception ex) {
			JsonObject object = new JsonObject(); 
			object.addProperty("success", false);
			object.addProperty("message", ex.getMessage());
			response.getWriter().println(object.toString());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
