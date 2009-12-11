package org.bh.data;

import java.util.ServiceLoader;

import org.bh.calculation.ICalculationPreparer;
import org.bh.calculation.sebi.Calculable;
import org.bh.platform.PluginManager;

public class DTOPeriod extends DTO<IPeriodicalValuesDTO> {
	public enum Key {
		@Method LIABILITIES,
		@Method FCF,
	}
	
	DTOPeriod previous = null;
	DTOPeriod next = null;
	
    /**
     * initialize key and method list
     */
	public DTOPeriod() {
		super(Key.values());
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
	
	public synchronized DTOPeriod getPrevious() {
		return previous;
	}

	public synchronized DTOPeriod getNext() {
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
}
