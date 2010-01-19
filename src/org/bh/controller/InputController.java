/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.controller;

import org.bh.data.DTOAccessException;
import org.bh.data.IDTO;
import org.bh.data.types.DoubleValue;
import org.bh.data.types.IValue;
import org.bh.data.types.IntegerValue;
import org.bh.data.types.IntervalValue;
import org.bh.gui.BHValidityEngine;
import org.bh.gui.View;
import org.bh.gui.ViewEvent;
import org.bh.gui.swing.IBHComponent;
import org.bh.gui.swing.IBHModelComponent;

import com.jgoodies.validation.ValidationResult;

/**
 * 
 * @author Marco Hammel
 * @author Robert
 */
public class InputController extends Controller implements IInputController {

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

	@Override
	public void viewEvent(ViewEvent e) {
		switch (e.getEventType()) {
		case VALUE_CHANGED:
			// the value of the component has been validated, so save it to the
			// model
			saveToModel((IBHModelComponent) e.getSource());
			// maybe other fields are now valid as well
			// TODO save values of fields which have previously been invalid and
			// have become valid now (does not trigger a change event, so maybe
			// we should simply save the values of all valid fields at this
			// point.
			ValidationResult validationResult = this.view.revalidate();
			model.setValid(!validationResult.hasErrors());
			break;
		case VALIDATION_FAILED:
			// the validation of the component went wrong, so remvove value for
			// DTO (necessary e.g. in case of saving DTO to file; otherwise,
			// deleted values would be saved)
			removeFromModel((IBHModelComponent) e.getSource());
			model.setValid(false);
			break;
		}
	}

	@Override
	protected void setView(View view) {
		if (this.view != null) {
			this.view.removeViewListener(this);
		}
		super.setView(view);
		// Marcus.Katzor: A listener can only be added when the view exists
		// TODO Vollmer.Robert: Does a controller without view make sense?
		if (view != null)
			view.addViewListener(this);
	}

	// TODO Javadoc, exception handling
	/*
	 * Static methods for data transfer between model and view
	 */
	public void saveAllToModel() throws DTOAccessException {
		for (IBHModelComponent comp : view.getBHModelComponents().values()) {
			saveToModel(comp);
		}
	}

	public void saveToModel(IBHModelComponent comp) throws DTOAccessException {
		// model.setSandBoxMode(true);
		String key = comp.getKey();
		if (key.startsWith(IBHComponent.MINVALUE)
				|| key.startsWith(IBHComponent.MAXVALUE)) {
			// interval
			String baseKey = key.toString().replaceFirst("^.*_", "");
			IBHModelComponent minComp = view.getBHModelComponents().get(
					IBHComponent.MINVALUE + baseKey);
			IBHModelComponent maxComp = view.getBHModelComponents().get(
					IBHComponent.MAXVALUE + baseKey);

			if (getValidator().validate(minComp).hasErrors()
					|| getValidator().validate(maxComp).hasErrors())
				return;

			IValue minValue = minComp.getValue();
			double min = 0;
			if (minValue instanceof IntegerValue)
				min = ((IntegerValue) minValue).getValue();
			else if (minValue instanceof DoubleValue)
				min = ((DoubleValue) minValue).getValue();

			IValue maxValue = maxComp.getValue();
			double max = 0;
			if (maxValue instanceof IntegerValue)
				max = ((IntegerValue) maxValue).getValue();
			else if (minValue instanceof DoubleValue)
				max = ((DoubleValue) maxValue).getValue();

			model.put(baseKey, new IntervalValue(min, max));
		} else {
			// "normal" value
			model.put(key, comp.getValue());
		}
	}

	/**
	 * Method to remove value from DTO e.g. necessary when validation went wrong
	 * 
	 * @param comp
	 * @param model
	 * @throws DTOAccessException
	 */
	public void removeFromModel(IBHModelComponent comp)
			throws DTOAccessException {
		removeFromModel(comp.getKey());
	}

	public void removeFromModel(Object key1) throws DTOAccessException {
		String key = key1.toString();
		if (key.startsWith(IBHComponent.MINVALUE)
				|| key.startsWith(IBHComponent.MAXVALUE)) {
			// interval
			key = key.toString().replaceFirst("^.*_", "");
		}
		model.remove(key);
	}

	public void loadAllToView() {
		for (String key : model.getKeys()) {
			loadToView(key, false);
		}
		ValidationResult validationResult = view.revalidate();
		model.setValid(!validationResult.hasErrors());
	}

	public void loadToView(Object key1) throws DTOAccessException {
		loadToView(key1, true);
	}

	public void loadToView(Object key1, boolean revalidate)
			throws DTOAccessException {
		String key = key1.toString();
		IValue value = null;
		try {
			value = model.get(key);
		} catch (DTOAccessException e) {
		}
		DoubleValue min = null, max = null;
		if (value instanceof IntervalValue) {
			min = new DoubleValue(((IntervalValue) value).getMin());
			max = new DoubleValue(((IntervalValue) value).getMax());
		}
		IBHModelComponent comp = view.getBHModelComponents().get(key);
		if (comp != null) {
			// "normal" values
			if (min == null)
				comp.setValue(value);
			else
				comp.setValue(min);
		} else {
			// interval
			IBHModelComponent minComp = view.getBHModelComponents().get(
					IBHComponent.MINVALUE + key.toString());

			if (minComp != null) {
				if (min == null)
					minComp.setValue(value);
				else
					minComp.setValue(min);
			}

			IBHModelComponent maxComp = view.getBHModelComponents().get(
					IBHComponent.MAXVALUE + key.toString());

			if (maxComp != null) {
				if (max == null)
					maxComp.setValue(value);
				else
					maxComp.setValue(max);
			}
		}

		if (revalidate) {
			ValidationResult validationResult = view.revalidate();
			model.setValid(!validationResult.hasErrors());
		}
	}
}
