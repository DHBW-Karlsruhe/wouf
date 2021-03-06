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

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.bh.data.DTOProject;
import org.bh.data.DTOScenario;
import org.bh.data.IDTO;
import org.bh.data.IPeriodicalValuesDTO;
import org.bh.data.types.Calculable;
import org.bh.data.types.DistributionMap;
import org.bh.gui.swing.importexport.BHDataExchangeDialog;
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
	
	static final int IMP_BALANCE_SHEET = 1 << 7;
	/**
	 * Import profit and loss statement (total cost)
	 */
	static final int IMP_PLS_TOTAL_COST = 1 << 8;
	
	/**
	 * Import profit and loss statement (cost of sales)
	 */
	static final int IMP_PLS_COST_OF_SALES = 1 << 9;
	
	static final int EXP_BALANCE_SHEET = 1 << 10;
	
	static final int EXP_PLS_TOTAL_COST = 1 << 11;
	
	static final int EXP_PLS_COST_OF_SALES = 1 << 12;
	
	
	IPeriodicalValuesDTO importBalanceSheet(final BHDataExchangeDialog importDialog);
	IPeriodicalValuesDTO importPLSTotalCost(final BHDataExchangeDialog importDialog);
	IPeriodicalValuesDTO importPLSCostOfSales(final BHDataExchangeDialog importDialog);
	
	List<IPeriodicalValuesDTO> importBSAndPLSTotalCost(final BHDataExchangeDialog importDialog);
	List<IPeriodicalValuesDTO> importBSAndPLSCostOfSales(final BHDataExchangeDialog importDialog);
	
	void exportBalanceSheet(final IPeriodicalValuesDTO model, final BHDataExchangeDialog exportDialog);
	void exportPLSTotalCost(final IPeriodicalValuesDTO model, final BHDataExchangeDialog exportDialog);
	void exportPLSCostOfSales(final IPeriodicalValuesDTO model, final BHDataExchangeDialog exportDialog);
	
	void exportBSAndPLSTotalCost(final List<IPeriodicalValuesDTO> model, final BHDataExchangeDialog importDialog);
	void exportBSAndPLSCostOfSales(final List<IPeriodicalValuesDTO> model, final BHDataExchangeDialog importDialog);
	

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
	
	/**
	 * Do not use!!!
	 * @param filename
	 */
	void setFile(String filename);
	
	/**
	 * Do not use!!!
	 * @return
	 * @throws IOException
	 * @throws RuntimeException
	 */
	IDTO<?> startImport() throws IOException, RuntimeException;

	/**
	 * Do not use!!!
	 * @param filename Name of file to write in
	 * @param model DTO to export
	 */
	void setFileAndModel(String filename, IDTO<?> model);
	
	/**
	 * Starts export of DTO into file.
	 * @return true if everything went well
	 * @throws IOException
	 */
	boolean startExport() throws IOException;
}
