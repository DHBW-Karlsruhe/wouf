package org.bh.calculation.sebi;

//TODO: Abstrakt + Werteparser (z.B. 5.0 als String = new Double(5.0)
public abstract class Calculable implements Value{

    public abstract Calculable add(Calculable summand);

    public abstract Calculable sub(Calculable subtrahend);

    public abstract Calculable div(Calculable divisor);

    public abstract Calculable mul(Calculable multiplicand);

    public abstract Calculable sqrt();

    public abstract Calculable pow(Calculable exponent);
    
    /**
     * @author Marcus Katzor 
     */    
    public abstract Calculable clone();

    /**
     * @param nodeValue
     * @return
     */
    public static Calculable parseCalculable(String nodeValue) {
	if (nodeValue.matches("[0-9]*\\.[0-]*")) {
	    return new Double(java.lang.Double.parseDouble(nodeValue));
	} else if (nodeValue.matches("[0-9]*")) {
	    return new Integer(java.lang.Integer.parseInt(nodeValue));
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

}
