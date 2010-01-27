package org.bh.gui.swing;

//import info.clearthought.layout.TableLayout;
//import info.clearthought.layout.TableLayoutConstants;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import org.bh.gui.chart.BHChartFactory;
import org.bh.gui.chart.BHChartPanel;
import org.bh.platform.DashBoardController;

/**
 * In this class a (panel for the) dashboard is created, to compare
 * results of scenarios against each other.
 * 
 * @author Norman Weisenburger, Patrick Heinz
 * @version 1.0, 22.01.2010
 * 
 */
public final class BHDashBoardPanel extends JPanel {

	private BHChartPanel stackedBarChart;

	
	public BHDashBoardPanel() {
		initialize();
	}

	public void initialize() {
		setLayout(new BorderLayout());


		JPanel chartArea = new JPanel();

		stackedBarChart = BHChartFactory
				.getStackedBarChart(DashBoardController.ChartKeys.DB_SBC_SV, true, true);
		chartArea.add(stackedBarChart);
		
		add(chartArea, BorderLayout.CENTER);
	}
}