/*******************************************************************************
 * Copyright 2011: Matthias Beste, Hannes Bischoff, Lisa Doerner, Victor Guettler, Markus Hattenbach, Tim Herzenstiel, Günter Hesse, Jochen Hülß, Daniel Krauth, Lukas Lochner, Mark Maltring, Sven Mayer, Benedikt Nees, Alexandre Pereira, Patrick Pfaff, Yannick Rödl, Denis Roster, Sebastian Schumacher, Norman Vogel, Simon Weber 
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
package org.bh.data;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.bh.calculation.IShareholderValueCalculator;
import org.bh.calculation.IStochasticProcess;
import org.bh.calculation.ITimeSeriesProcess;
import org.bh.data.types.Calculable;
import org.bh.data.types.DoubleValue;
import org.bh.data.types.IntegerValue;
import org.bh.data.types.ObjectValue;
import org.bh.gui.swing.BHMenuBar;
import org.bh.platform.Services;

/**
 * Scenario DTO
 * 
 * <p>
 * This DTO contains scenariodata, acts as a root-element for periods and as a
 * child for project DTO
 * 
 * @author Michael Löckelt
 * @author Marcus Katzor
 * @version 0.4, 25.12.2009
 * @update 23.12.2010 Timo Klein
 */

public class DTOScenario extends DTO<DTOPeriod> {
	private static final long serialVersionUID = -2952168332645683235L;
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(DTOScenario.class);
	private boolean isCalculated = false;

	public enum Key {
		/**
		 * equity yield
		 */
		REK,

		/**
		 * liability yield
		 */
		RFK,

		/**
		 * corporate income tax
		 */
		CTAX,

		/**
		 * business tax
		 */
		BTAX,

		/**
		 * summed tax
		 */
		@Method
		TAX,

		/**
		 * name
		 */
		NAME,

		/**
		 * comment
		 */
		COMMENT,

		/**
		 * Identifier
		 */
		IDENTIFIER,

		/**
		 * DCF method
		 */
		DCF_METHOD,
		
		DCF_METHOD_long,

		/**
		 * Stochastic process
		 */
		STOCHASTIC_PROCESS,
		
		/**
		 * TimeSeries process
		 */
		TIMESERIES_PROCESS,
		
		/**
		 * This scenario is calculated branch specific. So we have
		 * to take care of all that data here.
		 */
		BRANCH_SPECIFIC,
		INDUSTRY,
		REPRESENTATIVE,
		/**
		 * Period type
		 */
		PERIOD_TYPE,
		
		/**
		 * Whether to use interval arithmetic
		 */
		INTERVAL_ARITHMETIC,
		
		/**
		 * List of keys selected for stochastic process
		 */
		STOCHASTIC_KEYS,
		
		/**
		 * Parameters for the stochastic process
		 */
		STOCHASTIC_PARAMETERS, DETERMINISTIC_PERIODS,
		
		/**
		 * 
		 * Tooltips
		 */
		TOOLTIP_APV,
		TOOLTIP_FCF,
		TOOLTIP_FTE,
		TOOLTIP_DCF,
		TOOLTIP_ALL;

		@Override
		public String toString() {
			return getClass().getName() + "." + super.toString();
		}
	}

	/**
	 * Standard constructor
	 * 
	 * @author Marcus Katzor
	 */
	public DTOScenario() {
		this(true);
	}

	/**
	 * initialize key and method list
	 */
	public DTOScenario(boolean isDeterministic) {
		super(Key.class);
		if (!isDeterministic) {
			values.put(Key.STOCHASTIC_PROCESS.toString(), null);
		}
	}

	@Override
	public DTOPeriod addChild(DTOPeriod child) throws DTOAccessException {
		DTOPeriod result = super.addChild(child, true);
		child.scenario = this;
		refreshPeriodReferences();
		return result;
	}
	
	@Override
	public DTOPeriod addChild(DTOPeriod child, boolean addLast) throws DTOAccessException {
		DTOPeriod result = super.addChild(child, addLast);
		child.scenario = this;
		refreshPeriodReferences();
		return result;
	}
	
	@Override
	public void addChildToPosition(DTOPeriod child, int pos){
	    super.addChildToPosition(child, pos);
	    child.scenario = this;
	    refreshPeriodReferences();
	}

