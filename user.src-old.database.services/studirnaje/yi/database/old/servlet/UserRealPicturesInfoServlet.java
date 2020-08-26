package studirnaje.yi.database.old.servlet;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import studiranje.ip.controller.UserGeneralController;
import studiranje.ip.model.UserRequisit;

/**
 * Преузимање реалних имена за слике са сервера, а преко сервиса 
 * којом се управља удаљеним извором података, односно базе података
 * преко сервиса. 
 * @author mirko
 * @version 1.0
 */
@WebServlet("/UserRealPicturesInfoServlet")
public class UserRealPicturesInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String ATTR_SESSION_LOGIN = "status.logged";
	private transient UserGeneralController controller = UserGeneralController.getInstance(); 
	
    public UserRealPicturesInfoServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		
		String username = (String) request.getSession().getAttribute(ATTR_SESSION_LOGIN); 
		if(username==null) {response.sendError(404, "USER NOT LOGGED"); return;}
		
		try {
			UserRequisit requisit = controller.getRegistrator(request.getSession()).getUserDataLink().getRequisit(username); 
			JsonObject root = new JsonObject();
			File countryImage = requisit.getCountryFlagPicture(); 
			File profileImage = requisit.getProfilePicture(); 
			File userImage =  requisit.getUserPicture(); 
			if(countryImage!=null) root.addProperty("image.country", countryImage.getName());
			if(profileImage!=null) root.addProperty("image.profile", profileImage.getName());
			if(userImage!=null) root.addProperty("image.user", userImage.getName());
			response.getWriter().println(root.toString());
		}catch(Exception ex) {
			response.sendError(500, ex.getMessage()); return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
