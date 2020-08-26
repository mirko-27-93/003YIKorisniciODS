package studiranje.ip.bean;

import java.util.List;

import javax.servlet.http.HttpSession;

import studiranje.ip.controller.UserGeneralController;

/**
 * Страничење када су у питању табеле са активном корисничким сесијама. 
 * @author mirko
 * @version 1.0
 */
public class SessionPageBean extends PageBean{
	private static final long serialVersionUID = -3520809214215065493L;
	private transient UserGeneralController controller = UserGeneralController.getInstance();
	private static boolean ERROR_REMIX=false;
	
	public String reloadInfo(String username, String operation, String pageNo, String pageSize, String startFilter) {
		if(operation==null) operation = "GO"; 
		if(username == null) {setTotalCount(0); return "";}
		
		List<HttpSession> sessions = controller.getSessions().getSessionsFor(username); 
		if(sessions==null) {setTotalCount(0); return "";}
		setTotalCount(sessions.size()); 
		
		if(pageNo!=null) try {setPageNo(Integer.parseInt(pageNo));} catch (Exception ex) { if(ERROR_REMIX) ex.printStackTrace();}
		if(pageSize!=null) try {setPageSize(Integer.parseInt(pageSize));} catch (Exception ex) { if(ERROR_REMIX) ex.printStackTrace();}
		if(startFilter!=null) setStartFilter(startFilter);
			
		switch(operation) {
			case "NEXT": 
				nextPage();
				break;
			case "PREVIOUS":
				previousPage(); 
				break;
		}
		return ""; 
	}
}
