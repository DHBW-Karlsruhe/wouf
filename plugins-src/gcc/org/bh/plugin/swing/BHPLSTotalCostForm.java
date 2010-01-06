package org.bh.plugin.swing;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import org.bh.gui.swing.BHLabel;
import org.bh.gui.swing.BHTextField;
import org.bh.gui.swing.IBHComponent;
import org.bh.platform.i18n.BHTranslator;
import org.bh.plugin.gcc.data.DTOGCCProfitLossStatementTotalCost;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

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

    private BHLabel lUE;
    private BHLabel lABSCH;
    private BHLabel lSBA;
    private BHLabel lAUERG;

    private JLabel lmin;
    private JLabel lmax;

    final BHTranslator translator = BHTranslator.getInstance();

    public enum Key {
	/**
	 * 
	 */
	PLS_TotalCost;

	public String toString() {
	    return getClass().getName() + "." + super.toString();
	}

    }

    public BHPLSTotalCostForm() {
	this.initialize();
    }

    public void initialize() {
	String rowDef = "4px,p,4px,p,4px,p,4px,p,4px,p,4px";
	String colDef = "4px,right:pref,4px,60px:grow,4px,pref,24px:grow,pref,4px,max(35px;pref):grow,4px,pref,4px:grow,pref,4px,max(35px;pref):grow,4px,pref,4px:grow";

	FormLayout layout = new FormLayout(colDef, rowDef);
	this.setLayout(layout);

	CellConstraints cons = new CellConstraints();
	layout.setColumnGroups(new int[][] { { 4, 10, 16 } });

	this.add(this.getTfUE(), cons.xywh(4, 4, 1, 1));
	this.add(this.getTfABSCH(), cons.xywh(4, 6, 1, 1));
	this.add(this.getTfSBA(), cons.xywh(4, 8, 1, 1));
	this.add(this.getTfAUERG(), cons.xywh(4, 10, 1, 1));

	this.add(this.getLUE(), cons.xywh(2, 4, 1, 1));
	this.add(this.getLABSCH(), cons.xywh(2, 6, 1, 1));
	this.add(this.getLSBA(), cons.xywh(2, 8, 1, 1));
	this.add(this.getLAUERG(), cons.xywh(2, 10, 1, 1));

	this
		.add(new JSeparator(SwingConstants.VERTICAL), cons.xywh(8, 2,
			1, 6));
	this.add(this.getLmin(), cons.xywh(10, 2, 1, 1));
	this.add(new JSeparator(SwingConstants.VERTICAL), cons
		.xywh(14, 2, 1, 6));
	this.add(this.getLmax(), cons.xywh(16, 2, 1, 1));

	this.add(this.getTfUEmin(), cons.xywh(10, 4, 1, 1));
	this.add(this.getTfABSCHmin(), cons.xywh(10, 6, 1, 1));
	this.add(this.getTfSBAmin(), cons.xywh(10, 8, 1, 1));
	this.add(this.getTfAUERGmin(), cons.xywh(10, 10, 1, 1));

	this.add(this.getTfUEmax(), cons.xywh(16, 4, 1, 1));
	this.add(this.getTfABSCHmax(), cons.xywh(16, 6, 1, 1));
	this.add(this.getTfSBAmax(), cons.xywh(16, 8, 1, 1));
	this.add(this.getTfAUERGmax(), cons.xywh(16, 10, 1, 1));

	this.setBorder(BorderFactory.createTitledBorder(BorderFactory
		.createEtchedBorder(EtchedBorder.LOWERED), translator
		.translate(BHPLSTotalCostForm.Key.PLS_TotalCost))); // "GuV nach Gesamtkostenverfahren"
    }

    public BHTextField getTfUE() {
	if (tfUE == null) {
	    tfUE = new BHTextField(DTOGCCProfitLossStatementTotalCost.Key.UE);
	}
	return tfUE;
    }

    public BHTextField getTfABSCH() {
	if (tfABSCH == null) {
	    tfABSCH = new BHTextField(
		    DTOGCCProfitLossStatementTotalCost.Key.ABSCH);
	}
	return tfABSCH;
    }

    public BHTextField getTfSBA() {
	if (tfSBA == null) {
	    tfSBA = new BHTextField(DTOGCCProfitLossStatementTotalCost.Key.SBA);
	}
	return tfSBA;
    }

    public BHTextField getTfAUERG() {
	if (tfAUERG == null) {
	    tfAUERG = new BHTextField(
		    DTOGCCProfitLossStatementTotalCost.Key.AUERG);
	}
	return tfAUERG;
    }

    public BHTextField getTfUEmax() {
	if (tfUEmax == null) {
	    tfUEmax = new BHTextField(IBHComponent.MAXVALUE + "_"
		    + DTOGCCProfitLossStatementTotalCost.Key.UE);
	}
	return tfUEmax;
    }

    public BHTextField getTfABSCHmax() {
	if (tfABSCHmax == null) {
	    tfABSCHmax = new BHTextField(IBHComponent.MAXVALUE + "_"
		    + DTOGCCProfitLossStatementTotalCost.Key.ABSCH);
	}
	return tfABSCHmax;
    }

    public BHTextField getTfSBAmax() {
	if (tfSBAmax == null) {
	    tfSBAmax = new BHTextField(IBHComponent.MAXVALUE + "_"
		    + DTOGCCProfitLossStatementTotalCost.Key.SBA);
	}
	return tfSBAmax;
    }

    public BHTextField getTfAUERGmax() {
	if (tfAUERGmax == null) {
	    tfAUERGmax = new BHTextField(IBHComponent.MAXVALUE + "_"
		    + DTOGCCProfitLossStatementTotalCost.Key.AUERG);
	}
	return tfAUERGmax;
    }

    public BHTextField getTfUEmin() {
	if (tfUEmin == null) {
	    tfUEmin = new BHTextField(IBHComponent.MINVALUE + "_"
		    + DTOGCCProfitLossStatementTotalCost.Key.UE);
	}
	return tfUEmin;
    }

    public BHTextField getTfABSCHmin() {
	if (tfABSCHmin == null) {
	    tfABSCHmin = new BHTextField(IBHComponent.MINVALUE + "_"
		    + DTOGCCProfitLossStatementTotalCost.Key.ABSCH);
	}
	return tfABSCHmin;
    }

    public BHTextField getTfSBAmin() {
	if (tfSBAmin == null) {
	    tfSBAmin = new BHTextField(IBHComponent.MINVALUE + "_"
		    + DTOGCCProfitLossStatementTotalCost.Key.SBA);
	}
	return tfSBAmin;
    }

    public BHTextField getTfAUERGmin() {
	if (tfAUERGmin == null) {
	    tfAUERGmin = new BHTextField(IBHComponent.MINVALUE + "_"
		    + DTOGCCProfitLossStatementTotalCost.Key.AUERG);
	}
	return tfAUERGmin;
    }

    public BHLabel getLUE() {
	if (lUE == null) {
	    lUE = new BHLabel(DTOGCCProfitLossStatementTotalCost.Key.UE);
	}
	return lUE;
    }

    public BHLabel getLABSCH() {
	if (lABSCH == null) {
	    lABSCH = new BHLabel(DTOGCCProfitLossStatementTotalCost.Key.ABSCH);
	}
	return lABSCH;
    }

    public BHLabel getLSBA() {
	if (lSBA == null) {
	    lSBA = new BHLabel(DTOGCCProfitLossStatementTotalCost.Key.SBA);
	}
	return lSBA;
    }

    public BHLabel getLAUERG() {
	if (lAUERG == null) {
	    lAUERG = new BHLabel(DTOGCCProfitLossStatementTotalCost.Key.AUERG);
	}
	return lAUERG;
    }

    public JLabel getLmax() {
	if (lmax == null) {
	    lmax = new JLabel(translator.translate("MAX"));
	}
	return lmax;
    }

    public JLabel getLmin() {
	if (lmin == null) {
	    lmin = new JLabel(translator.translate("MIN"));
	}
	return lmin;
    }

    /**
     * Test main method.
     */
    public static void main(String args[]) {

	JFrame test = new JFrame("Test for ViewPeriodData1");
	test.setContentPane(new BHPLSTotalCostForm());
	test.addWindowListener(new WindowAdapter() {
	    public void windowClosing(WindowEvent e) {
		System.exit(0);
	    }
	});
	test.pack();
	test.show();
    }
}
