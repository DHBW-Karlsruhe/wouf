/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bh.plugin.resultAnalysis;

import java.util.Map;
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

//TODO Marco really necessary? by Norman
public final class ResultFormulaParser {

    private static Logger log = Logger.getLogger(ResultFormulaParser.class);
    private static IFormula formula;
    private static IFormulaFactory fFactory = IFormulaFactory.instance;

    static JMathComponent getFCFformula() throws ViewException {
        try {
            ResultFormulaParser.formula = ResultFormulaParser.fFactory.createFormula("FCF_SHV", ResultFormulaParser.class.getResourceAsStream("FCF_SHV.xm"));
            return formula.getJMathComponent();
        } catch (FormulaException ex) {
            throw new ViewException("Math ML compile error");
        }
    }

    static JMathComponent getFCFformula(Map<String, Calculable> para) throws ViewException {
        try {
            ResultFormulaParser.formula = ResultFormulaParser.fFactory.createFormula("FCF_SHV", ResultFormulaParser.class.getResourceAsStream("FCF_SHV.xm"));
            return formula.getJMathComponentForInputValues(para);
        } catch (FormulaException ex) {
            throw new ViewException("Math ML compile error");
        }
    }

    static JMathComponent getAPVformula() throws ViewException {
        try {
            ResultFormulaParser.formula = ResultFormulaParser.fFactory.createFormula("APV_SHV", ResultFormulaParser.class.getResourceAsStream("FCF_SHV.xm"));
            return formula.getJMathComponent();
        } catch (FormulaException ex) {
            throw new ViewException("Math ML compile error");
        }
    }

    static JMathComponent getAPVformula(Map<String, Calculable> para) throws ViewException {
        try {
            ResultFormulaParser.formula = ResultFormulaParser.fFactory.createFormula("APV_SHV", ResultFormulaParser.class.getResourceAsStream("FCF_SHV.xm"));
            return formula.getJMathComponentForInputValues(para);
        } catch (FormulaException ex) {
            throw new ViewException("Math ML compile error");
        }
    }

    static JMathComponent getFTEformula() throws ViewException {
        try {
            ResultFormulaParser.formula = ResultFormulaParser.fFactory.createFormula("FTE_SHV", ResultFormulaParser.class.getResourceAsStream("FCF_SHV.xm"));
            return formula.getJMathComponent();
        } catch (FormulaException ex) {
            throw new ViewException("Math ML compile error");
        }
    }

    static JMathComponent getFTEformula(Map<String, Calculable> para) throws ViewException {
        try {
            ResultFormulaParser.formula = ResultFormulaParser.fFactory.createFormula("FTE_SHV", ResultFormulaParser.class.getResourceAsStream("FCF_SHV.xm"));
            return formula.getJMathComponentForInputValues(para);
        } catch (FormulaException ex) {
            throw new ViewException("Math ML compile error");
        }
    }
}
