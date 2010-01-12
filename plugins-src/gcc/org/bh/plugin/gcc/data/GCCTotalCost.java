package org.bh.plugin.gcc.data;

import org.bh.calculation.ICalculationPreparer;
import org.bh.data.DTOPeriod;
import org.bh.data.IPeriodicalValuesDTO;
import org.bh.data.types.Calculable;
import org.bh.data.types.DoubleValue;
import org.bh.plugin.gcc.data.DTOGCCBalanceSheet.Key;

public class GCCTotalCost implements ICalculationPreparer {

	@Override
	public Calculable getFCF(DTOPeriod period) {
		// Check whether this method is applicable for the DTOs in this and the previous period.
		if (period.getPrevious() == null)
			return null;
		
		IPeriodicalValuesDTO plsNow = period.getPeriodicalValuesDTO("gcc_pls_totalcost");
		if (plsNow == null) {
			return null;
		}
		IPeriodicalValuesDTO bsNow = period.getPeriodicalValuesDTO("gccbalancesheet");
		if (bsNow == null)
		    return null;
		IPeriodicalValuesDTO bsPrev = period.getPrevious().getPeriodicalValuesDTO("gccbalancesheet");
		if (bsPrev == null)
			return null;
		
		Calculable ebit = new DoubleValue(0);
		
		ebit.add(plsNow.getCalculable(DTOGCCProfitLossStatementTotalCost.Key.UE),
				plsNow.getCalculable(DTOGCCProfitLossStatementTotalCost.Key.ABSCH),
				plsNow.getCalculable(DTOGCCProfitLossStatementTotalCost.Key.SBA),
				plsNow.getCalculable(DTOGCCProfitLossStatementTotalCost.Key.AUERG));
		
		Calculable bsCorrection = 
				bsNow.getCalculable(DTOGCCBalanceSheet.Key.RS).sub(
						bsPrev.getCalculable(DTOGCCBalanceSheet.Key.RS)).add(
								
//				bsNow.getCalculable(DTOGCCBalanceSheet.Key.AV).sub(
//						bsPrev.getCalculable(DTOGCCBalanceSheet.Key.AV)),
						
				(bsNow.getCalculable(Key.IVG).add(bsNow.getCalculable(Key.SA)).add(bsNow.getCalculable(Key.FA)))
						.sub(bsPrev.getCalculable(Key.IVG).add(bsPrev.getCalculable(Key.SA)).add(bsPrev.getCalculable(Key.FA))),
						
				bsNow.getCalculable(DTOGCCBalanceSheet.Key.VOR).sub(
						bsPrev.getCalculable(DTOGCCBalanceSheet.Key.VOR)),
				
				bsNow.getCalculable(DTOGCCBalanceSheet.Key.FSVG).sub(
						bsPrev.getCalculable(DTOGCCBalanceSheet.Key.FSVG)),
						
				bsNow.getCalculable(DTOGCCBalanceSheet.Key.VB).sub(
						bsPrev.getCalculable(DTOGCCBalanceSheet.Key.VB))
				);
		
		//Cash Flow aus laufender Geschäftstätigkeit
		ebit = ebit.add(bsCorrection);
		
		//Cash Flow aus der Investitionstätigkeit
		Calculable cfInvest = 
			bsNow.getCalculable(DTOGCCBalanceSheet.Key.SA).sub(
					bsPrev.getCalculable(DTOGCCBalanceSheet.Key.SA)).add(
							
			bsNow.getCalculable(DTOGCCBalanceSheet.Key.IVG).sub(
					bsPrev.getCalculable(DTOGCCBalanceSheet.Key.IVG)),
					
			bsNow.getCalculable(DTOGCCBalanceSheet.Key.FA).sub(
					bsPrev.getCalculable(DTOGCCBalanceSheet.Key.FA)),
			
			bsNow.getCalculable(DTOGCCBalanceSheet.Key.WP).sub(
					bsPrev.getCalculable(DTOGCCBalanceSheet.Key.WP)),
					
			bsNow.getCalculable(DTOGCCBalanceSheet.Key.KBGGKS).sub(
					bsPrev.getCalculable(DTOGCCBalanceSheet.Key.KBGGKS))
			);
		
		return ebit.sub(cfInvest);
	}

	@Override
	public Calculable getLiabilities(DTOPeriod period) {
		IPeriodicalValuesDTO bs = period.getPeriodicalValuesDTO("gccbalancesheet");
		if (bs == null)
		    return null;
		
		return bs.getCalculable(DTOGCCBalanceSheet.Key.VB).add(bs.getCalculable(DTOGCCBalanceSheet.Key.RS));
	}
}
