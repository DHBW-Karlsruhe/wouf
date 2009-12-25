package org.bh.plugin.xmldataexchange;

import java.awt.event.ActionEvent;
import java.util.Map;

import javax.swing.JFileChooser;

import org.bh.controller.Controller;
import org.bh.data.DTOProject;
import org.bh.data.IDTO;
import org.bh.gui.View;
import org.bh.gui.ViewException;
import org.bh.gui.swing.BHTextField;
import org.bh.gui.swing.IBHComponent;
import org.bh.platform.PlatformEvent;
import org.bh.plugin.xmldataexchange.export.XMLExport;

public class XMLDataExchangeController extends Controller {
	
	private XMLDataExchangeView view = null;
	
	private IDTO model = null;
	
	public XMLDataExchangeController() {
		super();
		try {			
			XMLDataExchangePanel panel = new XMLDataExchangePanel();		
			view = new XMLDataExchangeView(panel, null, null);
			
			//((BHButton)view.getBHtextComponents().get("btnExportChooseFile")).addActionListener(this);
			//((BHButton)view.getBHtextComponents().get("btnExport")).addActionListener(this);
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
	public void setResult(Map result) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		IBHComponent comp =  (IBHComponent) e.getSource();
		BHTextField textField = (BHTextField) view.getBHmodelComponents().get("txtExportPath");
		
		if (comp.getKey().equals("btnExportChooseFile"))
		{
			JFileChooser fileChooser = new JFileChooser();
			int returnVal = fileChooser.showSaveDialog(getView());
			
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				textField.setText(fileChooser.getSelectedFile().getPath());
			}
			
		}
		else if (comp.getKey().equals("btnExport"))
		{
			if (!textField.getText().equals(""))		
				new XMLExport(textField.getText(), (DTOProject) model).startExport();
			
		}
	}		
	@Override
	public void platformEvent(PlatformEvent e) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

}
