package org.bh.platform.expression;

import org.w3c.dom.Node;

public interface IExpressionFactoy {

	/** The instance of the currently used IExpressionFactory implementation. */
	IExpressionFactoy instance = new ExpressionFactoryImpl();
	
	IExpression createExpression(Node exp) throws ExpressionException;
}
