package studiranje.ip.data.event;

/**
 * Реакциони модел за пропагацију параметара за измјену корисничког имена. 
 * @author mirko
 * @version 1.0
 */
public class UpdateUsernameReactionModel extends ReactionModel<UpdateUsernameRunnable>{
	private String oldUsername = ""; 
	private String neoUsername = "";
	
	public String getOldUsername() {
		return oldUsername;
	}
	public void setOldUsername(String oldUsername) {
		if(oldUsername==null) oldUsername = ""; 
		this.oldUsername = oldUsername;
		for(String id : getAfterActions()) {
			UpdateUsernameRunnable rnb = getAfter(id);  
			if(rnb==null) continue; 
			rnb.setOldUsername(oldUsername);;
		}
		for(String id : getBeforeActions()) {
			UpdateUsernameRunnable rnb = getBefore(id);  
			if(rnb==null) continue; 
			rnb.setOldUsername(oldUsername);
		}
	}
	
	public String getNeoUsername() {
		return neoUsername;
	}
	public void setNeoUsername(String neoUsername) {
		if(neoUsername==null) neoUsername = ""; 
		this.neoUsername = neoUsername;
		for(String id : getAfterActions()) {
			UpdateUsernameRunnable rnb = getAfter(id);  
			if(rnb==null) continue; 
			rnb.setNeoUsername(neoUsername);;
		}
		for(String id : getBeforeActions()) {
			UpdateUsernameRunnable rnb = getBefore(id);  
			if(rnb==null) continue; 
			rnb.setNeoUsername(neoUsername);
		}
	}
	
	
}
