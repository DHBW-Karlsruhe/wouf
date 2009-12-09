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
	if (summand instanceof Integer) {
	    double x = min + ((Integer) summand).getValue();
	    double y = max + ((Integer) summand).getValue();
	    if (x < y) {
		return new Interval(x, y);
	    }
	    return new Interval(y, x);
	} else if (summand instanceof Double) {
	    double x = min + ((Double) summand).getValue();
	    double y = max + ((Double) summand).getValue();
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
	if (divisor instanceof Integer) {
	    double x = min / ((Integer) divisor).getValue();
	    double y = max / ((Integer) divisor).getValue();
	    if (x < y) {
		return new Interval(x, y);
	    }
	    return new Interval(y, x);
	} else if (divisor instanceof Double) {
	    double x = min / ((Double) divisor).getValue();
	    double y = max / ((Double) divisor).getValue();
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
	if (multiplicand instanceof Integer) {
	    double x = min * ((Integer) multiplicand).getValue();
	    double y = max * ((Integer) multiplicand).getValue();
	    if (x < y) {
		return new Interval(x, y);
	    }
	    return new Interval(y, x);
	} else if (multiplicand instanceof Double) {
	    double x = min * ((Double) multiplicand).getValue();
	    double y = max * ((Double) multiplicand).getValue();
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
	if (subtrahend instanceof Integer) {
	    double x = min - ((Integer) subtrahend).getValue();
	    double y = max - ((Integer) subtrahend).getValue();
	    if (x < y) {
		return new Interval(x, y);
	    }
	    return new Interval(y, x);
	} else if (subtrahend instanceof Double) {
	    double x = min - ((Double) subtrahend).getValue();
	    double y = max - ((Double) subtrahend).getValue();
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
