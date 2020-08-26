package programiranje.yi.database;

import java.net.URI;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.UriBuilder;

import studiranje.ip.data.DBRootDAO;
import studiranje.ip.data.RootConnectionPool;
import studiranje.ip.database.model.DBUserData;

/**
 * Баратање операција са табелом регистровања 
 * корисника базе података, као редовиних 
 * корисничких профила. 
 * @author mirko
 * @version 1.0
 */
public class MySQLUserDAO {
public static final String TABLE_DATABASE_INFO = "userdatabase"; 
	
	private RootConnectionPool connection; 
	private DBRootDAO dbRootInformer; 
	
	public static final String SQL_PROCEDURE_DB_USER_DELETE = "userdatabase_AD_PROC"; 
	public static final String SQL_PROCEDURE_DB_USER_INSERT = "userdatabase_AI_PROC"; 
	public static final String SQL_PROCEDURE_DB_UPDATE_USERNAME = "userdatabase_AU_PROC"; 
	public static final String SQL_PROCEDURE_DB_UPDATE_PASSWORD = "userdatabase_AU_PROC_Password";
	
	public static final List<String> fabricDBUsers = new ArrayList<>(Arrays.asList("mysql.infoschema", "mysql.session", "mysql.sys", "root")); 
	
	public String sqlSelectDbUsers() {
		return "SELECT username, databaseuser FROM "+connection.getDbName()+".userdatabase"; 
	}
	
	public String sqlCountDbUsers() {
		return "SELECT COUNT(username) FROM "+connection.getDbName()+".userdatabase"; 
	}
	
	public String sqlCountConcreteDbUser() {
		return "SELECT COUNT(username) FROM "+connection.getDbName()+".userdatabase WHERE username=?"; 
	}
	
	public String sqlSelectConcreteDbUser() {
		return "SELECT username, databaseuser  FROM "+connection.getDbName()+".userdatabase WHERE username=?"; 
	}
	
	public String sqlCreateDBUser() {
		return "INSERT INTO "+connection.getDbName()+".userdatabase(username, databaseuser) VALUES (?,?)"; 
	}
		
	public String sqlUpdateDBUser() {
		return "UPDATE "+connection.getDbName()+".userdatabase SET username=?, databaseuser=? WHERE username=?"; 
	}
	
	public String sqlDeleteDBUser() {
		return "DELETE FROM "+connection.getDbName()+".userdatabase WHERE username=?"; 
	}
	
	public String psqlInsertDBOwner() {
		return "{call "+connection.getDbName()+"."+SQL_PROCEDURE_DB_USER_INSERT+"(?,?)}"; 
	}
	
	public String psqlDeleteDBOwner() {
		return "{call "+connection.getDbName()+"."+SQL_PROCEDURE_DB_USER_DELETE+"(?)}"; 
	}
	
	public String psqlUsernameUpdateDBOwner() {
		return "{call "+connection.getDbName()+"."+ SQL_PROCEDURE_DB_UPDATE_USERNAME +"(?,?)}"; 
	}
	
	public String psqlPasswordUpdateDBOwner() {
		return "{call "+connection.getDbName()+"."+ SQL_PROCEDURE_DB_UPDATE_PASSWORD +"(?,?)}"; 
	}
	
	public String sqlListOfMySQLProcedures() { 
			return "SELECT ROUTINE_NAME, ROUTINE_TYPE, DATA_TYPE, SPECIFIC_NAME "
		    +"FROM information_schema.routines "
		    +"WHERE ROUTINE_SCHEMA ='"+connection.getDbName()+"'"; 
	}
	
	public MySQLUserDAO(RootConnectionPool pool) {
		if(pool == null) throw new NullPointerException("DB CONNECTION POOL");
		this.connection = pool; 
		this.dbRootInformer = new DBRootDAO(pool); 
	}

	public RootConnectionPool getConnection() {
		return connection;
	}

	public DBRootDAO getDbRootInformer() {
		return dbRootInformer;
	}

	public boolean databaseManervarable() throws SQLException {
		if(dbRootInformer==null) return false; 
		return dbRootInformer.getTables(connection.getDbName()).contains(TABLE_DATABASE_INFO); 
	}
	
