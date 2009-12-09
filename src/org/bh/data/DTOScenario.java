package org.bh.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DTOScenario extends DTO {
	
	public enum DTOScenarioKeys 
	{
		/**
		 * Rendite Eigenkapital
		 */
		REK("rendite_ek"),
		RFK("rendite_fk"),
		SG("sg"),
		SKS("sks");
		
		private String key;
		
		private DTOScenarioKeys(String key)
		{
			this.key = key;		
		}	

		
		public static List<String> getKeys()
		{
			List<String> result = new ArrayList<String>();
			for (DTOScenarioKeys key : values())
			{
				result.add(key.key);
			}
			return result;
		}
		
	}
	
	//private static final List<String> AVAILABLE_KEYS = Arrays.asList("rendite_ek", "rendite_fk", "sg", "sks");
	private static final List<String> AVAILABLE_METHODS = Arrays.asList();
	
    /**
     * initialize key and method list
     */
	public DTOScenario() {
		
		availableKeys = DTOScenarioKeys.getKeys();
		availableMethods = AVAILABLE_METHODS;
		
		
	}

	@Override
	public Boolean validate() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	
}
