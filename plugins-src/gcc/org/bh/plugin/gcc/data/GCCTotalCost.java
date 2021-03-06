/*******************************************************************************
 * Copyright 2011: Matthias Beste, Hannes Bischoff, Lisa Doerner, Victor Guettler, Markus Hattenbach, Tim Herzenstiel, Günter Hesse, Jochen Hülß, Daniel Krauth, Lukas Lochner, Mark Maltring, Sven Mayer, Benedikt Nees, Alexandre Pereira, Patrick Pfaff, Yannick Rödl, Denis Roster, Sebastian Schumacher, Norman Vogel, Simon Weber * : Anna Aichinger, Damian Berle, Patrick Dahl, Lisa Engelmann, Patrick Groß, Irene Ihl, Timo Klein, Alena Lang, Miriam Leuthold, Lukas Maciolek, Patrick Maisel, Vito Masiello, Moritz Olf, Ruben Reichle, Alexander Rupp, Daniel Schäfer, Simon Waldraff, Matthias Wurdig, Andreas Wußler
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
import org.bh.data.DTOScenario;
import org.bh.data.IPeriodicalValuesDTO;
import org.bh.data.types.Calculable;
import org.bh.data.types.DoubleValue;
import org.bh.plugin.gcc.data.DTOGCCBalanceSheet.Key;

public class GCCTotalCost implements ICalculationPreparer {

	@Override
	public Calculable getFCF(DTOPeriod period) {
		
		DTOScenario scenario = period.getScenario();
		
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
		
		
		// start with calculating corporate income
		Calculable ebit = new DoubleValue(0);
		
		// incomes
		ebit = ebit.add(plsNow.getCalculable(DTOGCCProfitLossStatementTotalCost.Key.UE),
				plsNow.getCalculable(DTOGCCProfitLossStatementTotalCost.Key.SBE),
				plsNow.getCalculable(DTOGCCProfitLossStatementTotalCost.Key.AAE));
		
		// expenses
		ebit = ebit.sub(plsNow.getCalculable(DTOGCCProfitLossStatementTotalCost.Key.MA),
				plsNow.getCalculable(DTOGCCProfitLossStatementTotalCost.Key.PA),
				plsNow.getCalculable(DTOGCCProfitLossStatementTotalCost.Key.ABSCH),
				plsNow.getCalculable(DTOGCCProfitLossStatementTotalCost.Key.SBA));
		
		// ebit minus interests
		Calculable ebt = new DoubleValue(0);
		ebt = ebt.add(ebit);
		ebt = ebt.sub(plsNow.getCalculable(DTOGCCProfitLossStatementTotalCost.Key.ZA));
		
		// calculate taxes
		Calculable ctax = scenario.getCalculable(DTOScenario.Key.CTAX);
		Calculable btax = scenario.getCalculable(DTOScenario.Key.BTAX);
		
		Calculable btax_value =  ebt.mul(btax);
		Calculable ctax_value = btax_value.mul(ctax);
		
		// add taxes to ebt - result will be annual net income
		Calculable annualNetIncome = new DoubleValue(0);
		
		annualNetIncome = annualNetIncome.add(ebt,ctax_value,btax_value);
		 
		// calculation of annualNetIncome completed
		// continue with operational cash flow
		
		Calculable operationalCF = new DoubleValue(0);
		
		// calculate balance sheet diff
		Calculable accruals = bsNow.getCalculable(DTOGCCBalanceSheet.Key.RS).sub(bsPrev.getCalculable(DTOGCCBalanceSheet.Key.RS));
		
		// calculate operational cash flow by adding annualNetIncome + diff balancesheet accruals + liability costs
		operationalCF = operationalCF.add(annualNetIncome, 
				plsNow.getCalculable(DTOGCCProfitLossStatementTotalCost.Key.ABSCH),
				accruals,
				plsNow.getCalculable(DTOGCCProfitLossStatementTotalCost.Key.ZA));
		
		// calculation of operational cash flow finished
		// continue with cash flow after investment operations
		Calculable afterInvestmentCF = new DoubleValue(0);
		
		// start with calculating av period before
		Calculable avBefore = new DoubleValue(0);
		avBefore = avBefore.add(bsPrev.getCalculable(Key.IVG).add(bsPrev.getCalculable(Key.SA)).add(bsPrev.getCalculable(Key.FA)));
		
		// subtract accruals
		avBefore = avBefore.sub(plsNow.getCalculable(DTOGCCProfitLossStatementTotalCost.Key.ABSCH));
		
		// calculate av period now
		Calculable avNow = new DoubleValue(0);
		avNow = bsNow.getCalculable(Key.IVG).add(bsNow.getCalculable(Key.SA)).add(bsNow.getCalculable(Key.FA));
		
		// calculate invests / deinvests in AV
		Calculable invDelta = new DoubleValue(0);
		invDelta = invDelta.add(avNow).sub(avBefore);
		
		// calculate uv diff
		Calculable uvDiff = new DoubleValue(0);
		uvDiff = uvDiff.add(bsNow.getCalculable(DTOGCCBalanceSheet.Key.VOR),
				bsNow.getCalculable(DTOGCCBalanceSheet.Key.FSVG),
				bsNow.getCalculable(DTOGCCBalanceSheet.Key.WP),
				bsNow.getCalculable(DTOGCCBalanceSheet.Key.KBGGKS));
		
		uvDiff = uvDiff.sub(bsPrev.getCalculable(DTOGCCBalanceSheet.Key.VOR),
				bsPrev.getCalculable(DTOGCCBalanceSheet.Key.FSVG),
				bsPrev.getCalculable(DTOGCCBalanceSheet.Key.WP),
				bsPrev.getCalculable(DTOGCCBalanceSheet.Key.KBGGKS));
		
		// sum up all results to afterInvestment Cash Flow
		afterInvestmentCF = afterInvestmentCF.add(invDelta,uvDiff);
		
		// finish after investment cash flow calculation
		// start calculating cash flow after financing operations
		Calculable afterFinancingCF = new DoubleValue(0);
		
		// difference of ek
		Calculable ekdif = new DoubleValue(0);
		ekdif = ekdif.add(bsNow.getCalculable(DTOGCCBalanceSheet.Key.EK)).sub(bsPrev.getCalculable(DTOGCCBalanceSheet.Key.EK));
		
		//difference of fk
		Calculable fkdif = new DoubleValue(0);
		fkdif = fkdif.add(bsNow.getCalculable(DTOGCCBalanceSheet.Key.VB),bsNow.getCalculable(DTOGCCBalanceSheet.Key.RS)).sub(bsPrev.getCalculable(DTOGCCBalanceSheet.Key.VB),bsPrev.getCalculable(DTOGCCBalanceSheet.Key.RS));
		
		afterFinancingCF = afterFinancingCF.add(ekdif,fkdif);
		
		// finish after financing operations cash flow calculation
		// start calculating final free cash flow
		Calculable fcf = new DoubleValue(0);
		
		fcf = fcf.add(operationalCF,afterInvestmentCF,afterFinancingCF);
		
		return fcf;
	}

	@Override
	public Calculable getLiabilities(DTOPeriod period) {
		IPeriodicalValuesDTO bs = period.getPeriodicalValuesDTO("gccbalancesheet");
		if (bs == null)
		    return null;
		
		return bs.getCalculable(DTOGCCBalanceSheet.Key.VB).add(bs.getCalculable(DTOGCCBalanceSheet.Key.RS));
	}
}
