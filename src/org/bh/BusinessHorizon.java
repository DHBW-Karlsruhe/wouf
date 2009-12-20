package org.bh;

import java.awt.Window;
import java.lang.Thread.UncaughtExceptionHandler;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import org.bh.data.IPeriodicalValuesDTO;
import org.bh.gui.swing.BHMainFrame;
import org.bh.gui.swing.BHSplashScreen;
import org.bh.platform.PluginManager;
import org.bh.platform.i18n.BHTranslator;

/**
 * 
 * This is the entry class for Business Horizon.
 * 
 * The main method of this class will be called when Business Horizon starts.
 * 
 * @author Robert Vollmer
 * @version 0.1, 06.12.2009
 * 
 * @author Patrick Heinz
 * added SplashScreen 20.12.2009
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
		
		Runnable splashScreenRunnable = new BHSplashScreen();
		Thread splashScreenThread = new Thread(splashScreenRunnable);
		splashScreenThread.start();
		((Window) splashScreenRunnable).setAlwaysOnTop(true);
		
		log.info("Business Horizon is starting...");

		Thread
				.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
					@Override
					public void uncaughtException(Thread t, Throwable e) {
						log.error("Uncaught exception", e);
					}
				});

		PluginManager.getInstance().loadAllServices(IPeriodicalValuesDTO.class);

		// new Main();
		
		// Invoke start of BHMainFrame
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new BHMainFrame(BHTranslator.getInstance().translate("title"));
			}

		});
	}
}
