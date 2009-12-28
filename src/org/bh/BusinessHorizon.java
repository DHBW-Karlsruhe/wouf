package org.bh;

import java.lang.Thread.UncaughtExceptionHandler;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import org.bh.data.IPeriodicalValuesDTO;
import org.bh.gui.swing.BHMainFrame;
import org.bh.gui.swing.BHSplashScreen;
import org.bh.platform.PlatformController;
import org.bh.platform.PluginManager;

/**
 * 
 * This is the entry class for Business Horizon.
 * 
 * The main method of this class will be called when Business Horizon starts.
 * 
 * @author Robert Vollmer
 * @author Patrick Heinz
 * @version 0.2, 20.12.2009
 * 
 * 
 */

public class BusinessHorizon {
	private static final Logger log = Logger.getLogger(BusinessHorizon.class);

	/**
	 * @param args
	 *            Commandline arguments
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		log.info("Business Horizon is starting...");
		// set Look&Feel
		BHMainFrame.setNimbusLookAndFeel();
		// show splash screen
		new Thread(new BHSplashScreen()).start();

		Thread
				.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
					@Override
					public void uncaughtException(Thread t, Throwable e) {
						log.error("Uncaught exception", e);
					}
				});

		PluginManager.getInstance().loadAllServices(IPeriodicalValuesDTO.class);

		// Invoke start of BHMainFrame
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				
				PlatformController pc = new PlatformController();
				
			}

		});
	}
}
