package org.bh.validation;

import javax.swing.JTextField;

import org.bh.gui.swing.BHTextField;
import org.bh.gui.swing.IBHModelComponent;

import com.jgoodies.validation.ValidationResult;

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
			String valueString = tf_toValidate.getText().replace(',', '.');
			try {
				double value = Double.parseDouble(valueString);
				success = (value != compareValue);
			} catch (NumberFormatException nfe) {
			}
		} else {
			success = true;
		}

		if (!success)
			validationResult.addError(translator.translate("Efield") + " '"
					+ translator.translate(comp.getKey()) + "' "
					+ translator.translate("EisNotEqual") + " "
					// TODO check where " '" and "' " for compare value (other) have to be
					+ ((other != null) ? translator.translate(other.getKey())
							: compareValue));
		return validationResult;
	}
}
