package org.bh.gui.swing;

import java.util.Map;

import org.bh.controller.OutputController;
import org.bh.data.DTOPeriod;
import org.bh.data.DTOScenario;
import org.bh.data.types.Calculable;
import org.bh.gui.View;
import org.bh.gui.chart.IBHAddValue;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;

public class BHDashBoardController extends OutputController {
	
    public static enum ChartKeys{
    	//DashBoard_StackedBarChart_ShareholderValue
    	DB_SBC_SV;
    
    	@Override
    	public String toString() {
    		return getClass().getName() + "." + super.toString();
    	}
    }

	Map<DTOScenario, Map<?,?>> result;
    
    public BHDashBoardController(View view, Map<DTOScenario, Map<?,?>> result){
    	super(view);
    	this.result = result;
    }

    
    // TODO make this block fit with DashBoard chart
    @Override
    public void setResult(Map<String, Calculable[]> result, DTOScenario scenario) {
		ITranslator translator = Services.getTranslator();
		super.setResult(result, scenario);
		
		if (scenario.getDCFMethod().getUniqueId().equals("apv")) {
		
			IBHAddValue comp = super.view.getBHchartComponents().get(ChartKeys.DB_SBC_SV.toString());
			comp.addValue(result.get("org.bh.plugin.apv.APVCalculator$Result.PRESENT_VALUE_FCF")[0].parse(),translator.translate(ChartKeys.DB_SBC_SV), translator.translate("org.bh.plugin.apv.APVCalculator$Result.PRESENT_VALUE_FCF"));
			comp.addValue(result.get("org.bh.plugin.apv.APVCalculator$Result.PRESENT_VALUE_TAX_SHIELD")[0].parse(),translator.translate(ChartKeys.DB_SBC_SV), translator.translate("org.bh.plugin.apv.APVCalculator$Result.PRESENT_VALUE_TAX_SHIELD"));
			comp.addValue((result.get("org.bh.calculation.IShareholderValueCalculator$Result.DEBT")[0].parse().doubleValue() * -1) ,translator.translate(ChartKeys.DB_SBC_SV), translator.translate("org.bh.calculation.IShareholderValueCalculator$Result.DEBT"));
			comp.addValue(result.get("org.bh.calculation.IShareholderValueCalculator$Result.SHAREHOLDER_VALUE")[0].parse(),translator.translate(ChartKeys.DB_SBC_SV), translator.translate("org.bh.calculation.IShareholderValueCalculator$Result.SHAREHOLDER_VALUE"));
			
			IBHAddValue comp2 = super.view.getBHchartComponents().get(ChartKeys.DB_SBC_SV.toString());
			int length = result.get("org.bh.plugin.apv.APVCalculator$Result.PRESENT_VALUE_FCF").length;
			for (int i = 0; i < length; i++) {
				String name = scenario.getChildren().get(i).get(DTOPeriod.Key.NAME).toString();
				comp2.addValue(result.get("org.bh.plugin.apv.APVCalculator$Result.PRESENT_VALUE_FCF")[i].parse(), translator.translate("org.bh.plugin.apv.APVCalculator$Result.PRESENT_VALUE_FCF"), name);
				comp2.addValue(result.get("org.bh.calculation.IShareholderValueCalculator$Result.DEBT")[i].parse(), translator.translate("org.bh.calculation.IShareholderValueCalculator$Result.DEBT"), name);
			}
		}
	}
}

