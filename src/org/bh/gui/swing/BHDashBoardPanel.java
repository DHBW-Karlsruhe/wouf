package org.bh.gui.swing;

import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.bh.data.DTOScenario;
import org.bh.gui.chart.BHChartFactory;
import org.bh.gui.chart.BHChartPanel;
import org.bh.plugin.resultAnalysis.BHResultController;

public class BHDashBoardPanel extends JPanel {
	public static enum dashbKeys{
		BAR_SV;
	}
	private BHChartPanel chartPanel;
	

	public BHDashBoardPanel(Map<DTOScenario, Map<?, ?>> results) {
		initialize(results);
	}
	
	public void initialize(Map<DTOScenario, Map<?, ?>> results) {
		add(new JLabel("TEST"));
		chartPanel = BHChartFactory.getStackedBarChart("UW Vergleich", "Szenario", "GE", "Tests");
		for(java.util.Map.Entry<DTOScenario, Map<?, ?>> e : results.entrySet()){
			add(new JLabel( e.getKey().get(DTOScenario.Key.IDENTIFIER).toString()));
		}
		
		
		this.add(chartPanel);
	}
	
}

