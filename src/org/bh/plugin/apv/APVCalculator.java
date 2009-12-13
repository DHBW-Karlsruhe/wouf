package org.bh.plugin.apv;

import java.util.HashMap;

import org.bh.calculation.IShareholderValueCalculator;
import org.bh.calculation.sebi.AdjustedPresentValue;
import org.bh.calculation.sebi.Calculable;
import org.bh.calculation.sebi.Value;
import org.bh.data.DTOPeriod;
import org.bh.data.DTOScenario;


public class APVCalculator implements IShareholderValueCalculator {
	private static final String UNIQUE_ID = "apv";

	@Override
	public Value calculate(DTOScenario scenario) {
		// note that this is just a hack to transform data from the new objects to the
		// existing calculation method
		Calculable[] fcf = new Calculable[scenario.getChildrenSize()];
		Calculable[] fk = new Calculable[scenario.getChildrenSize()];
		int i = 0;
		for (DTOPeriod period : scenario.getChildren()) {
			if (i > 0)
				fcf[i] =  period.getFCF();
			fk[i] = period.getLiabilities();
			i++;
		}
		HashMap<String, Object> input = new HashMap<String, Object>();
		input.put("FCF", fcf);
		input.put("FK", fk);
		input.put("EKr", scenario.get(DTOScenario.Key.REK));
		input.put("FKr", scenario.get(DTOScenario.Key.RFK));
		input.put("s", scenario.getTax());
		
		AdjustedPresentValue apv = new AdjustedPresentValue(input);
		
		return apv.getUW()[0];
	}

	@Override
	public String getUniqueId() {
		return UNIQUE_ID;
	}
}
