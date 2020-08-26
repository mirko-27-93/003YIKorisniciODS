package studiranje.ip.util;

import java.io.Serializable;

import javax.servlet.http.HttpSession;

/**
 * Апстракција погодна за безимене класе као активности, када се 
 * продлеђује старо и ново име корисника. Погодно за активности 
 * при промјенама имена корисника. Нпр. везање активности или 
 * биљежење и писање трага који ће се онда моћи употријебити. 
 * @author mirko
 * @version 1.0
 */
public interface UserChangingSessionable extends Cloneable, Serializable{
	public void change(String oldUsername, String neoUsername, HttpSession session);
	public UserChangingSessionable clone() throws CloneNotSupportedException;
}
