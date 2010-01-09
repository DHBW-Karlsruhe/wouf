package org.bh.plugin.wienerProcess;

import java.awt.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.swing.JPanel;
import javax.swing.JSeparator;

import org.apache.log4j.Logger;
import org.bh.calculation.IShareholderValueCalculator;
import org.bh.calculation.IStochasticProcess;
import org.bh.data.DTOKeyPair;
import org.bh.data.DTOPeriod;
import org.bh.data.DTOScenario;
import org.bh.data.IPeriodicalValuesDTO;
import org.bh.data.types.Calculable;
import org.bh.data.types.DistributionMap;
import org.bh.data.types.DoubleValue;
import org.bh.data.types.IValue;
import org.bh.data.types.IntegerValue;
import org.bh.gui.swing.BHDescriptionLabel;
import org.bh.gui.swing.BHTextField;
import org.bh.validation.VRIsInteger;
import org.bh.validation.VRIsPositive;
import org.bh.validation.VRMandatory;
import org.bh.validation.ValidationRule;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

//import org.bh.data.types.StringValue;
//import org.bh.plugin.directinput.DTODirectInput;
//import java.util.Iterator;
/**
 * This class provides the functionality to process the Wiener Process on every value which
 * should be determined stochastically.
 * 
 * @author Sebastian
 * @version 0.1, 04.01.2010
 *
 */
public class WienerProcess implements IStochasticProcess {
	private static final String SLOPE = "slope";
	private static final String STANDARD_DEVIATION = "standardDeviation";
	private static final String REPETITIONS = "repetitions";
	private static final String STEPS_PER_PERIOD = "stepsPerPeriod";
	private static final String AMOUNT_OF_PERIODS = "amountOfPeriods";
	
	
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(WienerProcess.class);

	private static final String UNIQUE_ID = "wienerProcess";
	private static final String GUI_KEY = "wienerProcess";

	private DTOScenario scenario;
	private JPanel panel;
	private HashMap<String, IValue> internalMap;
	private HashMap<String, Integer> map;

	@Override
	public DistributionMap calculate() {
		
		DistributionMap result = new DistributionMap(1);
		DTOPeriod last = scenario.getLastChild();
		List<DTOKeyPair> stochasticKeys = scenario.getPeriodStochasticKeys();
		// List<String> stochasticKeys = last.getStochasticKeys();

		for (int j = 0; j < map.get(REPETITIONS); j++) {
			DTOScenario temp = new DTOScenario(true);
			temp.put(DTOScenario.Key.REK, scenario.get(DTOScenario.Key.REK));
			temp.put(DTOScenario.Key.RFK, scenario.get(DTOScenario.Key.RFK));
			temp.put(DTOScenario.Key.BTAX, scenario.get(DTOScenario.Key.BTAX));
			temp.put(DTOScenario.Key.CTAX, scenario.get(DTOScenario.Key.CTAX));

			DTOPeriod clone = last.clone();
			DTOPeriod previous = last.getPrevious();

			for (DTOKeyPair key : stochasticKeys) {
				IPeriodicalValuesDTO pvdto = clone.getPeriodicalValuesDTO(key
						.getDtoId());
				IPeriodicalValuesDTO previousDto = previous.getPeriodicalValuesDTO(key
						.getDtoId());
				pvdto.put(key.getKey(), this.doOneWienerProcess(previousDto
						.getCalculable(key.getKey()), map.get(STEPS_PER_PERIOD),
						(Calculable) this.internalMap.get(key.getKey() + STANDARD_DEVIATION),
						(Calculable) this.internalMap.get(key.getKey() + SLOPE)));
			}
			temp.addChild(clone);

			for (int i = 0; i < map.get(AMOUNT_OF_PERIODS) - 1; i++) {
				previous = clone;
				clone = last.clone();
				for (DTOKeyPair key : stochasticKeys) {
					IPeriodicalValuesDTO pvdto = clone
							.getPeriodicalValuesDTO(key.getDtoId());
					IPeriodicalValuesDTO previousDto = previous.getPeriodicalValuesDTO(key
							.getDtoId());
					pvdto.put(key.getKey(), this.doOneWienerProcess(previousDto
							.getCalculable(key.getKey()), map.get(STEPS_PER_PERIOD),
							(Calculable) this.internalMap.get(key.getKey()
									+ STANDARD_DEVIATION), (Calculable) this.internalMap
									.get(key.getKey() + SLOPE)));
				}
				temp.addChild(clone);
			}
			
			result
					.put(((DoubleValue) scenario.getDCFMethod().calculate(
							temp).get(
							IShareholderValueCalculator.SHAREHOLDER_VALUE)[0])
							.getValue());
		}
		return result;
	}

