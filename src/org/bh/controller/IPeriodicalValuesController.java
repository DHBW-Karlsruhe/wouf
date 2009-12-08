package org.bh.controller;

import javax.swing.JPanel;

import org.bh.data.IPeriodicalValuesDTO;


/**
 * Interface for controllers which are responsible for creating and
 * editing DTOs with values for a single period. 
 *
 * This interface should be implemented by controllers...
 *
 * @author Robert Vollmer
 * @version 1.0, 08.12.2009
 *
 */

public interface IPeriodicalValuesController {
	/**
	 * This method is called by the platform whenever it needs a DTO for
	 * a specific period. If values already exist, the <code>dto</code>
	 * parameter contains a reference to the old DTO, otherwise it is null.
	 * It is up to the controller implemenation to modify the existing DTO
	 * or to create and return a new one.
	 * 
	 * If the controller registered as GUI controller, a reference to a JPanel
	 * will be passed where the view can display controls for editing the
	 * data.
	 * 
	 * Modifications to the DTO can (and probably will) occur even after the
	 * function returned. 
	 * 
	 * @param dto Either an existing DTO object or null.
	 * @param panel Panel for the view or null.
	 * @return The DTO with periodical values.
	 */
	IPeriodicalValuesDTO editDTO(IPeriodicalValuesDTO dto, JPanel panel);
}
