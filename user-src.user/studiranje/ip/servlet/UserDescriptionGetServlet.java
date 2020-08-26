package studiranje.ip.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import studiranje.ip.controller.UserGeneralController;

/**
 * Преузимање кратког описа за корисника. 
 * @author mirko
 * @version 1.0
 */
@WebServlet("/UserDescriptionGetServlet")
public class UserDescriptionGetServlet extends HttpServlet{
	private static final long serialVersionUID = 7732607951505366459L;
	public static final String ATTR_SESSION_LOGIN = "status.logged"; 
	
	private transient UserGeneralController controller = UserGeneralController.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/plain");
		String username = (String) req.getSession().getAttribute(ATTR_SESSION_LOGIN); 
		if(username==null) {
			resp.sendError(404, "USER NOT FOUND.");
			return; 
		}
		resp.setHeader("Content-Disposition", "attachment; filename=user_desc_"+username+".txt");
		try {
			String description = controller.getRegistrator(req.getSession()).getUserDataLink().getDescription(username);
			if(description==null) description = ""; 
			resp.getWriter().print(description);
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		} 
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
	
}
