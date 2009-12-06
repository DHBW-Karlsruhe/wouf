package org.bh.plugin.gccbalancesheet09.data;

import java.util.Arrays;
import org.bh.platform.DTO;
import org.bh.platform.Value;

/**
 * HGBBalanceSheet DTO 
 *
 * <p>
 * Data Transfer Object to handle GCCBalanceSheet09 values and methods
 *
 * @author Michael Lšckelt
 * @version 0.1, 03.12.2009
 *
 */

public class DTOGCCBalanceSheet09 extends DTO<Value> {
    
    /**
     * initialize key and method list
     */
	public DTOGCCBalanceSheet09() {
		// possible keys
		availableKeys = Arrays.asList(
				//
				"");								
		
		// possible methods
		availableMethods = Arrays.asList("Abc");
	}
	
}
