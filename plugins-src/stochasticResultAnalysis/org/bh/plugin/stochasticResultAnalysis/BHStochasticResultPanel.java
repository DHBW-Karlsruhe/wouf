package org.bh.plugin.stochasticResultAnalysis;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.bh.data.DTOScenario;
import org.bh.data.types.DistributionMap;
import org.bh.gui.chart.BHChartPanel;
import org.bh.gui.swing.comp.BHButton;
import org.bh.gui.swing.importexport.BHDataExchangeDialog;
import org.bh.platform.IImportExport;
import org.bh.platform.IPrint;
import org.bh.platform.Services;
import org.jfree.chart.JFreeChart;

public class BHStochasticResultPanel extends JPanel{
	
	static final Logger log = Logger.getLogger(BHStochasticResultPanel.class);
	private DTOScenario scenario;
	private DistributionMap result;
	private BHButton exportButton;
	private BHButton printButton;
	JPanel mainPanel = null;
	
	public BHStochasticResultPanel(DTOScenario scenario, DistributionMap result) {
		this.scenario = scenario;
		this.result = result;
		initialize();
	}
	private void initialize(){
		this.setLayout(new BorderLayout());
		exportButton = new BHButton(BHStochasticResultController.PanelKeys.EXPORTSCENARIO);
		exportButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				BHDataExchangeDialog dialog = new BHDataExchangeDialog(null,
						true);
				dialog.setAction(IImportExport.EXP_SCENARIO_RES);
				dialog.setModel(scenario);
				dialog.setResults(result);
				
				dialog.setIconImages(Services.setIcon());
								
				List<JFreeChart> charts = new ArrayList<JFreeChart>();
				for(Component c : mainPanel.getComponents()) {
					if(c instanceof BHChartPanel) {
						BHChartPanel cp = (BHChartPanel) c;
						charts.add(cp.getChart());
					}
				}
				dialog.setCharts(charts);
				
				dialog.setVisible(true);

			}
		});
		
		// printButton
		printButton = new BHButton(BHStochasticResultController.PanelKeys.PRINTSCENARIO);
		printButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Map<String, IPrint> pPlugs = Services.getPrintPlugins(IPrint.PRINT_SCENARIO_RES);
				
				List<JFreeChart> charts = new ArrayList<JFreeChart>();
				for(Component c : mainPanel.getComponents()) {
					if(c instanceof BHChartPanel) {
						BHChartPanel cp = (BHChartPanel) c;
						charts.add(cp.getChart());
					}
				}
				((IPrint) pPlugs.values().toArray()[0]).printScenarioResults(scenario, result, charts);
			}
		});
		
		
		mainPanel = new StochasticPanel();
		this.add(mainPanel, BorderLayout.CENTER);
		JPanel buttons = new JPanel();
		
		buttons.add(exportButton, BorderLayout.WEST);
		buttons.add(printButton, BorderLayout.EAST);
		add(buttons, BorderLayout.NORTH);
	}
}
