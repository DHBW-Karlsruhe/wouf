package org.bh.gui;

import java.util.Iterator;
import java.util.Map;

import org.bh.gui.swing.BHTextField;
import org.bh.gui.swing.IBHComponent;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.view.ValidationComponentUtils;

/**
 * This class contains the validation rules for a specific form panel 
 * TODO change classname xyFormValidation. Same in comment above.
 * 
 * @author Patrick Heinz
 * @version 0.2, 22.12.2009
 * 
 */

public class ValidationMethods extends BHValidityEngine {

	ITranslator translator = Services.getTranslator();
	
	public static final int isMandatory = 1;
	public static final int isDouble = 2;
	public static final int isInteger = 3;
	public static final int isPositive = 4;
	public static final int isNegative = 5;
	public static final int isNotZero = 6;
	public static final int isBetween0and100 = 7;
	

	@Override
	public ValidationResult validate(IBHComponent comp) {

		BHTextField toValidate = (BHTextField) comp;

		ValidationResult validationResult = new ValidationResult();

		// TODO implement the rest of the method similar to validateAll()

		return validationResult;

	}

	@Override
	ValidationResult validateAll(Map<String, IBHComponent> toValidate) {
		
		ValidationResult validationResult = new ValidationResult();
		
		int mapsize = toValidate.size();
		Iterator<?> iterator = toValidate.entrySet().iterator();
		
		for(int i=0; i < mapsize; i++) {
			
			Map.Entry entry = (Map.Entry)iterator.next();
			BHTextField textField = (BHTextField)entry.getValue();
			int[] validateRules = textField.getValidateRules();
			
			// TODO for schleife mit validateRules[0]..
			switch (i) {
				
				// TODO Change errormessage to make it translation neutral
				// TODO Test background behavior in case of error
			
				case isMandatory:
					// TODO implement validation rules and search for other checks
					ValidationComponentUtils.setErrorBackground(textField);
					break;
				
				case isDouble:
					// TODO implement validation rules and search for other checks
					ValidationComponentUtils.setErrorBackground(textField);
					break;
					
				case isInteger:
					// TODO implement validation rules and search for other checks
					ValidationComponentUtils.setErrorBackground(textField);
					break;
				
				case isPositive:
					// TODO implement validation rules and search for other checks
					ValidationComponentUtils.setErrorBackground(textField);
					break;
				
				case isNegative:
					// TODO implement validation rules and search for other checks
					ValidationComponentUtils.setErrorBackground(textField);
					break;
				
				case isNotZero:
					// TODO implement validation rules and search for other checks
					ValidationComponentUtils.setErrorBackground(textField);
					break;
				
				case isBetween0and100:
					// TODO implement validation rules and search for other checks
					ValidationComponentUtils.setErrorBackground(textField);
					break;
				
				default:
					// TODO implement default rules
			}
			
//			if(ValidationComponentUtils.isMandatory(tf)== true && (tf.getText()).equals("")) {
//				// TODO Change errormessage to make it translation neutral
//				validationResult.addError("The free cashflow is mandatory.");
//				// TODO Test background behavior in case of error
//				ValidationComponentUtils.setErrorBackground(tf);
//			}
		}
		return validationResult;

	}
}