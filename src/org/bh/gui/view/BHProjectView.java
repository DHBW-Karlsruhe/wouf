package org.bh.gui.view;

import javax.swing.JPanel;

import org.bh.validation.ValidationMethods;

public class BHProjectView extends View {

	public BHProjectView(JPanel viewPanel) throws ViewException {
		super(viewPanel, new ValidationMethods());
	}

}
