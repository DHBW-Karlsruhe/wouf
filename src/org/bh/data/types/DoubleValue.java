package org.bh.data.types;

/**
 * Calculable implementation for floating point values.
 * 
 * @author Sebastian
 * @author Norman
 * 
 * @version 0.1, 21.11.2009, Sebastian
 * @version 0.3, 21.12.2009, Norman
 */
public class DoubleValue extends Calculable {

	/** The value. */
	protected double value;

	/**
	 * Instantiates a new double value.
	 * 
	 * @param value
	 *            the value
	 */
	public DoubleValue(double value) {
		this.value = value;
	}

	/**
	 * Gets the value.
	 * 
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

	/* Specified by interface/super class. */
	@Override
	public final Calculable add(final Calculable summand) {
		if (summand instanceof IntegerValue) {
			return new DoubleValue(value + ((IntegerValue) summand).value);
		} else if (summand instanceof DoubleValue) {
			return new DoubleValue(value + ((DoubleValue) summand).value);
		} else if (summand instanceof IntervalValue) {
			return new IntervalValue(value, value).add(summand);
		} else {
			return null;
		}
	}

	/* Specified by interface/super class. */
	@Override
	public final Calculable sub(final Calculable subtrahend) {
		if (subtrahend instanceof IntegerValue) {
			return new DoubleValue(value
					- ((IntegerValue) subtrahend).value);
		} else if (subtrahend instanceof DoubleValue) {
			return new DoubleValue(value
					- ((DoubleValue) subtrahend).value);
		} else if (subtrahend instanceof IntervalValue) {
			return new IntervalValue(value, value).sub(subtrahend);
		} else {
			return null;
		}
	}

	/* Specified by interface/super class. */
	@Override
	public final Calculable mul(final Calculable multiplicand) {
		if (multiplicand instanceof IntegerValue) {
			return new DoubleValue(value
					* ((IntegerValue) multiplicand).value);
		} else if (multiplicand instanceof DoubleValue) {
			return new DoubleValue(value
					* ((DoubleValue) multiplicand).value);
		} else if (multiplicand instanceof IntervalValue) {
			return new IntervalValue(value, value).mul(multiplicand);
		} else {
			return null;
		}
	}

	/* Specified by interface/super class. */
	@Override
	public final Calculable div(final Calculable divisor) {
		if (divisor instanceof IntegerValue) {
			return new DoubleValue(value / ((IntegerValue) divisor).value);
		} else if (divisor instanceof DoubleValue) {
			return new DoubleValue(value / ((DoubleValue) divisor).value);
		} else if (divisor instanceof IntervalValue) {
			return new IntervalValue(value, value).div(divisor);
		} else {
			return null;
		}
	}

	/* Specified by interface/super class. */
	@Override
	public final Calculable pow(final Calculable exponent) {
		if (exponent instanceof IntegerValue) {
			return new DoubleValue(Math.pow(value, ((IntegerValue) exponent).value));
		} else if (exponent instanceof DoubleValue) {
			return new DoubleValue(Math.pow(value, ((DoubleValue) exponent).value));
		} else if (exponent instanceof IntervalValue) {
			return new IntervalValue(value, value).pow(exponent);
		} else {
			return null;
		}
	}

	/* Specified by interface/super class. */
	@Override
	public final Calculable sqrt() {
		return new DoubleValue(Math.sqrt(value));
	}

	/* Specified by interface/super class. */
	@Override
	public final String toString() {
		return "" + value;
	}

	/* Specified by interface/super class. */
	@Override
	public final Calculable clone() {
		return new DoubleValue(value);
	}

}
