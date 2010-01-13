/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.plugin.resultAnalysis;

import java.util.Map;
import org.bh.controller.OutputController;
import org.bh.data.types.Calculable;
import org.bh.gui.View;

/**
 *
 * @author Marco Hammel
 */
public class BHResultController extends OutputController{

    public static enum ChartKeys{
        BAR_SHAREHOLDER_VALUE,
        STACK_CV,
        WATER_CV,
        BAR_FCF_YEAR,
        STACK_WACC,
        BAR_FTE,
        WATER_FTE;

        @Override
        public String toString() {
            return getClass().getName() + "." + super.toString();
        }
       
    }
    public BHResultController(View view, Map<String, Calculable> result){
        super(view);
    }

    @Override
    public void setResult(Map<String, Calculable[]> result){
        for(String s : super.view.getBHchartComponents().keySet()){

        }
        super.setResult(result);
    }

}
