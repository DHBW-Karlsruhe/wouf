package org.bh.validation;

import java.util.Collection;

import org.bh.gui.IBHModelComponent;
import org.bh.gui.swing.comp.BHTextField;
import org.bh.gui.view.ViewException;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;

import com.jgoodies.validation.view.ValidationComponentUtils;

/**
 * This class registers all textfields of a certain panel, sets InputHints
 * and marks mandatory textfield as mandatory in the JGoodies
 * ValidationComponentUtils class. 
 * 
 * @author Patrick Heinz
 * @version 1.0, 22.01.2010
 * 
 */

public class ValidationMethods extends BHValidityEngine {

	ITranslator translator = Services.getTranslator();

	/**
	 * This method registers all components, checks for BHTextFields
	 * and sets InputHints and mandatory fields.
	 * 
	 * @param Collection of all components on a panel
	 */
	@Override
	public void registerComponents(Collection<IBHModelComponent> toValidate)
			throws ViewException {
		
		for (IBHModelComponent comp : toValidate) {
			if (comp instanceof BHTextField) {
				BHTextField tf_toValidate = (BHTextField) comp;

				// add some kind of tooltipp to textfield
				ValidationComponentUtils.setInputHint(tf_toValidate,
						tf_toValidate.getHint());

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