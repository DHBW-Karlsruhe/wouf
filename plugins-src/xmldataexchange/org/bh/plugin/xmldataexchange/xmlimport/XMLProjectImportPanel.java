package org.bh.plugin.xmldataexchange.xmlimport;

import java.awt.FlowLayout;

import javax.swing.JPanel;

import org.bh.gui.swing.BHButton;
import org.bh.gui.swing.BHTextField;

public class XMLProjectImportPanel extends JPanel {

	public static final String KEY = "xmldataexchange_project_import_panel";	
	
	public XMLProjectImportPanel() {
		super();
		
		setLayout(new FlowLayout());
		
		BHTextField txtPath = new BHTextField("txtImportPath", "                              ");
		txtPath.setEnabled(false);
		
		BHButton btnChooseFile = new BHButton("btnImportChooseFile");
		BHButton btnDoImport = new BHButton("btnImport");
		
		add(txtPath);
		add(btnChooseFile);
		add(btnDoImport);
	}

}
