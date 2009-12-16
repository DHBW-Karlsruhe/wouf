package org.bh.data;

import org.apache.log4j.Logger;
import org.bh.BusinessHorizon;
import org.bh.data.types.Tax;

/**
 * Scenario DTO
 * 
 * <p>
 * This DTO contains scenariodata, acts as a root-element for periods
 * and as a child for project DTO
 * 
 * @author Michael Löckelt
 * @version 0.2, 16.12.2009
 * 
 */

public class DTOScenario extends DTO<DTOPeriod> {
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
		 * tax
		 */
		TAX,
		
		/**
		 * name
		 */
		NAME,
		
		/**
		 * comment
		 */
		COMMENT
		
	}
	
    /**
     * initialize key and method list
     */
	public DTOScenario() {
		super(Key.values());
		log.debug("Object created");
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}
	
	@Override
	public DTOPeriod addChild(DTOPeriod child) throws DTOAccessException {
		DTOPeriod result = super.addChild(child);
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
	 * Get taxes for scenario.
	 * @return Taxes for scenario.
	 */
	public Tax getTax() {
		return (Tax)get(Key.TAX);
	}
}
