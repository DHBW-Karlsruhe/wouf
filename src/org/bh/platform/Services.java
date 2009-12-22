package org.bh.platform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ServiceLoader;

import javax.swing.event.EventListenerList;

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
	private static ITranslator translator = BHTranslator.getInstance();
	private static BHStatusBar bhStatusBar = BHStatusBar.getInstance();
	private static HashMap<String, DisplayablePluginWrapper<IShareholderValueCalculator>> dcfMethods;
	private static HashMap<String, DisplayablePluginWrapper<IStochasticProcess>> stochasticProcesses;

	public static ITranslator getTranslator() {
		return translator;
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
		return bhStatusBar;
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
}
