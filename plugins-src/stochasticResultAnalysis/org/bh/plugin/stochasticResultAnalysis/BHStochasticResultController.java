package org.bh.plugin.stochasticResultAnalysis;

import org.bh.controller.OutputController;
import org.bh.data.DTOScenario;
import org.bh.data.types.DistributionMap;
import org.bh.gui.View;
import org.bh.gui.chart.IBHAddValue;
import org.bh.platform.Services;

public class BHStochasticResultController extends OutputController{
	public static enum ChartKeys {
		DISTRIBUTION_CHART;

		@Override
		public String toString() {
			return getClass().getName() + "." + super.toString();
		}

	}
	
	public BHStochasticResultController(View view, DistributionMap result, DTOScenario scenario){
		super(view, result, scenario);
	}
	@Override
	public void setResult(DistributionMap result, DTOScenario scenario) {
		IBHAddValue comp = super.view.getBHchartComponents().get(ChartKeys.DISTRIBUTION_CHART.toString());
		comp.addSeries(Services.getTranslator().translate(ChartKeys.DISTRIBUTION_CHART.toString()), result.toDoubleArray());
	}
	
}
