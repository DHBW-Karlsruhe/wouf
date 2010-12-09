package org.bh.plugin.timeSeries;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import org.apache.log4j.Logger;
import org.bh.calculation.IStochasticProcess;
import org.bh.data.DTOKeyPair;
import org.bh.data.DTOScenario;
import org.bh.data.types.Calculable;
import org.bh.data.types.DistributionMap;
import org.bh.data.types.DoubleValue;
import org.bh.data.types.IntegerValue;
import org.bh.gui.swing.comp.BHDescriptionLabel;
import org.bh.gui.swing.comp.BHTextField;
import org.bh.platform.Services;
import org.bh.validation.VRIsDouble;
import org.bh.validation.VRIsGreaterThan;
import org.bh.validation.VRIsInteger;
import org.bh.validation.VRMandatory;
import org.bh.validation.ValidationRule;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * 
 * TODO plugin not finally implemented. 
 *
 *
 * @author Vito Masiello, Andreas Wussler
 * @version 1.0, 08.12.2010
 * @update 09.12.2010 Vito Masiello
 *
 */

public class TimeSeries implements IStochasticProcess {
	private static final Logger log = Logger.getLogger(TimeSeries.class);
	
	//new variables
	// new Code ends at //-------------------------
	private static final String SLOPE = "slope";
	private static final String STANDARD_DEVIATION = "standardDeviation";
	private static final String REPETITIONS = "repetitions";
	private static final String STEPS_PER_PERIOD = "stepsPerPeriod";
	private static final String AMOUNT_OF_PERIODS = "amountOfPeriods";
	private JPanel panel;
	//-------------------------------------------------------

	private static final String UNIQUE_ID = "timeSeries";
	private static final String GUI_KEY = "timeSeries";
	
	private DTOScenario scenario;
	private HashMap<String, Double> internalMap;
	private HashMap<String, Integer> map;

	@Override
	public String getGuiKey() {
		return GUI_KEY;
	}

