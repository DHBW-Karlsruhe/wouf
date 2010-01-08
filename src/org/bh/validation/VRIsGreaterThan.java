package org.bh.validation;

import javax.swing.JTextField;

import org.bh.gui.swing.BHTextField;
import org.bh.gui.swing.IBHModelComponent;

import com.jgoodies.validation.ValidationResult;

public class VRIsGreaterThan extends ValidationRule {
	/** Constant to check whether a value is greater than zero. */
	public static final VRIsGreaterThan GTZERO = new VRIsGreaterThan(0, false);
	/** Constant to check whether a value is greater than or equal to zero. */
	public static final VRIsGreaterThan GTEZERO = new VRIsGreaterThan(0, true);
	
	
	private final double compareValue;
	private final boolean orEqual;

	public VRIsGreaterThan(double compareValue, boolean orEqual) {
		this.compareValue = compareValue;
		this.orEqual = orEqual;
	}

	public VRIsGreaterThan(double compareValue) {
		this(compareValue, true);
	}

	@Override
	public ValidationResult validate(IBHModelComponent comp) {
		ValidationResult validationResult = new ValidationResult();
		if (comp instanceof JTextField || comp instanceof BHTextField) {
			BHTextField tf_toValidate = (BHTextField) comp;
			String valueString = tf_toValidate.getText().replace(',', '.');
			boolean success = false;
			try {
				double value = Double.parseDouble(valueString);
				success = orEqual ? (value >= compareValue)
						: (value > compareValue);

			} catch (NumberFormatException nfe) {
			}
			if (!success) {
				validationResult.addError(translator.translate("Efield")
						+ translator.translate(tf_toValidate.getKey())
						+ translator.translate("EisGreater") + compareValue);
			}
		}
		return validationResult;
	}

}
