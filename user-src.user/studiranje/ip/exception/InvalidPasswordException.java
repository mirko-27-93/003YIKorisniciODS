package studiranje.ip.exception;

/**
 * Грешке услед погрешне лозинке при логовању. 
 * @author mirko
 * @version 1.0
 */
public class InvalidPasswordException extends RuntimeException{
	private static final long serialVersionUID = -9174359702991196308L;

	public InvalidPasswordException() {
		super();
	}

	public InvalidPasswordException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidPasswordException(String message) {
		super(message);
	}

	public InvalidPasswordException(Throwable cause) {
		super(cause);
	}
}
