/*******************************************************************************
 * Copyright 2011: Matthias Beste, Hannes Bischoff, Lisa Doerner, Victor Guettler, Markus Hattenbach, Tim Herzenstiel, Günter Hesse, Jochen Hülß, Daniel Krauth, Lukas Lochner, Mark Maltring, Sven Mayer, Benedikt Nees, Alexandre Pereira, Patrick Pfaff, Yannick Rödl, Denis Roster, Sebastian Schumacher, Norman Vogel, Simon Weber 
 *
 * Copyright 2010: Anna Aichinger, Damian Berle, Patrick Dahl, Lisa Engelmann, Patrick Groß, Irene Ihl, Timo Klein, Alena Lang, Miriam Leuthold, Lukas Maciolek, Patrick Maisel, Vito Masiello, Moritz Olf, Ruben Reichle, Alexander Rupp, Daniel Schäfer, Simon Waldraff, Matthias Wurdig, Andreas Wußler
 *
 * Copyright 2009: Manuel Bross, Simon Drees, Marco Hammel, Patrick Heinz, Marcel Hockenberger, Marcus Katzor, Edgar Kauz, Anton Kharitonov, Sarah Kuhn, Michael Löckelt, Heiko Metzger, Jacqueline Missikewitz, Marcel Mrose, Steffen Nees, Alexander Roth, Sebastian Scharfenberger, Carsten Scheunemann, Dave Schikora, Alexander Schmalzhaf, Florian Schultze, Klaus Thiele, Patrick Tietze, Robert Vollmer, Norman Weisenburger, Lars Zuckschwerdt
 *
 * Copyright 2008: Camil Bartetzko, Tobias Bierer, Lukas Bretschneider, Johannes Gilbert, Daniel Huser, Christopher Kurschat, Dominik Pfauntsch, Sandra Rath, Daniel Weber
 *
 * This program is free software: you can redistribute it and/or modify it un-der the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT-NESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package org.bh.data.types;

import org.bh.platform.Services;

/**
 * Calculable implementation for floating point values.
 * 
 * @author Sebastian
 * @author Norman
 * 
 * @version 0.1, 21.11.2009, Sebastian
 * @version 0.3, 21.12.2009, Norman
 */
public class DoubleValue extends Calculable  {
	private static final long serialVersionUID = 6524989414667796793L;
	public static final String REGEX = "-?(?:[0-9]*[.,][0-9]+|[0-9]+[.,][0-9]*)+";

	/** The value. */
	protected final double value;

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
		if (summand instanceof DoubleValue) {
			return new DoubleValue(value + ((DoubleValue) summand).value);
		} else if (summand instanceof IntegerValue) {
			return new DoubleValue(value + ((IntegerValue) summand).value);
		} else if (summand instanceof IntervalValue) {
			return new IntervalValue(value, value).add(summand);
		} else {
			return null;
		}
	}

	/* Specified by interface/super class. */
	@Override
	public final Calculable sub(final Calculable subtrahend) {
		if (subtrahend instanceof DoubleValue) {
			return new DoubleValue(value - ((DoubleValue) subtrahend).value);
		} else if (subtrahend instanceof IntegerValue) {
			return new DoubleValue(value - ((IntegerValue) subtrahend).value);
		} else if (subtrahend instanceof IntervalValue) {
			return new IntervalValue(value, value).sub(subtrahend);
		} else {
			return null;
		}
	}

	/* Specified by interface/super class. */
	@Override
	public final Calculable mul(final Calculable multiplicand) {
		if (multiplicand instanceof DoubleValue) {
			return new DoubleValue(value * ((DoubleValue) multiplicand).value);
		} else if (multiplicand instanceof IntegerValue) {
			return new DoubleValue(value * ((IntegerValue) multiplicand).value);
		} else if (multiplicand instanceof IntervalValue) {
			return new IntervalValue(value, value).mul(multiplicand);
		} else {
			return null;
		}
	}

	/* Specified by interface/super class. */
	@Override
	public final Calculable div(final Calculable divisor) {
		if (divisor instanceof DoubleValue) {
			return new DoubleValue(value / ((DoubleValue) divisor).value);
		} else if (divisor instanceof IntegerValue) {
			return new DoubleValue(value / ((IntegerValue) divisor).value);
		} else if (divisor instanceof IntervalValue) {
			return new IntervalValue(value, value).div(divisor);
		} else {
			return null;
		}
	}

	/* Specified by interface/super class. */
	@Override
	public final Calculable pow(final Calculable exponent) {
		if (exponent instanceof DoubleValue) {
			return new DoubleValue(Math.pow(value,
					((DoubleValue) exponent).value));
		} else if (exponent instanceof IntegerValue) {
			return new DoubleValue(Math.pow(value,
					((IntegerValue) exponent).value));
		} else if (exponent instanceof IntervalValue) {
			return new IntervalValue(value, value).pow(exponent);
		} else {
			return null;
		}
	}

	/* Specified by interface/super class. */
	@Override
	public final Calculable sqrt(){
			return new DoubleValue(Math.sqrt(value));
	}

	@Override
	public boolean greaterThan(Calculable compare) {
		if (compare instanceof DoubleValue) {
			DoubleValue doubleValue = (DoubleValue) compare;
			return (this.getValue() > doubleValue.getValue());
		} else if (compare instanceof IntegerValue) {
			IntegerValue integerValue = (IntegerValue) compare;
			return (this.getValue() > integerValue.getValue());
		} else {
			return new IntervalValue(value, value).greaterThan(compare);
		}
	}

	/* Specified by interface/super class. */
	@Override
	public boolean lessThan(Calculable compare) {
		if (compare instanceof DoubleValue) {
			DoubleValue doubleValue = (DoubleValue) compare;
			return (this.getValue() < doubleValue.getValue());
		} else if (compare instanceof IntegerValue) {
			IntegerValue integerValue = (IntegerValue) compare;
			return (this.getValue() < integerValue.getValue());
		} else {
			return new IntervalValue(value, value).lessThan(compare);
		}
	}

	@Override
	public Calculable abs() {
		return new DoubleValue(Math.abs(this.value));
	}

	@Override
	public final Calculable clone() {
		return new DoubleValue(value);
	}

	/* Specified by interface/super class. */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(value);
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
		DoubleValue other = (DoubleValue) obj;
		if (Double.doubleToLongBits(value) != Double
				.doubleToLongBits(other.value))
			return false;
		return true;
	}

	@Override
	public final String toString() {
		return Services.numberToString(value);
	}

	@Override
	public boolean diffToLess(Calculable c, double limit) {
		if (c instanceof DoubleValue) {
			return Math.abs(value - ((DoubleValue) c).value) < limit;
		} else if (c instanceof IntegerValue) {
			return Math.abs(value - ((IntegerValue) c).value) < limit;
		} else if (c instanceof IntervalValue) {
			return new IntervalValue(value, value).diffToLess(c, limit);
		} else {
			throw new UnsupportedOperationException("Unsupported");
		}
	}

	@Override
	public Number toNumber() {
		return value;
	}

	@Override
	public double getMin() {
		return value;
	}
	
	@Override
	public double getMax() {
		return value;
	}	

	@Override
	public double getMinMaxDiff() {
		return 0;
	}
}
