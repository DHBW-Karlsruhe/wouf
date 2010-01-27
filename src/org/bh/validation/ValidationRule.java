package org.bh.validation;

import org.bh.gui.IBHModelComponent;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;

import com.jgoodies.validation.ValidationResult;

/**
 * Abstract class which contains the method validate for all subclasses.
 * 
 * @author Robert Vollmer
 * @version 1.0, 22.01.2010
 * 
 */
public abstract class ValidationRule {
	protected static ITranslator translator = Services.getTranslator();
	
	/**
	 * This method validates a certain IBHModelComponent according to a
	 * specific validation rule.
	 * 
	 * @param the component which has to be validated
	 * 
	 * @return ValidationResult (all errors or warnings a textfield has)
	 */
	public abstract ValidationResult validate(IBHModelComponent comp);
}
