package org.bh.data.types;

import javax.help.UnsupportedOperationException;

/**
 * Calculable implementation for integer values.
 * 
 * @author Sebastian
 * @author Norman
 * 
 * @version 0.1, 21.11.2009, Sebastian
 * @version 0.3, 21.12.2009, Norman
 */
public class IntegerValue extends Calculable {
	private static final long serialVersionUID = -1099799716306283566L;
	public static final String REGEX = "-?[0-9]+";

	/** The value. */
	protected final int value;

	/**
	 * Instantiates a new integer value.
	 * 
	 * @param value
	 *            the value
	 */
	public IntegerValue(int value) {
		this.value = value;
	}

	/* Specified by interface/super class. */
	@Override
	public final Calculable add(final Calculable summand) {
		if (summand instanceof IntegerValue) {
			return new IntegerValue(value + ((IntegerValue) summand).value);
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
			return new IntegerValue(value - ((IntegerValue) subtrahend).value);
		} else if (subtrahend instanceof DoubleValue) {
			return new DoubleValue(value - ((DoubleValue) subtrahend).value);
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
			return new IntegerValue(value * ((IntegerValue) multiplicand).value);
		} else if (multiplicand instanceof DoubleValue) {
			return new DoubleValue(value * ((DoubleValue) multiplicand).value);
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
			return new DoubleValue((double) value
					/ ((IntegerValue) divisor).value);
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
			return new DoubleValue(Math.pow(value,
					((IntegerValue) exponent).value));
		} else if (exponent instanceof DoubleValue) {
			return new DoubleValue(Math.pow(value,
					((DoubleValue) exponent).value));
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

	/**
	 * Gets the value.
	 * 
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/* Specified by interface/super class. */
	@Override
	public String toString() {
		return "" + value;
	}

	/* Specified by interface/super class. */
	@Override
	public Calculable clone() {
		return new IntegerValue(value);
	}

	@Override
	public boolean greaterThan(Calculable compare) {
		if (compare instanceof IntegerValue) {
			IntegerValue integerValue = (IntegerValue) compare;
			if (this.getValue() > integerValue.getValue()) {
				return true;
			}
			return false;
		} else if (compare instanceof DoubleValue) {
			DoubleValue doubleValue = (DoubleValue) compare;
			if (this.getValue() > doubleValue.getValue()) {
				return true;
			}
			return false;
		} else {
			return new IntervalValue(value, value).greaterThan(compare);
		}
	}

	@Override
	public boolean lessThan(Calculable compare) {
		if (compare instanceof IntegerValue) {
			IntegerValue integerValue = (IntegerValue) compare;
			if (this.getValue() < integerValue.getValue()) {
				return true;
			}
			return false;
		} else if (compare instanceof DoubleValue) {
			DoubleValue doubleValue = (DoubleValue) compare;
			if (this.getValue() < doubleValue.getValue()) {
				return true;
			}
			return false;
		} else {
			return new IntervalValue(value, value).lessThan(compare);
		}
	}

	@Override
	public Calculable abs() {
		return new IntegerValue(Math.abs(this.value));
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
		IntegerValue other = (IntegerValue) obj;
		if (value != other.value)
			return false;
		return true;
	}

	/* Specified by interface/super class. */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
		return result;
	}

	@Override
	public boolean diffToLess(Calculable c, double limit) {
		if (c instanceof IntegerValue) {
			return Math.abs((double) value - ((IntegerValue) c).value) < limit;
		} else if (c instanceof DoubleValue) {
			return Math.abs(value - ((DoubleValue) c).value) < limit;
		} else if (c instanceof IntervalValue) {
			return new IntervalValue(value, value).diffToLess(c, limit);
		} else {
			throw new UnsupportedOperationException("Unsupported");
		}
	}

	@Override
	public Number parse() {
		return new Integer(this.value);
	}
}
