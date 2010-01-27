package org.bh.gui.view;

import javax.swing.JPanel;

import org.bh.validation.BHValidityEngine;

public class BHScenarioView extends View {
	public BHScenarioView(JPanel viewPanel, BHValidityEngine validator) throws ViewException {
		super(viewPanel, validator);
	}
}
