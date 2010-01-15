package org.bh.platform;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.bh.calculation.IShareholderValueCalculator;
import org.bh.controller.InputController;
import org.bh.data.DTOProject;
import org.bh.data.DTOScenario;
import org.bh.data.IDTO;
import org.bh.data.types.Calculable;
import org.bh.data.types.DistributionMap;
import org.bh.gui.IDeterministicResultAnalyser;
import org.bh.gui.View;
import org.bh.gui.ViewException;
import org.bh.gui.swing.BHButton;
import org.bh.gui.swing.BHDashBoardPanelView;
import org.bh.gui.swing.BHTree;
import org.bh.gui.swing.BHTreeNode;
import org.bh.platform.ScenarioController.CalculationListener;

public class ProjectController extends InputController {

	public ProjectController(View view, IDTO<?> model) {
		super(view, model);

		((BHButton) view.getBHComponent(PlatformKey.CALCDASHBOARD))
				.addActionListener(new CalculationListener(PlatformController
						.getInstance().getMainFrame().getBHTree()));
	}

	protected class CalculationListener implements ActionListener {
		private final ImageIcon LOADING = Services.createImageIcon(
				"/org/bh/images/loading.gif", null);

		private BHTree bhTree;

		public CalculationListener(BHTree bhTree) {
			this.bhTree = bhTree;
		}

		public void actionPerformed(ActionEvent ae) {

			DTOProject project = (DTOProject) getModel();
			// Map an DashboardController übergeben
			Map<DTOScenario, Map<?, ?>> results = new HashMap<DTOScenario, Map<?, ?>>();

			for (DTOScenario scenario : project.getChildren()) {
				// start calculation

				if (scenario.isDeterministic()) {
					Map<String, Calculable[]> resultsDCF = scenario
							.getDCFMethod().calculate(scenario);

					results.put(scenario, resultsDCF);
				} else {
					DistributionMap resultStochastic = (DistributionMap) scenario
							.getStochasticProcess().calculate();

					results.put(scenario, resultStochastic);
				}
			}
			try {
				View v = new BHDashBoardPanelView(results);
				JSplitPane crForm = Services.createContentResultForm(v
						.getViewPanel());
				BHTreeNode tn = (BHTreeNode) bhTree.getSelectionPath()
						.getPathComponent(2);

				tn.setBackgroundPane(crForm);
			} catch (ViewException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}
