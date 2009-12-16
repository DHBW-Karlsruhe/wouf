package org.bh.plugin.apv;

import java.util.HashMap;

import org.bh.calculation.IShareholderValueCalculator;
import org.bh.calculation.sebi.AdjustedPresentValue;
import org.bh.data.DTOPeriod;
import org.bh.data.DTOScenario;
import org.bh.data.types.Calculable;


public class APVCalculator implements IShareholderValueCalculator {
	private static final String UNIQUE_ID = "apv";
	private static final String GUI_KEY = "apv";
	private Calculable shareholderValue = null;

	@Override
	public void calculate(DTOScenario scenario) {
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
		shareholderValue = apv.getUW()[0];
	}
	
	@Override
	public Calculable getShareholderValue() {
		return shareholderValue;
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
