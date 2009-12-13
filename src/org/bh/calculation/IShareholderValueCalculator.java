package org.bh.calculation;

import org.bh.calculation.sebi.Value;
import org.bh.data.DTOScenario;

/**
 * This interface is implemented by classes which provide calculation methods for
 * a company's shareholder value. 
 *
 * @author Robert Vollmer
 * @version 1.0, 08.12.2009
 *
 */
public interface IShareholderValueCalculator {
	/**
	 * This method calculates the shareholder value after every period of a scenario. 
	 * 
	 * @param scenario The DTO of the scenario.
	 * @return The current shareholder value (t=0)
	 */
	Value calculate(DTOScenario scenario);
	
	/**
	 * Defines a unique string which identifies this calculation method.
	 * 
	 * @return The unique ID.
	 */
	String getUniqueId();
}
