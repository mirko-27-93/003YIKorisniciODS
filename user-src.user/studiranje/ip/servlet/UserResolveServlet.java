package studiranje.ip.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import studiranje.ip.bean.InformationBean;
import studiranje.ip.bean.UserBean;
import studiranje.ip.configuration.ForbbidenUsernameList;
import studiranje.ip.controller.UserGeneralController;
import studiranje.ip.data.DBUserDAO;
import studiranje.ip.data.event.UpdateUsernameRunnable;
import studiranje.ip.database.UserDAO;
import studiranje.ip.database.UserDTO;
import studiranje.ip.engine.model.DataSourceUserModel;
import studiranje.ip.lang.UserSessionConstantes;
import studiranje.ip.object.GeneralOperationResponse;

/**
 * Сервел разрешавања форми за кориснике. 
 * @author mirko
 * @version 1.0
 * WEB SERVICE 
 */

@WebServlet("/UserResolveServlet/*")
public class UserResolveServlet extends HttpServlet{
	private static final long serialVersionUID = -855859210041197355L;
	
	public static final String URI_SEPARATOR = "/"; 
	public static final String ATTR_SESSION_LOGIN = "status.logged"; 
	
	private transient UserGeneralController controller = UserGeneralController.getInstance(); 
	
	private void loadUserBeanRegistration(HttpServletRequest req, HttpServletResponse resp) {
		UserBean userBean = (UserBean) req.getSession().getAttribute(UserSessionConstantes.USER_BEAN);
		UserDTO dto = controller.getRegistrator(req.getSession()).get(userBean.getUsername());
		userBean.reset();
		userBean.setUsername(dto.getUser().getUsername());
		userBean.setFirstname(dto.getUser().getFirstname());
		userBean.setSecondname(dto.getUser().getSecondname());
		userBean.setEmail(dto.getUser().getEmail());
	}
	
	
	private void initUserBeanRegistration(HttpServletRequest req, HttpServletResponse resp) {
		UserBean userBean = (UserBean) req.getSession().getAttribute(UserSessionConstantes.USER_BEAN); 
		userBean.reset();
		userBean.setUsername(req.getParameter("username"));
		userBean.setFirstname(req.getParameter("firstname"));
		userBean.setSecondname(req.getParameter("secondname"));
		userBean.setPassword(req.getParameter("password"));
		userBean.setEmail(req.getParameter("useremail"));
	}
	
	private void initUserBeanLogin(HttpServletRequest req, HttpServletResponse resp) {
		UserBean userBean = (UserBean) req.getSession().getAttribute(UserSessionConstantes.USER_BEAN); 
		userBean.reset();
		userBean.setUsername(req.getParameter("username"));
		userBean.setPassword(req.getParameter("password"));
	}
	
	
	private void initUserBeanErase(HttpServletRequest req, HttpServletResponse resp) {
		UserBean userBean = (UserBean) req.getSession().getAttribute(UserSessionConstantes.USER_BEAN); 
		userBean.reset();
		userBean.setPassword(req.getParameter("old_password"));
		String username = req.getParameter("username"); 
		
		if(username!=null) userBean.setUsername(username);
		else userBean.setUsername(req.getSession().getAttribute(ATTR_SESSION_LOGIN).toString());
	}
	
