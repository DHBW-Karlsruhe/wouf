package org.bh.validation;

import org.bh.gui.swing.IBHModelComponent;

import com.jgoodies.validation.ValidationResult;

public class VRIsBetween0and100 extends ValidationRule {
	public static final VRIsBetween0and100 INSTANCE = new VRIsBetween0and100();
	private static final VRIsLowerThan lowerThan100 = new VRIsLowerThan(100);

	private VRIsBetween0and100() {
	}

	@Override
	public ValidationResult validate(IBHModelComponent comp) {
		ValidationResult validationResult = new ValidationResult();
		if (VRIsGreaterThan.GTEZERO.validate(comp).hasMessages()
				|| lowerThan100.validate(comp).hasMessages()) {
			validationResult.addError(translator.translate("Efield")
					+ translator.translate(comp.getKey())
					+ translator.translate("EisBetween0and100"));
		}
		return validationResult;
	}
}
