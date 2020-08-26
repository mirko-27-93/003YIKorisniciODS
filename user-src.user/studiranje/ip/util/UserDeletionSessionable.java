package studiranje.ip.util;

import java.io.Serializable;

import javax.servlet.http.HttpSession;

/**
 * Интерфејс погодан за дефинисање активности када је порслеђено корисничко име. 
 * Првенствено је предвиђено за активности брисања корисника. 
 * @author mirko
 * @version 1.0
 */
public interface UserDeletionSessionable extends Cloneable, Serializable{
	public void delete(String username, HttpSession session);
	public UserDeletionSessionable clone() throws CloneNotSupportedException;
}
