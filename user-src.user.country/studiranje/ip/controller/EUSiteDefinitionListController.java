package studiranje.ip.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import studiranje.ip.lang.UserFileSystemPathConstants;

/**
 * Контролер улаза и излаза према локалној бази података односно датотекама 
 * са подацима држава/е које су презете и префилтриране са сајта ЕУ.   
 * @author mirko
 * @version 1.0
 */
public class EUSiteDefinitionListController implements CountryDefinitionListController{
	private static final long serialVersionUID = 2161862441375186323L;

	static {
		if(!CountryDefinitionListController.DIR_OF_ECD.exists())
			CountryDefinitionListController.DIR_OF_ECD.mkdir(); 
		if(!CountryDefinitionListController.ECD_LIST.exists()) {
			try(FileOutputStream fos = new FileOutputStream(CountryDefinitionListController.ECD_LIST)){
				fos.write("{}".getBytes("UTF-8"));
			}catch(Exception ex) {
				throw new RuntimeException(ex);
			}
		}
		if(!CountryDefinitionListController.ECD_LIST.exists()) {
			try(FileOutputStream fos = new FileOutputStream(CountryDefinitionListController.ECD_LIST)){
				fos.write("{}".getBytes("UTF-8"));
			}catch(Exception ex) {
				throw new RuntimeException(ex);
			}
		}
		if(!CountryDefinitionListController.ECD_LIST_EXC.exists()) {
			try(FileOutputStream fos = new FileOutputStream(CountryDefinitionListController.ECD_LIST_EXC)){
				fos.write("{}".getBytes("UTF-8"));
			}catch(Exception ex) {
				throw new RuntimeException(ex);
			}
		}
	}
	
	private CriticalyExceptioningDefinitionController appFilter = new CriticalyExceptioningDefinitionController(); 
	private LocalDefinitionListController localStorage = new LocalDefinitionListController();
	
	{
		localStorage.setRootInfoDirForCountries(CountryDefinitionListController.DIR_OF_ECD);
		localStorage.setRootInfoFileForCountries(CountryDefinitionListController.ECD_LIST);
	}

	public CriticalyExceptioningDefinitionController getAppFilter() {
		return appFilter;
	}

	public void setAppFilter(CriticalyExceptioningDefinitionController appFilter) {
		this.appFilter = appFilter;
	}

	public LocalDefinitionListController getLocalStorage() {
		return localStorage;
	}

	public void setLocalStorage(LocalDefinitionListController localStorage) {
		this.localStorage = localStorage;
	}
	
	public void loadFromEUServiceAndLocalizeTextDataInformation() throws EUCountryServiceException{
		try {
			localStorage.clear();
			URL url = new URL("https://restcountries.eu/rest/v2/region/europe");
			String dataString = ""; 
			try(Scanner scan= new Scanner(url.openStream(), "UTF-8")){
				while(scan.hasNextLine()) 
					dataString += scan.nextLine()+"\n"; 
			}
			
			JsonParser parser = new JsonParser();
			JsonArray array = parser.parse(dataString).getAsJsonArray();
			
			for(int i=0; i<array.size(); i++) {
				JsonObject object = array.get(i).getAsJsonObject(); 
				String name = object.getAsJsonPrimitive("name").getAsString();
				String code = object.getAsJsonPrimitive("alpha3Code").getAsString();
				if(!new CriticalyExceptioningDefinitionController().filter(name)) continue; 
				localStorage.putCountryInfo(code, name, null); 
				String a2c = object.getAsJsonPrimitive("alpha2Code").getAsString();
				localStorage.addA2C(code, a2c);
				localStorage.addA3C(code, code);
				List<String> ccsList  = new ArrayList<>(); 
				List<String> tldsList = new ArrayList<>();
				JsonArray ccs = object.get("callingCodes").getAsJsonArray();
				for(int j=0; j<ccs.size(); j++) {
					String cc = ccs.get(j).getAsString();
					ccsList.add(cc);
				}
				JsonArray tlds = object.get("topLevelDomain").getAsJsonArray();
				for(int j=0; j<tlds.size(); j++) {
					String tld = tlds.get(j).getAsString();
					tldsList.add(tld); 
				}
				localStorage.setCCs(code, ccsList);
				localStorage.setTLDs(code, tldsList);
			}
		}catch(Exception ex) {
			throw new EUCountryServiceException(ex);
		}
	}
	
