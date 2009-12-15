package org.bh.plugin.directinput;

import org.bh.data.DTO;
import org.bh.data.IPeriodicalValuesDTO;


/**
 * DTO for directly inputing
 *
 * Data Transfer Object which holds values for one period which
 * can directly be used for calculating the shareholder value. 
 *
 * @author Robert Vollmer
 * @version 0.1, 06.12.2009
 *
 */

@SuppressWarnings("unchecked")
public class DTODirectInput extends DTO implements IPeriodicalValuesDTO {
	private static final String UNIQUE_ID = "directinput";
	
	public enum Key {
		FCF,
		LIABILITIES,
	}
	
    /**
     * initialize key and method list
     */
	public DTODirectInput() {
		super(Key.values());
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
}
