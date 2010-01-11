package org.bh.validation;

import org.bh.gui.swing.IBHModelComponent;

import com.jgoodies.validation.ValidationResult;

public class VRBalanceIsAgreed extends ValidationRule {

	@Override
	public ValidationResult validate(IBHModelComponent comp) {
		ValidationResult validationResult = new ValidationResult();
		
		// TODO @Patrick H. implement rules (normal, min, max) and add to balance form
		
		return validationResult;
	}

}