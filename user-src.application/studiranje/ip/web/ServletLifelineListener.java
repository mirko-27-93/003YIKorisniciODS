package studiranje.ip.web;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import studiranje.ip.bean.GeneralUserBean;
import studiranje.ip.bean.UserBean;
import studiranje.ip.bean.UserConfigurationBean;
import studiranje.ip.controller.UserGeneralController;
import studiranje.ip.lang.UserSessionConstantes;

/**
 * Ослушкивач који одјављује корисника, уколико је остао пријављен,
 * а у случају ишчезавања сесије, зато што је напустио неодјављен нпр. . 
 * @author mirko
 * @version 1.0
 */
@WebListener
public class ServletLifelineListener implements HttpSessionListener {
	public static final boolean ERROR_REMIX = false; 
	public static final boolean TEST_REMIX = true; 
	
	public static final String ATTR_SESSION_LOGIN = "status.logged"; 
	private UserGeneralController controller = UserGeneralController.getDefault();
	
    public ServletLifelineListener() {}

        
    @Override
	public void sessionCreated(HttpSessionEvent se) {
    	if(TEST_REMIX) System.out.println("SESSION START : "+se.getSession().getId()); 
    	try {
	    	UserBean userBean = new UserBean(); 
	    	GeneralUserBean generalUserBean = new GeneralUserBean(); 
	    	UserConfigurationBean userConfigurationBean = new UserConfigurationBean();
	    	userBean.setSession(se.getSession());
	    	generalUserBean.setSession(se.getSession());
	    	userConfigurationBean.setSession(se.getSession());
			se.getSession().setAttribute(UserSessionConstantes.USER_BEAN, userBean);
	    	se.getSession().setAttribute(UserSessionConstantes.USER_GENERAL_BASIC_DATA_BEAN, generalUserBean);
	    	se.getSession().setAttribute(UserSessionConstantes.USER_CONFIGURATION_BASIC_DATA_BEAN, userConfigurationBean);
	    	controller.setRegistrator(se.getSession()); 
    	}catch(Exception ex) {
    		if(ERROR_REMIX) ex.printStackTrace();
    		throw ex; 
    	}
	}


	@Override
    public void sessionDestroyed(HttpSessionEvent se)  { 
	   if(TEST_REMIX) System.out.println("SESSION END : "+se.getSession().getId()); 
       try {
    	   String username = (String) se.getSession().getAttribute(ATTR_SESSION_LOGIN);
    	   if(username!=null) controller.getSessions().logout(username, se.getSession());
    	   controller.removeRegistrartor(se.getSession());
       }catch(Exception ex) {
    	   if(ERROR_REMIX) ex.printStackTrace();
   		   throw ex; 
       }
    }
}
