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
package org.bh.plugin.timeSeries;

import java.awt.Component;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.Map.Entry;
import javax.swing.JPanel;
import org.apache.log4j.Logger;
import org.bh.calculation.ITimeSeriesProcess;
import org.bh.data.DTOKeyPair;
import org.bh.data.DTOScenario;
import org.bh.data.types.Calculable;
import org.bh.data.types.DoubleValue;
import org.bh.gui.swing.comp.BHDescriptionLabel;
import org.bh.gui.swing.comp.BHTextField;
import org.bh.platform.Services;
import org.bh.validation.VRIsGreaterThan;
import org.bh.validation.VRIsInteger;
import org.bh.validation.VRMandatory;
import org.bh.validation.ValidationRule;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * 
 * TODO plugin not finally implemented. 
 *
 *
 * @author Vito Masiello, Andreas Wussler
 * @version 1.0, 08.12.2010
 * @update 09.12.2010 Vito Masiello
 * @update 23.12.2010 Timo Klein
 */

public class TimeSeries implements ITimeSeriesProcess {
	private static final Logger log = Logger.getLogger(TimeSeries.class);
	
	//new variables
	// new Code ends at //-------------------------
	private static final String SLOPE = "slope";
	private static final String STANDARD_DEVIATION = "standardDeviation";
	private static final String REPETITIONS = "repetitions";
	private static final String STEPS_PER_PERIOD = "stepsPerPeriod";
	private static final String AMOUNT_OF_PERIODS_BACK = "amountOfPeriodsBack";
	private static final String AMOUNT_OF_PERIODS_FUTURE = "amountOfPeriodsFuture";
	private JPanel panel;
	//-------------------------------------------------------

	private static final String UNIQUE_ID = "timeSeries";
	private static final String GUI_KEY = "timeSeries";
	
	private DTOScenario scenario;
	private HashMap<String, Double> internalMap;
	private HashMap<String, Integer> map;
	private TimeSeriesCalculator_v3 calc;

	@Override
	public String getGuiKey() {
		return GUI_KEY;
	}

	//@Override
	/**
	 * update Vito Masiello 
	 * After clicking calculate, Textfields will be shown.
	 * Textfields are the ones from WienerProcess
	 * new Code ends at //-------------------------
	 */
	
	/*
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
	public DistributionMap calculate() {
		// TODO Auto-generated method stub
		//throw new UnsupportedOperationException("This method has not been implemented");
		String message = "\"Noch nicht vollständig implementiert calculate()\"";
		JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
		return null;
	}*/

	@Override
	public String getUniqueId() {
		return UNIQUE_ID;
	}

	@Override
	public ITimeSeriesProcess createNewInstance(DTOScenario scenario) {
		TimeSeries instance = new TimeSeries();
		instance.scenario = scenario;
		return instance;
	}

	@Override
	public JPanel calculateParameters() {
		internalMap = new HashMap<String, Double>();
		map = new HashMap<String, Integer>();
		JPanel result = new JPanel();
		
		String rowDef = "4px,p,4px";
		String colDef = "4px,right:pref,4px,60px:grow,8px:grow,right:pref,4px,max(35px;pref):grow,4px:grow";
		FormLayout layout = new FormLayout(colDef, rowDef);
		result.setLayout(layout);
		layout.setColumnGroups(new int[][] { { 4, 8 } });
		CellConstraints cons = new CellConstraints();
		
		result.add(new BHDescriptionLabel(AMOUNT_OF_PERIODS_BACK), cons.xywh(2, 2,1, 1));
		BHTextField tf3 = new BHTextField(AMOUNT_OF_PERIODS_BACK);
		ValidationRule[] rules3 = { VRMandatory.INSTANCE, VRIsInteger.INSTANCE, VRIsGreaterThan.GTETWO};
		tf3.setValidationRules(rules3);
		result.add(tf3, cons.xywh(4, 2, 1, 1));
		
		result.add(new BHDescriptionLabel(AMOUNT_OF_PERIODS_FUTURE), cons.xywh(6, 2,1, 1));
		BHTextField tf2 = new BHTextField(AMOUNT_OF_PERIODS_FUTURE);
		ValidationRule[] rules2 = { VRMandatory.INSTANCE, VRIsInteger.INSTANCE, VRIsGreaterThan.GTZERO};
		tf2.setValidationRules(rules2);
		result.add(tf2, cons.xywh(8, 2, 1, 1));		
		
		this.panel = result;
		return result;
	}

	@Override
	public void print(DTOScenario scenario) {
		System.out.println("ich bin im neuen Interface");
		TreeMap<DTOKeyPair, List<Calculable>> toBeDetermined = scenario.getPeriodStochasticKeysAndValues();
		System.out.println("Zeitreihenanalyse: Cashflows:"+toBeDetermined.firstEntry().toString());
		
		System.out.println("vergangen " + map.get(AMOUNT_OF_PERIODS_BACK));
		System.out.println("zukünftig " + map.get(AMOUNT_OF_PERIODS_FUTURE));
	}
	
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
	public TreeMap<Integer, Double> calculate() {
		//Berechnung für den Cashflow-Chart Vergangenheit bis in die Zukunft
		TreeMap<Integer, Double> result = new TreeMap();
	
	
		TreeMap<DTOKeyPair, List<Calculable>> periods = scenario.getPeriodStochasticKeysAndValues();
    	Entry<DTOKeyPair, List<Calculable>> cashfl = periods.firstEntry();
		
    	List<Calculable> cashValues = cashfl.getValue();
    	int p = map.get(AMOUNT_OF_PERIODS_BACK);
    	int f = map.get(AMOUNT_OF_PERIODS_FUTURE);
    	calc = new TimeSeriesCalculator_v3(cashValues);
    	List<Calculable> cashCalc = calc.calculateCashflows(f,p,true,100);
    	int counter = 1;
    	for(Calculable cashflow : cashCalc){
    		int key = -(cashCalc.size()-f)+counter;
    		double value = cashflow.toNumber().doubleValue();
    		result.put(key, value);
    		counter ++;
    		
    	}
		return result;
	}
	
	
	public TreeMap<Integer, Double>[]  calculateCompare(int p){
		TreeMap<Integer, Double> result[] = new TreeMap[2];
			result[0] = new TreeMap<Integer, Double>(); //Ist Cashflows
			result[1] = new TreeMap<Integer, Double>(); //Vergleichs Cashflows
			
			List<Calculable> cashProg = calc.calcultionTest_4_periods_to_history(p, p+1, 100, true);
			List<Calculable> cashIs = calc.getCashflows();
			int counter = 1;
			
			for(Calculable cashflow : cashProg){
	    		int key = -(cashProg.size())+counter;
	    		double value = cashflow.toNumber().doubleValue();
	    		result[1].put(key, value);
	    		counter ++;    		
	    	}
			counter = 1;
			for(Calculable cashflow : cashIs){
	    		int key = -(cashProg.size())+counter;
	    		double value = cashflow.toNumber().doubleValue();
	    		result[0].put(key, value);
	    		counter ++;    		
	    	}
		return result;
		
	}
	

}
