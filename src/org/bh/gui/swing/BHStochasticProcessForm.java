package org.bh.gui.swing;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;

import org.bh.gui.swing.BHLabel;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * This class contains the form for stochastic processes
 * 
 * @author Anton Kharitonov
 * @version 0.1, 05.01.2010
 * 
 */
public class BHStochasticProcessForm extends JPanel {

	private BHStochasticInputForm stochasticInputForm;


	/**
	 * Constructor.
	 */
	public BHStochasticProcessForm() {
		this.initialize();
	}

	/**
	 * Initialize method.
	 */
	private void initialize() {
		FormLayout layout;
		//TODO rowDef überarbeiten
		String rowDef = "50px";
		String colDef = "4px,right:pref,4px,max(80px;pref),4px:grow";

		layout = new FormLayout(colDef, rowDef);
		this.setLayout(layout);

		CellConstraints cons = new CellConstraints();
		
		this.add(this.getStochasticInputForm(), cons.xywh(1, 1, 1, 4));
	}

	// TODO add missing label keys and translations, change hard coded values to keys
	public BHStochasticInputForm getStochasticInputForm() {
		if (stochasticInputForm == null) {
			stochasticInputForm = new BHStochasticInputForm();
		}
		return stochasticInputForm;
	}

}
