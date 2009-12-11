package org.bh.plugin.gcc;

import org.bh.calculation.ICalculationPreparer;
import org.bh.calculation.sebi.Calculable;
import org.bh.calculation.sebi.DoubleValue;
import org.bh.calculation.sebi.GermanTax;
import org.bh.calculation.sebi.Tax;
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
		// Check whether this method is applicable for the DTOs in this and the previous period.
		if (period.getPrevious() == null)
			return null;
		
		Tax tax = period.getTax();
		if (!(tax instanceof GermanTax))
			return null;
		
		IPeriodicalValuesDTO plsNow = period.getPeriodicalValuesDTO("gcc_pls_costofsales");
		if (plsNow == null) {
			plsNow = period.getPeriodicalValuesDTO("gcc_pls_totalcost");
			if (plsNow == null)
				return null;
		}
		IPeriodicalValuesDTO bsNow = period.getPeriodicalValuesDTO("gccbalancesheet");
		if (bsNow == null)
		    return null;
		IPeriodicalValuesDTO bsPrev = period.getPrevious().getPeriodicalValuesDTO("gccbalancesheet");
		if (bsPrev == null)
		    if (bsPrev == null)
			return null;
		
		Calculable ebit = Calculable.addAll(
			plsNow.getCalculable(DTOGCCProfitLossStatementCostOfSales.Key.ZA),
			plsNow.getCalculable(DTOGCCProfitLossStatementCostOfSales.Key.SEE),
			plsNow.getCalculable(DTOGCCProfitLossStatementCostOfSales.Key.JUJF));
		
		Calculable nopat = ebit.sub(Calculable.mulAll(
			((GermanTax)tax).getSg(),
			new DoubleValue(1).sub(((GermanTax)tax).getSks()),
			ebit));
		
		Calculable bsCorrections = Calculable.addAll(
			bsNow.getCalculable(DTOGCCBalanceSheet.Key.RS)
			.sub(bsPrev.getCalculable(DTOGCCBalanceSheet.Key.RS)),

			bsNow.getCalculable(DTOGCCBalanceSheet.Key.AV)
			.sub(bsPrev.getCalculable(DTOGCCBalanceSheet.Key.AV)),
	
			(bsNow.getCalculable(DTOGCCBalanceSheet.Key.UV).sub(bsNow.getCalculable(DTOGCCBalanceSheet.Key.KBGGKS)))
			.sub((bsPrev.getCalculable(DTOGCCBalanceSheet.Key.UV).sub(bsPrev.getCalculable(DTOGCCBalanceSheet.Key.KBGGKS))))
			);
		
		return nopat.add(bsCorrections);
	} 

	@Override
	public Calculable getLiabilities(DTOPeriod period) {
		IPeriodicalValuesDTO bs = period.getPeriodicalValuesDTO("gccbalancesheet");
		if (bs == null)
		    return null;
		
		return Calculable.addAll(
			bs.getCalculable(DTOGCCBalanceSheet.Key.ANL),
			bs.getCalculable(DTOGCCBalanceSheet.Key.SVB),
			bs.getCalculable(DTOGCCBalanceSheet.Key.VBK),
			bs.getCalculable(DTOGCCBalanceSheet.Key.RSPV)
		);
	}
}
