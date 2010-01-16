package org.bh.gui.swing;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import org.bh.gui.chart.BHChartFactory;
import org.bh.gui.chart.BHChartPanel;
import org.bh.platform.DashBoardController;

public class BHDashBoardPanel extends JPanel {

	private BHChartPanel stackedBarChart;
	private BHDescriptionLabel noOfScenarios;
	private BHDescriptionLabel noOfScenariosDescr;

	private BHDescriptionLabel svRange;
	private BHDescriptionLabel svRangeDescr;
	
	public BHDashBoardPanel() {
		initialize();
	}

	public void initialize() {
		setLayout(new BorderLayout());
		JPanel txtArea = new JPanel();

		double border = 10;
		double size[][] = {
				{ border, TableLayoutConstants.PREFERRED, border, TableLayoutConstants.PREFERRED,
						border }, // Columns
				{ border, TableLayoutConstants.PREFERRED, border, TableLayoutConstants.PREFERRED,
						border } }; // Rows
		txtArea.setLayout(new TableLayout(size));

		noOfScenariosDescr = new  BHDescriptionLabel(DashBoardController.Keys.NO_OF_SCENARIOS_DESCR);
		txtArea.add(noOfScenariosDescr,"1,1");
		
		noOfScenarios = new BHDescriptionLabel(DashBoardController.Keys.NO_OF_SCENARIOS);
		txtArea.add(noOfScenarios,"3,1");
		
		svRangeDescr = new  BHDescriptionLabel(DashBoardController.Keys.SV_RANGE_DESCR);
		txtArea.add(svRangeDescr,"1,3");
		
		svRange = new BHDescriptionLabel(DashBoardController.Keys.SV_RANGE);
		txtArea.add(svRange,"3,3");
		
		add(txtArea, BorderLayout.NORTH);

		JPanel chartArea = new JPanel();

		stackedBarChart = BHChartFactory
				.getStackedBarChart(DashBoardController.ChartKeys.DB_SBC_SV);
		chartArea.add(stackedBarChart);
		
		add(chartArea, BorderLayout.SOUTH);
	}
//	
//	public static void main(String[] arg) {
//		JFrame f = new JFrame();
//		f.add(new BHDashBoardPanel());
//		f.setSize(300, 300);
//		f.setVisible(true);
//	}
}
