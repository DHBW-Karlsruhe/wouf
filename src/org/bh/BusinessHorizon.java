package org.bh;

import java.lang.Thread.UncaughtExceptionHandler;

import org.apache.log4j.Logger;
import org.bh.data.IPeriodicalValuesDTO;
import org.bh.gui.swing.BHMainFrame;
import org.bh.platform.PluginManager;
import org.bh.test.Main;

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
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		log.info("Business Horizon is starting...");
		
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				log.error("Uncaught exception", e);
			}
		});
		
		PluginManager.getInstance().loadAllServices(IPeriodicalValuesDTO.class);
		
		new Main();
//		new BHMainFrame("Business Horizon");
	}
}
