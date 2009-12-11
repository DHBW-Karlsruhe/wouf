package org.bh.plugin.gcc;

import org.bh.data.DTO;
import org.bh.data.IPeriodicalValuesDTO;

@SuppressWarnings("unchecked")
public class DTOGCCProfitLossStatementCostOfSales extends DTO implements IPeriodicalValuesDTO {
	private static final String UNIQUE_ID = "gcc_pls_costofsales";
	
    public enum Key {
    	DUMMY,
    	VALUE1,
    	VALUE2,
    }
    
    public DTOGCCProfitLossStatementCostOfSales() {
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
