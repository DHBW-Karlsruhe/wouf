package org.bh.plugin.randomWalk;

import java.awt.Component;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import org.apache.log4j.Logger;
import org.bh.calculation.IShareholderValueCalculator;
import org.bh.calculation.IStochasticProcess;
import org.bh.data.DTO;
import org.bh.data.DTOKeyPair;
import org.bh.data.DTOPeriod;
import org.bh.data.DTOScenario;
import org.bh.data.IPeriodicalValuesDTO;
import org.bh.data.types.Calculable;
import org.bh.data.types.DistributionMap;
import org.bh.data.types.DoubleValue;
import org.bh.gui.swing.BHDescriptionLabel;
import org.bh.gui.swing.BHMainFrame;
import org.bh.gui.swing.BHOptionPane;
import org.bh.gui.swing.BHTextField;
import org.bh.platform.Services;
import org.bh.validation.VRIsDouble;
import org.bh.validation.VRIsGreaterThan;
import org.bh.validation.VRIsInteger;
import org.bh.validation.VRMandatory;
import org.bh.validation.ValidateStochastic;
import org.bh.validation.ValidationRule;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

//import org.bh.plugin.directinput.DTODirectInput;
//import org.bh.data.types.StringValue;
//import java.util.Iterator;
/**
 * This class provides the functionality to process the Random Walk on every
 * value which should be determined stochastically.
 * 
 * @author Sebastian
 * @version 0.1, 04.01.2010
 * 
 */
public class RandomWalk implements IStochasticProcess {
	private static final String INCREMENT = "increment";
	private static final String CHANCE = "chance";
	private static final String REPETITIONS = "repetitions";
	private static final String STEPS_PER_PERIOD = "stepsPerPeriod";
	private static final String AMOUNT_OF_PERIODS = "amountOfPeriods";

	private static final Logger log = Logger.getLogger(RandomWalk.class);

	private static final String UNIQUE_ID = "randomWalk";
	private static final String GUI_KEY = "randomWalk";
	private static final int NUM_THREADS = Runtime.getRuntime()
			.availableProcessors();

	private DTOScenario scenario;
	private JPanel panel;
	private HashMap<String, Double> internalMap;
	private HashMap<String, Integer> map;

	private BHMainFrame bhmf;

	
	@SuppressWarnings("static-access")
	@Override
	public DistributionMap calculate() {
		log.info("Random walk started");

		DistributionMap result = new DistributionMap(1);
		DTOPeriod last = scenario.getLastChild();
		List<DTOKeyPair> stochasticKeys = scenario.getPeriodStochasticKeys();
		IShareholderValueCalculator dcfMethod = scenario.getDCFMethod();

		boolean isValid = ValidateStochastic.validateRandomWalk(internalMap);
		int choice = 0;

		if (isValid == false) {
			choice = BHOptionPane.showConfirmDialog(bhmf, Services
					.getTranslator().translate("WCalcRWMessage"), Services
					.getTranslator().translate("WCalcRW"),
					JOptionPane.YES_NO_OPTION);
		}

		if (isValid == true || choice == JOptionPane.YES_OPTION) {

			Countdown countdown = new Countdown(map.get(REPETITIONS));
			int stepsPerPeriod = map.get(STEPS_PER_PERIOD);
			int amountOfPeriods = map.get(AMOUNT_OF_PERIODS);

			DTO.setThrowEvents(false);
			DTOScenario[] tempScenarios = new DTOScenario[NUM_THREADS];

			CalculationThread[] threads = new CalculationThread[NUM_THREADS];
			for (int i = 0; i < NUM_THREADS; i++) {
				DTOScenario tempScenario = tempScenarios[i] = new DTOScenario(
						true);
				tempScenario.put(DTOScenario.Key.REK, scenario
						.get(DTOScenario.Key.REK));
				tempScenario.put(DTOScenario.Key.RFK, scenario
						.get(DTOScenario.Key.RFK));
				tempScenario.put(DTOScenario.Key.BTAX, scenario
						.get(DTOScenario.Key.BTAX));
				tempScenario.put(DTOScenario.Key.CTAX, scenario
						.get(DTOScenario.Key.CTAX));

				for (int j = 0; j < amountOfPeriods; j++) {
					tempScenario.addChild((DTOPeriod) last.clone());
				}

				threads[i] = new CalculationThread(countdown, stepsPerPeriod,
						stochasticKeys, tempScenario, result, dcfMethod, last);
				threads[i].setName("Random Walk " + (i + 1));
				threads[i].start();
			}

			for (int i = 0; i < NUM_THREADS; i++) {
				try {
					threads[i].join();
				} catch (InterruptedException e) {
					log.error("", e);
				}
			}
		}

		DTO.setThrowEvents(true);
		log.info("Random walk finished");

		return result;
	}

