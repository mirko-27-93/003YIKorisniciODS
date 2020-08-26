package studiranje.ip.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import studiranje.ip.bean.InformationBean;
import studiranje.ip.bean.UserConfigurationBean;
import studiranje.ip.controller.UserGeneralController;
import studiranje.ip.database.UserFlagsDTO;
import studiranje.ip.lang.UserMessagesSourcesConstants;
import studiranje.ip.lang.UserSessionConstantes;
import studiranje.ip.object.GeneralOperationResponse;

/**
 * Сервлет/сервис којим се постављају основне поставке везане за корисника. 
 * @author mirko
 * @version 1.0
 */
@WebServlet("/UserFlagsSetServlet")
public class UserFlagsSetServlet extends HttpServlet{
	private static final long serialVersionUID = 456012138777018111L;
	private static final boolean ERROR_REMIX = true; 
	
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
	
	private UserConfigurationBean gengetConfigurationBean(String username, HttpServletRequest req, HttpServletResponse resp) throws SQLException {
		UserConfigurationBean bean = (UserConfigurationBean) req.getSession().getAttribute("userConfigurationBean");
		if(bean==null) req.getSession().setAttribute("userConfigurationBean", bean=new UserConfigurationBean());
		bean.setUserConfigurations(controller.getRegistrator(req.getSession()).getUserDataLink().getUserFlags(username).getConfigurations());
		return bean; 
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		InformationBean infoBean = gengetUserInformationBean(req, resp); 
		
		Gson gson = new Gson();
		GeneralOperationResponse response = new GeneralOperationResponse();
		
		try {
			String username = (String) req.getSession().getAttribute(ATTR_SESSION_LOGIN);
			if(username==null) {
				response.setMessage("NO LOGGED USER. SET FLAGS FAIL.");
				response.setSuccess(false);
				resp.getWriter().println(gson.toJson(response));
				return; 
			}
			UserConfigurationBean configBean = gengetConfigurationBean(username, req, resp); 
			
			String unws = (String) req.getParameter("unws"); 
			String unes = (String) req.getParameter("unes");
			String uscs  = (String) req.getParameter("uscs");
			
			String ctrl = (String) req.getParameter("control"); 
			
			if(ctrl == null) throw new NullPointerException("NO SET DATA PARAMETERS.");  
			
			boolean unwsData = false; 
			boolean unesData = false; 
			boolean uscsData = false; 
			
			if(unws!=null) unwsData=true; 
			if(unes!=null) unesData=true; 
			if(uscs!=null) uscsData=true;
			
			configBean.getUserConfigurations().setWebNotifications(unwsData);
			configBean.getUserConfigurations().setEmailNotifications(unesData);
			configBean.getUserConfigurations().setUserSessionsControl(uscsData);
			
			UserFlagsDTO dto = new UserFlagsDTO();
			dto.setConfigurations(configBean.getUserConfigurations());
			
			controller.getRegistrator(req.getSession()).getUserDataLink().putUserFlags(dto);
			
			infoBean.reset(); 
			infoBean.setMessageSource(UserMessagesSourcesConstants.CLASSIC_SUCCESS_MSG_SRC);
			infoBean.setMessage("msg", "Постављање поставки корисника је успјешно.");
			
			response.setMessage("SET FLAGS SUCCESS.");
			response.setSuccess(true);
			resp.getWriter().println(gson.toJson(response));
			
		}catch(Exception ex) {
			if(ERROR_REMIX) ex.printStackTrace();
			infoBean.reset(); 
			infoBean.setException("msg", ex);
			infoBean.setMessageSource(UserMessagesSourcesConstants.CLASSIC_FAILURE_MSG_SRC);
			infoBean.setMessage("msg", "Постављање поставки корисника није успјешно.");
			
			response.setMessage("SET FLAGS ERROR.");
			response.setSuccess(false);
			resp.getWriter().println(gson.toJson(response));
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
