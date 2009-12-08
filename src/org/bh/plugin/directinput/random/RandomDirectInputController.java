package org.bh.plugin.directinput.random;

import javax.swing.JPanel;

import org.bh.controller.IPeriodicalValuesController;
import org.bh.data.IPeriodicalValuesDTO;
import org.bh.data.PeriodicalValuesDTOFactory;
import org.bh.data.Value;

public class RandomDirectInputController implements IPeriodicalValuesController {
	static {
		/*
		 * @TODO if this controller was a GUI controller, we would see a call
		 * to the platform's xyz method here, where it registers as handler for
		 * a specific DTO type. 
		 */
	}
	
	@Override
	public IPeriodicalValuesDTO editDTO(IPeriodicalValuesDTO dto, JPanel panel) {
		if (dto == null)
			dto = PeriodicalValuesDTOFactory.getInstance().create("directinput");

		dto.put("fremdkapital", new Value(Math.random() * 200 + 1000));
		dto.put("fcf", new Value(Math.random() * 20 + 100));
			
		return dto;
	}
}
