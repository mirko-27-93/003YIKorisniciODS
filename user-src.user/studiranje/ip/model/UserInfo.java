package studiranje.ip.model;

import java.io.Serializable;

import studiranje.ip.exception.InvalidLiteralException;

/**
 * Класа којом се репрезентује апликациона логика, 
 * основних података за корисника. 
 * @author mirko
 * @version 1.0
 */
public final class UserInfo implements Serializable, Comparable<UserInfo>{
	private static final long serialVersionUID = 3270134651097766806L;
	private String username; 
	private String firstname; 
	private String secondname; 
	private String email; 
	
	public UserInfo(String username, String firstname, String secondname, String email) {
		setUsername(username);
		setFirstname(firstname);
		setSecondname(secondname);
		setEmail(email);
	}
	
	public UserInfo(UserInfo ui) {
		setUsername(ui.username);
		setFirstname(ui.firstname);
		setSecondname(ui.secondname);
		setEmail(ui.email);
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		if(username==null) username = ""; 
		this.username = username;
		validateUsername();
	}

	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firsrtname) {
		if(firsrtname==null) firsrtname = ""; 
		this.firstname = firsrtname;
		validateFirstname();
	}

	public String getSecondname() {
		return secondname;
	}
	
	public void setSecondname(String secondname) {
		if(secondname==null) secondname = ""; 
		this.secondname = secondname;
		validateSecondname();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if(email==null) email = ""; 
		this.email = email;
		validateEmail();
	}

	public String originalString() {
		return super.toString(); 
	}
	
	public int originalCode() {
		return super.hashCode(); 
	}
	
	@Override 
	public int hashCode() {
		return super.hashCode(); 
	}
	
	@Override 
	public boolean equals(Object o) {
		if(o instanceof UserInfo) {
			UserInfo ui = (UserInfo) o; 
			return username.contentEquals(ui.username); 
		}
		return false; 
	}
	
	public boolean fullEquals(UserInfo ui) {
		if(!ui.username.contentEquals(ui.username))     return false; 
		if(!ui.email.contentEquals(ui.email))           return false; 
		if(!ui.firstname.contentEquals(ui.firstname)) return false; 
		if(!ui.secondname.contentEquals(secondname))    return false; 
		return true; 		
	}
	
	@Override
	public int compareTo(UserInfo o) {
		return username.compareTo(o.username);
	}
	
	@Override
	public String toString() {
		return username;
	}
	
	public void validateUsername() {
		if(username.trim().length()==0) throw new InvalidLiteralException(); 
		char first = username.charAt(0); 
		if(!Character.isAlphabetic(first)) throw new InvalidLiteralException(); 
		for(char c: username.toCharArray()) {
			if(Character.isAlphabetic(c)) continue;
			if(Character.isDigit(c)) continue;
			if(c=='_') continue;
			if(c=='-') continue;
			if(c=='.') continue;
			throw new InvalidLiteralException();
		}
	}
	
	public void validateFirstname() {
		if(firstname.trim().length()==0) throw new InvalidLiteralException(); 
		char first = firstname.charAt(0); 
		if(!Character.isAlphabetic(first)) throw new InvalidLiteralException(); 
		for(char c: firstname.toCharArray()) {
			if(Character.isAlphabetic(c)) continue;
			if(Character.isDigit(c)) continue;
			if(c=='_') continue;
			if(c=='-') continue;
			if(c=='.') continue;
			throw new InvalidLiteralException();
		}
	}
	
	public void validateSecondname() {
		if(secondname.trim().length()==0) throw new InvalidLiteralException(); 
		char first = secondname.charAt(0); 
		if(!Character.isAlphabetic(first)) throw new InvalidLiteralException(); 
		for(char c: secondname.toCharArray()) {
			if(Character.isAlphabetic(c)) continue;
			if(Character.isDigit(c)) continue;
			if(c=='_') continue;
			if(c=='-') continue;
			if(c=='.') continue;
			throw new InvalidLiteralException();
		}
	}
	
	public void validateEmail() {
		String emailParts[] = email.split("@");
		if(emailParts[0].trim().length()==0) throw new InvalidLiteralException(); 
		char first = emailParts[0].charAt(0); 
		if(!Character.isAlphabetic(first)) throw new InvalidLiteralException(); 
		for(char c: emailParts[0].toCharArray()) {
			if(Character.isAlphabetic(c)) continue;
			if(Character.isDigit(c)) continue;
			if(c=='_') continue;
			if(c=='-') continue;
			if(c=='.') continue;
			throw new InvalidLiteralException();
		}
		if(emailParts[1].trim().length()==0) throw new InvalidLiteralException(); 
		first = emailParts[1].charAt(0); 
		if(!Character.isAlphabetic(first)) throw new InvalidLiteralException(); 
		for(char c: emailParts[1].toCharArray()) {
			if(Character.isAlphabetic(c)) continue;
			if(Character.isDigit(c)) continue;
			if(c=='_') continue;
			if(c=='-') continue;
			if(c=='.') continue;
			throw new InvalidLiteralException();
		}
	}
}
