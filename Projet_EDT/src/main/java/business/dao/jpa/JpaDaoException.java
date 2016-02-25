package business.dao.jpa;

import business.dao.DaoException;

/**
 * This Exception is thrown if JpaDao have malfunction
 * @author DUBUIS Michael
 *
 */
public class JpaDaoException extends DaoException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7932921522967967097L;

	/**
	 * 
	 */
	public JpaDaoException() {
		super();
	}

	/**
	 * @param message
	 */
	public JpaDaoException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public JpaDaoException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public JpaDaoException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public JpaDaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
