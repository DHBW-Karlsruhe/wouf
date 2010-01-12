package org.bh;

import java.lang.Thread.UncaughtExceptionHandler;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import org.bh.platform.PlatformController;
import org.bh.platform.PluginManager;
import org.bh.platform.Services;

/**
 * 
 * This is the entry class for Business Horizon.
 * 
 * The main method of this class will be called when Business Horizon starts.
 * 
 * @author Robert Vollmer
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
		
		PluginManager.init();

		Thread
				.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
					@Override
					public void uncaughtException(Thread t, Throwable e) {
						log.error("Uncaught exception", e);
					}
				});
		
		// set Look&Feel
		Services.setNimbusLookAndFeel();
		
		// TODO Thiele.Klaus Replace with java.awt.Splashscreen
		// new Thread(new BHSplashScreen()).start();
		
		// Invoke start of BHMainFrame
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				PlatformController pc = PlatformController.getInstance();
			}
		});
	}
}
