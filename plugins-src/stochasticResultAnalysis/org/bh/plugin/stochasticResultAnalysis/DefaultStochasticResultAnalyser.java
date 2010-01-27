package org.bh.plugin.stochasticResultAnalysis;

import java.awt.Component;

import org.apache.log4j.Logger;
import org.bh.data.DTOScenario;
import org.bh.data.types.DistributionMap;
import org.bh.gui.view.View;
import org.bh.gui.view.ViewException;
import org.bh.platform.IStochasticResultAnalyser;

public class DefaultStochasticResultAnalyser implements IStochasticResultAnalyser{
	@Override
	public Component setResult(DTOScenario scenario, DistributionMap result) {
		try {
			View view = new ViewBHStochasticResultPanel(scenario, result);
			new BHStochasticResultController(view, result, scenario);
			return view.getViewPanel();
		} catch (ViewException e) {
			Logger.getLogger(DefaultStochasticResultAnalyser.class).error("Cannot create view", e);
			return null;
		}
	}

}
