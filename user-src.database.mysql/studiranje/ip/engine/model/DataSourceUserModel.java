package studiranje.ip.engine.model;

import java.sql.SQLException;

import studiranje.ip.data.DBAbstractUserDAO;
import studiranje.ip.database.UserDTO;
import studiranje.ip.database.UserFlagsDTO;
import studiranje.ip.model.UserInfo;
import studiranje.ip.model.UserPassword;
import studiranje.ip.model.UserRequisit;

/**
 * Уопштење за изворе података који имитирају операције у вези са 
 * табелом корисника, а при  релационој бази података осносно сличним 
 * организацијама података. 
 * @author mirko
 * @version 1.0
 */
public interface DataSourceUserModel {
	public default DBAbstractUserDAO asDBAbstractUserDAO() {
		if(this instanceof DBAbstractUserDAO) {
			return (DBAbstractUserDAO) this;
		}
		return null; 
	}
	
	public abstract void insert(UserDTO dto) throws SQLException; 
	public abstract void update(String oldUsername, UserDTO neoUser) throws SQLException; 
	public abstract void delete(String username) throws SQLException;
	public abstract UserDTO getFullDTO(String username) throws SQLException; 
	
	public abstract UserDTO get(String username) throws SQLException;
	public abstract UserRequisit getRequisit(String username) throws SQLException; 
	public abstract UserInfo getUser(String username) throws SQLException;
	public abstract UserPassword getPassword(String username) throws SQLException;
	public abstract String getEmail(String username) throws SQLException; 

	public abstract boolean existsUser(String username) throws SQLException;
	public abstract boolean existsEmail(String email) throws SQLException; 
	
	public abstract void updateProfilePicture(String username, String profilePicturePath) throws SQLException; 
	public abstract void updateUserPicture(String username, String userPicturePath) throws SQLException;
	public abstract void updateCountryFlagPicture(String username, String countryFlagImage) throws SQLException;
	public abstract void updateRequisite(String oldUsername, UserRequisit neoData) throws SQLException;
	
	public abstract  String getDescription(String username) throws SQLException;	
	public abstract void setDescription(String username, String description) throws SQLException; 
	public abstract UserFlagsDTO getUserFlags(String username) throws SQLException;
	public abstract void putUserFlags(UserFlagsDTO dto) throws SQLException; 
}
