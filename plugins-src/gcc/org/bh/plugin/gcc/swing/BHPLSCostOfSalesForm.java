package org.bh.plugin.gcc.swing;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import org.bh.gui.IBHComponent;
import org.bh.gui.swing.comp.BHDescriptionLabel;
import org.bh.gui.swing.comp.BHTextField;
import org.bh.gui.swing.forms.border.BHBorderFactory;
import org.bh.platform.i18n.BHTranslator;
import org.bh.platform.i18n.ITranslator;
import org.bh.plugin.gcc.data.DTOGCCProfitLossStatementCostOfSales;
import org.bh.validation.VRIsDouble;
import org.bh.validation.VRIsGreaterThan;
import org.bh.validation.VRMandatory;
import org.bh.validation.ValidationRule;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * This class contains the cost of sales form for the plugin
 * 
 * @author Anton Kharitonov
 * @author Patrick Heinz
 * @version 0.1, 08.01.2010
 * 
 */
@SuppressWarnings("serial")
public class BHPLSCostOfSalesForm extends JPanel {

	private BHTextField tfUE;
	private BHTextField tfHK;
	private BHTextField tfVVSBA;
	private BHTextField tfSBE;

	private BHTextField tfUEmax;
	private BHTextField tfHKmax;
	private BHTextField tfVVSBAmax;
	private BHTextField tfSBEmax;

	private BHTextField tfUEmin;
	private BHTextField tfHKmin;
	private BHTextField tfVVSBAmin;
	private BHTextField tfSBEmin;

	private BHDescriptionLabel lUE;
	private BHDescriptionLabel lHK;
	private BHDescriptionLabel lVVSBA;
	private BHDescriptionLabel lSBE;

	private JLabel lmin;
	private JLabel lmax;

	final ITranslator translator = BHTranslator.getInstance();

	public enum Key {
		/**
	 * 
	 */
		PLS_CostOfSales;

		@Override
		public String toString() {
			return getClass().getName() + "." + super.toString();
		}

	}

	public BHPLSCostOfSalesForm(boolean intervalArithmetic) {
		this.initialize(intervalArithmetic);
	}

	public void initialize(boolean intervalArithmetic) {
		String rowDef = "4px,p,4px,p,4px,p,4px,p,4px";
		String colDef = "4px,right:pref,4px,max(100px;pref):grow,4px,pref,4px";
		if (intervalArithmetic) {
			rowDef = "4px,p," + rowDef;
			colDef += ",max(100px;pref):grow,4px,pref,4px";
		}

		FormLayout layout = new FormLayout(colDef, rowDef);
		this.setLayout(layout);

		CellConstraints cons = new CellConstraints();

		if (!intervalArithmetic) {
			this.add(this.getLUE(), cons.xywh(2, 2, 1, 1));
			this.add(this.getLSBE(), cons.xywh(2, 4, 1, 1));
			this.add(this.getLHK(), cons.xywh(2, 6, 1, 1));
			this.add(this.getLVVSBA(), cons.xywh(2, 8, 1, 1));
		
			this.add(this.getTfUE(), cons.xywh(4, 2, 1, 1));
			this.add(new JLabel(translator.translate("currency")), cons.xywh(6, 2, 1, 1));
			this.add(this.getTfSBE(), cons.xywh(4, 4, 1, 1));
			this.add(new JLabel(translator.translate("currency")), cons.xywh(6, 4, 1, 1));
			this.add(this.getTfHK(), cons.xywh(4, 6, 1, 1));
			this.add(new JLabel(translator.translate("currency")), cons.xywh(6, 6, 1, 1));
			this.add(this.getTfVVSBA(), cons.xywh(4, 8, 1, 1));
			this.add(new JLabel(translator.translate("currency")), cons.xywh(6, 8, 1, 1));

		} else {
			layout.setColumnGroups(new int[][] { { 4, 8 } });
			
			this.add(this.getLUE(), cons.xywh(2, 4, 1, 1));
			this.add(this.getLSBE(), cons.xywh(2, 6, 1, 1));
			this.add(this.getLHK(), cons.xywh(2, 8, 1, 1));
			this.add(this.getLVVSBA(), cons.xywh(2, 10, 1, 1));

			
//			this.add(new JSeparator(SwingConstants.VERTICAL), cons.xywh(6, 2,
//					1, 9));
			this.add(this.getLmin(), cons.xywh(4, 2, 1, 1, "center,default"));
			this.add(this.getLmax(), cons.xywh(8, 2, 1, 1, "center,default"));

			this.add(this.getTfUEmin(), cons.xywh(4, 4, 1, 1));
			this.add(new JLabel(translator.translate("currency")), cons.xywh(6, 4, 1, 1));
			this.add(this.getTfSBEmin(), cons.xywh(4, 6, 1, 1));
			this.add(new JLabel(translator.translate("currency")), cons.xywh(6, 6, 1, 1));
			this.add(this.getTfHKmin(), cons.xywh(4, 8, 1, 1));
			this.add(new JLabel(translator.translate("currency")), cons.xywh(6, 8, 1, 1));
			this.add(this.getTfVVSBAmin(), cons.xywh(4, 10, 1, 1));
			this.add(new JLabel(translator.translate("currency")), cons.xywh(6, 10, 1, 1));


			this.add(this.getTfUEmax(), cons.xywh(8, 4, 1, 1));
			this.add(new JLabel(translator.translate("currency")), cons.xywh(10, 4, 1, 1));
			this.add(this.getTfSBEmax(), cons.xywh(8, 6, 1, 1));
			this.add(new JLabel(translator.translate("currency")), cons.xywh(10, 6, 1, 1));
			this.add(this.getTfHKmax(), cons.xywh(8, 8, 1, 1));
			this.add(new JLabel(translator.translate("currency")), cons.xywh(10, 8, 1, 1));
			this.add(this.getTfVVSBAmax(), cons.xywh(8, 10, 1, 1));
			this.add(new JLabel(translator.translate("currency")), cons.xywh(10, 10, 1, 1));

		}
		
		this.setBorder(BHBorderFactory.getInstacnce().createTitledBorder(BHBorderFactory.getInstacnce()
				.createEtchedBorder(EtchedBorder.LOWERED), 
				BHPLSCostOfSalesForm.Key.PLS_CostOfSales));

	}

