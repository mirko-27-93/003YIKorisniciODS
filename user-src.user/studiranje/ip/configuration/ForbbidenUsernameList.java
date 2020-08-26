package studiranje.ip.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Забрањена корисничка имена (користе се за административности про базо података и 
 * апликацији). 
 * @author mirko
 * @version 1.0
 */
public class ForbbidenUsernameList {
	private List<String> forbiddenUsernames = new ArrayList<>();

	public ForbbidenUsernameList() {
		load(); 
	}
	
	public synchronized List<String> getForbiddenUsernames() {
		return new ArrayList<>(forbiddenUsernames);
	}
	
	public synchronized void load() {
		try(Scanner scanner = new Scanner(getClass().getResourceAsStream("/studiranje/ip/resource/forbidden_usernames.txt"))){
			forbiddenUsernames.clear();
			while(scanner.hasNextLine()) 
				forbiddenUsernames.add(scanner.nextLine()); 
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
