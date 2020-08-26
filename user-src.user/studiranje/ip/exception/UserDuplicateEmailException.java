package studiranje.ip.exception;

/**
 * Изузеци услед дуплирања корисничке електронске поште. 
 * @author mirko
 * @version 1.0
 */
public class UserDuplicateEmailException extends RuntimeException{
	private static final long serialVersionUID = -3873419138431631977L;

	public UserDuplicateEmailException() {
		super();
	}

	public UserDuplicateEmailException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserDuplicateEmailException(String message) {
		super(message);
	}

	public UserDuplicateEmailException(Throwable cause) {
		super(cause);
	}
}
