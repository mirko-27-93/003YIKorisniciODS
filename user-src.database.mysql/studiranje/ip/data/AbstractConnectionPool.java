package studiranje.ip.data;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Уопштење за конекционе пуле. 
 * @author mirko
 * @version 1.0
 */
public abstract class AbstractConnectionPool {
	 public abstract Connection checkOut() throws SQLException; 
	 public abstract void checkIn(Connection aConn); 
}
