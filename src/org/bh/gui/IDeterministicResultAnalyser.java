package org.bh.gui;

import java.awt.Component;
import java.util.Map;

import org.bh.data.DTOScenario;
import org.bh.data.types.Calculable;

public interface IDeterministicResultAnalyser {
	/**
	 * When called, the plugin should analyse the results and return the
	 * Component where the results are displayed.
	 * 
	 * @param result
	 *            The result to analyse.
	 */
	public Component setResult(DTOScenario scenario,
			Map<String, Calculable[]> result);
}
