package org.bh.plugin.apv;

import java.util.HashMap;
import java.util.Map;

import org.bh.calculation.IShareholderValueCalculator;
import org.bh.data.DTOPeriod;
import org.bh.data.DTOScenario;
import org.bh.data.types.Calculable;
import org.bh.data.types.DoubleValue;

/**
 * This class provides the functionality to calculate an enterprise value with the
 * Adjusted-Present-Value method. 
 * The formula for the enterprise value is </br>
 * UW[t] = presentValueFCF[t] + presentValueTaxShield[t] - FK[t] </br>
 * with:</br>
 * <li> PresentValueFCF[T] = FCF[T] / EKr </li>
 * <li> PresentValueTaxShield[T] = (s * FKr * FK[T]) / FKr </li>
 * <li> PresentValueFCF[t] = (FCF[t] + PresentValueFCF[t + 1]) / (EKr + 1) </li>
 * <li> PresentValueTaxShield[t] = (PresentValueTaxShield[t + 1] + (s * FKr * FK[t])) / (FKr + 1) </li>
 * @author Sebastian
 * @version 1.0, 25.12.2009
 */
public class APVCalculator implements IShareholderValueCalculator {
	private static final String UNIQUE_ID = "apv";
	private static final String GUI_KEY = "apv";
	
	public enum Result {
		/**
		 * The Shareholder Value Calculated with APV Method
		 * </br><b>Array</b> but only first value [0] interesting
		 */
		SHAREHOLDER_VALUE,
		/**
		 * Present Value of the not debted enterprise.
		 * </br><b>Array</b> but only first value [0] interesting
		 */
		PRESENT_VALUE_FCF,
		/**
		 * The advantage for the present value when having debts.
		 * </br><b>Array</b> but only first value [0] interesting
		 */
		PRESENT_VALUE_TAX_SHIELD;
	}
	
	public Map<String, Calculable[]> calculate(DTOScenario scenario) {
		Calculable[] fcf = new Calculable[scenario.getChildrenSize()];
		Calculable[] fk = new Calculable[scenario.getChildrenSize()];
		int i = 0;
		for (DTOPeriod period : scenario.getChildren()) {
			if (i > 0)
				fcf[i] = period.getFCF();
			fk[i] = period.getLiabilities();
			i++;
		}	
		// Get all needed input parameters for the calculation
		int T = fcf.length - 1;
		
		// Instantiate the result arrays
		Calculable[] uw = new Calculable[fcf.length];
		Calculable[] presentValueFCF = new Calculable[fcf.length];
		Calculable[] presentValueTaxShield = new Calculable[fcf.length];
		
		// Calculate PresentValueTaxShield
		presentValueTaxShield = calcPresentValueTaxShield(scenario.getTax(), (Calculable)scenario.get(DTOScenario.Key.RFK), fk, presentValueTaxShield);
		
		// Calculate results for endless period
		presentValueFCF[T] = calcPresentValueFCFun(fcf[T], (Calculable)scenario.get(DTOScenario.Key.REK));
		uw[T] = calcEnterpriseValue(presentValueFCF[T], presentValueTaxShield[T], fk[T]);
		
		// Calculate results for finite periods
		for(int t = T - 1; t >= 0; t--){
			presentValueFCF[t] = calcPresentValueFCFen(presentValueFCF[t + 1], fcf[t + 1], (Calculable)scenario.get(DTOScenario.Key.REK));
			uw[t] = calcEnterpriseValue(presentValueFCF[t], presentValueTaxShield[t], fk[t]);
		}
		Map<String, Calculable[]> result = new HashMap<String, Calculable[]>();
		result.put(SHAREHOLDER_VALUE, uw);
		result.put(Result.PRESENT_VALUE_FCF.name(), presentValueFCF);
		result.put(Result.PRESENT_VALUE_TAX_SHIELD.name(), presentValueTaxShield);
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
	// Calculate PresentValue for endless period
	// PresentValueFCF[T] = FCF[T] / EKr
	// BarwertFreeCashFlow = FreeCashFlow / Eigenkapitalrendite
	private Calculable calcPresentValueFCFun(Calculable FCF, Calculable EKr){
		return (Calculable) FCF.div(EKr);
	}
	
	// Calculate PresentValue for finite period
	// PresentValueFCF[t] = (FCF[t] + PresentValueFCF[t + 1]) / (EKr + 1)
	private Calculable calcPresentValueFCFen(Calculable presentValueFCF ,Calculable FCF, Calculable EKr){
		return (Calculable) (FCF.add(presentValueFCF)).div(EKr.add(new DoubleValue(1)));
	}
	
	// UW[t] = presentValueFCF[t] + presentValueTaxShield[t] - FK[t]
	// Unternehmenswert = WertUnverschuldetesUnternehmen + WertVerschuldetesUnternehmen - Fremdkapital
	private Calculable calcEnterpriseValue(Calculable presentValueFCF, Calculable presentValueTaxShield, Calculable FK){
		return (Calculable) presentValueFCF.add(presentValueTaxShield).sub(FK);
	}
	// Calculate PresentValueTaxShield for endless period
	// PresentValueTaxShield[T] = (s * FKr * FK[T]) / FKr
	private Calculable calcPresentValueTaxShieldEndless(Calculable s, Calculable FKr, Calculable FK){
		return s.mul(FKr).mul(FK).div(FKr);
	}
	
	// Calculate PresentValueTaxShield for finite period
	// PresentValueTaxShield[t] = (PresentValueTaxShield[t + 1] + (s * FKr * FK[t])) / (FKr + 1)
	private Calculable[] calcPresentValueTaxShieldFinite(Calculable s, Calculable FKr, Calculable[] FK, Calculable[] PVTS){
		for (int i = PVTS.length - 2; i >= 0; i--) {
			PVTS[i] = (PVTS[i + 1].add(s.mul(FKr).mul(FK[i + 1 - 1]))).div(FKr.add(new DoubleValue(1)));
		}
		return PVTS;
	}
	// Calculates the value of the indebted enterprise. Used in FCF and FTE method.
	private Calculable[] calcPresentValueTaxShield(Calculable s, Calculable FKr, Calculable[] FK, Calculable[] PVTS){
		PVTS[PVTS.length - 1] = calcPresentValueTaxShieldEndless(s, FKr, FK[FK.length - 1]);
		return calcPresentValueTaxShieldFinite(s, FKr, FK, PVTS);
	}
}
