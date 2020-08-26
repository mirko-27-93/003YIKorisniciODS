package studiranje.ip.servlet;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import studiranje.ip.bean.InformationBean;
import studiranje.ip.controller.UserGeneralController;
import studiranje.ip.lang.UserMessagesSourcesConstants;
import studiranje.ip.lang.UserSessionConstantes;

/**
 * Сервлет који служи за операције над сесијама, од стране корисника.
 * Проточни/негенератпрски сервлет. 
 * @author mirko
 * @version 1.0
 */
@WebServlet("/SessionManervarServlet")
public class SessionManervarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	public static final String ATTR_SESSION_LOGIN = "status.logged"; 
	private transient UserGeneralController controller = UserGeneralController.getInstance();
	
    public SessionManervarServlet() {
        super();
    }
    
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

	private void errorMessage(InformationBean infoBean, HttpServletRequest request, HttpServletResponse response, Exception ex, String msg) throws IOException {
		infoBean.setMessageSource(UserMessagesSourcesConstants.CLASSIC_FAILURE_MSG_SRC);
		infoBean.setMessage("msg", msg);
		infoBean.setException("msg", ex);
		infoBean.setAnnotation("USER_SESSIONS");
		response.sendRedirect(request.getContextPath()+"/WEB-PAGES/user/user_control.jsp#ACTIVE_SESSIONS");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String username = (String) request.getSession().getAttribute(ATTR_SESSION_LOGIN); 
		if(username == null) {
			response.sendError(404, "NO LOGGED USER.");
			return; 
		}
		
		String operationMode = request.getParameter("operation_mode"); 
		InformationBean infoBean = gengetUserInformationBean(request, response);
		if(operationMode==null) {
			infoBean.setMessageSource(UserMessagesSourcesConstants.CLASSIC_FAILURE_MSG_SRC);
			infoBean.setMessage("msg", "Операција није спецификована.");
			infoBean.setException("msg", new NullPointerException("OPERATION NOT SPECIFIED"));
			infoBean.setAnnotation("USER_SESSIONS");
			response.sendRedirect(request.getContextPath()+"/WEB-PAGES/user/user_control.jsp#ACTIVE_SESSIONS");
			return; 
		}
		
		SessionManervarOption operation = null; 
		try {
			operation = SessionManervarOption.valueOf(operationMode); 
		}catch(Exception ex) {
			infoBean.setMessageSource(UserMessagesSourcesConstants.CLASSIC_FAILURE_MSG_SRC);
			infoBean.setMessage("msg", "Операција није позната.");
			infoBean.setException("msg", new RuntimeException("OPERATION UNKNOWN.", ex));
			infoBean.setAnnotation("USER_SESSIONS");
			response.sendRedirect(request.getContextPath()+"/WEB-PAGES/user/user_control.jsp#ACTIVE_SESSIONS");
			return; 
		}
		
		infoBean.reset();
		switch(operation) {
			case LOGOUT:
				controller.getSessions().logout(request.getSession().getAttribute(ATTR_SESSION_LOGIN).toString(), request.getSession());
				infoBean.setMessage("msg", "Одјава је успјешна.");
				break;
			case LOGOUT_SESSION: 
				String logoutSessionId = request.getParameter("logout_sid");
				if(logoutSessionId==null || logoutSessionId.trim().length()==0) {
					errorMessage(infoBean, request, response, new NullPointerException("NO SESSION."), "Сесија није наведена."); 
					return; 
				}
				HttpSession session = controller.getSessions().getSessionsFor(username,logoutSessionId);
				if(session==null) {
					errorMessage(infoBean, request, response, new NullPointerException("NO USER SPECIFIED SESSION."), "Сесија не постоји у власништву корисника."); 
					return; 
				}
				controller.getSessions().logout(request.getSession().getAttribute(ATTR_SESSION_LOGIN).toString(), session);
				infoBean.setMessage("msg", "Одјава сесије је успјешна.");
				break;
			case LOGOUT_ALL: 
				controller.getSessions().logout(request.getSession().getAttribute(ATTR_SESSION_LOGIN).toString());
				infoBean.setMessage("msg", "Општа одјава је успјешна.");
				break;
			case LOGOUT_SELECTED: 
				Enumeration<String> params = request.getParameterNames(); 
				String prefix = "chcek_choose_"; 
				while(params.hasMoreElements()) {
					String paramName = params.nextElement();
					if(!paramName.startsWith(prefix)) continue; 
					String sessionId = paramName.substring(prefix.length()); 
					HttpSession sessionObj = controller.getSessions().getSessionsFor(username,sessionId);
					if(sessionObj==null) continue; 
					controller.getSessions().logout(request.getSession().getAttribute(ATTR_SESSION_LOGIN).toString(), sessionObj);
				}
				infoBean.setMessage("msg", "Одјава изабраних сесија је успјешна."); 
				break;
		}
		infoBean.setMessageSource(UserMessagesSourcesConstants.CLASSIC_SUCCESS_MSG_SRC);
		infoBean.setAnnotation("USER_SESSIONS");
		response.sendRedirect(request.getContextPath()+"/WEB-PAGES/user/user_control.jsp#ACTIVE_SESSIONS");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
