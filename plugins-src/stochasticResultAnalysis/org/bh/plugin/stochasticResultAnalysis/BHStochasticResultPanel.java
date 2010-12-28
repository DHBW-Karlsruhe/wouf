package org.bh.plugin.stochasticResultAnalysis;




import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
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
	GridBagConstraints d;
	JLabel space;
	
	public BHStochasticResultPanel(DTOScenario scenario, DistributionMap result) {
		this.scenario = scenario;
		this.result = result;
		initialize();
	}
	private void initialize(){
		this.setLayout(new BorderLayout());
		exportButton = new BHButton(BHStochasticResultController.PanelKeys.EXPORTSCENARIO);
		printButton = new BHButton(BHStochasticResultController.PanelKeys.PRINTSCENARIO);
		
		StochasticPanel mainStochastic = new StochasticPanel();
		if(result.isTimeSeries()){
			mainStochastic.addTimeSeries();
		}
		mainPanel = mainStochastic;
		this.add(mainPanel, BorderLayout.CENTER);
		
		JPanel exportArea = new JPanel(new GridBagLayout());
		d = new GridBagConstraints();

		
		d.fill = GridBagConstraints.HORIZONTAL;
		d.gridx = 0;
		d.gridy = 0;
		d.insets = new Insets(10,10,10,0);
		exportArea.add(exportButton, d );
		
		d.fill = GridBagConstraints.HORIZONTAL;
		d.gridx = 1;
		d.gridy = 0;
		d.insets = new Insets(10,10,10,10);
		exportArea.add(printButton, d);
		
		space = new JLabel();
		d.gridx = 2;
		d.gridy = 0;
		d.weightx = 1.0;
		exportArea.add(space, d);
		exportArea.setMaximumSize(new Dimension(200, 40));
		
		add(exportArea, BorderLayout.NORTH);
	}
}
