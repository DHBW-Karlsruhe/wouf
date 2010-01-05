package org.bh.platform;

import java.awt.Color;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.EventListenerList;

import org.apache.log4j.Logger;
import org.bh.calculation.IShareholderValueCalculator;
import org.bh.calculation.IStochasticProcess;
import org.bh.controller.IPeriodController;
import org.bh.data.DTOKeyPair;
import org.bh.data.DTO.Stochastic;
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
	private static HashMap<String, IShareholderValueCalculator> dcfMethods;
	private static HashMap<String, IStochasticProcess> stochasticProcesses;
	private static HashMap<String, IPeriodController> periodControllers;

	/*
	 * --------------------------------------- Platform Event Handling
	 * ---------------------------------------
	 */

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

	/*
	 * --------------------------------------- Service Loader / PlugIn
	 * Management ---------------------------------------
	 */

	/**
	 * Returns a reference to a DCF method with a specific id.
	 * 
	 * @param id
	 *            The id of the DCF method.
	 * @return The reference to the DCF method, or null if not found.
	 */
	public static IShareholderValueCalculator getDCFMethod(String id) {
		return getDCFMethods().get(id);
	}

	/**
	 * Returns the references to all loaded DCF methods.
	 * 
	 * @return References to all loaded DCF methods.
	 */
	public static Map<String, IShareholderValueCalculator> getDCFMethods() {
		if (dcfMethods == null)
			loadDCFMethods();
		return dcfMethods;
	}

	private static void loadDCFMethods() {
		// load all DCF methods and put them into the map
		dcfMethods = new HashMap<String, IShareholderValueCalculator>();
		ServiceLoader<IShareholderValueCalculator> calculators = PluginManager
				.getInstance().getServices(IShareholderValueCalculator.class);
		for (IShareholderValueCalculator calculator : calculators) {
			dcfMethods.put(calculator.getUniqueId(), calculator);
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
		return getStochasticProcesses().get(id);
	}

	/**
	 * Returns the references to all loaded stochastic processes.
	 * 
	 * @return References to all loaded stochastic processes.
	 */
	public static Map<String, IStochasticProcess> getStochasticProcesses() {
		if (stochasticProcesses == null)
			loadStochasticProcesses();
		return stochasticProcesses;
	}

	private static void loadStochasticProcesses() {
		// load all stochastic processes and put them into the map
		stochasticProcesses = new HashMap<String, IStochasticProcess>();
		ServiceLoader<IStochasticProcess> processes = PluginManager
				.getInstance().getServices(IStochasticProcess.class);
		for (IStochasticProcess process : processes) {
			stochasticProcesses.put(process.getUniqueId(), process);
		}
	}

	// TODO Schmalzhaf.Alexander Testen!!!
	public static IPeriodController getPeriodController(String id) {
		return getPeriodControllers().get(id);
	}

	public static Map<String, IPeriodController> getPeriodControllers() {
		if (periodControllers == null)
			loadPeriodControllers();
		return periodControllers;
	}

	private static void loadPeriodControllers() {
		// load all PeriodGUIControllers and put them into the map
		periodControllers = new HashMap<String, IPeriodController>();
		ServiceLoader<IPeriodController> controllers = PluginManager
				.getInstance().getServices(IPeriodController.class);
		for (IPeriodController controller : controllers) {
			periodControllers.put(controller.getGuiKey(), controller);
		}

	}

	@SuppressWarnings("unchecked")
	public static List<DTOKeyPair> getStochasticKeysFromEnum(String dtoId,
			Enum[] keyEnumeration) {
		ArrayList<DTOKeyPair> keys = new ArrayList<DTOKeyPair>();
		for (Enum element : keyEnumeration) {
			try {
				Field field = element.getClass().getDeclaredField(
						element.name());
				if (field.isAnnotationPresent(Stochastic.class))
					keys.add(new DTOKeyPair(dtoId, element.toString()));
			} catch (Throwable e) {
				continue;
			}
		}
		return keys;
	}

	/*
	 * --------------------------------------- GUI
	 * ---------------------------------------
	 */

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
					// Put specific look&feel attributes.
					UIManager.put("nimbusBase", new Color(55, 125, 170));
					UIManager.put("control", new Color(235, 240, 255));
					UIManager.put("progressBar", new Color(255, 165, 0));
					UIManager.put("nimbusOrange", UIManager.get("progressBar"));
					UIManager.put("nimbusSelectionBackground", new Color(80,
							160, 190));

					// BH specific attributes.
					UIManager.put("BHTree.nodeheight", 20);
					UIManager.put("BHTree.minimumWidth", 150);

					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			Logger.getLogger(Services.class).debug(
					"Error while invoking Nimbus", e);
		}
	}

	/**
	 * Returns an ImageIcon, or null if the path was invalid.
	 */
	public static ImageIcon createImageIcon(String path, String description) {
		URL imgURL = Services.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		}

		Logger.getLogger(Services.class).debug("Could not find icon " + path);
		return null;

	}
}
