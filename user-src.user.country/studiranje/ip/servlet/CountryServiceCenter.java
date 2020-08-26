package studiranje.ip.servlet;

import studiranje.ip.controller.CountryDefinitionListController;
import studiranje.ip.controller.CountryIESeviceController;
import studiranje.ip.controller.EUSiteDefinitionListController;

/**
 * Заједничке сервисне контроле које користе сервлети из апликације, 
 * који су задужени за регулацију података и заставица држава, 
 * у току рада апликације је углавном само за читање, али промјене 
 * и учитавање су сконцетрисане на почетку, изузев сингронизације. 
 * @author mirko
 * @version 1.0
 */
public final class CountryServiceCenter {
	public static final CountryDefinitionListController cdlc = new EUSiteDefinitionListController();
	public static final CountryIESeviceController ctrl = new CountryIESeviceController(cdlc);
	
	private CountryServiceCenter() {}
}
