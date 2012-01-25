/*******************************************************************************
 * Copyright 2011: Matthias Beste, Hannes Bischoff, Lisa Doerner, Victor Guettler, Markus Hattenbach, Tim Herzenstiel, Günter Hesse, Jochen Hülß, Daniel Krauth, Lukas Lochner, Mark Maltring, Sven Mayer, Benedikt Nees, Alexandre Pereira, Patrick Pfaff, Yannick Rödl, Denis Roster, Sebastian Schumacher, Norman Vogel, Simon Weber 
 *
 * Copyright 2010: Anna Aichinger, Damian Berle, Patrick Dahl, Lisa Engelmann, Patrick Groß, Irene Ihl, Timo Klein, Alena Lang, Miriam Leuthold, Lukas Maciolek, Patrick Maisel, Vito Masiello, Moritz Olf, Ruben Reichle, Alexander Rupp, Daniel Schäfer, Simon Waldraff, Matthias Wurdig, Andreas Wußler
 *
 * Copyright 2009: Manuel Bross, Simon Drees, Marco Hammel, Patrick Heinz, Marcel Hockenberger, Marcus Katzor, Edgar Kauz, Anton Kharitonov, Sarah Kuhn, Michael Löckelt, Heiko Metzger, Jacqueline Missikewitz, Marcel Mrose, Steffen Nees, Alexander Roth, Sebastian Scharfenberger, Carsten Scheunemann, Dave Schikora, Alexander Schmalzhaf, Florian Schultze, Klaus Thiele, Patrick Tietze, Robert Vollmer, Norman Weisenburger, Lars Zuckschwerdt
 *
 * Copyright 2008: Camil Bartetzko, Tobias Bierer, Lukas Bretschneider, Johannes Gilbert, Daniel Huser, Christopher Kurschat, Dominik Pfauntsch, Sandra Rath, Daniel Weber
 *
 * This program is free software: you can redistribute it and/or modify it un-der the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT-NESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package org.bh.platform;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ServiceLoader;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.EventListenerList;

import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.WriterAppender;
import org.bh.calculation.IShareholderValueCalculator;
import org.bh.calculation.IStochasticProcess;
import org.bh.calculation.ITimeSeriesProcess;
import org.bh.companydata.importExport.INACEImport;
import org.bh.controller.IDataExchangeController;
import org.bh.controller.IPeriodController;
import org.bh.data.DTO;
import org.bh.data.DTO.Stochastic;
import org.bh.data.DTOBusinessData;
import org.bh.data.DTOKeyPair;
import org.bh.gui.swing.BHPopupFrame;
import org.bh.gui.swing.BHStatusBar;
import org.bh.gui.swing.comp.BHTextField;
import org.bh.platform.i18n.BHTranslator;
import org.bh.platform.i18n.ITranslator;

/**
 * This class offers static functions which can be used by other parts of the
 * software.
 * 
 * @author Marco Hammel
 * @author Robert Vollmer
 * @update 23.12.2010 Timo Klein
 */
public class Services {
	private static final Logger log = Logger.getLogger(Services.class);
	private static EventListenerList platformListeners = new EventListenerList();
	private static HashMap<String, IShareholderValueCalculator> dcfMethods;
	private static HashMap<String, IStochasticProcess> stochasticProcesses;
	private static HashMap<String, ITimeSeriesProcess> timeSeriesProcesses;
	private static HashMap<String, IPeriodController> periodControllers;
	private static HashMap<String, IDataExchangeController> dataExchangeController;
	private static HashMap<String, IImportExport> importExport;
	private static HashMap<String, IPrint> print;
	private static HashMap<String, BHPopupFrame> popUpFrames;
	private static NumberFormat doubleFormat = null;
	private static NumberFormat integerFormat = null;
	private static NumberFormat oldDoubleFormat = null;
	private static StringWriter logWriter = new StringWriter();

	private static final String IMPORT_PATH_BRANCHES_DEFAULT = "org/bh/companydata/periods.xml";
	
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
		log.debug("Firing " + event);
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
	
	/**
	 * Überprüft, ob das TimeSeriesPlugin installiert und geladen ist
	 * @return true falls ja, false falls nein
	 */
	public static boolean check4TimeSeriesPlugin() {
		boolean isLoaded = false;
		ServiceLoader<ITimeSeriesProcess> timeSeriesPlugins = PluginManager
				.getInstance().getServices(ITimeSeriesProcess.class);
		for (ITimeSeriesProcess timeSeriesPlugin : timeSeriesPlugins) {
			isLoaded = true; break;
		}
		return isLoaded;
	}
	
