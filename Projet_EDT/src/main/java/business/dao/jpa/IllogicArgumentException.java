package business.dao.jpa;

/**
 * This Exception is thrown if an argument opposites to a previous parameter
 * @author DUBUIS Michael
 *
 */
public class IllogicArgumentException extends JpaDaoException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2163368331030192143L;

	/**
	 * 
	 */
	public IllogicArgumentException() {
		super();
	}

	/**
	 * @param message
	 */
	public IllogicArgumentException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public IllogicArgumentException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public IllogicArgumentException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public IllogicArgumentException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
