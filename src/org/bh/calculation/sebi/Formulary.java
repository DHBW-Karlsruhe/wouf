package org.bh.calculation.sebi;

import org.bh.data.types.Calculable;
import org.bh.data.types.DoubleValue;
import org.bh.data.types.Interval;


/**
 * This class provides static functionality which is used in all methods of enterprise value calculation.
 * It also provides methods to receive additional information. </br>
 * @author Sebastian
 * @version 1.0, 04.12.2009
 */
public class Formulary {
	
	// Calculate PresentValueTaxShield for endless period
	// PresentValueTaxShield[T] = (s * FKr * FK[T]) / FKr
	private static Calculable calcPresentValueTaxShieldEndless(Calculable s, Calculable FKr, Calculable FK){
		return s.mul(FKr).mul(FK).div(FKr);
	}
	
	// Calculate PresentValueTaxShield for finite period
	// PresentValueTaxShield[t] = (PresentValueTaxShield[t + 1] + (s * FKr * FK[t])) / (FKr + 1)
	private static Calculable[] calcPresentValueTaxShieldFinite(Calculable s, Calculable FKr, Calculable[] FK, Calculable[] PVTS){
		for (int i = PVTS.length - 2; i >= 0; i--) {
			PVTS[i] = (PVTS[i + 1].add(s.mul(FKr).mul(FK[i + 1 - 1]))).div(FKr.add(new DoubleValue(1)));
		}
		return PVTS;
	}
	/**
	 * Calculates the value of the indebted enterprise. Used in FCF and FTE method.
	 * @param s Taxes
	 * @param FKr Debt rate of return
	 * @param FK Debt
	 * @param PVTS self reference
	 * @return The value of the indebted enterprise for each year
	 */
	public static Calculable[] calcPresentValueTaxShield(Calculable s, Calculable FKr, Calculable[] FK, Calculable[] PVTS){
		PVTS[PVTS.length - 1] = calcPresentValueTaxShieldEndless(s, FKr, FK[FK.length - 1]);
		return calcPresentValueTaxShieldFinite(s, FKr, FK, PVTS);
	}
	/**
	 * Calculates the variable equity return rate for the endless period. Used in FCF and FTE method.</br>
	 * EKrFCF[T] = EKr + ((EKr - FKr) * (1-s) * (FK[T] / UW[T]))
	 * @param s Taxes
	 * @param FKr Debt rate of return  
	 * @param FK Debt
	 * @param EKr Equity rate of return
	 * @param UW Enterprise value
	 * @return Variable equity return rate for the endless period
	 */
	public static Calculable calcVariableEquityReturnRateEndless(Calculable s, Calculable FKr, Calculable FK, Calculable EKr, Calculable UW){
		return EKr.add(((EKr.sub(FKr)).mul(s.mul(new DoubleValue(-1)).add(new DoubleValue(1))).mul(FK.div(UW))));
	}
	/**
	 * Calculates the variable equity return rate for the finite periods. Used in FCF and FTE method.</br>
	 * EKrFCF[t] = EKr + ((EKr - FKr) * ((FK[t -1] - PVTS[t-1]) / UW[t - 1]))
	 * @param s Taxes
	 * @param FKr Debt rate of return
	 * @param FK Debt
	 * @param EKr Equity rate of return
	 * @param UW Enterprise value
	 * @param EKrV self reference
	 * @param PresentValueTaxShield Value of the indebted enterprise
	 * @return Variable equity return rate for the finite periods
	 */
	public static Calculable[] calcVariableEquityReturnRateFinite(Calculable s, Calculable FKr, Calculable[] FK, 
																		Calculable EKr, Calculable UW[], Calculable[] EKrV,
																		Calculable[] PresentValueTaxShield){
		for (int t = 1; t < UW.length - 1; t++) {
			EKrV[t] = EKr.add(((EKr.sub(FKr)).mul((FK[t - 1].sub(PresentValueTaxShield[t - 1])).div(UW[t - 1]))));
		}
		return EKrV;
	}
	/**
	 * Calculates the variable equity return rate. Used in FCF and FTE method.
	 * @param s Taxes
	 * @param FKr Debt rate of return
	 * @param FK Debt
	 * @param EKr Equity rate of return
	 * @param UW Enterprise value
	 * @param EKrV self reference
	 * @param PresentValueTaxShield Value of the indebted enterprise
	 * @return Variable equity return rate
	 */
	public static Calculable[] calcVariableEquityReturnRate(Calculable s, Calculable FKr, Calculable[] FK, 
																Calculable EKr, Calculable UW[], Calculable[] EKrV,
																Calculable[] PresentValueTaxShield){
		EKrV[EKrV.length - 1] = calcVariableEquityReturnRateEndless(s, FKr, FK[EKrV.length - 1], EKr, UW[EKrV.length - 1]);
		return calcVariableEquityReturnRateFinite(s, FKr, FK, EKr, UW, EKrV, PresentValueTaxShield);
	}
	
	
	/**
	 * This method checks whether the input parameters differ from each other more than
	 * 0.0000000001
	 * @param firstValue
	 * @param secondValue
	 * @return <b>true</b> if input parameters differ more<br>
	 * <b>false</b> if input parameters differ less
	 */
	public static boolean checkAbs(Calculable firstValue, Calculable secondValue){
		Interval uwI = (Interval) firstValue;
		Interval uwLastI = (Interval) secondValue;
		if(Math.abs(uwI.getMin() - uwLastI.getMin()) >= 0.0000000001){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * Calculates the Debt to Equity ratio for each period.</br>
	 * UW / FK
	 * @param UW Enterprise Value
	 * @param FK Debt
	 * @return Debt to Equity ratio for each period.
	 */
	public static Calculable[] calcDebtToEquityRatio(Calculable[] UW, Calculable[] FK){
		Calculable[] DebtToEquityRatio = new Calculable[UW.length];
		for (int i = 0; i < UW.length; i++) {			
			DebtToEquityRatio[i] = UW[i].div(FK[i]);
		}
		return DebtToEquityRatio; 
	}
	/**
	 * Calculates the Weighted Average Cost of Equity for the WACC.
	 * WACCEquity[t] = EKrV[t] * ( UW[t-1] / ( UW[t-1] + FK[t-1]))
	 * @param UW Enterprise Value
	 * @param FK Debt
	 * @param EKrV Variable equity rate of return
	 * @return Equity for Weighted Average Cost of Capital
	 */
	public static Calculable[] calcWACCEquity(Calculable[] UW, Calculable[] FK, Calculable[] EKrV){
		Calculable[] waccEquity = new Calculable[UW.length];
		for (int i = 1; i < UW.length; i++) {			
			waccEquity[i] = EKrV[i].mul((UW[i - 1].div((UW[i - 1].add(FK[i - 1])))));
		}
		return waccEquity; 
	}
	/**
	 * Calculates the Weighted Average Cost of Debt for the WACC.
	 * WACCDebt[t] = FKr * ((1-s) * (FK[t] / (UW[t] + FK[t-1])))
	 * @param FK Debt
	 * @param FKr Debt return ratio
	 * @param UW Enterprise value
	 * @param s Taxes
	 * @return Debt for Weighted Average Cost of Capital
	 */
	public static Calculable[] calcWACCDebts(Calculable[] FK, Calculable FKr, Calculable[] UW, Calculable s){
		Calculable[] waccDebts = new Calculable[UW.length];
		for (int i = 1; i < UW.length; i++) {			
			waccDebts[i] = FKr.mul((s.mul(new DoubleValue(-1)).add(new DoubleValue(1)))
					.mul((FK[i - 1].div((UW[i - 1].add(FK[i - 1]))))));
		}
		return waccDebts; 
	}
	/**
	 * Calculates the Weighted Average Cost of Capital.
	 * WACC = WACCEquity + WACCDebt
	 * @param waccEquity
	 * @param waccDebts
	 * @return Weighted Average Cost of Capital
	 */
	public static Calculable[] calcWACC(Calculable[] waccEquity, Calculable[] waccDebts){
		Calculable[] wacc = new Calculable[waccEquity.length];
		for (int i = 1; i < waccEquity.length; i++) {			
			wacc[i] = waccEquity[i].add(waccDebts[i]);
		}
		return wacc; 
	}
	/**
	 * Calculate the Free Cash Flow Tax Shield.
	 * @param FK Debt
	 * @param FKr Debt return rate
	 * @param s Tax
	 * @return Free Cash Flow Tax Shield
	 */
	public static Calculable[] calcFCFTaxShield(Calculable[] FK, Calculable FKr, Calculable s){
		Calculable[] FCFTaxShield = new Calculable[FK.length];
		for (int i = 1; i < FK.length; i++) {			
			FCFTaxShield[i] = (s.mul(FKr).mul(FK[i - 1]));
		}
		return FCFTaxShield; 
	}
}
