package org.bh.calculation;

import org.bh.platform.IDisplayablePlugin;

/**
 * This interface is implemented by plugins which can execute timeSeries
 * processes.
 * 
 * @author Andreas Wußler, Timo Klein
 * @version 1.0, 22.12.2010
 */
public interface ITimeSeriesProcess extends IDisplayablePlugin{
	
	
	/**
	 * Defines a unique string which identifies this timeSeries process.
	 * 
	 * @return The unique ID.
	 */
	String getUniqueId();

}
