package studiranje.ip.object;

import java.io.Serializable;

/**
 * Општи одговор када је у питању сервлет/сервис. 
 * @author mirko
 * @version 1.0
 */
public class GeneralOperationResponse implements Serializable{
	private static final long serialVersionUID = -4949916620967178364L;
	private boolean success = true; 
	private String message = "";
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		if(message==null) message = ""; 
		this.message = message;
	}
	
}
