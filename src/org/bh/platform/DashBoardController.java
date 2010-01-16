package org.bh.platform;

import java.util.Map;
import java.util.Map.Entry;

import org.bh.calculation.IShareholderValueCalculator;
import org.bh.controller.Controller;
import org.bh.data.DTOScenario;
import org.bh.data.types.Calculable;
import org.bh.data.types.DistributionMap;
import org.bh.data.types.IntervalValue;
import org.bh.gui.View;
import org.bh.gui.chart.IBHAddValue;
import org.bh.platform.i18n.ITranslator;

public class DashBoardController extends Controller{
	
    public static enum ChartKeys{
    	//DashBoard_StackedBarChart_ShareholderValue
    	DB_SBC_SV;
    
    	@Override
    	public String toString() {
    		return getClass().getName() + "." + super.toString();
    	}
    }
    
    public static enum Keys{
    	//DashBoard_StackedBarChart_ShareholderValue
    	NO_OF_SCENARIOS,
    	NO_OF_SCENARIOS_DESCR,
    	SV_RANGE_DESCR,
    	SV_RANGE;
    
    	@Override
    	public String toString() {
    		return getClass().getName() + "." + super.toString();
    	}
    }

	Map<DTOScenario, Map<?,?>> result;
    
	private int valueAtRisk = 95;
	
    public DashBoardController(View view){
    	super(view);
    }
    
    // TODO make this block fit with DashBoard chart
    @SuppressWarnings("unchecked")
	public void setResult(Map<DTOScenario, Map<?,?>> result) {
		ITranslator translator = Services.getTranslator();
		//DB Controller referenz vom feld holen und anzahl setzen
		
		DTOScenario s;
		DistributionMap d;
		Map<String, Calculable[]> r;
		Calculable sv;
		IntervalValue i;
		
		IBHAddValue stackedBarChart = view.getBHchartComponents().get(ChartKeys.DB_SBC_SV.toString());
	
		for(Entry<DTOScenario, Map<?, ?>> e : result.entrySet()){
			s = e.getKey();
			if(s.isDeterministic()) {
				r = (Map<String, Calculable[]>) e.getValue();
				sv = r.get(IShareholderValueCalculator.Result.SHAREHOLDER_VALUE.toString())[0];
				if(sv instanceof IntervalValue) {
					i = (IntervalValue) sv;
					stackedBarChart.addValue(i.getMin(),translator.translate(ChartKeys.DB_SBC_SV), s.get(DTOScenario.Key.NAME).toString() + " (" + translator.translate("IntervalArithmetic") + " )");
					stackedBarChart.addValue(i.getMax() - i.getMin(),translator.translate(ChartKeys.DB_SBC_SV), s.get(DTOScenario.Key.NAME).toString());
				}else { // instance of DoubleValue || IntegerValue
					stackedBarChart.addValue(sv.parse(),translator.translate(ChartKeys.DB_SBC_SV), s.get(DTOScenario.Key.NAME).toString() + " (" + translator.translate("deterministic") + ")");
				}
			}else { //stochastic scenario
				d = (DistributionMap) e.getValue();
				sv = d.valueAtRisk(valueAtRisk);
				i = (IntervalValue) sv;
				stackedBarChart.addValue(i.getMin(),translator.translate(ChartKeys.DB_SBC_SV), s.get(DTOScenario.Key.NAME).toString() + " (" + translator.translate("stochastic") + " )");
				stackedBarChart.addValue(i.getMax() - i.getMin(),translator.translate(ChartKeys.DB_SBC_SV), s.get(DTOScenario.Key.NAME).toString());
			}
			
//			if (scenario.getDCFMethod().getUniqueId().equals("apv")) {
//			
//				IBHAddValue comp = super.view.getBHchartComponents().get(ChartKeys.DB_SBC_SV.toString());
//				comp.addValue(result.get("org.bh.plugin.apv.APVCalculator$Result.PRESENT_VALUE_FCF")[0].parse(),translator.translate(ChartKeys.DB_SBC_SV), translator.translate("org.bh.plugin.apv.APVCalculator$Result.PRESENT_VALUE_FCF"));
//				comp.addValue(result.get("org.bh.plugin.apv.APVCalculator$Result.PRESENT_VALUE_TAX_SHIELD")[0].parse(),translator.translate(ChartKeys.DB_SBC_SV), translator.translate("org.bh.plugin.apv.APVCalculator$Result.PRESENT_VALUE_TAX_SHIELD"));
//				comp.addValue((result.get("org.bh.calculation.IShareholderValueCalculator$Result.DEBT")[0].parse().doubleValue() * -1) ,translator.translate(ChartKeys.DB_SBC_SV), translator.translate("org.bh.calculation.IShareholderValueCalculator$Result.DEBT"));
//				comp.addValue(result.get("org.bh.calculation.IShareholderValueCalculator$Result.SHAREHOLDER_VALUE")[0].parse(),translator.translate(ChartKeys.DB_SBC_SV), translator.translate("org.bh.calculation.IShareholderValueCalculator$Result.SHAREHOLDER_VALUE"));
//				
//				IBHAddValue comp2 = super.view.getBHchartComponents().get(ChartKeys.DB_SBC_SV.toString());
//				int length = result.get("org.bh.plugin.apv.APVCalculator$Result.PRESENT_VALUE_FCF").length;
//				for (int i = 0; i < length; i++) {
//					String name = scenario.getChildren().get(i).get(DTOPeriod.Key.NAME).toString();
//					comp2.addValue(result.get("org.bh.plugin.apv.APVCalculator$Result.PRESENT_VALUE_FCF")[i].parse(), translator.translate("org.bh.plugin.apv.APVCalculator$Result.PRESENT_VALUE_FCF"), name);
//					comp2.addValue(result.get("org.bh.calculation.IShareholderValueCalculator$Result.DEBT")[i].parse(), translator.translate("org.bh.calculation.IShareholderValueCalculator$Result.DEBT"), name);
//				}
//			}
		}
    }
}