	@Override
	public DTOPeriod removeChild(int index) throws DTOAccessException {
		DTOPeriod removedPeriod = super.removeChild(index);
		removedPeriod.next = removedPeriod.previous = null;
		removedPeriod.scenario = null;
		refreshPeriodReferences();
		return removedPeriod;
	}

	private void refreshPeriodReferences() {
		DTOPeriod previous = null;
		for (DTOPeriod child : children) {
			child.previous = previous;
			if (previous != null)
				previous.next = child;
			previous = child;
		}
		if (previous != null)
			previous.next = null;
	}
	
	
	/**
	 * Gets tax for scenario.
	 * 
	 * @return Tax for scenario.
	 */
	public Calculable getTax() {
		Calculable ctax = getCalculable(DTOScenario.Key.CTAX);
		Calculable btax = getCalculable(DTOScenario.Key.BTAX);

		Calculable myTax = btax.mul(new DoubleValue(0.5)).mul(
				ctax.mul(new DoubleValue(-1)).add(new DoubleValue(1)))
				.add(ctax);

		return myTax;
	}

	/**
	 * Returns a reference to the DCF method.
	 * 
	 * @return Reference to the DCF method.
	 */
	public IShareholderValueCalculator getDCFMethod() {
		return Services.getDCFMethod(get(Key.DCF_METHOD).toString());
	}

	/**
	 * Returns a reference to the stochastic process.
	 * 
	 * @return Reference to the stochastic process.
	 */
	public IStochasticProcess getStochasticProcess() {
		return Services.getStochasticProcess(get(Key.STOCHASTIC_PROCESS).toString()
				.toString()).createNewInstance(this);
	}
	
	/**
	 * Returns a reference to the time series process.
	 * 
	 * @return Reference to the time series process.
	 */
	public ITimeSeriesProcess getTimeSeriesProcess(String id) {
		return Services.getTimeSeriesProcess(id).createNewInstance(this);
	}
	
	

	/**
	 * Returns all keys whose values have to be determined stochastically.
	 * 
	 * @return List of keys.
	 */
	@SuppressWarnings("unchecked")
	public List<DTOKeyPair> getPeriodStochasticKeys() {
		return (List<DTOKeyPair>)((ObjectValue) get(Key.STOCHASTIC_KEYS)).getObject();
	}

	/**
	 * This method returns the keys whose values are determined stochastically
	 * together with their values in the past periods.
	 * 
	 * @see #getPeriodStochasticKeys()
	 * @return Keys and past values.
	 */
	public TreeMap<DTOKeyPair, List<Calculable>> getPeriodStochasticKeysAndValues() {
		List<DTOKeyPair> keys = getPeriodStochasticKeys();
		TreeMap<DTOKeyPair, List<Calculable>> map = new TreeMap<DTOKeyPair, List<Calculable>>();
		
		//TODO Michaael - check whether lastPeriod is needed
		//DTOPeriod lastPeriod = children.getLast();
		
		for (DTOKeyPair key : keys) {
			List<Calculable> pastValues = new ArrayList<Calculable>();
			for (DTOPeriod period : children) {
				IPeriodicalValuesDTO periodValuesDto = period
						.getPeriodicalValuesDTO(key.getDtoId());
				pastValues.add(periodValuesDto.getCalculable(key.getKey()));
			}
			map.put(key, pastValues);
		}
		return map;
	}

	/**
	 * Returns flag for differentiation between past and future values.
	 * 
	 * @author Marcus Katzor
	 * @return
	 */
	public boolean isDeterministic() {
	    	//System.out.println("---"+values.containsKey(Key.STOCHASTIC_PROCESS.toString()));
		return !values.containsKey(Key.STOCHASTIC_PROCESS.toString());
	}

	@Override
	public String toString() {
		String result;
		try
		{
			result = get(Key.NAME).toString();
		}
		catch  (DTOAccessException e)
		{
			return super.toString();
		}
		
		return result;
	}
	

	
	@Override
	public boolean isValid(boolean recursive) {
		return super.isValid(recursive) && children.size() > 1;
	}

	public boolean isIntervalArithmetic() {
		return new IntegerValue(1).equals(values.get(Key.INTERVAL_ARITHMETIC.toString()));
	}
	
	/**
	 * check if the object is calculated
	 */
	public void setCalculated (boolean b){
		isCalculated = b;
		BHMenuBar.enableFilePrint();
		BHMenuBar.enableSceExport();
		
	}
	
	public boolean isCalculated (){
		return isCalculated;
	}

}
