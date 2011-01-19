/*******************************************************************************
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
package org.bh.platform.formula;

import java.util.Map;

import javax.swing.Icon;

import net.sourceforge.jeuclid.swing.JMathComponent;

import org.bh.data.types.Calculable;

/**
 * This interface describes mathematical formulas and provides methods to
 * determine the result for given input values.
 * 
 * <p>
 * In mathematics, a formula is a key to solve an equation with variables. For
 * example, the problem of determining the volume of a sphere is one that
 * requires a significant amount of integral calculus to solve. However, having
 * done this once, mathematicians can produce a formula to describe the volume
 * in terms of some other parameter (the radius for example). <br>
 * This particular formula is: <br>
 * V = (4/3) * pi * r^3 <br>
 * Having determined this result, and having a sphere of which we know the
 * radius we can quickly and easily determine the volume.
 * (http://en.wikipedia.org/wiki/Formula, 01.12.2009)
 * 
 * @author Norman
 * @version 0.1, 21.12.2009
 * @version 0.2, 21.12.2009
 */
public interface IFormula {

	/**
	 * Gets the name of the formula.
	 * 
	 * @return the name
	 */
	String getName();

	/**
	 * Gets the (economic) key of left hand side.
	 * 
	 * @return the left hand side key (e.g. EK)
	 */
	String getLeftHandSideKey();

	/**
	 * Determine left hand side value.
	 * 
	 * @param inputValues
	 *            Map of economic values
	 * 
	 * @return the value for the left hand side key for given inputValues as a
	 *         <code>org.bh.data.types.Calculable</code> object
	 */
	Calculable determineLeftHandSideValue(Map<String, Calculable> inputValues);

	/**
	 * @see #determineLeftHandSideValue(Map) The difference is that this method
	 *      writes the result object to the inputValues map with the
	 *      {@link #getLeftHandSideKey()} key.
	 * 
	 * @param inputValues
	 *            Map of economic values
	 */
	void determineLeftHandSideValueToInpValues(
			Map<String, Calculable> inputValues);

	/**
	 * Gets the formula as an instance of <code>java.swing.Icon</code>.
	 * 
	 * @return the formula as an icon
	 */
	Icon getIcon();

	/**
	 * @see #getIcon() The difference is that the variables will be replaced
	 *      with the given input values.
	 * 
	 * @param inputValues
	 *            Map of economic values
	 * 
	 * @return the formula as an icon (with given input values instead of
	 *         economic value keys).
	 */
	Icon getIconForInputValues(Map<String, Calculable> inputValues);

	/**
	 * Gets the formula as an
	 * <code>net.sourceforge.jeculid.swing.JMathComponent</code>.
	 * 
	 * @return the formula as a JMathComponent (JComponent)
	 */
	JMathComponent getJMathComponent();

	/**
	 * @see #getJMathComponent() The difference is that the variables will be
	 *      replaced with the given input values.
	 * 
	 * @param inputValues
	 *            Map of economic values
	 * 
	 * @return the formula as a JMathComponent (JComponent) (with given input
	 *         values instead of economic value keys)
	 */
	JMathComponent getJMathComponentForInputValues(
			Map<String, Calculable> inputValues);

	/**
	 * @see #getJMathComponent() Sets formula as content to an already existing
	 *      JMathComponent .
	 * 
	 * @param mathCompExt
	 *            already existing JMathComponent
	 */
	void setFormulaToJMathComponent(JMathComponent mathCompExt);

	/**
	 * @see #getJMathComponentForInputValues(Map) Sets formula as content to an
	 *      already existing JMathComponent .
	 * 
	 * @param inputValues
	 *            Map of economic values
	 * @param mathCompExt
	 *            already existing JMathComponent
	 */
	void setInputValuesToJMathComponent(Map<String, Calculable> inputValues,
			JMathComponent mathCompExt);
}
