package org.bh.tests.apps;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.log4j.Logger;
import org.bh.calculation.IShareholderValueCalculator;
import org.bh.data.DTOPeriod;
import org.bh.data.DTOProject;
import org.bh.data.DTOScenario;
import org.bh.data.types.DoubleValue;
import org.bh.data.types.StringValue;
import org.bh.platform.DisplayablePluginWrapper;
import org.bh.platform.Services;

public class Main extends JFrame {
	private static final long serialVersionUID = 6644311268446279865L;
	private static final Logger log = Logger.getLogger(Main.class);
	private DTOScenario scenario;
	private JComboBox dcfCombo;
	private DTOProject project;

	public Main() {
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		setVisible(true);

		project = new DTOProject();
		StringValue projectName = new StringValue("Testprojekt 1");
		project.put(DTOProject.Key.NAME, projectName);

		scenario = new DTOScenario(true);
		scenario.put(DTOScenario.Key.REK, new DoubleValue(0.11));
		scenario.put(DTOScenario.Key.RFK, new DoubleValue(0.10));
		scenario.put(DTOScenario.Key.BTAX, new DoubleValue(0.1694));
		scenario.put(DTOScenario.Key.CTAX, new DoubleValue(0.26375));

		StringValue scenarioName = new StringValue("Testszenario 1");
		scenario.put(DTOScenario.Key.NAME, scenarioName);

		project.addChild(scenario, false);

		final PeriodPanel[] periodPanels = new PeriodPanel[3];
		for (int i = 0; i < periodPanels.length; i++) {
			DTOPeriod periodDto = new DTOPeriod();
			scenario.addChild(periodDto);

			periodPanels[i] = new PeriodPanel("" + (2009 + i), periodDto);
			add(periodPanels[i]);
		}

		dcfCombo = new JComboBox(Services.getDCFMethods().toArray());
		this.add(dcfCombo);

		final JLabel resultLabel = new JLabel();
		JButton button = new JButton("Calculate");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for (PeriodPanel period : periodPanels) {
					period.stopEditing();
				}
				try {
					@SuppressWarnings("unchecked")
					IShareholderValueCalculator calculator = ((DisplayablePluginWrapper<IShareholderValueCalculator>) dcfCombo
							.getSelectedItem()).getPlugin();

					resultLabel.setText(calculator.calculate(scenario).get(
							IShareholderValueCalculator.SHAREHOLDER_VALUE)[0]
							.toString());

					Main.this.validate();
				} catch (Exception e1) {
					log.error("", e1);
				}
			}
		});

		this.add(button);
		this.add(resultLabel);

		this.validate();
	}
}
