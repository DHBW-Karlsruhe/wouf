package org.bh.platform.formula;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import net.sourceforge.jeuclid.MathMLParserSupport;

import org.bh.platform.expression.ExpressionException;
import org.bh.platform.expression.IExpression;
import org.bh.platform.expression.IExpressionFactoy;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * Factory class for formulas. Do not instantiate formulas directly.
 * 
 * <p>
 * TODO
 * 
 * @author Norman
 * @version 0.1, 02.12.2009
 * @version 0.2, 21.12.2009
 */
public class FormulaFactoryImpl implements IFormulaFactory {

	/**
	 * Instantiates a new formula factory impl.
	 */
	protected FormulaFactoryImpl() {
		// noop
	}
	
	/* Specified by interface/super class. */
	@Override
	public final IFormula createFormula(String name, Document formulaDoc)
			throws FormulaException {
		return createFormulaInternal(name, formulaDoc, true);
	}

	/* Specified by interface/super class. */
	@Override
	public final IFormula createFormula(String name, Document formulaDoc,
			boolean initExpression) throws FormulaException {
		return createFormulaInternal(name, formulaDoc, initExpression);
	}

	private final IFormula createFormulaInternal(String name,
			Document formulaDoc, boolean initExpression)
			throws FormulaException {
		Node leftHandSideNode;
		Node rightHandSideNode;
		Node formula;
		String leftHandSide;
		IExpression rightHandSide = null;

		if (!formulaDoc.getDocumentElement().getNodeName().equals("math")) {
			throw new FormulaException(
					"FormulaDoc has no root element oft tyoe <math>");
		}
		formula = formulaDoc.getElementsByTagName("apply").item(0);

		if (!formula.getNodeName().equals("apply")) {
			throw new FormulaException(
					"FormulaDoc is not a formula. Formula is not surrounded by a <apply> tag");
		}

		// first child of the formula is the equal sign and the next sibling is
		// the left-hand side of the equation (don't forget the text nodes)
		leftHandSideNode = formula.getFirstChild().getNextSibling()
				.getNextSibling().getNextSibling();
		leftHandSide = leftHandSideNode.getTextContent();

		if (initExpression) {
			rightHandSideNode = leftHandSideNode.getNextSibling()
					.getNextSibling();
			try {
				rightHandSide = IExpressionFactoy.instance
						.createExpression(rightHandSideNode);
			} catch (ExpressionException e) {
				throw new FormulaException(e);
			}
		}

		return new FormulaImpl(name, formulaDoc, leftHandSide, rightHandSide);
	}

	/* Specified by interface/super class. */
	@Override
	public final IFormula createFormula(final String name, InputStream document, boolean initExpression)
			throws FormulaException {
		Document formulaDoc = null;
		try {
			formulaDoc = MathMLParserSupport.parseInputStreamXML(document);
		} catch (SAXException e) {
			throw new FormulaException(e);
		} catch (IOException e) {
			throw new FormulaException(e);
		}
		return createFormula(name, formulaDoc, initExpression);
	}

	/* Specified by interface/super class. */
	@Override
	public final IFormula createFormula(final String name, final String document, boolean initExpression)
			throws FormulaException {
		Document formulaDoc = null;
		try {
			formulaDoc = MathMLParserSupport.parseString(document);
		} catch (SAXException e) {
			throw new FormulaException(e);
		} catch (IOException e) {
			throw new FormulaException(e);
		} catch (ParserConfigurationException e) {
			throw new FormulaException(e);
		}
		return createFormula(name, formulaDoc, initExpression);
	}

	/* Specified by interface/super class. */
	@Override
	public final IFormula createFormula(final String name, final File mathMlDoc, boolean initExpression)
			throws FormulaException {
		Document formulaDoc = null;
		try {
			formulaDoc = MathMLParserSupport.parseFile(mathMlDoc);
		} catch (SAXException e) {
			throw new FormulaException(e);
		} catch (IOException e) {
			throw new FormulaException(e);
		}
		return createFormula(name, formulaDoc, initExpression);
	}

	@Override
	public IFormula createFormula(String name, InputStream mathMlDoc) throws FormulaException {
		return createFormula(name, mathMlDoc, true);
	}

	@Override
	public IFormula createFormula(String name, String mathMlDoc)
			throws FormulaException {
		return createFormula(name, mathMlDoc, true);
	}

	@Override
	public IFormula createFormula(String name, File mathMlDoc) throws FormulaException {
		return createFormula(name, mathMlDoc, true);
	}
}