	/**
	 * Loads a list of time series processes and returns them as a map.
	 * The Unique ID is the key here.
	 * 
	 * @return
	 */
	public static Map<String, ITimeSeriesProcess> getTimeSeriesProcesses(){
		if(timeSeriesProcesses == null){
			loadTimeSeriesProcesses();
		}
		return timeSeriesProcesses;
	}
	
	/**
	 * Checks whether plugin for branch specific representative plugin
	 * is loaded and exists.
	 * @return true if yes, false if no
	 */
	public static boolean doesBranchSpecificRepresentativePluginExist() {
		boolean isLoaded = false;
//		ServiceLoader<BHPopupFrame> popUpFrames = PluginManager
//				.getInstance().getServices(BHPopupFrame.class);
//		if(popUpFrames.iterator().hasNext()){
//			isLoaded = true;
//		}
//		return isLoaded;
		return true;
	}
	
	/**
	 * Loads a list of popups and returns them as a map.
	 * The Unique ID is the key here.
	 * 
	 * @return
	 */
	public static Map<String, BHPopupFrame> getPopUpFrames(){
		if(popUpFrames == null){
			loadPopUpFrames();
		}
		return popUpFrames;
	}
	
	/**
	 * Returns a reference to a popup frame.
	 * 
	 * @author Yannick Rödl, 27.12.2011
	 * @return The reference to the popup frames, or null if not found.
	 */
	public static BHPopupFrame getPopUpFrame(String id) {
		return getPopUpFrames().get(id);
	}
	
	/**
	 * Loads every given plugin for a time series.
	 */
	private static void loadPopUpFrames(){
		popUpFrames = new HashMap<String, BHPopupFrame>();
		ServiceLoader<BHPopupFrame> frames = PluginManager
				.getInstance().getServices(BHPopupFrame.class);
		for (BHPopupFrame frame : frames) {
			popUpFrames.put(frame.getUniqueId(), frame);
		}
	}
	
	/**
	 * Returns a reference to a time series process.
	 * 
	 * @author Timo Klein 23.12.2010
	 * @update Yannick Rödl, 12.12.2011
	 * @return The reference to the time series process, or null if not found.
	 */
	public static ITimeSeriesProcess getTimeSeriesProcess(String id) {
		return getTimeSeriesProcesses().get(id);
	}
	
	/**
	 * Loads every given plugin for a time series.
	 */
	private static void loadTimeSeriesProcesses(){
		timeSeriesProcesses = new HashMap<String, ITimeSeriesProcess>();
		ServiceLoader<ITimeSeriesProcess> processes = PluginManager
				.getInstance().getServices(ITimeSeriesProcess.class);
		for (ITimeSeriesProcess process : processes) {
			timeSeriesProcesses.put(process.getUniqueId(), process);
		}
	}

	
	// end

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

	public static Map<String, IDataExchangeController> getDataExchangeController() {
		if (dataExchangeController == null)
			loadDataExchangeController();
		return dataExchangeController;
	}

	public static IDataExchangeController getDataExchangeController(
			String dataFormat) {
		if (dataExchangeController == null)
			loadDataExchangeController();
		return dataExchangeController.get(dataFormat);
	}

