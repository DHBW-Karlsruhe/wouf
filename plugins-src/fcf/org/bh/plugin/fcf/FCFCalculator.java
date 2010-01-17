package org.bh.plugin.fcf;

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
 * the Free Cash Flow method. The formula for the enterprise value is </br>
 * UW[t] = (GK[t + 1] + FCF[t + 1] - FK[t] - (FKr * FK[t] * (1-s))) / (EKrFCF[t
 * + 1] + 1) </br> with:</br> <li>GK[t + 1] = UW[t + 1] + FK[t + 1]</li>
 * 
 * @author Sebastian
 * @author Norman
 * @version 1.0, 25.12.2009
 * @version 1.1, 06.01.2010
 */
public class FCFCalculator implements IShareholderValueCalculator {
	private static Logger log = Logger.getLogger(FCFCalculator.class);

	private static final String UNIQUE_ID = "fcf";
	private static final String GUI_KEY = "fcf";

	public enum Result {
		/**
		 * Present Value of the not debted enterprise. </br><b>Array</b> but
		 * only first value [0] interesting
		 */
		PRESENT_VALUE_TAX_SHIELD,
		/**
		 * Variable equity return rate </br><b>Array</b>
		 */
		EQUITY_RETURN_RATE_FCF,
		/**
		 * FK / UW </br><b>Array</b>
		 */
		DEBT_TO_EQUITY_RATIO,
		/**
		 * Weighted Average Cost of Equity </br><b>Array</b>
		 */
		WACC_EQUITY,
		/**
		 * Weighted Average Cost of Debts </br><b>Array</b>
		 */
		WACC_DEBTS,
		/**
		 * Weighted Average Cost of Capital = WACC_Equity + WACC_Debts
		 * </br><b>Array</b>
		 */
		WACC,
		/**
		 * Total Capital
		 */
		TOTAL_CAPITAL;

		@Override
		public String toString() {
			return getClass().getName() + "." + super.toString();
		}
	}

