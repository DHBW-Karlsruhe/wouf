package org.bh.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.log4j.Logger;
import org.bh.gui.BHValidityEngine;
import org.bh.gui.IViewListener;
import org.bh.gui.View;
import org.bh.gui.ViewEvent;
import org.bh.gui.swing.BHButton;
import org.bh.gui.swing.BHStatusBar;
import org.bh.gui.swing.IBHComponent;
import org.bh.gui.swing.IBHModelComponent;
import org.bh.platform.IPlatformListener;
import org.bh.platform.PlatformEvent;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;

/**
 * 
 * @author Marco Hammel
 */
public abstract class Controller implements IController, ActionListener,
		IPlatformListener, IViewListener {

	protected static Logger log = Logger.getLogger(Controller.class);

	/**
	 * Reference to the active view of the plugin Can be null
	 */
	protected View view = null;

	/**
	 * Reference to all model depending IBHcomponents on the UI
	 */
	protected Map<String, IBHModelComponent> bhMappingComponents;

	public Controller() {
		this(null);
	}

	public Controller(View view) {
		setView(view);
		Services.addPlatformListener(this);
	}

	/**
	 * central exception handler method. Should be called in every catch
	 * statement in each plugin.
	 * 
	 * @see BHstatusBar
	 * @param e
	 */
	private void handleException(Exception e) {
		log.error("Controller Exception ", e);
		// TODO how to show system erros to the user
		Services.getBHstatusBar().setHint(e.getMessage(), true);
	}

	public JPanel getViewPanel() {
		if (this.view != null) {
			return view.getViewPanel();
		}

		return null;
	}

	/**
	 * 
	 * @param view
	 */
	protected void setView(View view) {
		// TODO stop listening to previous view 
		if (this.view != null) {
			
		}
		this.view = view;
		if (view != null) {
			this.bhMappingComponents = this.view.getBHModelComponents();
			this.addControllerAsListener(this.view.getBHtextComponents());
		}
	}

	/**
	 * writes all dto values with a matching key in a IBHComponent to UI
	 * 
	 * @throws DTOAccessException
	 */

	/**
	 * get the ITranslator from the Platform
	 * 
	 * @see Servicess
	 * @return
	 */
	public static ITranslator getTranslator() {
		return Services.getTranslator();
	}

	/**
	 * concret BHValidityEngine can use this method to set Validation Tool Tip
	 * 
	 * @param pane
	 * @see BHStatusBar
	 */
	public static void setBHstatusBarErrorHint(JScrollPane pane) {
		Services.getBHstatusBar().setErrorHint(pane);
	}

	/**
	 * concret BHValidityEngine can use this method to set Info Tool Tip
	 * 
	 * @param hintLabel
	 * @see JLabel
	 * @see BHStatusBar
	 */
	public static void setBHstatusBarHint(JLabel hintLabel) {
		Services.getBHstatusBar().setHint(hintLabel);
	}

	/**
	 * add the Controller for each BHButton on the UI as ActionListener
	 * 
	 * @param comps
	 * @see ActionListener
	 * @see BHButton
	 */
	private void addControllerAsListener(Map<String, IBHComponent> comps) {
		for (IBHComponent comp : comps.values()) {
			if (comp instanceof BHButton) {
				((BHButton) comp).addActionListener(this);
			}
		}
	}

	protected BHValidityEngine getValidator() {
		return this.view.getValidator();
	}

	@Override
	public void viewEvent(ViewEvent e) {
		// to be defined by subclass
	}

	@Override
	public void platformEvent(PlatformEvent e) {
		// to be defined by subclass
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// to be defined by subclass
	}
}
