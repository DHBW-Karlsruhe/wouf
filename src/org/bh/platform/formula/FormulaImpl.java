package org.bh.platform.formula;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import net.sourceforge.jeuclid.LayoutContext;
import net.sourceforge.jeuclid.context.LayoutContextImpl;
import net.sourceforge.jeuclid.converter.Converter;
import net.sourceforge.jeuclid.swing.JMathComponent;

import org.apache.log4j.Logger;
import org.bh.data.types.Calculable;
import org.bh.platform.expression.Expression;
import org.bh.platform.expression.ExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class is able to calculate formulas represented in MathMl combined with
 * a set of input values.
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
 * @version 0.1, 01.12.2009
 * @version 0.2, 21.12.2009
 */
public class FormulaImpl implements IFormula {

	private static final int FONT_SIZE = 24;
	
	private static final Logger log = Logger.getLogger(FormulaImpl.class);

	/** The formula. */
	private Document formulaDoc;

	/** The left hand side. */
	private String leftHandSide;

	/** The right hand side. */
	private Expression rightHandSide;

	private String name;

	/**
	 * Instantiates a new formula.
	 * 
	 * @param formula
	 *            the formula
	 * @throws FormulaException
	 *             the formula exception
	 */
	protected FormulaImpl(String name, Document formula, String leftHandSide,
			Expression rightHandSide) throws FormulaException {
		this.name = name;
		this.formulaDoc = formula;
		this.leftHandSide = leftHandSide;
		this.rightHandSide = rightHandSide;
	}

	@Override
	public String getName() {
		return name;
	}

	/**
	 * Gets the left hand side.
	 * 
	 * @return the left hand side
	 */
	@Override
	public String getLeftHandSideKey() {
		return leftHandSide;
	}

	/**
	 * Determine left hand side.
	 * 
	 * @param inputValues
	 *            the input values
	 * 
	 * @return the map< string, calculable>
	 * 
	 */
	@Override
	public Calculable determineLeftHandSideValue(
			Map<String, Calculable> inputValues) {
		try {
			return rightHandSide.evaluate(inputValues);
		} catch (ExpressionException e) {
			log.fatal("Error while evaluating expression", e);
			return null;
		}
	}

	@Override
	public void determineLeftHandSideValueToInpValues(
			Map<String, Calculable> inputValues) {
		inputValues.put(leftHandSide, determineLeftHandSideValue(inputValues));
		return;
	}

	/**
	 * Gets the icon.
	 * 
	 * @return the icon
	 */
	@Override
	public Icon getIcon() {
		Converter c = Converter.getInstance();
		LayoutContext lc = LayoutContextImpl.getDefaultLayoutContext();
		try {
			BufferedImage bi = c.render(formulaDoc, lc);
			return new ImageIcon(bi);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Icon getIconForInputValues(Map<String, Calculable> inputValues) {
		Converter c = Converter.getInstance();
		LayoutContext lc = LayoutContextImpl.getDefaultLayoutContext();
		try {
			BufferedImage bi = c.render(replaceCIwithCN(inputValues), lc);
			return new ImageIcon(bi);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Gets the formula rendering as component.
	 * 
	 * @return the formula rendering as component.
	 */
	@Override
	public JMathComponent getMathComponent() {
		JMathComponent mathComponent = new JMathComponent();
		mathComponent.setFontSize(FONT_SIZE);
		mathComponent.setDocument(formulaDoc);
		return mathComponent;
	}

	@Override
	public JMathComponent getMathComponentForInputValues(
			Map<String, Calculable> inputValues) {
		JMathComponent mathComponent = new JMathComponent();
		mathComponent.setFontSize(FONT_SIZE);
		mathComponent.setDocument(replaceCIwithCN(inputValues));
		return mathComponent;
	}

	@Override
	public void setFormulaToJMathComponent(JMathComponent mathCompExt) {
		mathCompExt.setFontSize(FONT_SIZE);
		mathCompExt.setDocument(formulaDoc);
	}

	
	@Override
	public void setInputValuesToJMathComponent(
			Map<String, Calculable> inputValues, JMathComponent mathCompExt) {
		mathCompExt.setFontSize(FONT_SIZE);
		mathCompExt.setDocument(replaceCIwithCN(inputValues));
	}

	private Document replaceCIwithCN(Map<String, Calculable> inputValues) {
		determineLeftHandSideValueToInpValues(inputValues);
		Document formulaDoc2 = (Document) formulaDoc.cloneNode(true);
		NodeList variables = formulaDoc2.getElementsByTagName("ci");
		while (variables.getLength() > 0) {
			Node node = variables.item(0);
			Node newNode = formulaDoc2.createElementNS(node.getNamespaceURI(),
					"cn");
			newNode.setTextContent(inputValues.get(node.getTextContent())
					.toString());
			node.getParentNode().replaceChild(newNode, node);
		}
		return formulaDoc2;
	}
}
