package org.bh.platform;

import java.util.List;
import java.util.Map;

import org.bh.data.DTOProject;
import org.bh.data.DTOScenario;
import org.bh.data.types.Calculable;
import org.bh.data.types.DistributionMap;
import org.bh.gui.swing.BHDataExchangeDialog;
import org.jfree.chart.JFreeChart;

/**
 * Interface for plugins which can import export DTOs to third party file
 * formats.
 * 
 * @author Norman
 * @version 1.0, 10.01.2010
 * 
 */
public interface IImportExport extends IDisplayablePlugin {

	static final int EXP_PROJECT = 1 << 0;
	static final int EXP_PROJECT_RES = 1 << 1;
	static final int IMP_PROJECT = 1 << 2;

	static final int EXP_SCENARIO = 1 << 3;
	static final int EXP_SCENARIO_RES = 1 << 4;
	static final int IMP_SCENARIO = 1 << 5;

	static final int BATCH_EXPORT = 1 << 6;

	/**
	 * Must be implemented if export of a project DTO should be supported by the
	 * implementing plug-in.
	 * 
	 * @param exportDialog
	 */

	void exportProject(final DTOProject project,
			final BHDataExchangeDialog exportDialog);

	void exportProject(final DTOProject project, final String filePath);

	/**
	 * Must be implemented if export of a project DTO with results from a
	 * deterministic procedure should be supported by the implementing plug-in.
	 * 
	 * @param project
	 */
	void exportProjectResults(final DTOProject project,
			final Map<String, Calculable[]> results,
			final BHDataExchangeDialog bhDataExchangeDialog);

	void exportProjectResults(final DTOProject project,
			final Map<String, Calculable[]> results, final String filePath);

	/**
	 * Must be implemented if export of a project DTO with results from a
	 * stochastic procedure should be supported by the implementing plug-in.
	 * 
	 * @param project
	 */
	void exportProjectResults(final DTOProject project,
			final DistributionMap results,
			final BHDataExchangeDialog bhDataExchangeDialog);

	void exportProjectResults(final DTOProject project,
			final DistributionMap results, final String filePath);

	/**
	 * Must be implemented if import of projects should be supported by the
	 * implementing plug-in.
	 * 
	 * @param importDialog
	 *            TODO
	 * @return an instance of DTOProject
	 */
	DTOProject importProject(final BHDataExchangeDialog importDialog);

	/**
	 * Must be implemented if export of a scenario DTO should be supported by
	 * the implementing plug-in.
	 * 
	 * @param exportDialog
	 */
	void exportScenario(final DTOScenario scenario,
			final BHDataExchangeDialog bhDataExchangeDialog);

	void exportScenario(final DTOScenario scenario, final String filePath);

	/**
	 * Must be implemented if export of a scenario DTO with results from a
	 * deterministic procedure should be supported by the implementing plug-in.
	 * 
	 * @param scenario
	 */
	void exportScenarioResults(final DTOScenario scenario,
			final Map<String, Calculable[]> results, List<JFreeChart> charts,
			final BHDataExchangeDialog bhDataExchangeDialog);

	void exportScenarioResults(final DTOScenario scenario,
			final Map<String, Calculable[]> results, List<JFreeChart> charts,
			final String filePath);

	/**
	 * Must be implemented if export of a scenario DTO with results from a
	 * stochastic procedure should be supported by the implementing plug-in.
	 * 
	 * @param scenario
	 */
	void exportScenarioResults(final DTOScenario scenario,
			final DistributionMap results, List<JFreeChart> charts,
			final BHDataExchangeDialog bhDataExchangeDialog);

	void exportScenarioResults(final DTOScenario scenario,
			final DistributionMap results, List<JFreeChart> charts,
			final String filePath);

	/**
	 * Must be implemented if import of scenarios should be supported by the
	 * implementing plug-in.
	 * 
	 * @param importDialog
	 *            TODO
	 * 
	 * @return an instance of DTOScenario
	 */
	DTOScenario importScenario(BHDataExchangeDialog importDialog);

	/**
	 * Must return the sum of supported method bitmap.
	 * 
	 * @return int as bitmap of supported methodes
	 */
	int getSupportedMethods();

	String getFileExtension();

	String getFileDescription();

	String getUniqueId();

}
