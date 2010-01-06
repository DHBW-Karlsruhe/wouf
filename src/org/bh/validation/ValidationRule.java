package org.bh.validation;

import org.bh.gui.swing.IBHModelComponent;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;

import com.jgoodies.validation.ValidationResult;

public abstract class ValidationRule {
	protected static ITranslator translator = Services.getTranslator();
	public abstract ValidationResult validate(IBHModelComponent comp);
}