	@Override
	public Map<String, Calculable[]> calculate(DTOScenario scenario,
			boolean verboseLogging) {

		if (verboseLogging)
			log.info("----- FCF procedure started -----");

		Calculable[] fcf = new Calculable[scenario.getChildrenSize() + 1];
		Calculable[] fk = new Calculable[scenario.getChildrenSize() + 1];
		int i = 0;
		if (verboseLogging)
			log.debug("Input Values: FCF ; Liablities(FK):");
		for (DTOPeriod period : scenario.getChildren()) {
			if (i > 0)
				fcf[i] = period.getFCF();
			fk[i] = period.getLiabilities();
			if(i == fcf.length - 2){
				fcf[i + 1] = period.getFCF();
				fk[i + 1] = period.getLiabilities();
			}
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
		Calculable[] EKrFCF = new Calculable[fcf.length];
		Calculable[] FCFPresentValueTaxShield = new Calculable[fcf.length];
		Calculable ekr = (Calculable) scenario.get(DTOScenario.Key.REK);
		Calculable fkr = (Calculable) scenario.get(DTOScenario.Key.RFK);
		Calculable s = (DoubleValue) scenario.getTax();
		// Needed Helper Array
		Calculable[] uwFCFlastCalc = new Calculable[fcf.length];

		// ### Step 0
		// Load dynamic equity rate of return with initial equity rate of
		// return.
		// One value is needed to start
		for (int initialLoad = 0; initialLoad < EKrFCF.length; initialLoad++) {
			EKrFCF[initialLoad] = ekr;
		}

		// ### Step 1
		// Calculate enterprise value and dynamic equity rate of return for
		// endless period
		if (verboseLogging)
			log
					.debug(" Calculate enterprise value and dynamic equity rate of return for endless period");
		do {
			uwFCFlastCalc[T] = uw[T];
			// Calculate UW[T] = (FCF[T] - (FKr * FK[T] * (1 - s)))/ EKrFCF[T]
			uw[T] = (fcf[T].sub(fkr.mul(fk[T]).mul(
					s.mul(new DoubleValue(-1)).add(new DoubleValue(1)))))
					.div(EKrFCF[T]);

			// With this enterprise value the new dynamic equity rate of return
			EKrFCF[T] = calcVariableEquityReturnRateEndless(s, fkr, fk[T], ekr,
					uw[T]);
			if (verboseLogging && log.isDebugEnabled()) {
				log.debug("uw[T]: " + uw[T]);
				log.debug("EKrFCF[T]: " + EKrFCF[T]);
			}
			// re-calculate the enterprise value until the calculation doesn't
			// differs from the former one
		} while (uwFCFlastCalc[T] == null || checkAbs(uw[T], uwFCFlastCalc[T]));

		// ### Step 3
		// Calculation of PresentValueTaxShield[i] with i = 0,1,2,...,T-1
		FCFPresentValueTaxShield = calcPresentValueTaxShield(s, fkr, fk,
				FCFPresentValueTaxShield, verboseLogging);

		// ### Step 4
		// Calculation of enterprise value for finite periods
		if (verboseLogging)
			log.debug("Calculation of enterprise value for finite periods");
		int repetitions = 0;
		boolean isIdentical;
		do {
			// Put last values into array for old values.
			for (int j = 0; j < uw.length - 1; j++) {
				uwFCFlastCalc[j] = uw[j];
			}
			// Calculate UW[t] with t = 0,1,2,...,T-1
			// UW[t] = (GK[t + 1] + FCF[t + 1] - FK[t] - (FKr * FK[t] * (1-s)))
			// / (EKrFCF[t + 1] + 1)
			// with GK[t + 1] = UW[t + 1] + FK[t + 1]
			for (int t = uw.length - 2; t >= 0; t--) {
				uw[t] = (uw[t + 1].add(fk[t + 1]).add(fcf[t + 1]).sub(fk[t])
						.sub(fkr.mul(fk[t]).mul(
								s.mul(new DoubleValue(-1)).add(
										new DoubleValue(1)))))
						.div(EKrFCF[t + 1].add(new DoubleValue(1)));
				if (verboseLogging && log.isDebugEnabled()) {
					log.debug("UW[" + t + "]: " + uw[t]);
				}
			}

			// Calculate EKrFCF[t] with the new enterprise values
			EKrFCF = calcVariableEquityReturnRateFinite(s, fkr, fk, ekr, uw,
					EKrFCF, FCFPresentValueTaxShield);

			if (verboseLogging && log.isDebugEnabled()) {
				log.debug("EkrFCF: " + EKrFCF);
			}

			// re-calculate the enterprise value until the calculation doesn't
			// differs from the former one
			isIdentical = false;
			if (uwFCFlastCalc[0] == null || checkAbs(uw[0], uwFCFlastCalc[0])) {
				isIdentical = false;
			} else {
				isIdentical = true;
			}
			if (verboseLogging)
				log.debug("No. of repititions: " + repetitions);
			repetitions++;
			if (repetitions > 25000) {
				isIdentical = true;
			}
		} while (!isIdentical);

		Calculable[] waccEquity = calcWACCEquity(uw, fk, EKrFCF);
		Calculable[] waccDebts = calcWACCDebts(fk, fkr, uw, s);
		Calculable[] wacc = calcWACC(waccEquity, waccDebts);

		Map<String, Calculable[]> result = new HashMap<String, Calculable[]>();
		result.put(IShareholderValueCalculator.Result.SHAREHOLDER_VALUE
				.toString(), uw);
		result.put(IShareholderValueCalculator.Result.DEBT.toString(), fk);
		result.put(
				IShareholderValueCalculator.Result.FREE_CASH_FLOW.toString(),
				fcf);
		result.put(IShareholderValueCalculator.Result.TAXES.toString(),
				new Calculable[] { scenario.getTax() });
		result.put(IShareholderValueCalculator.Result.DEBT_RETURN_RATE
				.toString(), new Calculable[] { fkr });
		result.put(Result.PRESENT_VALUE_TAX_SHIELD.toString(),
				FCFPresentValueTaxShield);
		result.put(Result.EQUITY_RETURN_RATE_FCF.toString(), EKrFCF);
		result.put(Result.DEBT_TO_EQUITY_RATIO.toString(),
				calcDebtToEquityRatio(uw, fk));
		result.put(Result.WACC_EQUITY.toString(), waccEquity);
		result.put(Result.WACC_DEBTS.toString(), waccDebts);
		result.put(Result.WACC.toString(), wacc);

		Calculable[] gk = new Calculable[wacc.length];
		gk[gk.length - 1] = fcf[gk.length - 1].div(wacc[gk.length - 1]);
		for (int z = gk.length - 2; z >= 0; z--) {
			gk[z] = (gk[z + 1].add(fcf[z + 1])).div(new DoubleValue(1)
					.add(wacc[z + 1]));
		}
		result.put(Result.TOTAL_CAPITAL.toString(), gk);

		if (verboseLogging)
			log.info("----- FCF procedure finished -----");

		return result;
	}

	@Override
	public String getGuiKey() {
		return GUI_KEY;
	}

	@Override
	public String getUniqueId() {
		return UNIQUE_ID;
	}

	// Calculate PresentValueTaxShield for endless period
	// PresentValueTaxShield[T] = (s * FKr * FK[T]) / FKr
	private Calculable calcPresentValueTaxShieldEndless(Calculable s,
			Calculable FKr, Calculable FK, boolean verboseLogging) {
		if (verboseLogging && log.isDebugEnabled()) {
			log.debug("PVTSEndless (s * FKr * FK[T]) / FKr): "
					+ s.mul(FKr).mul(FK).div(FKr));
		}
		return s.mul(FKr).mul(FK).div(FKr);
	}

	// Calculate PresentValueTaxShield for finite period
	// PresentValueTaxShield[t] = (PresentValueTaxShield[t + 1] + (s * FKr *
	// FK[t])) / (FKr + 1)
	private Calculable[] calcPresentValueTaxShieldFinite(Calculable s,
			Calculable FKr, Calculable[] FK, Calculable[] PVTS, boolean verboseLogging) {
		for (int i = PVTS.length - 2; i >= 0; i--) {
			PVTS[i] = (PVTS[i + 1].add(s.mul(FKr).mul(FK[i + 1 - 1]))).div(FKr
					.add(new DoubleValue(1)));
			if (verboseLogging && log.isDebugEnabled()) {
				log
						.debug("PVTS["
								+ i
								+ "] (PresentValueTaxShield[t + 1] + (s * FKr * FK[t])) / (FKr + 1): "
								+ PVTS[i]);
			}
		}
		return PVTS;
	}

	/**
	 * Calculates the value of the indebted enterprise. Used in FCF and FTE
	 * method.
	 * 
	 * @param s
	 *            Taxes
	 * @param FKr
	 *            Debt rate of return
	 * @param FK
	 *            Debt
	 * @param PVTS
	 *            self reference
	 * @return The value of the indebted enterprise for each year
	 */
	private Calculable[] calcPresentValueTaxShield(Calculable s,
			Calculable FKr, Calculable[] FK, Calculable[] PVTS, boolean verboseLogging) {
		PVTS[PVTS.length - 1] = calcPresentValueTaxShieldEndless(s, FKr,
				FK[FK.length - 1], verboseLogging);
		return calcPresentValueTaxShieldFinite(s, FKr, FK, PVTS, verboseLogging);
	}

	/**
	 * Calculates the variable equity return rate for the endless period. Used
	 * in FCF and FTE method.</br> EKrFCF[T] = EKr + ((EKr - FKr) * (1-s) *
	 * (FK[T] / UW[T]))
	 * 
	 * @param s
	 *            Taxes
	 * @param FKr
	 *            Debt rate of return
	 * @param FK
	 *            Debt
	 * @param EKr
	 *            Equity rate of return
	 * @param UW
	 *            Enterprise value
	 * @return Variable equity return rate for the endless period
	 */
	private Calculable calcVariableEquityReturnRateEndless(Calculable s,
			Calculable FKr, Calculable FK, Calculable EKr, Calculable UW) {
		return EKr.add(((EKr.sub(FKr)).mul(s.mul(new DoubleValue(-1)).add(
				new DoubleValue(1))).mul(FK.div(UW))));
	}

	private Calculable[] calcDebtToEquityRatio(Calculable[] UW, Calculable[] FK) {
		Calculable[] DebtToEquityRatio = new Calculable[UW.length];
		for (int i = 0; i < UW.length; i++) {
			DebtToEquityRatio[i] = FK[i].div(UW[i]);
		}
		return DebtToEquityRatio;
	}

	/**
	 * Calculates the variable equity return rate for the finite periods. Used
	 * in FCF and FTE method.</br> EKrFCF[t] = EKr + ((EKr - FKr) * ((FK[t -1] -
	 * PVTS[t-1]) / UW[t - 1]))
	 * 
	 * @param s
	 *            Taxes
	 * @param FKr
	 *            Debt rate of return
	 * @param FK
	 *            Debt
	 * @param EKr
	 *            Equity rate of return
	 * @param UW
	 *            Enterprise value
	 * @param EKrV
	 *            self reference
	 * @param PresentValueTaxShield
	 *            Value of the indebted enterprise
	 * @return Variable equity return rate for the finite periods
	 */
	private Calculable[] calcVariableEquityReturnRateFinite(Calculable s,
			Calculable FKr, Calculable[] FK, Calculable EKr, Calculable UW[],
			Calculable[] EKrV, Calculable[] PresentValueTaxShield) {
		for (int t = 1; t < UW.length - 1; t++) {
			EKrV[t] = EKr.add(((EKr.sub(FKr)).mul((FK[t - 1]
					.sub(PresentValueTaxShield[t - 1])).div(UW[t - 1]))));
		}
		return EKrV;
	}

	/**
	 * This method checks whether the input parameters differ from each other
	 * more than 0.0000000001
	 * 
	 * @param firstValue
	 * @param secondValue
	 * @return <b>true</b> if input parameters differ more<br>
	 *         <b>false</b> if input parameters differ less
	 */
	private boolean checkAbs(Calculable firstValue, Calculable secondValue) {
		// if(firstValue. sub(secondValue).abs().greaterThan(new
		// DoubleValue(0.0000000001))){
		// return true;
		// }else{
		// return false;
		// }
		return !firstValue.diffToLess(secondValue, 0.0000000001);
	}

	/**
	 * Calculates the Weighted Average Cost of Equity for the WACC.
	 * WACCEquity[t] = EKrV[t] * ( UW[t-1] / ( UW[t-1] + FK[t-1]))
	 * 
	 * @param UW
	 *            Enterprise Value
	 * @param FK
	 *            Debt
	 * @param EKrV
	 *            Variable equity rate of return
	 * @return Equity for Weighted Average Cost of Capital
	 */
	private Calculable[] calcWACCEquity(Calculable[] UW, Calculable[] FK,
			Calculable[] EKrV) {
		Calculable[] waccEquity = new Calculable[UW.length];
		for (int i = 1; i < UW.length; i++) {
			waccEquity[i] = EKrV[i].mul((UW[i - 1].div((UW[i - 1]
					.add(FK[i - 1])))));
		}
		return waccEquity;
	}

	/**
	 * Calculates the Weighted Average Cost of Debt for the WACC. WACCDebt[t] =
	 * FKr * ((1-s) * (FK[t-1] / (UW[t-1] + FK[t-1])))
	 * 
	 * @param FK
	 *            Debt
	 * @param FKr
	 *            Debt return ratio
	 * @param UW
	 *            Enterprise value
	 * @param s
	 *            Taxes
	 * @return Debt for Weighted Average Cost of Capital
	 */
	private Calculable[] calcWACCDebts(Calculable[] FK, Calculable FKr,
			Calculable[] UW, Calculable s) {
		Calculable[] waccDebts = new Calculable[UW.length];
		for (int i = 1; i < UW.length; i++) {
			waccDebts[i] = FKr.mul((s.mul(new DoubleValue(-1))
					.add(new DoubleValue(1))).mul((FK[i - 1].div((UW[i - 1]
					.add(FK[i - 1]))))));
		}
		return waccDebts;
	}

	/**
	 * Calculates the Weighted Average Cost of Capital. WACC = WACCEquity +
	 * WACCDebt
	 * 
	 * @param waccEquity
	 * @param waccDebts
	 * @return Weighted Average Cost of Capital
	 */
	private Calculable[] calcWACC(Calculable[] waccEquity,
			Calculable[] waccDebts) {
		Calculable[] wacc = new Calculable[waccEquity.length];
		for (int i = 1; i < waccEquity.length; i++) {
			wacc[i] = waccEquity[i].add(waccDebts[i]);
		}
		return wacc;
	}
}
