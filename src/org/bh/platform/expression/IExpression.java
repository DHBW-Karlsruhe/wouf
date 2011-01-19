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
package org.bh.platform.expression;

import java.util.Map;

import org.bh.data.types.Calculable;

/**
 * This interface represents an mathematical expression.
 * 
 * <p>
 * In mathematics, the word expression is a term for any well-formed combination
 * of mathematical symbols
 * (http://en.wikipedia.org/wiki/Expression_%28mathematics%29; 27.11.2009) The
 * expression is described in postfix notation.
 * 
 * Usually (in this context) it is part of an economic equation. Once the
 * Expression Object was created it can be evaluated with several sets of input
 * values
 * 
 * @author Norman
 * @version 0.1, 21/12/2009
 */
public interface IExpression {

	/**
	 * Evaluates the result concerning the given input values.
	 * 
	 * @param inputValues
	 *            Map of economic values
	 * 
	 * @return the result value for this expression, concerning the given
	 *         inputValues as a <code>org.bh.data.types.Calculable</code> object
	 * 
	 * @throws ExpressionException
	 *             the expression exception
	 */
	Calculable evaluate(Map<String, Calculable> inputValues)
			throws ExpressionException;
}
