package studiranje.ip.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import studiranje.ip.bean.UserConfigurationBean;
import studiranje.ip.controller.UserGeneralController;
import studiranje.ip.database.UserFlagsDTO;

/**
 * Сервлет/сервис за добијање корисничких поставки, флегова, односно конфигурација. 
 * @author mirko
 * @version 1.0
 */

@WebServlet("/UserFlagsGetServlet")
public class UserFlagsGetServlet extends HttpServlet{
	private static final long serialVersionUID = -7672387377072554284L;

	public static final String ATTR_SESSION_LOGIN = "status.logged"; 
	private transient UserGeneralController controller = UserGeneralController.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/josn");
		
		String username = (String) req.getSession().getAttribute(ATTR_SESSION_LOGIN); 
		if(username==null) {
			resp.sendError(404, "USER NOT FOUND. NOT LOGGED IN.");
			return; 
		}		
		resp.setHeader("Content-Disposition", "inline;filename*=UTF-8''user_cofigs_"+URLEncoder.encode(username, "UTF-8")+".json");
		
		try {
			UserFlagsDTO flags = controller.getRegistrator(req.getSession()).getUserDataLink().getUserFlags(username); 
			JsonObject root = new JsonObject(); 
			root.addProperty("username", username);
			root.addProperty(UserConfigurationBean.USER_WEBAPP_NOTIFFICATION_SUPPORT, flags.getConfigurations().isWebNotifications());
			root.addProperty(UserConfigurationBean.USER_EMAIL_NOTIFFICATION_SUPPORT,  flags.getConfigurations().isEmailNotifications());
			root.addProperty(UserConfigurationBean.USER_SESSION_CONTROL_SUPPORT,      flags.getConfigurations().isUserSessionsControl());
			resp.getWriter().print(root.toString());
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
