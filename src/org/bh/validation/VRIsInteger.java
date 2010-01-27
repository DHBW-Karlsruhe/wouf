package org.bh.validation;

import javax.swing.JTextField;

import org.bh.gui.IBHModelComponent;
import org.bh.gui.swing.comp.BHTextField;
import org.bh.platform.Services;

import com.jgoodies.validation.ValidationResult;

/**
 * This class contains validation rules to check a textfield's content to be integers.
 * 
 * @author Robert Vollmer, Patrick Heinz
 * @version 1.0, 22.01.2010
 * 
 */
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
