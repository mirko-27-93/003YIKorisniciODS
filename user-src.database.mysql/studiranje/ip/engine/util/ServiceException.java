package studiranje.ip.engine.util;

/**
 * Грешке које се дешавају услед кориштења сервиса и везане за исти. 
 * @author mirko
 * @version 1.0
 */
public class ServiceException extends RuntimeException{
	private static final long serialVersionUID = -8538858061478186181L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}
}
