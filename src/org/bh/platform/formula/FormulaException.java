package org.bh.platform.formula;

/**
 * Exception class for formula related exceptions
 * 
 * <p>
 * This class is used for every exception telated to formula creation, result
 * determination and transformation.
 * 
 * @author Norman
 * @version 0.1 , 02.12.2009
 */
public class FormulaException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6402187330977945530L;

	/**
	 * Instantiates a new formula exception.
	 * 
	 * @param message
	 *            the message
	 */
	public FormulaException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new formula exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public FormulaException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new formula exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public FormulaException(Throwable cause) {
		super(cause);
	}
}
