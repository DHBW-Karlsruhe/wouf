package org.bh.gui.swing.forms;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import org.bh.gui.swing.forms.border.BHBorderFactory;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * This class contains the form for Perioddata
 * 
 * @author Anton Kharitonov
 * @author Patrick Heinz
 * @version 0.1, 13.01.2010
 * 
 */
@SuppressWarnings("serial")
public final class BHPeriodForm extends JPanel {

	public enum Key {
		/**
		 * 
		 */
		PERIOD_HEADDATA;

		@Override
		public String toString() {
			return getClass().getName() + "." + super.toString();
		}
	}

	private JPanel pperiod;
	private JPanel pvalues;

	ITranslator translator = Services.getTranslator();

	/**
	 * Constructor
	 */
	public BHPeriodForm() {
		this.initialize();
	}

	/**
	 * Initialize method.
	 */
	private void initialize() {

		String colDef = "4px,pref:grow,4px";
		String rowDef = "4px,p,14px,p,4px";

		FormLayout layout = new FormLayout(colDef, rowDef);
		this.setLayout(layout);

		CellConstraints cons = new CellConstraints();

		this.add(this.getPperiod(), cons.xywh(2, 2, 1, 1));
		this.add(this.getPvalues(), cons.xywh(2, 4, 1, 1));

	}

	private JPanel getPvalues() {
		if (pvalues == null) {
			pvalues = new JPanel();
		}
		return pvalues;
	}

	public void setPvalues(JPanel periodValues) {
		this.pvalues = periodValues;
		this.initialize();
	}

	public JPanel getProcessForm() {

		return pvalues;
	}

	public JPanel getPperiod() {
		if (pperiod == null) {
			pperiod = new BHPeriodHeadForm();
			pperiod.setBorder(BHBorderFactory.getInstacnce()
					.createTitledBorder(
							BHBorderFactory.getInstacnce().createEtchedBorder(
									EtchedBorder.LOWERED),
							BHPeriodForm.Key.PERIOD_HEADDATA));
		}
		return pperiod;
	}

}
