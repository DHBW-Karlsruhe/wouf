/*******************************************************************************
 * Copyright 2011: Matthias Beste, Hannes Bischoff, Lisa Doerner, Victor Guettler, Markus Hattenbach, Tim Herzenstiel, Günter Hesse, Jochen Hülß, Daniel Krauth, Lukas Lochner, Mark Maltring, Sven Mayer, Benedikt Nees, Alexandre Pereira, Patrick Pfaff, Yannick Rödl, Denis Roster, Sebastian Schumacher, Norman Vogel, Simon Weber * : Anna Aichinger, Damian Berle, Patrick Dahl, Lisa Engelmann, Patrick Groß, Irene Ihl, Timo Klein, Alena Lang, Miriam Leuthold, Lukas Maciolek, Patrick Maisel, Vito Masiello, Moritz Olf, Ruben Reichle, Alexander Rupp, Daniel Schäfer, Simon Waldraff, Matthias Wurdig, Andreas Wußler
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
package org.bh.plugin.excelexport;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.bh.data.DTOProject;
import org.bh.data.DTOScenario;
import org.bh.data.IDTO;
import org.bh.data.IPeriodicalValuesDTO;
import org.bh.data.types.Calculable;
import org.bh.data.types.DistributionMap;
import org.bh.gui.IBHComponent;
import org.bh.gui.swing.BHOptionPane;
import org.bh.gui.swing.importexport.BHDataExchangeDialog;
import org.bh.gui.swing.importexport.BHDefaultScenarioExportPanel;
import org.bh.platform.IImportExport;
import org.bh.platform.i18n.BHTranslator;
import org.bh.platform.i18n.ITranslator;
import org.jfree.chart.JFreeChart;

/**
 * Plug-in to export certain scenarios to Excel as a kind of report
 * 
 * @author Norman
 * @version 1.0, 16.01.2010
 * @update Vito Masiello 05.01.2011
 * @version 1.1
 * @comment Anpassung für die XLS Ausgabe
 * 
 */
public class ExcelExport implements IImportExport {
	/**
	 * Logger for this class
	 */
	static final Logger log = Logger.getLogger(ExcelExport.class);
	ITranslator trans = BHTranslator.getInstance();

	private static final String UNIQUE_ID = "xls";
	private static final String GUI_KEY = "XLS";

	private static final String FILE_DESC = "XLS Workbook (Excel 2003)";
	private static final String FILE_EXT = "xls";

	public enum Keys {
		OVERWRITE, OVERWRITETITLE, NOWRITE, NOWRITETITLE;

		@Override
		public String toString() {
			return getClass().getName() + "." + super.toString();
		}
	}

	XSSFDocumentBuilder db = new XSSFDocumentBuilder();

