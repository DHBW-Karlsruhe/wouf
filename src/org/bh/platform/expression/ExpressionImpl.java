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
