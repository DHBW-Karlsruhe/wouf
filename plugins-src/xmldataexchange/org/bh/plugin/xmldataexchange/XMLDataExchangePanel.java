package org.bh.plugin.xmldataexchange;

import java.awt.FlowLayout;

import javax.swing.JPanel;

import org.bh.gui.swing.BHButton;
import org.bh.gui.swing.BHLabel;
import org.bh.gui.swing.BHTextField;

public class XMLDataExchangePanel extends JPanel {

	public XMLDataExchangePanel() {
		super();
		
		setLayout(new FlowLayout());
				
		BHLabel lblPath = new BHLabel("exportPath", "");
		BHTextField txtPath = new BHTextField("txtExportPath", "                              ");
		txtPath.setEnabled(false);
		
		BHButton btnChooseFile = new BHButton("btnExportChooseFile");
		BHButton btnDoExport = new BHButton("btnExport");
		
		add(lblPath);
		add(txtPath);
		add(btnChooseFile);
		add(btnDoExport);
	}

}

