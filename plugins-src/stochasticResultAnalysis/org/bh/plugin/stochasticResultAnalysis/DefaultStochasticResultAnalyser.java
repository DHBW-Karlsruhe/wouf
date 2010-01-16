package org.bh.plugin.stochasticResultAnalysis;

import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.bh.data.DTOScenario;
import org.bh.data.types.DistributionMap;
import org.bh.gui.IStochasticResultAnalyser;
import org.bh.gui.View;
import org.bh.gui.ViewException;

public class DefaultStochasticResultAnalyser implements IStochasticResultAnalyser{
	
	public void setResult(DTOScenario scenario, DistributionMap result, JPanel panel) {
		try {
			View view = new ViewBHStochasticResultPanel(scenario, result);
			panel.add(view.getViewPanel());
			new BHStochasticResultController(view, result, scenario);
		} catch (ViewException e) {
			Logger.getLogger(DefaultStochasticResultAnalyser.class).error("Cannot create view", e);
		}
	}

}
