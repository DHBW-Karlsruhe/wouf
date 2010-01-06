package org.bh.gui;

import java.util.Map;

import org.bh.gui.swing.BHTextField;
import org.bh.gui.swing.IBHModelComponent;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;
import org.bh.validation.VRMandatory;
import org.bh.validation.ValidationRule;

import com.jgoodies.validation.view.ValidationComponentUtils;

/**
 * This class contains the validation rules for all platform panels
 * 
 * @author Patrick Heinz
 * @version 0.3, 30.12.2009
 * 
 */

public class ValidationMethods extends BHValidityEngine {

	ITranslator translator = Services.getTranslator();

	@Override
	public void registerComponents(Map<String, IBHModelComponent> toValidate)
			throws ViewException {
		
		for (Map.Entry<String, IBHModelComponent> entry : toValidate.entrySet()) {
			// TODO check why blue border disappears using if below
//			if (entry instanceof JTextField || entry instanceof BHTextField) {
				// TODO allow validation of other components?
				BHTextField tf_toValidate = (BHTextField) entry.getValue();

				// add some kind of tooltipp to textfield
				ValidationComponentUtils.setInputHint(tf_toValidate,
						tf_toValidate.getInputHint());

				// check if a textfield has the rule isMandatory
				for (ValidationRule validationRule : tf_toValidate.getValidationRules()) {
					if (validationRule instanceof VRMandatory) {
						// set textfield mandatory and highlight it with a blue border
						ValidationComponentUtils.setMandatory(tf_toValidate, true);
						ValidationComponentUtils.setMandatoryBorder(tf_toValidate);
						break;
					}
				}
//			}
		}
	}
}