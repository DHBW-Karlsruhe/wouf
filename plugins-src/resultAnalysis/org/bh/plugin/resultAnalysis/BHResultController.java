/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.plugin.resultAnalysis;

import java.util.Map;

import org.bh.controller.OutputController;
import org.bh.data.DTOScenario;
import org.bh.data.types.Calculable;
import org.bh.gui.View;
import org.bh.gui.chart.IBHAddValue;

/**
 *
 * @author Marco Hammel
 * @author Sebastian Scharfenberger
 */
public class BHResultController extends OutputController{

    public static enum ChartKeys{
    	APV_WF_SV,
    	APV_BC_CS,
    	FCF_WF_SV,
    	FCF_BC_CS,
    	FCF_BC_FCF,
    	FCF_BC_RR,
    	FTE_BC_SV,
    	FTE_BC_CS,
    	FTE_BC_FTE;

        @Override
        public String toString() {
            return getClass().getName() + "." + super.toString();
        }
       
    }
    public BHResultController(View view, Map<String, Calculable[]> result, DTOScenario scenario){
        super(view, result, scenario);
    }

    @Override
    public void setResult(Map<String, Calculable[]> result, DTOScenario scenario){
    	
    	super.setResult(result, scenario);
    	
    	if(scenario.getDCFMethod().getUniqueId().equals("apv")){
    		
    		IBHAddValue comp = super.view.getBHchartComponents().get(ChartKeys.APV_WF_SV.toString());
    		comp.addValue(result.get("org.bh.plugin.apv.APVCalculator$Result.PRESENT_VALUE_FCF")[0].parse(),1, "1");
    		comp.addValue(result.get("org.bh.plugin.apv.APVCalculator$Result.PRESENT_VALUE_TAX_SHIELD")[0].parse(),1, "2");
    		comp.addValue(result.get("org.bh.calculation.IShareholderValueCalculator$Result.DEBT")[0].parse(),1, "3");
    		comp.addValue(result.get("org.bh.calculation.IShareholderValueCalculator$Result.SHAREHOLDER_VALUE")[0].parse(),1, "4");
    		
    		IBHAddValue comp2 = super.view.getBHchartComponents().get(ChartKeys.APV_BC_CS.toString());
    		int length = result.get("org.bh.plugin.apv.APVCalculator$Result.PRESENT_VALUE_FCF").length;
    		for (int i = 0; i < length; i++) {
				comp2.addValue(result.get("org.bh.plugin.apv.APVCalculator$Result.PRESENT_VALUE_FCF")[i].parse(), 1, String.valueOf(i));
				comp2.addValue(result.get("org.bh.calculation.IShareholderValueCalculator$Result.DEBT")[i].parse(), 2, String.valueOf(i));
    		}
    		
    	}else if(scenario.getDCFMethod().getUniqueId().equals("fcf")){
    		
    		IBHAddValue comp = super.view.getBHchartComponents().get(ChartKeys.FCF_WF_SV.toString());
    		//TODO
    		
    		IBHAddValue comp2 = super.view.getBHchartComponents().get(ChartKeys.FCF_BC_CS.toString());
    		for (int i = 0; i < result.get("org.bh.plugin.fcf.FCFCalculator$Result.PRESENT_VALUE_TAX_SHIELD").length; i++) {
				comp2.addValue(result.get("org.bh.plugin.fcf.FCFCalculator$Result.PRESENT_VALUE_TAX_SHIELD")[i].parse(), 1, String.valueOf(i));
				comp2.addValue(result.get("org.bh.calculation.IShareholderValueCalculator$Result.DEBT")[i].parse(), 2, String.valueOf(i));
    		}
    		
    		IBHAddValue comp3 = super.view.getBHchartComponents().get(ChartKeys.FCF_BC_FCF.toString());
			if(result.get("org.bh.calculation.IShareholderValueCalculator$Result.FREE_CASH_FLOW")[0] != null)
				comp3.addValue(result.get("org.bh.calculation.IShareholderValueCalculator$Result.FREE_CASH_FLOW")[0].parse(), 1, String.valueOf(0));
    		for (int i = 1; i < result.get("org.bh.calculation.IShareholderValueCalculator$Result.FREE_CASH_FLOW").length; i++) {
				comp3.addValue(result.get("org.bh.calculation.IShareholderValueCalculator$Result.FREE_CASH_FLOW")[i].parse(), 1, String.valueOf(i));
    		}
    		
    		IBHAddValue comp4 = super.view.getBHchartComponents().get(ChartKeys.FCF_BC_RR.toString());
    		for (int i = 0; i < result.get("org.bh.plugin.fcf.FCFCalculator$Result.EQUITY_RETURN_RATE_FCF").length; i++) {
				comp4.addValue(result.get("org.bh.plugin.fcf.FCFCalculator$Result.EQUITY_RETURN_RATE_FCF")[i].parse(), 1, String.valueOf(i));
				comp4.addValue(result.get("org.bh.calculation.IShareholderValueCalculator$Result.DEBT_RETURN_RATE")[0].parse(), 2, String.valueOf(i));
    		}
    		
    	}else if(scenario.getDCFMethod().getUniqueId().equals("fte")){
    		
    		IBHAddValue comp = super.view.getBHchartComponents().get(ChartKeys.FTE_BC_SV.toString());
    		comp.addValue(result.get("org.bh.calculation.IShareholderValueCalculator$Result.SHAREHOLDER_VALUE")[0].parse(), "1");
    		
    		IBHAddValue comp2 = super.view.getBHchartComponents().get(ChartKeys.FTE_BC_CS.toString());
    		for (int i = 0; i < result.get("org.bh.plugin.fte.FTECalculator$Result.PRESENT_VALUE_TAX_SHIELD").length; i++) {
				comp2.addValue(result.get("org.bh.plugin.fte.FTECalculator$Result.PRESENT_VALUE_TAX_SHIELD")[i].parse(), 1, String.valueOf(i));
				comp2.addValue(result.get("org.bh.calculation.IShareholderValueCalculator$Result.DEBT")[i].parse(), 2, String.valueOf(i));
    		}
    		
    		IBHAddValue comp3 = super.view.getBHchartComponents().get(ChartKeys.FTE_BC_FTE.toString());
    		for (int i = 0; i < result.get("org.bh.plugin.fte.FTECalculator$Result.FLOW_TO_EQUITY").length; i++) {
				comp3.addValue(result.get("org.bh.plugin.fte.FTECalculator$Result.FLOW_TO_EQUITY")[i].parse(), String.valueOf(i));
    		}
    		
    	}else {
    		
    	}
    }
}
