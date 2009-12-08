package org.bh.plugin.directinput.data;

import java.util.Arrays;
import java.util.List;

import org.bh.data.DTO;
import org.bh.data.IPeriodicalValuesDTO;
import org.bh.data.Value;

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

public class DTODirectInput extends DTO<Value> implements IPeriodicalValuesDTO {
	private static final String UNIQUE_ID = "directinput";
	// @TODO correct keys
	private static final List<String> AVAILABLE_KEYS = Arrays.asList("fremdkapital", "fcf");
	private static final List<String> AVAILABLE_METHODS = Arrays.asList();
	
    /**
     * initialize key and method list
     */
	public DTODirectInput() {
		availableKeys = AVAILABLE_KEYS;
		availableMethods = AVAILABLE_METHODS;
	}
	
	@Override
	public String getUniqueId() {
		return UNIQUE_ID;
	}
}
