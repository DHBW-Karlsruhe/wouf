package org.bh.validation;

import org.bh.gui.IBHModelComponent;

import com.jgoodies.validation.ValidationResult;

/**
 * This class contains validation rules to check a textfield's content being positive.
 * 
 * @author Robert Vollmer, Patrick Heinz
 * @version 1.0, 22.01.2010
 * 
 */
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
