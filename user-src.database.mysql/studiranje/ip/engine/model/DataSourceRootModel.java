package studiranje.ip.engine.model;

import java.sql.SQLException;
import java.util.List;

import studiranje.ip.data.DBAbstractRootDAO;
import studiranje.ip.database.model.DBTableInfo;
import studiranje.ip.database.model.DBUserData;


/**
 * Уопштење за изворе података који имитирају операције на схемом 
 * табела релационе базе података. 
 * @author mirko
 * @version 1.0
 */
public interface DataSourceRootModel {
	public default DBAbstractRootDAO asDBAbstractRootDAO() {
		if(this instanceof DBAbstractRootDAO) {
			return (DBAbstractRootDAO) this;
		}
		return null; 
	}
	
	public abstract List<String> getDatabases() throws SQLException; 
	public abstract List<String> getTables(String database) throws SQLException;
	public abstract DBTableInfo getTableInfo(String database, String tablename) throws SQLException; 
	public abstract List<DBUserData> getUsers() throws SQLException; 
}
