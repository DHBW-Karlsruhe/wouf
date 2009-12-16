package org.bh.data.types;

public class StringValue implements Value {
	
	private String myString;
	
	public StringValue (String myString) {
		this.myString = myString;
	}
	@Override
	public Value clone() {
		return new StringValue(myString);
	}
	
	public String getString() {
		return this.myString;
	}

}
