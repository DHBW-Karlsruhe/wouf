package org.bh.platform;
import java.math.BigDecimal;

/**
 * Value Object
 *
 * <p>
 * Value Object
 *
 * @author Marcus Katzor
 * @author Michael Loeckelt
 * @version 1.0, 03.12.2009
 *
 */

public class Value {
	
	private BigDecimal min; 		// intervall calculation: min value
	private BigDecimal max; 		// intervall calculation: max value
	private BigDecimal expected;	
	private String text;			 

	public Value()
	{
		
	}
	
	public Value(double expected) {
		this(new BigDecimal(expected));		
	}
	
	public Value(BigDecimal expected) {
		super();
		this.expected = expected;
	}

	public Value add(Value val)
	{
		BigDecimal expectedAdd = expected.add(val.getExpected());
		Value result = new Value(expectedAdd);
		return result;
	}

	public BigDecimal getMin() {
		return min;
	}

	public void setMin(BigDecimal min) {
		this.min = min;
	}

	public BigDecimal getMax() {
		return max;
	}
	
	
	public void setMax(BigDecimal max) {
		this.max = max;
	}


	public BigDecimal getExpected() {
		return expected;
	}

	public void setExpected(BigDecimal expected) {
		this.expected = expected;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
