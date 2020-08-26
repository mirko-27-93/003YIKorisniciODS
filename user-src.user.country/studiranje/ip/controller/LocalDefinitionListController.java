package studiranje.ip.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import studiranje.ip.lang.UserFileSystemPathConstants;

/**
 * Контролер листе када је у питању локално архивирана листа. 
 * @author mirko
 * @version 1.0
 */
public class LocalDefinitionListController implements CountryDefinitionListController{	
	private static final long serialVersionUID = -2090810696765726512L;
	public static final boolean ERROR_REMIX = true; 
	
	private File rootInfoDirForCountries = CountryDefinitionListController.DIR_OF_LCD;
	private File rootInfoFileForCountries = CountryDefinitionListController.LCD_LIST; 
	
	
	
	public File getRootInfoDirForCountries() {
		return rootInfoDirForCountries;
	}

	public void setRootInfoDirForCountries(File rootInfoDirForCountries) {
		this.rootInfoDirForCountries = rootInfoDirForCountries;
	}

	public File getRootInfoFileForCountries() {
		return rootInfoFileForCountries;
	}

	public void setRootInfoFileForCountries(File rootInfoFileForCountries) {
		this.rootInfoFileForCountries = rootInfoFileForCountries;
	}

	{
		if(!rootInfoDirForCountries.exists())
			rootInfoDirForCountries.mkdir(); 
		if(!rootInfoFileForCountries.exists()) {
			try(FileOutputStream fos = new FileOutputStream(rootInfoFileForCountries)){
				fos.write("{}".getBytes("UTF-8"));
			}catch(RuntimeException ex) {
				throw ex; 
			}catch(Exception ex) {
				throw new RuntimeException(ex);
			}
		}
	}
	
	private HashMap<String, String> countriesMap     = new HashMap<>(); 
	private HashMap<String, File> countriesImagesMap = new HashMap<>(); 
	private HashMap<String, String> a2cMap = new HashMap<>(); 
	private HashMap<String, String> a3cMap = new HashMap<>(); 
	private HashMap<String, List<String>> ccMap = new HashMap<>();
	private HashMap<String, List<String>> tldMap = new HashMap<>(); 
	
	public HashMap<String, String> a2cMap(){
		return new HashMap<>(a2cMap); 
	}
	
	public HashMap<String, String> liveA2CMap(){
		return a2cMap; 
	}
	
	public HashMap<String, String> a3cMap(){
		return new HashMap<>(a3cMap); 
	}
	
	public HashMap<String, String> liveA3CMap(){
		return a3cMap; 
	}
	
	
	public HashMap<String, List<String>> ccMap(){
		return new HashMap<>(ccMap); 
	}
	
	public HashMap<String, List<String>> liveCCMap(){
		return ccMap;
	}
	
	public HashMap<String, List<String>> tldMap(){
		return new HashMap<>(tldMap); 
	}
	
	public HashMap<String, List<String>> liveTLDMap(){
		return tldMap; 
	}
	
	public void addA2C(String id, String a2c) {
		a2cMap.put(id, a2c);
	}
	
	public void removeA2C(String id) {
		a2cMap.remove(id); 
	}
	
	public void addA3C(String id, String a3c) {
		a3cMap.put(id, a3c); 
	}
	
	public void removeA3C(String id) {
		a3cMap.remove(id);
	}
	
	public void addCCs(String id, String ... ccs) {
		if(ccs.length==0) return; 
		if(ccMap.get(id)==null) {
			ccMap.put(id, new ArrayList<>(Arrays.asList(ccs))); 
		}else {
			ccMap.get(id).addAll(new ArrayList<>(Arrays.asList(ccs))); 
		}
	}
	
	public void addCCs(String id, Collection<String> ccs) {
		if(ccs.size()==0) return; 
		if(ccMap.get(id)==null) {
			ccMap.put(id, new ArrayList<>(ccs)); 
		}else {
			ccMap.get(id).addAll(new ArrayList<>(ccs)); 
		}
	}
	
	public void setCCs(String id, String ... ccs) {
		if(ccs.length==0) return; 
		if(ccMap.get(id)==null) {
			ccMap.put(id, new ArrayList<>(Arrays.asList(ccs))); 
		}else {
			ccMap.get(id).clear();
			ccMap.get(id).addAll(new ArrayList<>(Arrays.asList(ccs))); 
		}
	}
	
	public void setCCs(String id, Collection<String> ccs) {
		if(ccs.size()==0) return; 
		if(ccMap.get(id)==null) {
			ccMap.put(id, new ArrayList<>(ccs)); 
		}else {
			ccMap.get(id).clear();
			ccMap.get(id).addAll(new ArrayList<>(ccs)); 
		}
	}
	
	public void removeCCs(String id, String ... ccs) {
		if(ccMap.get(id)==null) return; 
		ccMap.get(id).removeAll(Arrays.asList(ccs));
		if(ccMap.get(id).isEmpty()) ccMap.remove(id);
	}
	
