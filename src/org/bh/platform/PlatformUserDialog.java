package org.bh.platform;

import javax.swing.JOptionPane;

import org.bh.gui.swing.BHMainFrame;

/**
 * User Dialog Messages
 *
 * <p>
 * Handle exceptions and warnings
 *
 * @author Loeckelt.Michael
 * @version 0.1, 09.01.2010
 *
 */
public class PlatformUserDialog {
	
	/**
	 * Mainframe reference
	 * @param message
	 */
	BHMainFrame bhmf = null;
	
	private static PlatformUserDialog instance = null;
	
	private PlatformUserDialog (BHMainFrame bhmf) {
		this.bhmf = bhmf;	
	}
	
	public static void init(BHMainFrame bhmf) {
		PlatformUserDialog.instance = new PlatformUserDialog(bhmf);
	}
	    
    public static PlatformUserDialog getInstance() {
    	return instance;
    }
	
    /**
     * standard error dialog
     * @param message
     * @param title
     */
    
	public void showErrorDialog (String message, String title) {
		JOptionPane.showMessageDialog(bhmf, message, title, JOptionPane.ERROR_MESSAGE);
	}
	

}
