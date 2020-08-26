package studiranje.ip.bean;

import java.io.Serializable;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;

import studiranje.ip.controller.UserGeneralController;
import studiranje.ip.database.UserDTO;

/**
 * Свеобухватно зрно са подацима када су у питању основни подаци за корисника. 
 * @author mirko
 * @version 1.0
 */
public class GeneralUserBean implements Serializable{
	private static final long serialVersionUID = 2331726667394105034L;
	private transient UserGeneralController controller = UserGeneralController.getInstance();
	
	private UserDTO dto;
	private String username; 
	private HttpSession session; 
	
	public GeneralUserBean() {}
	public GeneralUserBean(String username) {
		try {

			this.dto = controller.getRegistrator(session).getUserDataLink().getFullDTO(username); 
		}catch(Exception ex) {
			throw new RuntimeException(new JspException(ex)); 
		}
	}
	
	public UserDTO getDto() {
		return dto;
	}

	public void setDto(UserDTO dto) {
		this.dto = dto;
	}
	
	public boolean existsData() {
		return dto==null; 
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
		if(username==null) dto=null;
		else 
			try {
				this.dto = controller.getRegistrator(session).getUserDataLink().getFullDTO(username); 
			}catch(Exception ex) {
				throw new RuntimeException(new JspException(ex)); 
			}
	}
	
	public void reset() {
		try {
			this.dto = controller.getRegistrator(session).getUserDataLink().getFullDTO(username); 
		}catch(Exception ex) {
			throw new RuntimeException(new JspException(ex)); 
		}
	}
	
	public GeneralUserBean reload() {
		try {
			this.dto = controller.getRegistrator(session).getUserDataLink().getFullDTO(username); 
			return this;
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}
	}
	
	public String getUserName() {
		if(dto==null) return ""; 
		if(dto.getUser()==null) return ""; 
		if(dto.getUser().getUsername()==null) return ""; 
		return dto.getUser().getUsername(); 
	}
	
	public String getFirstName() {
		if(dto==null) return ""; 
		if(dto.getUser()==null) return ""; 
		if(dto.getUser().getFirstname()==null) return ""; 
		return dto.getUser().getFirstname(); 
	}
	
	public String getSecondName() {
		if(dto==null) return ""; 
		if(dto.getUser()==null) return ""; 
		if(dto.getUser().getSecondname()==null) return ""; 
		return dto.getUser().getSecondname(); 
	}
	
	public String getElectronicMailAddress() {
		if(dto==null) return ""; 
		if(dto.getUser()==null) return ""; 
		if(dto.getUser().getEmail()==null) return ""; 
		return dto.getUser().getEmail(); 
	}
	
	public String getPasswordRecord() {
		if(dto==null) return ""; 
		if(dto.getPassword()==null) return ""; 
		try {
			String prc = dto.getPassword().getToPasswordRecord();
			if(prc==null) return "";
			return prc; 
		}
		catch(Exception ex) {return "";}
	}
	
	public String getDescription() {
		if(dto==null) return ""; 
		if(dto.getDescription()==null) return ""; 
		return dto.getDescription(); 
	}
	
	public String getTelephone() {
		if(dto==null) return "";
		if(dto.getRequisit()==null) return ""; 
		if(dto.getRequisit().getTelephone()==null) return ""; 
		return dto.getRequisit().getTelephone(); 
	}
	
	public String getCityAndAddress() {
		if(dto==null) return "";
		if(dto.getRequisit()==null) return "";
		if(dto.getRequisit().getCity()==null) return ""; 
		return dto.getRequisit().getCity(); 
	}
	
	public String getUserImageName() {
		if(dto==null) return ""; 
		if(dto.getRequisit()==null) return ""; 
		if(dto.getRequisit().getUserPicture()==null) return ""; 
		return dto.getRequisit().getUserPicture().getName().split("_",2)[1]; 
	}
	
	public String getProfileImageName() {
		if(dto==null) return ""; 
		if(dto.getRequisit()==null) return ""; 
		if(dto.getRequisit().getProfilePicture()==null) return ""; 
		return dto.getRequisit().getProfilePicture().getName().split("_",2)[1]; 
	}
	
	public String getCountryFlagImageName() {
		if(dto==null) return ""; 
		if(dto.getRequisit()==null) return ""; 
		if(dto.getRequisit().getUserPicture()==null) return ""; 
		return dto.getRequisit().getCountryFlagPicture().getName(); 
	}
	
	public String getCountryName() {
		if(dto==null) return ""; 
		if(dto.getRequisit()==null) return "";
		if(dto.getRequisit().getCountry()==null) return ""; 
		return dto.getRequisit().getCountry();
	}
	public HttpSession getSession() {
		return session;
	}
	public void setSession(HttpSession session) {
		this.session = session;
	}
}
