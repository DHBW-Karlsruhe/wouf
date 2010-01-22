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
import org.bh.data.IPeriodicalValuesDTO;
import org.bh.data.types.Calculable;
import org.bh.data.types.DistributionMap;
import org.bh.gui.swing.BHDataExchangeDialog;
import org.bh.gui.swing.BHDefaultGCCImportExportPanel;
import org.bh.gui.swing.IBHComponent;
import org.bh.platform.IImportExport;
import org.bh.platform.PlatformUserDialog;
import org.bh.platform.i18n.BHTranslator;
import org.bh.plugin.xmldataexchange.xmlimport.XMLNotValidException;
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
		importPanel = importDialog.setDefaultImportExportPanel(FILE_DESC, FILE_EXT, false);
		importDialog.setPluginActionListener(this);
		
		importDialog.setSize((int)importDialog.getSize().getWidth(), 280);
		return null;
	}	

	@Override
	public List<IPeriodicalValuesDTO> importBSAndPLSTotalCost(
			BHDataExchangeDialog importDialog) {
		this.importDialog = importDialog;
		importPanel = importDialog.setDefaultImportExportPanel(FILE_DESC, FILE_EXT, false);
		importDialog.setPluginActionListener(this);
		
		importDialog.setSize((int)importDialog.getSize().getWidth(), 280);
		return null;
	}
	
	@Override
	public void exportBSAndPLSCostOfSales(List<IPeriodicalValuesDTO> model,
			BHDataExchangeDialog importDialog) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public void exportBSAndPLSTotalCost(List<IPeriodicalValuesDTO> model,
			BHDataExchangeDialog importDialog) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}
	
	
	@Override
	public IPeriodicalValuesDTO importBalanceSheet(
			BHDataExchangeDialog importDialog) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}
	
	@Override
	public IPeriodicalValuesDTO importPLSCostOfSales(
			BHDataExchangeDialog importDialog) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}


	@Override
	public IPeriodicalValuesDTO importPLSTotalCost(
			BHDataExchangeDialog importDialog) {
		// TODO Auto-generated method stub
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
				importPanel = importDialog.setDefaultImportExportPanel(FILE_DESC, FILE_EXT, false);
				importDialog.showPluginPanel();
			}
			catch (IOException e1) {
				JOptionPane.showMessageDialog(importDialog, BHTranslator.getInstance().translate("DImportFileError"),
						BHTranslator.getInstance().translate("DXMLProjectImport"),
						JOptionPane.WARNING_MESSAGE);
				importPanel = importDialog.setDefaultImportExportPanel(FILE_DESC, FILE_EXT, false);
				importDialog.showPluginPanel();
			}		
		}
	}
	
	
	
	// [start]
	
	@Override
	public void exportProject(DTOProject project,
			BHDataExchangeDialog exportDialog) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void exportProject(DTOProject project, String filePath) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void exportProjectResults(DTOProject project,
			Map<String, Calculable[]> results,
			BHDataExchangeDialog bhDataExchangeDialog) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void exportProjectResults(DTOProject project,
			Map<String, Calculable[]> results, String filePath) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void exportProjectResults(DTOProject project,
			DistributionMap results, BHDataExchangeDialog bhDataExchangeDialog) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void exportProjectResults(DTOProject project,
			DistributionMap results, String filePath) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void exportScenario(DTOScenario scenario,
			BHDataExchangeDialog bhDataExchangeDialog) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void exportScenario(DTOScenario scenario, String filePath) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void exportScenarioResults(DTOScenario scenario,
			Map<String, Calculable[]> results, List<JFreeChart> charts,
			BHDataExchangeDialog bhDataExchangeDialog) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void exportScenarioResults(DTOScenario scenario,
			Map<String, Calculable[]> results, List<JFreeChart> charts,
			String filePath) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void exportScenarioResults(DTOScenario scenario,
			DistributionMap results, List<JFreeChart> charts,
			BHDataExchangeDialog bhDataExchangeDialog) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void exportScenarioResults(DTOScenario scenario,
			DistributionMap results, List<JFreeChart> charts, String filePath) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}	

	@Override
	public DTOProject importProject(BHDataExchangeDialog importDialog) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public DTOScenario importScenario(BHDataExchangeDialog importDialog) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}
	// [end]	

	

	@Override
	public void exportBalanceSheet(IPeriodicalValuesDTO model,
			BHDataExchangeDialog exportDialog) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public void exportPLSCostOfSales(IPeriodicalValuesDTO model,
			BHDataExchangeDialog exportDialog) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public void exportPLSTotalCost(IPeriodicalValuesDTO model,
			BHDataExchangeDialog exportDialog) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	

	
}
