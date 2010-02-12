package org.bh.platform.expression;

import org.w3c.dom.Node;


/**
 * Interface for an expression factory
 * 
 * @author Norman
 * @version 0.1, 21/11/2009
 */
public interface IExpressionFactoy {

	/** The instance of the currently used IExpressionFactory implementation. */
	IExpressionFactoy instance = new ExpressionFactoryImpl();
	
	IExpression createExpression(Node exp) throws ExpressionException;
}
