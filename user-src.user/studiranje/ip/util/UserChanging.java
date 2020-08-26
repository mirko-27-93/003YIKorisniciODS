package studiranje.ip.util;

import java.io.Serializable;

/**
 * Апстракција погодна за безимене класе као активности, када се 
 * продлеђује старо и ново име корисника. Погодно за активности 
 * при промјенама имена корисника. Нпр. везање активности или 
 * биљежење и писање трага који ће се онда моћи употријебити. 
 * @author mirko
 * @version 1.0
 */
public interface UserChanging extends Cloneable, Serializable{
	public void change(String oldUsername, String neoUsername);
	public UserChanging clone() throws CloneNotSupportedException;
}
