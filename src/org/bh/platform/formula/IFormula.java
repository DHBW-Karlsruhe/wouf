package org.bh.platform.formula;

import java.util.Map;

import javax.swing.Icon;

import net.sourceforge.jeuclid.swing.JMathComponent;

import org.bh.data.types.Calculable;

public interface IFormula {

	String getName();

	String getLeftHandSideKey();

	Calculable determineLeftHandSideValue(Map<String, Calculable> inputValues);

	void determineLeftHandSideValueToInpValues(
			Map<String, Calculable> inputValues);

	Icon getIcon();

	Icon getIconForInputValues(Map<String, Calculable> inputValues);

	JMathComponent getMathComponent();
	JMathComponent getMathComponentForInputValues(
			Map<String, Calculable> inputValues);

	void setInputValuesToJMathComponent(Map<String, Calculable> inputValues,
			JMathComponent mathCompExt);

	void setFormulaToJMathComponent(JMathComponent mathCompExt);

}
