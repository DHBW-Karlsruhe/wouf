package org.bh.gui.swing;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.bh.data.DTOScenario;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * This class contains the form for stochastic processes
 * 
 * @author Anton Kharitonov
 * @author Patrick Heinz
 * @version 0.2, 03.01.2010
 * 
 */
public class BHProcessForm extends JPanel {
	private BHLabel lProcess;
	private JComboBox cbProcess;
	private BHLabel lInputType;
	private JComboBox cbInputType;

	/**
	 * Constructor.
	 */
	public BHProcessForm() {
		this.initialize();
	}

	/**
	 * Initialize method.
	 */
	private void initialize() {
		FormLayout layout;

		String rowDef = "p,4px,p";
		String colDef = "4px,p,4px,max(150px;p),4px";

		layout = new FormLayout(colDef, rowDef);
		this.setLayout(layout);

		CellConstraints cons = new CellConstraints();

		this.add(this.getlStochasticProcess(), cons.xy(2, 1));
		this.add(this.getcbStochasticProcess(), cons.xy(4, 1));
		this.add(this.getlInputType(), cons.xy(2, 3));
		this.add(this.getcbInputType(), cons.xy(4, 3));
	}

	// TODO add missing label keys and translations, change hard coded values to
	// keys
	/*
	 * public JSpinner getSpperiodcount() { if (spperiodcount == null) { // new
	 * SpinnerNumberModel(value, minimum, maximum, stepSize) spperiodcount = new
	 * JSpinner(new SpinnerNumberModel(1, 1, 100, 1)); } return spperiodcount; }
	 * 
	 * public JSpinner getSpintermstep() { if (spintermstep == null) { // new
	 * SpinnerNumberModel(value, minimum, maximum, stepSize) spintermstep = new
	 * JSpinner(new SpinnerNumberModel(10, 1, 10000, 10)); } return
	 * spintermstep; }
	 */

	public BHLabel getlStochasticProcess() {
		if (this.lProcess == null)
			this.lProcess = new BHLabel(DTOScenario.Key.STOCHASTIC_PROCESS,
					"Stochastischer Prozess");
		return this.lProcess;
	}

	public JComboBox getcbStochasticProcess() {
		if (this.cbProcess == null) {
			this.cbProcess = new JComboBox();
		}
		return this.cbProcess;
	}
	
	public BHLabel getlInputType() {
		if (this.lInputType == null)
			this.lInputType = new BHLabel(DTOScenario.Key.STOCHASTIC_PROCESS,
					"Eingabeart");
		return this.lInputType;
	}

	public JComboBox getcbInputType() {
		if (this.cbInputType == null) {
			this.cbInputType = new JComboBox();
		}
		return this.cbInputType;
	}

	/**
	 * Test main method.
	 */
	public static void main(String args[]) {

		JFrame test = new JFrame("Test for ViewStochasticData3");
		test.setContentPane(new BHProcessForm());
		test.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		test.pack();
		test.show();
	}

}