	private static void loadDataExchangeController() {
		dataExchangeController = new HashMap<String, IDataExchangeController>();
		ServiceLoader<IDataExchangeController> controller = PluginManager
				.getInstance().getServices(IDataExchangeController.class);
		for (IDataExchangeController contrl : controller)
			dataExchangeController.put(contrl.getDataFormat(), contrl);
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
				log.error("Could not check annotation", e);
				continue;
			}
		}
		return keys;
	}

	private static void loadImportExportPlugins() {
		// load all import export plug-ins and put them into the map
		importExport = new HashMap<String, IImportExport>();
		ServiceLoader<IImportExport> impExpPlugins = PluginManager
				.getInstance().getServices(IImportExport.class);
		for (IImportExport impExp : impExpPlugins) {
			importExport.put(impExp.getUniqueId(), impExp);
		}
	}

	/**
	 * ServiceLoader to get the first available plugin to read the NACE XML in two different languages.
	 * @return INACEImport interface to read NACE data.
	 */
	public static INACEImport getNACEReader(){
		ServiceLoader<INACEImport> importXML = PluginManager.getInstance().getServices(INACEImport.class);
		for(INACEImport plugin: importXML){
			return plugin.createNewInstance();
		}
		return null;
	}
	
	/**
	 * Returns the references to all import export plug-ins.
	 * 
	 * @return References to all import export plug-ins matching the required
	 *         methods.
	 */
	public static Map<String, IImportExport> getImportExportPlugins(
			int requiredMethods) {
		int check;
		Map<String, IImportExport> matchingImportExport;

		if (importExport == null) {
			loadImportExportPlugins();
		}
		matchingImportExport = new HashMap<String, IImportExport>();
		for (Entry<String, IImportExport> plugin : importExport.entrySet()) {
			check = requiredMethods & plugin.getValue().getSupportedMethods();
			if (requiredMethods == check) {
				matchingImportExport.put(plugin.getKey(), plugin.getValue());
			}
		}
		return matchingImportExport;
	}

	/**
	 * Returns the references to all print plug-ins.
	 * 
	 * @return References to all print
	 */
	public static Map<String, IPrint> getPrintPlugins(int requiredMethods) {
		if (print == null) {
			loadPrintPlugins();
		}
		return print;
	}

	private static void loadPrintPlugins() {
		// load all print plug-ins and put them into the map
		print = new HashMap<String, IPrint>();
		ServiceLoader<IPrint> printPlugins = PluginManager.getInstance()
				.getServices(IPrint.class);
		for (IPrint printPlug : printPlugins) {
			print.put(printPlug.getUniqueId(), printPlug);
		}
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
					UIManager.put("nimbusOrange", new Color(255, 165, 0));
					UIManager.put("nimbusSelectionBackground", new Color(80,
							160, 190));

					// BH specific attributes.
					UIManager.put("BHTree.nodeheight", 20);
					UIManager.put("BHTree.minimumWidth", 200);
					UIManager.put("Chart.background", UIManager.get("control"));

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
	public static ImageIcon createImageIcon(String path) {
		URL imgURL = Services.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		}

		Logger.getLogger(Services.class).debug("Could not find icon " + path);
		return null;

	}
	
	/**
	 * The setIcon method returns a icon list with the bh-logo in three different resolutions
	 * 
	 * @return icons for dialogs
	 */
	
	public static List<Image> setIcon(){
		try {
			List<Image> icons = new ArrayList<Image>();
			icons.add(ImageIO.read(Services.class.getResourceAsStream("/org/bh/images/BH-Logo-16px.png")));
			icons.add(ImageIO.read(Services.class.getResourceAsStream("/org/bh/images/BH-Logo-32px.png")));
			icons.add(ImageIO.read(Services.class.getResourceAsStream("/org/bh/images/BH-Logo-48px.png")));
			return icons;
		} catch (Exception e) {
			log.error("Failed to load IconImage", e);
			return null;
		}
		
	}
	
	/**
	 * This method loads all the branch data, we need to calculate with industry specific
	 * representatives. This is necessary to calculate the branch specific representative
	 * in a faster way and to prefill ComboBoxes with values.
	 * 
	 * The default is to load the data from a previously exported company data file. If
	 * this one is not accessible, the default one from the .jar is loaded.
	 */
	public static void loadBranches(){
		Map<String, IImportExport> plugins = getImportExportPlugins(IImportExport.IMP_PROJECT);
		
		IImportExport plugin = plugins.get("XML");
		
		//Hole den Dateipfad aus den Preferences. Ist gespeichert in der Variable "branches".
		String xmlBranchesName = PlatformController.preferences.get("branches", null);
		
		DTOBusinessData bd = null;
		DTO.setThrowEvents(false); //Don't throw events.
		
		//Did we get a default location
		if(xmlBranchesName == null){
			bd = loadBranchesFromXML(plugin, Services.IMPORT_PATH_BRANCHES_DEFAULT);
		} else {
			bd = loadBranchesFromXML(plugin, xmlBranchesName);
			
			if(bd == null){
				bd = loadBranchesFromXML(plugin, Services.IMPORT_PATH_BRANCHES_DEFAULT);
			}
			if (bd == null){
				bd = loadBranchesFromXML(plugin, "src/" + Services.IMPORT_PATH_BRANCHES_DEFAULT);
			}
		}
		
		if(bd == null){
			bd = new DTOBusinessData();
		}
			
		//Default BusinessDataDTO
		PlatformController.setBusinessDataDTO(bd);
			
		DTO.setThrowEvents(true);
	}
	
	/**
	 * This method is an abstraction from the loading of the data, to make the file import
	 * of the DTOBusinessData easier to read.
	 * 
	 * @param plugin the plugin to load the data with
	 * @param fileName the name of the file to load
	 * @return null if no data could be retrieved else the DTOBusinessData with all the data
	 */
	private static DTOBusinessData loadBranchesFromXML(IImportExport plugin, String fileName){
		plugin.setFile(fileName);
		log.info("Loading branch XML from path: " + fileName);
		
		try{
			return (DTOBusinessData) plugin.startImport();
		} catch (Exception exc){
			log.error("Could not load branches.", exc);
			return null;
		}
	}
	
	/**
	 * This method generates the path to the xml file, where the branch data
	 * is stored. This path is created hidden, so the user should not see it, and
	 * should therefore not be able to modify it.
	 * @return generated path to the place, where the xml should be stored.
	 */
	public static String generateBranchDataFilePath(){
		String filePath = System.getProperty("user.home");
		filePath = filePath + File.separator;
		if(!System.getProperty("os.name").startsWith("Windows")){
			filePath += ".";
		}
		
		filePath += "BusinessHorizon";
		
		//Check if filePath exists
		File f = new File(filePath);
		if(!f.exists()){
			f.mkdir();
			
			//Hide folder in windows
			if(System.getProperty("os.name").startsWith("Windows")){
				try {
					Runtime.getRuntime().exec("attrib +H " + filePath);
				} catch (IOException e) {
					log.error("Could not hide folder", e);
				}
			}
		}
		
		filePath = filePath + File.separator;
		
		//Windows uses other things to hide file Path.
		if(!System.getProperty("os.name").startsWith("Windows")){
			filePath += ".";
		}
		
		filePath += "BH_Unternehmensdaten.xml";
		return filePath;
	}

	/**
	 * Checks if JRE is fulfilling the requirements for Business Horizon.
	 * 
	 * Currently Business Horizon is requiring Java 6 Update 10. (1.6.0_10)
	 * 
	 * @return <code>true</code> if JRE fulfills and <code>false</code> if it
	 *         doesn't.
	 */
	public static boolean jreFulfillsRequirements() {
		// Require Java 6 Update 10 or higher.
		StringTokenizer javaVersion = new StringTokenizer(System
				.getProperty("java.version"), "._");

		int root = Integer.parseInt(javaVersion.nextToken());
		int major = Integer.parseInt(javaVersion.nextToken());
		int minor = Integer.parseInt(javaVersion.nextToken());
		int patchlevel = Integer.parseInt(javaVersion.nextToken());

		if (root < 1) {
			return false;
		}
		if (root == 1 && major < 6) {
			return false;
		}
		if (root == 1 && major == 6 && minor == 0 && patchlevel < 10) {
			return false;
		}
		return true;
	}

	public static boolean setFocus(Container cont) {
		for (Component comp : cont.getComponents()) {
			if (comp instanceof BHTextField) {
				final BHTextField tf = (BHTextField) comp;

				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						tf.requestFocus();
					}
				});
				return true;
			} else if (comp instanceof JPanel) {
				if (setFocus((Container) comp))
					return true;
			}

		}
		return false;
	}

	public static void setupLogger() {
		String pattern = "%d{ISO8601} %-5p [%t] %c: %m%n";
		WriterAppender appender = new WriterAppender(
				new PatternLayout(pattern), logWriter);
		Logger.getRootLogger().addAppender(appender);
	}

	public static String getLog() {
		logWriter.flush();
		return logWriter.toString();
	}

	public static void initNumberFormats() {
		oldDoubleFormat = doubleFormat;
		
		doubleFormat = NumberFormat.getNumberInstance();
		doubleFormat.setMinimumIntegerDigits(1);
		doubleFormat.setMinimumFractionDigits(0);
		doubleFormat.setMaximumFractionDigits(4);

		integerFormat = NumberFormat.getNumberInstance();
		integerFormat.setParseIntegerOnly(true);
		integerFormat.setMinimumIntegerDigits(1);
	}

	public static String numberToString(double number) {
		return doubleFormat.format(number);
	}

	public static double stringToDouble(String string) {
		ParsePosition pp = new ParsePosition(0);
		Number result = doubleFormat.parse(string, pp);
		if (result != null && string.length() == pp.getIndex())
			return result.doubleValue();
		
		return Double.NaN;
	}

	public static Integer stringToInt(String string) {
		ParsePosition pp = new ParsePosition(0);
		Number result = integerFormat.parse(string, pp);
		if (result != null && string.length() == pp.getIndex())
			return result.intValue();
		
		return null;
	}
	
	public static double oldStringToDouble(String string) {
		if (oldDoubleFormat == null)
			return Double.NaN;
		
		ParsePosition pp = new ParsePosition(0);
		Number result = oldDoubleFormat.parse(string, pp);
		if (result != null && string.length() == pp.getIndex())
			return result.doubleValue();
		
		return Double.NaN;
	}
}
