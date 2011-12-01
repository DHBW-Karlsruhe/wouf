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
import org.bh.gui.IBHComponent;
import org.bh.gui.IBHModelComponent;
import org.bh.gui.view.View;
import org.bh.gui.view.ViewEvent;
import org.bh.validation.BHValidityEngine;

import com.jgoodies.validation.ValidationResult;

/**
 * every plugin controller with a writer exercise should be a subclass of this class
 * @author Marco Hammel
 * @author Robert
 * @version 1.0
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
        /**
         * deliver <code>IDTO</code>
         * @return active model
         */
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
		if (view != null)
			view.addViewListener(this);
	}

	/*
	 * method for data transfer between all view components having a binding
         * key to the active model instance
         * @throws DTOAccessException in case of error while saving
	 */
	public void saveAllToModel() throws DTOAccessException {
		for (IBHModelComponent comp : view.getBHModelComponents().values()) {
			saveToModel(comp);
		}
	}
        /**
         * for data transfer between a specific ui component with a binding key to a model component
         * @param comp
         * @throws DTOAccessException in case of error while saving
         */
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
		}
		else if(key.startsWith("org.bh.data.DTOScenario$Key.TIMESERIES_PROCESS")){
			//Timeseriesperiod checkbox sould not be saved!
		}
		else {
			// "normal" value
			model.put(key, comp.getValue());
		}
	}

	/**
	 * Method to remove value from DTO e.g. necessary when validation went wrong
	 * @param comp
	 * @param model
	 * @throws DTOAccessException
	 */
	public void removeFromModel(IBHModelComponent comp)
			throws DTOAccessException {
		removeFromModel(comp.getKey());
	}
        /**
         * remove a specific value from the active model DTO
         * @param key1 binding key of the component
         * @throws DTOAccessException
         */
	public void removeFromModel(Object key1) throws DTOAccessException {
		String key = key1.toString();
		if (key.startsWith(IBHComponent.MINVALUE)
				|| key.startsWith(IBHComponent.MAXVALUE)) {
			// interval
			key = key.toString().replaceFirst("^.*_", "");
		}
		model.remove(key);
	}
        /**
         * Map every value of a model DTO to ui components with a matching binding key
         */
	public void loadAllToView() {
		for (String key : model.getKeys()) {
			loadToView(key, false);
		}
		ValidationResult validationResult = view.revalidate();
		model.setValid(!validationResult.hasErrors());
	}
        /**
         * map a specific model DTÓ value to a matching ui component
         * @param key1 key of the DTO component
         * @throws DTOAccessException
         */
	public void loadToView(Object key1) throws DTOAccessException {
		loadToView(key1, true);
	}
        /**
         * map a specific model DTÓ value to a matching ui component can revalidate ui
         * performance critical in case of revalidation.
         * @see loadToView
         * @param key1 key of the DTO component
         * @param revalidate if true ui will be revalidated
         * @throws DTOAccessException if DTO not contains a field with the key
         */
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
