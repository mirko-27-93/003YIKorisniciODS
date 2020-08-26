package studiranje.ip.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import studiranje.ip.controller.CountryDefinitionListController;
import studiranje.ip.controller.CountryIESeviceController;
import studiranje.ip.model.Country;

/**
 * Сервлет којим се прослеђују подаци о држави. ЈСОН формат. АЈАКС. 
 * @author mirko
 * @version 1.0
 */
@WebServlet("/CountryInfoServlet")
public class CountryInfoServlet extends HttpServlet{
	private static final long serialVersionUID = -5086378199542107634L; 
	public static final CountryDefinitionListController cdlc = CountryServiceCenter.cdlc;
	public static final CountryIESeviceController ctrl = CountryServiceCenter.ctrl;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json");
		
		String a3c = req.getParameter("a3c"); 
		if(a3c==null) throw new RuntimeException("Not specified country.");
		if(a3c.trim().length()==0) throw new RuntimeException("Not specified country.");
		
		resp.setHeader("Content-Disposition", "inline; filename*=UTF-8''country_"+a3c+".json"); 
		Country country = ctrl.getCountry(a3c); 
		
		if(country==null) { resp.sendError(404, "Country not found"); return; }
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
		
		resp.getWriter().println(countryJSON.toString()); 
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
