package studiranje.ip.engine.util;

/**
 * Специјализација грешака сервиса, за сервисе који раде са базама 
 * података. 
 * @author mirko
 * @version 1.0
 */
public class DatabaseServiceException extends ServiceException{
	private static final long serialVersionUID = 3721721302335111553L;

	public DatabaseServiceException() {
		super();
	}

	public DatabaseServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public DatabaseServiceException(String message) {
		super(message);
	}

	public DatabaseServiceException(Throwable cause) {
		super(cause);
	}
}
