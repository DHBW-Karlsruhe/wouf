package org.bh.platform;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import org.bh.calculation.IShareholderValueCalculator;
import org.bh.controller.InputController;
import org.bh.data.DTOProject;
import org.bh.data.DTOScenario;
import org.bh.data.IDTO;
import org.bh.data.types.Calculable;
import org.bh.data.types.DistributionMap;
import org.bh.gui.View;
import org.bh.gui.swing.BHButton;

public class ProjectController extends InputController {

	public ProjectController(View view, IDTO<?> model) {
		super(view, model);

		((BHButton) view.getBHComponent(PlatformKey.CALCDASHBOARD))
				.addActionListener(new CalculationListener());
	}

	protected class CalculationListener implements ActionListener {
		@SuppressWarnings({ "unchecked", "cast" })
		@Override
		public void actionPerformed(ActionEvent ae) {

			DTOProject project = (DTOProject) getModel();
			// Map an DashboardController übergeben
			List<Calculable[]> results = new ArrayList<Calculable[]>(); 
			
			for (DTOScenario scenario : project.getChildren()) {
				// start calculation
				try {
					Map<String, Calculable[]> resultDCF = scenario
							.getDCFMethod().calculate(scenario);
					resultDCF.get(IShareholderValueCalculator.Result.SHAREHOLDER_VALUE);
					results.add(resultDCF.get(IShareholderValueCalculator.Result.SHAREHOLDER_VALUE));
					
				}
				catch(Exception e) {
					DistributionMap resultStochastic = (DistributionMap) scenario
							.getStochasticProcess().calculate();
					// TODO @Patrick Heinz ausimplementieren
//					resultStochastic.get(IShareholderValueCalculator.);
				}
			}

			//JPanel panel = new JPanel();
			//Services.setCharts(panel);
		}
	}
}
