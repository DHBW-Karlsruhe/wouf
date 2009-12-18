package org.bh.gui.swing;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

import org.bh.data.DTOPeriod;
import org.bh.gui.swing.BHLabel;
import org.bh.gui.swing.BHTextField;
import org.bh.platform.i18n.BHTranslator;

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
    private String year;
    final BHTranslator translator = BHTranslator.getInstance();

    /**
     * Constructor.
     */
    public BHPeriodInputForm(String year) {
	
	this.year = year;
	
	String rowDef = "2dlu,p,2dlu";
	String colDef = "2dlu:grow,right:pref,2dlu,30dlu:grow(0.2),2dlu,pref,12dlu:grow,right:pref,2dlu,30dlu:grow(0.2),2dlu,pref,2dlu:grow";

	FormLayout layout = new FormLayout(colDef, rowDef);
	this.setLayout(layout);

	CellConstraints cons = new CellConstraints();

	this.add(this.getLfcf(), cons.xywh(2, 2, 1, 1));
	this.add(this.getTffcf(), cons.xywh(4, 2, 1, 1));
	this.add(this.getLcurrency1(), cons.xywh(6, 2, 1, 1));
	this.add(this.getLliabilities(), cons.xywh(8, 2, 1, 1));
	this.add(this.getTfliabilities(), cons.xywh(10, 2, 1, 1));
	this.add(this.getLcurrency2(), cons.xywh(12, 2, 1, 1));

	this.setBorder(BorderFactory.createTitledBorder(BorderFactory
		.createEtchedBorder(EtchedBorder.LOWERED), this.year));
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

//    /**
//     * Test main method.
//     */
//    public static void main(String args[]) {
//
//	JFrame test = new JFrame("Test for ViewPeriodData1");
//	test.setContentPane(new BHPeriodInputForm("2009"));
//	test.addWindowListener(new WindowAdapter() {
//	    public void windowClosing(WindowEvent e) {
//		System.exit(0);
//	    }
//	});
//	test.pack();
//	test.show();
//    }
}
