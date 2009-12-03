package org.bh.plugin.hgbbalancesheet09.data;

import java.util.Arrays;
import org.bh.platform.DTO;
import org.bh.platform.Value;

/**
 * HGBBalanceSheet DTO 
 *
 * <p>
 * Data Transfer Object to handle HGBBalanceSheets values and methods
 *
 * @author Michael Lšckelt
 * @version 1.0, 03.12.2009
 *
 */

public class DTOHGBBalanceSheet09 extends DTO<Value> {
    
    /**
     * initialize key and method list
     */
	public DTOHGBBalanceSheet09()
	{
		// possible keys
		availableKeys = Arrays.asList(
				//
				"");								
		
		// possible methods
		availableMethods = Arrays.asList("Abc");
	}
	
}