	private String reinitUserBeanAndPasswprdDataForUpdate(HttpServletRequest req, HttpServletResponse resp) {
		UserBean userBean = (UserBean) req.getSession().getAttribute(UserSessionConstantes.USER_BEAN); 
		userBean.reset();
		userBean.setUsername(req.getParameter("username"));
		userBean.setFirstname(req.getParameter("firstname"));
		userBean.setSecondname(req.getParameter("secondname"));
		userBean.setPassword(req.getParameter("password"));
		userBean.setEmail(req.getParameter("useremail"));
		String oldPassword = req.getParameter("old_password"); 
		return oldPassword; 
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
	
	private void initMessages(HttpServletRequest req, HttpServletResponse resp) {
		InformationBean userInfoBean = (InformationBean) req.getSession().getAttribute(UserSessionConstantes.USER_INFO_BEAN); 
		if(userInfoBean!=null)
		userInfoBean.reset();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		String operation = req.getPathInfo();
		UserBean userBean = (UserBean) req.getSession().getAttribute(UserSessionConstantes.USER_BEAN); 
		initMessages(req, resp);
		
		Gson gson = new Gson();
		GeneralOperationResponse response = new GeneralOperationResponse();
		if(userBean==null) {
			String pathInfo = req.getPathInfo(); 
			if(pathInfo==null) pathInfo = ""; 
			response.setMessage("["+req.getContextPath()+pathInfo+"] Not form found.");
			response.setSuccess(false);
			resp.getWriter().println(gson.toJson(response)); 
		}else if(operation==null) {
			String pathInfo = req.getPathInfo(); 
			if(pathInfo==null) pathInfo = ""; 
			response.setMessage("["+req.getContextPath()+pathInfo+"] Not found.");
			response.setSuccess(false);
			resp.getWriter().println(gson.toJson(response)); 
		}else if(operation.contentEquals(URI_SEPARATOR+UserResolveOperation.LOGIN)) {
			initUserBeanLogin(req, resp);
			InformationBean userInfoBean = gengetUserInformationBean(req, resp);
			if(req.getSession().getAttribute(ATTR_SESSION_LOGIN)!=null) {
				response.setMessage("User alredy logged. Login fail.");
				response.setSuccess(false);
				resp.getWriter().println(gson.toJson(response)); 
				return;
			}
			try {
				controller.getSessions().login(userBean.getUsername(), req.getSession());
				controller.login(userBean, req.getSession());
				req.getSession().setAttribute(ATTR_SESSION_LOGIN, userBean.getUsername());
				loadUserBeanRegistration(req, resp);

				controller.getMessages().setInfoBean(userInfoBean).setLoginSuccessForWeb(req, resp);
				response.setMessage("Login success.");
				response.setSuccess(true);
				resp.getWriter().println(gson.toJson(response)); 				
			}catch(Exception ex) {

				controller.getMessages().setInfoBean(userInfoBean).setLoginGeneralFailureForWeb(req, resp);
				response.setMessage("Login error.");
				response.setSuccess(false);
				resp.getWriter().println(gson.toJson(response)); 
			}
		}else if(operation.contentEquals(URI_SEPARATOR+UserResolveOperation.REGISTER)) {
			initUserBeanRegistration(req, resp);
			InformationBean userInfoBean = gengetUserInformationBean(req, resp);
			if(req.getSession().getAttribute(ATTR_SESSION_LOGIN)!=null) {
				response.setMessage("User logged. Registration fail.");
				response.setSuccess(false);
				resp.getWriter().println(gson.toJson(response)); 
				return;
			}
			try { 
				ForbbidenUsernameList list = new ForbbidenUsernameList();
				if(list.getForbiddenUsernames().contains(userBean.getUsername())) 
					throw new RuntimeException("Изабрано је резервисано корисничко име.");
				controller.getRegistrator(req.getSession()).register(userBean.getAllInfo());
				req.getSession().setAttribute(ATTR_SESSION_LOGIN, userBean.getUsername());
				controller.getSessions().login(userBean.getUsername(), req.getSession());
				controller.getMessages().setInfoBean(userInfoBean).setRegistrationSuccessForWeb(req, resp);
				response.setMessage("Registration success."); 
				resp.getWriter().println(gson.toJson(response)); 
			}catch(Exception ex) {
				ex.printStackTrace();
				controller.getMessages().setInfoBean(userInfoBean).setRegistrationGeneralFailureForWeb(req, resp);
				response.setMessage("Registration error.");
				response.setSuccess(false);
				resp.getWriter().println(gson.toJson(response)); 
			}
		}else if(operation.contentEquals(URI_SEPARATOR+UserResolveOperation.LOGOUT)) {
			InformationBean userInfoBean = gengetUserInformationBean(req, resp);
			
			if(req.getSession().getAttribute(ATTR_SESSION_LOGIN)==null) {
				response.setMessage("User not logged. Logout fail.");
				response.setSuccess(false);
				resp.getWriter().println(gson.toJson(response)); 
				return;
			}
			controller.getSessions().logout(req.getSession().getAttribute(ATTR_SESSION_LOGIN).toString(), req.getSession());
			req.getSession().removeAttribute(ATTR_SESSION_LOGIN);
			
			userBean.reset();
			controller.getMessages().setInfoBean(userInfoBean).setLogoutSuccessForWeb(req, resp);
			controller.getMessages().setInfoBean(userInfoBean).setRegistrationSuccessForWeb(req, resp);
			
			response.setMessage("Logout success.");
			response.setSuccess(true);
			resp.getWriter().println(gson.toJson(response)); 
			
		}else if(operation.contentEquals(URI_SEPARATOR+UserResolveOperation.LOGOUT_ALL_SESSIONS_FOR_USER)) {
			InformationBean userInfoBean = gengetUserInformationBean(req, resp);
			if(req.getSession().getAttribute(ATTR_SESSION_LOGIN)==null) {
				response.setMessage("User not logged. General logout fail.");
				response.setSuccess(false);
				resp.getWriter().println(gson.toJson(response)); 
				return;
			}
			controller.getSessions().logout(req.getSession().getAttribute(ATTR_SESSION_LOGIN).toString());
			req.getSession().removeAttribute(ATTR_SESSION_LOGIN);
			userBean.reset();
			controller.getMessages().setInfoBean(userInfoBean).setLogoutSuccessForWeb(req, resp);
			
			response.setMessage("General logout success.");
			response.setSuccess(true);
			resp.getWriter().println(gson.toJson(response)); 
		}else if(operation.contentEquals(URI_SEPARATOR+UserResolveOperation.DELETE)){
			initUserBeanErase(req,resp);
			if(userBean.getUsername()==null || userBean.getUsername().trim().length()==0) {
				response.setMessage("User not logged. Deletion fail.");
				response.setSuccess(false);
				resp.getWriter().println(gson.toJson(response)); 
			}else {
				String username = userBean.getUsername();
				InformationBean userInfoBean = gengetUserInformationBean(req, resp);
				try {
					controller.delete(userBean.getUsername(), userBean.getPassword(), req.getSession());
					req.getSession().removeAttribute(ATTR_SESSION_LOGIN);
					userBean.reset();
					controller.getMessages().setInfoBean(userInfoBean).setDeleteSuccessForWeb(req, resp); 
					for(HttpSession session: controller.getSessions().getSessionsFor(username)) {
						controller.getSessions().logout(username, session);
					}
					response.setMessage("Deletion success.");
					response.setSuccess(true);
					resp.getWriter().println(gson.toJson(response));
				}catch(Exception ex) {
					userBean.setUsername((req.getSession().getAttribute(ATTR_SESSION_LOGIN).toString()));
					loadUserBeanRegistration(req, resp);
					controller.getMessages().setInfoBean(userInfoBean).setDeleteGeneralFailureForWeb(req, resp);
					userInfoBean.setException("msg", ex);
					response.setMessage("Deletion error.");
					response.setSuccess(false);
					resp.getWriter().println(gson.toJson(response));
				}finally {
					userBean.setPassword("");
				}
			}
		}else if(operation.contentEquals(URI_SEPARATOR+UserResolveOperation.UPDATE)) {
			String oldPassword = reinitUserBeanAndPasswprdDataForUpdate(req, resp);
			if(req.getSession().getAttribute(ATTR_SESSION_LOGIN)==null) {
				response.setMessage("User not logged. Update fail.");
				response.setSuccess(false);
				resp.getWriter().println(gson.toJson(response));
				return;
			}
			String oldUsername = req.getSession().getAttribute(ATTR_SESSION_LOGIN).toString();
			InformationBean userInfoBean = gengetUserInformationBean(req, resp);
			try {
				DataSourceUserModel dsum = controller.getRegistrator(req.getSession()).getUserDataLink();
				if(dsum instanceof UserDAO) 
				try {
					UpdateUsernameRunnable m = (UpdateUsernameRunnable)(((UserDAO) dsum).getUpdateUsername().getBefore("user.database")); 
					if(m!=null) {
						if(!oldUsername.contentEquals(userBean.getUsername())) {
							m.setOldUsername(oldUsername);
							m.setNeoUsername(userBean.getUsername());
							m.run();
						}
					}
				}catch(Exception ex) {
					userBean.setUsername(oldUsername);
					loadUserBeanRegistration(req, resp);
					controller.getMessages().setInfoBean(userInfoBean).setUpdateGeneralFailureForWeb(req, resp);
					userInfoBean.setException("msg", ex);
					response.setMessage("Update error.");
					response.setSuccess(false);
					resp.getWriter().println(gson.toJson(response));
					return; 
				}
				if(dsum instanceof DBUserDAO) 
					try {
						UpdateUsernameRunnable m = (UpdateUsernameRunnable)(((DBUserDAO) dsum).getUpdateUsername().getBefore("user.database")); 
						if(m!=null) {
							if(!oldUsername.contentEquals(userBean.getUsername())) {
								m.setOldUsername(oldUsername);
								m.setNeoUsername(userBean.getUsername());
								m.run();
							}
						}
					}catch(Exception ex) {
						userBean.setUsername(oldUsername);
						loadUserBeanRegistration(req, resp);
						controller.getMessages().setInfoBean(userInfoBean).setUpdateGeneralFailureForWeb(req, resp);
						userInfoBean.setException("msg", ex);
						response.setMessage("Update error.");
						response.setSuccess(false);
						resp.getWriter().println(gson.toJson(response));
						return; 
					}
				controller.change(oldUsername, oldPassword, req.getSession(), userBean);
				req.getSession().setAttribute(ATTR_SESSION_LOGIN, userBean.getUsername());
				String username = userBean.getUsername();
				userBean.reset();
				userBean.setUsername(username);
				loadUserBeanRegistration(req, resp);
				controller.getMessages().setInfoBean(userInfoBean).setUpdateSuccessForWeb(req, resp);
				loadUserBeanRegistration(req, resp);
				String oun = oldUsername;
				String nun = userBean.getUsername(); 
				for(HttpSession session : controller.getSessions().getSessionsFor(oun)) {
					if(!session.getId().contentEquals(req.getSession().getId())) controller.getSessions().logout(username, session);
				}
				for(HttpSession session : controller.getSessions().getSessionsFor(nun)) {
					if(!session.getId().contentEquals(req.getSession().getId())) controller.getSessions().logout(username, session);
				}
				response.setMessage("Update success");
				response.setSuccess(true);
				resp.getWriter().println(gson.toJson(response));
			}catch(Exception ex) {
				ex.printStackTrace(System.out);
				userBean.setUsername(oldUsername);
				loadUserBeanRegistration(req, resp);
				controller.getMessages().setInfoBean(userInfoBean).setUpdateGeneralFailureForWeb(req, resp);
				userInfoBean.setException("msg", ex);
				response.setMessage("Update error.");
				response.setSuccess(false);
				resp.getWriter().println(gson.toJson(response));
			}finally {
				userBean.setPassword("");
			}
		}else {
			String pathInfo = req.getPathInfo(); 
			if(pathInfo==null) pathInfo = ""; 
			response.setMessage("["+req.getContextPath()+pathInfo+"] Not found.");
			response.setSuccess(false);
			resp.getWriter().println(gson.toJson(response));
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
