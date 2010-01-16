package org.bh.plugin.resultAnalysis;

import java.awt.Component;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bh.data.DTOScenario;
import org.bh.data.types.Calculable;
import org.bh.gui.IDeterministicResultAnalyser;
import org.bh.gui.View;
import org.bh.gui.ViewException;

public class DefaultResultAnalyser implements IDeterministicResultAnalyser {
	@Override
	public Component setResult(DTOScenario scenario, Map<String, Calculable[]> result) {
		try {
			View view = new ViewBHResultPanel(scenario, result);
			new BHResultController(view, result, scenario);
			return view.getViewPanel();
		} catch (ViewException e) {
			Logger.getLogger(DefaultResultAnalyser.class).error("Cannot create view", e);
			return null;
		}
	}
}
