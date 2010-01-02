package org.bh.gui.swing;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.bh.gui.swing.BHLabel;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class BHProcessForm extends JPanel {

	private BHLabel ldcfchoise;
	private JComboBox cbdcfchoise;
	private BHLabel lprocess;
	private JComboBox cbprocess;
	private BHLabel ldirect;
	private JCheckBox chbdierect;
	private BHLabel linterval;
	private JCheckBox chbinterval;
	private BHLabel lperiodcount;
	private JSpinner spperiodcount;
	private BHLabel lintermstep;
	private JSpinner spintermstep;

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

		String rowDef = "4px,p,4px,p,14px,p,4px,p,4px";
		String colDef = "4px,right:pref,4px,max(80px;pref),24px:grow(0.2),pref,4px,pref,4px:grow";

		layout = new FormLayout(colDef, rowDef);
		this.setLayout(layout);

		CellConstraints cons = new CellConstraints();

		this.add(this.getlProcess(), cons.xywh(2, 2, 1, 1));
		this.add(this.getcbProcess(), cons.xywh(4, 2, 1, 1));
		this.add(this.getlDCFchoise(), cons.xywh(2, 4, 1, 1));
		this.add(this.getcbDCFchoise(), cons.xywh(4, 4, 1, 1));
		this.add(this.getLdirect(), cons.xywh(6, 2, 1, 1));
		this.add(this.getChbdierect(), cons.xywh(8, 2, 1, 1));
		this.add(this.getLinterval(), cons.xywh(6, 4, 1, 1));
		this.add(this.getChbinterval(), cons.xywh(8, 4, 1, 1));
		this.add(this.getLperiodcount(), cons.xywh(2, 6, 1, 1));
		this.add(this.getSpperiodcount(), cons.xywh(4, 6, 1, 1));
		this.add(this.getLintermstep(), cons.xywh(2, 8, 1, 1));
		this.add(this.getSpintermstep(), cons.xywh(4, 8, 1, 1));
	}

	public BHLabel getLdirect() {
		if (ldirect == null) {
			ldirect = new BHLabel("", "Direkteingabe");
		}
		return ldirect;
	}

	public JCheckBox getChbdierect() {
		if (chbdierect == null) {
			chbdierect = new JCheckBox();
		}
		return chbdierect;
	}

	public BHLabel getLinterval() {
		if (linterval == null) {
			linterval = new BHLabel("", "Intervallrechnung");
		}
		return linterval;
	}

	public JCheckBox getChbinterval() {
		if (chbinterval == null) {
			chbinterval = new JCheckBox();
		}
		return chbinterval;
	}

	public BHLabel getLperiodcount() {
		if (lperiodcount == null) {
			lperiodcount = new BHLabel("", "Anzahl Perioden");
		}
		return lperiodcount;
	}

	public JSpinner getSpperiodcount() {
		if (spperiodcount == null) {
			spperiodcount = new JSpinner(new SpinnerNumberModel());
		}
		return spperiodcount;
	}

	public BHLabel getLintermstep() {
		if (lintermstep == null) {
			lintermstep = new BHLabel("", "Anzahl Schritte pro Periode");
		}
		return lintermstep;
	}

	public JSpinner getSpintermstep() {
		if (spintermstep == null) {
			spintermstep = new JSpinner(new SpinnerNumberModel());
		}
		return spintermstep;
	}

	/**
	 * Getter method for component lDCFchoise.
	 * 
	 * @return the initialized component
	 */
	public BHLabel getlDCFchoise() {

		if (this.ldcfchoise == null) {
			this.ldcfchoise = new BHLabel("", "Discounted Cashflow Verfahren");
		}

		return this.ldcfchoise;
	}

	/**
	 * Getter method for component cbDCFchoise.
	 * 
	 * @return the initialized component
	 */
	public JComboBox getcbDCFchoise() {

		if (this.cbdcfchoise == null) {
			this.cbdcfchoise = new JComboBox();
		}

		return this.cbdcfchoise;
	}

	/**
	 * Getter method for component lstochProcess.
	 * 
	 * @return the initialized component
	 */
	public BHLabel getlProcess() {

		if (this.lprocess == null) {
			this.lprocess = new BHLabel("", "Berechnungsart");
		}

		return this.lprocess;
	}

	/**
	 * Getter method for component cbstochProcess.
	 * 
	 * @return the initialized component
	 */
	public JComboBox getcbProcess() {

		if (this.cbprocess == null) {
			this.cbprocess = new JComboBox();
			this.cbprocess.setName("cbstochProcess");
		}

		return this.cbprocess;
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
