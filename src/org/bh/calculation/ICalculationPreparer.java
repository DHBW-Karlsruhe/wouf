package org.bh.calculation;

import org.bh.calculation.sebi.Calculable;
import org.bh.data.DTOPeriod;

/**
 * This is an interface for plugins which can calculate the Free Cash Flow
 * and/or the liabilites for one periods from its child DTOs. 
 *
 * @author Robert Vollmer
 * @version 1.0, 14.12.2009
 *
 */
public interface ICalculationPreparer {
	/**
	 * Calculates the Free Cash Flow for one period.
	 * @param period The DTO of the period.
	 * @return The FCF, or null if the plugin cannot calculate it.
	 */
	Calculable getFCF(DTOPeriod period);
	
	/**
	 * Calculates the Free Cash Flow for one period.
	 * @param period The DTO of the period.
	 * @return The FCF, or null if the plugin cannot calculate it.
	 */
	Calculable getLiabilities(DTOPeriod period);
}
