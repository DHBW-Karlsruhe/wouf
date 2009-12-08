package org.bh.calculation.sebi;

public class Integer extends Calculable {
    int value;

    public Integer(int value) {
	this.value = value;
    }

    @Override
    public Calculable add(Calculable summand) {
	if (summand instanceof Integer) {
	    return new Integer(value + ((Integer) summand).getValue());
	} else if (summand instanceof Double) {
	    return new Double(value + ((Double) summand).getValue());
	} else {
	    double x = value + ((Interval) summand).getMin();
	    double y = value + ((Interval) summand).getMax();
	    if (x < y) {
		return new Interval(x, y);
	    }
	    return new Interval(y, x);
	}
    }

    @Override
    public Calculable div(Calculable divisor) {
	if (divisor instanceof Integer) {
	    return new Double((double) value / ((Integer) divisor).getValue());
	} else if (divisor instanceof Double) {
	    return new Double(value / ((Double) divisor).getValue());
	} else {
	    double x = value / ((Interval) divisor).getMin();
	    double y = value / ((Interval) divisor).getMax();
	    if (x < y) {
		return new Interval(x, y);
	    }
	    return new Interval(y, x);
	}
    }

    public int getValue() {
	return value;
    }

    @Override
    public Calculable mul(Calculable multiplicand) {
	if (multiplicand instanceof Integer) {
	    return new Integer(value * ((Integer) multiplicand).getValue());
	} else if (multiplicand instanceof Double) {
	    return new Double(value * ((Double) multiplicand).getValue());
	} else {
	    double x = value * ((Interval) multiplicand).getMin();
	    double y = value * ((Interval) multiplicand).getMax();
	    if (x < y) {
		return new Interval(x, y);
	    }
	    return new Interval(y, x);
	}
    }

    @Override
    public Calculable pow(Calculable exponent) {
	return null;
    }

    @Override
    public Calculable sqrt() {
	return null;
    }

    @Override
    public Calculable sub(Calculable subtrahend) {
	if (subtrahend instanceof Integer) {
	    return new Integer(value - ((Integer) subtrahend).getValue());
	} else if (subtrahend instanceof Double) {
	    return new Double(value - ((Double) subtrahend).getValue());
	} else {
	    double x = value - ((Interval) subtrahend).getMin();
	    double y = value - ((Interval) subtrahend).getMax();
	    if (x < y) {
		return new Interval(x, y);
	    }
	    return new Interval(y, x);
	}
    }

    @Override
    public String toString() {
	return "" + value;
    }

}
