package org.bh.plugin.stochasticResultAnalysis;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.bh.data.DTOScenario;
import org.bh.data.types.DistributionMap;
import org.bh.gui.swing.comp.BHButton;

@SuppressWarnings("serial")
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
//		exportButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//
//				BHDataExchangeDialog dialog = new BHDataExchangeDialog(null,
//						true);
//				dialog.setAction(IImportExport.EXP_SCENARIO_RES);
//				dialog.setModel(scenario);
//				dialog.setResults(result);
//				
//				dialog.setIconImages(Services.setIcon());
//								
//				List<JFreeChart> charts = new ArrayList<JFreeChart>();
//				for(Component c : mainPanel.getComponents()) {
//					if(c instanceof BHChartPanel) {
//						BHChartPanel cp = (BHChartPanel) c;
//						charts.add(cp.getChart());
//					}
//				}
//				dialog.setCharts(charts);
//				
//				dialog.setVisible(true);
//
//			}
//		});
		
		// printButton
		printButton = new BHButton(BHStochasticResultController.PanelKeys.PRINTSCENARIO);
//		printButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				Map<String, IPrint> pPlugs = Services.getPrintPlugins(IPrint.PRINT_SCENARIO_RES);
//				
//				List<JFreeChart> charts = new ArrayList<JFreeChart>();
//				for(Component c : mainPanel.getComponents()) {
//					if(c instanceof BHChartPanel) {
//						BHChartPanel cp = (BHChartPanel) c;
//						charts.add(cp.getChart());
//					}
//				}
//				((IPrint) pPlugs.values().toArray()[0]).printScenarioResults(scenario, result, charts);
//			}
//		});
		
		
		mainPanel = new StochasticPanel();
		this.add(mainPanel, BorderLayout.CENTER);
		
		double border = 10;
		double size2[][] = {
			{ border, TableLayoutConstants.PREFERRED, border, TableLayoutConstants.PREFERRED, border }, // Columns
			{ border, TableLayoutConstants.PREFERRED, border} };
		JPanel exportArea = new JPanel(new TableLayout(size2));
		exportArea.add(exportButton, "1,1" );
		
		exportArea.add(printButton, "3,1");
		exportArea.setMaximumSize(new Dimension(200, 40));
		
		
//		JPanel buttons = new JPanel();
//		
//		buttons.add(exportButton, BorderLayout.WEST);
//		buttons.add(printButton, BorderLayout.EAST);
		add(exportArea, BorderLayout.NORTH);
	}
}
