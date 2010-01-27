package org.bh.gui.swing.forms;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import org.bh.data.DTOPeriod;
import org.bh.gui.IBHComponent;
import org.bh.gui.swing.comp.BHDescriptionLabel;
import org.bh.gui.swing.comp.BHTextField;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;
import org.bh.validation.VRIsGreaterThan;
import org.bh.validation.VRMandatory;
import org.bh.validation.ValidationRule;

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
public final class BHPeriodInputForm extends JPanel {

	private BHDescriptionLabel lfcf;
	private BHTextField tffcf;
	private BHDescriptionLabel lliabilities;
	private BHTextField tfliabilities;
	private JLabel lcurrency1;
	private JLabel lcurrency2;

	private JLabel lmax;
	private JLabel lmin;
	private JLabel lcurrency3;
	private JLabel lcurrency4;
	private JLabel lcurrency5;
	private JLabel lcurrency6;
	private BHTextField tfmaxliabilities;
	private BHTextField tfminliabilities;
	private BHTextField tfmaxfcf;
	private BHTextField tfminfcf;

	private String year;

	ITranslator translator = Services.getTranslator();

	/**
	 * Constructor.
	 */
	public BHPeriodInputForm(String year) {

		this.year = year;

		String rowDef = "4px,p,4px,p,4px,p,4px";
		String colDef = "4px,right:pref,4px,60px:grow,4px,pref,24px:grow,pref,4px,max(35px;pref):grow,4px,pref,4px:grow,pref,4px,max(35px;pref):grow,4px,pref,4px:grow";

		FormLayout layout = new FormLayout(colDef, rowDef);
		this.setLayout(layout);

		CellConstraints cons = new CellConstraints();
		layout.setColumnGroups(new int[][] { { 4, 10, 16 } });

		this.add(this.getLfcf(), cons.xywh(2, 4, 1, 1));
		this.add(this.getTffcf(), cons.xywh(4, 4, 1, 1));
		this.add(this.getLcurrency1(), cons.xywh(6, 4, 1, 1));
		this.add(this.getLliabilities(), cons.xywh(2, 6, 1, 1));
		this.add(this.getTfliabilities(), cons.xywh(4, 6, 1, 1));
		this.add(this.getLcurrency2(), cons.xywh(6, 6, 1, 1));

		this
				.add(new JSeparator(SwingConstants.VERTICAL), cons.xywh(8, 2,
						1, 6));
		this.add(this.getLmin(), cons.xywh(10, 2, 1, 1));
		this.add(new JSeparator(SwingConstants.VERTICAL), cons
				.xywh(14, 2, 1, 6));
		this.add(this.getLmax(), cons.xywh(16, 2, 1, 1));

		this.add(this.getTfminfcf(), cons.xywh(10, 4, 1, 1));
		this.add(this.getLcurrency3(), cons.xywh(12, 4, 1, 1));
		this.add(this.getTfmaxfcf(), cons.xywh(16, 4, 1, 1));
		this.add(this.getLcurrency4(), cons.xywh(18, 4, 1, 1));

		this.add(this.getTfminliabilities(), cons.xywh(10, 6, 1, 1));
		this.add(this.getLcurrency5(), cons.xywh(12, 6, 1, 1));
		this.add(this.getTfmaxliabilities(), cons.xywh(16, 6, 1, 1));
		this.add(this.getLcurrency6(), cons.xywh(18, 6, 1, 1));

		this.setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED), this.year));
	}

	public JLabel getLmax() {
		if (lmax == null) {
			lmax = new JLabel(translator.translate("max"));
		}
		return lmax;
	}

	public JLabel getLmin() {
		if (lmin == null) {
			lmin = new JLabel(translator.translate("min"));
		}
		return lmin;
	}

	public JLabel getLcurrency1() {
		if (lcurrency1 == null) {
			lcurrency1 = new JLabel(translator.translate("currency"));
		}
		return lcurrency1;
	}

	public JLabel getLcurrency2() {
		if (lcurrency2 == null) {
			lcurrency2 = new JLabel(translator.translate("currency"));
		}
		return lcurrency2;
	}
	
	public JLabel getLcurrency3() {
		if (lcurrency3 == null) {
			lcurrency3 = new JLabel(translator.translate("currency"));
		}
		return lcurrency3;
	}

	public JLabel getLcurrency4() {
		if (lcurrency4 == null) {
			lcurrency4 = new JLabel(translator.translate("currency"));
		}
		return lcurrency4;
	}

	public JLabel getLcurrency5() {
		if (lcurrency5 == null) {
			lcurrency5 = new JLabel(translator.translate("currency"));
		}
		return lcurrency5;
	}

	public JLabel getLcurrency6() {
		if (lcurrency6 == null) {
			lcurrency6 = new JLabel(translator.translate("currency"));
		}
		return lcurrency6;
	}

	// Here do the getters for the textfields begin

	/**
	 * Getter method for component tfmaxliabilities.
	 * 
	 * @return the initialized component
	 */
	public BHTextField getTfmaxliabilities() {
		if (tfmaxliabilities == null) {
			tfmaxliabilities = new BHTextField(IBHComponent.MAXVALUE
					+ DTOPeriod.Key.LIABILITIES);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					new VRIsGreaterThan(getTfminliabilities(), true)};
			tfmaxliabilities.setValidationRules(rules);
		}
		return tfmaxliabilities;
	}

	/**
	 * Getter method for component tfminliabilities.
	 * 
	 * @return the initialized component
	 */
	public BHTextField getTfminliabilities() {
		if (tfminliabilities == null) {
			tfminliabilities = new BHTextField(IBHComponent.MINVALUE
					+ DTOPeriod.Key.LIABILITIES);
			ValidationRule[] rules = { VRMandatory.INSTANCE };
			tfminliabilities.setValidationRules(rules);
		}
		return tfminliabilities;
	}

	/**
	 * Getter method for component tfmaxfcf.
	 * 
	 * @return the initialized component
	 */
	public BHTextField getTfmaxfcf() {
		if (tfmaxfcf == null) {
			tfmaxfcf = new BHTextField(IBHComponent.MAXVALUE
					+ DTOPeriod.Key.FCF);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					new VRIsGreaterThan(getTfminfcf(), true)};
			tfmaxfcf.setValidationRules(rules);
		}
		return tfmaxfcf;
	}

	/**
	 * Getter method for component tfminfcf.
	 * 
	 * @return the initialized component
	 */
	public BHTextField getTfminfcf() {
		if (tfminfcf == null) {
			tfminfcf = new BHTextField(IBHComponent.MINVALUE
					+ DTOPeriod.Key.FCF);
			ValidationRule[] rules = { VRMandatory.INSTANCE };
			tfminfcf.setValidationRules(rules);
		}
		return tfminfcf;
	}

	public BHDescriptionLabel getLfcf() {
		if (lfcf == null) {
			lfcf = new BHDescriptionLabel(DTOPeriod.Key.FCF);
		}
		return lfcf;
	}

	/**
	 * Getter method for component tffcf.
	 * 
	 * @return the initialized component
	 */
	public BHTextField getTffcf() {
		if (tffcf == null) {
			tffcf = new BHTextField(DTOPeriod.Key.FCF);
			ValidationRule[] rules = { VRMandatory.INSTANCE };
			tffcf.setValidationRules(rules);
		}
		return tffcf;
	}

	public BHDescriptionLabel getLliabilities() {
		if (lliabilities == null) {
			lliabilities = new BHDescriptionLabel(DTOPeriod.Key.LIABILITIES);
		}
		return lliabilities;
	}

	/**
	 * Getter method for component tfliabilities.
	 * 
	 * @return the initialized component
	 */
	public BHTextField getTfliabilities() {
		if (tfliabilities == null) {
			tfliabilities = new BHTextField(DTOPeriod.Key.LIABILITIES);
			ValidationRule[] rules = { VRMandatory.INSTANCE };
			tfliabilities.setValidationRules(rules);
		}
		return tfliabilities;
	}

	
	// TODO remove main later
//	/**
//	 * Test main method.
//	 */
//	public static void main(String args[]) {
//
//		JFrame test = new JFrame("Test for ViewPeriodData1");
//		test.setContentPane(new BHPeriodInputForm("2009"));
//		test.addWindowListener(new WindowAdapter() {
//			@Override
//			public void windowClosing(WindowEvent e) {
//				System.exit(0);
//			}
//		});
//		test.pack();
//		test.show();
//	}
}
