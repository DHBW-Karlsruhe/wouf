package org.bh.gui.swing;

import java.util.Map;

import javax.swing.JPanel;

import org.bh.gui.chart.BHChartFactory;
import org.bh.gui.chart.BHChartPanel;

public class BHDashBoardPanel extends JPanel {
	public static enum dashbKeys{
		BAR_SV;
	}
	private BHChartPanel chartPanel;

	public BHDashBoardPanel(Map <String, Object> resultMap) {
		initialize(resultMap);
	}
	
	public void initialize(Map <String, Object> result) {
		this.chartPanel = BHChartFactory.getBarChart("", "", "", BHDashBoardPanel.dashbKeys.BAR_SV);
		
		
		
		this.add(chartPanel);
	}
	
}

