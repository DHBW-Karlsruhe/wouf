package org.bh.plugin.swing;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.bh.gui.ValidationMethods;
import org.bh.gui.swing.BHLabel;
import org.bh.gui.swing.BHTextField;
import org.bh.gui.swing.IBHComponent;
import org.bh.platform.i18n.BHTranslator;
import org.bh.plugin.gcc.data.DTOGCCBalanceSheet;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * This class contains the balance sheet form for the ggc plugin
 * 
 * @author Anton Kharitonov
 * @author Patrick Heinz
 * @version 0.3, 01.01.2010
 * 
 */

public class BHBalanceSheetForm extends JPanel {

	private JPanel paktiva;
	private JPanel ppassiva;

	private BHLabel lIVG;
	private BHTextField tfIVG;
	private BHLabel lSA;
	private BHTextField tfSA;
	private BHLabel lFA;
	private BHTextField tfFA;
	private BHLabel lVOR;
	private BHTextField tfVOR;
	private BHLabel lFSVG;
	private BHTextField tfFSVG;
	private BHLabel lWP;
	private BHTextField tfWP;
	private BHLabel lKBGGKS;
	private BHTextField tfKBGGKS;
	private BHLabel lEK;
	private BHTextField tfEK;
	private BHLabel lRS;
	private BHTextField tfRS;
	private BHLabel lVB;
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

	private BHLabel lmaxakt;
	private BHLabel lminakt;
	private BHLabel lmaxpas;
	private BHLabel lminpas;

	final BHTranslator translator = BHTranslator.getInstance();

	/**
	 * Constructor.
	 */
	public BHBalanceSheetForm() {

		this.initialize();
	}

	public void initialize() {

		String rowDef = "4px,p:grow,4px";
		String colDef = "4px,pref:grow,4px,pref:grow,4px";

		FormLayout layout = new FormLayout(colDef, rowDef);
		this.setLayout(layout);

		CellConstraints cons = new CellConstraints();

		this.add(this.getAktiva(), cons.xywh(2, 2, 1, 1, "fill, fill"));
		this.add(this.getPassiva(), cons.xywh(4, 2, 1, 1, "fill, fill"));
	}

