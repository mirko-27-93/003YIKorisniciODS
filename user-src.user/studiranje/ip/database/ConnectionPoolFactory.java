package studiranje.ip.database;

import studiranje.ip.data.AbstractConnectionPool;
import studiranje.ip.data.RootConnectionPool;

/**
 * Производња и складиштење разноврсних конекционих пула у зависности од типа и 
 * коријенских, ако се користе измјењице базе података. 
 * @author mirko
 * @version 1.0
 */
public class ConnectionPoolFactory {	
	public ConnectionPool getBasicPool() {
		return ConnectionPool.getConnectionPool(); 
	}
	
	public RootConnectionPool getRootPool(String database) {
		return RootConnectionPool.getConnectionPool(database); 
	}
	
	public AbstractConnectionPool getInstance() {
		return getBasicPool(); 
	}
	
	public AbstractConnectionPool getInstance(String database) {
		return getRootPool(database); 
	}
}
