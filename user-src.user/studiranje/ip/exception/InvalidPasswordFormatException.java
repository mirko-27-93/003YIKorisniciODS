package studiranje.ip.exception;

/**
 * Некоректна синтакса за лозинке. 
 * @author mirko
 * @version 1.0
 */
public class InvalidPasswordFormatException extends RuntimeException{
	private static final long serialVersionUID = 1598704288208448574L;

	public InvalidPasswordFormatException() {
		super();
	}

	public InvalidPasswordFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidPasswordFormatException(String message) {
		super(message);
	}

	public InvalidPasswordFormatException(Throwable cause) {
		super(cause);
	}
}
