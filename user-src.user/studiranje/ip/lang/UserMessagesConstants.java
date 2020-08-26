package studiranje.ip.lang;

/**
 * Поруке које су подржане као резултати операција. 
 * @author mirko
 * @version 1.0
 */
public final class UserMessagesConstants {
	private UserMessagesConstants() {}
	public static final String REGISTRATION_SUCCESS = "Регистрација је успјешна."; 
	public static final String REGISTRATION_FAILURE = "Регистрација није успјешна.";
	public static final String LOGIN_SUCCESS = "Пријава је успјешна.";
	public static final String LOGOUT_SUCCESS = "Одјава је успјешна.";
	public static final String LOGIN_FAILURE = "Пријава није успјешна. Постоји могућност погрешне шифре или непостојања корисника.";
	public static final String DELETE_SUCCESS = "Брисање предходног корисника успјешно.";
	public static final String DELETE_FAILURE = "Брисање предходног корисника неуспјешно. Провјерити актуелну (стару) лозинку.";
	public static final String UPDATE_SUCCESS = "Измјене основних података корисника успјешне.";
	public static final String UPDATE_FAILURE = "Измјене основних података корисника неуспјешне. Провјерити актуелну (стару) лозинку. Можда је ново корисничко име заузето.";
	
	public static final String UPLOAD_PROFILE_PICTURE_FAILURE = "Подизање профилне слике није успјело. Грешка или се подигло више од једне или ниједна слика или датотека није слика."; 
	public static final String UPLOAD_USER_PICTURE_FAILURE = "Подизање корисичке слике није успјело. Грешка или се подигло више од једне или ниједна слика или датотека није слика."; 
	public static final String UPLOAD_COUNTRY_FLAG_PICTURE_FAILURE = "Подизање слике заставе  није успјело. Грешка или се подигло више од једне или ниједна слика или датотека није слика."; 
}
