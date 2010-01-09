package org.bh.gui.swing;

import javax.swing.JPanel;

import org.bh.gui.BHValidityEngine;
import org.bh.gui.View;
import org.bh.gui.ViewException;

public class BHScenarioView extends View {
	public BHScenarioView(JPanel viewPanel, BHValidityEngine validator) throws ViewException {
		super(viewPanel, validator);
	}
}
