/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.controller;

import org.bh.data.DTOAccessException;
import org.bh.data.IDTO;
import org.bh.gui.BHValidityEngine;
import org.bh.gui.View;
import org.bh.gui.swing.IBHComponent;

/**
 * 
 * @author Marco Hammel
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
		this.model = model;
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

	/**
	 * writes all data to its dto reference
	 * 
	 * @throws DTOAccessException
	 */
	protected void saveAllToModel() throws DTOAccessException {
		saveToModel(this.view, this.model);
	}

	/**
	 * save specific component to model
	 * 
	 * @param comp
	 * @throws DTOAccessException
	 */
	protected void saveToModel(IBHComponent comp) throws DTOAccessException {
		saveToModel(comp, this.model);
	}

	/**
	 * writes all dto values with a mathcing key in a IBHComponent to UI
	 * 
	 * @throws DTOAccessException
	 */
	protected void loadAllToView() throws DTOAccessException {
		log.debug("Plugin load from dto in view");
		for (String key : this.bhMappingComponents.keySet()) {
			// this.bhMappingComponents.get(key).setValue(this.model.get(key));
		}
	}

	/**
	 * 
	 * @param key
	 * @throws DTOAccessException
	 * @throws ControllerException
	 */
	protected void loadToView(String key) throws DTOAccessException,
			ControllerException {
		log.debug("Plugin load from dto in view");
		// this.bhMappingComponents.get(key).setValue(this.model.get(key));
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

	public static void saveToModel(View view, IDTO<?> model) {
		log.debug("Saving values from view to model");
		model.setSandBoxMode(true);
		for (IBHComponent comp : view.getBHmodelComponents().values()) {
			model.put(comp.getKey(), comp.getValue());
		}
	}

	public static void saveToModel(IBHComponent comp, IDTO<?> model) {
		log.debug("Saving value from component to model");
		model.setSandBoxMode(true);
		model.put(comp.getKey(), comp.getValue());
	}

	public static void loadToView(IDTO<?> model, View view) {
		log.debug("Loading values from model to view");
		for (IBHComponent comp : view.getBHmodelComponents().values()) {
			comp.setValue(model.get(comp.getKey()));
		}
	}

	public static void loadToView(IDTO<?> model, IBHComponent comp) {
		log.debug("Loading value from model to component");
		comp.setValue(model.get(comp.getKey()));
	}
}
