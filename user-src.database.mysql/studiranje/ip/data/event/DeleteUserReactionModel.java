package studiranje.ip.data.event;

/**
 * Реакциони модел са параметризацијом и пропагацијом за потребе брисања. 
 * @author mirko
 * @version 1.0
 */
public class DeleteUserReactionModel extends ReactionModel<DeleteUserRunnable>{
	private String username = "";

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		if(username==null) username = ""; 
		this.username = username;
		for(String id : getAfterActions()) {
			DeleteUserRunnable rnb = getAfter(id);  
			if(rnb==null) continue; 
			rnb.setUsername(username);
		}
		for(String id : getBeforeActions()) {
			DeleteUserRunnable rnb = getBefore(id);  
			if(rnb==null) continue; 
			rnb.setUsername(username);
		}
	} 
}
