package studiranje.ip.exception;

/**
 * Изузетак услед неважећег изузетка.
 * @author mirko
 * @version 1.0
 */
public class InvalidLiteralException extends RuntimeException{
	private static final long serialVersionUID = 5497256887636135249L;

	public InvalidLiteralException() {
		super();
	}

	public InvalidLiteralException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidLiteralException(String message) {
		super(message);
	}

	public InvalidLiteralException(Throwable cause) {
		super(cause);
	}
}
