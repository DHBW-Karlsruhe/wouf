package org.bh.gui.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * Main Frame for Business Horizon Application.
 * 
 * <p>
 * This <code>JFrame</code> provides the main frame for Business Horizon
 * Application.
 * 
 * @author Tietze.Patrick
 * @author Thiele.Klaus
 * @version 0.1.1, 2009/12/16
 * 
 */
public class BHMainFrame extends JFrame {

	public JDesktopPane desktop;

	public BHToolBar toolBar;
	public BHTreeBar treeBar;
	public BHStatusBar statusBar;
	public BHContent content;

	JSplitPane paneH, paneV;

	JLabel test;

	/**
	 * Standard constructor for <code>BHMainFrame</code>.
	 * 
	 * @param title
	 *            title to be set for the <code>BHMainFrame</code>.
	 */

	public BHMainFrame(String title) {
		super(title);
		this.setProperties();

		// create main frame
		// 50 pixel from every corner depending on the resolution
		int inset = 20;
		int standardBarHeight = 40;
		int treeBarWidth = 200;

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(inset, inset, screenSize.width - inset * 2, screenSize.height
				- inset * 2);

		// build GUI components
		desktop = new JDesktopPane();
		desktop.setLayout(new BorderLayout());

		toolBar = new BHToolBar(screenSize.width, standardBarHeight);
		// toolBar.setBounds(0, 0, screenSize.width, standardBarHeight);

		treeBar = new BHTreeBar();
		// treeBar.setBounds(0, standardBarHeight, treeBarWidth,
		// screenSize.height-standardBarHeight);
		// treeBar.setBounds(0,200,200,400);

		statusBar = new BHStatusBar("");
		content = new BHContent();

		// Create the horizontal split pane and put the treeBar and the content
		// in it.
		paneH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeBar, content);
		paneH.setOneTouchExpandable(true);
		paneH.setDividerLocation(treeBarWidth);

		// Provide minimum sizes for the two components in the split pane
		treeBar.setMinimumSize(new Dimension(100, 100));
		content.setMinimumSize(new Dimension(100, 100));

		// stop moving the divider
		// pane.setEnabled(false);

		desktop.add(toolBar, BorderLayout.PAGE_START);
		desktop.add(paneH, BorderLayout.CENTER);
		desktop.add(statusBar, BorderLayout.PAGE_END);
		// desktop.add(content, BorderLayout.CENTER);

		setContentPane(desktop);
	}

	/**
	 * Sets initial properties on <code>BHMainFrame</code>.
	 */
	private void setProperties() {
		this.setNimbusLookAndFeel();
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		// this.pack();
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
