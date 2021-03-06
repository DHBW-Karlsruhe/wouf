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
package org.bh.plugin.apv;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bh.calculation.IShareholderValueCalculator;
import org.bh.data.DTOPeriod;
import org.bh.data.DTOScenario;
import org.bh.data.types.Calculable;
import org.bh.data.types.DoubleValue;

/**
 * This class provides the functionality to calculate an enterprise value with
 * the Adjusted-Present-Value method. The formula for the enterprise value is
 * </br> UW[t] = presentValueFCF[t] + presentValueTaxShield[t] - FK[t] </br>
 * with:</br> <li>PresentValueFCF[T] = FCF[T] / EKr</li> <li>
 * PresentValueTaxShield[T] = (s * FKr * FK[T]) / FKr</li> <li>
 * PresentValueFCF[t] = (FCF[t] + PresentValueFCF[t + 1]) / (EKr + 1)</li> <li>
 * PresentValueTaxShield[t] = (PresentValueTaxShield[t + 1] + (s * FKr * FK[t]))
 * / (FKr + 1)</li>
 * 
 * @author Sebastian
 * @author Norman
 * 
 * @version 1.0, 25.12.2009
 * @version 1.1, 06.01.2010, added Logger
 */
public class APVCalculator implements IShareholderValueCalculator {
	private static final Logger log = Logger.getLogger(APVCalculator.class);

	private static final String UNIQUE_ID = "apv";
	private static final String GUI_KEY = "apv";

	public enum Result {
		/**
		 * Present Value of the not debted enterprise. </br><b>Array</b> but
		 * only first value [0] interesting
		 */
		PRESENT_VALUE_FCF,
		/**
		 * The advantage for the present value when having debts.
		 * </br><b>Array</b> but only first value [0] interesting
		 */
		PRESENT_VALUE_TAX_SHIELD;

		@Override
		public String toString() {
			return getClass().getName() + "." + super.toString();
		}
	}

	@Override
	public Map<String, Calculable[]> calculate(DTOScenario scenario,
			boolean verboseLogging) {

		if (verboseLogging)
			log.info("----- APV procedure started -----");

		Calculable[] fcf = new Calculable[scenario.getChildrenSize()];
		Calculable[] fk = new Calculable[scenario.getChildrenSize()];
		int i = 0;
		if (verboseLogging)
			log.debug("Input Values: FCF ; Liablities(FK):");
		for (DTOPeriod period : scenario.getChildren()) {
			if (i > 0)
				fcf[i] = period.getFCF();
			fk[i] = period.getLiabilities();
			if (verboseLogging && log.isDebugEnabled()) {
				log.debug("\t" + period.get(DTOPeriod.Key.NAME) + ": " + fcf[i]
						+ " ; " + fk[i]);
			}
			i++;
		}
		// Get all needed input parameters for the calculation
		int T = fcf.length - 1;

		// Instantiate the result arrays
		Calculable[] uw = new Calculable[fcf.length];
		Calculable[] presentValueFCF = new Calculable[fcf.length];
		Calculable[] presentValueTaxShield = new Calculable[fcf.length];

		// Calculate PresentValueTaxShield
		presentValueTaxShield = calcPresentValueTaxShield(scenario.getTax(),
				scenario.getCalculable(DTOScenario.Key.RFK), fk,
				presentValueTaxShield);

		// Calculate results for endless period
		presentValueFCF[T] = calcPresentValueFCFun(fcf[T],
				scenario.getCalculable(DTOScenario.Key.REK));
		uw[T] = calcEnterpriseValue(presentValueFCF[T],
				presentValueTaxShield[T], fk[T]);
		if (verboseLogging && log.isDebugEnabled()) {
			log
					.debug("PresentValue[T] (BarwertFreeCashFlow = FreeCashFlow / Eigenkapitalrendite: "
							+ presentValueFCF[T]);
			log
					.debug("UW[T] (Unternehmenswert = WertUnverschuldetesUnternehmen WertVerschuldetesUnternehmen - Fremdkapital): "
							+ uw[T]);
		}

		// Calculate results for finite periods
		for (int t = T - 1; t >= 0; t--) {
			presentValueFCF[t] = calcPresentValueFCFen(presentValueFCF[t + 1],
					fcf[t + 1], (Calculable) scenario.get(DTOScenario.Key.REK));
			uw[t] = calcEnterpriseValue(presentValueFCF[t],
					presentValueTaxShield[t], fk[t]);
			if (verboseLogging && log.isDebugEnabled()) {
				log
						.debug("PresentValue["
								+ t
								+ "] (BarwertFreeCashFlow = FreeCashFlow / Eigenkapitalrendite: "
								+ presentValueFCF[t]);

				log
						.debug("UW["
								+ t
								+ "] (Unternehmenswert = WertUnverschuldetesUnternehmen WertVerschuldetesUnternehmen - Fremdkapital): "
								+ uw[t]);
			}
		}
		
		//Prepare APV result:
		//Fremdkapital
		Map<String, Calculable[]> result = new HashMap<String, Calculable[]>();
		result.put(IShareholderValueCalculator.Result.DEBT.toString(), fk);
		//FCF
		result.put(
				IShareholderValueCalculator.Result.FREE_CASH_FLOW.toString(),
				fcf);
		//Shareholder Value
		result.put(IShareholderValueCalculator.Result.SHAREHOLDER_VALUE
				.toString(), uw);
		result.put(IShareholderValueCalculator.Result.EQUITY_RETURN_RATE
				.toString(), new Calculable[] { (Calculable) scenario
				.get(DTOScenario.Key.REK) });
		result.put(IShareholderValueCalculator.Result.TAXES.toString(),
				new Calculable[] { scenario.getTax() });
		result.put(IShareholderValueCalculator.Result.DEBT_RETURN_RATE
				.toString(), new Calculable[] { (Calculable) scenario
				.get(DTOScenario.Key.RFK) });
		result.put(Result.PRESENT_VALUE_FCF.toString(), presentValueFCF);
		result.put(Result.PRESENT_VALUE_TAX_SHIELD.toString(),
				presentValueTaxShield);

		if (verboseLogging)
			log.info("----- APV procedure finished -----");

		return result;
	}

