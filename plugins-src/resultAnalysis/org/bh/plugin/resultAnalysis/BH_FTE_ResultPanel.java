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
public class BH_FTE_ResultPanel extends JPanel {

    private static final Logger log = Logger.getLogger(BH_FTE_ResultPanel.class);
    private BHChartPanel fteShareholderValue;
    private BHChartPanel fteCapitalStructure;
    private BHChartPanel fteFlowToEquity;
    
    public BH_FTE_ResultPanel(boolean isAllSelected){
    	initialize(isAllSelected);
    }
    
    public void initialize(boolean isAllSelected) {

        double border = 30;
        
        if(!isAllSelected){
	        double size[][] = {{border, TableLayoutConstants.PREFERRED, border, TableLayoutConstants.PREFERRED, border, TableLayoutConstants.PREFERRED, border}, // Columns
	            {border, TableLayoutConstants.PREFERRED, border, TableLayoutConstants.PREFERRED, border, TableLayoutConstants.PREFERRED}}; // Rows
        	this.setLayout(new TableLayout(size));
        }else{
        	double size[][] = {{border, TableLayoutConstants.PREFERRED, border, TableLayoutConstants.PREFERRED, border, TableLayoutConstants.PREFERRED, border}, // Columns
    	            {border, TableLayoutConstants.PREFERRED, border, TableLayoutConstants.PREFERRED}}; // Rows
            	this.setLayout(new TableLayout(size));
        }

        //All FTE Charts
        fteShareholderValue = BHChartFactory.getBarChart( BHResultController.ChartKeys.FTE_BC_SV.toString(), false,false);
        fteCapitalStructure = BHChartFactory.getStackedBarChart( BHResultController.ChartKeys.FTE_BC_CS.toString(), true, false);
        fteFlowToEquity = BHChartFactory.getBarChart( BHResultController.ChartKeys.FTE_BC_FTE.toString(), false, false);
        
        if(!isAllSelected){
	        this.add(fteShareholderValue, "3,3");
	        //this.add(fteCapitalStructure, "3,5");
	        this.add(fteFlowToEquity, "3,7");
        }else{
        	this.add(fteFlowToEquity, "3,1");
        }

    }
//    public static void main(String[] args) {
//
//    	JFrame test = new JFrame("Test for ResultPanel");
//    	test.setContentPane(new BH_APV_ResultPanel());
//    	test.addWindowListener(new WindowAdapter() {
//
//    	    @Override
//    		public void windowClosing(WindowEvent e) {
//    		System.exit(0);
//    	    }
//    	});
//    	//test.pack();
//    	test.setVisible(true);
//
//    }
}
