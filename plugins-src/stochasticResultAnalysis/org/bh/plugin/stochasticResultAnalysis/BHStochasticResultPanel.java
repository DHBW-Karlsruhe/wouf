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
		printButton = new BHButton(BHStochasticResultController.PanelKeys.PRINTSCENARIO);
		
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
		
		add(exportArea, BorderLayout.NORTH);
	}
}
