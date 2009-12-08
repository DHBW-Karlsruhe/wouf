package org.bh.data;

import java.util.HashMap;
import java.util.ServiceLoader;

import org.apache.log4j.Logger;
import org.bh.platform.PluginManager;

/**
 * Factory to produce IPeriodicalValuesDTO objects.
 *
 * When the <code>getInstance</code> method is called for the first time, the factory
 * checks which implementations of the IPeriodicalValuesDTO interface are available.
 * After that, the <code>create</code> method creates an instance of such a DTO, which
 * is identified by a unique ID.
 *
 * @author Robert Vollmer
 * @version 1.0, 07.12.2009
 *
 */

public final class PeriodicalValuesDTOFactory {
	private static final Logger log = Logger.getLogger(PeriodicalValuesDTOFactory.class);
	
	private static PeriodicalValuesDTOFactory singletonInstance;
	@SuppressWarnings("unchecked")
	private HashMap<String, Class> dtoClasses = new HashMap<String, Class>();
	
	/**
	 * Get Singleton instance
	 * @return
	 */
	public static PeriodicalValuesDTOFactory getInstance() {
		if (singletonInstance == null)
			singletonInstance = new PeriodicalValuesDTOFactory();
		return singletonInstance;
	}
	
	private PeriodicalValuesDTOFactory() {
		PluginManager pluginManager = PluginManager.getInstance();
		// load all implementations of IPeriodicalValuesDTO and put the class in the map
		ServiceLoader<IPeriodicalValuesDTO> dtos = pluginManager.getServices(IPeriodicalValuesDTO.class);
		for (IPeriodicalValuesDTO dto : dtos) {
			dtoClasses.put(dto.getUniqueId(), dto.getClass());
		}
	}
	/**
	 * Create an instance of a DTO.
	 * @param uniqueId the ID of the DTO to be created
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IPeriodicalValuesDTO create(String uniqueId) {
		Class dtoClass = dtoClasses.get(uniqueId);
		if (dtoClass != null) {
			try {
				return (IPeriodicalValuesDTO) dtoClass.newInstance();
			} catch (Exception e) {
				log.error("Cannot instantiate instance of class " + dtoClass.getName(), e);
				// @TODO throw exception
				return null;
			}
		}
		// @TODO throw exception
		return null;
	}
	
	/**
	 * Check whether a DTO class exists.
	 * @param uniqueId the ID of the DTO to be created
	 * @return
	 */
	public boolean exists(String uniqueId) {
		return dtoClasses.containsKey(uniqueId);
	}
}
