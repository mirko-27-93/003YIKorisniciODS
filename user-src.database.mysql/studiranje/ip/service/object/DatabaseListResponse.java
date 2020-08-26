package studiranje.ip.service.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Одговор које сервиси враћају за захтјев листања база података. 
 * @author mirko
 * @version 1.0
 */
public class DatabaseListResponse implements Serializable{

	private static final long serialVersionUID = 589031957406562682L;
	private List<String> databases = new ArrayList<>();

	public List<String> getDatabases() {
		return databases;
	}

	public List<String> liveDatabases(){
		return databases;
	}
	
	public List<String> applyDatabases(){
		return new ArrayList<String>();
	}
	
	public List<String> cloneDatabases(){
		return new ArrayList<String>(); 
	}
	
	public void setDatabases(List<String> databases) {
		this.databases = databases;
	} 
	
	public void aliasDatabases(List<String> databases) {
		this.databases = databases;
	}
	
	public void applyDatabases(List<String> databases) {
		this.databases = new ArrayList<>(databases);
	}
	
	public void cloneDatabases(List<String> databases) {
		this.databases = new ArrayList<>(databases);
	}
}
