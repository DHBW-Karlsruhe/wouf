package org.bh.plugin.gcc.swing;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import org.bh.gui.swing.BHDescriptionLabel;
import org.bh.gui.swing.BHTextField;
import org.bh.gui.swing.IBHComponent;
import org.bh.platform.i18n.BHTranslator;
import org.bh.platform.i18n.ITranslator;
import org.bh.plugin.gcc.data.DTOGCCProfitLossStatementTotalCost;
import org.bh.validation.VRIsDouble;
import org.bh.validation.VRMandatory;
import org.bh.validation.ValidationRule;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * This class contains the total cost form for the plugin
 * 
 * @author Anton Kharitonov
 * @author Patrick Heinz
 * @version 0.1, 08.01.2010
 * 
 */
@SuppressWarnings("serial")
public class BHPLSTotalCostForm extends JPanel {

	private BHTextField tfUE;
	private BHTextField tfABSCH;
	private BHTextField tfSBA;
	private BHTextField tfAUERG;
	private BHTextField tfUEmax;
	private BHTextField tfABSCHmax;
	private BHTextField tfSBAmax;
	private BHTextField tfAUERGmax;
	private BHTextField tfUEmin;
	private BHTextField tfABSCHmin;
	private BHTextField tfSBAmin;
	private BHTextField tfAUERGmin;

	private BHDescriptionLabel lUE;
	private BHDescriptionLabel lABSCH;
	private BHDescriptionLabel lSBA;
	private BHDescriptionLabel lAUERG;

	private JLabel lmin;
	private JLabel lmax;

	final ITranslator translator = BHTranslator.getInstance();

	public enum Key {
		/**
	 * 
	 */
		PLS_TotalCost;

		public String toString() {
			return getClass().getName() + "." + super.toString();
		}

	}

	public BHPLSTotalCostForm(boolean intervalArithmetic) {
		this.initialize(intervalArithmetic);
	}

