package org.bh.validation;

import java.util.List;

import org.bh.data.DTOKeyPair;
import org.bh.data.types.ObjectValue;
import org.bh.gui.swing.IBHModelComponent;

import com.jgoodies.validation.ValidationResult;

public class VRListNotEmpty extends ValidationRule {
	public static final VRListNotEmpty INSTANCE = new VRListNotEmpty();

	private VRListNotEmpty() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public ValidationResult validate(IBHModelComponent comp) {
		ValidationResult validationResult = new ValidationResult();
		try {
			List<DTOKeyPair> list = (List<DTOKeyPair>) ((ObjectValue) comp.getValue())
					.getObject();
			if (list.isEmpty()) {
				validationResult.addError(translator.translate("Efield") + " '"
						+ translator.translate(comp.getKey()) + "' "
						+ translator.translate("EisMandatory"));
			}
		} catch (Exception e) {
			validationResult.addError(translator.translate("Efield") + " '"
					+ translator.translate(comp.getKey()) + "' "
					+ translator.translate("Eunexpected"));
		}
		return validationResult;
	}

}