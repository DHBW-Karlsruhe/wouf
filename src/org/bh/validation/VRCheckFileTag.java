package org.bh.validation;

import javax.swing.JTextField;

import org.bh.gui.swing.BHTextField;
import org.bh.gui.swing.IBHModelComponent;

import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.view.ValidationComponentUtils;

public class VRCheckFileTag extends ValidationRule {
	
	public static final VRCheckFileTag PDF = new VRCheckFileTag("pdf");
	
	String fileTagToCheck;
	
	public VRCheckFileTag(String fileTagToCheck) {
		this.fileTagToCheck = fileTagToCheck;
	}

	@Override
	public ValidationResult validate(IBHModelComponent comp) {
		ValidationResult validationResult = new ValidationResult();
		if (comp instanceof JTextField || comp instanceof BHTextField) {
			BHTextField tf_toValidate = (BHTextField) comp;
			String fileName = tf_toValidate.getText();
			
			String[] substrings = fileName.split("\\.");
			String fileTag = substrings[substrings.length-1];

			if((!(fileTag.equals(fileTagToCheck))) || fileName.endsWith(".")) {
				validationResult.addError(translator.translate("EfileTag") + " " + fileTagToCheck);
			}
		}
		return validationResult;
	}
}
