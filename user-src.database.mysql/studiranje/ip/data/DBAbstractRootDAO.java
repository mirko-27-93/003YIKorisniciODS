package studiranje.ip.data;

import java.sql.SQLException;
import java.util.List;


import studiranje.ip.database.model.DBTableInfo;
import studiranje.ip.database.model.DBUserData;
import studiranje.ip.engine.model.DataSourceRootModel;

/**
 * Уопштење за све коријенске адаптере према базама података, 
 * односно оне које се баве и баратају са очитавањем схема 
 * релационих база података. 
 * @author mirko
 * @version 1.0
 */
public abstract class DBAbstractRootDAO implements DataSourceRootModel{
	
	public abstract RootConnectionPool getConnections(); 
	public abstract void setConnections(RootConnectionPool connections); 
	
	@Override public abstract List<String> getDatabases() throws SQLException; 
	@Override public abstract List<String> getTables(String database) throws SQLException;
	@Override public abstract DBTableInfo getTableInfo(String database, String tablename) throws SQLException; 
	@Override public abstract List<DBUserData> getUsers() throws SQLException; 

	public  DBDatabaseSwappable asDBDatabaseSwappable() {
		if(this instanceof DBDatabaseSwappable) {
			return (DBDatabaseSwappable) this;
		}
		return null; 
	}
}
