package business.exceptions;

/**
 * @author DUBUIS Michael
 *
 */
public class MalformedParametersException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8813023817622767096L;

	/**
	 * 
	 */
	public MalformedParametersException() {}

	/**
	 * @param message
	 */
	public MalformedParametersException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public MalformedParametersException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public MalformedParametersException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public MalformedParametersException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
