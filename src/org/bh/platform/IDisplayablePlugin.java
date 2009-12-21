package org.bh.platform;

/**
 * Interface for plugins which can be displayed on the GUI.
 *
 * @author Robert Vollmer
 * @version 1.0, 21.12.2009
 *
 */
public interface IDisplayablePlugin {
	/**
	 * Defines the description of the plugin which will be displayed on the GUI.
	 * 
	 * @return Translation key for the description.
	 */
	String getGuiKey();
}
