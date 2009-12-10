package org.bh.data;

import java.util.Arrays;
import java.util.List;
import java.util.ServiceLoader;

import org.bh.calculation.ICalculationPreparer;
import org.bh.calculation.sebi.Value;
import org.bh.platform.PluginManager;

public class DTOPeriod extends DTO {		
	private static final List<String> AVAILABLE_KEYS = Arrays.asList("METHOD", "METHOD");
	private static final List<String> AVAILABLE_METHODS = Arrays.asList("fremdkapital", "fcf");  // @TODO translate
	
    /**
     * initialize key and method list
     */
	public DTOPeriod() {
		
		availableKeys = AVAILABLE_KEYS;
		availableMethods = AVAILABLE_METHODS;
	}
	
	public Value getFremdkapital() {  // @TODO translate
		Value result = getChildrenValue("fremdkapital"); // @TODO translate
		if (result != null)
			return result;
		
		ServiceLoader<ICalculationPreparer> preparers = PluginManager.getInstance().getServices(ICalculationPreparer.class);
		for (ICalculationPreparer preparer : preparers) {
			result = preparer.getFremdkapital(this);  // @TODO translate
			if (result != null)
				break;
		}
		if (result == null) {
			throw new DTOAccessException("Cannot calculate FK"); // @TODO translate
		}
		return result;
	}
	
	public Value getFCF() {
		Value result = getChildrenValue("fcf");
		if (result != null)
			return result;
		
		ServiceLoader<ICalculationPreparer> preparers = PluginManager.getInstance().getServices(ICalculationPreparer.class);
		for (ICalculationPreparer preparer : preparers) {
			result = preparer.getFCF(this);
			if (result != null)
				break;
		}
		if (result == null) {
			throw new DTOAccessException("Cannot calculate FCF"); // @TODO translate
		}
		return result;
	}
	
	protected Value getChildrenValue(String key) {
		for (IDTO child : children) {
			try {
				Value result = child.get(key);
				return result;
			} catch (DTOAccessException e) {
				continue;
			}
		}
		return null;
	}

	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}
}
