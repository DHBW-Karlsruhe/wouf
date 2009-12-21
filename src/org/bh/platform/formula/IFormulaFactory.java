package org.bh.platform.formula;

import java.io.File;
import java.io.InputStream;

import org.w3c.dom.Document;

public interface IFormulaFactory {
	
	IFormulaFactory instance = new FormulaFactoryImpl();
	
	IFormula createFormula(String name, String mathMlDoc) throws FormulaException;

	IFormula createFormula(String name, InputStream mathMlDoc) throws FormulaException;
	
	IFormula createFormula(String name, Document mathMlDoc) throws FormulaException;
	
	IFormula createFormula(String name, File mathMlDoc) throws FormulaException;
}
