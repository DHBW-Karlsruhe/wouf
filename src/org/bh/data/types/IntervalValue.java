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
	protected final double min;
	
	/** The max. */
	protected final double max;

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
			
			double x = min(
					min * ((IntervalValue) multiplicand).min,
					min * ((IntervalValue) multiplicand).max,
					max * ((IntervalValue) multiplicand).min,
					max * ((IntervalValue) multiplicand).max); 
			
			double y = max(
					min * ((IntervalValue) multiplicand).min,
					min * ((IntervalValue) multiplicand).max, 
					max * ((IntervalValue) multiplicand).min, 
					max * ((IntervalValue) multiplicand).max);
			
			return new IntervalValue(
					Math.nextAfter(x, Double.NEGATIVE_INFINITY),
					Math.nextAfter(y, Double.POSITIVE_INFINITY));
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
			if (!div.contains(0)) {
				return mul(new IntervalValue(1 / div.max, 1 / div.min));
// 						double rounding ? --> mul operation rounds as well
//						Math.nextAfter((1 / div.max), Double.NEGATIVE_INFINITY),
//						Math.nextAfter((1 / div.min), Double.POSITIVE_INFINITY)));
			}
			return new IntervalValue(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
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
		return "[" + min + "; " + max + "]";
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
		return minVal;
	}
	
	/**
	 * Checks whether, a given DoubleValue 
	 * is part of the current interval
	 * 
	 * @param d DoubleValue to check 
	 * @return true if the Interval contains the DoubleValue 
	 */
	public boolean contains(DoubleValue d){
		return contains(d.value);
	}
	
	
	/**
	 * Checks whether, a given double
	 * is part of the current interval
	 * 
	 * @param d double to check 
	 * @return true if the Interval contains the double value
	 */	
	public boolean contains(double d){
		return (d >= min && d <= max);
	}

	/* Specified by interface/super class. */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(max);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(min);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/* Specified by interface/super class. */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IntervalValue other = (IntervalValue) obj;
		if (Double.doubleToLongBits(max) != Double.doubleToLongBits(other.max))
			return false;
		if (Double.doubleToLongBits(min) != Double.doubleToLongBits(other.min))
			return false;
		return true;
	}

	@Override
	public boolean greaterThan(Calculable compare) {
		if (compare instanceof IntegerValue){
		    IntegerValue IntegerValue = (IntegerValue)compare;
		    if(this.min > IntegerValue.getValue())
		    	return true;
		    else
		    	return false;
		}else if (compare instanceof DoubleValue){
			DoubleValue doubleValue = (DoubleValue)compare;
		    if(this.min > doubleValue.getValue())
		    	return true;
		    else
		    	return false;
		}else{
			IntervalValue intervalValue = (IntervalValue)compare;
			IntervalValue sub = (IntervalValue) this.sub(intervalValue);
		    if(sub.getMin() > 0 && sub.getMax() > 0)
		    	return true;
		    else return false;
		}
	}

	@Override
	public boolean lessThan(Calculable compare) {
		if (compare instanceof IntegerValue){
		    IntegerValue IntegerValue = (IntegerValue)compare;
		    if(this.max < IntegerValue.getValue())
		    	return true;
		    else
		    	return false;
		}else if (compare instanceof DoubleValue){
			DoubleValue DoubleValueValue = (DoubleValue)compare;
		    if(this.max < DoubleValueValue.getValue())
		    	return true;
		    else
		    	return false;
		}else{
			IntervalValue intervalValue = (IntervalValue)compare;
			IntervalValue sub = (IntervalValue) this.sub(intervalValue);
		    if(sub.getMin() < 0 && sub.getMax() < 0)
		    	return true;
		    else return false;
		}
	}

	//TODO: Stimmt noch nicht!!! --> Norman!?!?
	public Calculable abs() {
		return new IntervalValue(Math.abs(this.min), Math.abs(this.max));
	}
}
