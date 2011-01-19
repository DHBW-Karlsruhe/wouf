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