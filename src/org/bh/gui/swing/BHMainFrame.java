/*******************************************************************************
 * Copyright 2011: Matthias Beste, Hannes Bischoff, Lisa Doerner, Victor Guettler, Markus Hattenbach, Tim Herzenstiel, Günter Hesse, Jochen Hülß, Daniel Krauth, Lukas Lochner, Mark Maltring, Sven Mayer, Benedikt Nees, Alexandre Pereira, Patrick Pfaff, Yannick Rödl, Denis Roster, Sebastian Schumacher, Norman Vogel, Simon Weber 
 *
 * Copyright 2010: Anna Aichinger, Damian Berle, Patrick Dahl, Lisa Engelmann, Patrick Groß, Irene Ihl, Timo Klein, Alena Lang, Miriam Leuthold, Lukas Maciolek, Patrick Maisel, Vito Masiello, Moritz Olf, Ruben Reichle, Alexander Rupp, Daniel Schäfer, Simon Waldraff, Matthias Wurdig, Andreas Wußler
 *
 * Copyright 2009: Manuel Bross, Simon Drees, Marco Hammel, Patrick Heinz, Marcel Hockenberger, Marcus Katzor, Edgar Kauz, Anton Kharitonov, Sarah Kuhn, Michael Löckelt, Heiko Metzger, Jacqueline Missikewitz, Marcel Mrose, Steffen Nees, Alexander Roth, Sebastian Scharfenberger, Carsten Scheunemann, Dave Schikora, Alexander Schmalzhaf, Florian Schultze, Klaus Thiele, Patrick Tietze, Robert Vollmer, Norman Weisenburger, Lars Zuckschwerdt
 *
 * Copyright 2008: Camil Bartetzko, Tobias Bierer, Lukas Bretschneider, Johannes Gilbert, Daniel Huser, Christopher Kurschat, Dominik Pfauntsch, Sandra Rath, Daniel Weber
 *
 * This program is free software: you can redistribute it and/or modify it un-der the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT-NESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package org.bh.gui.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.prefs.BackingStoreException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;
import org.bh.SVN;
import org.bh.gui.swing.tree.BHTree;
import org.bh.platform.IPlatformListener;
import org.bh.platform.PlatformController;
import org.bh.platform.PlatformEvent;
import org.bh.platform.ProjectRepositoryManager;
import org.bh.platform.Services;
import org.bh.platform.PlatformEvent;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;

/**
 * Main Frame for Business Horizon Application.
 * 
 * <p>
 * This <code>JFrame</code> provides the main frame for Business Horizon
 * Application.
 * 
 * @author Tietze.Patrick
 * @author Thiele.Klaus
 * @author Schmalzhaf.Alexander
 * @author Loeckelt.Michael
 * @version 0.1.1, 2009/12/16
 * @version 0.2, 2009/12/22
 * @version 0.3, 2009/12/31
 * @version 0.4, 2010/01/07
 * 
 */
@SuppressWarnings("serial")
public final class BHMainFrame extends JFrame implements IPlatformListener, TimingTarget {

	/**
	 * logger
	 */
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(BHMainFrame.class);
	
	/**
	 * Standard Bar height.
	 */
	public static final int STANDARDBARHEIGHT = 40;

	/**
	 * main panel.
	 */
	private JPanel desktop;

	/**
	 * Menu Bar for application.
	 */
	private BHMenuBar menuBar;

	/**
	 * ToolBar for desktop.
	 */
	private BHToolBar toolBar;

	/**
	 * Tree for File contents (placed on a ScrollPane).
	 */
	private JScrollPane bhTreeScroller;
	
	/**
	 * Tree displaying the file contents.
	 */
	private BHTree bhTree;

	/**
	 * Status Bar.
	 */
	private BHStatusBar statusBar;

	/**
	 * content place holder: white area with logo in background.
	 */
	private BHContent content;

	/**
	 * Horizontal Split pane.
	 */
	private JSplitPane paneH;
	
	/**
	 * vertical Split Pane on right side (upper part content, lower part result)
	 */
	private JSplitPane paneV;
	
	
	
	
	/**
	 * Open / Save dialog.
	 */
	private BHFileChooser chooser;
	
	private JScrollPane contentForm;
	
	private JScrollPane resultForm;
	
	/**
	 * Animator instance
	 */
	protected Animator animator;
	
