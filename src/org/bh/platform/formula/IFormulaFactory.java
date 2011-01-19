/*******************************************************************************
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
import java.io.InputStream;

import org.w3c.dom.Document;

/**
 * A factory for creating IFormula objects.
 */
public interface IFormulaFactory {

	/** The instance of the currently used IFormulaFactory implementation. */
	IFormulaFactory instance = new FormulaFactoryImpl();

	/**
	 * Creates a new IFormula object.
	 * 
	 * @param name
	 *            the formula name
	 * @param mathMlDoc
	 *            formula as an MathMl document
	 * 
	 * @return the formula instance
	 * 
	 * @throws FormulaException
	 *             the formula exception
	 */
	IFormula createFormula(String name, Document mathMlDoc)
			throws FormulaException;

	/**
	 * Creates a new IFormula object.
	 * 
	 * @param name
	 *            the formula name
	 * @param mathMlDoc
	 *            formula MathMl document as an InputStream
	 * 
	 * @return the formula instance
	 * 
	 * @throws FormulaException
	 *             the formula exception
	 */
	IFormula createFormula(String name, InputStream mathMlDoc)
			throws FormulaException;

	/**
	 * Creates a new IFormula object.
	 * 
	 * @param name
	 *            the formula name
	 * @param mathMlDoc
	 *            formula MathMl document as an String
	 * 
	 * @return the formula instance
	 * 
	 * @throws FormulaException
	 *             the formula exception
	 */
	IFormula createFormula(String name, String mathMlDoc)
			throws FormulaException;

	/**
	 * Creates a new IFormula object.
	 * 
	 * @param name
	 *            the formula name
	 * @param mathMlDoc
	 *            formula MathMl document as a File
	 * 
	 * @return the formula instance
	 * 
	 * @throws FormulaException
	 *             the formula exception
	 */
	IFormula createFormula(String name, File mathMlDoc) throws FormulaException;

	IFormula createFormula(String name, Document formulaDoc,
			boolean initExpression) throws FormulaException;

	IFormula createFormula(String name, InputStream document,
			boolean initExpression) throws FormulaException;

	IFormula createFormula(String name, String document, boolean initExpression)
			throws FormulaException;

	IFormula createFormula(String name, File mathMlDoc, boolean initExpression)
			throws FormulaException;
	
	void initialInit();
}
