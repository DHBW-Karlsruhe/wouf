package org.bh.plugin.xmldataexchange;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JFileChooser;

import org.bh.controller.InputController;
import org.bh.data.DTOProject;
import org.bh.data.IDTO;
import org.bh.gui.View;
import org.bh.gui.ViewException;
import org.bh.gui.swing.BHTextField;
import org.bh.gui.swing.IBHComponent;
import org.bh.platform.PlatformEvent;
import org.bh.plugin.xmldataexchange.xmlexport.XMLDataExportPanel;
import org.bh.plugin.xmldataexchange.xmlexport.XMLExport;
import org.bh.plugin.xmldataexchange.xmlimport.XMLDataImportPanel;
import org.bh.plugin.xmldataexchange.xmlimport.XMLImport;
import org.bh.plugin.xmldataexchange.xmlimport.XMLNotValidException;

public class XMLDataExchangeController extends InputController {
	
	private XMLDataExchangeView view = null;
	
	private IDTO model = null;
	
	public XMLDataExchangeController() {
		super();	
	}
	
	public void setExportView()
	{
		XMLDataExportPanel panel = new XMLDataExportPanel();		
		try {
			view = new XMLDataExchangeView(panel, null, null);
			setView(view);	
		} catch (ViewException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setImportView()
	{
		XMLDataImportPanel panel = new XMLDataImportPanel();		
		try {
			view = new XMLDataExchangeView(panel, null, null);
			setView(view);	
		} catch (ViewException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public XMLDataExchangeController(View view) {
		super(view);
		// TODO Auto-generated constructor stub
	}



	@Override
	public void setModel(IDTO model) {
		this.model = model;		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		IBHComponent comp =  (IBHComponent) e.getSource();
		BHTextField txtExportPath = (BHTextField) view.getBHModelComponents().get("txtExportPath");
		BHTextField txtImportPath = (BHTextField) view.getBHModelComponents().get("txtImportPath");
		
		if (comp.getKey().equals("btnExportChooseFile"))
		{
			JFileChooser fileChooser = new JFileChooser();
			int returnVal = fileChooser.showSaveDialog(getViewPanel());
			
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				txtExportPath.setText(fileChooser.getSelectedFile().getPath());
			}
			
		}
		else if (comp.getKey().equals("btnExport"))
		{
			if (!txtExportPath.getText().equals(""))		
				new XMLExport(txtExportPath.getText(), (DTOProject) model).startExport();
			
		}
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
		}
		
	}		
	@Override
	public void platformEvent(PlatformEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getGuiKey() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}
}
