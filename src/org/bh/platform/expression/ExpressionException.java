package org.bh.platform.expression;

/**
 * Exception class for all expression related reasons
 * 
 * @author Norman
 * @version 0.1, 21/11/2009
 */
public class ExpressionException extends Exception {

    private static final long serialVersionUID = 6402187330977945530L;

    public ExpressionException(String message) {
	super(message);
    }

    public ExpressionException(String message, Throwable cause) {
	super(message, cause);
    }

    public ExpressionException(Throwable cause) {
	super(cause);
    }
}