	public void initialize(boolean intervalArithmetic) {
		String rowDef = "4px,p,4px,p,4px,p,4px,p,4px";
		String colDef = "4px,right:pref,4px,max(100px;pref):grow,4px,pref,4px";
		if (intervalArithmetic) {
			rowDef = "4px,p," + rowDef;
			colDef += ",max(100px;pref):grow,4px,pref,4px";

			FormLayout layout = new FormLayout(colDef, rowDef);
			this.setLayout(layout);

			CellConstraints cons = new CellConstraints();

			if (!intervalArithmetic) {
				this.add(this.getLUE(), cons.xywh(2, 2, 1, 1));
				this.add(this.getLABSCH(), cons.xywh(2, 4, 1, 1));
				this.add(this.getLSBA(), cons.xywh(2, 6, 1, 1));
				this.add(this.getLAUERG(), cons.xywh(2, 8, 1, 1));

				this.add(this.getTfUE(), cons.xywh(4, 2, 1, 1));
				this.add(new JLabel(translator.translate("currency")), cons
						.xywh(6, 2, 1, 1));
				this.add(this.getTfABSCH(), cons.xywh(4, 4, 1, 1));
				this.add(new JLabel(translator.translate("currency")), cons
						.xywh(6, 4, 1, 1));
				this.add(this.getTfSBA(), cons.xywh(4, 6, 1, 1));
				this.add(new JLabel(translator.translate("currency")), cons
						.xywh(6, 6, 1, 1));
				this.add(this.getTfAUERG(), cons.xywh(4, 8, 1, 1));
				this.add(new JLabel(translator.translate("currency")), cons
						.xywh(6, 8, 1, 1));
			} else {
				layout.setColumnGroups(new int[][] { { 4, 8 } });

				this.add(this.getLUE(), cons.xywh(2, 4, 1, 1));
				this.add(this.getLABSCH(), cons.xywh(2, 6, 1, 1));
				this.add(this.getLSBA(), cons.xywh(2, 8, 1, 1));
				this.add(this.getLAUERG(), cons.xywh(2, 10, 1, 1));

				// this.add(new JSeparator(SwingConstants.VERTICAL),
				// cons.xywh(6, 2,
				// 1, 9));
				this.add(this.getLmin(), cons
						.xywh(4, 2, 1, 1, "center,default"));
				this.add(this.getLmax(), cons
						.xywh(8, 2, 1, 1, "center,default"));

				this.add(this.getTfUEmin(), cons.xywh(4, 4, 1, 1));
				this.add(new JLabel(translator.translate("currency")), cons
						.xywh(6, 4, 1, 1));
				this.add(this.getTfABSCHmin(), cons.xywh(4, 6, 1, 1));
				this.add(new JLabel(translator.translate("currency")), cons
						.xywh(6, 6, 1, 1));
				this.add(this.getTfSBAmin(), cons.xywh(4, 8, 1, 1));
				this.add(new JLabel(translator.translate("currency")), cons
						.xywh(6, 8, 1, 1));
				this.add(this.getTfAUERGmin(), cons.xywh(4, 10, 1, 1));
				this.add(new JLabel(translator.translate("currency")), cons
						.xywh(6, 10, 1, 1));

				this.add(this.getTfUEmax(), cons.xywh(8, 4, 1, 1));
				this.add(new JLabel(translator.translate("currency")), cons
						.xywh(10, 4, 1, 1));
				this.add(this.getTfABSCHmax(), cons.xywh(8, 6, 1, 1));
				this.add(new JLabel(translator.translate("currency")), cons
						.xywh(10, 6, 1, 1));
				this.add(this.getTfSBAmax(), cons.xywh(8, 8, 1, 1));
				this.add(new JLabel(translator.translate("currency")), cons
						.xywh(10, 8, 1, 1));
				this.add(this.getTfAUERGmax(), cons.xywh(8, 10, 1, 1));
				this.add(new JLabel(translator.translate("currency")), cons
						.xywh(10, 10, 1, 1));
			}
		}

		// TODO add handler for locale change
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED), translator
				.translate(BHPLSTotalCostForm.Key.PLS_TotalCost)));
	}

	// Here do the getters for the textfields begin

	public BHTextField getTfUE() {
		if (tfUE == null) {
			tfUE = new BHTextField(DTOGCCProfitLossStatementTotalCost.Key.UE);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfUE.setValidationRules(rules);
		}
		return tfUE;
	}

	public BHTextField getTfABSCH() {
		if (tfABSCH == null) {
			tfABSCH = new BHTextField(
					DTOGCCProfitLossStatementTotalCost.Key.ABSCH);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfABSCH.setValidationRules(rules);
		}
		return tfABSCH;
	}

	public BHTextField getTfSBA() {
		if (tfSBA == null) {
			tfSBA = new BHTextField(DTOGCCProfitLossStatementTotalCost.Key.SBA);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfSBA.setValidationRules(rules);
		}
		return tfSBA;
	}

	public BHTextField getTfAUERG() {
		if (tfAUERG == null) {
			tfAUERG = new BHTextField(
					DTOGCCProfitLossStatementTotalCost.Key.AUERG);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfAUERG.setValidationRules(rules);
		}
		return tfAUERG;
	}

	public BHTextField getTfUEmax() {
		if (tfUEmax == null) {
			tfUEmax = new BHTextField(IBHComponent.MAXVALUE
					+ DTOGCCProfitLossStatementTotalCost.Key.UE);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfUEmax.setValidationRules(rules);
		}
		return tfUEmax;
	}

	public BHTextField getTfABSCHmax() {
		if (tfABSCHmax == null) {
			tfABSCHmax = new BHTextField(IBHComponent.MAXVALUE
					+ DTOGCCProfitLossStatementTotalCost.Key.ABSCH);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfABSCHmax.setValidationRules(rules);
		}
		return tfABSCHmax;
	}

	public BHTextField getTfSBAmax() {
		if (tfSBAmax == null) {
			tfSBAmax = new BHTextField(IBHComponent.MAXVALUE
					+ DTOGCCProfitLossStatementTotalCost.Key.SBA);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfSBAmax.setValidationRules(rules);
		}
		return tfSBAmax;
	}

	public BHTextField getTfAUERGmax() {
		if (tfAUERGmax == null) {
			tfAUERGmax = new BHTextField(IBHComponent.MAXVALUE
					+ DTOGCCProfitLossStatementTotalCost.Key.AUERG);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfAUERGmax.setValidationRules(rules);
		}
		return tfAUERGmax;
	}

	public BHTextField getTfUEmin() {
		if (tfUEmin == null) {
			tfUEmin = new BHTextField(IBHComponent.MINVALUE
					+ DTOGCCProfitLossStatementTotalCost.Key.UE);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfUEmin.setValidationRules(rules);
		}
		return tfUEmin;
	}

	public BHTextField getTfABSCHmin() {
		if (tfABSCHmin == null) {
			tfABSCHmin = new BHTextField(IBHComponent.MINVALUE
					+ DTOGCCProfitLossStatementTotalCost.Key.ABSCH);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfABSCHmin.setValidationRules(rules);
		}
		return tfABSCHmin;
	}

	public BHTextField getTfSBAmin() {
		if (tfSBAmin == null) {
			tfSBAmin = new BHTextField(IBHComponent.MINVALUE
					+ DTOGCCProfitLossStatementTotalCost.Key.SBA);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfSBAmin.setValidationRules(rules);
		}
		return tfSBAmin;
	}

	public BHTextField getTfAUERGmin() {
		if (tfAUERGmin == null) {
			tfAUERGmin = new BHTextField(IBHComponent.MINVALUE
					+ DTOGCCProfitLossStatementTotalCost.Key.AUERG);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfAUERGmin.setValidationRules(rules);
		}
		return tfAUERGmin;
	}

	// Here do the getters for the labels begin

	public BHDescriptionLabel getLUE() {
		if (lUE == null) {
			lUE = new BHDescriptionLabel(
					DTOGCCProfitLossStatementTotalCost.Key.UE);
		}
		return lUE;
	}

	public BHDescriptionLabel getLABSCH() {
		if (lABSCH == null) {
			lABSCH = new BHDescriptionLabel(
					DTOGCCProfitLossStatementTotalCost.Key.ABSCH);
		}
		return lABSCH;
	}

	public BHDescriptionLabel getLSBA() {
		if (lSBA == null) {
			lSBA = new BHDescriptionLabel(
					DTOGCCProfitLossStatementTotalCost.Key.SBA);
		}
		return lSBA;
	}

	public BHDescriptionLabel getLAUERG() {
		if (lAUERG == null) {
			lAUERG = new BHDescriptionLabel(
					DTOGCCProfitLossStatementTotalCost.Key.AUERG);
		}
		return lAUERG;
	}

	public JLabel getLmax() {
		if (lmax == null) {
			lmax = new BHDescriptionLabel("max");
		}
		return lmax;
	}

	public JLabel getLmin() {
		if (lmin == null) {
			lmin = new BHDescriptionLabel("min");
		}
		return lmin;
	}

	// TODO remove main later
	/**
	 * Test main method.
	 */
	public static void main(String args[]) {

		JFrame test = new JFrame("Test for ViewPeriodData1");
		boolean intervalArithmetic = true;
		test.setContentPane(new BHPLSTotalCostForm(intervalArithmetic));
		test.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		test.pack();
		test.setVisible(true);
	}
}
