package org.bh.platform.expression;

import java.util.ArrayList;

import org.bh.data.types.Calculable;
import org.w3c.dom.Node;

/**
 * This class is a factory for mathematical expression objects.
 * 
 * <p>
 * The factory creates expression objects from MathMl nodes, which can be
 * evaluated.
 * 
 * @author Norman
 * @version 0.1, 21/11/2009
 * @version 0.2, 09/12/2009
 * @version 0.3, 21/12/2009
 */
public class ExpressionFactoryImpl implements IExpressionFactoy {

	/**
	 * Instantiates a new expression factory impl.
	 */
	protected ExpressionFactoryImpl() {
		// noop
	}

	/* Specified by interface/super class. */
	@Override
	public final IExpression createExpression(final Node exp)
			throws ExpressionException {

		ArrayList<Object> instructions = fillInstructionList(exp);

		return new ExpressionImpl(instructions);
	}

	/**
	 * Fills instruction list.
	 * Converts the MathMl content prefix notaion to a postfix notation
	 * 
	 * @param expNode the expression node
	 * 
	 * @return the array list< object> containing the evauation order
	 * 
	 * @throws ExpressionException the expression exception
	 */
	private ArrayList<Object> fillInstructionList(final Node expNode)
			throws ExpressionException {
		Node operatorNode = null;
		Node operand1Node = null;
		Node operand2Node = null;

		OperatorEnum operator = null;
		ArrayList<Object> instructions = new ArrayList<Object>();

		// check whether Node recExp is of element <apply>
		if (!isExpression(expNode)) {
			throw new ExpressionException(
					"Node is not an expression. Expressions tag name is <apply>");
		}

		// check whether operator is valid
		operatorNode = expNode.getFirstChild().getNextSibling();
		if ((operator = OperatorEnum.getOperator(operatorNode.getNodeName())) == null) {
			throw new ExpressionException(
					"First child of an content MathMl expression must be an operator.\n"
							+ operatorNode.getLocalName()
							+ "is not an operator.\n"
							+ "Valid operators are located in"
							+ OperatorEnum.class);
		}

		// check whether first operand is valid
		operand1Node = operatorNode.getNextSibling().getNextSibling();
		if (operand1Node == null) {
			throw new ExpressionException(
					"No operand available. At least one operand required (root)");
		} else if (isExpression(operand1Node)) {
			instructions.addAll(fillInstructionList(operand1Node));
		} else if (operand1Node.getNodeName().equals("ci")) {
			instructions.add(operand1Node.getFirstChild().getNodeValue());
		} else if (operand1Node.getNodeName().equals("cn")) {
			instructions.add(Calculable.parseCalculable(operand1Node
					.getFirstChild().getNodeValue()));
		} else {
			throw new ExpressionException("Operator1 has an unknown type");
		}

		/*
		 * check whether second operand is valid check whether no third operand
		 * exists calculate operands first they are expressions as well
		 */
		operand2Node = operand1Node.getNextSibling().getNextSibling();
		if (operand2Node == null && operator != OperatorEnum.sqrt) {
			throw new ExpressionException("Operator" + operator
					+ "needs two arguments");
		} else if (operator == OperatorEnum.sqrt && operand2Node != null) {
			throw new ExpressionException("Operator" + operator
					+ "has only one argument");
		} else if (operand2Node != null) {
			if (operand2Node.getNextSibling().getNextSibling() != null) {
				throw new ExpressionException(
						"No operator with three argument exists");
			} else if (isExpression(operand2Node)) {
				instructions.addAll(fillInstructionList(operand1Node));
			} else if (operand2Node.getNodeName().equals("ci")) {
				instructions.add(operand2Node.getFirstChild().getNodeValue());
			} else if (operand2Node.getNodeName().equals("cn")) {
				instructions.add(Calculable.parseCalculable(operand2Node
						.getFirstChild().getNodeValue()));
			} else {
				throw new ExpressionException("Operator2 has an unknown type");
			}
		}
		instructions.add(operator);
		return instructions;
	}

	/**
	 * Checks if is the given node can be an expression.
	 * 
	 * @param n the expression node
	 * 
	 * @return true, if node n is expression
	 */
	private boolean isExpression(final Node n) {
		return n.getNodeName().equals("apply");
	}
}
