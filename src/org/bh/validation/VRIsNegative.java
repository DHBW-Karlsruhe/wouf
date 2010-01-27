package org.bh.validation;

import org.bh.gui.IBHModelComponent;

import com.jgoodies.validation.ValidationResult;

/**
 * This class contains validation rules to check a textfield's content being negative.
 * 
 * @author Robert Vollmer, Patrick Heinz
 * @version 1.0, 22.01.2010
 * 
 */
public class VRIsNegative extends ValidationRule {
	public static final VRIsNegative INSTANCE = new VRIsNegative();
	
	private VRIsNegative() {
	}
	
	@Override
	public ValidationResult validate(IBHModelComponent comp) {
		ValidationResult validationResult = new ValidationResult();
		if (VRIsLowerThan.LTZERO.validate(comp).hasMessages()) {
			validationResult.addError(translator.translate("Efield") + " '"
					+ translator.translate(comp.getKey()) + "' "
					+ translator.translate("EisNegative"));
		}
		return validationResult;
	}
}
