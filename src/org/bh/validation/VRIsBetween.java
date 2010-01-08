package org.bh.validation;

import org.bh.gui.swing.IBHModelComponent;

import com.jgoodies.validation.ValidationResult;

public class VRIsBetween extends ValidationRule {
	public static final VRIsBetween BETWEEN0AND100 = new VRIsBetween(0, 100);
	public static final VRIsBetween BETWEEN1900AND2100 = new VRIsBetween(1900, 2100);
	
	private VRIsGreaterThan vrIsGreaterThan;
	private VRIsLowerThan vrIsLowerThan;
	
	
	public VRIsBetween(double lowerBound, double upperBound) {
		vrIsGreaterThan = new VRIsGreaterThan(lowerBound);
		vrIsLowerThan = new VRIsLowerThan(upperBound);
	}
	
	@Override
	public ValidationResult validate(IBHModelComponent comp) {
		ValidationResult validationResult = new ValidationResult();
		if ( vrIsGreaterThan.validate(comp).hasMessages()
				|| vrIsLowerThan.validate(comp).hasMessages()) {
			// TODO Error Messages for all values
			validationResult.addError(translator.translate("Efield")
					+ translator.translate(comp.getKey())
					+ translator.translate("EisBetween0and100"));
		}
		return validationResult;
	}
}