	public void removeCCs(String id, Collection<String> ccs) {
		if(ccMap.get(id)==null) return; 
		ccMap.get(id).removeAll(ccs);
		if(ccMap.get(id).isEmpty()) ccMap.remove(id);
	}
	
	public void removeCCs(String id) {
		ccMap.remove(id); 
	}
	
	public void addTLDs(String id, String ... tlds) {
		if(tlds.length==0) return; 
		if(tldMap.get(id)==null) {
			tldMap.put(id, new ArrayList<>(Arrays.asList(tlds))); 
		}else {
			tldMap.get(id).addAll(new ArrayList<>(Arrays.asList(tlds))); 
		}
	}
	
	public void addTLDs(String id, Collection<String> tlds) {
		if(tlds.size()==0) return; 
		if(tldMap.get(id)==null) {
			tldMap.put(id, new ArrayList<>(tlds)); 
		}else {
			tldMap.get(id).addAll(new ArrayList<>(tlds)); 
		}
	}
	
	public void setTLDs(String id, String ... tlds) {
		if(tlds.length==0) return; 
		if(tldMap.get(id)==null) {
			tldMap.put(id, new ArrayList<>(Arrays.asList(tlds))); 
		}else {
			tldMap.get(id).clear();
			tldMap.get(id).addAll(new ArrayList<>(Arrays.asList(tlds))); 
		}
	}
	
	public void setTLDs(String id, Collection<String> tlds) {
		if(tlds.size()==0) return; 
		if(tldMap.get(id)==null) {
			tldMap.put(id, new ArrayList<>(tlds)); 
		}else {
			tldMap.get(id).clear();
			tldMap.get(id).addAll(new ArrayList<>(tlds)); 
		}
	}
	
	public void removeTLDs(String id, String ... tlds) {
		if(tldMap.get(id)==null) return; 
		tldMap.get(id).removeAll(Arrays.asList(tlds));
		if(tldMap.get(id).isEmpty()) tldMap.remove(id);
	}
	
	public void removTLDs(String id, Collection<String> tlds) {
		if(tldMap.get(id)==null) return; 
		tldMap.get(id).removeAll(tlds);
		if(tldMap.get(id).isEmpty()) tldMap.remove(id);
	}
	
	public void removeTLDs(String id) {
		tldMap.remove(id); 
	}
	
	@Override
	public synchronized Map<String, String> countryMap() {
		return new HashMap<>(countriesMap);
	}

	@Override
	public synchronized Map<String, File> countryImageMap() {
		return new HashMap<>(countriesImagesMap);
	}

	@Override
	public synchronized void load() {
		countriesMap.clear(); 
		countriesImagesMap.clear(); 
		try(FileInputStream fis = new FileInputStream(rootInfoFileForCountries)){
			JsonParser parser = new JsonParser();
			JsonArray root = parser.parse(new InputStreamReader(fis, "UTF-8")).getAsJsonArray();
			for(int i=0; i<root.size(); i++) {
				JsonObject country = root.get(i).getAsJsonObject(); 
				String id   = country.get("id").getAsString(); 
				String name = country.get("name").getAsString(); 
				String pict = null;
				try{pict = country.get("pict").getAsString();}catch(Exception ex) {}
				String a2c  = country.get("a2c").getAsString();
				String a3c  = country.get("a3c").getAsString();
				JsonArray ccs = country.get("ccs").getAsJsonArray();
				JsonArray tlds = country.get("tlds").getAsJsonArray();
				if(name!=null) countriesMap.put(id, name);
				if(pict!=null) countriesImagesMap.put(id, new File(UserFileSystemPathConstants.COUNTRY_FLAG_IMAGES, pict));
				if(a2c!=null) addA2C(id, a2c);
				if(a3c!=null) addA3C(id, a3c);
				if(ccs!=null) 
					for(int j=0; j<ccs.size(); j++)
						setCCs(id, ccs.get(j).getAsString());
				if(tlds!=null) 
					for(int j=0; j<tlds.size(); j++)
						setTLDs(id, tlds.get(j).getAsString());
			}
		}catch(RuntimeException ex) {
			throw ex; 
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}
	}

	@Override
	public void init() {
		load(); 
	}

