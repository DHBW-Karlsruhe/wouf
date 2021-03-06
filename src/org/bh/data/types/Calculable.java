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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bh.platform.Services;

/**
 * Class for economic calculation with mixed types. Especially Interval
 * arithmetic is relevant.
 * 
 * @author Sebastian
 * @author Norman
 * @author Robert
 * @author Alex
 * @version 0.1, 21.11.2009, Sebastian
 * @version 0.2, unknown, Robert
 * @version 0.3, 21.12.2009, Norman
 * @version 0.4, 22.12.2009, Alex
 * @version 0.5, 22.12.2009, Robert
 * @version 0.6, 28.12.2009, Sebastian (added more needed functions)
 */
public abstract class Calculable implements IValue {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1234704259997706589L;

	/** Pattern for a double value. It matches zero or one minus sign, followed by zero or more digits (0-9), followed by a dot and one or more digits. Leading or trailing spaces will be ignored. Instead of the dot, a comma can be used.  <p> Examples for values are: <ul> <li>1.2 <li>1,2 <li>12.3 <li>12.34 <li>.1 <li>1. <li>12 <li>-1.2 <li>-.1 <li>-1 <li>&nbsp;1.2&nbsp; (spaces before and/or after the value) </ul>  <p> However, these values do not match: <ul> <li>(empty string) <li>1,2 <li>- 1.2 <li>1 <li>abc <li>a 1 b </ul> */
	public static final Pattern DOUBLE_PATTERN = Pattern.compile("^\\s*"
			+ DoubleValue.REGEX + "\\s*$");

	/** Pattern for an integer value. It matches zero or one minus sign, followed by one or more digits. Leading or trailing spaces will be ignored.  <p> Examples for values are: <ul> <li>1 <li>12 <li>-1 <li>&nbsp;1&nbsp; (spaces before and/or after the value) </ul>  <p> However, these values do not match: <ul> <li>(empty string) <li>- 1 <li>1. <li>.1 <li>1.2 <li>abc <li>a 1 b </ul> */
	public static final Pattern INTEGER_PATTERN = Pattern.compile("^\\s*"
			+ IntegerValue.REGEX + "\\s*$");

	/** Pattern for an interval. It matches two double or integer values, divided by a semicolon (;) and enclosed by squared brackets. Spaces are allowed, but not necessary, between the brackets and the digits, between the digits and the semicolon as well as before and after the interval.  <p> Examples for values are: <ul> <li>[1.2;3.4] <li>[-1.2;3.4] <li>[ 1.2;3.4] <li>[1.2;3.4 ] <li>[ 1.2 ; 3.4 ] <li>[ 1 ; 2 ] <li>[ 1.2 ;3 ] <li>[&nbsp;&nbsp;1.2;3.4] (two spaces between bracket and digits) <li>&nbsp;[ 1 ; 2 ]&nbsp; (spaces before and/or after the interval) </ul>  <p> However, these values do not match: <ul> <li>(empty string) <li>1. <li>.1 <li>1. <li>1.2 <li>abc <li>a 1 b <li>[;] <li>[1.2;] </ul> */
	public static final Pattern INTERVAL_PATTERN = Pattern
			.compile("^\\s*\\[\\s*(" + DoubleValue.REGEX + "|"
					+ IntegerValue.REGEX + ")\\s*;\\s*(" + DoubleValue.REGEX
					+ "|" + IntegerValue.REGEX + ")\\s*\\]\\s*$");

	/**
	 * Adds summand to the current Calculable.
	 * 
	 * @param summand the summand
	 * 
	 * @return the result as a new calculable instance
	 */
	public abstract Calculable add(Calculable summand);

	/**
	 * Subtracts the subtrahend from the current Calculable.
	 * 
	 * @param subtrahend the subtrahend
	 * 
	 * @return the result as a new calculable instance
	 */
	public abstract Calculable sub(Calculable subtrahend);

	/**
	 * Divides the current Calculable through the divisor.
	 * 
	 * @param divisor the divisor
	 * 
	 * @return the result as a new Calculable instance
	 */
	public abstract Calculable div(Calculable divisor);


