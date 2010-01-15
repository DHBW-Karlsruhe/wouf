package org.bh.plugin.xmldataexchange;

import java.awt.Container;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.bh.data.DTOProject;
import org.bh.data.DTOScenario;
import org.bh.data.IDTO;
import org.bh.data.types.Calculable;
import org.bh.data.types.DistributionMap;
import org.bh.gui.swing.BHDataExchangeDialog;
import org.bh.gui.swing.BHDefaultProjectExportPanel;
import org.bh.gui.swing.IBHComponent;
import org.bh.platform.IImportExport;
import org.bh.platform.PlatformController;
import org.bh.platform.i18n.BHTranslator;
import org.bh.plugin.xmldataexchange.xmlexport.XMLExport;
import org.bh.plugin.xmldataexchange.xmlexport.XMLProjectExportPanel;
import org.bh.plugin.xmldataexchange.xmlimport.XMLImport;
import org.bh.plugin.xmldataexchange.xmlimport.XMLNotValidException;
import org.bh.plugin.xmldataexchange.xmlimport.XMLProjectImportPanel;

public class XMLDataExchangeController implements IImportExport, ActionListener {
		
	private IDTO<?> model = null;
	
	private static String GUI_KEY = "xmldataexchange";
	private static final String dataFormat = "XML";
	

	private static final String UNIQUE_ID = "XML";
	
	
	
	private BHDefaultProjectExportPanel exportPanel = null;
	
	private Container container = null;

	private BHDataExchangeDialog exportDialog;
	
	public XMLDataExchangeController() {
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		IBHComponent comp =  (IBHComponent) e.getSource();	
		
		if (comp.getKey().equals("Mexport"))
		{
			DTOProject cloneProject = (DTOProject) model.clone();
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
						String msg = BHTranslator.getInstance().translate("DXMLExportSuccessfull");
						msg = msg.replace("[PATH]", exportPanel.getTxtPath().getText());
						JOptionPane.showMessageDialog(container, msg,
								BHTranslator.getInstance().translate(XMLProjectExportPanel.KEY),
								JOptionPane.INFORMATION_MESSAGE);
						exportDialog.dispose();
					}
					else
					{
						JOptionPane.showMessageDialog(container, BHTranslator.getInstance().translate("DXMLExportError"),
								BHTranslator.getInstance().translate(XMLProjectExportPanel.KEY),
								JOptionPane.ERROR_MESSAGE);
					}
					
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(container, BHTranslator.getInstance().translate("DXMLExportFileError"),
							BHTranslator.getInstance().translate(XMLProjectExportPanel.KEY),
							JOptionPane.WARNING_MESSAGE);
				}
		}		
		
		
		/*if (GUI_KEY.equals(XMLProjectExportPanel.KEY))
		{
			XMLProjectExportPanel projExportPanel = (XMLProjectExportPanel) exportPanels.get(XMLProjectExportPanel.KEY);
			if (comp.getKey().equals("Bbrowse"))
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
				
				int returnVal = fileChooser.showSaveDialog(projExportPanel);		
				
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					PlatformController.preferences.put("lastExportDirectory", fileChooser.getSelectedFile().getParent()); 
					projExportPanel.getTxtPath().setText(fileChooser.getSelectedFile().getPath());			
				}
				
			}
			else if (comp.getKey().equals("Mexport"))
			{
				DTOProject cloneProject = (DTOProject) model.get(0).clone();
				cloneProject.removeAllChildren();
				for (Object sec : projExportPanel.getSecList().getSelectedScenario())
				{				
					cloneProject.addChild((DTOScenario) sec);
				}			
				
				if (!projExportPanel.getTxtPath().getText().equals(""))
					try {
						boolean result = new XMLExport(projExportPanel.getTxtPath().getText(), cloneProject).startExport();
						if (result)
						{
							String msg = BHTranslator.getInstance().translate("DXMLExportSuccessfull");
							msg = msg.replace("[PATH]", projExportPanel.getTxtPath().getText());
							JOptionPane.showMessageDialog(container, msg,
									BHTranslator.getInstance().translate(XMLProjectExportPanel.KEY),
									JOptionPane.INFORMATION_MESSAGE);
							closeContainingWindow();
						}
						else
						{
							JOptionPane.showMessageDialog(container, BHTranslator.getInstance().translate("DXMLExportError"),
									BHTranslator.getInstance().translate(XMLProjectExportPanel.KEY),
									JOptionPane.ERROR_MESSAGE);
						}
						
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(container, BHTranslator.getInstance().translate("DXMLExportFileError"),
								BHTranslator.getInstance().translate(XMLProjectExportPanel.KEY),
								JOptionPane.WARNING_MESSAGE);
					}
					
					
				
			}
		}
		else if (GUI_KEY.equals(XMLProjectImportPanel.KEY))
		{
			XMLProjectImportPanel projImportPanel = (XMLProjectImportPanel) importPanels.get(XMLProjectImportPanel.KEY);
			if (comp.getKey().equals("Bbrowse"))
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
				
				int returnVal = fileChooser.showOpenDialog(projImportPanel);		
				
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					try {
						IDTO<?> proj = new XMLImport(fileChooser.getSelectedFile().getPath()).startImport();
						if (proj != null)
						{
							model = proj;
							projImportPanel.getSecList().setModel(proj.getChildren().toArray());						
						}
						else
						{
							// TODO Katzor.Marcus
						}
						
					} catch (XMLNotValidException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					
					/*PlatformController.preferences.put("lastImportDirectory", fileChooser.getSelectedFile().getParent()); 
					projImportPanel.getTxtPath().setText(fileChooser.getSelectedFile().getPath());			
				}
				
			}
			else if (comp.getKey().equals("Mimport"))
			{
				
				PlatformController.getInstance().addProject((DTOProject) model);
			}
		}
		
		if (comp.getKey().equals("Bcancel"))
		{
			closeContainingWindow();
			
		}
		/*
		if (comp.getKey().equals("btnImportChooseFile"))
		{
			JFileChooser fileChooser = new JFileChooser();
			int returnVal = fileChooser.showSaveDialog(getViewPanel());
			
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				txtImportPath.setText(fileChooser.getSelectedFile().getPath());
			}
			
		}
		else if (comp.getKey().equals("btnImport"))
		{
			if (!txtImportPath.getText().equals(""))
				try {
					new XMLImport(txtImportPath.getText()).startImport();
				} catch (XMLNotValidException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}*/
		
	}
	
	
	@Override
	public void exportProject(DTOProject project,
			BHDataExchangeDialog exportDialog) {
		GUI_KEY = "DXMLProjectExport";
		model = project;
		exportPanel = exportDialog.setDefaulExportProjectPanel();
		exportDialog.setPluginActionListener(this);
		this.exportDialog = exportDialog;
	}


	@Override
	public DTOProject importProject(DTOProject project,
			BHDataExchangeDialog exportDialog) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
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
