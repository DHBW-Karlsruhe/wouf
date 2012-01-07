package org.bh.plugin.branchSpecificRepresentative.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.bh.data.DTOScenario;

/**
 * Listener for the deviation analysis
 *
 * <p>
 * This listener reacts to the deviation analysis popup button
 * and should show a screen with the relevant data for the deviation analysis
 *
 * @author Yannick Rödl
 * @version 0.1, 12.12.2011
 *
 */
public class DeviationAnalysisListener implements ActionListener {

	private DTOScenario scenario;
	
	public DeviationAnalysisListener(DTOScenario scenario){
		super();
		this.scenario = scenario;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		new BHDeviationAnalysisFrame(scenario);
	}

}