	protected class CalculationThread extends Thread {
		private Countdown countdown;
		private int stepsPerPeriod;
		private List<DTOKeyPair> stochasticKeys;
		private DTOScenario tempScenario;
		private DistributionMap result;
		private IShareholderValueCalculator dcfMethod;
		private DTOPeriod last;
		private Random random = new Random();

		public CalculationThread(Countdown countdown, int stepsPerPeriod,
				List<DTOKeyPair> stochasticKeys, DTOScenario tempScenario,
				DistributionMap result, IShareholderValueCalculator dcfMethod,
				DTOPeriod last) {
			super();
			this.countdown = countdown;
			this.stepsPerPeriod = stepsPerPeriod;
			this.stochasticKeys = stochasticKeys;
			this.tempScenario = tempScenario;
			this.result = result;
			this.dcfMethod = dcfMethod;
			this.last = last;
		}

		@Override
		public void run() {
			while (countdown.hasRunsLeft()) {
				DTOPeriod previous = last.getPrevious();
				for (DTOPeriod period : tempScenario.getChildren()) {
					for (DTOKeyPair key : stochasticKeys) {
						IPeriodicalValuesDTO pvdto = period
								.getPeriodicalValuesDTO(key.getDtoId());
						IPeriodicalValuesDTO previousDto = previous
								.getPeriodicalValuesDTO(key.getDtoId());
						Calculable previousValue = previousDto
								.getCalculable(key.getKey());
						pvdto.put(key.getKey(), doOneRandomWalk(previousValue,
								stepsPerPeriod, internalMap.get(key.getKey()
										+ CHANCE), internalMap.get(key.getKey()
										+ INCREMENT), random));
					}
					previous = period;
				}

				Map<String, Calculable[]> dcfResult = dcfMethod.calculate(
						tempScenario, false);

				result
						.put(((DoubleValue) dcfResult
								.get(IShareholderValueCalculator.Result.SHAREHOLDER_VALUE
										.toString())[0]).getValue());
			}
		}
	}

	protected class Countdown {
		private int runsLeft;

		public Countdown(int runs) {
			runsLeft = runs;
		}

		public synchronized boolean hasRunsLeft() {
			return (--runsLeft >= 0);
		}
	}

	@Override
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
		map.put(STEPS_PER_PERIOD, new Integer(250 / 5));

		result.add(new BHDescriptionLabel(REPETITIONS), cons.xywh(6, 2, 1, 1));
		BHTextField tf2 = new BHTextField(REPETITIONS);
		ValidationRule[] rules2 = { VRMandatory.INSTANCE, VRIsInteger.INSTANCE,
				VRIsGreaterThan.GTZERO };
		tf2.setValidationRules(rules2);
		result.add(tf2, cons.xywh(8, 2, 1, 1));
		map.put(REPETITIONS, new Integer(100000));

		result.add(new JSeparator(), cons.xywh(2, 8, 7, 1));

