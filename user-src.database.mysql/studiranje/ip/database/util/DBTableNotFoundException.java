package studiranje.ip.database.util;

/**
 * Изузеци услед непостојања табеле.
 * @author mirko
 * @version 1.0
 */
public class DBTableNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 5689015772276553565L;

	public DBTableNotFoundException() {
		super();
	}

	public DBTableNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public DBTableNotFoundException(String message) {
		super(message);
	}

	public DBTableNotFoundException(Throwable cause) {
		super(cause);
	}
}
