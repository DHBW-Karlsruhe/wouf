package org.bh.data;

import org.apache.log4j.Logger;
import org.bh.data.types.DoubleValue;
import org.bh.data.types.GermanTax;
import org.bh.data.types.Tax;

/**
 * Scenario DTO
 * 
 * <p>
 * This DTO contains scenariodata, acts as a root-element for periods
 * and as a child for project DTO
 * 
 * @author Michael Löckelt
 * @version 0.3, 17.12.2009
 * 
 */

public class DTOScenario extends DTO<DTOPeriod> {
	private static final Logger log = Logger.getLogger(DTOScenario.class);
	
	/**
	 * Used to difference between values in the past and values in the future
	 * if futureValues is true, new childs are appended at the end of the childlist,
	 * if not, new childs are appended at the beginning
	 */
	protected boolean futureValues;
	
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
	 * Get taxes for scenario.
	 * @return Taxes for scenario.
	 */
	public Tax getTax() {
		Tax myTax = new GermanTax((DoubleValue) this.get(DTOScenario.Key.CTAX),(DoubleValue) this.get(DTOScenario.Key.BTAX));
		return myTax;
		
	}
}
