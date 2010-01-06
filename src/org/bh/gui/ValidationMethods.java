package org.bh.gui;

import java.util.Collection;

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
	public void registerComponents(Collection<IBHModelComponent> toValidate)
			throws ViewException {
		
		for (IBHModelComponent comp : toValidate) {
			if (comp instanceof BHTextField) {
				// TODO allow validation of other components?
				BHTextField tf_toValidate = (BHTextField) comp;

				// add some kind of tooltipp to textfield
				ValidationComponentUtils.setInputHint(tf_toValidate,
						tf_toValidate.getInputHint());

				// check if a textfield has the rule isMandatory
				for (ValidationRule validationRule : tf_toValidate.getValidationRules()) {
					if (validationRule instanceof VRMandatory) {
						// set textfield mandatory
						ValidationComponentUtils.setMandatory(tf_toValidate, true);
						break;
					}
				}
			}
		}
	}
}