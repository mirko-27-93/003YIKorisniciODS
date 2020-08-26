package studiranje.ip.data;

import java.sql.SQLException;

import studiranje.ip.database.UserDTO;
import studiranje.ip.database.UserFlagsDTO;
import studiranje.ip.engine.model.DataSourceUserModel;
import studiranje.ip.model.UserInfo;
import studiranje.ip.model.UserPassword;
import studiranje.ip.model.UserRequisit;

/**
 * Уопштење корисничког адаптера за корисничке основне податаке, а према базо података. 
 * @author mirko
 * @version 1.0
 */
public abstract class DBAbstractUserDAO implements DataSourceUserModel{
	public abstract AbstractConnectionPool getConnections();
	public abstract void setConnections(AbstractConnectionPool connections); 

	@Override public abstract void insert(UserDTO dto) throws SQLException; 
	@Override public abstract void update(String oldUsername, UserDTO neoUser) throws SQLException; 
	@Override public abstract void delete(String username) throws SQLException;
	@Override public abstract UserDTO getFullDTO(String username) throws SQLException; 
	
	@Override public abstract UserDTO get(String username) throws SQLException;
	@Override public abstract UserRequisit getRequisit(String username) throws SQLException; 
	@Override public abstract UserInfo getUser(String username) throws SQLException;
	@Override public abstract UserPassword getPassword(String username) throws SQLException;
	@Override public abstract String getEmail(String username) throws SQLException; 

	@Override public abstract boolean existsUser(String username) throws SQLException;
	@Override public abstract boolean existsEmail(String email) throws SQLException; 
	
	@Override public abstract void updateProfilePicture(String username, String profilePicturePath) throws SQLException; 
	@Override public abstract void updateUserPicture(String username, String userPicturePath) throws SQLException;
	@Override public abstract void updateCountryFlagPicture(String username, String countryFlagImage) throws SQLException;
	@Override public abstract void updateRequisite(String oldUsername, UserRequisit neoData) throws SQLException; 
	
	@Override public abstract  String getDescription(String username) throws SQLException;	
	@Override public abstract void setDescription(String username, String description) throws SQLException; 
	@Override public abstract UserFlagsDTO getUserFlags(String username) throws SQLException;
	@Override public abstract void putUserFlags(UserFlagsDTO dto) throws SQLException; 
	
	public  DBDatabaseSwappable asDBDatabaseSwappable() {
		if(this instanceof DBDatabaseSwappable) {
			return (DBDatabaseSwappable) this;
		}
		return null; 
	}
}
