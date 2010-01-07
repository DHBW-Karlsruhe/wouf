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

import org.bh.gui.swing.BHDescriptionLabel;
import org.bh.gui.swing.BHTextField;
import org.bh.gui.swing.IBHComponent;
import org.bh.platform.i18n.BHTranslator;
import org.bh.plugin.gcc.data.DTOGCCProfitLossStatementCostOfSales;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class BHPLSCostOfSalesForm extends JPanel {

    private BHTextField tfUE;
    private BHTextField tfHK;
    private BHTextField tfVVSBA;
    private BHTextField tfAUERG;

    private BHTextField tfUEmax;
    private BHTextField tfHKmax;
    private BHTextField tfVVSBAmax;
    private BHTextField tfAUERGmax;

    private BHTextField tfUEmin;
    private BHTextField tfHKmin;
    private BHTextField tfVVSBAmin;
    private BHTextField tfAUERGmin;

    private BHDescriptionLabel lUE;
    private BHDescriptionLabel lHK;
    private BHDescriptionLabel lVVSBA;
    private BHDescriptionLabel lAUERG;

    private JLabel lmin;
    private JLabel lmax;

    final BHTranslator translator = BHTranslator.getInstance();

    public enum Key {
	/**
	 * 
	 */
	PLS_CostOfSales;

	public String toString() {
	    return getClass().getName() + "." + super.toString();
	}

    }

    public BHPLSCostOfSalesForm() {

	this.initialize();
    }

    public void initialize() {

	String rowDef = "4px,p,4px,p,4px,p,4px,p,4px,p,4px";
	String colDef = "4px,right:pref,4px,60px:grow,4px,pref,24px:grow,pref,4px,max(35px;pref):grow,4px,pref,4px:grow,pref,4px,max(35px;pref):grow,4px,pref,4px:grow";

	FormLayout layout = new FormLayout(colDef, rowDef);
	this.setLayout(layout);

	CellConstraints cons = new CellConstraints();
	layout.setColumnGroups(new int[][] { { 4, 10, 16 } });

	this.add(this.getLUE(), cons.xywh(2, 4, 1, 1));
	this.add(this.getLHK(), cons.xywh(2, 6, 1, 1));
	this.add(this.getLVVSBA(), cons.xywh(2, 8, 1, 1));
	this.add(this.getLAUERG(), cons.xywh(2, 10, 1, 1));

	this.add(this.getTfUE(), cons.xywh(4, 4, 1, 1));
	this.add(this.getTfHK(), cons.xywh(4, 6, 1, 1));
	this.add(this.getTfVVSBA(), cons.xywh(4, 8, 1, 1));
	this.add(this.getTfAUERG(), cons.xywh(4, 10, 1, 1));

	this
		.add(new JSeparator(SwingConstants.VERTICAL), cons.xywh(8, 2,
			1, 6));
	this.add(this.getLmin(), cons.xywh(10, 2, 1, 1));
	this.add(new JSeparator(SwingConstants.VERTICAL), cons
		.xywh(14, 2, 1, 6));
	this.add(this.getLmax(), cons.xywh(16, 2, 1, 1));

	this.add(this.getTfUEmin(), cons.xywh(10, 4, 1, 1));
	this.add(this.getTfHKmin(), cons.xywh(10, 6, 1, 1));
	this.add(this.getTfVVSBAmin(), cons.xywh(10, 8, 1, 1));
	this.add(this.getTfAUERGmin(), cons.xywh(10, 10, 1, 1));

	this.add(this.getTfUEmax(), cons.xywh(16, 4, 1, 1));
	this.add(this.getTfHKmax(), cons.xywh(16, 6, 1, 1));
	this.add(this.getTfVVSBAmax(), cons.xywh(16, 8, 1, 1));
	this.add(this.getTfAUERGmax(), cons.xywh(16, 10, 1, 1));

	this.setBorder(BorderFactory.createTitledBorder(BorderFactory
		.createEtchedBorder(EtchedBorder.LOWERED), translator
		.translate(BHPLSCostOfSalesForm.Key.PLS_CostOfSales))); // "GuV nach Umsatzkostenverfahren"

    }

    public BHTextField getTfUE() {
	if (tfUE == null) {
	    tfUE = new BHTextField(DTOGCCProfitLossStatementCostOfSales.Key.UE);
	}
	return tfUE;
    }

    public BHTextField getTfHK() {
	if (tfHK == null) {
	    tfHK = new BHTextField(DTOGCCProfitLossStatementCostOfSales.Key.HK);
	}
	return tfHK;
    }

    public BHTextField getTfVVSBA() {
	if (tfVVSBA == null) {
	    tfVVSBA = new BHTextField(
		    DTOGCCProfitLossStatementCostOfSales.Key.VVSBA);
	}
	return tfVVSBA;
    }

    public BHTextField getTfAUERG() {
	if (tfAUERG == null) {
	    tfAUERG = new BHTextField(
		    DTOGCCProfitLossStatementCostOfSales.Key.AUERG);
	}
	return tfAUERG;
    }

    public BHTextField getTfUEmax() {
	if (tfUEmax == null) {
	    tfUEmax = new BHTextField(IBHComponent.MAXVALUE + "_"
		    + DTOGCCProfitLossStatementCostOfSales.Key.UE);
	}
	return tfUEmax;
    }

    public BHTextField getTfHKmax() {
	if (tfHKmax == null) {
	    tfHKmax = new BHTextField(IBHComponent.MAXVALUE + "_"
		    + DTOGCCProfitLossStatementCostOfSales.Key.HK);
	}
	return tfHKmax;
    }

    public BHTextField getTfVVSBAmax() {
	if (tfVVSBAmax == null) {
	    tfVVSBAmax = new BHTextField(IBHComponent.MAXVALUE + "_"
		    + DTOGCCProfitLossStatementCostOfSales.Key.VVSBA);
	}
	return tfVVSBAmax;
    }

    public BHTextField getTfAUERGmax() {
	if (tfAUERGmax == null) {
	    tfAUERGmax = new BHTextField(IBHComponent.MAXVALUE + "_"
		    + DTOGCCProfitLossStatementCostOfSales.Key.AUERG);
	}
	return tfAUERGmax;
    }

    public BHTextField getTfUEmin() {
	if (tfUEmin == null) {
	    tfUEmin = new BHTextField(IBHComponent.MINVALUE + "_"
		    + DTOGCCProfitLossStatementCostOfSales.Key.UE);
	}
	return tfUEmin;
    }

    public BHTextField getTfHKmin() {
	if (tfHKmin == null) {
	    tfHKmin = new BHTextField(IBHComponent.MINVALUE + "_"
		    + DTOGCCProfitLossStatementCostOfSales.Key.HK);
	}
	return tfHKmin;
    }

    public BHTextField getTfVVSBAmin() {
	if (tfVVSBAmin == null) {
	    tfVVSBAmin = new BHTextField(IBHComponent.MINVALUE + "_"
		    + DTOGCCProfitLossStatementCostOfSales.Key.VVSBA);
	}
	return tfVVSBAmin;
    }

    public BHTextField getTfAUERGmin() {
	if (tfAUERGmin == null) {
	    tfAUERGmin = new BHTextField(IBHComponent.MINVALUE + "_"
		    + DTOGCCProfitLossStatementCostOfSales.Key.AUERG);
	}
	return tfAUERGmin;
    }

    public BHDescriptionLabel getLUE() {
	if (lUE == null) {
	    lUE = new BHDescriptionLabel(DTOGCCProfitLossStatementCostOfSales.Key.UE);
	}
	return lUE;
    }

    public BHDescriptionLabel getLHK() {
	if (lHK == null) {
	    lHK = new BHDescriptionLabel(DTOGCCProfitLossStatementCostOfSales.Key.HK);
	}
	return lHK;
    }

    public BHDescriptionLabel getLVVSBA() {
	if (lVVSBA == null) {
	    lVVSBA = new BHDescriptionLabel(DTOGCCProfitLossStatementCostOfSales.Key.VVSBA);
	}
	return lVVSBA;
    }

    public BHDescriptionLabel getLAUERG() {
	if (lAUERG == null) {
	    lAUERG = new BHDescriptionLabel(DTOGCCProfitLossStatementCostOfSales.Key.AUERG);
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
	test.setContentPane(new BHPLSCostOfSalesForm());
	test.addWindowListener(new WindowAdapter() {
	    public void windowClosing(WindowEvent e) {
		System.exit(0);
	    }
	});
	test.pack();
	test.show();
    }
}
