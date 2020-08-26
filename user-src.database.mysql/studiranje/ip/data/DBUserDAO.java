package studiranje.ip.data;

import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import programiranje.yi.database.MySQLUserDAO;
import studiranje.ip.data.event.DeleteUserReactionModel;
import studiranje.ip.data.event.DeleteUserRunnable;
import studiranje.ip.data.event.ReactionModel;
import studiranje.ip.data.event.UpdateUsernameReactionModel;
import studiranje.ip.data.event.UpdateUsernameRunnable;
import studiranje.ip.data.event.UpdateUserpasswordReactionModel;
import studiranje.ip.data.event.UpdateUserpasswordRunnable;
import studiranje.ip.database.UserDTO;
import studiranje.ip.database.UserFlagsDTO;
import studiranje.ip.exception.UserDuplicationException;
import studiranje.ip.exception.UserNotFoundException;
import studiranje.ip.lang.UserFileSystemPathConstants;
import studiranje.ip.model.UserFlags;
import studiranje.ip.model.UserInfo;
import studiranje.ip.model.UserPassword;
import studiranje.ip.model.UserRequisit;

/**
 * Универзални кориснички DAO. 
 * @author mirko
 * @version 1.0
 */
public class DBUserDAO extends DBAbstractUserDAO implements Serializable, DBDatabaseSwappable{
	private static final long serialVersionUID = -3002735144914482291L;
	private String sqlInsert = "INSERT INTO yi.userinfo(username, firstname, secondname, emailaddress, passwordhash) VALUES (?,?,?,?,?)";
	private String sqlUpdate = "UPDATE yi.userinfo SET username=?, firstname=?, secondname=?, emailaddress=?, passwordhash=? WHERE username=?";
	private String sqlDelete = "DELETE FROM yi.userinfo WHERE username=?";
	private String sqlSelect = "SELECT username, firstname, secondname, emailaddress, passwordhash FROM yi.userinfo WHERE username=?";
	private String sqlGetUser = "SELECT username, firstname, secondname, emailaddress FROM yi.userinfo WHERE username=?"; 
	private String sqlGetRequisite = "SELECT username, telephone, city, country, profile_image_path, user_image_path, country_flag_image_path, notification_webapp_supported, notification_email_supported FROM yi.userinfo WHERE username=?";
	private String sqlGetPassword = "SELECT passwordhash FROM yi.userinfo WHERE username=?"; 
	private String sqlGetMail = "SELECT emailaddress FROM yi.userinfo WHERE username=?";
	private String sqlExistsUN = "SELECT count(username) FROM yi.userinfo WHERE username=?";
	private String sqlExistsEmail = "SELECT count(emailaddress) FROM yi.userinfo WHERE emailaddress=?";
	private String sqlUpdateProfileImagePath = "UPDATE yi.userinfo SET profile_image_path=? WHERE username=?"; 
	private String sqlUpdateUserImagePath = "UPDATE yi.userinfo SET user_image_path=? WHERE username=?"; 
	private String sqlUpdateCountryFlagImagePath = "UPDATE yi.userinfo SET country_flag_image_path=? WHERE username=?";
	private String sqlUpdateRequisit = "UPDATE yi.userinfo SET city=?, country=?, telephone=?, notification_webapp_supported=?, notification_email_supported=? WHERE username=?";
	private String sqlGetDescription = "SELECT short_description FROM yi.userinfo WHERE username=?"; 
	private String sqlUpdateDescription = "UPDATE yi.userinfo SET short_description = ? WHERE username=?"; 
	private String sqlGetUserFlags = "SELECT notification_webapp_supported, notification_email_supported, user_session_control FROM yi.userinfo WHERE username=?";
	private String sqlPutUserFlags = "UPDATE yi.userinfo SET notification_webapp_supported=?, notification_email_supported=?, user_session_control=? WHERE username=?"; 
	
	public static final boolean ERROR_REMIX = false;
	
	public String getSqlInsert() {
		return sqlInsert;
	}

	public String getSqlUpdate() {
		return sqlUpdate;
	}

	public String getSqlDelete() {
		return sqlDelete;
	}

	public String getSqlSelect() {
		return sqlSelect;
	}

	public String getSqlGetUser() {
		return sqlGetUser;
	}

	public String getSqlGetRequisite() {
		return sqlGetRequisite;
	}

