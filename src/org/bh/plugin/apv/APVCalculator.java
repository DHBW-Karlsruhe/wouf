package org.bh.plugin.apv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bh.calculation.IShareholderValueCalculator;
import org.bh.calculation.sebi.AdjustedPresentValue;
import org.bh.calculation.sebi.Calculable;
import org.bh.calculation.sebi.Value;
import org.bh.data.DTOPeriod;
import org.bh.data.DTOScenario;
import org.bh.data.IDTO;


public class APVCalculator implements IShareholderValueCalculator {
	private static final String UNIQUE_ID = "apv";

	@Override
	public List<Value> calculate(DTOScenario scenario) {
		// note that this is just a hack to transform data from the new objects to the
		// existing calculation method
		Calculable[] fcf = new Calculable[scenario.getChildrenSize()];
		Calculable[] fk = new Calculable[scenario.getChildrenSize()];
		int i = 0;
		for (IDTO period : scenario.getChildren()) {
			if (i > 0)
				fcf[i] =  ((DTOPeriod) period).getFCF();
			fk[i] = ((DTOPeriod) period).getLiabilities();
			i++;
		}
		HashMap<String, Object> input = new HashMap<String, Object>();
		input.put("FCF", fcf);
		input.put("FK", fk);
		input.put("EKr", scenario.get(DTOScenario.Key.REK));
		input.put("FKr", scenario.get(DTOScenario.Key.RFK));
		input.put("sks", scenario.get(DTOScenario.Key.SKS));
		input.put("sg", scenario.get(DTOScenario.Key.SG));
		
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
