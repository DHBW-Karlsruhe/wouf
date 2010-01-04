package org.bh.data;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.bh.calculation.IShareholderValueCalculator;
import org.bh.calculation.IStochasticProcess;
import org.bh.data.types.Calculable;
import org.bh.data.types.DoubleValue;
import org.bh.platform.Services;

/**
 * Scenario DTO
 * 
 * <p>
 * This DTO contains scenariodata, acts as a root-element for periods
 * and as a child for project DTO
 * 
 * @author Michael Löckelt
 * @author Marcus Katzor
 * @version 0.4, 25.12.2009
 * 
 */

public class DTOScenario extends DTO<DTOPeriod> {
	private static final Logger log = Logger.getLogger(DTOScenario.class);
	
	/**
	 * Used to difference between values in the past and values in the future
	 * if futureValues is true, new childs are appended at the end of the childlist,
	 * if not, new childs are appended at the beginning
	 */
	protected boolean futureValues = true;
	
	
	
	protected IShareholderValueCalculator dcfMethod;
	
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
		 * 	corporate income tax 
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
		
		/**
		 * Stochastic process
		 */
		STOCHASTIC_PROCESS;

		@Override
		public String toString() {
			return getClass().getName() + "." + super.toString();
		}	
	}
	
	/**
	 * Standard constructor
	 * @author Marcus Katzor
	 */
	public DTOScenario()
	{
		super(Key.values());
		log.debug("Object created");
	}
	
    /**
     * initialize key and method list
     */
	public DTOScenario(boolean futureValues) {
		super(Key.values());
		this.futureValues = futureValues;
		log.debug("Object created");
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}
	
	@Override
	public DTOPeriod addChild(DTOPeriod child) throws DTOAccessException {
		DTOPeriod result = super.addChild(child,this.futureValues);
		child.scenario = this;
		refreshPeriodReferences();
		return result;
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
		
		log.debug("PeriodReferences refreshed!");
	}
	
	/**
	 * Gets tax for scenario.
	 * @return Tax for scenario.
	 */
	public Calculable getTax() {
		
		DoubleValue ctax = (DoubleValue) this.get(DTOScenario.Key.CTAX);
		DoubleValue btax = (DoubleValue) this.get(DTOScenario.Key.BTAX);
		
		DoubleValue myTax = (DoubleValue) btax.mul(new DoubleValue(0.5)).mul(ctax.mul(new DoubleValue(-1)).add(new DoubleValue(1)));
		myTax = (DoubleValue) myTax.add(ctax);
		
		return myTax;
	}

	/**
	 * Returns a reference to the DCF method.
	 * @return Reference to the DCF method.
	 */
	public IShareholderValueCalculator getDCFMethod() {
		return Services.getDCFMethod(get(Key.DCF_METHOD).toString());
	}
	
	/**
	 * Returns a reference to the stochastic process.
	 * @return Reference to the stochastic process.
	 */
	public IStochasticProcess getStochasticProcess() {
		return Services.getStochasticProcess(get(Key.STOCHASTIC_PROCESS).toString());
	}
	
	/**
	 * Returns all keys whose values have to be determined stochastically.
	 * @return List of keys.
	 */
	public List<DTOKeyPair> getPeriodStochasticKeys() {
		// @TODO
		throw new UnsupportedOperationException();
	}
	
	/**
	 * This method returns the keys whose values are determined stochastically
	 * together with their values in the past periods.
	 * @see #getPeriodStochasticKeys()
	 * @return Keys and past values.
	 */
	public TreeMap<DTOKeyPair, List<Calculable>> getPeriodStochasticKeysAndValues() {
		List<DTOKeyPair> keys = getPeriodStochasticKeys();
		TreeMap<DTOKeyPair, List<Calculable>> map = new TreeMap<DTOKeyPair, List<Calculable>>();
		DTOPeriod lastPeriod = children.getLast();
		for (DTOKeyPair key : keys) {
			List<Calculable> pastValues = new ArrayList<Calculable>();
			for (DTOPeriod period : children) {
				if (period == lastPeriod)
					break;
				IPeriodicalValuesDTO periodValuesDto = period.getPeriodicalValuesDTO(key.getDtoId());
				pastValues.add(periodValuesDto.getCalculable(key.getKey()));
			}
			map.put(key, pastValues);
		}
		return map;
	}
	
	/**
	 * Returns flag for differentiation between
	 * past and future values.
	 * @author Marcus Katzor
	 * @return
	 */
	public boolean isFutureValues() {
		return futureValues;
	}
	
	/**
	 * Sets flag for differentiation between
	 * past and future values.
	 * @author Marcus Katzor	
	 * @param futureValues
	 */
	public void setFutureValues(boolean futureValues) {
		this.futureValues = futureValues;
	}
	
	public void regenerateMethodsList() {
		regenerateMethodsList(Key.values());
	}
}