	/**
	 * Checks whether this Claculable object (value) is less than the compare calculable object (value)
	 * 
	 * @param compare the compare
	 * 
	 * @return true, if this Calculable is less than the compare Calculable
	 */
	public abstract boolean lessThan(Calculable compare);

	/**
	 * Counterpart to lessThan
	 * 
	 * @param compare the compare
	 * 
	 * @return true, if this Calculable is grater than the compare Calculable
	 */
	public abstract boolean greaterThan(Calculable compare);

	/**
	 * Multiplies the current Calculable with the multiplicand.
	 * 
	 * @param multiplicand the multiplicand
	 * 
	 * @return the result as a new Calculable instance
	 */
	public abstract Calculable mul(Calculable multiplicand);

	/**
	 * Returns an Calculable instance with the absolute value of this instance.
	 * 
	 * @return the calculable
	 */
	public abstract Calculable abs();

	/**
	 * Returns the square root of the current Calculable.
	 * 
	 * @return the result as a new Calculable instance
	 */
	public abstract Calculable sqrt();

	/**
	 * Returns the power of the exponent of the current Calculable.
	 * 
	 * @param exponent the exponent
	 * 
	 * @return the result as a new Calculable instance
	 */
	public abstract Calculable pow(Calculable exponent);
	
	/**
	 * Converts the current Calculable to an instance of java.lang.Number.
	 * 
	 * @return the number
	 */
	public abstract Number toNumber();
	
	/**
	 * Gets the minimum.
	 * 
	 * @return the min
	 */
	public abstract double getMin();
	
	/**
	 * Gets the maximum.
	 * 
	 * @return the max
	 */
	public abstract double getMax();
	
	/**
	 * Gets the min max diff.
	 * 
	 * @return the min max diff
	 */
	public abstract double getMinMaxDiff();

	/* Specified by interface/super class. */
	@Override
	public abstract Calculable clone();

	/**
	 * Parses a given String to create a new Calculable instance.
	 * 
	 * @param s the String
	 * 
	 * @return the Calculable
	 */
	public static Calculable parseCalculable(String s) {
		double value = Services.stringToDouble(s);
		if (!Double.isNaN(value)) {
			return new DoubleValue(value);
		} else {
			// TODO might not work for big numbers
			Matcher intervalMatcher = INTERVAL_PATTERN.matcher(s);
			if (intervalMatcher.matches()) {
				String min = intervalMatcher.group(1);
				String max = intervalMatcher.group(2);
				return new IntervalValue(Double.parseDouble(min), Double
						.parseDouble(max));
			}
		}
		return null;
	}

	/**
	 * Calculates the sum of all parameters.
	 * 
	 * @param summands the summands
	 * 
	 * @return Sum of all parameters as new Calculable
	 */
	public Calculable add(Calculable... summands) {
		Calculable result = this;
		for (Calculable summand : summands) {
			result = result.add(summand);
		}
		return result;
	}
	
	/**
	 * Calculates the difference of all parameters.
	 * 
	 * @param subtrahends the subtrahends
	 * 
	 * @return sum of all parameters as new Calculable
	 */
	public Calculable sub(Calculable... subtrahends) {
		Calculable result = this;
		for (Calculable subtrahend : subtrahends) {
			result = result.sub(subtrahend);
		}
		return result;
	}

	/**
	 * Calculates the product of all parameters.
	 * 
	 * @param factors the factors
	 * 
	 * @return Product of all parameters as new Calculable
	 */
	public Calculable mul(Calculable... factors) {
		Calculable result = this;
		for (Calculable factor : factors) {
			result = result.mul(factor);
		}
		return result;
	}

	/**
	 * Checks whether the (value) difference of the given calculable objects is
	 * less than the limit.
	 * 
	 * @param c calculable to compare
	 * @param limit the limit of difference
	 * 
	 * @return true if the difference is less than the limit
	 */
	public abstract boolean diffToLess(Calculable c, double limit);

	/* Specified by interface/super class. */
	@Override
	public abstract String toString();

}
