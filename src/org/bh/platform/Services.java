package org.bh.platform;

import java.awt.Color;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ServiceLoader;

import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.EventListenerList;

import org.apache.log4j.Logger;
import org.bh.calculation.IShareholderValueCalculator;
import org.bh.calculation.IStochasticProcess;
import org.bh.gui.swing.BHStatusBar;
import org.bh.platform.i18n.BHTranslator;
import org.bh.platform.i18n.ITranslator;

/**
 * This class offers static functions which can be used by other parts of the
 * software.
 * 
 * @author Marco Hammel
 * @author Robert Vollmer
 */
public class Services {
	private static EventListenerList platformListeners = new EventListenerList();
	private static HashMap<String, DisplayablePluginWrapper<IShareholderValueCalculator>> dcfMethods;
	private static HashMap<String, DisplayablePluginWrapper<IStochasticProcess>> stochasticProcesses;

	public static ITranslator getTranslator() {
		return BHTranslator.getInstance();
	}

	public static void addPlatformListener(IPlatformListener l) {
		platformListeners.add(IPlatformListener.class, l);
	}

	public static void removePlatformListener(IPlatformListener l) {
		platformListeners.remove(IPlatformListener.class, l);
	}

	public static void firePlatformEvent(PlatformEvent event) {
		for (IPlatformListener l : platformListeners
				.getListeners(IPlatformListener.class))
			l.platformEvent(event);
	}

	public static BHStatusBar getBHstatusBar() {
		return BHStatusBar.getInstance();
	}

	/**
	 * Returns a reference to a DCF method with a specific id.
	 * 
	 * @param id
	 *            The id of the DCF method.
	 * @return The reference to the DCF method, or null if not found.
	 */
	public static IShareholderValueCalculator getDCFMethod(String id) {
		if (dcfMethods == null)
			loadDCFMethods();
		DisplayablePluginWrapper<IShareholderValueCalculator> wrapper = dcfMethods
				.get(id);

		if (wrapper == null)
			return null;
		return wrapper.getPlugin();
	}

	/**
	 * Returns the references to all loaded DCF methods.
	 * 
	 * @return References to all loaded DCF methods.
	 */
	public static List<DisplayablePluginWrapper<IShareholderValueCalculator>> getDCFMethods() {
		if (dcfMethods == null)
			loadDCFMethods();
		List<DisplayablePluginWrapper<IShareholderValueCalculator>> result = new ArrayList<DisplayablePluginWrapper<IShareholderValueCalculator>>(
				dcfMethods.values());
		Collections.sort(result);
		return result;
	}

	private static void loadDCFMethods() {
		// load all DCF methods and put them into the map
		dcfMethods = new HashMap<String, DisplayablePluginWrapper<IShareholderValueCalculator>>();
		ServiceLoader<IShareholderValueCalculator> calculators = PluginManager
				.getInstance().getServices(IShareholderValueCalculator.class);
		for (IShareholderValueCalculator calculator : calculators) {
			dcfMethods.put(calculator.getUniqueId(),
					new DisplayablePluginWrapper<IShareholderValueCalculator>(
							calculator));
		}
	}

	/**
	 * Returns a reference to a stochastic process with a specific id.
	 * 
	 * @param id
	 *            The id of the stochastic process.
	 * @return The reference to the stochastic process, or null if not found.
	 */
	public static IStochasticProcess getStochasticProcess(String id) {
		if (stochasticProcesses == null)
			loadStochasticProcesses();

		DisplayablePluginWrapper<IStochasticProcess> wrapper = stochasticProcesses
				.get(id);
		if (wrapper == null)
			return null;
		return wrapper.getPlugin();
	}

	/**
	 * Returns the references to all loaded stochastic processes.
	 * 
	 * @return References to all loaded stochastic processes.
	 */
	public static List<DisplayablePluginWrapper<IStochasticProcess>> getStochasticProcesses() {
		if (stochasticProcesses == null)
			loadStochasticProcesses();

		List<DisplayablePluginWrapper<IStochasticProcess>> result = new ArrayList<DisplayablePluginWrapper<IStochasticProcess>>(
				stochasticProcesses.values());
		Collections.sort(result);
		return result;
	}

	private static void loadStochasticProcesses() {
		// load all stochastic processes and put them into the map
		stochasticProcesses = new HashMap<String, DisplayablePluginWrapper<IStochasticProcess>>();
		ServiceLoader<IStochasticProcess> processes = PluginManager
				.getInstance().getServices(IStochasticProcess.class);
		for (IStochasticProcess process : processes) {
			stochasticProcesses.put(process.getUniqueId(),
					new DisplayablePluginWrapper<IStochasticProcess>(process));
		}
	}

	/**
	 * Sets Nimbus from Sun Inc. as default Look & Feel. Java 6 Update 10
	 * required. Don't change complex looking implementation of invokation,
	 * there are valid reasons for it.<br />
	 * 
	 * <b>Remark</b> <br />
	 * For further information on Nimbus see <a href=
	 * "http://developers.sun.com/learning/javaoneonline/2008/pdf/TS-6096.pdf"
	 * >JavaOne Slides</a>
	 */
	public static void setNimbusLookAndFeel() {
		// set Nimbus if available
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					//Put specific colors
					UIManager.put("nimbusBase", new Color(56, 124, 171));
					UIManager.put("control", new Color(235,240,255));
					UIManager.setLookAndFeel(info.getClassName());	
					break;
				}
			}
		} catch (Exception e) {
			Logger.getLogger(Services.class).debug("Error while invoking Nimbus", e);
		}
	}
	
	/** 
	 * Returns an ImageIcon, or null if the path was invalid. 
	 */
	public static ImageIcon createImageIcon(String path, String description) {
	    URL imgURL = Services.class.getResource(path);
	    if (imgURL != null) {
	        return new ImageIcon(imgURL, description);
	    } else {
	        Logger.getLogger(Services.class).debug("Could not find icon " + path);
	        return null;
	    }
	}
}
