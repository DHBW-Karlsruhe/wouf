/*******************************************************************************
 * Copyright 2010: Anna Aichinger, Damian Berle, Patrick Dahl, Lisa Engelmann, Patrick Groß, Irene Ihl, Timo Klein, Alena Lang, Miriam Leuthold, Lukas Maciolek, Patrick Maisel, Vito Masiello, Moritz Olf, Ruben Reichle, Alexander Rupp, Daniel Schäfer, Simon Waldraff, Matthias Wurdig, Andreas Wußler
 *
 * Copyright 2009: Manuel Bross, Simon Drees, Marco Hammel, Patrick Heinz, Marcel Hockenberger, Marcus Katzor, Edgar Kauz, Anton Kharitonov, Sarah Kuhn, Michael Löckelt, Heiko Metzger, Jacqueline Missikewitz, Marcel Mrose, Steffen Nees, Alexander Roth, Sebastian Scharfenberger, Carsten Scheunemann, Dave Schikora, Alexander Schmalzhaf, Florian Schultze, Klaus Thiele, Patrick Tietze, Robert Vollmer, Norman Weisenburger, Lars Zuckschwerdt
 *
 * Copyright 2008: Camil Bartetzko, Tobias Bierer, Lukas Bretschneider, Johannes Gilbert, Daniel Huser, Christopher Kurschat, Dominik Pfauntsch, Sandra Rath, Daniel Weber
 *
 * This program is free software: you can redistribute it and/or modify it un-der the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT-NESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package org.bh.plugin.gcc.data;

import org.bh.calculation.ICalculationPreparer;
import org.bh.data.DTOPeriod;
import org.bh.data.IPeriodicalValuesDTO;
import org.bh.data.types.Calculable;
import org.bh.data.types.DoubleValue;
import org.bh.plugin.gcc.data.DTOGCCBalanceSheet.Key;

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
		
		IPeriodicalValuesDTO plsNow = period.getPeriodicalValuesDTO("gcc_pls_costofsales");
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
		
		ebit = ebit.add(plsNow.getCalculable(DTOGCCProfitLossStatementCostOfSales.Key.UE),
				plsNow.getCalculable(DTOGCCProfitLossStatementCostOfSales.Key.HK),
//				plsNow.getCalculable(DTOGCCProfitLossStatementCostOfSales.Key.VERTK),
//				plsNow.getCalculable(DTOGCCProfitLossStatementCostOfSales.Key.VERWK),
//				plsNow.getCalculable(DTOGCCProfitLossStatementCostOfSales.Key.SBA),
				plsNow.getCalculable(DTOGCCProfitLossStatementCostOfSales.Key.VVSBA),
				plsNow.getCalculable(DTOGCCProfitLossStatementCostOfSales.Key.SBE));
		
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
