package studiranje.ip.servlet;

/**
 * Операције које се односе на рад са корисника, а могу се наћи 
 * на разрешавању при веб, апликацији. 
 * @author mirko
 * @version 1.0
 */
public enum UserResolveOperation {
	LOGIN, LOGOUT, REGISTER, DELETE, UPDATE, LOGOUT_ALL_SESSIONS_FOR_USER
}
