package org.bh.plugin.directinput.random;

import javax.swing.JPanel;

import org.bh.calculation.sebi.DoubleValue;
import org.bh.controller.IPeriodicalValuesController;
import org.bh.data.IPeriodicalValuesDTO;
import org.bh.plugin.directinput.DTODirectInput;


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
			dto = new DTODirectInput();

		dto.put(DTODirectInput.Key.LIABILITIES, new DoubleValue(Math.random() * 200 + 1000));
		dto.put(DTODirectInput.Key.FCF, new DoubleValue(Math.random() * 20 + 100));
			
		return dto;
	}
}
