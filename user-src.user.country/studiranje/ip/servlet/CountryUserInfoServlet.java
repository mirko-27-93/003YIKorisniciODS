package studiranje.ip.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import studiranje.ip.controller.CountryDefinitionListController;
import studiranje.ip.controller.CountryIESeviceController;
import studiranje.ip.controller.UserGeneralController;
import studiranje.ip.database.UserDTO;
import studiranje.ip.model.Country;

/**
 * Сервлет/сервис који се односи на прослеђивање основних података о кориснику, 
 * укључив и податке о реквизитима, односно о држави. Односи се на пријављеног. 
 * @author mirko
 * @version 1.0
 */

@WebServlet("/CountryUserInfoServlet")
public class CountryUserInfoServlet extends HttpServlet{
	private static final long serialVersionUID = 3461812051802664073L;
	public static final String ATTR_SESSION_LOGIN = "status.logged"; 
	
	public static final UserGeneralController controller = UserGeneralController.getInstance(); 
	
	public static final CountryDefinitionListController cdlc = CountryServiceCenter.cdlc;
	public static final CountryIESeviceController ctrl = CountryServiceCenter.ctrl;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			req.setCharacterEncoding("UTF-8");
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("application/json");
			
			String username = (String) req.getSession().getAttribute(ATTR_SESSION_LOGIN);
			String userparam = req.getParameter("username"); 
			if(userparam!=null) username = userparam; 
			if(username==null) throw new RuntimeException("No logged in user.");
			
			resp.setHeader("Content-Disposition", "inline; filename*=UTF-8''user_"+username+".json");
			UserDTO userDTO;
			try {
				userDTO = controller.getRegistrator(req.getSession()).getUserDataLink().getFullDTO(username);
			} catch (SQLException ex) {
				throw new RuntimeException(ex);
			} 
			
			if(userDTO==null) throw new RuntimeException("No user data.");
			
			JsonObject userRoot = new JsonObject(); 
			JsonObject personal = new JsonObject();
			JsonObject basicPersonal = new JsonObject(); 
			JsonObject extendedPersonal = new JsonObject(); 
			
			userRoot.addProperty("success", true);
			userRoot.addProperty("message", "");
			userRoot.addProperty("username", username);
			userRoot.addProperty("email", userDTO.getUser().getEmail());
			userRoot.add("personal", personal);
			
			personal.add("basic", basicPersonal);
			personal.add("extended", extendedPersonal);
			
			basicPersonal.addProperty("fname", userDTO.getUser().getFirstname());
			basicPersonal.addProperty("sname", userDTO.getUser().getSecondname());
			basicPersonal.addProperty("passwd", userDTO.getPassword().getToPasswordRecord());
			
			extendedPersonal.addProperty("telephone", userDTO.getRequisit().getTelephone());
			extendedPersonal.addProperty("address", userDTO.getRequisit().getCity());
			
			JsonObject profileInfo = new JsonObject();
			JsonObject countryInfo = new JsonObject(); 
			
			extendedPersonal.add("profile", profileInfo); 
			extendedPersonal.add("country", countryInfo); 
			
			File userPicture = userDTO.getRequisit().getUserPicture(); 
			File profilePicture = userDTO.getRequisit().getProfilePicture(); 
			File countryPicture = userDTO.getRequisit().getCountryFlagPicture(); 
			
			if(userPicture!=null) 
				profileInfo.addProperty("user_picture", userPicture.getName().split("_",2)[1]);
			
			if(profilePicture!=null) 
				profileInfo.addProperty("profile_picture", profilePicture.getName().split("_",2)[1]);
			
			try {
				ctrl.synchronizeWithService();
			}catch(Exception ex) {
				ctrl.getCdlc().load();
			}
			
			if(userDTO.getRequisit().getCountry()!=null)
				countryInfo.addProperty("country", userDTO.getRequisit().getCountry());
			
			if(countryPicture!=null) 
				countryInfo.addProperty("country_picture", countryPicture.getName());
			
			
			String countryName = userDTO.getRequisit().getCountry(); 
			if(countryName!=null) {
				String idA3C = ctrl.getIdByName(countryName); 
				Country country = ctrl.getCountry(idA3C);
				JsonObject countryInf = new JsonObject(); 
				
				String detail = req.getParameter("detail"); 
				
				if(detail!=null && detail.contentEquals("full") && country!=null) {
					if(country.getA2c()!=null) countryInf.addProperty("a2c", country.getA2c());
					if(country.getA3c()!=null) countryInf.addProperty("a3c", country.getA3c());
					JsonArray ccs  = new JsonArray(); 
					JsonArray tlds = new JsonArray();
					
					if(country.getCcs()!=null)
						for(int i=0; i<country.getCcs().size(); i++)
							ccs.add(country.getCcs().get(i));
							
					if(country.getTlds()!=null)
						for(int i=0; i<country.getTlds().size(); i++)
							tlds.add(country.getTlds().get(i)); 
					
					countryInf.add("ccs", ccs);
					countryInf.add("tlds", tlds);
				}
				
				countryInfo.add("country_info", countryInf);
			}
			resp.getWriter().println(userRoot.toString());
		}catch(Exception ex) {
			JsonObject userRoot = new JsonObject(); 
			userRoot.addProperty("success", false);
			userRoot.addProperty("message", ex.getMessage());
			resp.getWriter().println(userRoot.toString());
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
	
}
