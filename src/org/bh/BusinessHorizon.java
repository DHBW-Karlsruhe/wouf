package org.bh;

import java.util.ServiceLoader;

import org.apache.log4j.Logger;
import org.bh.platform.IPeriodicalValuesController;
import org.bh.platform.IPeriodicalValuesDTO;
import org.bh.platform.PluginManager;

/**
 *
 * This is the entry class for Business Horizon. 
 *
 * The main method of this class will be called when Business
 * Horizon starts.
 *
 * @author Robert Vollmer
 * @version 0.1, 06.12.2009
 *
 */

public class BusinessHorizon {
	private static final Logger log = Logger.getLogger(BusinessHorizon.class);
	
	/**
	 * @param args Commandline arguments
	 */
	public static void main(String[] args) {
		log.info("Business Horizon is starting...");
		
		PluginManager pluginManager = PluginManager.getInstance();
		ServiceLoader<IPeriodicalValuesController> controllers = pluginManager.getServices(IPeriodicalValuesController.class);
		for (IPeriodicalValuesController controller : controllers) {
			IPeriodicalValuesDTO dto = controller.dummy();
			log.debug(dto.toString());
		}
	}
}
