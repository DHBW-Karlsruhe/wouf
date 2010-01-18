package org.bh.calculation;

import java.util.HashMap;
import java.util.Map;

import org.bh.data.DTOScenario;
import org.bh.data.types.Calculable;
import org.bh.platform.Services;

/**
 * Shareholder value calculator which uses all other calculators and combines
 * their results.
 * 
 * @author Robert
 * @version 1.0, 16.01.2010
 * 
 */
public class AllDCFMethodsCalculator implements IShareholderValueCalculator {
	private static final String UNIQUE_ID = "all";
	private static final String GUI_KEY = "allDCF";

	@Override
	public Map<String, Calculable[]> calculate(DTOScenario scenario, boolean verboseLogging) {
		Map<String, Calculable[]> result = new HashMap<String, Calculable[]>();
		for (IShareholderValueCalculator calculator : Services.getDCFMethods()
				.values()) {
			if (calculator.getUniqueId().equals(UNIQUE_ID))
				continue;
			Map<String, Calculable[]> result2 = calculator.calculate(scenario, verboseLogging);
			if(result.containsKey("org.bh.calculation.IShareholderValueCalculator$Result.DEBT") && result.containsKey("org.bh.calculation.IShareholderValueCalculator$Result.FREE_CASH_FLOW")){
				Calculable[] debt = result.get("org.bh.calculation.IShareholderValueCalculator$Result.DEBT");
				Calculable[] fcf = result.get("org.bh.calculation.IShareholderValueCalculator$Result.FREE_CASH_FLOW");
				Calculable[] debt2 = result2.get("org.bh.calculation.IShareholderValueCalculator$Result.DEBT");
				Calculable[] fcf2 = result2.get("org.bh.calculation.IShareholderValueCalculator$Result.FREE_CASH_FLOW");
				result.putAll(result2);
				if(debt.length > debt2.length)
					result.put("org.bh.calculation.IShareholderValueCalculator$Result.DEBT", debt);
				if(fcf.length > fcf2.length)
					result.put("org.bh.calculation.IShareholderValueCalculator$Result.FREE_CASH_FLOW", fcf);
			}else
				result.putAll(result2);
		}
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

}
