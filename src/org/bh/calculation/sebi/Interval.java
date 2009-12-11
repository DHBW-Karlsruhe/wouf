package org.bh.calculation.sebi;

public class Interval extends Calculable {

    double min;
    double max;

    public Interval(double min, double max) {
	this.min = min;
	this.max = max;
    }

    @Override
    public Calculable add(Calculable summand) {
	if (summand instanceof IntegerValue) {
	    double x = min + ((IntegerValue) summand).getValue();
	    double y = max + ((IntegerValue) summand).getValue();
	    if (x < y) {
		return new Interval(x, y);
	    }
	    return new Interval(y, x);
	} else if (summand instanceof DoubleValue) {
	    double x = min + ((DoubleValue) summand).getValue();
	    double y = max + ((DoubleValue) summand).getValue();
	    if (x < y) {
		return new Interval(x, y);
	    }
	    return new Interval(y, x);
	} else {
	    return new Interval(min + ((Interval) summand).getMin(), max
		    + ((Interval) summand).getMax());
	}
    }

    @Override
    public Calculable div(Calculable divisor) {
	if (divisor instanceof IntegerValue) {
	    double x = min / ((IntegerValue) divisor).getValue();
	    double y = max / ((IntegerValue) divisor).getValue();
	    if (x < y) {
		return new Interval(x, y);
	    }
	    return new Interval(y, x);
	} else if (divisor instanceof DoubleValue) {
	    double x = min / ((DoubleValue) divisor).getValue();
	    double y = max / ((DoubleValue) divisor).getValue();
	    if (x < y) {
		return new Interval(x, y);
	    }
	    return new Interval(y, x);
	} else {
	    return mul(new Interval((1 / ((Interval) divisor).getMin()),
		    (1 / ((Interval) divisor).getMax())));
	}
    }

    public double getMax() {
	return max;
    }

    public double getMin() {
	return min;
    }

    private double max(double a, double b, double c, double d) {
	double max = Math.max(a, b);
	max = Math.max(max, c);
	max = Math.max(max, d);
	return max;
    }

    private double min(double a, double b, double c, double d) {
	double min = Math.min(a, b);
	min = Math.min(min, c);
	min = Math.min(min, d);
	return min;
    }

    @Override
    public Calculable mul(Calculable multiplicand) {
	if (multiplicand instanceof IntegerValue) {
	    double x = min * ((IntegerValue) multiplicand).getValue();
	    double y = max * ((IntegerValue) multiplicand).getValue();
	    if (x < y) {
		return new Interval(x, y);
	    }
	    return new Interval(y, x);
	} else if (multiplicand instanceof DoubleValue) {
	    double x = min * ((DoubleValue) multiplicand).getValue();
	    double y = max * ((DoubleValue) multiplicand).getValue();
	    if (x < y) {
		return new Interval(x, y);
	    }
	    return new Interval(y, x);
	} else {
	    return new Interval(min(min * ((Interval) multiplicand).getMin(),
		    min * ((Interval) multiplicand).getMax(), max
			    * ((Interval) multiplicand).getMin(), max
			    * ((Interval) multiplicand).getMax()), max(min
		    * ((Interval) multiplicand).getMin(), min
		    * ((Interval) multiplicand).getMax(), max
		    * ((Interval) multiplicand).getMin(), max
		    * ((Interval) multiplicand).getMax()));
	}
    }

    @Override
    public Calculable pow(Calculable exponent) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Calculable sqrt() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Calculable sub(Calculable subtrahend) {
	if (subtrahend instanceof IntegerValue) {
	    double x = min - ((IntegerValue) subtrahend).getValue();
	    double y = max - ((IntegerValue) subtrahend).getValue();
	    if (x < y) {
		return new Interval(x, y);
	    }
	    return new Interval(y, x);
	} else if (subtrahend instanceof DoubleValue) {
	    double x = min - ((DoubleValue) subtrahend).getValue();
	    double y = max - ((DoubleValue) subtrahend).getValue();
	    if (x < y) {
		return new Interval(x, y);
	    }
	    return new Interval(y, x);
	} else {
	    return new Interval(min - ((Interval) subtrahend).getMax(), max
		    - ((Interval) subtrahend).getMin());
	}
    }

    @Override
    public String toString() {
	return "(" + min + "; " + max + ")";
    }

	@Override
	public Calculable clone() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}	
}
