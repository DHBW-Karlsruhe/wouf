package org.bh.gui;

import java.util.Iterator;
import java.util.Map;

import org.bh.gui.swing.BHTextField;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.view.ValidationComponentUtils;

public class ValidationMethods {
	
	public ValidationResult validateAll(Map<String, BHTextField> toValidate) {
		
		ValidationResult validationResult = new ValidationResult();
		
		int mapsize = toValidate.size();
		Iterator<?> iterator = toValidate.entrySet().iterator();
		
		for(int i=0; i < mapsize; i++) {
			
			Map.Entry entry = (Map.Entry)iterator.next();
			BHTextField tf = (BHTextField)entry.getValue();
			
			if(ValidationComponentUtils.isMandatory(tf)== true && (tf.getText()).equals("")) {
				// TODO Change errormessage to make it translation neutral
				validationResult.addError("The free cashflow is mandatory.");
				// TODO Test background behavior in case of error
				ValidationComponentUtils.setErrorBackground(tf);
			}
		}
		return validationResult;
	}
	
	public void validate(BHTextField toValidate) {
		
	}
}
