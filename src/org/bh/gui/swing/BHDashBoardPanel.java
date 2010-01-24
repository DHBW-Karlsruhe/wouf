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
public class BHDashBoardPanel extends JPanel {

	private BHChartPanel stackedBarChart;
//	private BHDescriptionLabel noOfScenarios;
//	private BHDescriptionLabel noOfScenariosDescr;
//
//	private BHDescriptionLabel svRange;
//	private BHDescriptionLabel svRangeDescr;
	
	public BHDashBoardPanel() {
		initialize();
	}

	public void initialize() {
		setLayout(new BorderLayout());
//		JPanel txtArea = new JPanel();
//
//		double border = 10;
//		double size[][] = {
//				{ border, TableLayoutConstants.PREFERRED, border, TableLayoutConstants.PREFERRED,
//						border }, // Columns
//				{ border, TableLayoutConstants.PREFERRED, border, TableLayoutConstants.PREFERRED,
//						border } }; // Rows
//		txtArea.setLayout(new TableLayout(size));
//
//		noOfScenariosDescr = new  BHDescriptionLabel(DashBoardController.Keys.NO_OF_SCENARIOS_DESCR);
//		txtArea.add(noOfScenariosDescr,"1,1");
//		
//		noOfScenarios = new BHDescriptionLabel(DashBoardController.Keys.NO_OF_SCENARIOS);
//		txtArea.add(noOfScenarios,"3,1");
//		
//		svRangeDescr = new  BHDescriptionLabel(DashBoardController.Keys.SV_RANGE_DESCR);
//		txtArea.add(svRangeDescr,"1,3");
//		
//		svRange = new BHDescriptionLabel(DashBoardController.Keys.SV_RANGE);
//		txtArea.add(svRange,"3,3");
//		
//		add(txtArea, BorderLayout.NORTH);

		JPanel chartArea = new JPanel();

		stackedBarChart = BHChartFactory
				.getStackedBarChart(DashBoardController.ChartKeys.DB_SBC_SV, true, true);
		chartArea.add(stackedBarChart);
		
		add(chartArea, BorderLayout.CENTER);
	}
//	
//	public static void main(String[] arg) {
//		JFrame f = new JFrame();
//		f.add(new BHDashBoardPanel());
//		f.setSize(300, 300);
//		f.setVisible(true);
//	}
}
