package org.bh.plugin.xmldataexchange;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.bh.data.DTOProject;
import org.bh.data.DTOScenario;
import org.bh.data.IDTO;
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

public class XMLDataExchangeController implements IImportExport, ActionListener {
		
	private IDTO<?> exportModel = null;
	private IDTO<?> importModel = null;
	
	private static String GUI_KEY = "xmldataexchange";
	
	private static final String GUI_KEY_IMPORT = "DXMLProjectImport";
	private static final String GUI_KEY_EXPORT = "DXMLProjectExport";

	private static final String UNIQUE_ID = "XML";
	
	
	private BHDefaultProjectExportPanel exportPanel = null;
	
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
			DTOProject cloneProject = (DTOProject) exportModel.clone();
			cloneProject.removeAllChildren();
			for (Object sec : exportPanel.getSecList().getSelectedItems())
			{				
				cloneProject.addChild((DTOScenario) sec);
			}			
			
			if (!exportPanel.getTxtPath().getText().equals(""))
				try {
					boolean result = new XMLExport(exportPanel.getTxtPath().getText(), cloneProject).startExport();
					if (result)
					{
						String msg = BHTranslator.getInstance().translate("DExportSuccessfull");
						msg = msg.replace("[PATH]", exportPanel.getTxtPath().getText());
						JOptionPane.showMessageDialog(exportDialog, msg,
								BHTranslator.getInstance().translate("DProjectExport"),
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
					JOptionPane.showMessageDialog(exportDialog, BHTranslator.getInstance().translate("DXMLExportFileError"),
							BHTranslator.getInstance().translate("DProjectExport"),
							JOptionPane.WARNING_MESSAGE);
				}
		}		
		else if (comp.getKey().equals("Mimport"))
		{
			importModel.removeAllChildren();
			for (Object sec : importPanel.getSecList().getSelectedItems())
				((DTOProject)importModel).addChild((DTOScenario) sec);
			PlatformController.getInstance().addProject((DTOProject) importModel);			
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
						importPanel.getSecList().setModel(importModel.getChildren().toArray());	
						importPanel.getBtnImport().setEnabled(true);
					}
					else
					{
						// TODO Katzor.Marcus
					}
					
				} catch (XMLNotValidException e1) {
					
				} catch (IOException e1) {
					
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
		exportPanel = exportDialog.setDefaulExportProjectPanel();
		exportDialog.setPluginActionListener(this);
		this.exportDialog = exportDialog;
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
			Map<String, Calculable[]> results, BHDataExchangeDialog exportDialog) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}


	@Override
	public void exportScenarioResults(DTOScenario scenario,
			DistributionMap results, BHDataExchangeDialog exportDialog) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}


	@Override
	public int getSupportedMethods() {
		return 	IImportExport.EXP_PROJECT +
				IImportExport.IMP_PROJECT;
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
	public void exportScenarioResults(DTOScenario scenario,
			Map<String, Calculable[]> results, String filePath) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}


	@Override
	public void exportScenarioResults(DTOScenario scenario,
			DistributionMap results, String filePath) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}


	@Override
	public String getFileDescription() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}


	@Override
	public String getFileExtension() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}


	


	
}
