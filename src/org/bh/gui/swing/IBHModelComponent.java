package org.bh.gui.swing;

import org.bh.data.types.IValue;
import org.bh.gui.BHValidityEngine;

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
	public int[] getValidateRules();

	/**
	 * can set the Rules for the validation by runtime;
	 * 
	 * @param validateRules
	 */
	public void setValidateRules(int[] validateRules);
}
