/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bh.plugin.resultAnalysis;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.bh.gui.chart.BHChartFactory;
import org.bh.gui.chart.BHChartPanel;

/**
 * View for APV results containing APV charts
 * 
 * @author Marco Hammel
 * @version 1.0
 */
@SuppressWarnings("serial")
public class BH_APV_ResultPanel extends JPanel {

	@SuppressWarnings("unused")
	private static final Logger log = Logger
			.getLogger(BH_APV_ResultPanel.class);
	// APV Charts
	private BHChartPanel apvWFShareholderValues;
	private BHChartPanel apvBCCapitalStructure;

	public BH_APV_ResultPanel() {
		initialize();
	}

	public void initialize() {
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// All APV Charts
		apvWFShareholderValues = BHChartFactory.getWaterfallChart(
				BHResultController.ChartKeys.APV_WF_SV, false, true);
		apvBCCapitalStructure = BHChartFactory.getStackedBarChart(
				BHResultController.ChartKeys.APV_BC_CS, true, true);

		//add components to ResultPane
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(30,0,0,0); //border top 30
		c.weightx = 1.0;
		this.add(apvWFShareholderValues, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(30,0,30,0); //border top 30, border bottom 30	
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1.0;
		this.add(apvBCCapitalStructure, c);
	}
}
