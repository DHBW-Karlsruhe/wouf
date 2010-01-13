package org.bh.platform;

import java.util.Map;


import org.bh.data.DTOProject;
import org.bh.data.DTOScenario;
import org.bh.data.types.Calculable;
import org.bh.data.types.DistributionMap;
import org.bh.gui.swing.BHProjectDataExchangeDialog;

/**
 * Interface for plugins which can import export DTOs to third party file
 * formats.
 * 
 * @author Norman
 * @version 1.0, 10.01.2010
 * 
 */
public interface IImportExport extends IDisplayablePlugin {
	
	static final int EXP_PROJECT		 	= 1 << 0;
	static final int EXP_PROJECT_RES_DET	= 1 << 1;
	static final int EX_PROJECT_RES_STOCH 	= 1 << 2;
	static final int IMP_PROJECT		    = 1 << 3;
	
	static final int EXP_SCENARIO		 	= 1 << 4;
	static final int EXP_SCENARIO_RES_DET	= 1 << 5;
	static final int EX_SCENARIO_RES_STOCH 	= 1 << 6;
	static final int IMP_SCENARIO		    = 1 << 7;

	/**
	 * Must be implemented if export of a project DTO should be supported by the
	 * implementing plug-in.
	 * 
	 * @param project
	 * @param exportDialog 
	 */
	void exportProject(DTOProject project, BHProjectDataExchangeDialog exportDialog);	

	/**
	 * Must be implemented if export of a project DTO with results from a
	 * deterministic procedure should be supported by the implementing plug-in.
	 * 
	 * @param project
	 */
	void exportProjectResults(DTOProject project,
			Map<String, Calculable[]> results);

	/**
	 * Must be implemented if export of a project DTO with results from a
	 * stochastic procedure should be supported by the implementing plug-in.
	 * 
	 * @param project
	 */
	void exportProjectResults(DTOProject project, DistributionMap results);

	/**
	 * Must be implemented if import of projects should be supported  by the
	 * implementing plug-in.
	 * 
	 * @return an instance of DTOProject
	 */
	DTOProject importProject();

	
	/**
	 * Must be implemented if export of a scenario DTO should be supported by the
	 * implementing plug-in.
	 * 
	 * @param scenario
	 */
	void exportScenario(DTOScenario scenario);

	/**
	 * Must be implemented if export of a scenario DTO with results from a
	 * deterministic procedure should be supported by the implementing plug-in.
	 * 
	 * @param scenario
	 */
	void exportScenarioResults(DTOScenario scenario,
			Map<String, Calculable[]> results);
	
	/**
	 * Must be implemented if export of a scenario DTO with results from a
	 * stochastic procedure should be supported by the implementing plug-in.
	 * 
	 * @param scenario
	 */
	void exportScenarioResults(DTOScenario scenario, DistributionMap results);

	/**
	 * Must be implemented if import of scenarios should be supported  by the
	 * implementing plug-in.
	 * 
	 * @return an instance of DTOScenario
	 */
	DTOScenario importScenario();

	/**
	 * Must return the sum of supported method bitmap.
	 * @return int as bitmap of supported methodes
	 */
	int getSupportedMethods();

	String getUniqueId();
	
}