	@Override
	public synchronized void store() {
		if(rootInfoFileForCountries.exists()) rootInfoFileForCountries.delete();
		try(FileOutputStream fos = new FileOutputStream(rootInfoFileForCountries)){
			JsonArray root = new JsonArray(); 
			for(Map.Entry<String, String> me: countriesMap.entrySet()) {
				String id = me.getKey(); 
				String name = me.getValue(); 
				File pict = countriesImagesMap.get(id); 
				JsonObject country = new JsonObject();
				country.addProperty("id", id);
				country.addProperty("name", name);
				country.addProperty("a2c", a2cMap.get(id));
				country.addProperty("a3c", a3cMap.get(id));
				JsonArray ccs = new JsonArray(); 
				JsonArray tlds =  new JsonArray();
				List<String> ccm = ccMap.get(id); 
				List<String> tldm = tldMap.get(id); 
				if(ccm!=null)  for(int i=0; i<ccm.size(); i++)  ccs.add(ccm.get(i));
				if(tldm!=null) for(int i=0; i<tldm.size(); i++) tlds.add(tldm.get(i));
				country.add("ccs", ccs);
				country.add("tlds", tlds);
				if(pict!=null) country.addProperty("pict", pict.toString());
				root.add(country);
			}
			fos.write(root.toString().getBytes("UTF-8"));
		}catch(RuntimeException ex) {
			throw ex; 
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}
	}
	
	public synchronized boolean existsCountry(String idCode) {
		return countriesMap.get(idCode) != null; 
	}
	
	public synchronized boolean existsCountryPicture(String idCode) {
		return countriesImagesMap.get(idCode) != null; 
	}
	
	public synchronized void setCountryInfo(String countryIdCode, String countryName, String countryPictureFilename) {
		if(countryIdCode==null)  throw new NullPointerException(); 
		if(countryName==null)    throw new NullPointerException(); 
		if(countriesMap.get(countryIdCode)!=null) throw new RuntimeException("COUNTRY EXISTS."); 
		countriesMap.put(countryIdCode, countryName);
		countriesImagesMap.put(countryIdCode, new File(UserFileSystemPathConstants.COUNTRY_FLAG_IMAGES, countryPictureFilename));
		if(countriesImagesMap.get(countryIdCode)==null) countriesImagesMap.remove(countryIdCode);
	}
	
	public synchronized void updateCountryInfo(String oldCIC, String countryIdCode, String countryName, String countryPictureFilename) {
		File pict = null; 
		if(oldCIC==null)		 throw new NullPointerException(); 
		if(countriesMap.get(oldCIC)==null)        throw new RuntimeException("OLD COUNTRY NOT EXISTS."); 
		if(countriesMap.get(countryIdCode)!=null) throw new RuntimeException("NEW COUNTRY EXISTS"); 
		if(countryIdCode==null) countryIdCode=oldCIC; 
		if(countryName==null)   countryName=countriesMap.get(countryIdCode);
		if(countryPictureFilename==null)    pict = countriesImagesMap.get(oldCIC); 
		else pict = new File(UserFileSystemPathConstants.COUNTRY_FLAG_IMAGES, countryPictureFilename);
		countriesMap.remove(oldCIC);
		countriesImagesMap.remove(oldCIC);
		countriesMap.put(countryIdCode, countryName);
		countriesImagesMap.put(countryIdCode, pict);
		if(countriesImagesMap.get(countryIdCode)==null) countriesImagesMap.remove(countryIdCode);
	}
	
	public synchronized void removeCountryInfo(String countryCode) {
		if(countryCode==null) throw new NullPointerException();
		if(countriesMap.get(countryCode)==null) throw new RuntimeException("COUNTRY NOT EXISTS."); 
		countriesMap.remove(countryCode);
		countriesImagesMap.remove(countryCode); 
	}
	
	public synchronized void putCountryInfo(String countryIdCode, String countryName, String countryPictureFilename) {
		if(countryIdCode==null)  throw new NullPointerException(); 
		if(countryName==null)    throw new NullPointerException(); 
		countriesMap.put(countryIdCode, countryName);
		if(countryPictureFilename!=null) 
			countriesImagesMap.put(countryIdCode, new File(UserFileSystemPathConstants.COUNTRY_FLAG_IMAGES, countryPictureFilename));
		if(countriesImagesMap.get(countryIdCode)==null) countriesImagesMap.remove(countryIdCode);
	}
	
	public synchronized String get(String countryIdCode) {
		return countriesMap.get(countryIdCode); 
	}
	
	public synchronized File getPicture(String countryIdCode) {
		return countriesImagesMap.get(countryIdCode); 
	}
	
	public synchronized void clear() {
		countriesImagesMap.clear(); 
		countriesMap.clear(); 
	}
	
	public synchronized void setImageInfo(String id, String imageName) {
		if(!countriesMap.containsKey(id)) return; 
		countriesImagesMap.put(id, new File(UserFileSystemPathConstants.COUNTRY_FLAG_IMAGES, imageName)); 
	}

	@Override
	public long size() {
		return countriesMap.size();
	}
	
	public synchronized void inspectPictures() {
		for(String id: countriesMap.keySet()) {
			String a2c = a2cMap.get(id); 
			if(a2c==null) continue;
			File pict = new File(UserFileSystemPathConstants.COUNTRY_FLAG_IMAGES,a2c+".png");
			if(pict.exists()) {
				this.countriesImagesMap.put(id, pict);
			}
		}
		this.store();
	}
}
