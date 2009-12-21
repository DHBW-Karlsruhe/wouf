package org.bh.platform.expression;

/**
 * Exception class for all expression related reasons.
 * 
 * @author Norman
 * @version 0.1, 21/11/2009
 */
public class ExpressionException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 6402187330977945530L;

    /**
     * Instantiates a new expression exception.
     * 
     * @param message the message
     */
    public ExpressionException(String message) {
	super(message);
    }

    /**
     * Instantiates a new expression exception.
     * 
     * @param message the message
     * @param cause the cause
     */
    public ExpressionException(String message, Throwable cause) {
	super(message, cause);
    }

    /**
     * Instantiates a new expression exception.
     * 
     * @param cause the cause
     */
    public ExpressionException(Throwable cause) {
	super(cause);
    }
}
