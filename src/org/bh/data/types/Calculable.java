package org.bh.data.types;

import java.security.InvalidParameterException;

//TODO: Abstrakt + Werteparser (z.B. 5.0 als String = new Double(5.0)
public abstract class Calculable implements Value{

    public abstract Calculable add(Calculable summand);

    public abstract Calculable sub(Calculable subtrahend);

    public abstract Calculable div(Calculable divisor);

    public abstract Calculable mul(Calculable multiplicand);

    public abstract Calculable sqrt();

    public abstract Calculable pow(Calculable exponent);

    public abstract Calculable clone();

    /**
     * @param nodeValue
     * @return
     */
    public static Calculable parseCalculable(String nodeValue) {
	if (nodeValue.matches("[0-9]*\\.[0-]*")) {
	    return new DoubleValue(java.lang.Double.parseDouble(nodeValue));
	} else if (nodeValue.matches("[0-9]*")) {
	    return new IntegerValue(java.lang.Integer.parseInt(nodeValue));
	} else if (nodeValue.matches("interval regex")) {
	    throw new UnsupportedOperationException(
		    "interval parser has not been implemented yet");
	} else {
	    throw new UnsupportedOperationException(
		    "unknown type for calculable exception has not been implemented yet");
	}
    }

    /* Specified by interface/super class. */
    @Override
    public abstract String toString();

    /**
     * Calculates the sum of all parameters.
     * @param summands
     * @return Sum of all parameters.
     */
	public static Calculable addAll(Calculable... summands) {
		if (summands.length == 0)
			throw new InvalidParameterException("No summands have been passed");
		
		Calculable result = summands[0];
		for (int i = 1; i < summands.length; i++) {
			result = result.add(summands[i]);
		}
		return result;
	}
	
	/**
     * Calculates the product of all parameters.
     * @param factors
     * @return Product of all parameters.
     */
	public static Calculable mulAll(Calculable... factors) {
		if (factors.length == 0)
			throw new InvalidParameterException("No factors have been passed");
		
		Calculable result = factors[0];
		for (int i = 1; i < factors.length; i++) {
			result = result.mul(factors[i]);
		}
		return result;
	}

}
