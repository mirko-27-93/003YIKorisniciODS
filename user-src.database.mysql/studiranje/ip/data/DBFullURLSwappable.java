package studiranje.ip.data;

import java.net.URI;

/**
 * Интерфејс који омогућава изградњу објеката са потпуно прилагодивим/подесивим 
 * или измјењеивим параметрима за базу података. 
 * @author mirko
 * @version 1.0
 */
public interface DBFullURLSwappable extends DBDatabaseSwappable{
	public String geDBtHostURL(); 
	public void setDBHostURL(String dbHostURL); 
	public String getDatabaseUser(); 
	public void setDatabaseUser(String dbRootUser); 
	public String getDatabasePassword(); 
	public void setDatabasePassword(String dbRootPasswd); 
	
	public default boolean swappableDBCredentialsEquals(DBFullURLSwappable o) {
		if(!getDbName().contentEquals(o.getDbName())) return false;
		if(!geDBtHostURL().contentEquals(o.geDBtHostURL())) return false;
		if(!getDatabaseUser().contentEquals(o.getDatabaseUser())) return false;  
		return true;
	}
	
	public default boolean swappableDBFullEquals( DBFullURLSwappable o) {
		if(!getDbName().contentEquals(o.getDbName())) return false;
		if(!geDBtHostURL().contentEquals(o.geDBtHostURL())) return false;
		if(!getDatabaseUser().contentEquals(o.getDatabaseUser())) return false;
		if(!getDatabasePassword().contentEquals(o.getDatabasePassword())) return false; 
		return true; 
	}
	
	public default URI getFullDatabaseAccessURI() {
		URI uri = URI.create(geDBtHostURL());
		URI fullURI = URI.create(uri.getScheme()+"://"+getDatabaseUser()+":"+getDatabasePassword()+"@"+uri.getHost()+":"+uri.getPort()); 
		return fullURI; 
	}
	
	public String getFullDatabaseInclassURI();
}
