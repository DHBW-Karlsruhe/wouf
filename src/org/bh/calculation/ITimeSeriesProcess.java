package org.bh.calculation;

import java.util.TreeMap;

import javax.swing.JPanel;

import org.bh.data.DTOScenario;
import org.bh.platform.IDisplayablePlugin;

/**
 * This interface is implemented by plugins which can execute timeSeries
 * processes.
 * 
 * @author Andreas Wußler, Timo Klein
 * @version 1.0, 22.12.2010
 * @update 23.12.2010 Timo Klein
 */
public interface ITimeSeriesProcess extends IDisplayablePlugin{
	
	
	/**
	 * Defines a unique string which identifies this timeSeries process.
	 * 
	 * @return The unique ID.
	 */
	String getUniqueId();
	
	/**
	 * Creates a new instance of the same class for a specific scenario.
	 * 
	 * @param The scenario used in this instance of the time series process.
	 * @return A new instance of the same class.
	 */
	
	ITimeSeriesProcess createNewInstance(DTOScenario scenario);

	JPanel calculateParameters();

	void print(DTOScenario scenario);

	void updateParameters();

	TreeMap<Integer, Integer> calculate();

}
