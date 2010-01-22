package org.bh.plugin.xmldataexchange;

import org.apache.log4j.Logger;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.bh.data.DTO;
import org.bh.data.DTOProject;
import org.bh.data.DTOScenario;
import org.bh.data.IDTO;
import org.bh.data.IPeriodicalValuesDTO;
import org.bh.data.types.Calculable;
import org.bh.data.types.DistributionMap;
import org.bh.gui.swing.BHDataExchangeDialog;
import org.bh.gui.swing.BHDefaultProjectExportPanel;
import org.bh.gui.swing.BHDefaultProjectImportPanel;
import org.bh.gui.swing.IBHComponent;
import org.bh.platform.IImportExport;
import org.bh.platform.PlatformController;
import org.bh.platform.i18n.BHTranslator;
import org.bh.plugin.xmldataexchange.xmlexport.XMLExport;
import org.bh.plugin.xmldataexchange.xmlimport.XMLImport;
import org.bh.plugin.xmldataexchange.xmlimport.XMLNotValidException;
import org.jfree.chart.JFreeChart;

public class XMLDataExchangeController implements IImportExport, ActionListener {
	/**
	 * Logger for this class
	 */
	private static final Logger log = Logger.getLogger(XMLDataExchangeController.class);
		
	private Object exportModel = null;
	private Object importModel = null;
	
	private static String GUI_KEY = "xmldataexchange";
	
	private static final String GUI_KEY_IMPORT = "DXMLProjectImport";
	private static final String GUI_KEY_EXPORT = "DXMLProjectExport";

	private static final String UNIQUE_ID = "XML";

	private static final String FILE_EXT = "xml";

	private static final String FILE_DESC = "eXtensible Markup Language";
	
	
	private Object exportPanel = null;
	
	private Container container = null;

	private BHDataExchangeDialog exportDialog;

	private BHDefaultProjectImportPanel importPanel;

	private BHDataExchangeDialog importDialog;
	
	public XMLDataExchangeController() {
		
	}	

	@Override
	public void actionPerformed(ActionEvent e) {
		IBHComponent comp =  (IBHComponent) e.getSource();	
		
		if (comp.getKey().equals("Mexport"))
		{
			if ((exportDialog.getAction() & IImportExport.EXP_PROJECT) == IImportExport.EXP_PROJECT)
			{
				DTOProject cloneProject = (DTOProject) ((DTOProject) exportModel).clone();
				cloneProject.removeAllChildren();
				for (Object sec : ((BHDefaultProjectExportPanel) exportPanel).getSecList().getSelectedItems())
				{				
					cloneProject.addChild((DTOScenario) sec);
				}			
				
				if (!((BHDefaultProjectExportPanel) exportPanel).getTxtPath().getText().equals(""))
				{
					try {
						boolean result = new XMLExport(((BHDefaultProjectExportPanel) exportPanel).getTxtPath().getText(), cloneProject).startExport();
						if (result)
						{ 
							String msg = BHTranslator.getInstance().translate("DExportSuccessfull");
							msg = msg.replace("[PATH]", ((BHDefaultProjectExportPanel) exportPanel).getTxtPath().getText());
							JOptionPane.showMessageDialog(exportDialog, msg,
									BHTranslator.getInstance().translate("DXMLProjectExport"),
									JOptionPane.INFORMATION_MESSAGE);
							exportDialog.dispose();
						}
						else
						{
							JOptionPane.showMessageDialog(exportDialog, BHTranslator.getInstance().translate("DXMLExportError"),
									BHTranslator.getInstance().translate("DProjectExport"),
									JOptionPane.ERROR_MESSAGE);
						}
						
					} catch (IOException e1) {
						log.debug(e1);
						JOptionPane.showMessageDialog(exportDialog, BHTranslator.getInstance().translate("DXMLExportFileError"),
								BHTranslator.getInstance().translate("DProjectExport"),
								JOptionPane.WARNING_MESSAGE);
					}
				}
			}
			else if ((exportDialog.getAction() & IImportExport.EXP_BALANCE_SHEET) == IImportExport.EXP_BALANCE_SHEET)				
			{
				
			}
		}		
		else if (comp.getKey().equals("Mimport"))
		{
			((DTO<DTOScenario>) importModel).removeAllChildren();
			for (Object sec : importPanel.getSecList().getSelectedItems())
				((DTOProject)importModel).addChild((DTOScenario) sec);
			PlatformController.getInstance().addProject((DTOProject) importModel);	
			
			//TODO Marcus -- check Norman added
			importDialog.dispose();
		}		
		else if (comp.getKey().equals("Bbrowse") && GUI_KEY.equals(GUI_KEY_IMPORT))
		{
			
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			
			String strDefDir = PlatformController.preferences.get("lastExportDirectory", null);
			if (strDefDir != null)
			{
				File defDir = new File(strDefDir);
				fileChooser.setCurrentDirectory(defDir);
			}		
			
			String descr = BHTranslator.getInstance().translate("DXMLFileDescription");
			String ext = BHTranslator.getInstance().translate("DXMLFileExtension");			
			fileChooser.setFileFilter(new FileNameExtensionFilter(descr, ext));
			
			int returnVal = fileChooser.showOpenDialog(importDialog);		
			
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{			
				try {
					importModel = new XMLImport(fileChooser.getSelectedFile().getPath()).startImport();
					if (importModel != null)
					{					
						importPanel.getSecList().setModel(((DTO<DTOScenario>) importModel).getChildren().toArray());	
						importPanel.getBtnImport().setEnabled(true);
					}
					else
					{
						JOptionPane.showMessageDialog(importDialog, BHTranslator.getInstance().translate("DImportError"),
								BHTranslator.getInstance().translate("DXMLProjectImport"),
								JOptionPane.ERROR_MESSAGE);
						importPanel = importDialog.setDefaultImportProjectPanel();
						importDialog.showPluginPanel();
					}
					
				} catch (XMLNotValidException e1) {
					JOptionPane.showMessageDialog(importDialog, BHTranslator.getInstance().translate("DXMLNotValid"),
							BHTranslator.getInstance().translate("DXMLProjectImport"),
							JOptionPane.WARNING_MESSAGE);
					importPanel = importDialog.setDefaultImportProjectPanel();
					importDialog.showPluginPanel();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(importDialog, BHTranslator.getInstance().translate("DImportFileError"),
							BHTranslator.getInstance().translate("DXMLProjectImport"),
							JOptionPane.WARNING_MESSAGE);
					importPanel = importDialog.setDefaultImportProjectPanel();
					importDialog.showPluginPanel();
				}				
				
				PlatformController.preferences.put("lastImportDirectory", fileChooser.getSelectedFile().getParent()); 
				importPanel.getTxtPath().setText(fileChooser.getSelectedFile().getPath());			
			}
			
			
		}
		
	}
	
	
	@Override
	public void exportProject(DTOProject project,
			BHDataExchangeDialog exportDialog) {
		GUI_KEY = GUI_KEY_EXPORT;
		exportModel = project;
		exportPanel = exportDialog.setDefaulExportProjectPanel(FILE_DESC, FILE_EXT);
		exportDialog.setPluginActionListener(this);
		this.exportDialog = exportDialog;
	}
	
