package studiranje.ip.data.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Основни модел за реактивности.
 * @author mirko
 * @version 1.0
 */
public class ReactionModel<T extends Runnable> {
	private ArrayList<String> afterAction = new ArrayList<>(); 
	private ArrayList<String> beforeAction = new ArrayList<>();
	
	private String id = ""; 
	private T action = null; 
	
	private HashMap<String, T> afterActionMap = new HashMap<>(); 
	private HashMap<String, T> beforeActionMap = new HashMap<>(); 
	
	public synchronized void setBefore(String id, T action) { 
		if(!beforeActionMap.containsKey(id)) beforeAction.add(id); 
		beforeActionMap.put(id, action); 
	}
	
	public synchronized T getBefore(String id) {
		return beforeActionMap.get(id); 
	}
	
	public synchronized void removeBefore(String id) {
		beforeActionMap.remove(id); 
		beforeAction.remove(id); 
	}
	
	public synchronized void runBefore() {
		for(String a: beforeAction)
			if(beforeActionMap.get(a)!=null)
				beforeActionMap.get(a).run(); 
	}
	
	public synchronized void setAfter(String id, T action) {
		if(!afterActionMap.containsKey(id)) afterAction.add(id); 
		afterActionMap.put(id, action); 
	}
	
	public synchronized T getAfter(String id) {
		return afterActionMap.get(id); 
	}
	
	public synchronized void removeAfter(String id) {
		afterActionMap.remove(id); 
		afterAction.remove(id); 
	}
	
	public synchronized void runAfter() {
		for(String a: afterAction)
			if(afterActionMap.get(a)!=null)
				afterActionMap.get(a).run(); 
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		if(id==null) id = ""; 
		this.id = id;
	}

	public T getAction() {
		return action;
	}

	public void setAction(T action) { 
		this.action = action;
	}
	
	public synchronized void run() {
		runBefore(); 
		if(action!=null)
			action.run();
		runAfter(); 
	}
	
	public synchronized List<String> getAfterActions(){
		return new ArrayList<>(afterAction); 
	}
	
	public synchronized List<String> getBeforeActions(){
		return new ArrayList<>(beforeAction);
	}
	
	public DeleteUserReactionModel asDeleteUserReactionModel() {
		if(this instanceof DeleteUserReactionModel) return (DeleteUserReactionModel) this;
		return null;
	}
	
	public UpdateUsernameReactionModel asUpdateUsernameReactionModel() {
		if(this instanceof UpdateUsernameReactionModel) return (UpdateUsernameReactionModel) this;
		return null;
	}
	
	public UpdateUserpasswordReactionModel asUpdateUserpasswordReactionModel() {
		if(this instanceof UpdateUserpasswordReactionModel) return (UpdateUserpasswordReactionModel) this; 
		return null;
	}
	
	public boolean isDeleteUserReactionModel() {
		return asDeleteUserReactionModel()!=null; 
	}
	
	public boolean isUpdateUsernameReactionModel() {
		return asUpdateUsernameReactionModel()!=null; 
	}
	
	public boolean isUpdateUserpasswordReactionModel() {
		return asUpdateUserpasswordReactionModel()!=null; 
	}
}
