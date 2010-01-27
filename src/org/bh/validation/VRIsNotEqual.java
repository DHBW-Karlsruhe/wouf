package org.bh.validation;

import javax.swing.JTextField;

import org.bh.gui.IBHModelComponent;
import org.bh.gui.swing.comp.BHTextField;
import org.bh.platform.Services;

import com.jgoodies.validation.ValidationResult;

/**
 * This class contains validation rules to check a textfield's content not being
 * equal to a certain value or another textfield's content.
 * 
 * @author Robert Vollmer, Patrick Heinz
 * @version 1.0, 22.01.2010
 * 
 */
public class VRIsNotEqual extends ValidationRule {
	/** Constant to compare a value against zero. */
	public static final VRIsNotEqual ISNOTZERO = new VRIsNotEqual(0);
	private IBHModelComponent other = null;
	private double compareValue;

	public VRIsNotEqual(IBHModelComponent other) {
		this.other = other;
	}

	public VRIsNotEqual(double compareValue) {
		this.compareValue = compareValue;
	}

	@Override
	public ValidationResult validate(IBHModelComponent comp) {
		ValidationResult validationResult = new ValidationResult();
		boolean success = false;
		if (other != null) {
			success = comp.getValue().equals(other.getValue());
		} else if (comp instanceof JTextField || comp instanceof BHTextField) {
			BHTextField tf_toValidate = (BHTextField) comp;
			double value = Services.stringToDouble(tf_toValidate.getText());
			success = (value != compareValue);
		} else {
			success = true;
		}

		if (!success)
			validationResult.addError(translator.translate("Efield") + " '"
					+ translator.translate(comp.getKey()) + "' "
					+ translator.translate("EisNotEqual") + " "
					+ ((other != null) ? "'" + translator.translate(other.getKey() + "'.")
							: compareValue + "."));
		return validationResult;
	}
}
