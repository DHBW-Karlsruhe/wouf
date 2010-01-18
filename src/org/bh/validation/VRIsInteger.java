package org.bh.validation;

import javax.swing.JTextField;

import org.bh.gui.swing.BHTextField;
import org.bh.gui.swing.IBHModelComponent;
import org.bh.platform.Services;

import com.jgoodies.validation.ValidationResult;

public class VRIsInteger extends ValidationRule {
	public static final VRIsInteger INSTANCE = new VRIsInteger();
	
	private VRIsInteger() {
	}
	
	@Override
	public ValidationResult validate(IBHModelComponent comp) {
		ValidationResult validationResult = new ValidationResult();
		if (comp instanceof JTextField || comp instanceof BHTextField) {
			BHTextField tf_toValidate = (BHTextField) comp;
			Integer value = Services.stringToInt(tf_toValidate.getText());
			if (value == null) {
				validationResult.addError(translator.translate("Efield") + " '"
						+ translator.translate(tf_toValidate.getKey()) + "' "
						+ translator.translate("EisInteger"));
			}
		}
		return validationResult;
	}

}
