package studiranje.ip.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import studiranje.ip.bean.GeneralUserBean;
import studiranje.ip.bean.InformationBean;
import studiranje.ip.controller.UserGeneralController;
import studiranje.ip.lang.UserMessagesSourcesConstants;
import studiranje.ip.lang.UserSessionConstantes;
import studiranje.ip.model.UserRequisit;
import studiranje.ip.object.GeneralOperationResponse;

/**
 * Постављање кратког описа за сервлет (у виду датотеке обичног текстуалног фајла)
 * (базом података - до 500 карактера)
 * @author mirko
 * @version 1.0
 */
@WebServlet("/UserDescriptionSetServlet")
public class UserDescriptionSetServlet extends HttpServlet{
	private static final long serialVersionUID = -71520751615872120L;
	
	private transient UserGeneralController controller = UserGeneralController.getInstance(); 
	public static final String ATTR_SESSION_LOGIN = "status.logged"; 
	
	/**
	 * Generate or get. Генерише и поставља или преузима и врћа зрно за информације у ВА (веб апликацији).
	 * @param req захтијев. 
	 * @param resp одговор. 
	 * @return зрно за информације. 
	 */
	private InformationBean gengetUserInformationBean(HttpServletRequest req, HttpServletResponse resp) {
		InformationBean infoBean = (InformationBean) req.getSession().getAttribute(UserSessionConstantes.USER_INFO_BEAN); 
		if(infoBean == null) {
			infoBean = new InformationBean(); 
			req.getSession().setAttribute(UserSessionConstantes.USER_INFO_BEAN, infoBean);
		}
		return infoBean; 
	}
	

	private GeneralUserBean gengetUserBasicDataUserBean(String username, HttpServletRequest req, HttpServletResponse resp) {
		GeneralUserBean basicDataBean = (GeneralUserBean) req.getSession().getAttribute(UserSessionConstantes.USER_GENERAL_BASIC_DATA_BEAN); 
		if(basicDataBean == null) {
			basicDataBean = new GeneralUserBean(username); 
			req.getSession().setAttribute(UserSessionConstantes.USER_INFO_BEAN, basicDataBean);
		}
		basicDataBean.setUsername(username);
		return basicDataBean; 
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		
		InformationBean infoBean = gengetUserInformationBean(req, resp);
		
		Gson gson = new Gson();
		GeneralOperationResponse response = new GeneralOperationResponse();
		
		String username = (String) req.getSession().getAttribute(ATTR_SESSION_LOGIN); 
		if(username==null) {
			response.setMessage("NO USER LOGGED. SET DESCRIPTION FAIL");
			response.setSuccess(false);
			resp.getWriter().println(gson.toJson(response)); 
			return; 
		}
		
		String telephone   = req.getParameter("telephone");
		String cityAddress = req.getParameter("city_address"); 
		String userDescription = req.getParameter("user_description"); 
		
		if(telephone==null) telephone = ""; 
		if(cityAddress==null) cityAddress = ""; 
		if(userDescription==null) userDescription=""; 
		
		try {
			controller.getRegistrator(req.getSession()).getUserDataLink().setDescription(username, userDescription);
			UserRequisit requisite = controller.getRegistrator(req.getSession()).getUserDataLink().getRequisit(username); 
			requisite.setTelephone(telephone);
			requisite.setCity(cityAddress);
			controller.getRegistrator(req.getSession()).getUserDataLink().updateRequisite(username, requisite);
			infoBean.reset();
			infoBean.setMessageSource(UserMessagesSourcesConstants.CLASSIC_SUCCESS_MSG_SRC);
			infoBean.setMessage("msg", "Постављање описа корисника успјешно.");
			infoBean.setAnnotation("OTHER_BASIC_USER_DATA");
			response.setMessage("SET DESCRIPTION SUCCESS.");
			response.setSuccess(true);
			resp.getWriter().println(gson.toJson(response)); 
		}catch(Exception ex) {
			infoBean.reset();
			infoBean.setException("msg",ex);
			infoBean.setMessageSource(UserMessagesSourcesConstants.CLASSIC_FAILURE_MSG_SRC);
			infoBean.setMessage("msg", "Постављање описа корисника неуспјешно.");
			infoBean.setAnnotation("OTHER_BASIC_USER_DATA");
			response.setMessage("SET DESCRIPTION ERROR.");
			response.setSuccess(false);
			resp.getWriter().println(gson.toJson(response)); 
		}
		GeneralUserBean basicDataBean = gengetUserBasicDataUserBean(username, req, resp); 
		basicDataBean.reload();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
