package business.exceptions;

/**
 * This Exception is thrown if pedagogic registration is not complete or well formed
 * @author DUBUIS Michael
 *
 */
public class PedagogicRegistrationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1538624576381685533L;

	/**
	 * 
	 */
	public PedagogicRegistrationException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public PedagogicRegistrationException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public PedagogicRegistrationException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public PedagogicRegistrationException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public PedagogicRegistrationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
