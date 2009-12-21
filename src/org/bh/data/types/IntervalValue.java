package org.bh.data.types;

/**
 * Calculable implementation for interval values.
 * 
 * @author Sebastian
 * @author Norman
 * 
 * @version 0.1, 21.11.2009, Sebastian
 * @version 0.3, 21.12.2009, Norman
 */
public class IntervalValue extends Calculable {

	/** The min. */
	protected double min;
	
	/** The max. */
	protected double max;

	/**
	 * Instantiates a new interval value.
	 * 
	 * @param min the min
	 * @param max the max
	 */
	public IntervalValue(double min, double max) {
		this.min = min;
		this.max = max;
	}

	/* Specified by interface/super class. */
	@Override
	public final Calculable add(final Calculable summand) {
		if (summand instanceof IntegerValue) {
			int value = ((IntegerValue) summand).value;
			return add(new IntervalValue(value, value));
		} else if (summand instanceof DoubleValue) {
			double value = ((DoubleValue) summand).value;
			return add(new IntervalValue(value, value));
		} else {
			return new IntervalValue(
				Math.nextAfter(min + ((IntervalValue) summand).min, Double.NEGATIVE_INFINITY),
				Math.nextAfter( max + ((IntervalValue) summand).max, Double.POSITIVE_INFINITY));
		}
	}

	/* Specified by interface/super class. */
	@Override
	public final Calculable sub(final Calculable subtrahend) {
		if (subtrahend instanceof IntegerValue) {
			int value = ((IntegerValue) subtrahend).value;
			return sub(new IntervalValue(value, value));
		} else if (subtrahend instanceof DoubleValue) {
			double value = ((DoubleValue) subtrahend).value;
			return sub(new IntervalValue(value, value));
		} else {
			return new IntervalValue(
					Math.nextAfter(min - ((IntervalValue) subtrahend).max, Double.NEGATIVE_INFINITY),
					Math.nextAfter(max - ((IntervalValue) subtrahend).min, Double.POSITIVE_INFINITY));
		}
	}

	/* Specified by interface/super class. */
	@Override
	public final Calculable mul(final Calculable multiplicand) {
		if (multiplicand instanceof IntegerValue) {
			int value = ((IntegerValue) multiplicand).value;
			return mul(new IntervalValue(value, value));
		} else if (multiplicand instanceof DoubleValue) {
			double value = ((DoubleValue) multiplicand).value;
			return mul(new IntervalValue(value, value));
		} else {
			return new IntervalValue(
					Math.nextAfter(min(
						min * ((IntervalValue) multiplicand).min,
						min * ((IntervalValue) multiplicand).max,
						max * ((IntervalValue) multiplicand).min,
						max * ((IntervalValue) multiplicand).max), 
					Double.NEGATIVE_INFINITY), 
					Math.nextAfter(max(
						min * ((IntervalValue) multiplicand).min,
						min * ((IntervalValue) multiplicand).max, 
						max * ((IntervalValue) multiplicand).min, 
						max * ((IntervalValue) multiplicand).max),
					Double.POSITIVE_INFINITY));
		}
	}

	/* Specified by interface/super class. */
	@Override
	public final Calculable div(final Calculable divisor) {
		if (divisor instanceof IntegerValue) {
			int value = ((IntegerValue) divisor).value;
			return div(new IntervalValue(value, value));
		} else if (divisor instanceof DoubleValue) {
			double value = ((DoubleValue) divisor).value;
			return div(new IntervalValue(value, value));
		} else {
			IntervalValue div = (IntervalValue) divisor;
			if ((div.min < 0 && div.max < 0) || (div.min > 0 && div.max > 0)) {
				return mul(new IntervalValue(
						Math.nextAfter((1 / div.max), Double.NEGATIVE_INFINITY),
						Math.nextAfter((1 / div.min), Double.POSITIVE_INFINITY)));
			}
			//TODO
			throw new UnsupportedOperationException("Division for Intevals containing 0 not implemented");
		}
	}

	/* Specified by interface/super class. */
	@Override
	public final Calculable pow(final Calculable exponent) {
		if (exponent instanceof IntegerValue) {
			int value = ((IntegerValue) exponent).value;
			return pow(new IntervalValue(value, value));
		} else if (exponent instanceof DoubleValue) {
			double value = ((DoubleValue) exponent).value;
			return pow(new IntervalValue(value, value));
		} else if (exponent instanceof IntervalValue) {
			// TODO
			throw new UnsupportedOperationException("Not implemented so far");
		} else {
			return null;
		}
	}

	/* Specified by interface/super class. */
	@Override
	public final Calculable sqrt() {
		// TODO
		throw new UnsupportedOperationException("Not implemented so far");
	}

	/**
	 * Gets the max.
	 * 
	 * @return the max
	 */
	public double getMax() {
		return max;
	}

	/**
	 * Gets the min.
	 * 
	 * @return the min
	 */
	public double getMin() {
		return min;
	}

	/* Specified by interface/super class. */
	@Override
	public final String toString() {
		return "(" + min + "; " + max + ")";
	}

	/* Specified by interface/super class. */
	@Override
	public final Calculable clone() {
		return new IntervalValue(min, max);
	}

	/**
	 * Determines the maximum value of the given parameters.
	 * 
	 * @param a the a
	 * @param b the b
	 * @param c the c
	 * @param d the d
	 * 
	 * @return the maximum double value
	 */
	private double max(double a, double b, double c, double d) {
		double maxVal;
		maxVal = Math.max(a, b);
		maxVal = Math.max(maxVal, c);
		maxVal = Math.max(maxVal, d);
		return maxVal;
	}

	/**
	 * Determines the minimum value of the given parameters.
	 * 
	 * @param a the a
	 * @param b the b
	 * @param c the c
	 * @param d the d
	 * 
	 * @return the minimum double value
	 */
	private double min(double a, double b, double c, double d) {
		double minVal;
		minVal = Math.min(a, b);
		minVal = Math.min(minVal, c);
		minVal = Math.min(minVal, d);
		return min;
	}
}
