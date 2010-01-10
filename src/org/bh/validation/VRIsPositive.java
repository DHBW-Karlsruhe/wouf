package org.bh.validation;

import org.bh.gui.swing.IBHModelComponent;

import com.jgoodies.validation.ValidationResult;

public class VRIsPositive extends ValidationRule {
	public static final VRIsPositive INSTANCE = new VRIsPositive();
	
	private VRIsPositive() {
	}

	@Override
	public ValidationResult validate(IBHModelComponent comp) {
		ValidationResult validationResult = new ValidationResult();
		if (VRIsGreaterThan.GTEZERO.validate(comp).hasMessages()) {
			validationResult.addError(translator.translate("Efield") + " '"
					+ translator.translate(comp.getKey()) + "' "
					+ translator.translate("EisPositive"));
		}
		return validationResult;
	}
}
