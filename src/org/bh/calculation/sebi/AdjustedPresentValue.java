package org.bh.calculation.sebi;

import java.util.HashMap;

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
 * @version 1.0, 04.12.2009
 */
public class AdjustedPresentValue {
	
	private Calculable[] UW;
	private Calculable[] presentValueFCF;
	private Calculable[] presentValueTaxShield;
	private Tax s;
	
	/**
	 * This method creates an Object which calculates the enterprise value according to
	 * the Adjusted-Present-Value method.
	 * The result can be accessed via the get Methods
	 * @param input HashMap<String, Object> containing the needed input with String 
	 * as primary key containing the name of the value 
	 * e.g. "EK" as equity and value as Object (Calculable or Calculable[])  
	 */
	public AdjustedPresentValue(HashMap<String, Object> input){
		
		// Get all needed input parameters for the calculation
		int T = ((Calculable[])input.get("FCF")).length - 1;
		Calculable[] FCF = (Calculable[])input.get("FCF");
		Calculable[] FK = (Calculable[])input.get("FK");
		Calculable EKr = (Calculable)input.get("EKr");
		Calculable FKr = (Calculable)input.get("FKr");
		s = (Tax)input.get("s");
		
		// Instantiate the result arrays
		UW = new Calculable[FCF.length];
		presentValueFCF = new Calculable[FCF.length];
		presentValueTaxShield = new Calculable[FCF.length];
		
		// Calculate PresentValueTaxShield
		presentValueTaxShield = Formulary.calcPresentValueTaxShield(s, FKr, FK, presentValueTaxShield);
		
		// Calculate results for endless period
		presentValueFCF[T] = calcPresentValueFCFun(FCF[T], EKr);
		UW[T] = calcEnterpriseValue(presentValueFCF[T], presentValueTaxShield[T], FK[T]);
		
		// Calculate results for finite periods
		for(int t = T - 1; t >= 0; t--){
			presentValueFCF[t] = calcPresentValueFCFen(presentValueFCF[t + 1], FCF[t + 1], EKr);
			UW[t] = calcEnterpriseValue(presentValueFCF[t], presentValueTaxShield[t], FK[t]);
		}
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
	
	/**
	 * 
	 * @return <b>UW: </b> ENterprise value</br>
	 */
	public Calculable[] getUW() {
		return UW;
	}
	/**
	 * 
	 * @return <b>presentValueFCF: </b> Not indebted enterprise Value</br>
	 */
	public Calculable[] getPresentValueFCF() {
		return presentValueFCF;
	}
	/**
	 * 
	 * @return <b>presentValueTaxShield: </b> Indebted enterprise Value</br>
	 */
	public Calculable[] getPresentValueTaxShield() {
		return presentValueTaxShield;
	}	
}
