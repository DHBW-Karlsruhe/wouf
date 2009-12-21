package org.bh.data.types;

import java.security.InvalidParameterException;
import java.util.regex.Pattern;

/**
 * Class for economic calculation wiht mixed types. Especially Interval
 * arithmetic is relevant.
 * 
 * @author Sebastian
 * @author Norman
 * @author Robert Vollmer
 * 
 * @version 0.1, 21.11.2009, Sebastian
 * @version 0.2, unknown, Robert
 * @version 0.3, 21.12.2009, Norman
 */
public abstract class Calculable implements IValue {

	/** The Constant DOUBLE_PATTERN. */
	public static final Pattern DOUBLE_PATTERN = Pattern
			.compile("[0-9]*\\.?[0-9]*");
	
	/** The Constant INTEGER_PATTERN. */
	public static final Pattern INTEGER_PATTERN = Pattern.compile("[0-9]*");
	
	/** The Constant INTERVAL_PATTERN. */
	public static final Pattern INTERVAL_PATTERN = Pattern
			.compile("\\[\\s?[0-9]*\\.?[0-9]*\\s?;\\s?[0-9]*\\.?[0-9]*\\s?\\]");

	/**
	 * Adds summand to the current Calculable.
	 * 
	 * @param summand the summand
	 * 
	 * @return the result as a new calculable instance
	 */
	public abstract Calculable add(Calculable summand);

	/**
	 * Subtracts the subtrahend from the current Calculable.
	 * 
	 * @param subtrahend the subtrahend
	 * 
	 * @return the result as a new calculable instance
	 */
	public abstract Calculable sub(Calculable subtrahend);

	/**
	 * Divides the current Calculable through the divisor.
	 * 
	 * @param divisor the divisor
	 * 
	 * @return the result as a new calculable instance
	 */
	public abstract Calculable div(Calculable divisor);

	/**
	 * Multiplies the current Calculable with the multiplicand.
	 * 
	 * @param multiplicand the multiplicand
	 * 
	 * @return the result as a new calculable instance
	 */
	public abstract Calculable mul(Calculable multiplicand);

	/**
	 * Returns the square root of the current Calculable.
	 * 
	 * @return the result as a new calculable instance
	 */
	public abstract Calculable sqrt();

	/**
	 * Returns the power of the exponent of the current Calculable.
	 * 
	 * @param exponent the exponent
	 * 
	 * @return the result as a new calculable instance
	 */
	public abstract Calculable pow(Calculable exponent);

	/* Specified by interface/super class. */
	@Override
	public abstract Calculable clone();

	/**
	 * Parses a calculable instance of a given string.
	 * 
	 * @param s the s
	 * 
	 * @return the calculable
	 */
	public static Calculable parseCalculable(String s) {
		if (DOUBLE_PATTERN.matcher(s).matches()) {
			return new DoubleValue(java.lang.Double.parseDouble(s));
		} else if (INTEGER_PATTERN.matcher(s).matches()) {
			return new IntegerValue(java.lang.Integer.parseInt(s));
		} else if (INTERVAL_PATTERN.matcher(s).matches()) {
			String[] parts =  s.split(";", 2);
			return new IntervalValue(Double.parseDouble(parts[0].substring(1).trim()),
								Double.parseDouble(parts[1].substring(1).trim()));
		} else {
			throw new UnsupportedOperationException(
					"unknown type for calculable,  has not been implemented yet");
		}
	}

	/**
	 * Calculates the sum of all parameters.
	 * 
	 * @param summands the summands
	 * 
	 * @return Sum of all parameters.
	 */
	public Calculable add(Calculable... summands) {
		if (summands.length == 0)
			throw new InvalidParameterException("No summands have been passed");
		
		Calculable result = null;
		for (int i = 0; i < summands.length; i++) {
			result = add(summands[i]);
		}
		return result;
	}

	/**
	 * Calculates the product of all parameters.
	 * 
	 * @param factors the factors
	 * 
	 * @return Product of all parameters.
	 */
	public Calculable mul(Calculable... factors) {
		if (factors.length == 0)
			throw new InvalidParameterException("No factors have been passed");

		Calculable result = null;
		for (int i = 0; i < factors.length; i++) {
			result = mul(factors[i]);
		}
		return result;
	}

	/* Specified by interface/super class. */
	@Override
	public abstract String toString();
}
