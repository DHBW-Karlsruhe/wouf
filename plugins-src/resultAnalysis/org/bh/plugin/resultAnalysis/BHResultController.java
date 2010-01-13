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
