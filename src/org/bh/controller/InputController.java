/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.controller;

import org.bh.data.DTOAccessException;
import org.bh.data.IDTO;
import org.bh.gui.BHValidityEngine;
import org.bh.gui.View;
import org.bh.gui.swing.IBHModelComponent;

/**
 * 
 * @author Marco Hammel
 * @author Robert
 */
public abstract class InputController extends Controller implements
		IInputController {

	/**
	 * Referenz to the model Can be null
	 */
	private IDTO<?> model = null;

	/**
	 * have to be used in case of a UI based and model driven mvc plugin and
	 * register the plugin at the platform
	 * 
	 * @param view
	 *            a instance of a View subclass
	 * @param model
	 *            a instance of a dto
	 */
	public InputController(View view, IDTO<?> model) {
		super(view);
		setModel(model);
	}

	public InputController() {
		super();
	}

	public InputController(View view) {
		super(view);
	}

	public InputController(IDTO<?> model) {
		this(null, model);
	}

	public void setModel(IDTO<?> model) {
		this.model = model;
	}

	public IDTO<?> getModel() {
		return model;
	}

	@Override
	protected BHValidityEngine getValidator() {
		return this.view.getValidator();
	}

	// TODO Javadoc, exception handling
	/*
	 * Static methods for data transfer between model and view
	 */
	public static void saveAllToModel(View view, IDTO<?> model)
			throws DTOAccessException {
		log.debug("Saving values from view to model");
		model.setSandBoxMode(true);
		for (IBHModelComponent comp : view.getBHModelComponents().values()) {
			model.put(comp.getKey(), comp.getValue());
		}
	}

	public static void saveToModel(IBHModelComponent comp, IDTO<?> model)
			throws DTOAccessException {
		log.debug("Saving value from component to model");
		model.setSandBoxMode(true);
		model.put(comp.getKey(), comp.getValue());
	}

	public static void saveToModel(View view, IDTO<?> model, Object key)
			throws DTOAccessException {
		log.debug("Saving value from view to model");
		model.setSandBoxMode(true);
		IBHModelComponent comp = view.getBHModelComponents()
				.get(key.toString());
		if (comp != null) {
			model.put(key.toString(), comp.getValue());
		}
	}

	public static void loadAllToView(IDTO<?> model, View view) {
		log.debug("Loading values from model to view");
		for (IBHModelComponent comp : view.getBHModelComponents().values()) {
			try {
				comp.setValue(model.get(comp.getKey()));
			} catch (DTOAccessException e) {
				comp.setValue(null);
			}
		}
	}

	public static void loadToView(IDTO<?> model, IBHModelComponent comp)
			throws DTOAccessException {
		log.debug("Loading value from model to component");
		try {
			comp.setValue(model.get(comp.getKey()));
		} catch (DTOAccessException e) {
			comp.setValue(null);
		}
	}

	public static void loadToView(IDTO<?> model, View view, Object key)
			throws DTOAccessException {
		log.debug("Loading value from model to component");
		IBHModelComponent comp = view.getBHModelComponents()
				.get(key.toString());
		if (comp != null) {
			try {
				comp.setValue(model.get(key.toString()));
			} catch (DTOAccessException e) {
				comp.setValue(null);
			}
		}
	}

	/*
	 * Wrappers for the static data transfer classes
	 */
	protected void saveAllToModel() throws DTOAccessException {
		saveAllToModel(this.view, this.model);
	}

	protected void saveToModel(IBHModelComponent comp)
			throws DTOAccessException {
		saveToModel(comp, this.model);
	}

	protected void saveToModel(Object key) throws DTOAccessException {
		saveToModel(this.view, this.model, key);
	}

	protected void loadAllToView() throws DTOAccessException {
		loadAllToView(this.model, this.view);
	}

	protected void loadToView(IBHModelComponent comp) throws DTOAccessException {
		loadToView(this.model, comp);
	}

	protected void loadToView(Object key) {
		loadToView(this.model, this.view, key);
	}
}