	@Override
	public JPanel calculateParameters() {
		internalMap = new HashMap<String, IValue>();
		map = new HashMap<String, Integer>();
		TreeMap<DTOKeyPair, List<Calculable>> toBeDetermined = scenario
				.getPeriodStochasticKeysAndValues();

		if (toBeDetermined.isEmpty())
			return null;
		else {
			JPanel result = new JPanel();

			String rowDef = "4px,p,4px,p,4px,p,4px,p,4px";
			String colDef = "4px,right:pref,4px,60px:grow,8px:grow,right:pref,4px,max(35px;pref):grow,4px:grow";
			FormLayout layout = new FormLayout(colDef, rowDef);
			result.setLayout(layout);
			layout.setColumnGroups(new int[][] {{4,8}});
			CellConstraints cons = new CellConstraints();
			
			result.add(new BHDescriptionLabel(AMOUNT_OF_PERIODS), cons.xywh(2, 2, 1, 1));
			BHTextField tf = new BHTextField(AMOUNT_OF_PERIODS);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsInteger.INSTANCE,
					VRIsPositive.INSTANCE };
			tf.setValidationRules(rules);
			result.add(tf, cons.xywh(4, 2, 1, 1));
			map.put(AMOUNT_OF_PERIODS, new Integer(5));

			result.add(new BHDescriptionLabel(STEPS_PER_PERIOD), cons.xywh(2, 4, 1, 1));
			BHTextField tf1 = new BHTextField(STEPS_PER_PERIOD);
			ValidationRule[] rules1 = { VRMandatory.INSTANCE,
					VRIsInteger.INSTANCE,
					VRIsPositive.INSTANCE };
			tf1.setValidationRules(rules1);
			result.add(tf1, cons.xywh(4, 4, 1, 1));
			map.put(STEPS_PER_PERIOD, new Integer(1));

			result.add(new BHDescriptionLabel(REPETITIONS), cons.xywh(6, 2, 1, 1));
			BHTextField tf2 = new BHTextField(REPETITIONS);
			ValidationRule[] rules2 = { VRMandatory.INSTANCE,
					VRIsInteger.INSTANCE,
					VRIsPositive.INSTANCE };
			tf2.setValidationRules(rules2);
			result.add(tf2, cons.xywh(8, 2, 1, 1));
			map.put(REPETITIONS, new Integer(100000));
			
			result.add(new JSeparator(), cons.xywh(2, 8, 7, 1));

			for (Entry<DTOKeyPair, List<Calculable>> e : toBeDetermined
					.entrySet()) {
				String key = e.getKey().getKey();
				Calculable standardDeviation = calcStandardDeviation(e
						.getValue());
				Calculable slope = calcSlope(e.getValue());
				internalMap.put(e.getKey().getKey() + SLOPE, slope);
				internalMap.put(e.getKey().getKey() + STANDARD_DEVIATION, standardDeviation);
				
				layout.appendRow(RowSpec.decode("p"));
				layout.appendRow(RowSpec.decode("4px"));

				result.add(new BHDescriptionLabel(key), cons.xywh(2, layout.getRowCount()-1, 1, 1));

				layout.appendRow(RowSpec.decode("p"));
				layout.appendRow(RowSpec.decode("14px"));
				
				result.add(new BHDescriptionLabel(SLOPE), cons.xywh(2, layout.getRowCount()-1, 1, 1));
				result.add(new BHTextField(key + SLOPE, "" + slope), cons.xywh(4, layout.getRowCount()-1, 1, 1));
				result.add(new BHDescriptionLabel(STANDARD_DEVIATION), cons.xywh(6, layout.getRowCount()-1, 1, 1));
				result.add(new BHTextField(key + STANDARD_DEVIATION, "" + standardDeviation), cons.xywh(8, layout.getRowCount()-1, 1, 1));

			}
			this.panel = result;
			return result;
		}
	}

	@Override
	public Map<String, IValue> getParametersForAnalysis() {
		Map<String, IValue> returnMap = new HashMap<String, IValue>();
		returnMap.putAll(internalMap);
		for(Entry<String, Integer> e : map.entrySet())
			returnMap.put(e.getKey(), new IntegerValue(e.getValue()));
		return returnMap;
	}

	@Override
	public void setScenario(DTOScenario scenario) {
		this.scenario = scenario;
	}

	@Override
	public void updateParameters() {
		Component[] components = panel.getComponents();
		for (int i = 0; i < components.length; i++) {
			if (components[i] instanceof BHTextField) {
				BHTextField c = (BHTextField) components[i];
				String key = c.getKey();
				String text = c.getText();
				if (internalMap.containsKey(key))
					internalMap.put(key, Calculable.parseCalculable(text));
				else
					map.put(key, Integer.parseInt(text));
			}
		}
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
//			Calculable x = inputValues.get(i + 1).sub(inputValues.get(i)).sub(d).pow(new IntegerValue(2));
//			sum = sum
//					.add(x);
			sum = sum.add(inputValues.get(i + 1).sub(inputValues.get(i)).sub(d));
		}
		Calculable result = (sum.div(new IntegerValue(inputValues.size())))
				.sqrt();
		return result;
	}

	private Calculable doOneWienerProcess(Calculable lastValue,
			int amountOfSteps, Calculable standardDeviation, Calculable slope) {
		Random r = new Random();
		Calculable deltaT = new DoubleValue(1.0 / amountOfSteps);
		Calculable value = lastValue;
		for(int i = 0; i < amountOfSteps; i++){
			// Xt+dT = Xt + d * dT + (standardA * dTsqrt * eps)
			value = value.add(slope.mul(deltaT))
					.add(standardDeviation.mul(deltaT.sqrt()).mul(
							new DoubleValue(r.nextGaussian())));
		}
		return value;
	}

	@Override
	public IStochasticProcess createNewInstance() {
		return new WienerProcess();
	}

	@Override
	public String getUniqueId() {
		return UNIQUE_ID;
	}

	@Override
	public String getGuiKey() {
		return GUI_KEY;
	}
