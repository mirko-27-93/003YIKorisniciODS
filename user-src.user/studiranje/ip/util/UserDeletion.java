package studiranje.ip.util;

import java.io.Serializable;

/**
 * Интерфејс погодан за дефинисање активности када је порслеђено корисничко име. 
 * Првенствено је предвиђено за активности брисања корисника. 
 * @author mirko
 * @version 1.0
 */
public interface UserDeletion extends Cloneable, Serializable{
	public void delete(String username);
	public UserDeletion clone() throws CloneNotSupportedException;
}
