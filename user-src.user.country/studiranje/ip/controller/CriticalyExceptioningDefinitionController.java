package studiranje.ip.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Управљање корисничким филтером, односно код апликације 
 * у који је уграђен овај код. 
 * @author mirko
 * @version 1.0
 */
public class CriticalyExceptioningDefinitionController{
	public static final String FILTER_FILE_RESOURCE = "/studiranje/ip/controller/resource/app_countries.list.txt";
	private ArrayList<String> loaded = new ArrayList<>(); 
	
	public void load() {
		ArrayList<String> readed = new ArrayList<>(); 
		try(Scanner scanner=new Scanner(getClass().getResourceAsStream(FILTER_FILE_RESOURCE))){
			while(scanner.hasNext()) {
				readed.add(scanner.nextLine()); 
			}
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
		loaded = readed; 
	}
	
	{load();}
	
	public boolean fileter(List<String> countries) {
		return loaded.containsAll(countries);
	}
	
	public boolean filter(String country) {
		return loaded.contains(country); 
	}
}
