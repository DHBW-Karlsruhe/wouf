package org.bh.gui.swing.forms;

import javax.swing.JPanel;

import org.bh.data.DTOProject;
import org.bh.gui.swing.comp.BHDescriptionLabel;
import org.bh.gui.swing.comp.BHTextField;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;
import org.bh.validation.VRMandatory;
import org.bh.validation.ValidationRule;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * This class contains the form for stochastic processes
 * 
 * @author Anton Kharitonov
 * @version 0.2, 03.01.2010
 * 
 */

public final class BHProjectInputForm extends JPanel {

	private BHDescriptionLabel lproject;
	private BHDescriptionLabel lprojectname;
	private BHDescriptionLabel lcomment;
	private BHTextField tfprojectname;
	private BHTextField tfcomment;

	ITranslator translator = Services.getTranslator();

	public BHProjectInputForm() {

		String rowDef = "4px:grow,p,4px,p,4px,p,4px:grow";
		String colDef = "4px:grow,right:pref,4px,150px:grow,4px:grow";

		FormLayout layout = new FormLayout(colDef, rowDef);
		this.setLayout(layout);

		CellConstraints cons = new CellConstraints();

//		this.add(this.getLproject(), cons.xywh(4, 2, 1, 1, "center, center"));
		this.add(this.getLprojectname(), cons.xywh(2, 4, 1, 1));
		this.add(this.getTfprojectname(), cons.xywh(4, 4, 1, 1));
		this.add(this.getLcomment(), cons.xywh(2, 6, 1, 1));
		this.add(this.getTfcomment(), cons.xywh(4, 6, 1, 1));
	}
	
	public BHDescriptionLabel getLproject() {
		if (lproject == null) {
			lproject = new BHDescriptionLabel("project");
		}
		return lproject;
	}

	public BHDescriptionLabel getLcomment() {
		if (lcomment == null) {
			lcomment = new BHDescriptionLabel(DTOProject.Key.COMMENT);
		}
		return lcomment;
	}
	
	public BHDescriptionLabel getLprojectname() {
		if (lprojectname == null) {
			lprojectname = new BHDescriptionLabel(DTOProject.Key.NAME);
			lprojectname.setVisible(true);
		}
		return lprojectname;
	}

	/**
	 * Getter method for component tfprojectname.
	 * 
	 * @return BHTextField
	 */
	public BHTextField getTfprojectname() {
		if (tfprojectname == null) {
			tfprojectname = new BHTextField(DTOProject.Key.NAME, false);
			ValidationRule[] rules = { VRMandatory.INSTANCE };
			tfprojectname.setValidationRules(rules);
		}
		return tfprojectname;
	}

	/**
	 * Getter method for component tfcomment.
	 * 
	 * @return BHTextField
	 */
	public BHTextField getTfcomment() {
		if (tfcomment == null) {
			tfcomment = new BHTextField(DTOProject.Key.COMMENT, false);
		}
		return tfcomment;
	}
	
	// TODO remove main later
//	/**
//	 * Test main method.
//	 */
//	public static void main(String args[]) {
//
//		JFrame test = new JFrame("Test for ViewPeriodData1");
//		test.setContentPane(new BHProjectInputForm());
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
