package org.bh.calculation.sebi;

public class Double extends Calculable {
    double value;

    public Double(double value) {
	this.value = value;
    }

    public double getValue() {
	return value;
    }

    @Override
    public Calculable add(Calculable summand) {
	if (summand instanceof Integer)
	    return new Double(value + ((Integer) summand).getValue());
	else if (summand instanceof Double)
	    return new Double(value + ((Double) summand).getValue());
	else {
	    double x = value + ((Interval) summand).getMin();
	    double y = value + ((Interval) summand).getMax();
	    if (x < y)
		return new Interval(x, y);
	    return new Interval(y, x);
	}
    }

    @Override
    public Calculable div(Calculable divisor) {
	if (divisor instanceof Integer)
	    return new Double(value / ((Integer) divisor).getValue());
	else if (divisor instanceof Double)
	    return new Double(value / ((Double) divisor).getValue());
	else {
	    double x = value / ((Interval) divisor).getMin();
	    double y = value / ((Interval) divisor).getMax();
	    if (x < y)
		return new Interval(x, y);
	    return new Interval(y, x);
	}
    }

    @Override
    public Calculable mul(Calculable multiplicand) {
	if (multiplicand instanceof Integer)
	    return new Double(value * ((Integer) multiplicand).getValue());
	else if (multiplicand instanceof Double)
	    return new Double(value * ((Double) multiplicand).getValue());
	else {
	    double x = value * ((Interval) multiplicand).getMin();
	    double y = value * ((Interval) multiplicand).getMax();
	    if (x < y) {
		return new Interval(x, y);
	    }
	    return new Interval(y, x);
	}
    }

    @Override
    public Calculable sub(Calculable subtrahend) {
	if (subtrahend instanceof Integer)
	    return new Double(value - ((Integer) subtrahend).getValue());
	else if (subtrahend instanceof Double)
	    return new Double(value - ((Double) subtrahend).getValue());
	else {
	    double x = value - ((Interval) subtrahend).getMin();
	    double y = value - ((Interval) subtrahend).getMax();
	    if (x < y) {
		return new Interval(x, y);
	    }
	    return new Interval(y, x);
	}
    }

    @Override
    public Calculable pow(Calculable exponent) {
	// TODO Auto-generated method stub
	throw new UnsupportedOperationException(
		"This method has not been implemented");
    }

    @Override
    public Calculable sqrt() {
	// TODO Auto-generated method stub
	throw new UnsupportedOperationException(
		"This method has not been implemented");
    }

    @Override
    public String toString() {
	return "" + value;
    }
    
}