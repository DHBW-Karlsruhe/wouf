package org.bh.platform.formula;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import net.sourceforge.jeuclid.LayoutContext;
import net.sourceforge.jeuclid.context.LayoutContextImpl;
import net.sourceforge.jeuclid.converter.Converter;
import net.sourceforge.jeuclid.swing.JMathComponent;

import org.apache.log4j.Logger;
import org.bh.data.types.Calculable;
import org.bh.platform.expression.ExpressionException;
import org.bh.platform.expression.IExpression;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class is an implementation of <code>org.bh.platform.IFormula</code>.
 * 
 * @see IFormula
 * 
 * @author Norman
 * @version 0.1, 01.12.2009
 * @version 0.2, 21.12.2009
 * @version 0.3, 21.12.2009
 */
public class FormulaImpl implements IFormula {

	/** The Constant FONT_SIZE. */
	private static final int FONT_SIZE = 24;

	/** The log4j logger instance corresponding to this class. */
	private static final Logger LOG = Logger.getLogger(FormulaImpl.class);

	/** The name (id) of this formula. */
	private String name;

	/** The representation of this formula as a MathMl document. */
	private Document formulaDoc;

	/** The left hand side of this formula. */
	private String leftHandSide;

	/** The right hand side of this formula. */
	private IExpression rightHandSide;

	/**
	 * Instantiates a new formula impl.
	 * 
	 * @param name
	 *            the name
	 * @param formula
	 *            the formula
	 * @param leftHandSide
	 *            the left hand side
	 * @param rightHandSide2
	 *            the right hand side
	 * 
	 * @throws FormulaException
	 *             the formula exception
	 */
	protected FormulaImpl(final String name, final Document formula,
			final String leftHandSide, final IExpression rightHandSide2)
			throws FormulaException {
		this.name = name;
		this.formulaDoc = formula;
		this.leftHandSide = leftHandSide;
		this.rightHandSide = rightHandSide2;
	}

	/* Specified by interface/super class. */
	@Override
	public final String getName() {
		return name;
	}

	/* Specified by interface/super class. */
	@Override
	public final String getLeftHandSideKey() {
		return leftHandSide;
	}

	/* Specified by interface/super class. */
	@Override
	public final Calculable determineLeftHandSideValue(
			final Map<String, Calculable> inputValues) {
		Calculable result;
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("------------------------------------------");
				LOG.debug("Formula: " + name + " invoked with values:");
				for (Entry<String, Calculable> entry : inputValues.entrySet()){
					LOG.debug(entry.getKey() + " = " + entry.getValue());
				}
			}

			// calculate the result value
			result = rightHandSide.evaluate(inputValues);

			if (LOG.isDebugEnabled()) {
				LOG.debug("Invocation succeeded; result: " + leftHandSide
						+ " = " + result.toString());
				LOG.debug("------------------------------------------");
			}

			return result;
		} catch (ExpressionException e) {
			LOG.fatal("Error while evaluating expression of formula" + name, e);
			LOG.debug("------------------------------------------");
			return null;
		}
	}

	/* Specified by interface/super class. */
	@Override
	public final void determineLeftHandSideValueToInpValues(
			final Map<String, Calculable> inputValues) {
		inputValues.put(leftHandSide, determineLeftHandSideValue(inputValues));
		return;
	}

	/* Specified by interface/super class. */
	@Override
	public final Icon getIcon() {
		return renderIcon(formulaDoc);
	}

	/* Specified by interface/super class. */
	@Override
	public final Icon getIconForInputValues(
			final Map<String, Calculable> inputValues) {
		return renderIcon(replaceCIwithCN(inputValues));
	}

	/* Specified by interface/super class. */
	@Override
	public final JMathComponent getJMathComponent() {
		return createJMathComponent(formulaDoc);
	}

	/* Specified by interface/super class. */
	@Override
	public final JMathComponent getJMathComponentForInputValues(
			final Map<String, Calculable> inputValues) {
		return createJMathComponent(replaceCIwithCN(inputValues));
	}

	/* Specified by interface/super class. */
	@Override
	public final void setFormulaToJMathComponent(
			final JMathComponent mathCompExt) {
		mathCompExt.setDocument(formulaDoc);
	}

	/* Specified by interface/super class. */
	@Override
	public final void setInputValuesToJMathComponent(
			final Map<String, Calculable> inputValues,
			final JMathComponent mathCompExt) {
		mathCompExt.setDocument(replaceCIwithCN(inputValues));
	}

	/**
	 * Replaces all variables (<ci> tags) with its corresponding value (<cn>
	 * tag) taken from the inputValue map.
	 * 
	 * @param inputValues
	 *            Map of economic values
	 * 
	 * @return the dormula document without <ci> tags
	 */
	private Document replaceCIwithCN(final Map<String, Calculable> inputValues) {
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

	/**
	 * Renders the formula document to an icon.
	 * 
	 * @param document
	 *            the formula document
	 * 
	 * @return the formula icon
	 */
	private Icon renderIcon(final Document document) {
		Converter c;
		LayoutContext lc;
		BufferedImage bi;
		try {
			c = Converter.getInstance();
			lc = LayoutContextImpl.getDefaultLayoutContext();
			bi = c.render(document, lc);
			return new ImageIcon(bi);
		} catch (IOException e) {
			LOG.error("IO Exception while rendering Icon", e);
			return null;
		}
	}

	/**
	 * Creates the <code>JMathComponent</code> from the given formula document.
	 * 
	 * @param document
	 *            the formula document
	 * 
	 * @return the fomrula JMathComponent
	 */
	private JMathComponent createJMathComponent(final Document document) {
		JMathComponent mathComponent = new JMathComponent();
		mathComponent.setFontSize(FONT_SIZE);
		mathComponent.setDocument(document);
		return mathComponent;
	}
}
