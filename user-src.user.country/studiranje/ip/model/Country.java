package studiranje.ip.model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Модел података о држави или народности. 
 * @author mirko
 * @version 1.0
 */
public final class Country implements Serializable, Comparable<Country>{
	private static final long serialVersionUID = 8024967270022061444L;
	
	private String idCode; 
	private String a2c; 
	private String a3c; 
	private String name; 
	private List<String> ccs = new ArrayList<>(); 
	private List<String> tlds = new ArrayList<>();
	private File flag; 
	
	
	public Country(String countryId) {
		if(countryId==null) throw new NullPointerException();
		idCode = countryId; 
	}
	
	public String getIdCode() {
		return idCode;
	}
	
	public String getA2c() {
		return a2c;
	}
	public void setA2c(String a2c) {
		this.a2c = a2c;
	}
	
	public String getA3c() {
		return a3c;
	}
	public void setA3c(String a3c) {
		this.a3c = a3c;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public List<String> getCcs() {
		return ccs;
	}
	public void setCcs(List<String> ccs) {
		this.ccs = ccs;
	}
	
	public List<String> getTlds() {
		return tlds;
	}
	public void setTlds(List<String> tlds) {
		this.tlds = tlds;
	}
	
	public final String originalString() {
		return super.toString(); 
	}
	
	public final int originalCode() {
		return super.hashCode();
	}
	
	@Override
	public String toString() {
		return idCode;
	}
	
	@Override
	public int hashCode() {
		return idCode.hashCode(); 
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Country) {
			Country country = (Country) obj;
			return idCode.contentEquals(country.idCode);
		}
		return false; 
	}
	
	@Override
	public int compareTo(Country o) {
		return idCode.compareTo(o.idCode);
	}

	public File getFlag() {
		return flag;
	}

	public void setFlag(File flag) {
		this.flag = flag;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}
}
