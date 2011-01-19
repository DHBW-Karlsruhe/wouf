/*******************************************************************************
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
package org.bh.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.bh.gui.IBHComponent;
import org.bh.gui.IBHModelComponent;
import org.bh.gui.swing.comp.BHButton;
import org.bh.gui.view.IViewListener;
import org.bh.gui.view.View;
import org.bh.gui.view.ViewEvent;
import org.bh.gui.view.ViewException;
import org.bh.platform.IPlatformListener;
import org.bh.platform.PlatformEvent;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;
import org.bh.validation.BHValidityEngine;

/**
 * abstract controller component every mvc based application in BusinessHorizon should
 * implement at least one class extending this class or a subclass of controller in package org.bh.controller
 * @author Marco Hammel
 * @version 1.0
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
        /**
         * constructor for all non ui based mvc components or for dynamically generated uis
         */
	public Controller() {
		this(null);
	}
        /**
         * constructor for all ui based mvc components
         * @param view
         */
	public Controller(View view) {
		setView(view);
		Services.addPlatformListener(this);
	}
        
        @Override
	public JPanel getViewPanel() {
		if (this.view != null) {
			return view.getViewPanel();
		}

		return null;
	}
	/**
	 * set a instance of View by runtime and trigger ui component mapping
	 * @param view instance of View
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
         * retrigger the acitve instance of view in case of a dynamically changing ui
         * @throws ViewException if view is null
         */
	protected void reloadView() throws ViewException {
		view.setViewPanel(getViewPanel());
		setView(view);
	}

	/**
	 * get the ITranslator from the Platform
	 * @see Servicess
	 * @return
	 */
	public static ITranslator getTranslator() {
		return Services.getTranslator();
	}
	/**
	 * add the controller for each BHButton on the ui as ActionListener
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
        /**
         * acitve instance of the validator managed by the active view instance
         * @return BHValidityEngine
         */
	protected BHValidityEngine getValidator() {
		return this.view.getValidator();
	}
        /**
         * handle events from a instance of View or its subclasses
         * @param e
         */
	@Override
	public void viewEvent(ViewEvent e) {
		// to be defined by subclass
	}
        /**
         * handle events from thje platform type <code>PLatfromEvent</code>
         * @param e
         */
	@Override
	public void platformEvent(PlatformEvent e) {
		// to be defined by subclass
	}
        /**
         * handle a button event from a registered button. Have to be registered by
         * <code>addControllerAsListener</code>
         * @param e
         */
	@Override
	public void actionPerformed(ActionEvent e) {
		// to be defined by subclass
	}
	@Override
	public View getView() {
		return view;
	}
}
