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
package org.bh.gui;

import org.bh.data.types.IValue;
import org.bh.validation.BHValidityEngine;
import org.bh.validation.ValidationRule;


/**
 * Interface for components for displaying and optionally editing values.
 *
 * @author Robert
 * @version 1.0, 05.01.2010
 *
 */
public interface IBHModelComponent extends IBHComponent {
	/**
	 * Should be implemented in components which comtaining a model relevant
	 * value
	 * 
	 * @return relevant value
	 */
	public IValue getValue();

	/**
	 * Set the value of the component.
	 * 
	 * @param value
	 *            The value to be set.
	 */
	public void setValue(IValue value);
	
	/**
	 * Number of rules and the rules itself are platform independent but shall
	 * be consistent in every plugin by using one Validity Engine per plugin.
	 * 
	 * @return amount of rules defined in a subclass of BHValidity engine
	 * @see BHValidityEngine
	 */
	public ValidationRule[] getValidationRules();

	/**
	 * can set the Rules for the validation by runtime;
	 * 
	 * @param validationRules
	 */
	public void setValidationRules(ValidationRule[] validationRules);
	
	/**
	 * Returns the object which manages the value change listeners.
	 * @return
	 */
	public CompValueChangeManager getValueChangeManager();
}
