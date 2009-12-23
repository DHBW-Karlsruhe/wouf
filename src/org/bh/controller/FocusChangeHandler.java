package org.bh.controller;

import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.JLabel;

import com.jgoodies.validation.view.ValidationComponentUtils;

/**
 * The FocusChangeHandler is a part of the validation. Whenever the focus
 * changes to another textfield, a fitting InputHint is shown in the TooltippBar
 * 
 * @author Patrick Heinz
 * @version 0.1, 22.12.2009
 * 
 */

public class FocusChangeHandler implements PropertyChangeListener {

	// Label which has to show InputHints
	JLabel infoLabel;
	
	public FocusChangeHandler(JLabel infoLabel) {
		this.infoLabel = infoLabel;
	}

	public void propertyChange(PropertyChangeEvent pce) {
		String propertyName = pce.getPropertyName();
		if (!"permanentFocusOwner".equals(propertyName)) {
			return;
		}
		
		// Check which component is focused
		Component focusOwner = KeyboardFocusManager
				.getCurrentKeyboardFocusManager().getFocusOwner();
		
		// Returning the component's InputHint text
		String focusHint = (focusOwner instanceof JComponent) ? (String) ValidationComponentUtils
				.getInputHint((JComponent) focusOwner)
				: null;

		infoLabel.setText(focusHint);
		infoLabel.setVisible(focusHint != null);
	}
}