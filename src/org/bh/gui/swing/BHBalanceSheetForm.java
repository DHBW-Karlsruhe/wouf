package org.bh.gui.swing;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.bh.gui.swing.BHLabel;
import org.bh.gui.swing.BHTextField;
import org.bh.platform.i18n.BHTranslator;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class BHBalanceSheetForm extends JPanel {

    private JPanel paktiva;
    private JPanel ppassiva;

    private BHLabel lIMVG;
    private BHTextField tfIMVG;
    private BHLabel lSHAN;
    private BHTextField tfSHAN;
    private BHLabel lFIAN;
    private BHTextField tfFIAN;
    private BHLabel lVORR;
    private BHTextField tfVORR;
    private BHLabel lFORD;
    private BHTextField tfFORD;
    private BHLabel lWRTP;
    private BHTextField tfWRTP;
    private BHLabel lKASS;
    private BHTextField tfKASS;
    private BHLabel lEK;
    private BHTextField tfEK;
    private BHLabel lRCKS;
    private BHTextField tfRCKS;
    private BHLabel lVRBD;
    private BHTextField tfVRBD;

    private BHTextField tfIMVGmax;
    private BHTextField tfSHANmax;
    private BHTextField tfFIANmax;
    private BHTextField tfVORRmax;
    private BHTextField tfFORDmax;
    private BHTextField tfWRTPmax;
    private BHTextField tfKASSmax;
    private BHTextField tfEkmax;
    private BHTextField tfRCKSmax;
    private BHTextField tfVRBDmax;

    private BHTextField tfIMVGmin;
    private BHTextField tfSHANmin;
    private BHTextField tfFIANmin;
    private BHTextField tfVORRmin;
    private BHTextField tfFORDmin;
    private BHTextField tfWRTPmin;
    private BHTextField tfKASSmin;
    private BHTextField tfEKmin;
    private BHTextField tfRCKSmin;
    private BHTextField tfVRBDmin;

    private BHLabel lmaxakt;
    private BHLabel lminakt;
    private BHLabel lmaxpas;
    private BHLabel lminpas;

    private BHTextField tfmaxliabilities;
    private BHTextField tfminliabilities;
    private BHTextField tfmaxfcf;
    private BHTextField tfminfcf;

    final BHTranslator translator = BHTranslator.getInstance();

    /**
     * Constructor.
     */
    public BHBalanceSheetForm() {

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

	paktiva.add(this.getLIMVG(), cons.xywh(2, 4, 1, 1));
	paktiva.add(this.getLSHAN(), cons.xywh(2, 6, 1, 1));
	paktiva.add(this.getLFIAN(), cons.xywh(2, 8, 1, 1));
	paktiva.add(this.getLVORR(), cons.xywh(2, 10, 1, 1));
	paktiva.add(this.getLFORD(), cons.xywh(2, 12, 1, 1));
	paktiva.add(this.getLWRTP(), cons.xywh(2, 14, 1, 1));
	paktiva.add(this.getLKASS(), cons.xywh(2, 16, 1, 1));

	paktiva.add(this.getTfIMVG(), cons.xywh(4, 4, 1, 1));
	paktiva.add(this.getTfSHAN(), cons.xywh(4, 6, 1, 1));
	paktiva.add(this.getTfFIAN(), cons.xywh(4, 8, 1, 1));
	paktiva.add(this.getTfVORR(), cons.xywh(4, 10, 1, 1));
	paktiva.add(this.getTfFORD(), cons.xywh(4, 12, 1, 1));
	paktiva.add(this.getTfWRTP(), cons.xywh(4, 14, 1, 1));
	paktiva.add(this.getTfKASS(), cons.xywh(4, 16, 1, 1));

	paktiva.add(new JSeparator(SwingConstants.VERTICAL), cons.xywh(8, 2, 1,
		15));
	paktiva.add(this.getLminakt(), cons.xywh(10, 2, 1, 1));
	paktiva.add(new JSeparator(SwingConstants.VERTICAL), cons.xywh(14, 2,
		1,15));
	paktiva.add(this.getLmaxakt(), cons.xywh(16, 2, 1, 1));

	paktiva.add(this.getTfIMVGmin(), cons.xywh(10, 4, 1, 1));
	paktiva.add(this.getTfSHANmin(), cons.xywh(10, 6, 1, 1));
	paktiva.add(this.getTfFIANmin(), cons.xywh(10, 8, 1, 1));
	paktiva.add(this.getTfVORRmin(), cons.xywh(10, 10, 1, 1));
	paktiva.add(this.getTfFORDmin(), cons.xywh(10, 12, 1, 1));
	paktiva.add(this.getTfWRTPmin(), cons.xywh(10, 14, 1, 1));
	paktiva.add(this.getTfKASSmin(), cons.xywh(10, 16, 1, 1));

	paktiva.add(this.getTfIMVGmax(), cons.xywh(16, 4, 1, 1));
	paktiva.add(this.getTfSHANmax(), cons.xywh(16, 6, 1, 1));
	paktiva.add(this.getTfFIANmax(), cons.xywh(16, 8, 1, 1));
	paktiva.add(this.getTfVORRmax(), cons.xywh(16, 10, 1, 1));
	paktiva.add(this.getTfFORDmax(), cons.xywh(16, 12, 1, 1));
	paktiva.add(this.getTfWRTPmax(), cons.xywh(16, 14, 1, 1));
	paktiva.add(this.getTfKASSmax(), cons.xywh(16, 16, 1, 1));

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
	ppassiva.add(this.getLRCKS(), cons.xywh(2, 6, 1, 1));
	ppassiva.add(this.getLVRBD(), cons.xywh(2, 8, 1, 1));

	ppassiva.add(this.getTfEK(), cons.xywh(4, 4, 1, 1));
	ppassiva.add(this.getTfRCKS(), cons.xywh(4, 6, 1, 1));
	ppassiva.add(this.getTfVRBD(), cons.xywh(4, 8, 1, 1));
	
	ppassiva.add(new JSeparator(SwingConstants.VERTICAL), cons.xywh(8, 2, 1,
		15));
	ppassiva.add(this.getLminpas(), cons.xywh(10, 2, 1, 1));
	ppassiva.add(new JSeparator(SwingConstants.VERTICAL), cons.xywh(14, 2,
		1, 15));
	ppassiva.add(this.getLmaxpas(), cons.xywh(16, 2, 1, 1));

	ppassiva.add(this.getTfEKmin(), cons.xywh(10, 4, 1, 1));
	ppassiva.add(this.getTfRCKSmin(), cons.xywh(10, 6, 1, 1));
	ppassiva.add(this.getTfVRBDmin(), cons.xywh(10, 8, 1, 1));

	ppassiva.add(this.getTfEkmax(), cons.xywh(16, 4, 1, 1));
	ppassiva.add(this.getTfRCKSmax(), cons.xywh(16, 6, 1, 1));
	ppassiva.add(this.getTfVRBDmax(), cons.xywh(16, 8, 1, 1));

	ppassiva.setBorder(BorderFactory.createTitledBorder(BorderFactory
		.createEtchedBorder(EtchedBorder.LOWERED), translator
		.translate("PASSIVA"), TitledBorder.CENTER,
		TitledBorder.DEFAULT_JUSTIFICATION));

	return ppassiva;
    }

    public BHLabel getLIMVG() {
	if (lIMVG == null) {
	    lIMVG = new BHLabel("IMVG", "Immaterielle Vermögensgegenstände");
	}
	return lIMVG;
    }

    public BHLabel getLSHAN() {
	if (lSHAN == null) {
	    lSHAN = new BHLabel("SHAN", "Sachanlagen");
	}
	return lSHAN;
    }

    public BHLabel getLFIAN() {
	if (lFIAN == null) {
	    lFIAN = new BHLabel("FIAN", "Finanzanlagen");
	}
	return lFIAN;
    }

    public BHLabel getLVORR() {
	if (lVORR == null) {
	    lVORR = new BHLabel("VORR", "Vorräte");
	}
	return lVORR;
    }

    public BHLabel getLFORD() {
	if (lFORD == null) {
	    lFORD = new BHLabel("FORD", "Forderungen, ...");
	}
	return lFORD;
    }

    public BHLabel getLWRTP() {
	if (lWRTP == null) {
	    lWRTP = new BHLabel("WRTP", "Wertpapiere");
	}
	return lWRTP;
    }

    public BHLabel getLKASS() {
	if (lKASS == null) {
	    lKASS = new BHLabel("KASS", "Kassenbestand, ...");
	}
	return lKASS;
    }

    public BHLabel getLEK() {
	if (lEK == null) {
	    lEK = new BHLabel("EK", "Eigenkapital");
	}
	return lEK;
    }

    public BHLabel getLRCKS() {
	if (lRCKS == null) {
	    lRCKS = new BHLabel("RCKS", "Rückstellungen");
	}
	return lRCKS;
    }

    public BHLabel getLVRBD() {
	if (lVRBD == null) {
	    lVRBD = new BHLabel("VRBD", "Verbindlichkeiten");
	}
	return lVRBD;
    }

    public BHTextField getTfIMVG() {
	if (tfIMVG == null) {
	    tfIMVG = new BHTextField("IMVG");
	}
	return tfIMVG;
    }

    public BHTextField getTfSHAN() {
	if (tfSHAN == null) {
	    tfSHAN = new BHTextField("SHAN");
	}
	return tfSHAN;
    }

    public BHTextField getTfFIAN() {
	if (tfFIAN == null) {
	    tfFIAN = new BHTextField("FIAN");
	}
	return tfFIAN;
    }

    public BHTextField getTfVORR() {
	if (tfVORR == null) {
	    tfVORR = new BHTextField("VORR");
	}
	return tfVORR;
    }

    public BHTextField getTfFORD() {
	if (tfFORD == null) {
	    tfFORD = new BHTextField("FORD");
	}
	return tfFORD;
    }

    public BHTextField getTfWRTP() {
	if (tfWRTP == null) {
	    tfWRTP = new BHTextField("WRTP");
	}
	return tfWRTP;
    }

    public BHTextField getTfKASS() {
	if (tfKASS == null) {
	    tfKASS = new BHTextField("KASS");
	}
	return tfKASS;
    }

    public BHTextField getTfEK() {
	if (tfEK == null) {
	    tfEK = new BHTextField("EK");
	}
	return tfEK;
    }

    public BHTextField getTfRCKS() {
	if (tfRCKS == null) {
	    tfRCKS = new BHTextField("RCKS");
	}
	return tfRCKS;
    }

    public BHTextField getTfVRBD() {
	if (tfVRBD == null) {
	    tfVRBD = new BHTextField("VRBD");
	}
	return tfVRBD;
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

    public BHTextField getTfmaxliabilities() {
	if (tfmaxliabilities == null) {
	    tfmaxliabilities = new BHTextField("");
	}
	return tfmaxliabilities;
    }

    public BHTextField getTfminliabilities() {
	if (tfminliabilities == null) {
	    tfminliabilities = new BHTextField("");
	}
	return tfminliabilities;
    }

    public BHTextField getTfmaxfcf() {
	if (tfmaxfcf == null) {
	    tfmaxfcf = new BHTextField("");
	}
	return tfmaxfcf;
    }

    public BHTextField getTfminfcf() {
	if (tfminfcf == null) {
	    tfminfcf = new BHTextField("");
	}
	return tfminfcf;
    }

    public BHTextField getTfIMVGmax() {
	if (tfIMVGmax == null) {
	    tfIMVGmax = new BHTextField("IMVGmax");
	}
	return tfIMVGmax;
    }

    public BHTextField getTfSHANmax() {
	if (tfSHANmax == null) {
	    tfSHANmax = new BHTextField("SHANmax");
	}
	return tfSHANmax;
    }

    public BHTextField getTfFIANmax() {
	if (tfFIANmax == null) {
	    tfFIANmax = new BHTextField("FIANmax");
	}
	return tfFIANmax;
    }

    public BHTextField getTfVORRmax() {
	if (tfVORRmax == null) {
	    tfVORRmax = new BHTextField("VORRmax");
	}
	return tfVORRmax;
    }

    public BHTextField getTfFORDmax() {
	if (tfFORDmax == null) {
	    tfFORDmax = new BHTextField("FORDmax");
	}
	return tfFORDmax;
    }

    public BHTextField getTfWRTPmax() {
	if (tfWRTPmax == null) {
	    tfWRTPmax = new BHTextField("WRTPmax");
	}
	return tfWRTPmax;
    }

    public BHTextField getTfKASSmax() {
	if (tfKASSmax == null) {
	    tfKASSmax = new BHTextField("KASSmax");
	}
	return tfKASSmax;
    }

    public BHTextField getTfEkmax() {
	if (tfEkmax == null) {
	    tfEkmax = new BHTextField("Ekmax");
	}
	return tfEkmax;
    }

    public BHTextField getTfRCKSmax() {
	if (tfRCKSmax == null) {
	    tfRCKSmax = new BHTextField("RCKSmax");
	}
	return tfRCKSmax;
    }

    public BHTextField getTfVRBDmax() {
	if (tfVRBDmax == null) {
	    tfVRBDmax = new BHTextField("VRBDmax");
	}
	return tfVRBDmax;
    }

    public BHTextField getTfIMVGmin() {
	if (tfIMVGmin == null) {
	    tfIMVGmin = new BHTextField("IMVGmin");
	}
	return tfIMVGmin;
    }

    public BHTextField getTfSHANmin() {
	if (tfSHANmin == null) {
	    tfSHANmin = new BHTextField("SHANmin");
	}
	return tfSHANmin;
    }

    public BHTextField getTfFIANmin() {
	if (tfFIANmin == null) {
	    tfFIANmin = new BHTextField("FIANmin");
	}
	return tfFIANmin;
    }

    public BHTextField getTfVORRmin() {
	if (tfVORRmin == null) {
	    tfVORRmin = new BHTextField("VORRmin");
	}
	return tfVORRmin;
    }

    public BHTextField getTfFORDmin() {
	if (tfFORDmin == null) {
	    tfFORDmin = new BHTextField("FORDmin");
	}
	return tfFORDmin;
    }

    public BHTextField getTfWRTPmin() {
	if (tfWRTPmin == null) {
	    tfWRTPmin = new BHTextField("WRTPmin");
	}
	return tfWRTPmin;
    }

    public BHTextField getTfKASSmin() {
	if (tfKASSmin == null) {
	    tfKASSmin = new BHTextField("KASSmin");
	}
	return tfKASSmin;
    }

    public BHTextField getTfEKmin() {
	if (tfEKmin == null) {
	    tfEKmin = new BHTextField("EKmin");
	}
	return tfEKmin;
    }

    public BHTextField getTfRCKSmin() {
	if (tfRCKSmin == null) {
	    tfRCKSmin = new BHTextField("RCKSmin");
	}
	return tfRCKSmin;
    }

    public BHTextField getTfVRBDmin() {
	if (tfVRBDmin == null) {
	    tfVRBDmin = new BHTextField("VRBDmin");
	}
	return tfVRBDmin;
    }

    /**
     * Test main method.
     */
    public static void main(String args[]) {

	JFrame test = new JFrame("Test for ViewPeriodData1");
	test.setContentPane(new BHBalanceSheetForm());
	test.addWindowListener(new WindowAdapter() {
	    public void windowClosing(WindowEvent e) {
		System.exit(0);
	    }
	});
	test.pack();
	test.show();
    }
}
