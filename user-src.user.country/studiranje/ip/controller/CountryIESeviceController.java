package studiranje.ip.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import studiranje.ip.model.Country;

/**
 * Уопштена контрола када је ријеч о конртолеру за податке о државама и 
 * њихово прибављање. 
 * @author mirko
 * @version 1.0
 */
public class CountryIESeviceController {
	private CountryDefinitionListController cdlc;
	
	public CountryIESeviceController(CountryDefinitionListController cdlc) {
		super();
		this.cdlc = cdlc;
	}
	
	public CountryDefinitionListController getCdlc() {
		return cdlc;
	}

	public void setCdlc(CountryDefinitionListController cdlc) {
		this.cdlc = cdlc;
	} 
	
	public List<String> getCountriesList(){
		ArrayList<String> list = new ArrayList<>(cdlc.countryMap().keySet());
		Collections.sort(list);
		return list; 
	}
	
	public String getIdByName(String name){
		for(Map.Entry<String, String> me: getCdlc().countryMap().entrySet()) {
			if(me.getValue().contentEquals(name)) return me.getKey();
		}
		return null; 
	}
	
	public Country getCountry(String id) {
		String name = cdlc.countryMap().get(id);
		if(name==null) return null;
		Country c = new Country(id);
		c.setA3c(id);
		c.setFlag(cdlc.countryImageMap().get(id));
		if(cdlc instanceof EUSiteDefinitionListController) {
			EUSiteDefinitionListController edlc = (EUSiteDefinitionListController) cdlc; 
			LocalDefinitionListController ldlc = edlc.getLocalStorage();
			c.setName(ldlc.get(id));
			c.setA2c(ldlc.liveA2CMap().get(id));
			c.setA3c(ldlc.liveA3CMap().get(id));
			c.setCcs(new ArrayList<>(ldlc.liveCCMap().get(id)));
			c.setTlds(new ArrayList<>(ldlc.liveTLDMap().get(id)));
		}
		return c;
	}
	
	public void synchronizeWithService() {
		if(cdlc instanceof EUSiteDefinitionListController) {
			EUSiteDefinitionListController edlc = (EUSiteDefinitionListController) cdlc; 
			LocalDefinitionListController ldlc = edlc.getLocalStorage();
			edlc.loadFromEUServiceAndLocalizeTextDataInformation();
			ldlc.store();
		}
	}

	
	public List<Country> getCountries(){
		ArrayList<Country> countries = new ArrayList<>(); 
		ArrayList<String> list = new ArrayList<>(cdlc.countryMap().keySet());
		Collections.sort(list);
		for(String str: list) {
			Country country = getCountry(str);
			countries.add(country);
		}
		return countries;
	}
}
