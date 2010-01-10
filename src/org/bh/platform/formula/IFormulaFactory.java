package org.bh.platform.formula;

import java.io.File;
import java.io.InputStream;

import org.w3c.dom.Document;

/**
 * A factory for creating IFormula objects.
 */
public interface IFormulaFactory {

	/** The instance of the currently used IFormulaFactory implementation. */
	static IFormulaFactory instance = new FormulaFactoryImpl();

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
}
