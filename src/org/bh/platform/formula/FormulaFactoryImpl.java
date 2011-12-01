/*******************************************************************************
 * Copyright 2011: Matthias Beste, Hannes Bischoff, Lisa Doerner, Victor Guettler, Markus Hattenbach, Tim Herzenstiel, Günter Hesse, Jochen Hülß, Daniel Krauth, Lukas Lochner, Mark Maltring, Sven Mayer, Benedikt Nees, Alexandre Pereira, Patrick Pfaff, Yannick Rödl, Denis Roster, Sebastian Schumacher, Norman Vogel, Simon Weber 
 *
 * Copyright 2010: Anna Aichinger, Damian Berle, Patrick Dahl, Lisa Engelmann, Patrick Groß, Irene Ihl, Timo Klein, Alena Lang, Miriam Leuthold, Lukas Maciolek, Patrick Maisel, Vito Masiello, Moritz Olf, Ruben Reichle, Alexander Rupp, Daniel Schäfer, Simon Waldraff, Matthias Wurdig, Andreas Wußler
 *
 * Copyright 2009: Manuel Bross, Simon Drees, Marco Hammel, Patrick Heinz, Marcel Hockenberger, Marcus Katzor, Edgar Kauz, Anton Kharitonov, Sarah Kuhn, Michael Löckelt, Heiko Metzger, Jacqueline Missikewitz, Marcel Mrose, Steffen Nees, Alexander Roth, Sebastian Scharfenberger, Carsten Scheunemann, Dave Schikora, Alexander Schmalzhaf, Florian Schultze, Klaus Thiele, Patrick Tietze, Robert Vollmer, Norman Weisenburger, Lars Zuckschwerdt
 *
 * Copyright 2008: Camil Bartetzko, Tobias Bierer, Lukas Bretschneider, Johannes Gilbert, Daniel Huser, Christopher Kurschat, Dominik Pfauntsch, Sandra Rath, Daniel Weber
 *
 * This program is free software: you can redistribute it and/or modify it un-der the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT-NESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
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
		leftHandSideNode = formula.getFirstChild().getNextSibling();
				//.getNextSibling().getNextSibling();
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

	@Override
	public void initialInit() {
		try {
			IFormula f = createFormula("init", "<math xmlns=\"http://www.w3.org/1998/Math/MathML\"><apply><eq/><ci>TEST</ci><ci>InitialLoad</ci></apply></math>", false);
			f.getJMathComponent();
			f.getIcon();
		} catch (FormulaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
