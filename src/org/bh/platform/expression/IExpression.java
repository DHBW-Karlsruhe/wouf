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
