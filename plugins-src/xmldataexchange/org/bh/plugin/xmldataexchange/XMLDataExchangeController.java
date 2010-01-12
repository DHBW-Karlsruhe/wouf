package org.bh.plugin.xmldataexchange;

import java.awt.Component;
import java.awt.Container;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.bh.controller.IDataExchangeController;
import org.bh.data.DTOPeriod;
import org.bh.data.DTOProject;
import org.bh.data.DTOScenario;
import org.bh.data.IDTO;
import org.bh.gui.swing.BHButton;
import org.bh.gui.swing.IBHComponent;
import org.bh.platform.PlatformController;
import org.bh.platform.ProjectRepositoryManager;
import org.bh.platform.i18n.BHTranslator;
import org.bh.plugin.xmldataexchange.xmlexport.XMLExport;
import org.bh.plugin.xmldataexchange.xmlexport.XMLProjectExportPanel;
import org.bh.plugin.xmldataexchange.xmlimport.XMLImport;
import org.bh.plugin.xmldataexchange.xmlimport.XMLNotValidException;
import org.bh.plugin.xmldataexchange.xmlimport.XMLProjectImportPanel;

public class XMLDataExchangeController implements IDataExchangeController, ActionListener {
		
	private List<IDTO<?>> model = null;
	
	private static String GUI_KEY = "xmldataexchange";
	private static final String dataFormat = "XML";
	
	private Map<String, JPanel> exportPanels = new HashMap<String, JPanel>();
	private Map<String, JPanel> importPanels = new HashMap<String, JPanel>();
	
	private Container container = null;
	
	public XMLDataExchangeController() {
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		IBHComponent comp =  (IBHComponent) e.getSource();		
		
		if (GUI_KEY.equals(XMLProjectExportPanel.KEY))
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
							setModel(proj);
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
					
					
					
					/*PlatformController.preferences.put("lastImportDirectory", fileChooser.getSelectedFile().getParent());*/ 
					projImportPanel.getTxtPath().setText(fileChooser.getSelectedFile().getPath());			
				}
				
			}
			else if (comp.getKey().equals("Mimport"))
			{
				
				PlatformController.getInstance().addProject((DTOProject) getModel());
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


	private void closeContainingWindow() {
		if (container != null && 
				Window.class.isAssignableFrom(container.getClass()))
				{
					((Window)container).dispose();
				}
	}		
	
	@Override
	public String getDataFormat() {
		return dataFormat;
	}

	@Override
	public JPanel getExportPanel(String type, Container cont) {		
		initializeExportPanels();
		GUI_KEY = type;
		container = cont;
		
		if (exportPanels.containsKey(type))
			return exportPanels.get(type);
		else
			return null;
	}
	
	

	private void initializeExportPanels() {
		exportPanels = new HashMap<String, JPanel>();
		XMLProjectExportPanel projPanel = new XMLProjectExportPanel(model);		
		exportPanels.put(XMLProjectExportPanel.KEY, projPanel);
		for (JPanel panel : exportPanels.values())		
			addActionListener(panel);
	}


	@Override
	public JPanel getImportPanel(String type, Container cont) {
		initializeImportPanels();
		GUI_KEY = type;
		container = cont;
		
		if (importPanels.containsKey(type))
			return importPanels.get(type);
		else			
			return null;
	}

	private void initializeImportPanels() {
		importPanels = new HashMap<String, JPanel>();
		XMLProjectImportPanel projPanel = new XMLProjectImportPanel();
		importPanels.put(XMLProjectImportPanel.KEY, projPanel);		
		for (JPanel panel : importPanels.values())		
			addActionListener(panel);		
	}


	@Override
	public String getGuiKey() {
		return GUI_KEY;
	}


	@Override
	public IDTO<?> getModel() {
		if (model != null && model.size() >= 1)
			return model.get(0);
		else
			return null;
	}


	@Override
	public void setModel(IDTO<?> dto) {
		model = new ArrayList<IDTO<?>>();
		model.add(dto);
	}

	@Override
	public List<IDTO<?>> getModelAsList() {
		return model;
	}

	@Override
	public void setModelAsList(List<IDTO<?>> dtos) {
		model = dtos;
	}
	
	private void addActionListener(JPanel panel)
	{
		for (Component comp : panel.getComponents())
		{
			if (comp instanceof JPanel)
				addActionListener((JPanel) comp);			
			else if (comp instanceof BHButton)
				((JButton)comp).addActionListener(this);
		}
	}


	
}