/*
	public static void main(String[] args) {
		DTOScenario scenario = new DTOScenario(false);
		scenario.put(DTOScenario.Key.REK, new DoubleValue(0.11));
		scenario.put(DTOScenario.Key.RFK, new DoubleValue(0.1));
		scenario.put(DTOScenario.Key.BTAX, new DoubleValue(0.1694));
		scenario.put(DTOScenario.Key.CTAX, new DoubleValue(0.26375));
		scenario.put(DTOScenario.Key.DCF_METHOD, new StringValue("apv"));

		DTODirectInput di0 = new DTODirectInput();
		DTOPeriod period0 = new DTOPeriod();
		period0.put(DTOPeriod.Key.NAME, new StringValue("Test"));
		period0.addChild(di0);
		scenario.addChild(period0);

		DTODirectInput di = new DTODirectInput();
		di.put(DTODirectInput.Key.FCF, new DoubleValue(130));
		di.put(DTODirectInput.Key.LIABILITIES, new DoubleValue(1200));
		DTOPeriod period1 = new DTOPeriod();
		period1.put(DTOPeriod.Key.NAME, new StringValue("Test"));
		period1.addChild(di);
		scenario.addChild(period1);

		DTODirectInput di2 = new DTODirectInput();
		di2.put(DTODirectInput.Key.FCF, new DoubleValue(105));
		di2.put(DTODirectInput.Key.LIABILITIES, new DoubleValue(1050));
		DTOPeriod period2 = new DTOPeriod();
		period2.put(DTOPeriod.Key.NAME, new StringValue("Test"));
		period2.addChild(di2);
		scenario.addChild(period2);

		DTODirectInput di3 = new DTODirectInput();
		di3.put(DTODirectInput.Key.FCF, new DoubleValue(90));
		di3.put(DTODirectInput.Key.LIABILITIES, new DoubleValue(1100));
		DTOPeriod period3 = new DTOPeriod();
		period3.put(DTOPeriod.Key.NAME, new StringValue("Test"));
		period3.addChild(di3);
		scenario.addChild(period3);

		DTODirectInput di4 = new DTODirectInput();
		di4.put(DTODirectInput.Key.FCF, new DoubleValue(100));
		di4.put(DTODirectInput.Key.LIABILITIES, new DoubleValue(1000));
		DTOPeriod period4 = new DTOPeriod();
		period4.put(DTOPeriod.Key.NAME, new StringValue("Test"));
		period4.addChild(di4);
		scenario.addChild(period4);

		WienerProcess wp = new WienerProcess();
		wp.setScenario(scenario);
		wp.calculateParameters();
		DistributionMap dm = wp.calculate();

		Iterator<Entry<java.lang.Double, Integer>> iter = dm.iterator();
		System.out.println("Key 0");
		while (iter.hasNext()) {
			Map.Entry<java.lang.Double, java.lang.Integer> entry = (Map.Entry<java.lang.Double, java.lang.Integer>) iter
					.next();
			System.out.println(entry.getKey());
		}
		iter = dm.iterator();
		System.out.println("Value 0");
		while (iter.hasNext()) {
			Map.Entry<java.lang.Double, java.lang.Integer> entry = (Map.Entry<java.lang.Double, java.lang.Integer>) iter
					.next();
			System.out.println(entry.getValue());
		}
	}
*/
}
