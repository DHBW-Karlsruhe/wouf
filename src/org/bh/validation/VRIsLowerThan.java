package org.bh.validation;

import javax.swing.JTextField;

import org.bh.gui.swing.BHTextField;
import org.bh.gui.swing.IBHModelComponent;
import org.bh.platform.Services;

import com.jgoodies.validation.ValidationResult;

/**
 * This class contains validation rules to check a textfield's content being lower
 * than a value or against another textfield's value.
 * 
 * @author Robert Vollmer, Patrick Heinz
 * @version 1.0, 22.01.2010
 * 
 */
public class VRIsLowerThan extends ValidationRule {
	/** Constant to check whether a value is lower than zero. */
	public static final VRIsLowerThan LTZERO = new VRIsLowerThan(0, false);
	/** Constant to check whether a value is lower than or equal to zero. */
	public static final VRIsLowerThan LTEZERO = new VRIsLowerThan(0, true);
	
	private IBHModelComponent other = null;
	private double compareValue;
	private boolean orEqual;

	public VRIsLowerThan(double compareValue, boolean orEqual) {
		this.compareValue = compareValue;
		this.orEqual = orEqual;
	}

	public VRIsLowerThan(IBHModelComponent other, boolean orEqual) {
		this.other = other;
		this.orEqual = orEqual;
	}
	
	public VRIsLowerThan(double compareValue) {
		this(compareValue, true);
	}

	@Override
	public ValidationResult validate(IBHModelComponent comp) {
		ValidationResult validationResult = new ValidationResult();
		if (comp instanceof JTextField || comp instanceof BHTextField) {
			BHTextField tf_toValidate = (BHTextField) comp;
			BHTextField tf_other = (BHTextField) other;
			
			boolean success = false;
			double value = Services.stringToDouble(tf_toValidate.getText());
			if (other != null) {
				compareValue = Services.stringToDouble(tf_other.getText());
			}
			success = orEqual ? (value <= compareValue)
					: (value < compareValue);

			if (!success) {
				if (other != null) {
					validationResult.addError(translator.translate("EValueField") + " '"
							+ translator.translate(tf_toValidate.getKey()) + "' "
							+ translator.translate("EisLower") + " '" + 
							translator.translate(other.getKey()) + "'.");
				}
				else {
					validationResult.addError(translator.translate("Efield") + " '"
							+ translator.translate(tf_toValidate.getKey()) + "' "
							+ translator.translate("EisLowerValue") + " " + compareValue);
				}
			}
		}
		return validationResult;
	}

}
