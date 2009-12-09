package org.bh.plugin.apv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bh.calculation.IShareholderValueCalculator;
import org.bh.calculation.sebi.AdjustedPresentValue;
import org.bh.calculation.sebi.Calculable;
import org.bh.calculation.sebi.Double;
import org.bh.calculation.sebi.Value;
import org.bh.data.DTOScenario;
import org.bh.data.IDTO;
import org.bh.data.IPeriodicalValuesDTO;


public class APVCalculator implements IShareholderValueCalculator {
	private static final String UNIQUE_ID = "apv";

	@Override
	public List<Value> calculate(DTOScenario scenario) {
		// note that this is just a hack to transform data from the new objects to the
		// existing calculation method
		Calculable[] fcf = new Calculable[scenario.getChildrenSize()];
		Calculable[] fk = new Calculable[scenario.getChildrenSize()];
		int i = 0;
		for (IDTO periodValues : scenario.getChildren()) {
			fcf[i] = (Calculable) periodValues.get("fcf");
			fk[i] = (Calculable) periodValues.get("fremdkapital");
			i++;
		}
		HashMap<String, Object> input = new HashMap<String, Object>();
		input.put("FCF", fcf);
		input.put("FK", fk);
		input.put("EKr", scenario.get("rendite_ek"));
		input.put("FKr", scenario.get("rendite_fk"));
		input.put("sks", scenario.get("sks"));
		input.put("sg", scenario.get("sg"));
		
		AdjustedPresentValue apv = new AdjustedPresentValue(input);
		Calculable[] uws = apv.getUW();
		List<Value> result = new ArrayList<Value>();
		for (Calculable uw : uws) {
			result.add(uw);
		}
		return result;
	}

	@Override
	public String getUniqueId() {
		return UNIQUE_ID;
	}
}
