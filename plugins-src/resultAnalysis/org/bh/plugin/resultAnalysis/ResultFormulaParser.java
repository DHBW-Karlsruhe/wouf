/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bh.plugin.resultAnalysis;

import java.util.Map;
import java.util.logging.Level;
import net.sourceforge.jeuclid.swing.JMathComponent;
import org.bh.gui.ViewException;
import org.bh.platform.formula.FormulaException;
import org.bh.platform.formula.IFormula;
import org.bh.platform.formula.IFormulaFactory;
import org.apache.log4j.Logger;
import org.bh.data.types.Calculable;

/**
 *
 * @author Marco Hammel
 */
public final class ResultFormulaParser {
    private static Logger log = Logger.getLogger(ResultFormulaParser.class);

    private static IFormula formula;
    private static IFormulaFactory fFactory = IFormulaFactory.instance;

    JMathComponent getFCFformula() throws ViewException{
        try {
            ResultFormulaParser.formula = ResultFormulaParser.fFactory.createFormula("FCF_SHV", getClass().getResourceAsStream("FCF_SHV.xm"));
            return formula.getJMathComponent();
        } catch (FormulaException ex) {
            throw new ViewException("Math ML compile error");
        }
    }

    JMathComponent getFCFformula(Map<String, Calculable> para) throws ViewException{
        try {
            ResultFormulaParser.formula = ResultFormulaParser.fFactory.createFormula("FCF_SHV", getClass().getResourceAsStream("FCF_SHV.xm"));
            return formula.getJMathComponentForInputValues(para);
        } catch (FormulaException ex) {
            throw new ViewException("Math ML compile error");
        }
    }

    JMathComponent getAPVformula() throws ViewException{
        try {
            ResultFormulaParser.formula = ResultFormulaParser.fFactory.createFormula("APV_SHV", getClass().getResourceAsStream("FCF_SHV.xm"));
            return formula.getJMathComponent();
        } catch (FormulaException ex) {
            throw new ViewException("Math ML compile error");
        }
    }

     JMathComponent getAPVformula(Map<String, Calculable> para) throws ViewException{
        try {
            ResultFormulaParser.formula = ResultFormulaParser.fFactory.createFormula("APV_SHV", getClass().getResourceAsStream("FCF_SHV.xm"));
            return formula.getJMathComponentForInputValues(para);
        } catch (FormulaException ex) {
            throw new ViewException("Math ML compile error");
        }
    }

     JMathComponent getFTEformula() throws ViewException{
        try {
            ResultFormulaParser.formula = ResultFormulaParser.fFactory.createFormula("FTE_SHV", getClass().getResourceAsStream("FCF_SHV.xm"));
            return formula.getJMathComponent();
        } catch (FormulaException ex) {
            throw new ViewException("Math ML compile error");
        }
    }

     JMathComponent getFTEformula(Map<String, Calculable> para) throws ViewException{
        try {
            ResultFormulaParser.formula = ResultFormulaParser.fFactory.createFormula("FTE_SHV", getClass().getResourceAsStream("FCF_SHV.xm"));
            return formula.getJMathComponentForInputValues(para);
        } catch (FormulaException ex) {
            throw new ViewException("Math ML compile error");
        }
    }
}
