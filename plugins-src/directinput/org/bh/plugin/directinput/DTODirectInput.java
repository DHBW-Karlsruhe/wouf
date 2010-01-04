package org.bh.plugin.directinput;

import org.apache.log4j.Logger;
import org.bh.data.DTO;
import org.bh.data.IPeriodicalValuesDTO;


/**
 * DTO for directly inputing
 *
 * Data Transfer Object which holds values for one period which
 * can directly be used for calculating the shareholder value. 
 *
 * @author Robert Vollmer
 * @author Michael Löckelt
 * @version 0.4, 16.12.2009
 *
 */

@SuppressWarnings("unchecked")
public class DTODirectInput extends DTO implements IPeriodicalValuesDTO {
	private static final String UNIQUE_ID = "directinput";
	private static final Logger log = Logger.getLogger(DTODirectInput.class);
	
	public enum Key {
		/**
		 * FreeCashFlow
		 */
		@Stochastic
		FCF,
		
		/**
		 * Liabilities
		 */
		@Stochastic
		LIABILITIES,
	}
	
    /**
     * initialize key and method list
     */
	public DTODirectInput() {
		super(Key.values());
		log.debug("Object created!");
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}
	
	@Override
	public String getUniqueId() {
		return UNIQUE_ID;
	}
	
	public static String getUniqueIdStatic() {
		return UNIQUE_ID;
	}
	
	public void regenerateMethodsList() {
		regenerateMethodsList(Key.values());
	}
}