	public String getSqlGetPassword() {
		return sqlGetPassword;
	}

	public String getSqlGetMail() {
		return sqlGetMail;
	}

	public String getSqlExistsUN() {
		return sqlExistsUN;
	}

	public String getSqlExistsEmail() {
		return sqlExistsEmail;
	}

	public String getSqlUpdateProfileImagePath() {
		return sqlUpdateProfileImagePath;
	}

	public String getSqlUpdateUserImagePath() {
		return sqlUpdateUserImagePath;
	}

	public String getSqlUpdateCountryFlagImagePath() {
		return sqlUpdateCountryFlagImagePath;
	}

	public String getSqlUpdateRequisit() {
		return sqlUpdateRequisit;
	}

	public String getSqlGetDescription() {
		return sqlGetDescription;
	}

	public String getSqlUpdateDescription() {
		return sqlUpdateDescription;
	}

	public String getSqlGetUserFlags() {
		return sqlGetUserFlags;
	}

	public String getSqlPutUserFlags() {
		return sqlPutUserFlags;
	}

	private transient AbstractConnectionPool connections; 
	private String dbName = "yi";
	

	
	public DBUserDAO(AbstractConnectionPool connections) {
		setConnections(connections);
	}
	
	public DBUserDAO(AbstractConnectionPool connections, String dbName) {
		setConnections(connections);
		setDbName(dbName);
		
	}
	
	@Override
	public String getDbName() {
		return dbName;
	}

	@Override
	public void setDbName(String dbName) {
		if(dbName==null) dbName="yi"; 
		this.dbName = dbName;
		sqlInsert = "INSERT INTO "+dbName+".userinfo(username, firstname, secondname, emailaddress, passwordhash) VALUES (?,?,?,?,?)";
		sqlUpdate = "UPDATE "+dbName+".userinfo SET username=?, firstname=?, secondname=?, emailaddress=?, passwordhash=? WHERE username=?";
		sqlDelete = "DELETE FROM "+dbName+".userinfo WHERE username=?";
		sqlSelect = "SELECT username, firstname, secondname, emailaddress, passwordhash FROM "+dbName+".userinfo WHERE username=?";
		sqlGetUser = "SELECT username, firstname, secondname, emailaddress FROM "+dbName+".userinfo WHERE username=?"; 
		sqlGetRequisite = "SELECT username, telephone, city, country, profile_image_path, user_image_path, country_flag_image_path, notification_webapp_supported, notification_email_supported FROM "+dbName+".userinfo WHERE username=?";
		sqlGetPassword = "SELECT passwordhash FROM "+dbName+".userinfo WHERE username=?"; 
		sqlGetMail = "SELECT emailaddress FROM "+dbName+".userinfo WHERE username=?";
		sqlExistsUN = "SELECT count(username) FROM "+dbName+".userinfo WHERE username=?";
		sqlExistsEmail = "SELECT count(emailaddress) FROM "+dbName+".userinfo WHERE emailaddress=?";
		sqlUpdateProfileImagePath = "UPDATE "+dbName+".userinfo SET profile_image_path=? WHERE username=?"; 
		sqlUpdateUserImagePath = "UPDATE "+dbName+".userinfo SET user_image_path=? WHERE username=?"; 
		sqlUpdateCountryFlagImagePath = "UPDATE "+dbName+".userinfo SET country_flag_image_path=? WHERE username=?";
		sqlUpdateRequisit = "UPDATE "+dbName+".userinfo SET city=?, country=?, telephone=?, notification_webapp_supported=?, notification_email_supported=? WHERE username=?";
		sqlGetDescription = "SELECT short_description FROM "+dbName+".userinfo WHERE username=?"; 
		sqlUpdateDescription = "UPDATE "+dbName+".userinfo SET short_description = ? WHERE username=?"; 
		sqlGetUserFlags = "SELECT notification_webapp_supported, notification_email_supported, user_session_control FROM "+dbName+".userinfo WHERE username=?";
		sqlPutUserFlags = "UPDATE "+dbName+".userinfo SET notification_webapp_supported=?, notification_email_supported=?, user_session_control=? WHERE username=?"; 
	}
	
	@Override
	public AbstractConnectionPool getConnections() {
		return connections;
	}

	@Override
	public void setConnections(AbstractConnectionPool connections) {
		this.connections = connections;
	}
	
