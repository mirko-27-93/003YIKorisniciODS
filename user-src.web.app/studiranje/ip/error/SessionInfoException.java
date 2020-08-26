package studiranje.ip.error;

/**
 * Изузеци који се дешавају приликом размјена информација о сесијама. 
 * @author mirko
 * @version 1.0
 */
public class SessionInfoException extends RuntimeException{
	private static final long serialVersionUID = -6188645129448579198L;

	public SessionInfoException() {
		super();
	}

	public SessionInfoException(String message, Throwable cause) {
		super(message, cause);
	}

	public SessionInfoException(String message) {
		super(message);
	}

	public SessionInfoException(Throwable cause) {
		super(cause);
	}
}
