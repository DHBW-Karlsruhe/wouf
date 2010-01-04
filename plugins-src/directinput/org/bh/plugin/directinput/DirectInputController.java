package org.bh.plugin.directinput;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.bh.controller.IPeriodController;
import org.bh.data.DTOPeriod;
import org.bh.data.types.DoubleValue;


public class DirectInputController implements IPeriodController {
	private static final String GUI_KEY = "Direct Input";
	private static final int PRIORITY = 100;
	DTODirectInput dto;
	JTextField fcf;
	JTextField liabilities;
	boolean isInited = false;

	@Override
	public void editDTO(DTOPeriod period, JPanel panel) {
		while (period.getChildrenSize() > 0)
			period.removeChild(0);
		
		dto = new DTODirectInput();
		period.addChild(dto);
		
		panel.setLayout(new GridLayout(2,2));
		
		panel.add(new JLabel("FCF"));
		fcf = new JTextField("0");
		panel.add(fcf);
		
		panel.add(new JLabel("Liabilities"));
		liabilities = new JTextField("0");
		panel.add(liabilities);
		
		panel.getRootPane().validate();
		isInited = true;		
	}

	@Override
	public void stopEditing() {
		if (!isInited)
			return;
		
		dto.put(DTODirectInput.Key.FCF, new DoubleValue(Double.parseDouble(fcf.getText())));
		dto.put(DTODirectInput.Key.LIABILITIES, new DoubleValue(Double.parseDouble(liabilities.getText())));
		//dto = null;
	}
	
	@Override
	public String getGuiKey() {
		return GUI_KEY;
	}

	@Override
	public int getGuiPriority() {
		return PRIORITY;
	}
}
