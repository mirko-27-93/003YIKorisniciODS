package studiranje.ip.database.util;

/**
 * Изузетак услед непостојања базе података. 
 * @author mirko
 * @version 1.0
 */
public class DatabaseNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 6795752605542082702L;

	public DatabaseNotFoundException() {
		super();
	}

	public DatabaseNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public DatabaseNotFoundException(String message) {
		super(message);
	}

	public DatabaseNotFoundException(Throwable cause) {
		super(cause);
	}
}
