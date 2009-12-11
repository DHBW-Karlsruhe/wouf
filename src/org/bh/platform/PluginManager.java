package org.bh.platform;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;

/**
 * 
 * This class manages all plugins for Business Horizon. 
 *
 * This class first checks which plugins are available. It checks the "plugins" package as well
 * as the "plugins" folder of the current working directory for plugins (however, subfolders are
 * not checked). Plugins can be a folder or a JAR file. They contain the .class files for the plugin
 * and a description which service(s) it provides. This description is a plain text file with the
 * full name of the superclass/interface, placed in META-INF/services. Every line of the file is the
 * full class name of a class which extends/implements  the superclass/interface.
 *   
 *
 * @author Robert Vollmer
 * @version 1.0, 06.12.2009
 *
 */

public final class PluginManager {
	private static final Logger log = Logger.getLogger(PluginManager.class);
	private static PluginManager singletonInstance;
	
	private ClassLoader pluginClassLoader;  
	@SuppressWarnings("unchecked")
	private Map<Class, ServiceLoader> cache = new HashMap<Class, ServiceLoader>();
	
	/**
	 * Constructor: Look for plugins and create a classloader
	 */
	private PluginManager() {
		log.debug("Initializing PluginManager...");
		
		// these are the paths to plugin folders and jars 
		List<URL> pluginUrls = new ArrayList<URL>();
		
		// look for plugins in the "plugins" package of the application...
		URL bundledPluginsDir = Thread.currentThread().getContextClassLoader().getResource("plugins");
		loadPlugins(bundledPluginsDir, pluginUrls);
		// ... and in the "plugins" folder of the current working directory
		loadPlugins(new File("plugins"), pluginUrls);
		
		log.info("The following plugins have been found:");
		for (URL url : pluginUrls) {
			log.info("- " + url);
		}
		
		// this is the default ClassLoader
		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		// create a new ClassLoader for all found plugins (with default ClassLoader as fallback)
		pluginClassLoader = new URLClassLoader(pluginUrls.toArray(new URL[0]), contextClassLoader);
	}
	
	/**
	 * Get instance (Singleton)
	 * @return instance of PluginManager
	 */
	public synchronized static PluginManager getInstance() {
		if (singletonInstance == null)
			singletonInstance = new PluginManager();
		return singletonInstance;		
	}
	
	/**
	 * Get all services for a specific class.
	 * 
	 * Usage:
	 * <pre>
	 * PluginManager pluginManager = PluginManager.getInstance();
	 * ServiceLoader&lt;Superclass&gt; superclassServices = pluginManager.getServices(Superclass.class);
	 * for (Superclass subclassInstance : superclassServices) {
	 *     // subclassInstance contains one instance of the subclass
	 * }
	 * </pre>
	 * 
	 * @param serviceClass superclass or interface of the service
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public <T> ServiceLoader<T> getServices(Class<T> serviceClass) {
		// check whether the services for this class have already been loaded
		ServiceLoader<T> serviceLoader = cache.get(serviceClass);
		if (serviceLoader == null) {
			// not cached => loaded services
			serviceLoader = ServiceLoader.load(serviceClass, pluginClassLoader);

			if (log.isDebugEnabled()) {
				log.debug("Found the following services for class " + serviceClass.getName() + ":");
				for (T service : serviceLoader) {
					log.debug("- " + service.getClass().getName());
				}
			}
			
			// add service loader to cache
			cache.put(serviceClass, serviceLoader);
		}
		return serviceLoader;
	}
	
	/**
	 * Initiate all services for a specific class so that they are known to the class loader.
	 * @param serviceClass
	 * @see #getServices(Class)
	 */
	public <T> void loadAllServices(Class<T> serviceClass) {
		ServiceLoader<T> services = getServices(serviceClass);
		for (@SuppressWarnings("unused") T service : services) {
			// do nothing, class has been loaded
		}
	}
	
	/**
	 * Load all plugins at a URL (e.g. folder or JAR file).
	 * 
	 * @param dir
	 * @param pluginUrls
	 */
	
	private void loadPlugins(URL dir, List<URL> pluginUrls) {
		if (dir == null)
			return;

		if (dir.getProtocol().equals("file")) {
			loadPlugins(new File(dir.getFile()), pluginUrls);
		} else if (dir.getProtocol().equals("jar")) {
			try {
				JarFile jar = (((JarURLConnection)dir.openConnection()).getJarFile());
				// search for plugins in the jar
				Enumeration<JarEntry> entries = jar.entries();
				while (entries.hasMoreElements()) {
					JarEntry entry = entries.nextElement();
					
					// filter: only folders placed directly in the "plugins" folder
					// plugins in jar files (inside the application's jar file) cannot be loaded
					if (!entry.getName().matches("plugins/[^/]+/$"))
						continue;
					
					// create an URL pointing the the plugin we just found
					String entryPath = dir.getFile().split("!")[0] + "!/" + entry.getName(); 
					pluginUrls.add(new URL(dir.getProtocol(), dir.getHost(), entryPath));
				}
				jar.close();
			} catch (IOException e) {
				log.error("Failed to load JAR", e);
			}
		}
	}
	
	/**
	 * Load all plugins in a folder.
	 * 
	 * @param folder
	 * @param pluginUrls
	 */
	private void loadPlugins(File folder, List<URL> pluginUrls) {
		if (folder == null || !folder.isDirectory())
			return;
		
		for (File file : folder.listFiles()) {
			// Look for folders or jar files
			if (file.isDirectory() || file.getName().endsWith(".jar")) {
				try {
					pluginUrls.add(file.toURI().toURL());
				} catch (MalformedURLException e) {
					log.error("Could not get URL for file " + file, e);
				}
			}
		}
	}
}
