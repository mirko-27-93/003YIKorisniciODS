package studiranje.ip.service.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import studiranje.ip.database.model.DBUserData;

/**
 * Објекат који се односи на одговор на захтијев за листу база података. 
 * @author mirko
 * @version 1.0
 */
public class UserListResponse implements Serializable{
	private static final long serialVersionUID = -348486996382739678L;
	
	private List<DBUserData> users = new ArrayList<>();

	public List<DBUserData> liveUsers() {
		return users;
	}
	
	public synchronized List<DBUserData> copyUsers() {
		return new ArrayList<>(users);
	}
	
	
	public synchronized List<DBUserData> cloneUsers() {
		ArrayList<DBUserData> users = new ArrayList<>(); 
		for(DBUserData user: this.users) 
			users.add(user.clone()); 
		return users;
	}
	
	public List<DBUserData> getUsers() {
		return users;
	}

	public void setUsers(List<DBUserData> users) {
		this.users = users;
	} 
	
	public void aliasUsers(List<DBUserData> users) {
		this.users =  users; 
	}
	
	public void applyUsers(List<DBUserData> users) {
		this.users = new ArrayList<>();
	}
	
	public synchronized void flatUsers(List<DBUserData> users) {
		this.users = new ArrayList<>(); 
		for(DBUserData data: users) 
			this.users.add(data.clone()); 
	}
}