	@Override
	public void exportProject(DTOProject project,
			BHDataExchangeDialog exportDialog) {
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void exportProject(DTOProject project, String filePath) {
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void exportProjectResults(DTOProject project,
			Map<String, Calculable[]> results,
			BHDataExchangeDialog bhDataExchangeDialog) {
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void exportProjectResults(DTOProject project,
			Map<String, Calculable[]> results, String filePath) {
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void exportProjectResults(DTOProject project,
			DistributionMap results, BHDataExchangeDialog bhDataExchangeDialog) {
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void exportProjectResults(DTOProject project,
			DistributionMap results, String filePath) {
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void exportScenario(final DTOScenario scenario,
			final BHDataExchangeDialog exportDialog) {

		final BHDefaultScenarioExportPanel dp = exportDialog
				.setDefaulExportScenarioPanel(FILE_DESC, FILE_EXT);
		exportDialog.pack();

		exportDialog.setPluginActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				IBHComponent comp = (IBHComponent) e.getSource();
				if (comp.getKey().equals("Mexport")) {
					if (!checkFile(dp.getFilePath(), exportDialog)) {
						return;
					}
					db.newDocument();
					db.buildScenarioSheet(scenario);
					db.buildPeriodSheet(scenario);
					db.closeDocument(dp.getFilePath());
					log.debug("excel export completed " + dp.getFilePath());
					if (dp.openAfterExport()) {
						try {
							Desktop.getDesktop().open(
									new File(dp.getFilePath()));
						} catch (IOException e1) {
							log.error(e1);
						}
					}
					exportDialog.dispose();
				}
				if (comp.getKey().equals("Bcancel")) {
					exportDialog.dispose();
				}
			}
		});
	}

	@Override
	public void exportScenario(DTOScenario scenario, String filePath) {
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void exportScenarioResults(final DTOScenario scenario,
			final Map<String, Calculable[]> results, List<JFreeChart> charts,
			final BHDataExchangeDialog exportDialog) {

		final BHDefaultScenarioExportPanel dp = exportDialog
				.setDefaulExportScenarioPanel(FILE_DESC, FILE_EXT);
		exportDialog.pack();

		exportDialog.setPluginActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				IBHComponent comp = (IBHComponent) e.getSource();
				if (comp.getKey().equals("Mexport")) {
					if (!checkFile(dp.getFilePath(), exportDialog)) {
						return;
					}
					db.newDocument();
					db.buildScenarioSheet(scenario);
					db.buildPeriodSheet(scenario);
					db.buildResultSheet(results);
					db.closeDocument(dp.getFilePath());
					log.debug("excel export completed " + dp.getFilePath());
					if (dp.openAfterExport()) {
						try {
							Desktop.getDesktop().open(
									new File(dp.getFilePath()));
						} catch (IOException e1) {
							log.error(e1);
						}
					}
					exportDialog.dispose();
				}
				if (comp.getKey().equals("Bcancel")) {
					exportDialog.dispose();
				}
			}
		});
	}

	@Override
	public void exportScenarioResults(DTOScenario scenario,
			Map<String, Calculable[]> results, List<JFreeChart> charts,
			String filePath) {
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void exportScenarioResults(final DTOScenario scenario,
			final DistributionMap results, List<JFreeChart> charts,
			final BHDataExchangeDialog exportDialog) {

		final BHDefaultScenarioExportPanel dp = exportDialog
				.setDefaulExportScenarioPanel(FILE_DESC, FILE_EXT);
		exportDialog.pack();

		exportDialog.setPluginActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				IBHComponent comp = (IBHComponent) e.getSource();
				if (comp.getKey().equals("Mexport")) {
					if (!checkFile(dp.getFilePath(), exportDialog)) {
						return;
					}
					db.newDocument();
					db.buildScenarioSheet(scenario);
					db.buildPeriodSheet(scenario);
					db.buildResultSheet(results);
					db.closeDocument(dp.getFilePath());
					log.debug("excel export completed " + dp.getFilePath());
					if (dp.openAfterExport()) {
						try {
							Desktop.getDesktop().open(
									new File(dp.getFilePath()));
						} catch (IOException e1) {
							log.error(e1);
						}
					}
					exportDialog.dispose();
				}
				if (comp.getKey().equals("Bcancel")) {
					exportDialog.dispose();
				}
			}
		});
	}

	@Override
	public void exportScenarioResults(DTOScenario scenario,
			DistributionMap results, List<JFreeChart> charts, String filePath) {
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public String getFileDescription() {
		return FILE_DESC;
	}

	@Override
	public String getFileExtension() {
		return FILE_EXT;
	}

	@Override
	public int getSupportedMethods() {
		return IImportExport.EXP_SCENARIO + IImportExport.EXP_SCENARIO_RES;
	}

	@Override
	public String getUniqueId() {
		return UNIQUE_ID;
	}

	@Override
	public DTOProject importProject(BHDataExchangeDialog importDialog) {
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public DTOScenario importScenario(BHDataExchangeDialog importDialog) {
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public String getGuiKey() {
		return GUI_KEY;
	}

	@Override
	public String toString() {
		return FILE_EXT + " - " + FILE_DESC;
	}

	protected boolean checkFile(String filePath, Component parent) {
		File f = new File(filePath);
		if (f.exists()) {
			if (!f.canWrite()) {
				JOptionPane.showMessageDialog(parent, trans
						.translate(Keys.NOWRITE), trans
						.translate(Keys.NOWRITETITLE),
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
			int res = BHOptionPane.showConfirmDialog(parent, trans
					.translate(Keys.OVERWRITE), trans
					.translate(Keys.OVERWRITETITLE), JOptionPane.YES_NO_OPTION);
			if (res == JOptionPane.YES_OPTION) {
				return true;
			}
			return false;
		}
		// f does not exist
		try {
			f.createNewFile();
		} catch (IOException e) {
			// no write rights
			JOptionPane.showMessageDialog(parent,
					trans.translate(Keys.NOWRITE), trans
							.translate(Keys.NOWRITETITLE),
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		// can write
		f.delete();
		return true;
	}

	@Override
	public IPeriodicalValuesDTO importBalanceSheet(
			BHDataExchangeDialog importDialog) {
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public IPeriodicalValuesDTO importPLSCostOfSales(
			BHDataExchangeDialog importDialog) {
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public IPeriodicalValuesDTO importPLSTotalCost(
			BHDataExchangeDialog importDialog) {
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public List<IPeriodicalValuesDTO> importBSAndPLSCostOfSales(
			BHDataExchangeDialog importDialog) {
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public List<IPeriodicalValuesDTO> importBSAndPLSTotalCost(
			BHDataExchangeDialog importDialog) {
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public void exportBSAndPLSCostOfSales(List<IPeriodicalValuesDTO> model,
			BHDataExchangeDialog importDialog) {
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public void exportBSAndPLSTotalCost(List<IPeriodicalValuesDTO> model,
			BHDataExchangeDialog importDialog) {
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public void exportBalanceSheet(IPeriodicalValuesDTO model,
			BHDataExchangeDialog exportDialog) {
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public void exportPLSCostOfSales(IPeriodicalValuesDTO model,
			BHDataExchangeDialog exportDialog) {
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public void exportPLSTotalCost(IPeriodicalValuesDTO model,
			BHDataExchangeDialog exportDialog) {
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public void setFile(String filename) {
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public IDTO<?> startImport() throws IOException, RuntimeException {
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	/* Specified by interface/super class. */
	@Override
	public void setFileAndModel(String filename, IDTO<?> model) {
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	/* Specified by interface/super class. */
	@Override
	public boolean startExport() throws IOException {
		throw new UnsupportedOperationException("This method has not been implemented");
	}

}
