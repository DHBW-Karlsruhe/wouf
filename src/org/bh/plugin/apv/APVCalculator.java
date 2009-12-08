package org.bh.plugin.apv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bh.calculation.IShareholderValueCalculator;
import org.bh.calculation.sebi.AdjustedPresentValue;
import org.bh.calculation.sebi.Calculable;
import org.bh.calculation.sebi.Double;
import org.bh.data.DTOScenario;
import org.bh.data.IPeriodicalValuesDTO;
import org.bh.data.Value;

public class APVCalculator implements IShareholderValueCalculator {
	private static final String UNIQUE_ID = "apv";

	@Override
	public List<Value> calculate(DTOScenario scenario) {
		// note that this is just a hack to transform data from the new objects to the
		// existing calculation method
		Calculable[] fcf = new Calculable[scenario.getChildrenSize()];
		Calculable[] fk = new Calculable[scenario.getChildrenSize()];
		int i = 0;
		for (IPeriodicalValuesDTO periodValues : scenario.getChildren()) {
			fcf[i] = new Double(periodValues.get("fcf").getExpected().doubleValue());
			fk[i] = new Double(periodValues.get("fremdkapital").getExpected().doubleValue());
			i++;
		}
		HashMap<String, Object> input = new HashMap<String, Object>();
		input.put("FCF", fcf);
		input.put("FK", fk);
		input.put("EKr", new Double(scenario.get("rendite_ek").getExpected().doubleValue()));
		input.put("FKr", new Double(scenario.get("rendite_fk").getExpected().doubleValue()));
		input.put("sks", new Double(scenario.get("sks").getExpected().doubleValue()));
		input.put("sg", new Double(scenario.get("sg").getExpected().doubleValue()));
		
		AdjustedPresentValue apv = new AdjustedPresentValue(input);
		Calculable[] uws = apv.getUW();
		List<Value> result = new ArrayList<Value>();
		for (Calculable uw : uws) {
			result.add(new Value(((Double)uw).getValue()));
		}
		return result;
	}

	@Override
	public String getUniqueId() {
		return UNIQUE_ID;
	}
}
