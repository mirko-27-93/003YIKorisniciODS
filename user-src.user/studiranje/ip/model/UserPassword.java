package studiranje.ip.model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.tomcat.util.codec.binary.Base64;

import studiranje.ip.exception.InvalidPasswordFormatException;

/**
 * Корисничка лозинка са референцом на корисника. 
 * Уствари информације о кориснику са корисничком лозинком. 
 * @author mirko
 * @version 1.0
 */
public final class UserPassword implements Serializable{
	private static final long serialVersionUID = -4775999412162436243L;
	private String passwordHash; 
	private byte[] passwordSalt; 
	private String plainPassword = ""; 
	
	public UserPassword(String password) 
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		setPassword(password);
	}
	
	public UserPassword(String password, boolean isRecord) 
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if(!isRecord) setPassword(password);
		else setFromPasswordRecord(password);
	}
	
	public UserPassword(UserPassword password) {
		this.passwordHash = password.passwordHash; 
		this.passwordSalt = password.passwordSalt; 
	}
	public String getPasswordSaltStringized() throws UnsupportedEncodingException {
		return Base64.encodeBase64String(passwordSalt);
	}
	public void setPasswordSalt(byte[] salt) {
		this.passwordSalt = salt; 
	}
	public void setPasswordSaltFromString(String salt) throws UnsupportedEncodingException {
		this.passwordSalt = salt.getBytes("UTF-8");
	}
	
	public String getToPasswordRecord() throws UnsupportedEncodingException {
		return getPasswordSaltStringized()+"$"+passwordHash;
	}
	public void setFromPasswordRecord(String passwordRec) {
		String[] passPart = passwordRec.split("\\$"); 
		passwordSalt = Base64.decodeBase64(passPart[0]);
		passwordHash = passPart[1];
	}
	
	public byte[] getPasswordSalt() {
		return passwordSalt;
	}
	public String getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	public void setUserInfo(UserInfo userInfo) {
		if(userInfo==null) throw new InvalidPasswordFormatException();
	} 
	public void setPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if(password.trim().length()<8) throw new InvalidPasswordFormatException();
		int digit = 0; 
		int lower = 0;
		int upper = 0; 
		for(char ch: password.toCharArray()) {
			if(Character.isDigit(ch))     digit++;
			else if(Character.isUpperCase(ch)) upper++; 
			else if(Character.isLowerCase(ch)) lower++;
			else throw new InvalidPasswordFormatException();
		}
		if(digit==0 || lower==0 || upper==0) throw new InvalidPasswordFormatException();
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		passwordSalt = salt;
		md.update(passwordSalt);
		byte[] bytes = md.digest(password.getBytes("UTF-8"));
		for(int i=0; i<1000; i++)
			bytes = md.digest(bytes);
		passwordHash = Base64.encodeBase64String(bytes); 
	}
	public boolean checkPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if(password.trim().length()<8) return false;
		int digit = 0;
		int lower = 0;
		int upper = 0;
		for(char ch: password.toCharArray()) {
			if(Character.isDigit(ch))          digit++;
			else if(Character.isUpperCase(ch)) upper++; 
			else if(Character.isLowerCase(ch)) lower++;
			else return false; 
		}
		if(digit==0 || lower==0 || upper==0) throw new InvalidPasswordFormatException();
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(passwordSalt);
		byte[] bytes = md.digest(password.getBytes("UTF-8"));
		for(int i=0; i<1000; i++)
			bytes = md.digest(bytes);
		String passwordHash = Base64.encodeBase64String(bytes);
		return passwordHash.contentEquals(this.passwordHash);
	}
	
	public int originalCode() {
		return super.hashCode();
	}
	
	@Override
	public int hashCode() {
		return passwordHash.hashCode();
	}
	
	public String originalString() {
		return super.toString();
	}
	
	@Override 
	public String toString() {
		return passwordHash; 
	}
	
	@Override
	public boolean equals(Object object) {
		if(object instanceof UserPassword) {
			UserPassword up = (UserPassword) object;
			return passwordHash.contentEquals(up.passwordHash);
		}
		return false;
	}

	public String getPlainPassword() {
		return plainPassword;
	}

	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}
}