	/**
	 * flag for direction of animation. Only used for animation.
	 */
	private boolean moveIn;
	
	/**
	 * current cached position of divider. Only used for animation.
	 */
	private int dividerLocation;

	/**
	 * Standard constructor for <code>BHMainFrame</code>.
	 * 
	 * @param title
	 *            title to be set for the <code>BHMainFrame</code>.
	 */
	public BHMainFrame() {
		super();
		this.setProperties();

		// Build MenuBar
		menuBar = new BHMenuBar();
		this.setJMenuBar(menuBar);
		//disable ToolBar buttons
		menuBar.disableMenuScenarioAllItems();
		menuBar.disableMenuPeriodAllItems();

		// build GUI components
		desktop = new JPanel();
		desktop.setLayout(new BorderLayout());

		toolBar = new BHToolBar(getWidth(), STANDARDBARHEIGHT);
		//disable menu items
		toolBar.disableScenarioButton();
		toolBar.disablePeriodButton();
		
		bhTree = new BHTree();
		
		bhTreeScroller = new JScrollPane(bhTree);
		
		statusBar = Services.getBHstatusBar();
		content = new BHContent();
		//Create vertical split pane 
		paneV = new JSplitPane(JSplitPane.VERTICAL_SPLIT, content, resultForm);
		paneV.setOneTouchExpandable(true);
		
		// Create the horizontal split pane and put the treeBar and the content
		// in it.
		paneH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, bhTreeScroller,
				paneV);
		paneH.setOneTouchExpandable(true);
		
		bhTreeScroller.setMinimumSize(new Dimension(UIManager.getInt("BHTree.minimumWidth"), bhTreeScroller.getMinimumSize().height));


		desktop.add(toolBar, BorderLayout.PAGE_START);
		desktop.add(paneH, BorderLayout.CENTER);
		desktop.add(statusBar, BorderLayout.PAGE_END);
		// desktop.add(content, BorderLayout.CENTER);

		this.setContentPane(desktop);

		chooser = new BHFileChooser();
		
