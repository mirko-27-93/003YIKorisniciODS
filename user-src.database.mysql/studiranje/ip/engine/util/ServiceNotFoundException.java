package studiranje.ip.engine.util;

/**
 * Грешке које се дешавају услед одсуства сервиса. 
 * @author mirko
 * @version 1.0
 */
public class ServiceNotFoundException extends ServiceException{
	private static final long serialVersionUID = 4126772149336453401L;

	public ServiceNotFoundException() {
		super();
	}

	public ServiceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceNotFoundException(String message) {
		super(message);
	}

	public ServiceNotFoundException(Throwable cause) {
		super(cause);
	}
}
