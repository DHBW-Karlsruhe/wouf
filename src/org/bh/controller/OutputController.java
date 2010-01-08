/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.controller;

import java.util.Arrays;
import java.util.Map;

import org.bh.data.types.Calculable;
import org.bh.gui.View;
import org.bh.gui.chart.IBHAddValue;
import org.bh.gui.swing.IBHComponent;
import org.bh.gui.swing.IBHModelComponent;

/**
 * 
 * @author Marco Hammel
 */
public class OutputController extends Controller implements IOutputController {
	protected Map<String, Calculable[]> result;

	public OutputController(View view, Map<String, Calculable[]> result) {
		super(view);
		setResult(result);
	}

	public OutputController(View view) {
		super(view);
	}

	public OutputController(Map<String, Calculable[]> result) {
		this(null, result);
	}

	@Override
	public void setResult(Map<String, Calculable[]> result)
			throws ControllerException {
		this.result = result;

		log.debug("Loading results to view");
		for (Map.Entry<String, IBHComponent> entry : view.getBHComponents()
				.entrySet()) {
			String key = entry.getKey();
			IBHComponent comp = entry.getValue();
			Calculable[] values = result.get(key);
			if (values == null)
				continue;

			if (comp instanceof IBHModelComponent) {
				((IBHModelComponent) comp).setValue(values[0]);
			} else if (comp instanceof IBHAddValue) {
				((IBHAddValue) comp).addValues(Arrays.asList(values));
			}
		}
	}
}
