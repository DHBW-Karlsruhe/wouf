package org.bh.gui.swing;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * Main Frame for Business Horizon Application.
 * 
 * <p>
 * This <code>JFrame</code> provides the main frame for Business Horizon
 * Application.
 * 
 * @author Thiele.Klaus
 * @version 0.1, 2009/12/13
 * 
 */
public class BHMainFrame extends JFrame {

    /**
     * Standard constructor for <code>BHMainFrame</code>.
     * 
     * @param title
     *            title to be set for the <code>BHMainFrame</code>.
     */
    public BHMainFrame(String title) {
	super(title);
	this.setNimbusLookAndFeel();
    }

    /**
     * Sets Nimbus from Sun Inc. as default Look & Feel. Java 6 Update 10
     * required. Don't change complex looking implementation of invokation,
     * there are valid reasons for it.<br />
     * 
     * <b>Remark</b> <br />
     * For further information on Nimbus see <a href=
     * "http://developers.sun.com/learning/javaoneonline/2008/pdf/TS-6096.pdf"
     * >JavaOne Slides</a>
     */
    private void setNimbusLookAndFeel() {
	// set Nimbus if available
	try {
	    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		if ("Nimbus".equals(info.getName())) {
		    UIManager.setLookAndFeel(info.getClassName());
		    break;
		}
	    }
	} catch (Exception e) {
	    // If Nimbus is not available - leave JRE default Look & Feel.
	}
    }
}
