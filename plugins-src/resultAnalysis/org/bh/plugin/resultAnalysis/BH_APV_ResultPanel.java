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
 * View for APV results containing APV charts
 * @author Marco Hammel
 * @version 1.0
 */
@SuppressWarnings("serial")
public class BH_APV_ResultPanel extends JPanel {

    private static final Logger log = Logger.getLogger(BH_APV_ResultPanel.class);
    // APV Charts
    private BHChartPanel apvWFShareholderValues;
    private BHChartPanel apvBCCapitalStructure;

    public BH_APV_ResultPanel() {
	initialize();
    }

    public void initialize() {
	double border = 30;

	double size[][] = { { TableLayoutConstants.PREFERRED}, // Columns
		{ border, TableLayoutConstants.PREFERRED, border, TableLayoutConstants.PREFERRED, border} }; // Rows
	this.setLayout(new TableLayout(size));

	// All APV Charts
	apvWFShareholderValues = BHChartFactory.getWaterfallChart(BHResultController.ChartKeys.APV_WF_SV, false, true);
	apvBCCapitalStructure = BHChartFactory.getStackedBarChart(BHResultController.ChartKeys.APV_BC_CS, true, true);

	this.add(apvWFShareholderValues, "0,1");
	this.add(apvBCCapitalStructure, "0,3");
    }
}
