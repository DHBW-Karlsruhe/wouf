package org.bh.validation;

import javax.swing.JTextField;

import org.bh.gui.swing.BHTextField;
import org.bh.gui.swing.IBHModelComponent;
import org.bh.platform.Services;

import com.jgoodies.validation.ValidationResult;

/**
 * This class contains validation rules to check a textfield's content to be doubles.
 * 
 * @author Patrick Heinz
 * @version 1.0, 22.01.2010
 * 
 */
public class VRIsDouble extends ValidationRule {
	public static final VRIsDouble INSTANCE = new VRIsDouble();
	
	private VRIsDouble() {
	}

	@Override
	public ValidationResult validate(IBHModelComponent comp) {
		ValidationResult validationResult = new ValidationResult();
		if (comp instanceof JTextField || comp instanceof BHTextField) {
			BHTextField tf_toValidate = (BHTextField) comp;
			double value = Services.stringToDouble(tf_toValidate.getText());
			if (Double.isNaN(value)) {
				validationResult.addError(translator.translate("Efield") + " '"
						+ translator.translate(tf_toValidate.getKey()) + "' "
						+ translator.translate("EisDouble"));
			}
		}
		return validationResult;
	}
}
