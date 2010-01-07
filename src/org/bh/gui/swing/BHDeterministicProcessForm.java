package org.bh.gui.swing;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * This class contains the form for stochastic processes
 * 
 * @author Anton Kharitonov
 * @version 0.1, 05.01.2010
 * 
 */
public class BHDeterministicProcessForm extends JPanel {

	private BHDescriptionLabel linterval;
	private JCheckBox chbinterval;
	private JTable tperioddata;


	/**
	 * Constructor.
	 */
	public BHDeterministicProcessForm() {
		this.initialize();
	}

	/**
	 * Initialize method.
	 */
	private void initialize() {
		FormLayout layout;
		//TODO rowDef überarbeiten
		String rowDef = "4px,p,4px,50px";
		String colDef = "4px,right:pref,4px,max(80px;pref),4px:grow";

		layout = new FormLayout(colDef, rowDef);
		this.setLayout(layout);

		CellConstraints cons = new CellConstraints();

		this.add(this.getLinterval(), cons.xywh(2, 2, 1, 1));
		this.add(this.getChbinterval(), cons.xywh(4, 2, 1, 1));
		this.add(new JScrollPane(this.getTperioddata()), cons
				.xywh(1, 3, 5, 2));
	}

	// TODO add missing label keys and translations, change hard coded values to keys
	
	public BHDescriptionLabel getLinterval() {
		if (linterval == null) {
			linterval = new BHDescriptionLabel("Intervallrechnung");
		}
		return linterval;
	}

	public JCheckBox getChbinterval() {
		if (chbinterval == null) {
			chbinterval = new JCheckBox();
		}
		return chbinterval;
	}

	public JTable getTperioddata() {
		if (this.tperioddata == null) {

			String[] columnnames = { "Periode", "Fremdkapital", "FCF" };
			Integer[][] data = { { 2009, 0, 0 } };
			this.tperioddata = new JTable(data, columnnames);
			this.tperioddata.setGridColor(new Color(0, 0, 0));
			this.tperioddata.setPreferredScrollableViewportSize(new Dimension(
					30, 30));

		}
		return tperioddata;
	}
	
	
	
	
	
	/**
	 * Test main method.
	 */
	public static void main(String args[]) {

		JFrame test = new JFrame("Test for ViewStochasticData3");
		test.setContentPane(new BHDeterministicProcessForm());
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
