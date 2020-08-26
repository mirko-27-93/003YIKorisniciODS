package studiranje.ip.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import studiranje.ip.bean.InformationBean;
import studiranje.ip.controller.UserGeneralController;
import studiranje.ip.lang.UserMessagesSourcesConstants;
import studiranje.ip.lang.UserSessionConstantes;

/**
 * Проточни сервлет који служи за постављање накнадних података о сесији за 
 * ове веб апликације, а то су опис и основно име којом се накнадно може 
 * идентификовати сесија из стране друге (исти корисник). Прецизност и јасноћа. 
 * Остали подаци се бришу, неки су фабрички. 
 * @author mirko
 * @version 1.0
 */

@WebServlet("/SessionSetInfoServlet")
public class SessionSetInfoServlet extends HttpServlet{
	private static final long serialVersionUID = -3636369763391869475L;
	
	public static final String ATTR_SESSION_LOGIN = "status.logged"; 
	private transient UserGeneralController controller = UserGeneralController.getInstance();
	
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
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		InformationBean infoBean = gengetUserInformationBean(req, resp); 
		String username = (String) req.getSession().getAttribute(ATTR_SESSION_LOGIN); 
		
		if(username==null) {
			resp.sendError(404, "NOT LOGGED USER");
			return;
		}
		
		try {
			controller.getSessions().apsorbeSessionInfo(req);
			infoBean.reset(); 
			infoBean.setMessageSource(UserMessagesSourcesConstants.CLASSIC_SUCCESS_MSG_SRC);
			infoBean.setMessage("msg", "Промјена и постављање информација о сесији је успјешно.");
			infoBean.setAnnotation("CURRENT_SESSION");
		}catch(Exception ex) {
			infoBean.reset(); 
			infoBean.setException("msg", ex);
			infoBean.setMessageSource(UserMessagesSourcesConstants.CLASSIC_FAILURE_MSG_SRC);
			infoBean.setMessage("msg", "Промјена и постављање информација о сесији није успјешно.");
			infoBean.setAnnotation("CURRENT_SESSION");
		}
		resp.sendRedirect(req.getContextPath()+"/WEB-PAGES/user/user_control.jsp#CURRENT_SESSION");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
