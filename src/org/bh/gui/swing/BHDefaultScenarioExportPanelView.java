package org.bh.gui.swing;

import javax.swing.JPanel;
import org.bh.gui.View;

import org.bh.gui.BHValidityEngine;
import org.bh.gui.ViewException;

public class BHDefaultScenarioExportPanelView extends View {
	public BHDefaultScenarioExportPanelView(JPanel viewPanel,
			BHValidityEngine validator) throws ViewException {
		super(viewPanel, validator);
	}
}
