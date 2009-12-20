package org.bh.gui.swing;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.UIManager.LookAndFeelInfo;

import org.bh.platform.PlatformEvent;
import org.bh.platform.Services;
import org.bh.platform.PlatformEvent.Type;


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
	private static final long serialVersionUID = -8173399020286070131L;

	public static JDesktopPane desktop;

	public BHToolBar toolBar;
	public static BHTree treeBar;
	public BHStatusBar statusBar;
	public static BHContent content;

	static JSplitPane paneH;

	JSplitPane paneV;

	JLabel test;
	
	int standardBarHeight = 40;
	static int treeBarWidth = 200;

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
		setExtendedState(MAXIMIZED_BOTH);

		// build GUI components
		desktop = new JDesktopPane();
		desktop.setLayout(new BorderLayout());

		toolBar = new BHToolBar(getWidth(), standardBarHeight);
		// toolBar.setBounds(0, 0, screenSize.width, standardBarHeight);

		treeBar = new BHTree();
		// treeBar.setBounds(0, standardBarHeight, treeBarWidth,
		// screenSize.height-standardBarHeight);
		// treeBar.setBounds(0,200,200,400);

		statusBar = Services.getBHstatusBar();
		content = new BHContent();
	
		// Create the horizontal split pane and put the treeBar and the content
		// in it.
		paneH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeBar, content);
		paneH.setOneTouchExpandable(true);
		paneH.setDividerLocation(treeBarWidth);
		
		// stop moving the divider
		// pane.setEnabled(false);

		desktop.add(toolBar, BorderLayout.PAGE_START);
		desktop.add(paneH, BorderLayout.CENTER);
		desktop.add(statusBar, BorderLayout.PAGE_END);
		// desktop.add(content, BorderLayout.CENTER);

		setContentPane(desktop);
		setSize(getPreferredSize());
		setVisible(true);
		
		// work around a Java-or-whatever bug which causes the main window to hide behind Eclipse
		this.setAlwaysOnTop(true);
		this.setAlwaysOnTop(false);

		Services.firePlatformEvent(new PlatformEvent(this, Type.PLATFORM_LOADING_COMPLETED));
	}

	/**
	 * Sets initial properties on <code>BHMainFrame</code>.
	 */
	private void setProperties() {
		// Mac properties
		// System.setProperty("apple.laf.useScreenMenuBar", "true");
		// System.setProperty("com.apple.mrj.application.apple.menu.about.name",
		// BHTranslator.getInstance().translate("title"));

		this.setNimbusLookAndFeel();
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setJMenuBar(new BHMenuBar());
	}
	
	public static void addContentForms(Component content){
		paneH.setRightComponent(content);
			
	}
	public static void addContentFormsAndChart(Component forms, Component chart){
		JSplitPane paneV = new JSplitPane(JSplitPane.VERTICAL_SPLIT, forms, chart);
		paneV.setOneTouchExpandable(true);
		
		paneH.setRightComponent(paneV);
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
