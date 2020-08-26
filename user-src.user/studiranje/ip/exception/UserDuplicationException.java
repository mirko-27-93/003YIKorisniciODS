package studiranje.ip.exception;

/**
 * Изузеци услед дупликата корисника или корисничког имена. 
 * @author mirko
 * @version 1.0
 */
public class UserDuplicationException extends RuntimeException{
	private static final long serialVersionUID = -6362119496880389582L;

	public UserDuplicationException() {
		super();
	}

	public UserDuplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserDuplicationException(String message) {
		super(message);
	}

	public UserDuplicationException(Throwable cause) {
		super(cause);
	}
}
