package org.bh.plugin.directinput.random;

import org.apache.log4j.Logger;
import org.bh.platform.DTOAccessException;
import org.bh.platform.IPeriodicalValuesController;
import org.bh.platform.IPeriodicalValuesDTO;
import org.bh.platform.PeriodicalValuesDTOFactory;
import org.bh.platform.Value;

public class RandomDirectInputController implements IPeriodicalValuesController {
	@Override
	public IPeriodicalValuesDTO dummy() {
		IPeriodicalValuesDTO dto = PeriodicalValuesDTOFactory.getInstance().create("directinput");
		try {
			dto.put("fremdkapital", new Value(1200));
			dto.put("fcf", new Value(120));
		} catch (DTOAccessException e) {
			Logger.getRootLogger().error("", e);
		}
		return dto;
	}
}
