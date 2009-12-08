package org.bh.data;

import java.util.Arrays;
import java.util.List;

public class DTOScenario extends ChildrenDTO<Value, IPeriodicalValuesDTO> {
	private static final List<String> AVAILABLE_KEYS = Arrays.asList("rendite_ek", "rendite_fk", "sg", "sks");
	private static final List<String> AVAILABLE_METHODS = Arrays.asList();
	
    /**
     * initialize key and method list
     */
	public DTOScenario() {
		availableKeys = AVAILABLE_KEYS;
		availableMethods = AVAILABLE_METHODS;
	}
}
