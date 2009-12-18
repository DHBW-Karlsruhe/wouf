package org.bh.data.types;

/**
 * String value data type
 * 
 * <p>
 * This type offers the possibility to create String values and use them in DTOs enums
 * 
 * @author Michael Löckelt
 * @version 0.2, 16.12.2009
 * 
 */

public class StringValue implements IValue {
	
	private String myString;
	
	/**
	 * Constructor
	 * @param myString
	 */
	public StringValue (String myString) {
		this.myString = myString;
	}
	
	@Override
	public IValue clone() {
		return new StringValue(myString);
	}
	
	/**
	 * Getter-Method
	 * @return String
	 */
	public String getString() {
		return this.myString;
	}

}