	public List<MySQLUserDTO> getDBUsers() throws SQLException{
		ArrayList<MySQLUserDTO> users = new ArrayList<>();
		Connection conn = connection.checkOut();
		try {
			try(PreparedStatement statement = conn.prepareStatement(sqlSelectDbUsers())){
				try(ResultSet rs = statement.executeQuery()){
					while(rs.next()) {
						String username = rs.getString("username"); 
						boolean databaseuser = rs.getBoolean("databaseuser"); 
						MySQLUserDTO dto = new MySQLUserDTO();
						dto.setUsername(username);
						dto.setDatabaseUser(databaseuser);
						users.add(dto); 
					}
				}
			}
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally {
			connection.checkIn(conn);
		}
		return users; 
	}
	
	public int countDBUsers() throws SQLException {
		Connection conn = connection.checkOut();
		try {
			try(PreparedStatement statement = conn.prepareStatement(sqlCountDbUsers())){
				try(ResultSet rs = statement.executeQuery()){
					while(rs.next()) {
						return rs.getInt(1); 
					}
					throw new RuntimeException("NO INFO DATA.");
				}
			}
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally {
			connection.checkIn(conn);
		}
	}
	
	public MySQLUserDTO getDBUser(String userName) throws SQLException{
		MySQLUserDTO user = null;
		Connection conn = connection.checkOut();
		try {
			try(PreparedStatement statement = conn.prepareStatement(sqlSelectConcreteDbUser())){
				statement.setString(1, userName);
				try(ResultSet rs = statement.executeQuery()){
					while(rs.next()) {
						String username = rs.getString("username"); 
						boolean databaseuser = rs.getBoolean("databaseuser"); 
						MySQLUserDTO dto = new MySQLUserDTO();
						dto.setUsername(username);
						dto.setDatabaseUser(databaseuser);
						user = dto; 
					}
				}
			}
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally {
			connection.checkIn(conn);
		}
		return user; 
	}
	
	public int countConcreteDBUser(String userName) throws SQLException {
		Connection conn = connection.checkOut();
		try {
			try(PreparedStatement statement = conn.prepareStatement(sqlCountConcreteDbUser())){
				statement.setString(1, userName);
				try(ResultSet rs = statement.executeQuery()){
					while(rs.next()) {
						return rs.getInt(1); 
					}
					throw new RuntimeException("NO INFO DATA.");
				}
			}
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally {
			connection.checkIn(conn);
		}
	}
	
	public DBUserData getDBOwner(String username) throws SQLException {
		if(username==null) return null; 
		if(dbRootInformer==null) return null; 
		for(DBUserData data: dbRootInformer.getUsers()) 
			if(username.equals(data.getUserName()))return data; 
		return null;
	}
	
	public boolean isRealDBUser(String dbUser) throws SQLException {
		return getDBOwner(dbUser)!=null && getDBUser(dbUser)!=null;
	}
	
	public void insert(MySQLUserDTO dto) throws SQLException {
		Connection conn = connection.checkOut();
		try {
			try(PreparedStatement statement = conn.prepareStatement(sqlCreateDBUser())){
				statement.setString(1, dto.getUsername());
				statement.setBoolean(2, dto.isDatabaseUser());
				statement.execute();
			}
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally {
			connection.checkIn(conn);
		}
	}
	
	public void update(String oldUserName, MySQLUserDTO dto) throws SQLException {
		Connection conn = connection.checkOut();
		try {
			try(PreparedStatement statement = conn.prepareStatement(sqlUpdateDBUser())){
				statement.setString(1, dto.getUsername());
				statement.setBoolean(2, dto.isDatabaseUser()); 
				statement.setString(3, oldUserName);
				statement.execute();
			}
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally {
			connection.checkIn(conn);
		}
	}
	
	public void put(MySQLUserDTO dto) throws SQLException {
		if(dto==null) throw new NullPointerException(); 
		if(getDBUser(dto.getUsername())!=null) update(dto.getUsername(), dto);
		else insert(dto);
	}
	
	public void delete(String oldUserName) throws SQLException {
		Connection conn = connection.checkOut();
		try {
			try(PreparedStatement statement = conn.prepareStatement(sqlDeleteDBUser())){
				statement.setString(1, oldUserName);
				statement.execute(); 
			}
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally {
			connection.checkIn(conn);
		}
	}
	
	public void clean(String username) throws SQLException {
		if(getDBUser(username)==null) return;
		delete(username);
	}
	
	public void openAsDatabaseUser(String username, String password) throws SQLException {
		if(fabricDBUsers.contains(username)) 
			throw new RuntimeException("ADD FABRIC DB USERS IS NOT SUPPORTED"); 
		Connection conn = connection.checkOut();
		try {
			try(CallableStatement statement = conn.prepareCall(psqlInsertDBOwner())){
				statement.setString(1, username);
				statement.setString(2, password);
				statement.execute();
			}
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally {
			connection.checkIn(conn);
		}
	}
	
	public List<String> listOfProcedureRoutines() throws SQLException{
		ArrayList<String> procedures = new ArrayList<>(); 
		Connection conn = connection.checkOut(); 
		try {
			try(PreparedStatement statement = conn.prepareStatement(sqlListOfMySQLProcedures())){
				try(ResultSet rs = statement.executeQuery()){
					while(rs.next()) {
						String routine = rs.getString(1); 
						String type = rs.getString(2);
						if(type.toUpperCase().contentEquals("PROCEDURE"))
							procedures.add(routine); 
					}
				}
			}
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}finally {
			connection.checkIn(conn);
		}
		return procedures;
	}
	
	public boolean existsDeleteDBUserProcedure() throws SQLException {
		return  listOfProcedureRoutines().contains(SQL_PROCEDURE_DB_USER_DELETE); 
	}
	
	public boolean existsInsertDBUserProcedure() throws SQLException {
		return  listOfProcedureRoutines().contains(SQL_PROCEDURE_DB_USER_INSERT); 
	}
	
	public boolean existsUsernameUpdateDBUserProcedure() throws SQLException {
		return  listOfProcedureRoutines().contains(SQL_PROCEDURE_DB_UPDATE_USERNAME); 
	}
	
	public boolean existsPasswordUpdateDBUserProcedure() throws SQLException {
		return  listOfProcedureRoutines().contains(SQL_PROCEDURE_DB_UPDATE_PASSWORD); 
	}
	
	public void closeDatabaseUserProfile(String username) throws SQLException {
		if(fabricDBUsers.contains(username)) 
			throw new RuntimeException("CHANGING FABRIC DB USERS IS NOT SUPPORTED"); 
		Connection conn = connection.checkOut();
		try {
			try(CallableStatement statement = conn.prepareCall(psqlDeleteDBOwner())){
				statement.setString(1, username);
				statement.execute();
			}
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally {
			connection.checkIn(conn);
		}
	}
	
	public void updateUsernameOfDatabaseUser(String oldUsername, String neoUsername) throws SQLException {
		if(fabricDBUsers.contains(oldUsername)) 
			throw new RuntimeException("CHANGING FABRIC DB USERS IS NOT SUPPORTED"); 
		Connection conn = connection.checkOut();
		try {
			try(CallableStatement statement = conn.prepareCall(psqlUsernameUpdateDBOwner())){
				statement.setString(1, oldUsername);
				statement.setString(2, neoUsername);
				statement.execute();
			}
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally {
			connection.checkIn(conn);
		}
	}
	
	public void updatePasswordOfDatabaseUser(String oldUsername, String neoPassword) throws SQLException {
		if(fabricDBUsers.contains(oldUsername)) 
			throw new RuntimeException("CHANGING FABRIC DB USERS IS NOT SUPPORTED"); 
		Connection conn = connection.checkOut();
		try {
			try(CallableStatement statement = conn.prepareCall(psqlPasswordUpdateDBOwner())){
				statement.setString(1, oldUsername);
				statement.setString(2, neoPassword);
				statement.execute();
			}
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally {
			connection.checkIn(conn);
		}
	}
	
	public boolean validatePassword(String username, String password) {
		try {
			String dbURI = connection.getFullDatabaseInclassURI();
			URI uri = new URI(dbURI);
			URI userURI = UriBuilder.fromUri(uri)
					.userInfo(username+":"+password)
					.replacePath(null)
					.build(); 
			RootConnectionPool bsp = RootConnectionPool.isolatedConnectionPool(userURI.toString()); 
			Connection conn = bsp.checkOut();
			bsp.checkIn(conn);
			bsp.close();
		}catch(Exception ex) {
			return false; 
		}
		return true; 
	}
}