	public void loadFromEUServiceAndLocalize() throws EUCountryServiceException{
		try {
			localStorage.clear();
			URL url = new URL("https://restcountries.eu/rest/v2/region/europe");
			String dataString = ""; 
			try(Scanner scan= new Scanner(url.openStream(), "UTF-8")){
				while(scan.hasNextLine()) 
					dataString += scan.nextLine()+"\n"; 
			}
			JsonParser parser = new JsonParser();
			JsonArray array = parser.parse(dataString).getAsJsonArray();
			
			for(int i=0; i<array.size(); i++) {
				JsonObject object = array.get(i).getAsJsonObject(); 
				String name = object.getAsJsonPrimitive("name").getAsString();
				String code = object.getAsJsonPrimitive("alpha3Code").getAsString();
				String CA2C = object.get("alpha2Code").getAsString();
				String pict = loadAndLocalizePictureIfNotExists(CA2C);
				if(!new CriticalyExceptioningDefinitionController().filter(name)) continue; 
				localStorage.putCountryInfo(code, name, pict); 
				String a2c = object.getAsJsonPrimitive("alpha2Code").getAsString();
				localStorage.addA2C(code, a2c);
				localStorage.addA3C(code, code);
				List<String> ccsList  = new ArrayList<>(); 
				List<String> tldsList = new ArrayList<>();
				JsonArray ccs = object.get("callingCodes").getAsJsonArray();
				for(int j=0; j<ccs.size(); j++) {
					String cc = ccs.get(j).getAsString();
					ccsList.add(cc);
				}
				JsonArray tlds = object.get("topLevelDomain").getAsJsonArray();
				for(int j=0; j<tlds.size(); j++) {
					String tld = tlds.get(j).getAsString();
					tldsList.add(tld); 
				}
				localStorage.setCCs(code, ccsList);
				localStorage.setTLDs(code, tldsList);
			}
		}catch(Exception ex) {
			throw new EUCountryServiceException(ex);
		}
	}
	
	public String loadAndLocalizePictureIfNotExists(String countryA2C){
		String pictHref = "https://www.countryflags.io/"+countryA2C+"/flat/64.png";
		String name = countryA2C+".png";
		File localizedPict = new File(UserFileSystemPathConstants.COUNTRY_FLAG_IMAGES, name);
		if(localizedPict.exists()) return name;
		try {
			URL pict = new URL(pictHref);
			long total = 0L;
			long size = 0L; 
			
			try(FileOutputStream fos=new FileOutputStream(localizedPict); InputStream svcis=pict.openStream()){				
					byte[] all = new byte[svcis.available()]; 
					svcis.read(all); 
					size = all.length;
					fos.write(all);
			}
			
			String infoCFP = ""; 
			JsonObject root = null;
			
			try(Scanner infoScan = new Scanner(UserFileSystemPathConstants.COUNTRY_FLAG_IMAGES_INFO, "UTF-8")) {
				while(infoScan.hasNextLine()) {
					infoCFP+=infoScan.nextLine()+"\n"; 
				}
				JsonParser parser = new JsonParser();
			    root= parser.parse(infoCFP).getAsJsonObject(); 
				total = root.getAsJsonPrimitive("country_falg.images.size").getAsLong();
				total += size;
				root.remove("country_falg.images.size");
				root.addProperty("country_falg.images.size", total);
			} 
			
			
			try(FileOutputStream fos=new FileOutputStream(UserFileSystemPathConstants.COUNTRY_FLAG_IMAGES_INFO)){
				fos.write(root.toString().getBytes("UTF-8"));
			}
			
			return name;
		}catch(Exception ex) {
			return null; 
		}
	}

	@Override
	public Map<String, String> countryMap() {
		return localStorage.countryMap();
	}

	@Override
	public Map<String, File> countryImageMap() {
		return localStorage.countryImageMap();
	}

	@Override
	public void load() {
		localStorage.load();
	}

	@Override
	public void init() {
		localStorage.init();
	}

	@Override
	public void store() {
		localStorage.store();
	}
	
	public void synchronizedWithService() {
		loadFromEUServiceAndLocalize();
		localStorage.store();
	}

	@Override
	public long size() {
		return localStorage.size();
	}
}
