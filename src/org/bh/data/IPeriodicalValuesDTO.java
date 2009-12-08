package org.bh.data;

/**
 * 
 * Marker interface for DTOs which provide all values for one period. 
 *
 * The following values have to be provided by the DTO:
 * - Fremdkapital
 * - FCF
 * 
 *
 * @author Robert Vollmer
 * @version 1.0, 06.12.2009
 *
 */

public interface IPeriodicalValuesDTO extends IDTO<Value> {
	/**
	 * Returns an ID for this DTO.
	 * @return
	 */
	String getUniqueId();	
}