	public JPanel getAktiva() {

		paktiva = new JPanel();

		String rowDef = "4px,p,4px,p,4px,p,4px,p,4px,p,4px,p,4px,p,4px,p,4px";
		String colDef = "4px,right:pref,4px,60px:grow,4px,pref,24px,pref,4px,max(35px;pref):grow,4px,pref,4px,pref,4px,max(35px;pref):grow,4px,pref,4px";

		FormLayout layout = new FormLayout(colDef, rowDef);
		paktiva.setLayout(layout);

		CellConstraints cons = new CellConstraints();
		layout.setColumnGroups(new int[][] { { 4, 10, 16 } });

		paktiva.add(this.getLIVG(), cons.xywh(2, 4, 1, 1));
		paktiva.add(this.getLSA(), cons.xywh(2, 6, 1, 1));
		paktiva.add(this.getLFA(), cons.xywh(2, 8, 1, 1));
		paktiva.add(this.getLVOR(), cons.xywh(2, 10, 1, 1));
		paktiva.add(this.getLFSVG(), cons.xywh(2, 12, 1, 1));
		paktiva.add(this.getLWP(), cons.xywh(2, 14, 1, 1));
		paktiva.add(this.getLKBGGKS(), cons.xywh(2, 16, 1, 1));

		paktiva.add(this.getTfIVG(), cons.xywh(4, 4, 1, 1));
		paktiva.add(this.getTfSA(), cons.xywh(4, 6, 1, 1));
		paktiva.add(this.getTfFA(), cons.xywh(4, 8, 1, 1));
		paktiva.add(this.getTfVOR(), cons.xywh(4, 10, 1, 1));
		paktiva.add(this.getTfFSVG(), cons.xywh(4, 12, 1, 1));
		paktiva.add(this.getTfWP(), cons.xywh(4, 14, 1, 1));
		paktiva.add(this.getTfKBGGKS(), cons.xywh(4, 16, 1, 1));

		paktiva.add(new JSeparator(SwingConstants.VERTICAL), cons.xywh(8, 2, 1,
				15));
		paktiva.add(this.getLminakt(), cons.xywh(10, 2, 1, 1));
		paktiva.add(new JSeparator(SwingConstants.VERTICAL), cons.xywh(14, 2,
				1, 15));
		paktiva.add(this.getLmaxakt(), cons.xywh(16, 2, 1, 1));

		paktiva.add(this.getTfIVGmin(), cons.xywh(10, 4, 1, 1));
		paktiva.add(this.getTfSAmin(), cons.xywh(10, 6, 1, 1));
		paktiva.add(this.getTfFAmin(), cons.xywh(10, 8, 1, 1));
		paktiva.add(this.getTfVORmin(), cons.xywh(10, 10, 1, 1));
		paktiva.add(this.getTfFSVGmin(), cons.xywh(10, 12, 1, 1));
		paktiva.add(this.getTfWPmin(), cons.xywh(10, 14, 1, 1));
		paktiva.add(this.getTfKBGGKSmin(), cons.xywh(10, 16, 1, 1));

		paktiva.add(this.getTfIVGmax(), cons.xywh(16, 4, 1, 1));
		paktiva.add(this.getTfSAmax(), cons.xywh(16, 6, 1, 1));
		paktiva.add(this.getTfFAmax(), cons.xywh(16, 8, 1, 1));
		paktiva.add(this.getTfVORmax(), cons.xywh(16, 10, 1, 1));
		paktiva.add(this.getTfFSVGmax(), cons.xywh(16, 12, 1, 1));
		paktiva.add(this.getTfWPmax(), cons.xywh(16, 14, 1, 1));
		paktiva.add(this.getTfKBGGKSmax(), cons.xywh(16, 16, 1, 1));

		paktiva.setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED), translator
				.translate("AKTIVA"), TitledBorder.CENTER,
				TitledBorder.DEFAULT_JUSTIFICATION));

		return paktiva;
	}

	public JPanel getPassiva() {

		ppassiva = new JPanel();

		String rowDef = "4px,p,4px,p,4px,p,4px,p,4px,p,4px,p,4px,p,4px,p,4px";
		String colDef = "4px,right:pref,4px,60px:grow,4px,pref,24px,pref,4px,max(35px;pref):grow,4px,pref,4px,pref,4px,max(35px;pref):grow,4px,pref,4px";

		FormLayout layout = new FormLayout(colDef, rowDef);
		ppassiva.setLayout(layout);

		CellConstraints cons = new CellConstraints();
		layout.setColumnGroups(new int[][] { { 4, 10, 16 } });

		ppassiva.add(this.getLEK(), cons.xywh(2, 4, 1, 1));
		ppassiva.add(this.getLRS(), cons.xywh(2, 6, 1, 1));
		ppassiva.add(this.getLVB(), cons.xywh(2, 8, 1, 1));

		ppassiva.add(this.getTfEK(), cons.xywh(4, 4, 1, 1));
		ppassiva.add(this.getTfRS(), cons.xywh(4, 6, 1, 1));
		ppassiva.add(this.getTfVB(), cons.xywh(4, 8, 1, 1));

		ppassiva.add(new JSeparator(SwingConstants.VERTICAL), cons.xywh(8, 2,
				1, 15));
		ppassiva.add(this.getLminpas(), cons.xywh(10, 2, 1, 1));
		ppassiva.add(new JSeparator(SwingConstants.VERTICAL), cons.xywh(14, 2,
				1, 15));
		ppassiva.add(this.getLmaxpas(), cons.xywh(16, 2, 1, 1));

		ppassiva.add(this.getTfEKmin(), cons.xywh(10, 4, 1, 1));
		ppassiva.add(this.getTfRSmin(), cons.xywh(10, 6, 1, 1));
		ppassiva.add(this.getTfVBmin(), cons.xywh(10, 8, 1, 1));

		ppassiva.add(this.getTfEKmax(), cons.xywh(16, 4, 1, 1));
		ppassiva.add(this.getTfRSmax(), cons.xywh(16, 6, 1, 1));
		ppassiva.add(this.getTfVBmax(), cons.xywh(16, 8, 1, 1));

		ppassiva.setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED), translator
				.translate("PASSIVA"), TitledBorder.CENTER,
				TitledBorder.DEFAULT_JUSTIFICATION));

		return ppassiva;
	}

	public BHLabel getLIVG() {
		if (lIVG == null) {
			lIVG = new BHLabel(DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.IVG, "");
		}
		return lIVG;
	}

	public BHLabel getLSA() {
		if (lSA == null) {
			lSA = new BHLabel(DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.SA, "");
		}
		return lSA;
	}

	public BHLabel getLFA() {
		if (lFA == null) {
			lFA = new BHLabel(DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.FA, "");
		}
		return lFA;
	}

	public BHLabel getLVOR() {
		if (lVOR == null) {
			lVOR = new BHLabel(DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.VOR, "");
		}
		return lVOR;
	}

	public BHLabel getLFSVG() {
		if (lFSVG == null) {
			lFSVG = new BHLabel(DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.FSVG, "");
		}
		return lFSVG;
	}

	public BHLabel getLWP() {
		if (lWP == null) {
			lWP = new BHLabel(DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.WP, "");
		}
		return lWP;
	}

	public BHLabel getLKBGGKS() {
		if (lKBGGKS == null) {
			lKBGGKS = new BHLabel(DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.KBGGKS, "");
		}
		return lKBGGKS;
	}

	public BHLabel getLEK() {
		if (lEK == null) {
			lEK = new BHLabel(DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.EK, "");
		}
		return lEK;
	}

	public BHLabel getLRS() {
		if (lRS == null) {
			lRS = new BHLabel(DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.RS, "");
		}
		return lRS;
	}

	public BHLabel getLVB() {
		if (lVB == null) {
			lVB = new BHLabel(DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.VB, "");
		}
		return lVB;
	}

	
	// Here do the getters for the textfields begin
	
	public BHTextField getTfIVG() {
		if (tfIVG == null) {
			tfIVG = new BHTextField(DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.IVG);
			// TODO add more rules, add InputHints
			int[] rules = { ValidationMethods.isMandatory };
			tfIVG.setValidateRules(rules);
		}
		return tfIVG;
	}

	public BHTextField getTfSA() {
		if (tfSA == null) {
			tfSA = new BHTextField(DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.SA);
			// TODO add more rules, add InputHints
			int[] rules = { ValidationMethods.isMandatory };
			tfSA.setValidateRules(rules);
		}
		return tfSA;
	}

	public BHTextField getTfFA() {
		if (tfFA == null) {
			tfFA = new BHTextField(DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.FA);
			// TODO add more rules, add InputHints
			int[] rules = { ValidationMethods.isMandatory };
			tfFA.setValidateRules(rules);
		}
		return tfFA;
	}

	public BHTextField getTfVOR() {
		if (tfVOR == null) {
			tfVOR = new BHTextField(DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.VOR);
			// TODO add more rules, add InputHints
			int[] rules = { ValidationMethods.isMandatory };
			tfVOR.setValidateRules(rules);
		}
		return tfVOR;
	}

	public BHTextField getTfFSVG() {
		if (tfFSVG == null) {
			tfFSVG = new BHTextField(DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.FSVG);
			// TODO add more rules, add InputHints
			int[] rules = { ValidationMethods.isMandatory };
			tfFSVG.setValidateRules(rules);
		}
		return tfFSVG;
	}

	public BHTextField getTfWP() {
		if (tfWP == null) {
			tfWP = new BHTextField(DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.WP);
			// TODO add more rules, add InputHints
			int[] rules = { ValidationMethods.isMandatory };
			tfWP.setValidateRules(rules);
		}
		return tfWP;
	}

	public BHTextField getTfKBGGKS() {
		if (tfKBGGKS == null) {
			tfKBGGKS = new BHTextField(DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.KBGGKS);
			// TODO add more rules, add InputHints
			int[] rules = { ValidationMethods.isMandatory };
			tfKBGGKS.setValidateRules(rules);
		}
		return tfKBGGKS;
	}

	public BHTextField getTfEK() {
		if (tfEK == null) {
			tfEK = new BHTextField(DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.EK);
			// TODO add more rules, add InputHints
			int[] rules = { ValidationMethods.isMandatory };
			tfEK.setValidateRules(rules);
		}
		return tfEK;
	}

	public BHTextField getTfRS() {
		if (tfRS == null) {
			tfRS = new BHTextField(DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.RS);
			// TODO add more rules, add InputHints
			int[] rules = { ValidationMethods.isMandatory };
			tfRS.setValidateRules(rules);
		}
		return tfRS;
	}

	public BHTextField getTfVB() {
		if (tfVB == null) {
			tfVB = new BHTextField(DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.VB);
			// TODO add more rules, add InputHints
			int[] rules = { ValidationMethods.isMandatory };
			tfVB.setValidateRules(rules);
		}
		return tfVB;
	}

	// Maximum TextFields
	
	public BHTextField getTfIVGmax() {
		if (tfIVGmax == null) {
			tfIVGmax = new BHTextField(IBHComponent.MAXVALUE
					+ DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.IVG);
			// TODO add more rules, add InputHints
			int[] rules = { ValidationMethods.isMandatory };
			tfIVGmax.setValidateRules(rules);
		}
		return tfIVGmax;
	}

	public BHTextField getTfSAmax() {
		if (tfSAmax == null) {
			tfSAmax = new BHTextField(IBHComponent.MAXVALUE
					+ DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.SA);
			// TODO add more rules, add InputHints
			int[] rules = { ValidationMethods.isMandatory };
			tfSAmax.setValidateRules(rules);
		}
		return tfSAmax;
	}

	public BHTextField getTfFAmax() {
		if (tfFAmax == null) {
			tfFAmax = new BHTextField(IBHComponent.MAXVALUE
					+ DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.FA);
			// TODO add more rules, add InputHints
			int[] rules = { ValidationMethods.isMandatory };
			tfFAmax.setValidateRules(rules);
		}
		return tfFAmax;
	}

	public BHTextField getTfVORmax() {
		if (tfVORmax == null) {
			tfVORmax = new BHTextField(IBHComponent.MAXVALUE
					+ DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.VOR);
			// TODO add more rules, add InputHints
			int[] rules = { ValidationMethods.isMandatory };
			tfVORmax.setValidateRules(rules);
		}
		return tfVORmax;
	}

	public BHTextField getTfFSVGmax() {
		if (tfFSVGmax == null) {
			tfFSVGmax = new BHTextField(IBHComponent.MAXVALUE
					+ DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.FSVG);
			// TODO add more rules, add InputHints
			int[] rules = { ValidationMethods.isMandatory };
			tfFSVGmax.setValidateRules(rules);
		}
		return tfFSVGmax;
	}

	public BHTextField getTfWPmax() {
		if (tfWPmax == null) {
			tfWPmax = new BHTextField(IBHComponent.MAXVALUE
					+ DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.WP);
			// TODO add more rules, add InputHints
			int[] rules = { ValidationMethods.isMandatory };
			tfWPmax.setValidateRules(rules);
		}
		return tfWPmax;
	}

	public BHTextField getTfKBGGKSmax() {
		if (tfKBGGKSmax == null) {
			tfKBGGKSmax = new BHTextField(IBHComponent.MAXVALUE
					+ DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.KBGGKS);
			// TODO add more rules, add InputHints
			int[] rules = { ValidationMethods.isMandatory };
			tfKBGGKSmax.setValidateRules(rules);
		}
		return tfKBGGKSmax;
	}

	public BHTextField getTfEKmax() {
		if (tfEKmax == null) {
			tfEKmax = new BHTextField(IBHComponent.MAXVALUE
					+ DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.EK);
			// TODO add more rules, add InputHints
			int[] rules = { ValidationMethods.isMandatory };
			tfEKmax.setValidateRules(rules);
		}
		return tfEKmax;
	}

	public BHTextField getTfRSmax() {
		if (tfRSmax == null) {
			tfRSmax = new BHTextField(IBHComponent.MAXVALUE
					+ DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.RS);
			// TODO add more rules, add InputHints
			int[] rules = { ValidationMethods.isMandatory };
			tfRSmax.setValidateRules(rules);
		}
		return tfRSmax;
	}

	public BHTextField getTfVBmax() {
		if (tfVBmax == null) {
			tfVBmax = new BHTextField(IBHComponent.MAXVALUE
					+ DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.VB);
			// TODO add more rules, add InputHints
			int[] rules = { ValidationMethods.isMandatory };
			tfVBmax.setValidateRules(rules);
		}
		return tfVBmax;
	}
	
	// Minimum TextFields
	
	public BHTextField getTfIVGmin() {
		if (tfIVGmin == null) {
			tfIVGmin = new BHTextField(IBHComponent.MINVALUE
					+ DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.IVG);
			// TODO add more rules, add InputHints
			int[] rules = { ValidationMethods.isMandatory };
			tfIVGmin.setValidateRules(rules);
		}
		return tfIVGmin;
	}

	public BHTextField getTfSAmin() {
		if (tfSAmin == null) {
			tfSAmin = new BHTextField(IBHComponent.MINVALUE
					+ DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.SA);
			// TODO add more rules, add InputHints
			int[] rules = { ValidationMethods.isMandatory };
			tfSAmin.setValidateRules(rules);
		}
		return tfSAmin;
	}

	public BHTextField getTfFAmin() {
		if (tfFAmin == null) {
			tfFAmin = new BHTextField(IBHComponent.MINVALUE
					+ DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.FA);
			// TODO add more rules, add InputHints
			int[] rules = { ValidationMethods.isMandatory };
			tfFAmin.setValidateRules(rules);
		}
		return tfFAmin;
	}

	public BHTextField getTfVORmin() {
		if (tfVORmin == null) {
			tfVORmin = new BHTextField(IBHComponent.MINVALUE
					+ DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.VOR);
			// TODO add more rules, add InputHints
			int[] rules = { ValidationMethods.isMandatory };
			tfVORmin.setValidateRules(rules);
		}
		return tfVORmin;
	}

	public BHTextField getTfFSVGmin() {
		if (tfFSVGmin == null) {
			tfFSVGmin = new BHTextField(IBHComponent.MINVALUE
					+ DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.FSVG);
			// TODO add more rules, add InputHints
			int[] rules = { ValidationMethods.isMandatory };
			tfFSVGmin.setValidateRules(rules);
		}
		return tfFSVGmin;
	}

	public BHTextField getTfWPmin() {
		if (tfWPmin == null) {
			tfWPmin = new BHTextField(IBHComponent.MINVALUE
					+ DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.WP);
			// TODO add more rules, add InputHints
			int[] rules = { ValidationMethods.isMandatory };
			tfWPmin.setValidateRules(rules);
		}
		return tfWPmin;
	}

	public BHTextField getTfKBGGKSmin() {
		if (tfKBGGKSmin == null) {
			tfKBGGKSmin = new BHTextField(IBHComponent.MINVALUE
					+ DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.KBGGKS);
			// TODO add more rules, add InputHints
			int[] rules = { ValidationMethods.isMandatory };
			tfKBGGKSmin.setValidateRules(rules);
		}
		return tfKBGGKSmin;
	}

	public BHTextField getTfEKmin() {
		if (tfEKmin == null) {
			tfEKmin = new BHTextField(IBHComponent.MINVALUE
					+ DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.EK);
			// TODO add more rules, add InputHints
			int[] rules = { ValidationMethods.isMandatory };
			tfEKmin.setValidateRules(rules);
		}
		return tfEKmin;
	}

	public BHTextField getTfRSmin() {
		if (tfRSmin == null) {
			tfRSmin = new BHTextField(IBHComponent.MINVALUE
					+ DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.RS);
			// TODO add more rules, add InputHints
			int[] rules = { ValidationMethods.isMandatory };
			tfRSmin.setValidateRules(rules);
		}
		return tfRSmin;
	}

	public BHTextField getTfVBmin() {
		if (tfVBmin == null) {
			tfVBmin = new BHTextField(IBHComponent.MINVALUE
					+ DTOGCCBalanceSheet.class.getName()+"_"
					+ DTOGCCBalanceSheet.Key.VB);
			// TODO add more rules, add InputHints
			int[] rules = { ValidationMethods.isMandatory };
			tfVBmin.setValidateRules(rules);
		}
		return tfVBmin;
	}

	
	public BHLabel getLmaxakt() {
		if (lmaxakt == null) {
			lmaxakt = new BHLabel("", "Max");
		}
		return lmaxakt;
	}

	public BHLabel getLminakt() {
		if (lminakt == null) {
			lminakt = new BHLabel("", "Min");
		}
		return lminakt;
	}

	public BHLabel getLmaxpas() {
		if (lmaxpas == null) {
			lmaxpas = new BHLabel("", "Max");
		}
		return lmaxpas;
	}

	public BHLabel getLminpas() {
		if (lminpas == null) {
			lminpas = new BHLabel("", "Min");
		}
		return lminpas;
	}

	
	// TODO remove main later
	/**
	 * Test main method.
	 */
	@SuppressWarnings("deprecation")
	public static void main(String args[]) {

		JFrame test = new JFrame("Test for ViewPeriodData1");
		test.setContentPane(new BHBalanceSheetForm());
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
