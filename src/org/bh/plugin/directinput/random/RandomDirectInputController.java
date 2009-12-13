package org.bh.plugin.directinput.random;

import javax.swing.JPanel;

import org.bh.calculation.sebi.DoubleValue;
import org.bh.controller.IPeriodGUIController;
import org.bh.data.DTOPeriod;
import org.bh.plugin.directinput.DTODirectInput;


public class RandomDirectInputController implements IPeriodGUIController {
	private static final String GUI_KEY = "Random values";
	private static final int PRIORITY = 0;

	@Override
	public void editDTO(DTOPeriod period, JPanel panel) {
		while (period.getChildrenSize() > 0)
			period.removeChild(0);
		
		DTODirectInput dto = new DTODirectInput();
		dto.put(DTODirectInput.Key.LIABILITIES, new DoubleValue(Math.random() * 200 + 1000));
		dto.put(DTODirectInput.Key.FCF, new DoubleValue(Math.random() * 20 + 100));
		period.addChild(dto);
	}

	@Override
	public String getGuiKey() {
		return GUI_KEY;
	}

	@Override
	public int getGuiPriority() {
		return PRIORITY;
	}

	@Override
	public void stopEditing() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}
}
