package org.bh.data;

/**
 * 
 * Marker interface for DTOs which provide all values for one period.
 *
 * @author Robert Vollmer
 * @version 1.0, 06.12.2009
 *
 */

@SuppressWarnings("unchecked")
public interface IPeriodicalValuesDTO extends IDTO {
	/**
	 * Returns an ID for this DTO.
	 * @return
	 */
	String getUniqueId();
}
