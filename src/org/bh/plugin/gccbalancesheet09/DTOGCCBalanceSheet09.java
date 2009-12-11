package org.bh.plugin.gccbalancesheet09;

import org.bh.data.DTO;
import org.bh.data.IPeriodicalValuesDTO;

/**
 * GCCBalanceSheet DTO.
 * 
 * <p>
 * Data Transfer Object to handle GCCBalanceSheet09 values and methods
 * 
 * @author Michael Löckelt 
 * @version 0.3, 07.12.2009
 * 
 */

@SuppressWarnings("unchecked")
public class DTOGCCBalanceSheet09 extends DTO implements IPeriodicalValuesDTO {
	private static final String UNIQUE_ID = "gccbalancesheet09";
	
    public enum Key {
    	DUMMY,
    	LIABILITIES,
    }

    public DTOGCCBalanceSheet09() {
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