	@Override
	/**
	 * update Vito Masiello 
	 * After clicking calculate, Textfields will be shown.
	 * Textfields are the ones from WienerProcess
	 * new Code ends at //-------------------------
	 */
	public JPanel calculateParameters() {
		internalMap = new HashMap<String, Double>();
		map = new HashMap<String, Integer>();
		TreeMap<DTOKeyPair, List<Calculable>> toBeDetermined = scenario
				.getPeriodStochasticKeysAndValues();

		JPanel result = new JPanel();

		String rowDef = "4px,p,4px,p,4px,p,4px,p,4px";
		String colDef = "4px,right:pref,4px,60px:grow,8px:grow,right:pref,4px,max(35px;pref):grow,4px:grow";
		FormLayout layout = new FormLayout(colDef, rowDef);
		result.setLayout(layout);
		layout.setColumnGroups(new int[][] { { 4, 8 } });
		CellConstraints cons = new CellConstraints();

		result.add(new BHDescriptionLabel(AMOUNT_OF_PERIODS), cons.xywh(2, 2,
				1, 1));
		BHTextField tf = new BHTextField(AMOUNT_OF_PERIODS);
		ValidationRule[] rules = { VRMandatory.INSTANCE, VRIsInteger.INSTANCE,
				new VRIsGreaterThan(2, true) };
		tf.setValidationRules(rules);
		result.add(tf, cons.xywh(4, 2, 1, 1));
		map.put(AMOUNT_OF_PERIODS, new Integer(5));

		result.add(new BHDescriptionLabel(STEPS_PER_PERIOD), cons.xywh(2, 4, 1,
				1));
		BHTextField tf1 = new BHTextField(STEPS_PER_PERIOD);
		ValidationRule[] rules1 = { VRMandatory.INSTANCE, VRIsInteger.INSTANCE,
				VRIsGreaterThan.GTZERO };
		tf1.setValidationRules(rules1);
		result.add(tf1, cons.xywh(4, 4, 1, 1));
		map.put(STEPS_PER_PERIOD, new Integer(1));

		result.add(new BHDescriptionLabel(REPETITIONS), cons.xywh(6, 2, 1, 1));
		BHTextField tf2 = new BHTextField(REPETITIONS);
		ValidationRule[] rules2 = { VRMandatory.INSTANCE, VRIsInteger.INSTANCE,
				VRIsGreaterThan.GTZERO };
		tf2.setValidationRules(rules2);
		result.add(tf2, cons.xywh(8, 2, 1, 1));
		map.put(REPETITIONS, new Integer(1));

		result.add(new JSeparator(), cons.xywh(2, 8, 7, 1));

		for (Entry<DTOKeyPair, List<Calculable>> e : toBeDetermined.entrySet()) {
			String key = e.getKey().getKey();
			double standardDeviation = calcStandardDeviation(e.getValue())
					.toNumber().doubleValue();
			double slope = calcSlope(e.getValue()).toNumber().doubleValue();
			internalMap.put(e.getKey().getKey() + SLOPE, slope);
			internalMap.put(e.getKey().getKey() + STANDARD_DEVIATION,
					standardDeviation);

			layout.appendRow(RowSpec.decode("p"));
			layout.appendRow(RowSpec.decode("4px"));

			result.add(new BHDescriptionLabel(key), cons.xywh(2, layout
					.getRowCount() - 1, 1, 1));

			layout.appendRow(RowSpec.decode("p"));
			layout.appendRow(RowSpec.decode("14px"));

			BHTextField tfslope = new BHTextField(key + SLOPE, Services
					.numberToString(slope));
			ValidationRule[] rules_slope = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfslope.setValidationRules(rules_slope);

			BHTextField tfstandardDeviation = new BHTextField(key
					+ STANDARD_DEVIATION, Services
					.numberToString(standardDeviation));
			ValidationRule[] rules_standardDeviation = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfstandardDeviation.setValidationRules(rules_standardDeviation);

			result.add(new BHDescriptionLabel(SLOPE), cons.xywh(2, layout
					.getRowCount() - 1, 1, 1));
			result.add(tfslope, cons.xywh(4, layout.getRowCount() - 1, 1, 1));
			result.add(new BHDescriptionLabel(STANDARD_DEVIATION), cons.xywh(6,
					layout.getRowCount() - 1, 1, 1));
			result.add(tfstandardDeviation, cons.xywh(8,
					layout.getRowCount() - 1, 1, 1));

		}
		this.panel = result;
		return result;
	}
	
	private Calculable calcSlope(List<Calculable> inputValues) {
		// d = 1/n(X0 - X-n)
		Calculable result = (inputValues.get(inputValues.size() - 1)
				.sub(inputValues.get(0))).div(new IntegerValue(inputValues
				.size()));
		return result;
	}
	
	private Calculable calcStandardDeviation(List<Calculable> inputValues) {
		Calculable d = calcSlope(inputValues);
		Calculable sum = new DoubleValue(0);
		for (int i = 0; i < inputValues.size() - 1; i++) {
			Calculable x = inputValues.get(i + 1).sub(inputValues.get(i))
					.sub(d).pow(new IntegerValue(2));
			sum = sum.add(x);
			// sum = sum.add(inputValues.get(i +
			// 1).sub(inputValues.get(i)).sub(d));
		}
		Calculable result = (sum.div(new IntegerValue(inputValues.size())))
				.sqrt();
		return result;
	}
	//-------------------------------------------------------

	@Override
	public void updateParameters() {
		// TODO Auto-generated method stub
		//throw new UnsupportedOperationException("This method has not been implemented");
		String message = "\"Noch nicht vollständig implementiert updateParameters()\"";
		JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public DistributionMap calculate() {
		// TODO Auto-generated method stub
		//throw new UnsupportedOperationException("This method has not been implemented");
		String message = "\"Noch nicht vollständig implementiert calculate()\"";
		JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
		return null;
	}

	@Override
	public String getUniqueId() {
		return UNIQUE_ID;
	}
	
	/**
	 * Initaliasing: at first use
	 */
	@Override
	public TimeSeries createNewInstance(DTOScenario scenario) {
		TimeSeries instance = new TimeSeries(); //new TimeSeries instance
		instance.scenario = scenario; //give instance the reference to scenario
		return instance; //return new instance
	}

}
