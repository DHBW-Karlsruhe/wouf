package org.bh.calculation;

import javax.swing.JPanel;

import org.bh.data.DTOScenario;
import org.bh.data.types.DistributionMap;
import org.bh.platform.IDisplayablePlugin;

/**
 * This interface is implemented by plugins which can execute stochastic
 * processes.
 * 
 * @author Robert Vollmer
 * @version 1.1, 21.12.2009
 */
public interface IStochasticProcess extends IDisplayablePlugin {
	/**
	 * Uses the scenario to calculate the parameters needed for the stochastic
	 * process.
	 * 
	 * <p>
	 * The plugin has to care about storing these parameters internally. If the
	 * plugin needs to display a GUI for changing these parameters or the like,
	 * it returns a JPanel.
	 * 
	 * @see DTOScenario#getPeriodStochasticKeys()
	 * @see DTOScenario#getPeriodStochasticKeysAndValues()
	 * @return The GUI or null.
	 */
	JPanel calculateParameters();

	/**
	 * This function will be called when the user confirmed the parameters,
	 * right before starting the calculation. The plugin should then write the
	 * parameters back from the GUI to its internal data structures.
	 */
	void updateParameters();

	/**
	 * This is the calculation of the distribution of possible shareholder
	 * values.
	 * 
	 * <p>
	 * This method uses the internally stored parameters to execute the
	 * stochastic process. For the calculation of the shareholder value, it uses
	 * the DCF method stored in the scenario.
	 * 
	 * @see DTOScenario#getDCFMethod()
	 * @return The distribution of shareholder values.
	 */
	DistributionMap calculate();

	/**
	 * Defines a unique string which identifies this stochastic process.
	 * 
	 * @return The unique ID.
	 */
	String getUniqueId();

	/**
	 * Creates a new instance of the same class for a specific scenario.
	 * 
	 * @param The scenario used in this instance of the stochastic process.
	 * @return A new instance of the same class.
	 */
	IStochasticProcess createNewInstance(DTOScenario scenario);
}
