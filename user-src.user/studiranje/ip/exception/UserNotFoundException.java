package studiranje.ip.exception;

/**
 * Изузеци услед непостојања корисника.
 * @author mirko
 * @version 1.0
 */
public class UserNotFoundException extends RuntimeException{
	private static final long serialVersionUID = -6892069841110652316L;

	public UserNotFoundException() {
		super();
	}

	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserNotFoundException(String message) {
		super(message);
	}

	public UserNotFoundException(Throwable cause) {
		super(cause);
	}
}
