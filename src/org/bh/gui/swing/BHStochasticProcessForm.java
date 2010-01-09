package org.bh.gui.swing;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
		String rowDef = "4px,p,4px";
		String colDef = "pref:grow";

		layout = new FormLayout(colDef, rowDef);
		this.setLayout(layout);

		CellConstraints cons = new CellConstraints();
		
		this.add(new JScrollPane(this.getStochasticInputForm()), cons.xywh(1, 2, 1, 1));
	}

	// TODO add missing label keys and translations, change hard coded values to keys
	public BHStochasticInputForm getStochasticInputForm() {
		if (stochasticInputForm == null) {
			stochasticInputForm = new BHStochasticInputForm();
		}
		return stochasticInputForm;
	}

}