		// init animator.
		this.animator = new Animator(1000, this);
		this.animator.setAcceleration(0.3f);
		this.animator.setDeceleration(0.2f);
		this.animator.setResolution(50);
		this.moveIn = false; 
	}

	/**
	 * Sets initial properties on <code>BHMainFrame</code>.
	 */
	private synchronized void setProperties() {
		this.resetTitle();
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setLocationRelativeTo(null);
		this.setSize(1000, 700);
		// EXIT is like app suicide
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		Services.addPlatformListener(this);
		
		this.setIconImages(Services.setIcon());
		
		this.moveIn = false;
	}
	
	/**
	 * resets the title of the <code>BHMainFrame</code>.
	 */
	public void resetTitle() {
		String title =  Services.getTranslator().translate("title");
		String path = PlatformController.preferences.get("path", null);
		if (path != null) {
			title += " - " + path;
		}
		
		if (ProjectRepositoryManager.isChanged()) {
			String changedSuffix = " (" + Services.getTranslator().translate("changed") + ")";
			title += changedSuffix;
		}
		
		if (SVN.isRevisionSet()) {
			title += " (Revision " + SVN.getRevision() + ")";
		}
		
		setTitle(title);
	}
	
	/**
	 * Disposes the Frame.
	 */
	@Override
	public void dispose() {
		// Data has been changed.
		if (ProjectRepositoryManager.isChanged()) {
			
			/*
			 * Show the user a dialog to decide, whether he really wants to exit the application
			 */
			int i = BHOptionPane.showConfirmDialog(this, Services.getTranslator().translate("PsaveEnd_long"), Services.getTranslator().translate("PsaveEnd"),JOptionPane.YES_NO_CANCEL_OPTION);
			/*
			 * The user wants to save all changed data.
			 */
			if (i == JOptionPane.YES_OPTION) {
			
				/**
				 * Try to save all preferences
				 * @author Marcus Katzor
				 */
				try {
					Logger.getLogger(getClass()).debug("Save application preferences");
					PlatformController.preferences.flush();
					Services.firePlatformEvent(new PlatformEvent(BHMainFrame.class, PlatformEvent.Type.SAVE));
				} catch (BackingStoreException e) {
					Logger.getLogger(getClass()).error("Error while saving application preferences", e);
				}
				
				super.dispose();
				
				//Exiting application without saving the changed data
			} else if (i == JOptionPane.NO_OPTION) {
				Logger.getLogger(getClass()).debug("Existing changes but no save wish - exiting app");
				super.dispose();
			}
			
			//No changes were made. We can directly exit the application.
		} else { 
			Logger.getLogger(getClass()).debug("No changes - exiting app");
			super.dispose();
		}
	}

	public void setContentForm(Component content) {
		//Achtung content != JScrollPanel
		contentForm = new JScrollPane(content);
		contentForm.getHorizontalScrollBar().setUnitIncrement(10);
		contentForm.setWheelScrollingEnabled(true);
		paneV.setTopComponent(contentForm);
	}

	public void setResultForm(Component result) {
		paneV.setBottomComponent(result);
	}
	
	public void moveInResultForm(Component result) {
		// Achtung result ist meist schon JScrollPanel
		this.setResultForm(result);
		if (PlatformController.preferences.getBoolean("animation", true)) {
			if (animator.isRunning()) {
				animator.cancel();
			}
			this.moveIn = true;
			paneV.setDividerLocation(1.0);
			animator.start();
		}
		else {
			paneV.setDividerLocation(0.5);
		}
	}
	
	public void moveOutResultForm() {
		if (PlatformController.preferences.getBoolean("animation", true)) {
			if (animator.isRunning()) {
				animator.cancel();
			}
			this.moveIn = false;
			animator.start();
		}
		else {
			this.removeResultForm();
		}
	}
	
	public void removeResultForm() {
		paneV.setDividerLocation(1.0);
		paneV.setBottomComponent(null);
	}
	
	/**
	 * Returns the <code>FileChooser</code> of the <code>BHMainFrame</code>.
	 * @return the current <code>BHFileChooser</code>.
	 */
	public BHFileChooser getChooser() {
		return this.chooser;
	}
	
	/**
	 * Returns the BHTree.
	 * @return the BHTree.
	 */
	public BHTree getBHTree() {
		return bhTree;
	}

	/**
	 * Handles platform events.
	 */
	@Override
	public void platformEvent(PlatformEvent e) {

		// Platform loading completed. show.
		if (e.getEventType() == PlatformEvent.Type.PLATFORM_LOADING_COMPLETED) {
			this.setVisible(true);
			this.toFront();
			this.requestFocus();
		}
		
		// Data changed. Add changed suffix to title.
		if (e.getEventType() == PlatformEvent.Type.DATA_CHANGED) {
			String changedSuffix = " (" + Services.getTranslator().translate("changed") + ")";
			if (! this.getTitle().endsWith(changedSuffix) && ProjectRepositoryManager.isChanged()) 
				this.setTitle(this.getTitle() + changedSuffix);
		}
		
		// Locale changed
		if (e.getEventType() == PlatformEvent.Type.LOCALE_CHANGED) {
			this.resetTitle();
			this.chooser = new BHFileChooser();
			this.repaint(); // enforce repaint to everybody.
		}
	}
	
	public void setVDividerLocation(int dividerLocation){
		paneV.setDividerLocation(dividerLocation);
	}
	
	public int getVDividerLocation(){
		return paneV.getDividerLocation();
	}

	/**
	 * Called by animator at the beginnig of animation.
	 */
	@Override
	public void begin() {
		this.dividerLocation = this.paneV.getDividerLocation();
	}

	/**
	 * Called by animator at the end of animation.
	 */
	@Override
	public void end() {
		if (this.moveIn) {
			paneV.setDividerLocation(0.5);
		}
		else {
			paneV.setDividerLocation(1.0);
			paneV.setBottomComponent(null);
		}
	}
	
	/**
	 * not needed by animation.
	 */
	@Override
	public void repeat() {
	}

	/**
	 * Called by the animator at every step of animation.
	 */
	@Override
	public void timingEvent(float fraction) {
		if (moveIn) {
			paneV.setDividerLocation(1 - (fraction * 0.5) );
		}
		else {
			paneV.setDividerLocation((int) (this.dividerLocation + (paneV.getHeight() - this.dividerLocation) * fraction));
		}
	}
	
	public BHMenuBar getBHMenuBar(){
	    return menuBar;
	}
	
	public BHToolBar getBHToolBar(){
	    return toolBar;
	}
}
