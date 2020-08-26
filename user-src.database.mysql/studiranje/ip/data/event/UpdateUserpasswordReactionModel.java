package studiranje.ip.data.event;

/**
 * Реакциони модел са пријемом и пропагацијом параметара за потребе 
 * постављања лозинке на кориснички профил базе података, односно 
 * уопштено. 
 * @author mirko
 * @version 1.0
 */
public class UpdateUserpasswordReactionModel extends ReactionModel<UpdateUserpasswordRunnable>{
	private String username = ""; 
	private String neoPassword = "";
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		if(username==null) username = ""; 
		this.username = username;
		for(String id : getAfterActions()) {
			UpdateUserpasswordRunnable rnb = getAfter(id);  
			if(rnb==null) continue; 
			rnb.setUsername(username);
		}
		for(String id : getBeforeActions()) {
			UpdateUserpasswordRunnable rnb = getBefore(id);  
			if(rnb==null) continue; 
			rnb.setUsername(username);
		}
	}
	public String getNeoPassword() {
		return neoPassword;
	}
	public void setNeoPassword(String neoPassword) {
		if(neoPassword==null) neoPassword = ""; 
		this.neoPassword = neoPassword;
		for(String id : getAfterActions()) {
			UpdateUserpasswordRunnable rnb = getAfter(id);  
			if(rnb==null) continue; 
			rnb.setNeoPassword(neoPassword);
		}
		for(String id : getBeforeActions()) {
			UpdateUserpasswordRunnable rnb = getBefore(id);  
			if(rnb==null) continue; 
			rnb.setNeoPassword(neoPassword);
		}
	} 	
}
