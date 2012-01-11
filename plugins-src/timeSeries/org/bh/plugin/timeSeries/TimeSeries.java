/*******************************************************************************
 * Copyright 2011: Matthias Beste, Hannes Bischoff, Lisa Doerner, Victor Guettler, Markus Hattenbach, Tim Herzenstiel, Günter Hesse, Jochen Hülß, Daniel Krauth, Lukas Lochner, Mark Maltring, Sven Mayer, Benedikt Nees, Alexandre Pereira, Patrick Pfaff, Yannick Rödl, Denis Roster, Sebastian Schumacher, Norman Vogel, Simon Weber * : Anna Aichinger, Damian Berle, Patrick Dahl, Lisa Engelmann, Patrick Groß, Irene Ihl, Timo Klein, Alena Lang, Miriam Leuthold, Lukas Maciolek, Patrick Maisel, Vito Masiello, Moritz Olf, Ruben Reichle, Alexander Rupp, Daniel Schäfer, Simon Waldraff, Matthias Wurdig, Andreas Wußler
 *
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
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.bh.calculation.ITimeSeriesProcess;
import org.bh.data.DTO;
import org.bh.data.DTOKeyPair;
import org.bh.data.DTOPeriod;
import org.bh.data.DTOScenario;
import org.bh.data.types.Calculable;
import org.bh.data.types.DistributionMap;
import org.bh.gui.swing.comp.BHDescriptionLabel;
import org.bh.gui.swing.comp.BHProgressBar;
import org.bh.gui.swing.comp.BHTextField;
import org.bh.platform.Services;
import org.bh.platform.i18n.BHTranslator;
import org.bh.validation.VRIsGreaterThan;
import org.bh.validation.VRIsInteger;
import org.bh.validation.VRIsLowerThan;
import org.bh.validation.VRMandatory;
import org.bh.validation.ValidationRule;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * 
 * TimeSeries Hauptklasse. Liefert alle GUI-Elemente. Verwendet die
 * CalculatorKlasse der Version 3
 * 
 * 
 * @author Timo Klein, Andreas Wussler, Vito Masiello
 * @version 1.0, 1.2.2011
 * @update Yannick Rödl, 22.12.2011
 */

public class TimeSeries implements ITimeSeriesProcess {
	private static final Logger log = Logger.getLogger(TimeSeries.class);

	private static final String AMOUNT_OF_PERIODS_BACK = "amountOfPeriodsBack";
	private static final String AMOUNT_OF_PERIODS_FUTURE = "amountOfPeriodsFuture";
	private JPanel panel;
	// -------------------------------------------------------

	private static final String UNIQUE_ID = "timeSeries";
	private static final String GUI_KEY = "timeSeries";

	private DTOScenario scenario;
	private HashMap<String, Double> internalMap;
	private HashMap<String, Integer> map;
	private TimeSeriesCalculator_v3 calc;
	private BHProgressBar progressB;
	private boolean branchSpecific = false;

