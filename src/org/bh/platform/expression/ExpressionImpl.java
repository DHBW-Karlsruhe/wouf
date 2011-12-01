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
package org.bh.platform.expression;

import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;

import org.bh.data.types.Calculable;

/**
 * This class implements the mathematical expression inteface IExpression.
 * 
 * <p>
 * 
 * @see IExpression
 * @author Norman
 * @version 0.1, 21/11/2009
 * @version 0.2, 09/12/2009
 * @version 0.3, 21/12/2009
 */
public class ExpressionImpl implements IExpression {

	/** The instructions in ascending order to evaluate the result (postfix notation). */
	private Object[] instructions;

	/** The operand stack used to evaluate the result. */
	private Stack<Calculable> operands = new Stack<Calculable>();

	/**
	 * Instantiates a new expression.
	 * 
	 * @param instructions the instructions in ascending order to evaluate the result
	 */
	protected ExpressionImpl(final ArrayList<Object> instructions) {
		this.instructions = instructions.toArray();
	}

	/* Specified by interface/super class. */
	@Override
	public final Calculable evaluate(final Map<String, Calculable> inputValues)
			throws ExpressionException {
		Calculable op1;
		Calculable op2;
		operands.clear();
		for (Object next : instructions) {
			if (next instanceof String) {
				operands.push(inputValues.get((String) next));
			} else if (next instanceof Calculable) {
				operands.push((Calculable) next);
			} else {
				switch ((OperatorEnum) next) {
				case sqrt:
					operands.push(operands.pop().sqrt());
					break;
				case pow:
					op2 = operands.pop();
					op1 = operands.pop();
					operands.push(op1.pow(op2));
					break;
				case add:
					op2 = operands.pop();
					op1 = operands.pop();
					operands.push(op1.add(op2));
					break;
				case sub:
					op2 = operands.pop();
					op1 = operands.pop();
					operands.push(op1.sub(op2));
					break;
				case mul:
					op2 = operands.pop();
					op1 = operands.pop();
					operands.push(op1.mul(op2));
					break;
				case div:
					op2 = operands.pop();
					op1 = operands.pop();
					operands.push(op1.div(op2));
					break;
				default:
					throw new ExpressionException("unsupported operator "
							+ next);
				}
			}
		}
		return operands.pop();
	}
}
