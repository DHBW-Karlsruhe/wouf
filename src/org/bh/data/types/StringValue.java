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

public class StringValue implements IValue{
	private static final long serialVersionUID = 1081936369068868863L;
	private final String myString;
	
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
	
	@Override
	public String toString() {
		return myString;
	}

	@Override
	public int hashCode() {
		return myString.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || !(obj instanceof StringValue))
			return false;
		return myString.equals(((StringValue)obj).myString); 
	}
}
