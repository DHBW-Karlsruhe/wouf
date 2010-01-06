package org.bh.validation;

import org.bh.gui.swing.IBHModelComponent;

import com.jgoodies.validation.ValidationResult;

public class VRIsNegative extends ValidationRule {
	public static final VRIsNegative INSTANCE = new VRIsNegative();
	
	private VRIsNegative() {
	}
	
	@Override
	public ValidationResult validate(IBHModelComponent comp) {
		ValidationResult validationResult = new ValidationResult();
		if (VRIsLowerThan.LTZERO.validate(comp).hasMessages()) {
			validationResult.addError(translator.translate("Efield")
					+ translator.translate(comp.getKey())
					+ translator.translate("EisNegative"));
		}
		return validationResult;
	}
}
