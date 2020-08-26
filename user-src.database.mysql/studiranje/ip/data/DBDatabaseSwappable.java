package studiranje.ip.data;

/**
 * Објекти којима се може мијењати име базе података. 
 * Нпр. за функционалности над истом табелом под разним базама података. 
 * @author mirko
 * @version 1.0
 */
public interface DBDatabaseSwappable {
	public String getDbName(); 
	public void setDbName(String dbName);
}
