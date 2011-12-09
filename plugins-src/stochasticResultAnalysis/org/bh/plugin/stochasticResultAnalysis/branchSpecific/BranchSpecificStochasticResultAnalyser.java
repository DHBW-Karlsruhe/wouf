package org.bh.plugin.stochasticResultAnalysis.branchSpecific;

import java.awt.Component;

import org.apache.log4j.Logger;
import org.bh.data.DTOScenario;
import org.bh.data.types.DistributionMap;
import org.bh.gui.view.View;
import org.bh.gui.view.ViewException;
import org.bh.platform.IStochasticResultAnalyser;
import org.bh.plugin.stochasticResultAnalysis.BHStochasticResultController;
import org.bh.plugin.stochasticResultAnalysis.DefaultStochasticResultAnalyser;
import org.bh.plugin.stochasticResultAnalysis.ViewBHStochasticResultPanel;

/**
 * Analyse a result depending on a branch specific representative.
 *
 * <p>
 * This class provides an entrance point for a stochastic result analysis
 * for stochastic processes where you use a branch specific representative to
 * get better results than with your own company.
 *
 * @author Yannick Rödl
 * @version 1.0, 09.12.2011
 *
 */
public class BranchSpecificStochasticResultAnalyser implements
		IStochasticResultAnalyser {
	
	@Override
	public Component setResult(DTOScenario scenario, DistributionMap result) {
		try {
			View view = new ViewBHStochasticResultPanel(scenario, result);
			new BHStochasticResultController(view, result, scenario);
			Logger.getLogger(BranchSpecificStochasticResultAnalyser.class).info("Loaded branch specific stochastic result analyser.");
			return view.getViewPanel();
		} catch (ViewException e) {
			Logger.getLogger(DefaultStochasticResultAnalyser.class).error("Cannot create view", e);
			return null;
		}
	}

	@Override
	public String getUniqueID() {
		return IStochasticResultAnalyser.Keys.BRANCH_SPECIFIC.toString();
	}

}
