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
package org.bh.plugin.gcc.xbrlimport;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.bh.data.DTOProject;
import org.bh.data.DTOScenario;
import org.bh.data.IDTO;
import org.bh.data.IPeriodicalValuesDTO;
import org.bh.data.types.Calculable;
import org.bh.data.types.DistributionMap;
import org.bh.gui.IBHComponent;
import org.bh.gui.swing.importexport.BHDataExchangeDialog;
import org.bh.gui.swing.importexport.BHDefaultGCCImportExportPanel;
import org.bh.platform.IImportExport;
import org.bh.platform.i18n.BHTranslator;
import org.jfree.chart.JFreeChart;

public class XBRLImportController implements IImportExport, ActionListener {

	
	private static final String UNIQUE_ID = "XBRL";
	
	private static final String FILE_EXT = "xml";

	private static final String FILE_DESC = "eXtensible Markup Language";
	
	private static String GUI_KEY = "DXBRLImport";

	private BHDefaultGCCImportExportPanel importPanel;

	private BHDataExchangeDialog importDialog;
	
	
	@Override
	public List<IPeriodicalValuesDTO> importBSAndPLSCostOfSales(
			BHDataExchangeDialog importDialog) {
		this.importDialog = importDialog;
		importPanel = importDialog.setDefaultGCCImportExportPanel(FILE_DESC, FILE_EXT, false);
		importPanel.setDescription("DGCCXBRLImportDescription");
		importDialog.setPluginActionListener(this);
		
		importDialog.setSize((int)importDialog.getSize().getWidth(), 280);
		return null;
	}	

	@Override
	public List<IPeriodicalValuesDTO> importBSAndPLSTotalCost(
			BHDataExchangeDialog importDialog) {
		this.importDialog = importDialog;
		importPanel = importDialog.setDefaultGCCImportExportPanel(FILE_DESC, FILE_EXT, false);
		importPanel.setDescription("DGCCXBRLImportDescription");
		importDialog.setPluginActionListener(this);
		
		importDialog.setSize((int)importDialog.getSize().getWidth(), 280);
		return null;
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
	public String getFileDescription() {
		return FILE_DESC;
	}

	@Override
	public String getFileExtension() {
		return FILE_EXT;
	}

	@Override
	public int getSupportedMethods() {
		return IImportExport.IMP_BALANCE_SHEET +
			IImportExport.IMP_PLS_COST_OF_SALES +
			IImportExport.IMP_PLS_TOTAL_COST;
	}

	@Override
	public String getUniqueId() {
		return UNIQUE_ID;
	}
	
	@Override
	public String getGuiKey() {
		return GUI_KEY;
	}	
	
	@Override
	public String toString() {
		return "XBRL - eXtensible Business Reporting Language";
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		IBHComponent comp =  (IBHComponent) e.getSource();	
		
		if (comp.getKey().equals("Mimport"))
		{
			 
			try {				
				List<IPeriodicalValuesDTO> importedObjects = new ArrayList<IPeriodicalValuesDTO>();
				boolean noBalanceSheet = false;
				boolean noProfitLossStatement = false;
				
				// Try to import a balance sheet
				try
				{
					IPeriodicalValuesDTO balanceSheet = XBRLImport.getInstance()
						.getBalanceSheetDTO(importPanel.getFilePath());
					importedObjects.add(balanceSheet);
				}
				catch (XBRLNoValueFoundException e1) {
					noBalanceSheet = true;
				} 		
				
				try
				{
					if ((importDialog.getAction() & IImportExport.IMP_PLS_COST_OF_SALES) == IImportExport.IMP_PLS_COST_OF_SALES)
					{
						IPeriodicalValuesDTO plsCostOfSales = XBRLImport.getInstance()
						.getProfitLossStatementCostOfSalesDTO(importPanel.getFilePath());
						importedObjects.add(plsCostOfSales);
					}
					else if ((importDialog.getAction() & IImportExport.IMP_PLS_TOTAL_COST) == IImportExport.IMP_PLS_TOTAL_COST)
					{
						IPeriodicalValuesDTO plsTotalCost = XBRLImport.getInstance()
						.getProfitLossStatementTotalCostDTO(importPanel.getFilePath());
						importedObjects.add(plsTotalCost);
					}
				}
				catch (XBRLNoValueFoundException e1) {
					noProfitLossStatement = true;
				} 
				
				if (noBalanceSheet && noProfitLossStatement)
				{
					JOptionPane.showMessageDialog(importDialog, BHTranslator.getInstance()
							.translate("DXBRLNoValuesFound"), BHTranslator.getInstance().translate("DXBRLImport"),
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				else if ((noBalanceSheet  && !noProfitLossStatement) ||
						(!noBalanceSheet && noProfitLossStatement))
				{
					JOptionPane.showMessageDialog(importDialog, BHTranslator.getInstance()
							.translate("DXBRLPartlyImported"), BHTranslator.getInstance().translate("DXBRLImport"),
							JOptionPane.WARNING_MESSAGE);
				}				
				importDialog.fireImportListener(importedObjects);
				importDialog.dispose();
			}
			catch (XMLNotValidException invalidE)
			{
				JOptionPane.showMessageDialog(importDialog, BHTranslator.getInstance().translate("DXMLNotValid"),
						BHTranslator.getInstance().translate("DXBRLImport"),
						JOptionPane.WARNING_MESSAGE);
				importPanel = importDialog.setDefaultGCCImportExportPanel(FILE_DESC, FILE_EXT, false);
				importDialog.showPluginPanel();
			}
			catch (IOException e1) {
				JOptionPane.showMessageDialog(importDialog, BHTranslator.getInstance().translate("DImportFileError"),
						BHTranslator.getInstance().translate("DXMLProjectImport"),
						JOptionPane.WARNING_MESSAGE);
				importPanel = importDialog.setDefaultGCCImportExportPanel(FILE_DESC, FILE_EXT, false);
				importDialog.showPluginPanel();
			}		
		}
	}
	
	
	
	// [start]
	
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
	public void exportScenario(DTOScenario scenario,
			BHDataExchangeDialog bhDataExchangeDialog) {
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void exportScenario(DTOScenario scenario, String filePath) {
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void exportScenarioResults(DTOScenario scenario,
			Map<String, Calculable[]> results, List<JFreeChart> charts,
			BHDataExchangeDialog bhDataExchangeDialog) {
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void exportScenarioResults(DTOScenario scenario,
			Map<String, Calculable[]> results, List<JFreeChart> charts,
			String filePath) {
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void exportScenarioResults(DTOScenario scenario,
			DistributionMap results, List<JFreeChart> charts,
			BHDataExchangeDialog bhDataExchangeDialog) {
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void exportScenarioResults(DTOScenario scenario,
			DistributionMap results, List<JFreeChart> charts, String filePath) {
		throw new UnsupportedOperationException(
				"This method has not been implemented");
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
	// [end]	

	

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

	/* Specified by interface/super class. */
	@Override
	public void setFile(String filename) {
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	/* Specified by interface/super class. */
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