	// Here do the getters for the textfields begin
	
	public BHTextField getTfUE() {
		if (tfUE == null) {
			tfUE = new BHTextField(DTOGCCProfitLossStatementCostOfSales.Key.UE);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfUE.setValidationRules(rules);
		}
		return tfUE;
	}

	public BHTextField getTfHK() {
		if (tfHK == null) {
			tfHK = new BHTextField(DTOGCCProfitLossStatementCostOfSales.Key.HK);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfHK.setValidationRules(rules);
		}
		return tfHK;
	}

	public BHTextField getTfVVSBA() {
		if (tfVVSBA == null) {
			tfVVSBA = new BHTextField(
					DTOGCCProfitLossStatementCostOfSales.Key.VVSBA);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfVVSBA.setValidationRules(rules);
		}
		return tfVVSBA;
	}

	public BHTextField getTfSBE() {
		if (tfSBE == null) {
			tfSBE = new BHTextField(
					DTOGCCProfitLossStatementCostOfSales.Key.SBE);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfSBE.setValidationRules(rules);
		}
		return tfSBE;
	}

	public BHTextField getTfUEmax() {
		if (tfUEmax == null) {
			tfUEmax = new BHTextField(IBHComponent.MAXVALUE
					+ DTOGCCProfitLossStatementCostOfSales.Key.UE);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE,
					new VRIsGreaterThan(getTfUEmin(), true) };
			tfUEmax.setValidationRules(rules);
		}
		return tfUEmax;
	}

	public BHTextField getTfHKmax() {
		if (tfHKmax == null) {
			tfHKmax = new BHTextField(IBHComponent.MAXVALUE
					+ DTOGCCProfitLossStatementCostOfSales.Key.HK);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE,
					new VRIsGreaterThan(getTfHKmin(), true) };
			tfHKmax.setValidationRules(rules);
		}
		return tfHKmax;
	}

	public BHTextField getTfVVSBAmax() {
		if (tfVVSBAmax == null) {
			tfVVSBAmax = new BHTextField(IBHComponent.MAXVALUE
					+ DTOGCCProfitLossStatementCostOfSales.Key.VVSBA);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE,
					new VRIsGreaterThan(getTfVVSBAmin(), true) };
			tfVVSBAmax.setValidationRules(rules);
		}
		return tfVVSBAmax;
	}

	public BHTextField getTfSBEmax() {
		if (tfSBEmax == null) {
			tfSBEmax = new BHTextField(IBHComponent.MAXVALUE
					+ DTOGCCProfitLossStatementCostOfSales.Key.SBE);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE,
					new VRIsGreaterThan(getTfSBEmin(), true) };
			tfSBEmax.setValidationRules(rules);
		}
		return tfSBEmax;
	}

	public BHTextField getTfUEmin() {
		if (tfUEmin == null) {
			tfUEmin = new BHTextField(IBHComponent.MINVALUE
					+ DTOGCCProfitLossStatementCostOfSales.Key.UE);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfUEmin.setValidationRules(rules);
		}
		return tfUEmin;
	}

	public BHTextField getTfHKmin() {
		if (tfHKmin == null) {
			tfHKmin = new BHTextField(IBHComponent.MINVALUE
					+ DTOGCCProfitLossStatementCostOfSales.Key.HK);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfHKmin.setValidationRules(rules);
		}
		return tfHKmin;
	}

	public BHTextField getTfVVSBAmin() {
		if (tfVVSBAmin == null) {
			tfVVSBAmin = new BHTextField(IBHComponent.MINVALUE
					+ DTOGCCProfitLossStatementCostOfSales.Key.VVSBA);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfVVSBAmin.setValidationRules(rules);
		}
		return tfVVSBAmin;
	}

	public BHTextField getTfSBEmin() {
		if (tfSBEmin == null) {
			tfSBEmin = new BHTextField(IBHComponent.MINVALUE
					+ DTOGCCProfitLossStatementCostOfSales.Key.SBE);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfSBEmin.setValidationRules(rules);
		}
		return tfSBEmin;
	}

	// Here do the getters for the labels begin

	public BHDescriptionLabel getLUE() {
		if (lUE == null) {
			lUE = new BHDescriptionLabel(
					DTOGCCProfitLossStatementCostOfSales.Key.UE);
		}
		return lUE;
	}

	public BHDescriptionLabel getLHK() {
		if (lHK == null) {
			lHK = new BHDescriptionLabel(
					DTOGCCProfitLossStatementCostOfSales.Key.HK);
		}
		return lHK;
	}

	public BHDescriptionLabel getLVVSBA() {
		if (lVVSBA == null) {
			lVVSBA = new BHDescriptionLabel(
					DTOGCCProfitLossStatementCostOfSales.Key.VVSBA);
		}
		return lVVSBA;
	}

	public BHDescriptionLabel getLSBE() {
		if (lSBE == null) {
			lSBE = new BHDescriptionLabel(
					DTOGCCProfitLossStatementCostOfSales.Key.SBE);
		}
		return lSBE;
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
		test.setContentPane(new BHPLSCostOfSalesForm(intervalArithmetic));
		test.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		test.pack();
		test.setVisible(true);
	}
}
