/*******************************************************************************
 * Copyright 2010: Anna Aichinger, Damian Berle, Patrick Dahl, Lisa Engelmann, Patrick Groß, Irene Ihl, Timo Klein, Alena Lang, Miriam Leuthold, Lukas Maciolek, Patrick Maisel, Vito Masiello, Moritz Olf, Ruben Reichle, Alexander Rupp, Daniel Schäfer, Simon Waldraff, Matthias Wurdig, Andreas Wußler
 *
 * Copyright 2009: Manuel Bross, Simon Drees, Marco Hammel, Patrick Heinz, Marcel Hockenberger, Marcus Katzor, Edgar Kauz, Anton Kharitonov, Sarah Kuhn, Michael Löckelt, Heiko Metzger, Jacqueline Missikewitz, Marcel Mrose, Steffen Nees, Alexander Roth, Sebastian Scharfenberger, Carsten Scheunemann, Dave Schikora, Alexander Schmalzhaf, Florian Schultze, Klaus Thiele, Patrick Tietze, Robert Vollmer, Norman Weisenburger, Lars Zuckschwerdt
 *
 * Copyright 2008: Camil Bartetzko, Tobias Bierer, Lukas Bretschneider, Johannes Gilbert, Daniel Huser, Christopher Kurschat, Dominik Pfauntsch, Sandra Rath, Daniel Weber
 *
 * This program is free software: you can redistribute it and/or modify it un-der the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT-NESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package org.bh.validation;

import javax.swing.JTextField;

import org.bh.gui.IBHModelComponent;
import org.bh.gui.swing.comp.BHTextField;
import org.bh.platform.Services;

import com.jgoodies.validation.ValidationResult;

/**
 * This class contains validation rules to check a textfield's content being greater
 * than a value or against another textfield's value.
 * 
 * @author Robert Vollmer, Patrick Heinz
 * @version 1.0, 22.01.2010
 * 
 */
public class VRIsGreaterThan extends ValidationRule {
	/** Constant to check whether a value is greater than zero. */
	public static final VRIsGreaterThan GTZERO = new VRIsGreaterThan(0, false);
	/** Constant to check whether a value is greater than or equal to zero. */
	public static final VRIsGreaterThan GTEZERO = new VRIsGreaterThan(0, true);
	/** Constant to check whether a value is greater than or equal to two. */
	public static final VRIsGreaterThan GTETWO = new VRIsGreaterThan(2, true);
	
	private IBHModelComponent other = null;
	private double compareValue;
	private final boolean orEqual;

	public VRIsGreaterThan(double compareValue, boolean orEqual) {
		this.compareValue = compareValue;
		this.orEqual = orEqual;
	}
	
	public VRIsGreaterThan(IBHModelComponent other, boolean orEqual) {
		this.other = other;
		this.orEqual = orEqual;
	}

	public VRIsGreaterThan(double compareValue) {
		this(compareValue, true);
	}

	@Override
	public ValidationResult validate(IBHModelComponent comp) {
		ValidationResult validationResult = new ValidationResult();
		if (comp instanceof JTextField || comp instanceof BHTextField) {
			BHTextField tf_toValidate = (BHTextField) comp;
			BHTextField tf_other = (BHTextField) other;
			
			boolean success = false;
			double value = Services.stringToDouble(tf_toValidate.getText());
			if (other != null) {
				compareValue = Services.stringToDouble(tf_other.getText());
			}
			success = orEqual ? (value >= compareValue)
					: (value > compareValue);
			
			if (!success) {
				if (other != null) {
					validationResult.addError(translator.translate("EValueField") + " '"
							+ translator.translate(tf_toValidate.getKey()) + "' "
							+ translator.translate("EisGreater") + " '" + 
							translator.translate(other.getKey()) + "'.");
				}
				else { // (other == null)
					validationResult.addError(translator.translate("Efield") + " '"
							+ translator.translate(tf_toValidate.getKey()) + "' "
							+ translator.translate("EisGreaterValue") + " "
							+ compareValue + ".");
				}

			}
		}
		return validationResult;
	}
}
