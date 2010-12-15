/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bh.plugin.resultAnalysis;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.bh.gui.chart.BHChartFactory;
import org.bh.gui.chart.BHChartPanel;

/**
 * View for FTE results containing FTE charts
 * @author Marco Hammel
 * @version 1.0
 */
@SuppressWarnings("serial")
public class BH_FTE_ResultPanel extends JPanel {

    @SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(BH_FTE_ResultPanel.class);
    private BHChartPanel fteFlowToEquity;
    private BHChartPanel fteReturnRate;

    public BH_FTE_ResultPanel(boolean isAllSelected) {
        initialize(isAllSelected);
    }

    public void initialize(boolean isAllSelected) {

    	this.setLayout(new GridBagLayout());
 		GridBagConstraints c = new GridBagConstraints();

        //All FTE Charts
        fteFlowToEquity = BHChartFactory.getBarChart(BHResultController.ChartKeys.FTE_BC_FTE.toString(), true, true);
        fteReturnRate = BHChartFactory.getBarChart(BHResultController.ChartKeys.FTE_BC_RR, true, true);

        //add components to Result Pane
        if (!isAllSelected) {
        	c.fill = GridBagConstraints.HORIZONTAL;
    		c.gridx = 0;
    		c.gridy = 0;
    		c.insets = new Insets(30,0,0,0); //border top 30
    		c.weightx = 1.0;
            this.add(fteFlowToEquity, c);
            
            c.fill = GridBagConstraints.HORIZONTAL;
    		c.gridx = 0;
    		c.gridy = 1;
    		c.insets = new Insets(30,0,30,0); //border top 30 border bottom 30
    		c.weightx = 1.0;
            this.add(fteReturnRate, c);
        } else {
        	c.fill = GridBagConstraints.HORIZONTAL;
    		c.gridx = 0;
    		c.gridy = 0;
    		c.insets = new Insets(30,0,30,0); //border top 30
    		c.weightx = 1.0;
            this.add(fteFlowToEquity, c);
        }

    }
}
