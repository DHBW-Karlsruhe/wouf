package org.bh.gui.swing.forms;

import javax.swing.JPanel;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * This class contains the form for stochastic processes.
 * 
 * @author Anton Kharitonov
 * @version 1.0, 22.01.2010
 * 
 */
@SuppressWarnings("serial")
public final class BHStochasticProcessForm extends JPanel {

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
		
		this.add(this.getStochasticInputForm(), cons.xywh(1, 2, 1, 1));
	}

	public BHStochasticInputForm getStochasticInputForm() {
		if (stochasticInputForm == null) {
			stochasticInputForm = new BHStochasticInputForm();
		}
		return stochasticInputForm;
	}

}