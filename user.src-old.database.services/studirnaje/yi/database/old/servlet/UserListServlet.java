package studirnaje.yi.database.old.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import studiranje.ip.bean.PageBean;
import studiranje.ip.controller.UserGeneralController;
import studiranje.ip.data.DBAbstractUserDAO;
import studiranje.ip.data.DBUserListDAO;
import studiranje.ip.database.UserDTO;
import studiranje.ip.database.bean.RootDatabaseInfoStateBean;
import studiranje.ip.lang.UserSessionConstantes;

/**
 * Сервлет за прослеђивање дељивих основних података о корисницима. 
 * @author mirko
 * @version 1.0
 */
@WebServlet("/UserListServlet")
public class UserListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private UserGeneralController ctrl = UserGeneralController.getInstance(); 
	
    public UserListServlet() {
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
		
		
		String pageNo = request.getParameter("page_no");
		String pageSize = request.getParameter("page_size"); 
		String startFilter = request.getParameter("start_filter"); 
		
		
		if(startFilter==null) startFilter = ""; 
		int pageNoVal = -1; 
		int pageSizeVal = 0; 
		 
		
		try {
			 pageNoVal = Integer.parseInt(pageNo); 
			 pageSizeVal = Integer.parseInt(pageSize); 
		}catch(Exception ex) {
			pageSize = null; 
			pageNo = null; 
			pageNoVal=-1;
			pageSizeVal=0; 
		}
		
	 
		
		DBAbstractUserDAO link = (DBAbstractUserDAO) ctrl.getRegistrator(request.getSession()).getUserDataLink();
		DBUserListDAO dao = new DBUserListDAO(link); 
		
		RootDatabaseInfoStateBean bean = this.gengetDatabaseStateBean(request, response); 		
		dao.setDatabase(bean.getDatabaseAdrressSplited(bean.getChoosedDatabase()).get("database"));
	
		
		List<UserDTO> users = new ArrayList<>();
		
		try {
			if(pageNoVal<0 || pageSizeVal<1) {
				users = dao.getUsers(); 
			}else {
				try {
					PageBean page = new PageBean();
					page.setPageNo(pageNoVal);
					page.setPageSize(pageSizeVal);
					page.setStartFilter(startFilter);
					users = dao.getUsers(page); 
				}catch(Exception ex) {
					users = dao.getUsers();
				}
			}
			
			JsonArray array = new JsonArray();
			for(UserDTO dto: users) {
				JsonObject object = new JsonObject(); 
				object.addProperty("username", dto.getUser().getUsername());
				object.addProperty("firstname", dto.getUser().getFirstname());
				object.addProperty("secondname", dto.getUser().getSecondname());
				object.addProperty("useremail", dto.getUser().getEmail());
				object.addProperty("telephone", dto.getRequisit().getTelephone());
				object.addProperty("country", dto.getRequisit().getCountry());
				object.addProperty("city", dto.getRequisit().getCity());
				array.add(object);
			}
			
			response.getWriter().println(array.toString()); 
		}catch(Exception ex) {
			response.sendError(500, ex.getMessage());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
