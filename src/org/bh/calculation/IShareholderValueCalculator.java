package org.bh.calculation;

import org.bh.data.DTOScenario;
import org.bh.data.types.Calculable;

/**
 * This interface is implemented by classes which provide calculation methods for
 * a company's shareholder value. 
 *
 * @author Robert Vollmer
 * @version 1.1, 14.12.2009
 *
 */
public interface IShareholderValueCalculator {
	/**
	 * This method calculates the shareholder value. However, the {@link #getShareholderValue()}
	 * function has to be called to retrieve the result. 
	 * 
	 * @param scenario The DTO of the scenario.
	 */
	void calculate(DTOScenario scenario);
	
	/**
	 * Returns the calculated shareholder value;
	 * @return The current (t=0) shareholder value or null if it has not been calculated yet. 
	 */
	Calculable getShareholderValue();
	
	/**
	 * Defines the description of the calculator which will be displayed at the GUI.
	 * @return Translation key for the description.
	 */
	String getGuiKey();
	
	/**
	 * Defines a unique string which identifies this calculation method.
	 * 
	 * @return The unique ID.
	 */
	String getUniqueId();
}
