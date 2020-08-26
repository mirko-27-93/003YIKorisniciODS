package studiranje.ip.engine.model;

/**
 * Уопштење за објекат извора података. 
 * @author mirko
 * @version 1.0
 */
public interface DataSourceModel {
	public default DataSourceRootModel asDataSourceRootModel() {
		if(this instanceof DataSourceRootModel) {
			return (DataSourceRootModel) this; 
		}
		return null; 
	}
	public default DataSourceUserModel asUserSourceRootModel() {
		if(this instanceof DataSourceUserModel) {
			return (DataSourceUserModel) this; 
		}
		return null; 
	}
}
