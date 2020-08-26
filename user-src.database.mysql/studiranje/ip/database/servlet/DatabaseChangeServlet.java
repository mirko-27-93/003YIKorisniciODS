package studiranje.ip.database.servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import studiranje.ip.bean.DatabaseInfoBean;
import studiranje.ip.bean.InformationBean;
import studiranje.ip.controller.UserGeneralController;
import studiranje.ip.database.bean.RootDatabaseInfoBean;
import studiranje.ip.database.bean.RootDatabaseInfoStateBean;
import studiranje.ip.database.controller.DatabaseSwitchController;
import studiranje.ip.lang.UserMessagesSourcesConstants;
import studiranje.ip.lang.UserSessionConstantes;
import studiranje.ip.object.GeneralOperationResponse;

/**
 * Сервлет за потребе измјене, односно постављања нове базе података. 
 * @author mirko
 * @version 1.0
 */
@WebServlet("/DatabaseChangeServlet")
public class DatabaseChangeServlet extends HttpServlet{
	public static final String ATTR_SESSION_LOGIN = "status.logged"; 
	private static final long serialVersionUID = 8011999633809546895L;
	public static final boolean ERROR_REMIX = false; 
	
	private transient UserGeneralController controller = UserGeneralController.getInstance(); 
	private transient DatabaseSwitchController dbSwitchController = new DatabaseSwitchController(); 
	
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
	
	private RootDatabaseInfoBean gengetDatabaseInfoBean(HttpServletRequest req, HttpServletResponse resp) {
		RootDatabaseInfoBean infoBean = (RootDatabaseInfoBean) req.getSession().getAttribute(UserSessionConstantes.DATABASE_INFO_BEAN); 
		if(infoBean == null) {
			infoBean = new RootDatabaseInfoBean(); 
			req.getSession().setAttribute(UserSessionConstantes.DATABASE_INFO_BEAN, infoBean);
		}
		return infoBean; 
	}
	
	private RootDatabaseInfoStateBean gengetDatabaseStateBean(HttpServletRequest req, HttpServletResponse resp) {
		RootDatabaseInfoStateBean infoBean = (RootDatabaseInfoStateBean) req.getSession().getAttribute(UserSessionConstantes.DATABASE_STATE_BEAN); 
		if(infoBean == null) {
			infoBean = new RootDatabaseInfoStateBean(); 
			req.getSession().setAttribute(UserSessionConstantes.DATABASE_STATE_BEAN, infoBean);
		}
		return infoBean; 
	}
	
	private DatabaseInfoBean gengetDatabaseBean(HttpServletRequest req, HttpServletResponse resp) {
		DatabaseInfoBean infoBean = (DatabaseInfoBean) req.getSession().getAttribute(UserSessionConstantes.DATABASE_JSTL_JSP_BEAN); 
		if(infoBean == null) {
			infoBean = new DatabaseInfoBean(); 
			req.getSession().setAttribute(UserSessionConstantes.DATABASE_JSTL_JSP_BEAN, infoBean);
		}
		return infoBean; 
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		
		GeneralOperationResponse response = new GeneralOperationResponse(); 
		String username = (String) req.getSession().getAttribute(ATTR_SESSION_LOGIN);
		InformationBean info = gengetUserInformationBean(req, resp);
		
		RootDatabaseInfoBean dbStateBean =  gengetDatabaseInfoBean(req, resp);
		RootDatabaseInfoStateBean dbInfoBean = gengetDatabaseStateBean(req, resp);
		
		DatabaseInfoBean dbBean =  gengetDatabaseBean(req, resp);
		Gson gson = new Gson(); 
		
		if(!(username!=null && username.trim().length()!=0)) {
			try {
				controller.initModificationsReaction();
				if(dbStateBean == null) throw new NullPointerException("DB STATE BEAN");
				if(dbInfoBean == null) throw new NullPointerException("DB INFO ");
				String name = req.getParameter("db_name");
				
				
				HashMap<String, String> dbMap = new HashMap<>(); 
				for(String dbName : dbSwitchController.getDatabaseNames()) 
					dbMap.put(dbName, dbSwitchController.getDatabaseAddress(dbName)); 
				
				dbInfoBean.setDatabaseMap(dbMap);
				name = dbInfoBean.nameByValue(name); 
				dbInfoBean.setChoosedDatabase(name);
				
				try {
					String dbAddress = dbInfoBean.getDatabaseAdrressSplited(name).get("address"); 
					String dbName = dbInfoBean.getDatabaseAdrressSplited(name).get("database"); 
					String dbUser = dbInfoBean.getDatabaseAdrressSplited(name).get("user"); 
					String dbPassword = dbInfoBean.getDatabaseAdrressSplited(name).get("password"); 
					String jdbcAddress = dbInfoBean.getJDBCMysqlAddress(name);
					controller.setRegistrator(req.getSession(), dbAddress, dbName);
					dbBean.setJdbcURL(jdbcAddress);
					dbBean.setUsername(dbUser);
					dbBean.setPassword(dbPassword);
					dbBean.setDatabase(dbName);
					
					dbInfoBean.apply(); 
					dbStateBean.apply(dbInfoBean);
				}catch(Exception ex) {
					if(ERROR_REMIX) ex.printStackTrace();
					throw ex; 
				}
				
				info.reset();
				info.setMessageSource(UserMessagesSourcesConstants.CLASSIC_SUCCESS_MSG_SRC); 
				info.setMessage("msg", "Промјена базе података је успјешна.");
				info.setAnnotation("");
				
				response.setMessage("Database switch successfull.");
				response.setSuccess(true);
				resp.getWriter().println(gson.toJson(response)); 
			}catch(Exception ex) {
	
				info.reset();
				info.setMessageSource(UserMessagesSourcesConstants.CLASSIC_FAILURE_MSG_SRC); 
				info.setMessage("msg", "Измјена базе података није успјешна.");
				info.setException("msg", ex);
				info.setAnnotation("");
				
				response.setMessage("Database switch error.");
				response.setSuccess(false);
				resp.getWriter().println(gson.toJson(response)); 
			}
			return; 
		}else {
			info.reset();
			info.setMessageSource(UserMessagesSourcesConstants.CLASSIC_FAILURE_MSG_SRC); 
			info.setMessage("msg", "Измјена базе података није успјешна. Потребно је радити када корисник није пријављен.");
			info.setAnnotation("");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
