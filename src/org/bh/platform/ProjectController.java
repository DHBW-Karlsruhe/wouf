/*******************************************************************************
 * Copyright 2010: Anna Aichinger, Damian Berle, Patrick Dahl, Lisa Engelmann, Patrick Groß, Irene Ihl, Timo Klein, Alena Lang, Miriam Leuthold, Lukas Maciolek, Patrick Maisel, Vito Masiello, Moritz Olf, Ruben Reichle, Alexander Rupp, Daniel Schäfer, Simon Waldraff, Matthias Wurdig, Andreas Wußler
 *
 * Copyright 2009: Manuel Bross, Simon Drees, Marco Hammel, Patrick Heinz, Marcel Hockenberger, Marcus Katzor, Edgar Kauz, Anton Kharitonov, Sarah Kuhn, Michael Löckelt, Heiko Metzger, Jacqueline Missikewitz, Marcel Mrose, Steffen Nees, Alexander Roth, Sebastian Scharfenberger, Carsten Scheunemann, Dave Schikora, Alexander Schmalzhaf, Florian Schultze, Klaus Thiele, Patrick Tietze, Robert Vollmer, Norman Weisenburger, Lars Zuckschwerdt
 *
 * Copyright 2008: Camil Bartetzko, Tobias Bierer, Lukas Bretschneider, Johannes Gilbert, Daniel Huser, Christopher Kurschat, Dominik Pfauntsch, Sandra Rath, Daniel Weber
 *
 * This program is free software: you can redistribute it and/or modify it un-der the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT-NESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package org.bh.platform;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;

import org.apache.log4j.Logger;
import org.bh.controller.InputController;
import org.bh.data.DTOProject;
import org.bh.data.DTOScenario;
import org.bh.data.IDTO;
import org.bh.data.types.Calculable;
import org.bh.gui.swing.comp.BHButton;
import org.bh.gui.swing.comp.BHDescriptionLabel;
import org.bh.gui.swing.forms.BHProjectForm;
import org.bh.gui.swing.forms.BHScenarioForm;
import org.bh.gui.swing.tree.BHTree;
import org.bh.gui.swing.tree.BHTreeNode;
import org.bh.gui.view.BHDashBoardPanelView;
import org.bh.gui.view.View;
import org.bh.gui.view.ViewException;
import org.bh.platform.PlatformEvent.Type;

/**
 * This class helps to create the dashboard on projectlevel. There is the
 * CALCDASHBOARD Button, which starts the calculation of scenario's results.
 * 
 * @author Norman Weisenburger, Patrick Heinz
 * @version 1.0, 22.01.2010
 * 
 */
public class ProjectController extends InputController implements
		IPlatformListener {
	/**
	 * Logger for this class
	 */
	static final Logger log = Logger.getLogger(ProjectController.class);
	
	/**
	 * The constructor for the distribution map.
	 * 
	 * @param view, IDTO<?> model
	 */
	public ProjectController(View view, IDTO<?> model) {
		super(view, model);
		Services.addPlatformListener(this);

		((BHButton) view.getBHComponent(BHScenarioForm.Key.CALCDASHBOARD))
				.addActionListener(new CalculationListener(PlatformController
						.getInstance().getMainFrame().getBHTree()));
		
		setCalcEnabled();
	}

	/**
	 * Inner class calculating all shareholder values of a project's scenarios.
	 */
	protected class CalculationListener implements ActionListener {
		private final ImageIcon prCalcLoading = Services.createImageIcon("/org/bh/images/loading.gif");

		private BHTree bhTree;

		public CalculationListener(BHTree bhTree) {
			this.bhTree = bhTree;
		}

		public void actionPerformed(final ActionEvent ae) {
			new Thread(new Runnable() {
				public void run() {
					if (log.isDebugEnabled()) {
						log.debug("actionPerformed(ActionEvent)-start-");
					}

					try {
						JButton b = (JButton) ae.getSource();
						BHDescriptionLabel calcImage = (BHDescriptionLabel) getView().getBHComponent(
								BHProjectForm.Key.CALCULATING_IMAGE);
						
						b.setEnabled(false);
						calcImage.setIcon(prCalcLoading);
						
						DTOProject project = (DTOProject) getModel();
						// Give Map to DashboardController
						Map<DTOScenario, Map<?, ?>> results = new HashMap<DTOScenario, Map<?, ?>>();

						for (DTOScenario scenario : project.getChildren()) {
							// start calculation
							if (scenario.isDeterministic()) {
								Map<String, Calculable[]> resultsDCF = scenario
										.getDCFMethod().calculate(scenario, true);

								results.put(scenario, resultsDCF);

//							Decommented, because dashboard does not support stochastic calculation so far.
//							Use the code below, if you want to implement stochastic calculation for dashboard.

//							} else {
//								DistributionMap resultStochastic = scenario
//										.getStochasticProcess().calculate();
//
//								results.put(scenario, resultStochastic);																						
							}
						}

						View v = new BHDashBoardPanelView();
						DashBoardController d = new DashBoardController(v);
						d.setResult(results);

						BHTreeNode tn = (BHTreeNode) bhTree.getSelectionPath()
						.getPathComponent(1);

						tn.setResultPane(new JScrollPane(v.getViewPanel()));
						PlatformController.getInstance().getMainFrame().moveInResultForm(tn.getResultPane());
						
						calcImage.setIcon(null);
						b.setEnabled(true);
					} catch (ViewException e) {
						log.error(e);
						((JButton) ae.getSource()).setIcon(null);
					}

					if (log.isDebugEnabled()) {
						log.debug("actionPerformed(ActionEvent)-end-");
					}
				}
			}).start();
		}
	}

	@Override
	public void platformEvent(PlatformEvent e) {
		super.platformEvent(e);
		if (e.getEventType() == Type.DATA_CHANGED
				&& getModel().isMeOrChild(e.getSource())) {
			setCalcEnabled();
		}
	}

	/**
	 * This method enables the calculation button if there are more than
	 * 2 valid scenarios to compare. Otherwise there will be a hint that
	 * the calculation is not possible.
	 */
	protected void setCalcEnabled() {
		DTOProject project = (DTOProject) getModel();
		int counter = 0;
		for (DTOScenario scenario : project.getChildren()) {
			if (scenario.isValid(true)) {
				counter++;
			}
		}
		boolean calculationEnabled = (counter >= 2);

		((Component) view.getBHComponent(BHScenarioForm.Key.CALCDASHBOARD))
				.setEnabled(calculationEnabled);
		((Component) view.getBHComponent(BHProjectForm.Key.CANNOT_CALCULATE_HINT))
				.setVisible(!calculationEnabled);
	}
}
