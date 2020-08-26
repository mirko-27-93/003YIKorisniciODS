package studiranje.ip.controller;

/**
 * Изузеци који се јављају услед недостатка сервиса са називима држава.
 * @author mirko
 * @version 1.0
 */
public class EUCountryServiceException extends RuntimeException{
	private static final long serialVersionUID = 7383107758618287569L;

	public EUCountryServiceException() {
		super();
	}

	public EUCountryServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public EUCountryServiceException(String message) {
		super(message);
	}

	public EUCountryServiceException(Throwable cause) {
		super(cause);
	}
}
