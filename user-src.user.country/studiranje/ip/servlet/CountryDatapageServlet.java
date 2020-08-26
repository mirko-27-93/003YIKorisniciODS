package studiranje.ip.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import studiranje.ip.bean.PageBean;
import studiranje.ip.controller.CountryDefinitionListController;
import studiranje.ip.controller.CountryIESeviceController;
import studiranje.ip.model.Country;

/**
 * Сервлет којим се враћају ЈСОН подаци о једној држави, датој идентификатором. 
 * @author mirko
 * @version 1.0
 */
@WebServlet("/CountryDatapageServlet")
public class CountryDatapageServlet extends HttpServlet{
	private static final long serialVersionUID = -5086378199542107634L; 
	public static final CountryDefinitionListController cdlc = CountryServiceCenter.cdlc;
	public static final CountryIESeviceController ctrl = CountryServiceCenter.ctrl;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PageBean pagging = (PageBean) req.getSession().getAttribute("country.pagging"); 
		if(pagging == null) req.getSession().setAttribute("country.pagging", pagging = new PageBean());
		
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		resp.addHeader("Content-Disposition", "inline; filename=\"countries.json\"");
		
		try {pagging.setPageNo(Integer.parseInt(req.getParameter("no")));}catch(Exception ex) {}
		try {pagging.setPageSize(Integer.parseInt(req.getParameter("size")));}catch(Exception ex) {}
		try {pagging.setStartFilter(req.getParameter("filter").toString());}catch(Exception ex) {}
		
		JsonObject root = new JsonObject(); 
		JsonObject page = new JsonObject(); 
		JsonArray list = new JsonArray();
		
		long size = ctrl.getCdlc().size(); 
		pagging.setTotalCount((int)size);
		
		String com = req.getParameter("command"); 
		if(com==null) com = "CURRENT"; 
		CountryPageVariantCommand command = CountryPageVariantCommand.valueOf(com); 
		if(command==null) command = CountryPageVariantCommand.CURRENT; 
		
		if(command==CountryPageVariantCommand.NEXT) 	pagging.nextPage();
		if(command==CountryPageVariantCommand.PREVIOUS) pagging.previousPage();
		String all = req.getParameter("all"); 
		
		page.addProperty("no", pagging.getPageNo());
		page.addProperty("size", pagging.getPageSize());
		page.addProperty("filter", pagging.getStartFilter()); 
		page.addProperty("total", pagging.getTotalCount());
		page.addProperty("count", pagging.pageCount());
		page.addProperty("index", pagging.indexStartOfPage());
		page.addProperty("view", pagging.itemsInPage());
		
		List<Country> countries = ctrl.getCountries();
		
		if(all!=null) {
			for(Country country: countries) {
				JsonObject countryJSON = new JsonObject();
				countryJSON.addProperty("name", country.getName());
				countryJSON.addProperty("a2c", country.getA2c());
				countryJSON.addProperty("a3c", country.getA3c());
				countryJSON.addProperty("id", country.getIdCode());
				JsonArray ccs = new JsonArray(); 
				JsonArray tlds = new JsonArray(); 
				for(String cc: country.getCcs()) 
					ccs.add(cc);
				for(String tld: country.getTlds()) 
					tlds.add(tld);
				countryJSON.add("ccs", ccs);
				countryJSON.add("tlds", tlds);
				list.add(countryJSON);
			}
		}else {
			ArrayList<Country> filtered = new ArrayList<>();  
			for(int i=0; i<countries.size(); i++) {
				Country country = countries.get(i);
				if(country.getName().startsWith(pagging.getStartFilter()))
					filtered.add(country); 
			}
			
			if(pagging.getPageNo()>0) {
				int a = (pagging.getPageNo()-1)*pagging.getPageSize(); 
				int b = pagging.getPageNo()*pagging.getPageSize(); 
				for(int i=a; i<Math.min(b, filtered.size()); i++) {
					JsonObject countryJSON = new JsonObject();
					Country country = filtered.get(i); 
					countryJSON.addProperty("name", country.getName());
					countryJSON.addProperty("a2c", country.getA2c());
					countryJSON.addProperty("a3c", country.getA3c());
					countryJSON.addProperty("id", country.getIdCode());
					JsonArray ccs = new JsonArray(); 
					JsonArray tlds = new JsonArray(); 
					for(String cc: country.getCcs()) 
						ccs.add(cc);
					for(String tld: country.getTlds()) 
						tlds.add(tld);
					countryJSON.add("ccs", ccs);
					countryJSON.add("tlds", tlds);
					list.add(countryJSON);
				}
			}
		}
		
		root.add("page", page);
		root.add("list", list);
	
		resp.getWriter().println(root.toString());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
