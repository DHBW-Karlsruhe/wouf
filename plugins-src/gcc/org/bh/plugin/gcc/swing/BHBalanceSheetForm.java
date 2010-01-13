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
import javax.swing.border.TitledBorder;

import org.bh.controller.Controller;
import org.bh.gui.swing.BHDescriptionLabel;
import org.bh.gui.swing.BHTextField;
import org.bh.gui.swing.IBHComponent;
import org.bh.platform.i18n.ITranslator;
import org.bh.plugin.gcc.data.DTOGCCBalanceSheet;
import org.bh.validation.VRIsDouble;
import org.bh.validation.VRIsGreaterThan;
import org.bh.validation.VRIsPositive;
import org.bh.validation.VRMandatory;
import org.bh.validation.ValidationRule;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * This class contains the balance sheet form for the plugin
 * 
 * @author Anton Kharitonov
 * @author Patrick Heinz
 * @version 0.4, 04.01.2010
 * 
 */

@SuppressWarnings("serial")
public class BHBalanceSheetForm extends JPanel {

	private JPanel paktiva;
	private JPanel ppassiva;

	private BHDescriptionLabel lIVG;
	private BHTextField tfIVG;
	private BHDescriptionLabel lSA;
	private BHTextField tfSA;
	private BHDescriptionLabel lFA;
	private BHTextField tfFA;
	private BHDescriptionLabel lVOR;
	private BHTextField tfVOR;
	private BHDescriptionLabel lFSVG;
	private BHTextField tfFSVG;
	private BHDescriptionLabel lWP;
	private BHTextField tfWP;
	private BHDescriptionLabel lKBGGKS;
	private BHTextField tfKBGGKS;
	private BHDescriptionLabel lEK;
	private BHTextField tfEK;
	private BHDescriptionLabel lRS;
	private BHTextField tfRS;
	private BHDescriptionLabel lVB;
	private BHTextField tfVB;

	private BHTextField tfIVGmax;
	private BHTextField tfSAmax;
	private BHTextField tfFAmax;
	private BHTextField tfVORmax;
	private BHTextField tfFSVGmax;
	private BHTextField tfWPmax;
	private BHTextField tfKBGGKSmax;
	private BHTextField tfEKmax;
	private BHTextField tfRSmax;
	private BHTextField tfVBmax;

	private BHTextField tfIVGmin;
	private BHTextField tfSAmin;
	private BHTextField tfFAmin;
	private BHTextField tfVORmin;
	private BHTextField tfFSVGmin;
	private BHTextField tfWPmin;
	private BHTextField tfKBGGKSmin;
	private BHTextField tfEKmin;
	private BHTextField tfRSmin;
	private BHTextField tfVBmin;

	private BHDescriptionLabel lmaxakt;
	private BHDescriptionLabel lminakt;
	private BHDescriptionLabel lmaxpas;
	private BHDescriptionLabel lminpas;

	ITranslator translator = Controller.getTranslator();

	public enum Key {
		
		/**
		 * 
		 */
		BALANCE_SHEET,
		/**
		 * 
		 */
		AKTIVA,

		/**
		 * 
		 */
		PASSIVA;

		public String toString() {
			return getClass().getName() + "." + super.toString();
		}

	}

	/**
	 * Constructor.
	 */
	public BHBalanceSheetForm(boolean intervalArithmetic) {
		this.initialize(intervalArithmetic);
	}