	@Override
	public String getUniqueId() {
		return UNIQUE_ID;
	}

	@Override
	public String getGuiKey() {
		return GUI_KEY;
	}
	
	/**
	 * Calculate PresentValue for endless period
	 * PresentValueFCF[T] = FCF[T] / EKr
	 * BarwertFreeCashFlow = FreeCashFlow / Eigenkapitalrendite
	 * @param FCF
	 * @param EKr
	 * @return
	 */
	private Calculable calcPresentValueFCFun(Calculable FCF, Calculable EKr) {
		return FCF.div(EKr);
	}

	/**
	 * Calculate PresentValue for finite period
	 * PresentValueFCF[t] = (FCF[t] + PresentValueFCF[t + 1]) / (EKr + 1)
	 * @param presentValueFCF
	 * @param FCF
	 * @param EKr
	 * @return
	 */
	private Calculable calcPresentValueFCFen(Calculable presentValueFCF,
			Calculable FCF, Calculable EKr) {
		return (FCF.add(presentValueFCF)).div(EKr.add(new DoubleValue(1)));
	}

	/**
	 * UW[t] = presentValueFCF[t] + presentValueTaxShield[t] - FK[t]
	 * Unternehmenswert = WertUnverschuldetesUnternehmen +
	 * WertVerschuldetesUnternehmen - Fremdkapital
	 * @param presentValueFCF
	 * @param presentValueTaxShield
	 * @param FK
	 * @return
	 */
	private Calculable calcEnterpriseValue(Calculable presentValueFCF,
			Calculable presentValueTaxShield, Calculable FK) {
		return presentValueFCF.add(presentValueTaxShield).sub(FK);
	}

	/**
	 * Calculate PresentValueTaxShield for endless period
	 * PresentValueTaxShield[T] = (s * FKr * FK[T]) / FKr
	 * @param s
	 * @param FKr
	 * @param FK
	 * @return
	 */
	private Calculable calcPresentValueTaxShieldEndless(Calculable s,
			Calculable FKr, Calculable FK) {
		// if (log.isDebugEnabled()) {
		// log.debug("PVTSEndless (s * FKr * FK[T]) / FKr): "
		// + s.mul(FKr).mul(FK).div(FKr));
		// }
		return s.mul(FKr).mul(FK).div(FKr);
	}

	/**
	 * Calculate PresentValueTaxShield for finite period
	 * PresentValueTaxShield[t] = (PresentValueTaxShield[t + 1] + (s * FKr *
	 * FK[t])) / (FKr + 1)
	 * @param s
	 * @param FKr
	 * @param FK
	 * @param PVTS
	 * @return
	 */
	private Calculable[] calcPresentValueTaxShieldFinite(Calculable s,
			Calculable FKr, Calculable[] FK, Calculable[] PVTS) {
		for (int i = PVTS.length - 2; i >= 0; i--) {
			PVTS[i] = (PVTS[i + 1].add(s.mul(FKr).mul(FK[i + 1 - 1]))).div(FKr
					.add(new DoubleValue(1)));
			// if (log.isDebugEnabled()) {
			// log
			// .debug("PVTS["
			// + i
			// +
			// "] (PresentValueTaxShield[t + 1] + (s * FKr * FK[t])) / (FKr + 1): "
			// + PVTS[i]);
			// }
		}
		return PVTS;
	}

	/**
	 * Calculates the value of the indebted enterprise. Used in FCF and FTE
	 * method.
	 * @param s
	 * @param FKr
	 * @param FK
	 * @param PVTS
	 * @return
	 */
	private Calculable[] calcPresentValueTaxShield(Calculable s,
			Calculable FKr, Calculable[] FK, Calculable[] PVTS) {
		PVTS[PVTS.length - 1] = calcPresentValueTaxShieldEndless(s, FKr,
				FK[FK.length - 1]);
		return calcPresentValueTaxShieldFinite(s, FKr, FK, PVTS);
	}

	@Override
	public boolean isApplicableForStochastic() {
		return true;
	}
}
