package studiranje.ip.controller;

import studiranje.ip.data.DBAbstractUserDAO;
import studiranje.ip.database.UserDTO;
import studiranje.ip.model.UserInfo;
import studiranje.ip.model.UserPassword;

/**
 * Уопђтење за регистрациони контролер при базама података. 
 * @author mirko
 * @version 1.0
 */
public abstract class AbstractUserRegsistrationController {	
	public abstract void register(UserDTO  user); 
	public abstract void unregister(String username);
	public abstract UserDTO get(String username);
	public abstract UserInfo getInfo(String username);
	public abstract UserPassword getPassword(String username);
	public abstract DBAbstractUserDAO getUserDataLink();
}
