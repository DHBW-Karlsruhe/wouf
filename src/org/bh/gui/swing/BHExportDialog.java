package org.bh.gui.swing;

import javax.swing.JDialog;
import javax.swing.WindowConstants;

import org.bh.platform.i18n.BHTranslator;

public class BHExportDialog extends JDialog {

	public BHExportDialog() {
		
		setWindowProperties();
		
	}

	private void setWindowProperties() {
		this.setTitle(BHTranslator.getInstance().translate("DExportTitle"));
		this.setSize(400, 500);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	

}
