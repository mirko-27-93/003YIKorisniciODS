package studiranje.ip.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Очитавање листе дозвољени база података, за које се сматра да имају листу са 
 * табелом userinfo која би требала бити стандардизоване схеме у овим пројектима
 * на схеме IP/YI база података и које имају дата поља.
 * @author mirko
 * @version 1.0
 */
public final class UserinfoDBList {
	private UserinfoDBList() {}
	private static List<String> listOfDatabases = new ArrayList<String>(); 
	public static final boolean ERROR_REMIX = true; 
	
	public static void load() {
		listOfDatabases.clear(); 
		try (Scanner scanner = new Scanner(UserinfoDBList.class.getResourceAsStream("/studiranje/ip/databse/configuration/userinfo.dblist.configuration.txt"))){
			while(scanner.hasNextLine()) {
				listOfDatabases.add(scanner.nextLine()); 
			}
		}
		catch(Exception ex) {
			if(ERROR_REMIX) ex.printStackTrace(); 
		}
	}
	
	static {
		load(); 
	}
	
	public static List<String> getListOfCandidate(){
		return new ArrayList<>(listOfDatabases); 
	}
}