	@Override
	public void exportBSAndPLSCostOfSales(List<IPeriodicalValuesDTO> model,
			BHDataExchangeDialog importDialog) {
		GUI_KEY = GUI_KEY_EXPORT;
		this.exportDialog = importDialog;
		exportModel = model;
		exportPanel = importDialog.setDefaultImportExportPanel(FILE_DESC, FILE_EXT, true);
		exportDialog.setPluginActionListener(this);		
	}


	@Override
	public DTOProject importProject(BHDataExchangeDialog importDialog) {
		GUI_KEY = GUI_KEY_IMPORT;		
		importPanel = importDialog.setDefaultImportProjectPanel();
		importDialog.setPluginActionListener(this);
		this.importDialog = importDialog;
		return null;
	}


	@Override
	public String getGuiKey() {
		return GUI_KEY;
	}

	@Override
	public void exportProjectResults(DTOProject project,
			Map<String, Calculable[]> results, BHDataExchangeDialog exportDialog) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}


	@Override
	public void exportProjectResults(DTOProject project, DistributionMap results, BHDataExchangeDialog exportDialog) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}


	@Override

	public void exportScenario(DTOScenario scenario, BHDataExchangeDialog exportDialog) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}


	@Override
	public void exportScenarioResults(DTOScenario scenario,
			Map<String, Calculable[]> results, List<JFreeChart> charts, BHDataExchangeDialog exportDialog) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public int getSupportedMethods() {
		return 	IImportExport.EXP_PROJECT +
				IImportExport.IMP_PROJECT +
				IImportExport.EXP_BALANCE_SHEET +
				IImportExport.EXP_PLS_COST_OF_SALES +
				IImportExport.EXP_PLS_TOTAL_COST;
	}


	@Override
	public String getUniqueId() {
		return UNIQUE_ID;
	}
	
	@Override
	public String toString() {
		return "XML - Extensible Markup Language";
	}

	@Override
	public DTOScenario importScenario(BHDataExchangeDialog importDialog) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public void exportProject(DTOProject project, String filePath) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}


	@Override
	public void exportProjectResults(DTOProject project,
			Map<String, Calculable[]> results, String filePath) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}


	@Override
	public void exportProjectResults(DTOProject project,
			DistributionMap results, String filePath) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}


	@Override
	public void exportScenario(DTOScenario scenario, String filePath) {
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
	public void exportScenarioResults(DTOScenario scenario,
			Map<String, Calculable[]> results, List<JFreeChart> charts,
			String filePath) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public void exportScenarioResults(DTOScenario scenario,
			DistributionMap results, List<JFreeChart> charts,
			BHDataExchangeDialog bhDataExchangeDialog) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public void exportScenarioResults(DTOScenario scenario,
			DistributionMap results, List<JFreeChart> charts, String filePath) {
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
	public List<IPeriodicalValuesDTO> importBSAndPLSCostOfSales(
			BHDataExchangeDialog importDialog) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public List<IPeriodicalValuesDTO> importBSAndPLSTotalCost(
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
