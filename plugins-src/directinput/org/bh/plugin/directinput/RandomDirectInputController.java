package org.bh.plugin.directinput;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.bh.controller.IPeriodController;
import org.bh.data.DTOPeriod;
import org.bh.data.types.Calculable;
import org.bh.data.types.DoubleValue;

//TODO Javadoc fehlt!

public class RandomDirectInputController implements IPeriodController {
	private static final String GUI_KEY = "Random values";
	private static final int PRIORITY = 0;

	@Override
	public void editDTO(DTOPeriod period, JPanel panel) {
		while (period.getChildrenSize() > 0)
			period.removeChild(0);
		
		DTODirectInput dto = new DTODirectInput();
		Calculable liabilities = new DoubleValue(Math.random() * 200 + 1000);
		dto.put(DTODirectInput.Key.LIABILITIES, liabilities);
		Calculable fcf = new DoubleValue(Math.random() * 20 + 100);
		dto.put(DTODirectInput.Key.FCF, fcf);
		period.addChild(dto);
		
		panel.setLayout(new BorderLayout());
		panel.add(new JLabel("FCF = " + fcf + ", liabilities = " + liabilities));
		
		panel.getRootPane().validate();
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
		// not applicable
	}
}
