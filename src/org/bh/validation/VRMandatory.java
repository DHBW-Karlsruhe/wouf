package org.bh.validation;

import javax.swing.JTextField;

import org.bh.gui.swing.BHTextField;
import org.bh.gui.swing.IBHModelComponent;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.view.ValidationComponentUtils;

public class VRMandatory extends ValidationRule {
	public static final VRMandatory INSTANCE = new VRMandatory();
	
	private VRMandatory() {
	}

	@Override
	public ValidationResult validate(IBHModelComponent comp) {
		ValidationResult validationResult = new ValidationResult();
		if (comp instanceof JTextField || comp instanceof BHTextField) {
			BHTextField tf_toValidate = (BHTextField) comp;
			if (ValidationComponentUtils.isMandatoryAndBlank(tf_toValidate)) {
				validationResult.addError(translator.translate("Efield") + " '"
						+ translator.translate(tf_toValidate.getKey()) + "' "
						+ translator.translate("EisMandatory"));
			}
		}
		return validationResult;
	}

}