	@Override
	public String getGuiKey() {
		return GUI_KEY;
	}

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
	public JPanel calculateParameters(boolean branchSpecific) {
		this.branchSpecific = branchSpecific;
		
		internalMap = new HashMap<String, Double>();
		map = new HashMap<String, Integer>();
		JPanel result = new JPanel();

		String rowDef = "4px,p,4px";
		String colDef = "4px,right:pref,4px,60px:grow,8px:grow,right:pref,4px,max(35px;pref):grow,4px:grow";
		FormLayout layout = new FormLayout(colDef, rowDef);
		result.setLayout(layout);
		layout.setColumnGroups(new int[][] { { 4, 8 } });
		CellConstraints cons = new CellConstraints();

		result.add(new BHDescriptionLabel(AMOUNT_OF_PERIODS_BACK),
				cons.xywh(2, 2, 1, 1));
		BHTextField tf3 = new BHTextField(AMOUNT_OF_PERIODS_BACK);
		ValidationRule[] rules3 = { VRMandatory.INSTANCE, VRIsInteger.INSTANCE,
				VRIsGreaterThan.GTETWO, VRIsLowerThan.LTEHUNDRED };
		tf3.setValidationRules(rules3);
		result.add(tf3, cons.xywh(4, 2, 1, 1));

		result.add(new BHDescriptionLabel(AMOUNT_OF_PERIODS_FUTURE),
				cons.xywh(6, 2, 1, 1));
		BHTextField tf2 = new BHTextField(AMOUNT_OF_PERIODS_FUTURE);
		ValidationRule[] rules2 = { VRMandatory.INSTANCE, VRIsInteger.INSTANCE,
				VRIsGreaterThan.GTZERO, VRIsLowerThan.LTEHUNDRED };
		tf2.setValidationRules(rules2);
		result.add(tf2, cons.xywh(8, 2, 1, 1));

		this.panel = result;
		return result;
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
	public DistributionMap calculate() {
		log.info("Start time series analysis");
		// Berechnung für den Cashflow-Chart Vergangenheit bis in die Zukunft
		TreeMap<Integer, Double> averageCashflows = new TreeMap<Integer, Double>();
		
		DistributionMap resultMap = new DistributionMap(1);

		TreeMap<DTOKeyPair, List<Calculable>> periods = scenario
				.getPeriodStochasticKeysAndValues(branchSpecific);
		Entry<DTOKeyPair, List<Calculable>> cashfl = periods.firstEntry();

		List<Calculable> cashValues = null;
		
//		log.debug("First entry in periods: " + cashfl.getKey());
//		log.debug("FCF Key " + BHTranslator.getInstance().translate("org.bh.plugin.directinput.DTODirectInput$Key.FCF"));
		//We have the Free Cash Flow only in Direct Input Scenario.
		if(cashfl.getKey().toString().equals(BHTranslator.getInstance().translate("org.bh.plugin.directinput.DTODirectInput$Key.FCF"))){
			cashValues = cashfl.getValue();
			log.debug("Default TimeSeries Cashflow: " + cashfl.getValue());
			
		} else {
			// We have to calculate the cashvalues manually
			int i = 0;
			cashValues = new LinkedList<Calculable>();
			for(DTOPeriod period: scenario.getChildren()){
				if(i > 0){ //First Period produces DTOAccessExceptions
					cashValues.add(period.getFCF());
					log.debug("Manually calculated cashflow: " + period.getFCF());
				}
				i++;
			}
		}
		
		int periodsInPast = map.get(AMOUNT_OF_PERIODS_BACK);
		int periodsInFuture = map.get(AMOUNT_OF_PERIODS_FUTURE);
		
		if (periodsInPast > cashValues.size() - 1) {
			periodsInPast = cashValues.size() - 1;
		}
		
		calc = new TimeSeriesCalculator_v3(cashValues, progressB, resultMap, scenario);

		//Start calculation
		DTO.setThrowEvents(false);
		List<Calculable> cashCalc = calc.calculateCashflows(periodsInFuture,
				periodsInPast, true, 1000, true, null, true);
		DTO.setThrowEvents(true);
		//End calculation
		
		//Calculate arithmetic average
		int counter = 1;
		for (Calculable cashflow : cashCalc) {
			int key = -(cashCalc.size() - periodsInFuture) + counter;
			double value = cashflow.toNumber().doubleValue();
			averageCashflows.put(key, value);
			counter++;
		}
		
		branchSpecific = false;
		
		log.info("Time series analysis finished.");
		
		resultMap.setTimeSeries(this, averageCashflows, calculateCompare(3));
		
		return resultMap;
	}

	@Override
	public TreeMap<Integer, Double>[] calculateCompare(int p) {
		@SuppressWarnings("unchecked")
		TreeMap<Integer, Double> result[] = new TreeMap[2];
		result[0] = new TreeMap<Integer, Double>(); // Ist Cashflows
		result[1] = new TreeMap<Integer, Double>(); // Vergleichs Cashflows
		
		// System.out.println("TimeSeries: call calcultionTest_4_periods_to_history");
		List<Calculable> cashProg = calc
				.calcultionTest_4_periods_to_history_v2(p, 1000, false);
		// System.out.println("TimeSeries: call calcultionTest_4_periods_to_history beendet");
		List<Calculable> cashIs = calc.getCashflows();
		int counter = 1;

		for (Calculable cashflow : cashProg) {
			int key = -(cashProg.size()) + counter;
			double value = cashflow.toNumber().doubleValue();
			result[1].put(key, value);
			counter++;
		}
		
		counter = 1;
		for (Calculable cashflow : cashIs) {
			int key = -(cashProg.size()) + counter;
			double value = cashflow.toNumber().doubleValue();
			result[0].put(key, value);
			counter++;
		}
		
		return result;

	}

	@Override
	public void setProgressB(BHProgressBar progressB) {
		this.progressB = progressB;

	}

	@Override
	public void setInterrupted() {
		calc.setInterrupted();
	}

}
