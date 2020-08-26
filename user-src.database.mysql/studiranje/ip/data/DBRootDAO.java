package studiranje.ip.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import studiranje.ip.database.model.DBRecordInfo;
import studiranje.ip.database.model.DBTableInfo;
import studiranje.ip.database.model.DBUserData;

/**
 * Основни адаптер, када су у питању информације о базама података. 
 * @author mirko
 * @version 1.0
 */
public class DBRootDAO extends DBAbstractRootDAO{
	public final static String sqlGetDatabases = "SHOW DATABASES";
	public final static String sqlGetTables = "SHOW TABLES ";
	public final static String sqlGetTableInfo = "DESCRIBE "; 
	public final static String sqlGetUser = "SELECT user, host, authentication_string FROM mysql.user";
	
	
	public static String getSqlgetdatabases() {
		return sqlGetDatabases;
	}

	public static String getSqlgettables(String database) { 
		return "SHOW TABLES FROM "+database;
	}

	public static String getSqlgettableinfo() {
		return sqlGetTableInfo;
	}

	public static String getSqlgetuser() {
		return sqlGetUser;
	}

	private RootConnectionPool connections;
	
	public DBRootDAO(RootConnectionPool connections) {
		this.connections = connections; 
	}
	
	@Override
	public RootConnectionPool getConnections() {
		return connections;
	}

	@Override
	public void setConnections(RootConnectionPool connections) {
		this.connections = connections;
	}
	
	@Override
	public List<String> getDatabases() throws SQLException{
		ArrayList<String> databases = new ArrayList<>(); 
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(sqlGetDatabases)){
			try(ResultSet resultSet = statement.executeQuery()){
				while(resultSet.next()) {
					databases.add(resultSet.getString("database"));
				}
			}
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally{
			connections.checkIn(connection);
		}
		return databases;
	}
	
	@Override
	public List<String> getTables(String database) throws SQLException{
		ArrayList<String> tables = new ArrayList<>(); 
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement("USE "+database)){
			statement.execute();
		}
		try (PreparedStatement statement = connection.prepareStatement(getSqlgettables(database))){
			try(ResultSet resultSet = statement.executeQuery()){
				while(resultSet.next()) {
					tables.add(resultSet.getString(1));
				}
			}
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally{
			connections.checkIn(connection);
		}
		return tables;
	}
	
	@Override
	public DBTableInfo getTableInfo(String database, String tablename) throws SQLException {
		DBTableInfo result = new DBTableInfo(); 
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement("USE "+database)){
			statement.execute();
		}
		try (PreparedStatement statement = connection.prepareStatement(sqlGetTableInfo+database+"."+tablename)){
			try(ResultSet resultSet = statement.executeQuery()){
				while(resultSet.next()) {
					String field = resultSet.getString("field"); 
					String type  = resultSet.getString("type");
					String zero = resultSet.getString("null");
					String classic = resultSet.getString("default");
					String key = resultSet.getString("key");
					String extra = resultSet.getString("extra");
					DBRecordInfo record = new DBRecordInfo(); 
					record.setField(field);
					record.setClassic(classic);
					record.setType(type);
					record.setExtra(extra);
					record.setKey(key);
					record.setZero(zero);
					result.add(field, record);
				}
			}
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally{
			connections.checkIn(connection);
		}
		return result;
	}
	
	@Override
	public List<DBUserData> getUsers() throws SQLException{
		ArrayList<DBUserData> users = new ArrayList<>(); 
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement("USE information_schema")){
			statement.execute();
		}
		try (PreparedStatement statement = connection.prepareStatement(sqlGetUser)){
			try(ResultSet resultSet = statement.executeQuery()){
				while(resultSet.next()) {
					DBUserData userData = new DBUserData(); 
					userData.setUserName(resultSet.getString("user"));
					userData.setHostName(resultSet.getString("host"));
					userData.setAuthenticationString(resultSet.getString("authentication_string")); 
					users.add(userData); 
				}
			}
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally{
			connections.checkIn(connection);
		}
		return users;
	}
}
