package org.bh.platform.formula;

import java.io.IOException;
import java.io.InputStream;

import net.sourceforge.jeuclid.MathMLParserSupport;

import org.bh.platform.expression.Expression;
import org.bh.platform.expression.ExpressionException;
import org.bh.platform.expression.ExpressionFactory;
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
 * 
 */
public class FormulaFactory {

    private static FormulaFactory instance = null;

    public static FormulaFactory getInstance() {
	if (instance == null) {
	    instance = new FormulaFactory();
	}
	return instance;
    }

    private FormulaFactory() {
    }

    /**
     * Creates a stand alone formula for the given type
     * 
     * @param formula
     * @return f
     * @throws FormulaException 
     */
    public Formula createFormula(String name, InputStream document) throws FormulaException {
    	Document formulaDoc = null;
		try {
			formulaDoc = MathMLParserSupport.parseInputStreamXML(document);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return createFormula(name, formulaDoc);
    	
    }

    public Formula createFormula(String name, Document formulaDoc) throws FormulaException {
    	Node leftHandSideNode;
		Node rightHandSideNode;
		Node formula;
    	String leftHandSide;
    	Expression rightHandSide = null;
    	
    	if (!formulaDoc.getDocumentElement().equals("math")) {
			throw new FormulaException(
					"FormulaDoc has no root element oft tyoe <math>");
		}
		formula = formulaDoc.getElementsByTagName("apply").item(0);

		if (!formula.getNodeName().equals("apply")) {
			throw new FormulaException(
					"FormulaDoc is not a formula. Formula is not surrounded by a <apply> tag");
		}

		// first child of the formula is the equal sign and the next sibling is
		// the left-hand side of the equation
		leftHandSideNode = formula.getFirstChild().getNextSibling()
				.getNextSibling().getNextSibling();
		leftHandSide = leftHandSideNode.getTextContent();
		
		
		rightHandSideNode = leftHandSideNode.getNextSibling().getNextSibling();
		try {
			rightHandSide = ExpressionFactory.getInstance().createExpression(
					rightHandSideNode);
		} catch (ExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return new Formula(name, formulaDoc ,leftHandSide, rightHandSide);
    }
}
