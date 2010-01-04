package org.bh.data;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.bh.calculation.ICalculationPreparer;
import org.bh.data.types.Calculable;
import org.bh.data.types.IValue;
import org.bh.platform.PluginManager;

/**
 * Project DTO
 * 
 * <p>
 * This DTO contains projectdata and acts as a root-element
 * 
 * @author Michael Löckelt
 * @version 0.2, 16.12.2009
 * 
 */

public class DTOPeriod extends DTO<IPeriodicalValuesDTO> {
	
	private static final Logger log = Logger.getLogger(DTOPeriod.class);
	
	public enum Key {
		/**
		 * identify the position of this period
		 * for example a year or a quarter
		 */
		NAME,
		
		/**
		 * total liabilities
		 */
		@Method LIABILITIES,
		
		/**
		 * FreeCashFlow
		 */
		@Method FCF;
		
		@Override
		public String toString() {
			return getClass().getName() + "." + super.toString();
		}	
	}
	
	DTOPeriod previous = null;
	DTOPeriod next = null;
	DTOScenario scenario = null;
	
    /**
     * initialize key and method list
     */
	public DTOPeriod() {
		super(Key.values());
		//log.debug("Object created!");
	}
	
	/**
	 * Get or calculate the liabilities for this period.
	 * @return
	 */
	public Calculable getLiabilities() {
		Calculable result = getChildrenValue(Key.LIABILITIES);
		if (result != null)
			return result;
		
		ServiceLoader<ICalculationPreparer> preparers = PluginManager.getInstance().getServices(ICalculationPreparer.class);
		for (ICalculationPreparer preparer : preparers) {
			result = preparer.getLiabilities(this);
			if (result != null)
				break;
		}
		if (result == null) {
			throw new DTOAccessException("Cannot calculate liabilities");
		}
		return result;
	}
	
	/**
	 * Get or calculate the FCF for this period.
	 * @return
	 */
	public Calculable getFCF() {
		Calculable result = getChildrenValue(Key.FCF);
		if (result != null)
			return result;

		ServiceLoader<ICalculationPreparer> preparers = PluginManager.getInstance().getServices(ICalculationPreparer.class);
		for (ICalculationPreparer preparer : preparers) {
			result = preparer.getFCF(this);
			if (result != null)
				break;
		}
		if (result == null) {
			throw new DTOAccessException("Cannot calculate FCF");
		}
		return result;
	}
	
	/**
	 * Find a value in one of the children of this period.
	 * @param key
	 * @return The value if it was found, otherwise null;
	 */
	protected Calculable getChildrenValue(Key key) {
		for (IPeriodicalValuesDTO child : children) {
			try {
				Calculable result = child.getCalculable(key);
				return result;
			} catch (DTOAccessException e) {
				continue;
			}
		}
		return null;
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}
	
	/**
	 * Get the DTO for the previous period.
	 * @return DTO for the previous period.
	 */
	public DTOPeriod getPrevious() {
		return previous;
	}

	/**
	 * Get the DTO for the following period.
	 * @return DTO for the following period.
	 */
	public DTOPeriod getNext() {
		return next;
	}

	/**
	 * Returns the first matching DTO with periodical values.
	 * @param uniqueId Type of the DTO.
	 * @return The DTO or null if none could be found.
	 */
	public IPeriodicalValuesDTO getPeriodicalValuesDTO(String uniqueId) {
		for (IPeriodicalValuesDTO child : children) {
			if (child.getUniqueId().equals(uniqueId))
				return child;
		}
		return null;
	}
	
	/**
	 * Get taxes for scenario.
	 * @return Taxes for scenario.
	 */
	public Calculable getTax() {
		return scenario.getTax();
	}
	
	public DTOScenario getScenario() {
		return scenario;
	}

	@Override
	public void regenerateMethodsList() {
		regenerateMethodsList(Key.values());
	}

	/**
	 * return a period clone (used in stochastic process)
	 * @author Michael Löckelt
	 * TODO Check by Robert / Marcus
	 */
	@Override
	public DTOPeriod clone() throws DTOAccessException {
		DTOPeriod result = new DTOPeriod();
		try {
			for (Map.Entry<String, IValue> entry: values.entrySet()) {
				result.put(entry.getKey(), entry.getValue().clone());
				// Copy and add children to the new instance
			}
			for (IPeriodicalValuesDTO child : children) {
				//TODO check mit Robert
				result.addChild((IPeriodicalValuesDTO) child.clone());
			}
		} catch (Exception e) {
			throw new DTOAccessException("An error occured during the cloning of a DTO. Class: " 
					+ getClass().getName());
		}
		return result;
	}
	
}
