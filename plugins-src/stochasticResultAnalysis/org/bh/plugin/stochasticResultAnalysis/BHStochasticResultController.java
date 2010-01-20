package org.bh.plugin.stochasticResultAnalysis;

import java.util.Map;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import org.bh.controller.OutputController;
import org.bh.data.DTOScenario;
import org.bh.data.types.DistributionMap;
import org.bh.data.types.DoubleValue;
import org.bh.data.types.IntervalValue;
import org.bh.data.types.StringValue;
import org.bh.gui.View;
import org.bh.gui.chart.IBHAddValue;
import org.bh.gui.swing.BHTextField;
import org.bh.gui.swing.IBHModelComponent;
import org.bh.platform.Services;

public class BHStochasticResultController extends OutputController{
	public static enum ChartKeys {
		DISTRIBUTION_CHART,
		STANDARD_DEVIATION,
		AVERAGE,
		RISK_AT_VALUE,
		RISK_AT_VALUE_MIN,
		RISK_AT_VALUE_MAX;

		@Override
		public String toString() {
			return getClass().getName() + "." + super.toString();
		}

	}
	public static enum PanelKeys {
		riskAtValue,
		AVERAGE,
		VALUE;

		@Override
		public String toString() {
			return getClass().getName() + "." + super.toString();
		}

	}
	public BHStochasticResultController(View view, DistributionMap result, DTOScenario scenario){
		super(view, result, scenario);
		((BHTextField)(view.getBHModelComponents().get(ChartKeys.RISK_AT_VALUE.toString()))).addCaretListener(new RiskAtValueListener());
	}
	@Override
	public void setResult(DistributionMap result, DTOScenario scenario) {
		super.setResult(result, scenario);
		IBHAddValue comp = super.view.getBHchartComponents().get(ChartKeys.DISTRIBUTION_CHART.toString());
		comp.addSeries(Services.getTranslator().translate(ChartKeys.DISTRIBUTION_CHART.toString()), result.toDoubleArray(), result.getAmountOfValues(), result.getMaxAmountOfValuesInCluster());
		for (Map.Entry<String, IBHModelComponent> entry : view.getBHModelComponents().entrySet()) {
			if(entry.getKey().equals(ChartKeys.STANDARD_DEVIATION.toString()))
				entry.getValue().setValue(new DoubleValue(result.getStandardDeviation()));
			else if(entry.getKey().equals(ChartKeys.AVERAGE.toString()))
				entry.getValue().setValue(new DoubleValue(result.getAverage()));
			else if(entry.getKey().equals(ChartKeys.RISK_AT_VALUE.toString())){
				if(((BHTextField)(view.getBHModelComponents().get(ChartKeys.RISK_AT_VALUE.toString()))).getValue() != null){
					double confidence = ((DoubleValue)((BHTextField)(view.getBHModelComponents().get(ChartKeys.RISK_AT_VALUE.toString()))).getValue()).getValue();
					calcRiskAtValue(confidence);
				}else
					calcRiskAtValue(null);
			}
		}
		//calcRiskAtValue(confidence);
	}
	public void calcRiskAtValue(Double confidence){
		if(confidence == null){
			for (Map.Entry<String, IBHModelComponent> entry : view.getBHModelComponents().entrySet()) {
				if(entry.getKey().equals(ChartKeys.RISK_AT_VALUE_MAX.toString()))
					entry.getValue().setValue(new StringValue(""));
				else if(entry.getKey().equals(ChartKeys.RISK_AT_VALUE_MIN.toString()))
					entry.getValue().setValue(new StringValue(""));
			}
		}else{
			IntervalValue interval = stochasticResult.valueAtRisk(confidence);
			for (Map.Entry<String, IBHModelComponent> entry : view.getBHModelComponents().entrySet()) {
				if(entry.getKey().equals(ChartKeys.RISK_AT_VALUE_MAX.toString()))
					entry.getValue().setValue(new DoubleValue(interval.getMax()));
				else if(entry.getKey().equals(ChartKeys.RISK_AT_VALUE_MIN.toString()))
					entry.getValue().setValue(new DoubleValue(interval.getMin()));
			}
		}
	}
	class RiskAtValueListener implements CaretListener{

		@Override
		public void caretUpdate(CaretEvent e) {
			if(((BHTextField)(view.getBHModelComponents().get(ChartKeys.RISK_AT_VALUE.toString()))).getValue() != null){
				double confidence = ((DoubleValue)((BHTextField)(view.getBHModelComponents().get(ChartKeys.RISK_AT_VALUE.toString()))).getValue()).getValue();
				calcRiskAtValue(confidence);
			}else
				calcRiskAtValue(null);
		}		
	}
}

