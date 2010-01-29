package org.bh.data;

/**
 * Exception for DTO access
 * @author Marcus
 *
 */
public class DTOAccessException extends RuntimeException {

	
	private static final long serialVersionUID = 6655172989787669561L;

	public DTOAccessException() {
		super();
	}

	public DTOAccessException(String arg0) {
		super(arg0);
	}

	public DTOAccessException(Throwable cause) {
		super(cause);
	}

	public DTOAccessException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
