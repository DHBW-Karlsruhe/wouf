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
 * View for FCF results containing FCF charts
 * @author Marco Hammel
 * @version 1.0
 */
@SuppressWarnings("serial")
public class BH_FCF_ResultPanel extends JPanel {

    private static final Logger log = Logger.getLogger(BH_FCF_ResultPanel.class);

    public BH_FCF_ResultPanel(boolean isAllSelected) {
        this.initialize(isAllSelected);
    }

    public void initialize(boolean isAllSelected) {

        double border = 30;

        double size[][] = {{border, TableLayoutConstants.PREFERRED, border, TableLayoutConstants.PREFERRED, border, TableLayoutConstants.PREFERRED, border}, // Columns
            {border, TableLayoutConstants.PREFERRED, border, TableLayoutConstants.PREFERRED, border, TableLayoutConstants.PREFERRED}}; // Rows
        this.setLayout(new TableLayout(size));
        //      }

        //All charts
        //TODO Schön Darstellen!!!!
        BHChartPanel fcf_shareholderValue = BHChartFactory.getWaterfallChart(BHResultController.ChartKeys.FCF_WF_SV, false, false);
        BHChartPanel fcf_freecashflow = BHChartFactory.getBarChart(BHResultController.ChartKeys.FCF_BC_FCF, true, false);
        BHChartPanel fcf_returnrate = BHChartFactory.getBarChart(BHResultController.ChartKeys.FCF_BC_RR, true, false);
        if (!isAllSelected) {
            this.add(fcf_shareholderValue, "3,1");
            this.add(fcf_freecashflow, "3,3");
            this.add(fcf_returnrate, "3,5");
        }else{
            this.add(fcf_freecashflow, "3,1");
            this.add(fcf_returnrate, "3,3");
        }

//        }
    }
}
