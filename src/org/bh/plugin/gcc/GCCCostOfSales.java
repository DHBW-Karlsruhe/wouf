package org.bh.plugin.gcc;

import org.bh.calculation.ICalculationPreparer;
import org.bh.calculation.sebi.Calculable;
import org.bh.data.DTOPeriod;
import org.bh.data.IPeriodicalValuesDTO;

/**
 * Calculates the FCF from the current profit&loss statement, the current balance sheet and the
 * balance sheet from the year before.
 * 
 * @author Robert Vollmer  
 */
public class GCCCostOfSales implements ICalculationPreparer {
	@Override
	public Calculable getFCF(DTOPeriod period) {
		if (period.getPrevious() == null)
			return null;
		
		IPeriodicalValuesDTO plsNow = period.getPeriodicalValuesDTO("gcc_pls_costofsales");
		if (plsNow == null)
			return null;
		IPeriodicalValuesDTO bsNow = period.getPeriodicalValuesDTO("gccbalancesheet");
		if (bsNow == null)
		    return null;
		IPeriodicalValuesDTO bsBefore = period.getPrevious().getPeriodicalValuesDTO("gccbalancesheet09");
		if (bsBefore == null)
		    if (bsBefore == null)
			return null;
		
	/*	Calculable result = 
			plsNow.getCalculable(DTOGCCProfitLossStatementCostOfSales.Key.VALUE1)
				.add(plsNow.getCalculable(DTOGCCProfitLossStatementCostOfSales.Key.VALUE2))
				.add(
					bsNow.getCalculable(DTOGCCBalanceSheet.Key.DUMMY)
						.sub(bsBefore.getCalculable(DTOGCCBalanceSheet09.DTOGCCBalanceSheet.DUMMY))
					);
	*/	return null;
	} 

	@Override
	public Calculable getLiabilities(DTOPeriod period) {
		return null;
	}

}