	public void initialize(boolean intervalArithmetic) {
		String rowDef = "4px,p:grow,4px";
		String colDef = "4px,pref:grow,4px,pref:grow,4px";

		FormLayout layout = new FormLayout(colDef, rowDef);
		this.setLayout(layout);

		CellConstraints cons = new CellConstraints();

		this.add(this.getAktiva(intervalArithmetic), cons.xywh(2, 2, 1, 1,
				"fill, fill"));
		this.add(this.getPassiva(intervalArithmetic), cons.xywh(4, 2, 1, 1,
				"fill, fill"));
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED), translator
				.translate(BHBalanceSheetForm.Key.BALANCE_SHEET)));
	}

	public JPanel getAktiva(boolean intervalArithmetic) {
		paktiva = new JPanel();

		String rowDef = "4px,p,4px,p,4px,p,4px,p,4px,p,4px,p,4px,p,4px";
		String colDef = "4px,right:pref,4px,max(100px;pref):grow,4px,pref,4px";
		if (intervalArithmetic) {
			rowDef = "4px,p," + rowDef;
			colDef += ",max(100px;pref):grow,4px,pref,4px";
		}

		FormLayout layout = new FormLayout(colDef, rowDef);
		paktiva.setLayout(layout);

		CellConstraints cons = new CellConstraints();

		if (!intervalArithmetic) {
			paktiva.add(this.getLIVG(), cons.xywh(2, 2, 1, 1));
			paktiva.add(this.getLSA(), cons.xywh(2, 4, 1, 1));
			paktiva.add(this.getLFA(), cons.xywh(2, 6, 1, 1));
			paktiva.add(this.getLVOR(), cons.xywh(2, 8, 1, 1));
			paktiva.add(this.getLFSVG(), cons.xywh(2, 10, 1, 1));
			paktiva.add(this.getLWP(), cons.xywh(2, 12, 1, 1));
			paktiva.add(this.getLKBGGKS(), cons.xywh(2, 14, 1, 1));

			paktiva.add(this.getTfIVG(), cons.xywh(4, 2, 1, 1));
			paktiva.add(new JLabel(translator.translate("currency")), cons.xywh(6, 2, 1, 1));
			paktiva.add(this.getTfSA(), cons.xywh(4, 4, 1, 1));
			paktiva.add(new JLabel(translator.translate("currency")), cons.xywh(6, 4, 1, 1));
			paktiva.add(this.getTfFA(), cons.xywh(4, 6, 1, 1));
			paktiva.add(new JLabel(translator.translate("currency")), cons.xywh(6, 6, 1, 1));
			paktiva.add(this.getTfVOR(), cons.xywh(4, 8, 1, 1));
			paktiva.add(new JLabel(translator.translate("currency")), cons.xywh(6, 8, 1, 1));
			paktiva.add(this.getTfFSVG(), cons.xywh(4, 10, 1, 1));
			paktiva.add(new JLabel(translator.translate("currency")), cons.xywh(6, 10, 1, 1));
			paktiva.add(this.getTfWP(), cons.xywh(4, 12, 1, 1));
			paktiva.add(new JLabel(translator.translate("currency")), cons.xywh(6, 12, 1, 1));
			paktiva.add(this.getTfKBGGKS(), cons.xywh(4, 14, 1, 1));
			paktiva.add(new JLabel(translator.translate("currency")), cons.xywh(6, 14, 1, 1));
		} else {
			layout.setColumnGroups(new int[][] { { 4, 8 } });

			paktiva.add(this.getLIVG(), cons.xywh(2, 4, 1, 1));
			paktiva.add(this.getLSA(), cons.xywh(2, 6, 1, 1));
			paktiva.add(this.getLFA(), cons.xywh(2, 8, 1, 1));
			paktiva.add(this.getLVOR(), cons.xywh(2, 10, 1, 1));
			paktiva.add(this.getLFSVG(), cons.xywh(2, 12, 1, 1));
			paktiva.add(this.getLWP(), cons.xywh(2, 14, 1, 1));
			paktiva.add(this.getLKBGGKS(), cons.xywh(2, 16, 1, 1));

//			paktiva.add(new JSeparator(SwingConstants.VERTICAL), cons.xywh(6,
//					2, 1, 15));
			paktiva.add(this.getLminakt(), cons.xywh(4, 2, 1, 1,
					"center,default"));
			paktiva.add(this.getLmaxakt(), cons.xywh(8, 2, 1, 1,
					"center,default"));

			paktiva.add(this.getTfIVGmin(), cons.xywh(4, 4, 1, 1));
			paktiva.add(new JLabel(translator.translate("currency")), cons.xywh(6, 4, 1, 1));
			paktiva.add(this.getTfSAmin(), cons.xywh(4, 6, 1, 1));
			paktiva.add(new JLabel(translator.translate("currency")), cons.xywh(6, 6, 1, 1));
			paktiva.add(this.getTfFAmin(), cons.xywh(4, 8, 1, 1));
			paktiva.add(new JLabel(translator.translate("currency")), cons.xywh(6, 8, 1, 1));
			paktiva.add(this.getTfVORmin(), cons.xywh(4, 10, 1, 1));
			paktiva.add(new JLabel(translator.translate("currency")), cons.xywh(6, 10, 1, 1));
			paktiva.add(this.getTfFSVGmin(), cons.xywh(4, 12, 1, 1));
			paktiva.add(new JLabel(translator.translate("currency")), cons.xywh(6, 12, 1, 1));
			paktiva.add(this.getTfWPmin(), cons.xywh(4, 14, 1, 1));
			paktiva.add(new JLabel(translator.translate("currency")), cons.xywh(6, 14, 1, 1));
			paktiva.add(this.getTfKBGGKSmin(), cons.xywh(4, 16, 1, 1));
			paktiva.add(new JLabel(translator.translate("currency")), cons.xywh(6, 16, 1, 1));

			paktiva.add(this.getTfIVGmax(), cons.xywh(8, 4, 1, 1));
			paktiva.add(new JLabel(translator.translate("currency")), cons.xywh(10, 4, 1, 1));
			paktiva.add(this.getTfSAmax(), cons.xywh(8, 6, 1, 1));
			paktiva.add(new JLabel(translator.translate("currency")), cons.xywh(10, 6, 1, 1));
			paktiva.add(this.getTfFAmax(), cons.xywh(8, 8, 1, 1));
			paktiva.add(new JLabel(translator.translate("currency")), cons.xywh(10, 8, 1, 1));
			paktiva.add(this.getTfVORmax(), cons.xywh(8, 10, 1, 1));
			paktiva.add(new JLabel(translator.translate("currency")), cons.xywh(10, 10, 1, 1));
			paktiva.add(this.getTfFSVGmax(), cons.xywh(8, 12, 1, 1));
			paktiva.add(new JLabel(translator.translate("currency")), cons.xywh(10, 12, 1, 1));
			paktiva.add(this.getTfWPmax(), cons.xywh(8, 14, 1, 1));
			paktiva.add(new JLabel(translator.translate("currency")), cons.xywh(10, 14, 1, 1));
			paktiva.add(this.getTfKBGGKSmax(), cons.xywh(8, 16, 1, 1));
			paktiva.add(new JLabel(translator.translate("currency")), cons.xywh(10, 16, 1, 1));
		}

		// TODO add handler for locale change
		paktiva.setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED), translator
				.translate(BHBalanceSheetForm.Key.AKTIVA), TitledBorder.CENTER,
				TitledBorder.DEFAULT_JUSTIFICATION));

		return paktiva;
	}

	public JPanel getPassiva(boolean intervalArithmetic) {
		ppassiva = new JPanel();

		String rowDef = "4px,p,4px,p,4px,p,4px";
		String colDef = "4px,right:pref,4px,max(100px;pref):grow,4px,pref,4px";
		if (intervalArithmetic) {
			rowDef = "4px,p," + rowDef;
			colDef += ",max(100px;pref):grow,4px,pref,4px";
		}

		FormLayout layout = new FormLayout(colDef, rowDef);
		ppassiva.setLayout(layout);

		CellConstraints cons = new CellConstraints();

		if (!intervalArithmetic) {
			ppassiva.add(this.getLEK(), cons.xywh(2, 2, 1, 1));
			ppassiva.add(this.getLRS(), cons.xywh(2, 4, 1, 1));
			ppassiva.add(this.getLVB(), cons.xywh(2, 6, 1, 1));

			ppassiva.add(this.getTfEK(), cons.xywh(4, 2, 1, 1));
			ppassiva.add(new JLabel(translator.translate("currency")), cons.xywh(6, 2, 1, 1));
			ppassiva.add(this.getTfRS(), cons.xywh(4, 4, 1, 1));
			ppassiva.add(new JLabel(translator.translate("currency")), cons.xywh(6, 4, 1, 1));
			ppassiva.add(this.getTfVB(), cons.xywh(4, 6, 1, 1));
			ppassiva.add(new JLabel(translator.translate("currency")), cons.xywh(6, 6, 1, 1));
		} else {
			layout.setColumnGroups(new int[][] { { 4, 8 } });
			
			ppassiva.add(this.getLEK(), cons.xywh(2, 4, 1, 1));
			ppassiva.add(this.getLRS(), cons.xywh(2, 6, 1, 1));
			ppassiva.add(this.getLVB(), cons.xywh(2, 8, 1, 1));

//			ppassiva.add(new JSeparator(SwingConstants.VERTICAL), cons.xywh(6,
//					2, 1, 7));
			ppassiva.add(this.getLminpas(), cons.xywh(4, 2, 1, 1,
					"center,default"));
			ppassiva.add(this.getLmaxpas(), cons.xywh(8, 2, 1, 1,
					"center,default"));

			ppassiva.add(this.getTfEKmin(), cons.xywh(4, 4, 1, 1));
			ppassiva.add(new JLabel(translator.translate("currency")), cons.xywh(6, 4, 1, 1));
			ppassiva.add(this.getTfRSmin(), cons.xywh(4, 6, 1, 1));
			ppassiva.add(new JLabel(translator.translate("currency")), cons.xywh(6, 6, 1, 1));
			ppassiva.add(this.getTfVBmin(), cons.xywh(4, 8, 1, 1));
			ppassiva.add(new JLabel(translator.translate("currency")), cons.xywh(6, 8, 1, 1));

			ppassiva.add(this.getTfEKmax(), cons.xywh(8, 4, 1, 1));
			ppassiva.add(new JLabel(translator.translate("currency")), cons.xywh(10, 4, 1, 1));
			ppassiva.add(this.getTfRSmax(), cons.xywh(8, 6, 1, 1));
			ppassiva.add(new JLabel(translator.translate("currency")), cons.xywh(10, 6, 1, 1));
			ppassiva.add(this.getTfVBmax(), cons.xywh(8, 8, 1, 1));
			ppassiva.add(new JLabel(translator.translate("currency")), cons.xywh(10, 8, 1, 1));
		}

		// TODO add handler for locale change
		ppassiva.setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED), translator
				.translate(BHBalanceSheetForm.Key.PASSIVA),
				TitledBorder.CENTER, TitledBorder.DEFAULT_JUSTIFICATION));

		return ppassiva;
	}

	public BHDescriptionLabel getLIVG() {
		if (lIVG == null) {
			lIVG = new BHDescriptionLabel(DTOGCCBalanceSheet.Key.IVG);
		}
		return lIVG;
	}

	public BHDescriptionLabel getLSA() {
		if (lSA == null) {
			lSA = new BHDescriptionLabel(DTOGCCBalanceSheet.Key.SA);
		}
		return lSA;
	}

	public BHDescriptionLabel getLFA() {
		if (lFA == null) {
			lFA = new BHDescriptionLabel(DTOGCCBalanceSheet.Key.FA);
		}
		return lFA;
	}

	public BHDescriptionLabel getLVOR() {
		if (lVOR == null) {
			lVOR = new BHDescriptionLabel(DTOGCCBalanceSheet.Key.VOR);
		}
		return lVOR;
	}

	public BHDescriptionLabel getLFSVG() {
		if (lFSVG == null) {
			lFSVG = new BHDescriptionLabel(DTOGCCBalanceSheet.Key.FSVG);
		}
		return lFSVG;
	}

	public BHDescriptionLabel getLWP() {
		if (lWP == null) {
			lWP = new BHDescriptionLabel(DTOGCCBalanceSheet.Key.WP);
		}
		return lWP;
	}

	public BHDescriptionLabel getLKBGGKS() {
		if (lKBGGKS == null) {
			lKBGGKS = new BHDescriptionLabel(DTOGCCBalanceSheet.Key.KBGGKS);
		}
		return lKBGGKS;
	}

	public BHDescriptionLabel getLEK() {
		if (lEK == null) {
			lEK = new BHDescriptionLabel(DTOGCCBalanceSheet.Key.EK);
		}
		return lEK;
	}

	public BHDescriptionLabel getLRS() {
		if (lRS == null) {
			lRS = new BHDescriptionLabel(DTOGCCBalanceSheet.Key.RS);
		}
		return lRS;
	}

	public BHDescriptionLabel getLVB() {
		if (lVB == null) {
			lVB = new BHDescriptionLabel(DTOGCCBalanceSheet.Key.VB);
		}
		return lVB;
	}

	// Here do the getters for the textfields begin

	/**
	 * Getter method for component tfIVG.
	 * 
	 * @return the initialized component
	 */
	public BHTextField getTfIVG() {
		if (tfIVG == null) {
			tfIVG = new BHTextField(DTOGCCBalanceSheet.Key.IVG);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE };
			tfIVG.setValidationRules(rules);
		}
		return tfIVG;
	}

	/**
	 * Getter method for component tfSA.
	 * 
	 * @return the initialized component
	 */
	public BHTextField getTfSA() {
		if (tfSA == null) {
			tfSA = new BHTextField(DTOGCCBalanceSheet.Key.SA);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE };
			tfSA.setValidationRules(rules);
		}
		return tfSA;
	}

	/**
	 * Getter method for component tfFA.
	 * 
	 * @return the initialized component
	 */
	public BHTextField getTfFA() {
		if (tfFA == null) {
			tfFA = new BHTextField(DTOGCCBalanceSheet.Key.FA);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE };
			tfFA.setValidationRules(rules);
		}
		return tfFA;
	}

	/**
	 * Getter method for component tfVOR.
	 * 
	 * @return the initialized component
	 */
	public BHTextField getTfVOR() {
		if (tfVOR == null) {
			tfVOR = new BHTextField(DTOGCCBalanceSheet.Key.VOR);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE };
			tfVOR.setValidationRules(rules);
		}
		return tfVOR;
	}

	/**
	 * Getter method for component tfFSVG.
	 * 
	 * @return the initialized component
	 */
	public BHTextField getTfFSVG() {
		if (tfFSVG == null) {
			tfFSVG = new BHTextField(DTOGCCBalanceSheet.Key.FSVG);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE };
			tfFSVG.setValidationRules(rules);
		}
		return tfFSVG;
	}

	/**
	 * Getter method for component tfWP.
	 * 
	 * @return the initialized component
	 */
	public BHTextField getTfWP() {
		if (tfWP == null) {
			tfWP = new BHTextField(DTOGCCBalanceSheet.Key.WP);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE };
			tfWP.setValidationRules(rules);
		}
		return tfWP;
	}

	/**
	 * Getter method for component tfKBGGKS.
	 * 
	 * @return the initialized component
	 */
	public BHTextField getTfKBGGKS() {
		if (tfKBGGKS == null) {
			tfKBGGKS = new BHTextField(DTOGCCBalanceSheet.Key.KBGGKS);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE };
			tfKBGGKS.setValidationRules(rules);
		}
		return tfKBGGKS;
	}

	/**
	 * Getter method for component tfEK.
	 * 
	 * @return the initialized component
	 */
	public BHTextField getTfEK() {
		if (tfEK == null) {
			tfEK = new BHTextField(DTOGCCBalanceSheet.Key.EK);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE };
			tfEK.setValidationRules(rules);
		}
		return tfEK;
	}

	/**
	 * Getter method for component tfRS.
	 * 
	 * @return the initialized component
	 */
	public BHTextField getTfRS() {
		if (tfRS == null) {
			tfRS = new BHTextField(DTOGCCBalanceSheet.Key.RS);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE };
			tfRS.setValidationRules(rules);
		}
		return tfRS;
	}

	/**
	 * Getter method for component tfIVB.
	 * 
	 * @return the initialized component
	 */
	public BHTextField getTfVB() {
		if (tfVB == null) {
			tfVB = new BHTextField(DTOGCCBalanceSheet.Key.VB);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE };
			tfVB.setValidationRules(rules);
		}
		return tfVB;
	}

	// Maximum TextFields

	/**
	 * Getter method for component tfIVGmax.
	 * 
	 * @return the initialized component
	 */
	public BHTextField getTfIVGmax() {
		if (tfIVGmax == null) {
			tfIVGmax = new BHTextField(IBHComponent.MAXVALUE
					+ DTOGCCBalanceSheet.Key.IVG);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE,
					new VRIsGreaterThan(getTfIVGmin(), true) };
			tfIVGmax.setValidationRules(rules);
		}
		return tfIVGmax;
	}

	/**
	 * Getter method for component tfSAmax.
	 * 
	 * @return the initialized component
	 */
	public BHTextField getTfSAmax() {
		if (tfSAmax == null) {
			tfSAmax = new BHTextField(IBHComponent.MAXVALUE
					+ DTOGCCBalanceSheet.Key.SA);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE,
					new VRIsGreaterThan(getTfSAmin(), true) };
			tfSAmax.setValidationRules(rules);
		}
		return tfSAmax;
	}

	/**
	 * Getter method for component tfFAmax.
	 * 
	 * @return the initialized component
	 */
	public BHTextField getTfFAmax() {
		if (tfFAmax == null) {
			tfFAmax = new BHTextField(IBHComponent.MAXVALUE
					+ DTOGCCBalanceSheet.Key.FA);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE,
					new VRIsGreaterThan(getTfFAmin(), true) };
			tfFAmax.setValidationRules(rules);
		}
		return tfFAmax;
	}

	/**
	 * Getter method for component tfVORmax.
	 * 
	 * @return the initialized component
	 */
	public BHTextField getTfVORmax() {
		if (tfVORmax == null) {
			tfVORmax = new BHTextField(IBHComponent.MAXVALUE
					+ DTOGCCBalanceSheet.Key.VOR);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE,
					new VRIsGreaterThan(getTfVORmin(), true) };
			tfVORmax.setValidationRules(rules);
		}
		return tfVORmax;
	}

	/**
	 * Getter method for component tfFSVGmax.
	 * 
	 * @return the initialized component
	 */
	public BHTextField getTfFSVGmax() {
		if (tfFSVGmax == null) {
			tfFSVGmax = new BHTextField(IBHComponent.MAXVALUE
					+ DTOGCCBalanceSheet.Key.FSVG);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE,
					new VRIsGreaterThan(getTfFSVGmin(), true)};
			tfFSVGmax.setValidationRules(rules);
		}
		return tfFSVGmax;
	}

	/**
	 * Getter method for component tfWPmax.
	 * 
	 * @return the initialized component
	 */
	public BHTextField getTfWPmax() {
		if (tfWPmax == null) {
			tfWPmax = new BHTextField(IBHComponent.MAXVALUE
					+ DTOGCCBalanceSheet.Key.WP);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE,
					new VRIsGreaterThan(getTfWPmin(), true) };
			tfWPmax.setValidationRules(rules);
		}
		return tfWPmax;
	}

	/**
	 * Getter method for component tfKBGGKSmax.
	 * 
	 * @return the initialized component
	 */
	public BHTextField getTfKBGGKSmax() {
		if (tfKBGGKSmax == null) {
			tfKBGGKSmax = new BHTextField(IBHComponent.MAXVALUE
					+ DTOGCCBalanceSheet.Key.KBGGKS);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE,
					new VRIsGreaterThan(getTfKBGGKSmin(), true) };
			tfKBGGKSmax.setValidationRules(rules);
		}
		return tfKBGGKSmax;
	}

	/**
	 * Getter method for component tfEKmax.
	 * 
	 * @return the initialized component
	 */
	public BHTextField getTfEKmax() {
		if (tfEKmax == null) {
			tfEKmax = new BHTextField(IBHComponent.MAXVALUE
					+ DTOGCCBalanceSheet.Key.EK);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE,
					new VRIsGreaterThan(getTfEKmin(), true)};
			tfEKmax.setValidationRules(rules);
		}
		return tfEKmax;
	}

	/**
	 * Getter method for component tfRSmax.
	 * 
	 * @return the initialized component
	 */
	public BHTextField getTfRSmax() {
		if (tfRSmax == null) {
			tfRSmax = new BHTextField(IBHComponent.MAXVALUE
					+ DTOGCCBalanceSheet.Key.RS);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE,
					new VRIsGreaterThan(getTfRSmin(), true)};
			tfRSmax.setValidationRules(rules);
		}
		return tfRSmax;
	}

	/**
	 * Getter method for component tfVBmax.
	 * 
	 * @return the initialized component
	 */
	public BHTextField getTfVBmax() {
		if (tfVBmax == null) {
			tfVBmax = new BHTextField(IBHComponent.MAXVALUE
					+ DTOGCCBalanceSheet.Key.VB);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE,
					new VRIsGreaterThan(getTfVBmin(), true)};
			tfVBmax.setValidationRules(rules);
		}
		return tfVBmax;
	}

	// Minimum TextFields

	/**
	 * Getter method for component tfIVGmin.
	 * 
	 * @return the initialized component
	 */
	public BHTextField getTfIVGmin() {
		if (tfIVGmin == null) {
			tfIVGmin = new BHTextField(IBHComponent.MINVALUE
					+ DTOGCCBalanceSheet.Key.IVG);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE };
			tfIVGmin.setValidationRules(rules);
		}
		return tfIVGmin;
	}

	/**
	 * Getter method for component tfSAmin.
	 * 
	 * @return the initialized component
	 */
	public BHTextField getTfSAmin() {
		if (tfSAmin == null) {
			tfSAmin = new BHTextField(IBHComponent.MINVALUE
					+ DTOGCCBalanceSheet.Key.SA);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE };
			tfSAmin.setValidationRules(rules);
		}
		return tfSAmin;
	}

	/**
	 * Getter method for component tfFAmin.
	 * 
	 * @return the initialized component
	 */
	public BHTextField getTfFAmin() {
		if (tfFAmin == null) {
			tfFAmin = new BHTextField(IBHComponent.MINVALUE
					+ DTOGCCBalanceSheet.Key.FA);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE };
			tfFAmin.setValidationRules(rules);
		}
		return tfFAmin;
	}

	/**
	 * Getter method for component tfVORmin.
	 * 
	 * @return the initialized component
	 */
	public BHTextField getTfVORmin() {
		if (tfVORmin == null) {
			tfVORmin = new BHTextField(IBHComponent.MINVALUE
					+ DTOGCCBalanceSheet.Key.VOR);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE };
			tfVORmin.setValidationRules(rules);
		}
		return tfVORmin;
	}

	/**
	 * Getter method for component tfFSVGmin.
	 * 
	 * @return the initialized component
	 */
	public BHTextField getTfFSVGmin() {
		if (tfFSVGmin == null) {
			tfFSVGmin = new BHTextField(IBHComponent.MINVALUE
					+ DTOGCCBalanceSheet.Key.FSVG);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE };
			tfFSVGmin.setValidationRules(rules);
		}
		return tfFSVGmin;
	}

	/**
	 * Getter method for component tfWPmin.
	 * 
	 * @return the initialized component
	 */
	public BHTextField getTfWPmin() {
		if (tfWPmin == null) {
			tfWPmin = new BHTextField(IBHComponent.MINVALUE
					+ DTOGCCBalanceSheet.Key.WP);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE };
			tfWPmin.setValidationRules(rules);
		}
		return tfWPmin;
	}

	/**
	 * Getter method for component tfKBGGKSmin.
	 * 
	 * @return the initialized component
	 */
	public BHTextField getTfKBGGKSmin() {
		if (tfKBGGKSmin == null) {
			tfKBGGKSmin = new BHTextField(IBHComponent.MINVALUE
					+ DTOGCCBalanceSheet.Key.KBGGKS);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE };
			tfKBGGKSmin.setValidationRules(rules);
		}
		return tfKBGGKSmin;
	}

	/**
	 * Getter method for component tfEKmin.
	 * 
	 * @return the initialized component
	 */
	public BHTextField getTfEKmin() {
		if (tfEKmin == null) {
			tfEKmin = new BHTextField(IBHComponent.MINVALUE
					+ DTOGCCBalanceSheet.Key.EK);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE };
			tfEKmin.setValidationRules(rules);
		}
		return tfEKmin;
	}

	/**
	 * Getter method for component tfRSmin.
	 * 
	 * @return the initialized component
	 */
	public BHTextField getTfRSmin() {
		if (tfRSmin == null) {
			tfRSmin = new BHTextField(IBHComponent.MINVALUE
					+ DTOGCCBalanceSheet.Key.RS);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE };
			tfRSmin.setValidationRules(rules);
		}
		return tfRSmin;
	}

	/**
	 * Getter method for component tfVBmin.
	 * 
	 * @return the initialized component
	 */
	public BHTextField getTfVBmin() {
		if (tfVBmin == null) {
			tfVBmin = new BHTextField(IBHComponent.MINVALUE
					+ DTOGCCBalanceSheet.Key.VB);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE };
			tfVBmin.setValidationRules(rules);
		}
		return tfVBmin;
	}

	public BHDescriptionLabel getLmaxakt() {
		if (lmaxakt == null) {
			lmaxakt = new BHDescriptionLabel(translator.translate("max"));
		}
		return lmaxakt;
	}

	public BHDescriptionLabel getLminakt() {
		if (lminakt == null) {
			lminakt = new BHDescriptionLabel(translator.translate("min"));
		}
		return lminakt;
	}

	public BHDescriptionLabel getLmaxpas() {
		if (lmaxpas == null) {
			lmaxpas = new BHDescriptionLabel(translator.translate("max"));
		}
		return lmaxpas;
	}

	public BHDescriptionLabel getLminpas() {
		if (lminpas == null) {
			lminpas = new BHDescriptionLabel(translator.translate("min"));
		}
		return lminpas;
	}

	// TODO remove main later
	/**
	 * Test main method.
	 */
	public static void main(String args[]) {

		JFrame test = new JFrame("Test for ViewPeriodData1");
		boolean intervalArithmetic = true;
		test.setContentPane(new BHBalanceSheetForm(intervalArithmetic));
		test.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		test.pack();
		test.setVisible(true);
	}
}
