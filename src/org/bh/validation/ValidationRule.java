package org.bh.validation;

import org.bh.gui.swing.IBHModelComponent;
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
	public abstract ValidationResult validate(IBHModelComponent comp);
}
