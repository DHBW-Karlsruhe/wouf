package org.bh.data;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.bh.calculation.IShareholderValueCalculator;
import org.bh.calculation.IStochasticProcess;
import org.bh.data.types.Calculable;
import org.bh.data.types.DoubleValue;
import org.bh.data.types.ObjectValue;
import org.bh.data.types.StringValue;
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
 * 
 */

public class DTOScenario extends DTO<DTOPeriod> {
	private static final long serialVersionUID = -2952168332645683235L;
	private static final Logger log = Logger.getLogger(DTOScenario.class);

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

		/**
		 * Stochastic process
		 */
		STOCHASTIC_PROCESS,
		
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
		STOCHASTIC_PARAMETERS;

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
		DTOPeriod result = super.addChild(child, isDeterministic());
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
		return Services.getStochasticProcess(get(Key.STOCHASTIC_PROCESS)
				.toString());
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
		DTOPeriod lastPeriod = children.getLast();
		for (DTOKeyPair key : keys) {
			List<Calculable> pastValues = new ArrayList<Calculable>();
			for (DTOPeriod period : children) {
				if (period == lastPeriod)
					break;
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
		return new StringValue("true").equals(values.get(Key.INTERVAL_ARITHMETIC.toString()));
	}

}
