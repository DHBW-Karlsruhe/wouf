package org.bh.calculation.sebi;

public class DoubleValue extends Calculable {
    double value;

    public DoubleValue(double value) {
	this.value = value;
    }

    public double getValue() {
	return value;
    }

    @Override
    public Calculable add(Calculable summand) {
	if (summand instanceof IntegerValue)
	    return new DoubleValue(value + ((IntegerValue) summand).getValue());
	else if (summand instanceof DoubleValue)
	    return new DoubleValue(value + ((DoubleValue) summand).getValue());
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
	if (divisor instanceof IntegerValue)
	    return new DoubleValue(value / ((IntegerValue) divisor).getValue());
	else if (divisor instanceof DoubleValue)
	    return new DoubleValue(value / ((DoubleValue) divisor).getValue());
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
	if (multiplicand instanceof IntegerValue)
	    return new DoubleValue(value * ((IntegerValue) multiplicand).getValue());
	else if (multiplicand instanceof DoubleValue)
	    return new DoubleValue(value * ((DoubleValue) multiplicand).getValue());
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
	if (subtrahend instanceof IntegerValue)
	    return new DoubleValue(value - ((IntegerValue) subtrahend).getValue());
	else if (subtrahend instanceof DoubleValue)
	    return new DoubleValue(value - ((DoubleValue) subtrahend).getValue());
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

	@Override
	public Calculable clone() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}
    
}
