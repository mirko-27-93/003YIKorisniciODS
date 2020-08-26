package studiranje.ip.exception;

/**
 * Изузерак који се баца када садржај или датотека није слика. 
 * @author mirko
 * @version 1.0
 */
public class InvalidImageFormatException extends RuntimeException{
	private static final long serialVersionUID = -5269180239224830618L;

	public InvalidImageFormatException() {
		super();
	}

	public InvalidImageFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidImageFormatException(String message) {
		super(message);
	}

	public InvalidImageFormatException(Throwable cause) {
		super(cause);
	}
}
