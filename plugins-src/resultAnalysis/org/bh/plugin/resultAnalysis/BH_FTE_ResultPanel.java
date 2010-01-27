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
 * View for FTE results containing FTE charts
 * @author Marco Hammel
 * @version 1.0
 */
public class BH_FTE_ResultPanel extends JPanel {

    private static final Logger log = Logger.getLogger(BH_FTE_ResultPanel.class);
    private BHChartPanel fteFlowToEquity;
    private BHChartPanel fteReturnRate;

    public BH_FTE_ResultPanel(boolean isAllSelected) {
        initialize(isAllSelected);
    }

    public void initialize(boolean isAllSelected) {

        double border = 30;

        if (!isAllSelected) {
            double size[][] = {{border, TableLayoutConstants.PREFERRED, border, TableLayoutConstants.PREFERRED, border, TableLayoutConstants.PREFERRED, border}, // Columns
                {border, TableLayoutConstants.PREFERRED, border, TableLayoutConstants.PREFERRED, border, TableLayoutConstants.PREFERRED}}; // Rows
            this.setLayout(new TableLayout(size));
        } else {
            double size[][] = {{border, TableLayoutConstants.PREFERRED, border, TableLayoutConstants.PREFERRED, border, TableLayoutConstants.PREFERRED, border}, // Columns
                {border, TableLayoutConstants.PREFERRED, border, TableLayoutConstants.PREFERRED}}; // Rows
            this.setLayout(new TableLayout(size));
        }

        //All FTE Charts
        fteFlowToEquity = BHChartFactory.getBarChart(BHResultController.ChartKeys.FTE_BC_FTE.toString(), true, true);
        fteReturnRate = BHChartFactory.getBarChart(BHResultController.ChartKeys.FTE_BC_RR, true, true);

        if (!isAllSelected) {
            this.add(fteFlowToEquity, "3,1");
            this.add(fteReturnRate, "3,3");
        } else {
            this.add(fteFlowToEquity, "3,1");
        }

    }
}
