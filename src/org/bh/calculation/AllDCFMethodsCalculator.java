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
	public Map<String, Calculable[]> calculate(DTOScenario scenario) {
		Map<String, Calculable[]> result = new HashMap<String, Calculable[]>();
		for (IShareholderValueCalculator calculator : Services.getDCFMethods()
				.values()) {
			if (calculator.getUniqueId().equals(UNIQUE_ID))
				continue;

			result.putAll(calculator.calculate(scenario));
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
