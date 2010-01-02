package org.bh.gui.swing;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import org.bh.data.DTOPeriod;
import org.bh.platform.Services;
import org.bh.platform.i18n.BHTranslator;
import org.bh.platform.i18n.ITranslator;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Visual class ViewPeriodData1.
 * 
 * 
 */
public class BHPeriodInputForm extends JPanel {

    private BHLabel lfcf;
    private BHTextField tffcf;
    private BHLabel lliabilities;
    private BHTextField tfliabilities;
    private BHLabel lcurrency1;
    private BHLabel lcurrency2;
    
    private BHLabel lmax;
    private BHLabel lmin;
    private BHLabel lcurrency3;
    private BHLabel lcurrency4;
    private BHLabel lcurrency5;
    private BHLabel lcurrency6;
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
	layout.setColumnGroups(new int[][]{{4,10,16}});

	this.add(this.getLfcf(), cons.xywh(2, 4, 1, 1));
	this.add(this.getTffcf(), cons.xywh(4, 4, 1, 1));
	this.add(this.getLcurrency1(), cons.xywh(6, 4, 1, 1));
	this.add(this.getLliabilities(), cons.xywh(2, 6, 1, 1));
	this.add(this.getTfliabilities(), cons.xywh(4, 6, 1, 1));
	this.add(this.getLcurrency2(), cons.xywh(6, 6, 1, 1));
	
	this.add(new JSeparator(SwingConstants.VERTICAL), cons.xywh(8, 2, 1, 6));
	this.add(this.getLmin(), cons.xywh(10, 2, 1, 1));
	this.add(new JSeparator(SwingConstants.VERTICAL), cons.xywh(14, 2, 1, 6));
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
    
    

    public BHLabel getLmax() {
	if (lmax == null) {
	    lmax = new BHLabel("", "Max");
	}
        return lmax;
    }



    public BHLabel getLmin() {
	if (lmin == null) {
	    lmin = new BHLabel("", "Min");
	}
	return lmin;
    }



    public BHLabel getLcurrency3() {
	if (lcurrency3 == null) {
	    lcurrency3 = new BHLabel("", "€");
	}
	return lcurrency3;
    }
    
    public BHLabel getLcurrency4() {
	if (lcurrency4 == null) {
	    lcurrency4 = new BHLabel("", "€");
	}
	return lcurrency4;
    }
    
    public BHLabel getLcurrency5() {
	if (lcurrency5 == null) {
	    lcurrency5 = new BHLabel("", "€");
	}
	return lcurrency5;
    }
    
    public BHLabel getLcurrency6() {
	if (lcurrency6 == null) {
	    lcurrency6 = new BHLabel("", "€");
	}
	return lcurrency6;
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



    public BHLabel getLfcf() {
	if (lfcf == null) {
	    lfcf = new BHLabel(DTOPeriod.Key.FCF.toString(), "Free Cashflow");
	}
	return lfcf;
    }

    public BHTextField getTffcf() {
	if (tffcf == null) {
	    tffcf = new BHTextField(DTOPeriod.Key.FCF.toString());
	}
	return tffcf;
    }

    public BHLabel getLliabilities() {
	if (lliabilities == null) {
	    lliabilities = new BHLabel(DTOPeriod.Key.LIABILITIES.toString(),"Bilanzwert Fremdkapital");
	}
	return lliabilities;
    }

    public BHTextField getTfliabilities() {
	if (tfliabilities == null) {
	    tfliabilities = new BHTextField(DTOPeriod.Key.LIABILITIES
		    .toString());
//	    tfliabilities.setText("bla");
	}
	return tfliabilities;
    }

    public BHLabel getLcurrency1() {
	if (lcurrency1 == null) {
	    lcurrency1 = new BHLabel("","€");
	}
	return lcurrency1;
    }

    public BHLabel getLcurrency2() {
	if (lcurrency2 == null) {
	    lcurrency2 = new BHLabel("","€");
	}
	return lcurrency2;
    }

    /**
     * Test main method.
     */
    public static void main(String args[]) {

	JFrame test = new JFrame("Test for ViewPeriodData1");
	test.setContentPane(new BHPeriodInputForm("2009"));
	test.addWindowListener(new WindowAdapter() {
	    public void windowClosing(WindowEvent e) {
		System.exit(0);
	    }
	});
	test.pack();
	test.show();
    }
}
