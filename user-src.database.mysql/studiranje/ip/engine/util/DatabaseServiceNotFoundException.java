package studiranje.ip.engine.util;

/**
 * Грешке које се дешавају услед непостојања сервиса који би требао управљати 
 * базом података.
 * @author mirko
 * @version 1.0
 */
public class DatabaseServiceNotFoundException extends DatabaseServiceException{
	private static final long serialVersionUID = -5049663423220230071L;

	public DatabaseServiceNotFoundException() {
		super();
	}

	public DatabaseServiceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public DatabaseServiceNotFoundException(String message) {
		super(message);
	}

	public DatabaseServiceNotFoundException(Throwable cause) {
		super(cause);
	}
}
