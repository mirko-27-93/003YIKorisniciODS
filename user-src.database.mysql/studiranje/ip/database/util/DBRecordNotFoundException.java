package studiranje.ip.database.util;

/**
 * Изузеци услед непостојања колоне у табели.
 * @author mirko
 * @version 1.0
 */
public class DBRecordNotFoundException extends RuntimeException{
	private static final long serialVersionUID = -5899027762639751692L;

	public DBRecordNotFoundException() {
		super();
	}

	public DBRecordNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public DBRecordNotFoundException(String message) {
		super(message);
	}

	public DBRecordNotFoundException(Throwable cause) {
		super(cause);
	}
}
