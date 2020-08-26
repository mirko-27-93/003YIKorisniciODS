package studiranje.ip.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import studiranje.ip.bean.PageBean;
import studiranje.ip.database.UserDTO;
import studiranje.ip.model.UserInfo;
import studiranje.ip.model.UserRequisit;

/**
 *  Адаптер за листу корисника. 
 *  @author mirko
 *  @version 1.0
 */
public class DBUserListDAO {
	private DBAbstractUserDAO dao; 
	private String sqlGetAllUsers    = "SELECT username, firstname, secondname, emailaddress, telephone,  city, country FROM yi.userinfo";
	private String sqlGetPageOfUsers = "SELECT username, firstname, secondname, emailaddress, telephone,  city, country FROM yi.userinfo WHERE username LIKE ? LIMIT ? OFFSET ?"; 
	private String sqlCountUsers     = "SELECT count(username) FROM yi.userinfo"; 
	private String sqlCountFiltredUsers = "SELECT count(username) FROM yi.userinfo WHERE username LIKE ?"; 
	private String database = "yi"; 
	
	public void  setDatabase(String database) {
		sqlGetAllUsers    = "SELECT username, firstname, secondname, emailaddress, telephone,  city, country FROM "+database+".userinfo";
		sqlGetPageOfUsers = "SELECT username, firstname, secondname, emailaddress, telephone,  city, country FROM "+database+".userinfo WHERE username LIKE ? LIMIT ? OFFSET ?";
		sqlCountUsers     = "SELECT count(username) FROM "+database+".userinfo";
		sqlCountFiltredUsers = "SELECT count(username) FROM "+database+".userinfo WHERE username LIKE ?"; 
		this.database = database; 
	}
	
	public void  setDatabase(String database, PageBean page) {
		int pageNo = page.getPageNo(); 
		int pageSize = page.getPageSize(); 
		pageNo--; 
		int offset = pageNo*pageSize;
		sqlGetAllUsers    = "SELECT username, firstname, secondname, emailaddress, telephone,  city, country FROM "+database+".userinfo";
		sqlGetPageOfUsers = "SELECT username, firstname, secondname, emailaddress, telephone,  city, country FROM "+database+".userinfo WHERE username LIKE ? LIMIT "+pageSize+" OFFSET "+offset;
		sqlCountUsers     = "SELECT count(username) FROM "+database+".userinfo";
		sqlCountFiltredUsers = "SELECT count(username) FROM "+database+".userinfo WHERE username LIKE ?"; 
		this.database = database; 
	}
	
	public String getDatabase() {
		return database; 
	}
	
	public String getSqlCountUsers() {
		return sqlCountUsers;
	}
	public void setSqlCountUsers(String sqlCountUsers) {
		
		this.sqlCountUsers = sqlCountUsers;
	}
	public String getSqlGetAllUsers() {
		return sqlGetAllUsers;
	}
	public void setSqlGetAllUsers(String sqlGetAllUsers) {
		this.sqlGetAllUsers = sqlGetAllUsers;
	}
	public String getSqlGetPageOfUsers() {
		return sqlGetPageOfUsers;
	}
	public void setSqlGetPageOfUsers(String sqlGetPageOfUsers) {
		this.sqlGetPageOfUsers = sqlGetPageOfUsers;
	}
	
	public DBUserListDAO(DBAbstractUserDAO dao) {
		this.dao = dao; 
	}
	public DBAbstractUserDAO getDao() {
		return dao;
	}
	public void setDao(DBAbstractUserDAO dao) {
		this.dao = dao;
	}

	public int countUsers() throws SQLException {
		AbstractConnectionPool connections = dao.getConnections(); 
		Connection connection = connections.checkOut(); 
		try(PreparedStatement statement = connection.prepareStatement(sqlCountUsers)){
			try(ResultSet rs = statement.executeQuery()){
				while(rs.next()) {
					return rs.getInt(1); 
				}
				throw new RuntimeException("NOT FOND INFORMATION");
			}
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally {
			connections.checkIn(connection);
		}
	}
	
	public int countUsers(PageBean page) throws SQLException {
		AbstractConnectionPool connections = dao.getConnections(); 
		Connection connection = connections.checkOut(); 
		try(PreparedStatement statement = connection.prepareStatement(sqlCountFiltredUsers)){
			statement.setString(1, page.getSQLEscapeStartFilter()+"%");
			try(ResultSet rs = statement.executeQuery()){
				while(rs.next()) {
					return rs.getInt(1); 
				}
				throw new RuntimeException("NOT FOND INFORMATION");
			}
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally {
			connections.checkIn(connection);
		}
	}
	
	public List<UserDTO> getUsers() throws SQLException{
		ArrayList<UserDTO> users = new ArrayList<>();
		AbstractConnectionPool connections = dao.getConnections(); 
		Connection connection = connections.checkOut(); 
		try(PreparedStatement statement = connection.prepareStatement(sqlGetAllUsers)){ 
			try(ResultSet rs = statement.executeQuery()){
				while(rs.next()) {
					String username = rs.getString("username"); 
					String firstname = rs.getString("firstname"); 
					String secondname = rs.getString("secondname");
					String useremail = rs.getString("emailaddress"); 
					String telephone = rs.getString("telephone");
					String city = rs.getString("city"); 
					String country = rs.getString("country"); 
					UserInfo ui = new UserInfo(username, firstname, secondname, useremail); 
					UserRequisit  ur = new UserRequisit();
					ur.setTelephone(telephone);
					ur.setCity(city);
					ur.setCountry(country);
					UserDTO dto = new UserDTO(ui, null, ur);
					users.add(dto);
				}
			}
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally {
			connections.checkIn(connection);
		}
		return users; 
	}
	
	public List<UserDTO> getUsers(PageBean page) throws SQLException{
		ArrayList<UserDTO> users = new ArrayList<>();
		AbstractConnectionPool connections = dao.getConnections(); 
		Connection connection = connections.checkOut(); 
		setDatabase(database, page);
		try(PreparedStatement statement = connection.prepareStatement(sqlGetPageOfUsers)){	
			statement.setString(1, page.getSQLEscapeStartFilter()+"%");
			try(ResultSet rs = statement.executeQuery()){
				while(rs.next()) {
					String username = rs.getString("username"); 
					String firstname = rs.getString("firstname"); 
					String secondname = rs.getString("secondname");
					String useremail = rs.getString("emailaddress"); 
					String telephone = rs.getString("telephone");
					String city = rs.getString("city"); 
					String country = rs.getString("country"); 
					UserInfo ui = new UserInfo(username, firstname, secondname, useremail); 
					UserRequisit  ur = new UserRequisit();
					UserDTO dto = new UserDTO(ui, null, ur);
					ur.setTelephone(telephone);
					ur.setCity(city);
					ur.setCountry(country);
					users.add(dto);
				}
			}
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally {
			connections.checkIn(connection);
		}
		return users; 
	}
}
