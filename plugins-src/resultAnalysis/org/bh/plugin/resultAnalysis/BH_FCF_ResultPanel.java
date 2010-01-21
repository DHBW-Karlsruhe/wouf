/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bh.plugin.resultAnalysis;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.bh.gui.chart.BHChartFactory;
import org.bh.gui.chart.BHChartPanel;

/**
 *
 * @author Marco Hammel
 */
@SuppressWarnings("serial")
public class BH_FCF_ResultPanel extends JPanel {

    private static final Logger log = Logger.getLogger(BH_FCF_ResultPanel.class);
    
    public BH_FCF_ResultPanel(boolean isAllSelected){
        this.initialize(isAllSelected);
    }

    public void initialize(boolean isAllSelected) {

        double border = 30;
//        if(!isAllSelected){
//	        double size[][] = {{border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border}, // Columns
//	            {border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, border, TableLayout.PREFERRED, 
//	        	 border, TableLayout.PREFERRED, border,	TableLayout.PREFERRED, border, TableLayout.PREFERRED, 
//	        	 border, TableLayout.PREFERRED,border, TableLayout.PREFERRED}}; // Rows
//	        this.setLayout(new TableLayout(size));
//        }else{
        	double size[][] = {{border, TableLayoutConstants.PREFERRED, border, TableLayoutConstants.PREFERRED, border, TableLayoutConstants.PREFERRED, border}, // Columns
    	            {border, TableLayoutConstants.PREFERRED, border, TableLayoutConstants.PREFERRED, border, TableLayoutConstants.PREFERRED}}; // Rows
    	        this.setLayout(new TableLayout(size));
  //      }

        //All charts
        //TODO Schön Darstellen!!!!
        BHChartPanel fcf_shareholderValue = BHChartFactory.getWaterfallChart( BHResultController.ChartKeys.FCF_WF_SV, false, false);
        BHChartPanel fcf_fcf = BHChartFactory.getBarChart( BHResultController.ChartKeys.FCF_BC_FCF, false, false);
        BHChartPanel fcf_returnRate = BHChartFactory.getBarChart( BHResultController.ChartKeys.FCF_BC_RR, true, false);        
//        
//        if(!isAllSelected){
//	        this.add(infiniteFormula, "3,3");
//	        this.add(valueInfiniteFormula, "3,5");
//	        this.add(finiteFormula, "3,7");
//	        this.add(valueFiniteFormula, "3,9");
//	        this.add(fcf_shareholderValue, "3,11");
//	        this.add(fcf_fcf, "3,13");
//	        this.add(fcf_returnRate, "3,15");
//        }else{
	        this.add(fcf_shareholderValue, "3,1");
	        this.add(fcf_fcf, "3,3");
	        this.add(fcf_returnRate, "3,5");
//        }
    }
}
