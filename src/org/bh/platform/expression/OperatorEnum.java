package org.bh.platform.expression;

/**
 * Enumeration to list all available operators.
 * 
 * @author Norman
 * @version 0.1 ; 04.12.2009
 */
public enum OperatorEnum {
	/**
	 * All implemented mathematical operations.
	 */
	add("plus"), sub("minus"), div("divide"), mul("times"), pow("power"), sqrt(
			"root");

	/**
	 * @param tagName
	 *            MathMl tag name
	 * @return op the corresponding operator
	 */
	public static OperatorEnum getOperator(final String tagName) {
		for (OperatorEnum op : values()) {
			if (tagName.equals(op.tagName)) {
				return op;
			}
		}
		return null;
	}

	/**
	 * MathMl tag name.
	 */
	private final String tagName;

	/**
	 * Standard constructor with tag Name.
	 * 
	 * @param tagName
	 *            MathMl tag name
	 */
	private OperatorEnum(final String tagName) {
		this.tagName = tagName;
	}
}
