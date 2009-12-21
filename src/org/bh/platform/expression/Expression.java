package org.bh.platform.expression;

import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;

import org.bh.data.types.Calculable;



/**
 * This class represents an mathematical expression.
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
 * @version 0.1, 21/11/2009
 * @version 0.2, 09/12/2009
 * @version 0.3, 21/12/2009
 */
public class Expression {

    private Object[] instructions;
    Stack<Calculable> operands = new Stack<Calculable>();

    /**
     * Instantiates a new expression.
     * 
     * @param exp
     */
    protected Expression(ArrayList<Object> instructions) {
	this.instructions = instructions.toArray();
    }

    /**
     * This is the external representation of the evaluate Method. Usually the
     * input represents the right side of an economic equation
     * 
     * @param inputValues
     *            Map containing all necessary input values
     * 
     * @return calculable the result as a calculable object (wrapper for Double,
     *         Int or Interval)
     * @throws ExpressionException
     * 
     * @throws ExpressionException
     *             the expression exception
     */
    @SuppressWarnings( { "cast" })
    public Calculable evaluate(Map<String, Calculable> inputValues)
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
