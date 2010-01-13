/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.controller;

import java.util.Arrays;
import java.util.Map;

import org.bh.data.DTOScenario;
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
	protected DTOScenario scenario;

	public OutputController(View view, Map<String, Calculable[]> result, DTOScenario scenario) {
		super(view);
		setResult(result, scenario);
	}

	public OutputController(View view) {
		super(view);
	}

	@Override
	public void setResult(Map<String, Calculable[]> result, DTOScenario scenario)
			throws ControllerException {
		this.result = result;
		this.scenario = scenario;

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
