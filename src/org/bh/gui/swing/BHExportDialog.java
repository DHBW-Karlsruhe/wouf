package org.bh.gui.swing;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.bh.platform.i18n.BHTranslator;

public class BHExportDialog extends JDialog {

	public BHExportDialog() {
		
		setWindowProperties();


	}
	//TODO make generic for all export plugins
	private void setWindowProperties() {
		this.setTitle(BHTranslator.getInstance().translate("DXMLExportTitle"));
		this.setSize(400, 500);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
	}
	
	

}
