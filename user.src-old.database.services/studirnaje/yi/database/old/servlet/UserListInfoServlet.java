package studirnaje.yi.database.old.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import studiranje.ip.bean.PageBean;
import studiranje.ip.controller.UserGeneralController;
import studiranje.ip.data.DBUserListDAO;
import studiranje.ip.database.bean.RootDatabaseInfoStateBean;
import studiranje.ip.lang.UserSessionConstantes;

@WebServlet("/UserListInfoServlet")
public class UserListInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private UserGeneralController ctrl = UserGeneralController.getInstance(); 
	
    public UserListInfoServlet() {
        super();
    }

    private RootDatabaseInfoStateBean gengetDatabaseStateBean(HttpServletRequest req, HttpServletResponse resp) {
		RootDatabaseInfoStateBean infoBean = (RootDatabaseInfoStateBean) req.getSession().getAttribute(UserSessionConstantes.DATABASE_STATE_BEAN); 
		if(infoBean == null) {
			infoBean = new RootDatabaseInfoStateBean(); 
			infoBean.apply();
			req.getSession().setAttribute(UserSessionConstantes.DATABASE_STATE_BEAN, infoBean);
		}
		return infoBean; 
	}
	
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		
		DBUserListDAO dao = new DBUserListDAO(ctrl.getRegistrator(request.getSession()).getUserDataLink()); 
		RootDatabaseInfoStateBean bean = this.gengetDatabaseStateBean(request, response); 		
		dao.setDatabase(bean.getDatabaseAdrressSplited(bean.getChoosedDatabase()).get("database"));
		
		boolean filter = true;  
		String startFilter = request.getParameter("start_filter");
		if(startFilter==null) filter = false; 
		if(startFilter==null) startFilter = ""; 
		
		PageBean pbean = new PageBean(); 
		pbean.setStartFilter(startFilter);
		
		try {
			JsonObject info = new JsonObject(); 
			if(!filter)  info.addProperty("count", dao.countUsers());
			else  		info.addProperty("count", dao.countUsers(pbean));
			response.getWriter().println(info.toString());
		}catch(Exception ex) {
			response.sendError(500, ex.getMessage());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
