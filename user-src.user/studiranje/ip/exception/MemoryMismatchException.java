package studiranje.ip.exception;

/**
 * Изузеци који се дешавају услед, прекорачења меморије.
 * @author mirko
 * @version 1.0
 */
public class MemoryMismatchException extends RuntimeException{
	private static final long serialVersionUID = -2874718305690211855L;

	public MemoryMismatchException() {
		super();
	}

	public MemoryMismatchException(String message, Throwable cause) {
		super(message, cause);
	}

	public MemoryMismatchException(String message) {
		super(message);
	}

	public MemoryMismatchException(Throwable cause) {
		super(cause);
	}
}