	@Override
	public void insert(UserDTO dto) throws SQLException {
		if(dto == null) throw new NullPointerException(); 
		if(dto.getUser()==null) throw new NullPointerException();
		if(dto.getPassword() == null) throw new NullPointerException(); 
		if(get(dto.getUser().getUsername())!=null) throw new UserDuplicationException();
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(sqlInsert)){
			statement.setString(1, dto.getUser().getUsername());
			statement.setString(2, dto.getUser().getFirstname());
			statement.setString(3, dto.getUser().getSecondname());
			statement.setString(4, dto.getUser().getEmail());
			statement.setString(5, dto.getPassword().getToPasswordRecord());
			statement.execute();
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally{
			connections.checkIn(connection);
		}
	}
	
	@Override
	public void update(String oldUsername, UserDTO neoUser) throws SQLException {
		if(oldUsername==null)  throw new NullPointerException(); 
		if(neoUser==null) throw new NullPointerException();
		if(neoUser.getUser()==null) throw new NullPointerException();
		if(neoUser.getPassword() == null) throw new NullPointerException(); 
		if(get(oldUsername)==null) throw new UserNotFoundException();
		String password = neoUser.getPassword().getPlainPassword(); 
		updatePassword.asUpdateUserpasswordReactionModel().setUsername(oldUsername);
		updatePassword.asUpdateUserpasswordReactionModel().setNeoPassword(password);
		updatePassword.runBefore();
		if(!neoUser.getUser().getUsername().contentEquals(oldUsername)) {
			updateUsername.asUpdateUsernameReactionModel().setOldUsername(oldUsername);
			updateUsername.asUpdateUsernameReactionModel().setNeoUsername(neoUser.getUser().getUsername());
			updateUsername.runBefore();
		}
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(sqlUpdate)){
			statement.setString(1, neoUser.getUser().getUsername());
			statement.setString(2, neoUser.getUser().getFirstname());
			statement.setString(3, neoUser.getUser().getSecondname());
			statement.setString(4, neoUser.getUser().getEmail());
			statement.setString(5, neoUser.getPassword().getToPasswordRecord());
			statement.setString(6, oldUsername);
			statement.execute();
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally {
			connections.checkIn(connection);
		}
		if(!neoUser.getUser().getUsername().contentEquals(oldUsername)) 
			updateUsername.runAfter();
		updatePassword.runAfter();
	}
	
	@Override
	public void delete(String username) throws SQLException {
		if(username==null) throw new NullPointerException(); 
		if(get(username)==null) throw new UserNotFoundException();
		delete.asDeleteUserReactionModel().setUsername(username);
		delete.runBefore();
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(sqlDelete)){
			statement.setString(1, username);
			statement.execute();
			statement.close();
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally{
			connections.checkIn(connection);
		}
		delete.runAfter();
	}
	
	@Override
	public UserDTO getFullDTO(String username) throws SQLException {
		UserDTO dto = get(username); 
		UserRequisit requisit = getRequisit(username); 
		if(dto==null) return dto; 
		dto.setRequisit(requisit);
		dto.setDescription(getDescription(username));
		return dto; 
	}
	
	@Override
	public UserDTO get(String username) throws SQLException {
		if(username==null) throw new NullPointerException(); 
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(sqlSelect)){
			String uname=null;
			String fname=null; 
			String sname=null; 
			String email=null;
			String passwd=null;
			statement.setString(1, username);
			try(ResultSet rs=statement.executeQuery()){
				while(rs.next()) {
					uname = rs.getString("username");
					fname = rs.getString("firstname");
					sname = rs.getString("secondname");
					email = rs.getString("emailaddress");
					passwd = rs.getString("passwordhash");
				}
				if(uname==null) return null;
				if(fname==null) fname="";
				if(sname==null) sname="";
				if(email==null) email="";
				if(passwd==null) passwd="";
				UserInfo ui = new UserInfo(uname,fname,sname,email);
				UserPassword up = new UserPassword(passwd, true);
				UserRequisit ur = new UserRequisit();
				UserDTO dto = new UserDTO(ui,up, ur);
				return dto; 
			}catch(Exception ex) {
				throw new RuntimeException(ex); 
			}
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally{
			connections.checkIn(connection);
		}
	}
	
