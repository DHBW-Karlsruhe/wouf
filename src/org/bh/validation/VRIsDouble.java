package org.bh.validation;

import javax.swing.JTextField;

import org.bh.gui.swing.BHTextField;
import org.bh.gui.swing.IBHModelComponent;

import com.jgoodies.validation.ValidationResult;

public class VRIsDouble extends ValidationRule {
	public static final VRIsDouble INSTANCE = new VRIsDouble();
	
	private VRIsDouble() {
	}

	@Override
	public ValidationResult validate(IBHModelComponent comp) {
		ValidationResult validationResult = new ValidationResult();
		if (comp instanceof JTextField || comp instanceof BHTextField) {
			BHTextField tf_toValidate = (BHTextField) comp;
			String valueString = tf_toValidate.getText().replace(',', '.');
			try {
				Double.parseDouble(valueString);
			} catch (NumberFormatException nfe) {
				validationResult.addError(translator.translate("Efield")
						+ translator.translate(tf_toValidate.getKey())
						+ translator.translate("EisDouble"));
			}
		}
		return validationResult;
	}

}
