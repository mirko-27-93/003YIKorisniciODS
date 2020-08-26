package studiranje.ip.error;

/**
 * Грешке услед тражења информација о сесијама које нису од корисника. 
 * @author mirko
 * @version 1.0
 */
public class SessionOwnerException extends SessionInfoException {
	private static final long serialVersionUID = -6107098961440330044L;

	public SessionOwnerException() {
		super();
	}

	public SessionOwnerException(String message, Throwable cause) {
		super(message, cause);
	}

	public SessionOwnerException(String message) {
		super(message);
	}

	public SessionOwnerException(Throwable cause) {
		super(cause);
	}
}