	@Override
	public UserRequisit getRequisit(String username) throws SQLException{
		if(username==null) throw new NullPointerException(); 
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(sqlGetRequisite)){
				String uname=null;
				String phone=null; 
				String city=null; 
				String country=null;
				String profileImagePath=null;
				String userImagePath=null; 
				String countryFlagImagePath=null; 
				boolean notificationWebappSupport=false; 
				boolean notificationEmailSupport=false; 
				statement.setString(1, username);
				try(ResultSet rs=statement.executeQuery()){
					while(rs.next()) {
						uname  = rs.getString("username");
						phone = rs.getString("telephone");
						city = rs.getString("city");
						country = rs.getString("country");
						profileImagePath = rs.getString("profile_image_path");
						userImagePath = rs.getString("user_image_path"); 
						countryFlagImagePath = rs.getString("country_flag_image_path");
						notificationWebappSupport = rs.getBoolean("notification_webapp_supported");
						notificationEmailSupport = rs.getBoolean("notification_email_supported");
					}
					if(uname==null) return null;
					UserRequisit ui = new UserRequisit(uname);
					ui.setTelephone(phone);
					ui.setCity(city);
					ui.setCountry(country);
					if(profileImagePath!=null) ui.setProfilePicture(new File(UserFileSystemPathConstants.PROFILE_IMAGES, profileImagePath));
					if(userImagePath!=null) ui.setUserPicture(new File(UserFileSystemPathConstants.USER_IMAGES, userImagePath));
					if(countryFlagImagePath!=null) ui.setCountryFlagPicture(new File(UserFileSystemPathConstants.COUNTRY_FLAG_IMAGES, countryFlagImagePath));
					ui.setWebappNotifications(notificationWebappSupport);
					ui.setEmailNotifications(notificationEmailSupport);
					return ui;
			}catch(Exception ex) {
				throw new RuntimeException(ex); 
			}finally{
				connections.checkIn(connection);
			}
		}
	}
	
	@Override
	public UserInfo getUser(String username) throws SQLException {
		if(username==null) throw new NullPointerException(); 
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(sqlGetUser)){
				String uname=null;
				String fname=null; 
				String sname=null; 
				String email=null;
				statement.setString(1, username);
				try(ResultSet rs=statement.executeQuery()){
					while(rs.next()) {
						uname = rs.getString("username");
						fname = rs.getString("firstname");
						sname = rs.getString("secondname");
						email = rs.getString("emailaddress");
					}
					if(uname==null) return null;
					if(fname==null) fname="";
					if(sname==null) sname="";
					if(email==null) email="";
					UserInfo ui = new UserInfo(uname,fname,sname,email);
					return ui; 
			}catch(Exception ex) {
				throw new RuntimeException(ex); 
			}finally{
				connections.checkIn(connection);
			}
		}
	}
	
	@Override
	public UserPassword getPassword(String username) throws SQLException {
		if(username==null) throw new NullPointerException(); 
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(sqlGetPassword)){
				String passwd=null;
				statement.setString(1, username);
				try(ResultSet rs=statement.executeQuery()){
					while(rs.next()) {
						passwd = rs.getString("passwordhash");
					}
					if(passwd==null) return null;
					UserPassword up = new UserPassword(passwd, true);
					return up;
			}catch(Exception ex) {
				throw new RuntimeException(ex); 
			}finally{
				connections.checkIn(connection);
			}
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	@Override
	public String getEmail(String username) throws SQLException {
		if(username==null) throw new NullPointerException(); 
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(sqlGetMail)){
			String email=null;
			statement.setString(1, username);
			try(ResultSet rs=statement.executeQuery()){
				while(rs.next()) {
					email = rs.getString("emailaddress");
				}
				if(email==null) return null;
				return email; 
			}catch(Exception ex) {
				throw new RuntimeException(ex); 
			}finally{
				connections.checkIn(connection);
			}
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	@Override
	public boolean existsUser(String username) throws SQLException {
		boolean exists = false;
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(sqlExistsUN)){
			statement.setString(1, username);
			try(ResultSet rs = statement.executeQuery()){ 
				while(rs.next()) {
					if(rs.getInt(1)>0) exists = true; 
				}
			}
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally {
			connections.checkIn(connection);
		}
		return exists; 
	}
	
	@Override
	public boolean existsEmail(String email) throws SQLException {
		boolean exists = false;
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(sqlExistsEmail)){
			statement.setString(1, email);
			try(ResultSet rs = statement.executeQuery()){ 
				while(rs.next()) {
					if(rs.getInt(1)>0) exists = true; 
				}
			}
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally {
			connections.checkIn(connection);
		}
		return exists; 
	}
	
	@Override
	public void updateProfilePicture(String username, String profilePicturePath) throws SQLException {
		if(username==null)  throw new NullPointerException(); 
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(sqlUpdateProfileImagePath)){
			statement.setString(1, profilePicturePath);
			statement.setString(2, username);
			statement.execute();
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally {
			connections.checkIn(connection);
		}
	}
	
	@Override
	public void updateUserPicture(String username, String userPicturePath) throws SQLException {
		if(username==null)  throw new NullPointerException(); 
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(sqlUpdateUserImagePath)){
			statement.setString(1, userPicturePath);
			statement.setString(2, username);
			statement.execute();
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally {
			connections.checkIn(connection);
		}
	}
	
	@Override
	public void updateCountryFlagPicture(String username, String countryFlagImage) throws SQLException {
		if(username==null)  throw new NullPointerException(); 
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(sqlUpdateCountryFlagImagePath)){
			statement.setString(1, countryFlagImage);
			statement.setString(2, username);
			statement.execute();
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally {
			connections.checkIn(connection);
		}
	}
	
	@Override
	public void updateRequisite(String oldUsername, UserRequisit neoData) throws SQLException {
		if(oldUsername==null)  throw new NullPointerException(); 
		if(neoData==null) throw new NullPointerException(); 
		if(get(oldUsername)==null) throw new UserNotFoundException();
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(sqlUpdateRequisit)){
			statement.setString(1, neoData.getCity());
			statement.setString(2, neoData.getCountry());
			statement.setString(3, neoData.getTelephone());
			statement.setBoolean(4, neoData.isWebappNotifications());
			statement.setBoolean(5, neoData.isEmailNotifications());
			statement.setString(6, oldUsername);
			statement.execute();
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		} finally {
			connections.checkIn(connection);
		}
	}
	
	@Override
	public String getDescription(String username) throws SQLException {
		if(username==null) 		throw new NullPointerException(); 
		if(get(username)==null) throw new UserNotFoundException(); 
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(sqlGetDescription)){
			statement.setString(1, username);
			try(ResultSet resultSet = statement.executeQuery()){
				String description = "";
				while(resultSet.next()) 
					description = resultSet.getString(1); 
				return description; 
			}
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally {
			connections.checkIn(connection);
		}
	}
	
	@Override
	public void setDescription(String username, String description) throws SQLException {
		if(username==null) 		throw new NullPointerException(); 
		if(get(username)==null) throw new UserNotFoundException(); 
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(sqlUpdateDescription)){
			statement.setString(1, description);
			statement.setString(2, username);
			statement.execute();
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally {
			connections.checkIn(connection);
		}
	}
	
	@Override
	public UserFlagsDTO getUserFlags(String username) throws SQLException {
		if(username==null) 		throw new NullPointerException(); 
		if(get(username)==null) throw new UserNotFoundException(); 
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(sqlGetUserFlags)){
			UserFlagsDTO dto = new UserFlagsDTO(); 
			UserFlags flags =  new UserFlags(username);
			boolean found = false; 
			dto.setConfigurations(flags);
			
			statement.setString(1, username);
			try(ResultSet resultSet=statement.executeQuery()){
				while(resultSet.next()) {
					found=true;
					boolean notificationWebAppSupport = resultSet.getBoolean("notification_webapp_supported"); 
					boolean notificationEmailSupport = resultSet.getBoolean("notification_email_supported"); 
					boolean userSessionControlSupport = resultSet.getBoolean("user_session_control"); 
					flags.setEmailNotifications(notificationEmailSupport);
					flags.setWebNotifications(notificationWebAppSupport);
					flags.setUserSessionsControl(userSessionControlSupport);
				}
			}
			if(!found) return null; 
			return dto; 
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}finally {
			connections.checkIn(connection);
		}
	}
	
	@Override
	public void putUserFlags(UserFlagsDTO dto) throws SQLException {
		if(dto==null) throw new NullPointerException("DTO NOT FOUND"); 
		if(dto.getConfigurations()==null) throw new NullPointerException("DATA NOT FOUND");
		if(dto.getConfigurations().getUsername()==null) throw new NullPointerException("USER NOT FOUND");
		if(dto.getConfigurations().getUsername().trim().length()==0) throw new NullPointerException("USER NOT FOUND");
		
		Connection connection = connections.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(sqlPutUserFlags)){
			statement.setBoolean(1, dto.getConfigurations().isWebNotifications());
			statement.setBoolean(2, dto.getConfigurations().isEmailNotifications());
			statement.setBoolean(3, dto.getConfigurations().isUserSessionsControl());
			statement.setString(4, dto.getConfigurations().getUsername());
			statement.execute();
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}finally {
			connections.checkIn(connection);
		}
	}
	
	private ReactionModel<?> updatePassword =  new UpdateUserpasswordReactionModel(); 
	private ReactionModel<?> updateUsername = new UpdateUsernameReactionModel(); 
	private ReactionModel<?> delete = new DeleteUserReactionModel();
	
	{
		updateUsername.asUpdateUsernameReactionModel()
		.setBefore("user.database", 
				new UpdateUsernameRunnable() {
			@Override
			public void run() {
				if(!(connections instanceof RootConnectionPool)) return; 
				MySQLUserDAO dao = new MySQLUserDAO((RootConnectionPool) connections);
				try {
					if(dao.getDBOwner(getNeoUsername())!=null) throw new RuntimeException("WRONG USER RENAME BECAUSE EXISTING DATABASE PROFILE OF NEW NAME");
					if(dao.getDBUser(getNeoUsername())!=null) throw new RuntimeException("WRONG USER RENAME BECAUSE EXISTING DATABASE PROFILE NEW NAME");
				}catch(Exception ex) {
					if(ex instanceof RuntimeException) throw (RuntimeException) ex; 
					throw new RuntimeException(ex); 
				}
			}
		});
		delete.asDeleteUserReactionModel().setBefore("user.database.1", 
			new DeleteUserRunnable(){
				@Override
				public void run() {
					if(!(connections instanceof RootConnectionPool)) return; 
					MySQLUserDAO dao = new MySQLUserDAO((RootConnectionPool) connections);
					try {
						if(!dao.existsDeleteDBUserProcedure()) return; 
						if(!dao.isRealDBUser(getUsername())) return;
						dao.clean(getUsername());
						dao.closeDatabaseUserProfile(getUsername());
					}catch(Exception ex) {
						if(ERROR_REMIX) ex.printStackTrace(); 
					}
			}
		});
		updatePassword.asUpdateUserpasswordReactionModel()
		.setBefore("user.database", 
			new UpdateUserpasswordRunnable() {
				@Override
				public void run() {
					if(!(connections instanceof RootConnectionPool)) return; 
					MySQLUserDAO dao = new MySQLUserDAO((RootConnectionPool) connections);
					try {
						if(!dao.existsPasswordUpdateDBUserProcedure()) return; 
						if(!dao.isRealDBUser(getUsername())) return;
						dao.updatePasswordOfDatabaseUser(getUsername(), getNeoPassword());
					}catch(Exception ex) {
						if(ERROR_REMIX) ex.printStackTrace(); 
					}
				}
			}
		);
		updateUsername.asUpdateUsernameReactionModel()
		.setBefore("user.database", 
				new UpdateUsernameRunnable() {
			@Override
			public void run() {
				if(!(connections instanceof RootConnectionPool)) return; 
				MySQLUserDAO dao = new MySQLUserDAO((RootConnectionPool) connections);
				try {
					if(!dao.existsPasswordUpdateDBUserProcedure()) return; 
					if(!dao.isRealDBUser(getOldUsername())) return;
					if(dao.getDBOwner(getNeoUsername())!=null) return;
					if(dao.getDBUser(getNeoUsername())!=null) return;
					dao.updateUsernameOfDatabaseUser(getOldUsername(), getNeoUsername());
				}catch(Exception ex) {
					if(ERROR_REMIX) ex.printStackTrace(); 
				}
			}
		});
	}

	public ReactionModel<?> getUpdatePassword() {
		return updatePassword;
	}

	public ReactionModel<?> getUpdateUsername() {
		return updateUsername;
	}

	public ReactionModel<?> getDelete() {
		return delete;
	}
}
