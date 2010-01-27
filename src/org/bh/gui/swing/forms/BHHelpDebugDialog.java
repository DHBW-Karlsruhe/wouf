package org.bh.gui.swing.forms;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import org.bh.platform.Services;
import org.bh.platform.i18n.BHTranslator;

/**
 * Dialog to show debug information
 *
 * <p>
 * Show debug informaiton if DEBUG in BusinessHorizon.java is set to true, otherwise the menu item is disabled
 *
 * @author Loeckelt.Michael
 * @version 1.0, 16.01.2010
 *
 */
public final class BHHelpDebugDialog extends JDialog {
	
	private JTextArea codeArea;
	private JScrollPane scrollPane;

	public BHHelpDebugDialog () {
		this.setProperties();
		
		codeArea = new JTextArea();
		codeArea.setText(Services.getLog());
		
		scrollPane = new JScrollPane(codeArea);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		add(scrollPane);
		
		validate();
	}
	
	private void setProperties() {
		this.setTitle(BHTranslator.getInstance().translate("Mdebug"));
		this.setSize(900, 600);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
	}

}
