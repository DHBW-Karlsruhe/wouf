package org.bh.controller;

import java.awt.Component;
import java.util.List;

import javax.swing.JPanel;

import org.bh.data.DTOKeyPair;
import org.bh.data.DTOPeriod;
import org.bh.platform.IDisplayablePlugin;

/**
 * Interface for controllers which are responsible for creating and editing DTOs
 * with values for a single period.
 * 
 * @author Robert Vollmer
 * @version 0.1, 08.12.2009
 * @version 1.0
 * 
 */
public interface IPeriodController extends IDisplayablePlugin {
	/**
	 * This method is called by the platform whenever it needs DTOs for a
	 * specific period.
	 * 
	 * <p>
	 * The <code>dto</code> parameter contains a reference to the DTO for this
	 * period. It's up to the controller to remove any existing child DTOs.
	 * 
	 * <p>
	 * The <code>panel</code> parameter contains a reference to a {@link JPanel}
	 * where the view can put its components.
	 * 
	 * Modifications to the DTO can (and most probably will) occur even after
	 * the function has returned.
	 * 
	 * @param period
	 *            The DTO of the period.
	 * @return Component where the view has put its components, or null if not
	 *         necessary
	 */
	Component editDTO(DTOPeriod period);

	/**
	 * Defines the priority. This is used to sort the controllers before
	 * displaying them at the GUI. The controller with the highest priority will
	 * be displayed first.
	 * 
	 * @return The priority of the controller.
	 */
	int getGuiPriority();

	/**
	 * Returns a list with the keys of all values which can be determined
	 * stochastically.
	 * 
	 * @return a list with the keys of all values which can be determined
	 *         stochastically.
	 */
	List<DTOKeyPair> getStochasticKeys();
}