		for (Entry<DTOKeyPair, List<Calculable>> e : toBeDetermined.entrySet()) {
			String key = e.getKey().getKey();
			double chance = calcChance(e.getValue());
			double increment = calcIncrement(e.getValue());

			layout.appendRow(RowSpec.decode("p"));
			layout.appendRow(RowSpec.decode("4px"));

			result.add(new BHDescriptionLabel(key), cons.xywh(2, layout
					.getRowCount() - 1, 1, 1));

			layout.appendRow(RowSpec.decode("p"));
			layout.appendRow(RowSpec.decode("14px"));

			BHTextField tfchance = new BHTextField(key + CHANCE, Services
					.numberToString(chance));
			ValidationRule[] rules_chance = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfchance.setValidationRules(rules_chance);

			BHTextField tfincrement = new BHTextField(key + INCREMENT, Services
					.numberToString(increment));
			ValidationRule[] rules_increment = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfincrement.setValidationRules(rules_increment);

			result.add(new BHDescriptionLabel(CHANCE), cons.xywh(2, layout
					.getRowCount() - 1, 1, 1));
			result.add(tfchance, cons.xywh(4, layout.getRowCount() - 1, 1, 1));
			result.add(new BHDescriptionLabel(INCREMENT), cons.xywh(6, layout
					.getRowCount() - 1, 1, 1));
			result.add(tfincrement, cons
					.xywh(8, layout.getRowCount() - 1, 1, 1));

			internalMap.put(key + CHANCE, chance);
			internalMap.put(key + INCREMENT, increment);
		}
		this.panel = result;
		return result;
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
					internalMap.put(key, Services.stringToDouble(text));
				else
					map.put(key, Services.stringToInt(text));
			}
		}
	}

	@Override
	public IStochasticProcess createNewInstance() {
		return new RandomWalk();
	}

	@Override
	public String getUniqueId() {
		return UNIQUE_ID;
	}

	@Override
	public String getGuiKey() {
		return GUI_KEY;
	}

	private double calcIncrement(List<Calculable> inputValues) {
		double sum = 0;
		double old = 0;
		int i = 0;
		for (Calculable c : inputValues) {
			double value = c.parse().doubleValue();
			if (i > 0) {
				sum += Math.abs(value - old);
			}
			old = value;
			i++;
		}
		double result = sum / (inputValues.size() - 1);
		// System.out.println("Increment: " + result);
		return result;
	}

	private double calcChance(List<Calculable> inputValues) {
		double sum = 0;
		int i = 0;
		Calculable old = null;
		for (Calculable c : inputValues) {
			if (i > 0) {
				if (old.lessThan(c))
					sum++;
			}
			old = c;
			i++;
		}
		double result = sum / (inputValues.size() - 1);
		// System.out.println("Chance: " + result);
		return result;
	}

	private Calculable doOneRandomWalk(Calculable lastValue, int amountOfSteps,
			double chance, double increment, Random random) {
		int inc = 0;
		for (int i = 0; i < amountOfSteps; i++) {
			if (random.nextDouble() < chance)
				inc++;
			else
				inc--;
		}
		Calculable value = lastValue.add(new DoubleValue(inc * increment
				/ amountOfSteps));
		return value;
	}
	/*
	 * public static void main(String[] args) { DTOScenario scenario = new
	 * DTOScenario(false); scenario.put(DTOScenario.Key.REK, new
	 * DoubleValue(0.11)); scenario.put(DTOScenario.Key.RFK, new
	 * DoubleValue(0.1)); scenario.put(DTOScenario.Key.BTAX, new
	 * DoubleValue(0.1694)); scenario.put(DTOScenario.Key.CTAX, new
	 * DoubleValue(0.26375)); scenario.put(DTOScenario.Key.DCF_METHOD, new
	 * StringValue("apv"));
	 * 
	 * DTODirectInput di0 = new DTODirectInput(); DTOPeriod period0 = new
	 * DTOPeriod(); period0.addChild(di0); scenario.addChild(period0);
	 * 
	 * DTODirectInput di = new DTODirectInput(); di.put(DTODirectInput.Key.FCF,
	 * new DoubleValue(130)); di.put(DTODirectInput.Key.LIABILITIES, new
	 * DoubleValue(1200)); DTOPeriod period1 = new DTOPeriod();
	 * period1.addChild(di); scenario.addChild(period1);
	 * 
	 * DTODirectInput di2 = new DTODirectInput();
	 * di2.put(DTODirectInput.Key.FCF, new DoubleValue(105));
	 * di2.put(DTODirectInput.Key.LIABILITIES, new DoubleValue(1050)); DTOPeriod
	 * period2 = new DTOPeriod(); period2.addChild(di2);
	 * scenario.addChild(period2);
	 * 
	 * DTODirectInput di3 = new DTODirectInput();
	 * di3.put(DTODirectInput.Key.FCF, new DoubleValue(90));
	 * di3.put(DTODirectInput.Key.LIABILITIES, new DoubleValue(1100)); DTOPeriod
	 * period3 = new DTOPeriod(); period3.addChild(di3);
	 * scenario.addChild(period3);
	 * 
	 * DTODirectInput di4 = new DTODirectInput();
	 * di4.put(DTODirectInput.Key.FCF, new DoubleValue(100));
	 * di4.put(DTODirectInput.Key.LIABILITIES, new DoubleValue(1000)); DTOPeriod
	 * period4 = new DTOPeriod(); period4.addChild(di4);
	 * scenario.addChild(period4);
	 * 
	 * RandomWalk rw = new RandomWalk(); rw.setScenario(scenario);
	 * rw.calculateParameters(); DistributionMap dm = rw.calculate();
	 * 
	 * Iterator<Entry<java.lang.Double, Integer>> iter = dm.iterator();
	 * System.out.println("Key 0"); while (iter.hasNext()) {
	 * Map.Entry<java.lang.Double, java.lang.Integer> entry =
	 * (Map.Entry<java.lang.Double, java.lang.Integer>) iter .next();
	 * System.out.println(entry.getKey()); } iter = dm.iterator();
	 * System.out.println("Value 0"); while (iter.hasNext()) {
	 * Map.Entry<java.lang.Double, java.lang.Integer> entry =
	 * (Map.Entry<java.lang.Double, java.lang.Integer>) iter .next();
	 * System.out.println(entry.getValue()); } }
	 */
}
