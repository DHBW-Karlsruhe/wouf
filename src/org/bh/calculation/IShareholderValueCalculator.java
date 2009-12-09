package org.bh.calculation;

import java.util.List;

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
	 * @return A list of shareholder values for each year, beginning with t=0.
	 */
	List<Value> calculate(DTOScenario scenario);
	
	/**
	 * Defines a unique string which identifies this calculation method.
	 * 
	 * @return The unique ID.
	 */
	String getUniqueId();
}
