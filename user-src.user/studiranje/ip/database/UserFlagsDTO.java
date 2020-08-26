package studiranje.ip.database;

import java.io.Serializable;

import studiranje.ip.model.UserFlags;

/**
 * Објекат за корисничке поставке које се чувају у бази података или бар 
 * чији коријени и дијелови се могу довести у везу са базом података.
 * @author mirko
 * @version 1.0
 */
public class UserFlagsDTO implements Serializable{
	private static final long serialVersionUID = -3527847168183425554L;
	
	private UserFlags configurations;

	public UserFlags getConfigurations() {
		return configurations;
	}

	public void setConfigurations(UserFlags configurations) {
		this.configurations = configurations;
	}
}
