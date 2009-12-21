package org.bh.platform.formula;

/**
 * Exception class for formula related exceptions
 * 
 * <p>
 * TODO
 * 
 * @author Norman
 * @version 0.1 , 02.12.2009
 * 
 */
public class FormulaException extends Exception {

    private static final long serialVersionUID = 6402187330977945530L;

    public FormulaException(String message) {
	super(message);
    }

    public FormulaException(String message, Throwable cause) {
	super(message, cause);
    }

    public FormulaException(Throwable cause) {
	super(cause);
    }
}
