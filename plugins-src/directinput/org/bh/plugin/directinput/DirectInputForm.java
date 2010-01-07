package org.bh.plugin.directinput;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import org.bh.gui.swing.BHDescriptionLabel;
import org.bh.gui.swing.BHTextField;
import org.bh.platform.i18n.BHTranslator;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Visual class ViewPeriodData1.
 * 
 * 
 */
public class DirectInputForm extends JPanel {

    private BHDescriptionLabel lfcf;
    private BHTextField tffcf;
    private BHDescriptionLabel lliabilities;
    private BHTextField tfliabilities;
    private BHDescriptionLabel lcurrency1;
    private BHDescriptionLabel lcurrency2;
    
    private BHDescriptionLabel lmax;
    private BHDescriptionLabel lmin;
    private BHDescriptionLabel lcurrency3;
    private BHDescriptionLabel lcurrency4;
    private BHDescriptionLabel lcurrency5;
    private BHDescriptionLabel lcurrency6;
    private BHTextField tfmaxliabilities;
    private BHTextField tfminliabilities;
    private BHTextField tfmaxfcf;
    private BHTextField tfminfcf;
    
    private String year;
    final BHTranslator translator = BHTranslator.getInstance();

    /**
     * Constructor.
     */
    public DirectInputForm(String year) {
	
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
    
    

    public BHDescriptionLabel getLmax() {
	if (lmax == null) {
	    lmax = new BHDescriptionLabel("max");
	}
        return lmax;
    }



    public BHDescriptionLabel getLmin() {
	if (lmin == null) {
	    lmin = new BHDescriptionLabel("min");
	}
	return lmin;
    }



    public BHDescriptionLabel getLcurrency3() {
	if (lcurrency3 == null) {
	    lcurrency3 = new BHDescriptionLabel("currency");
	}
	return lcurrency3;
    }
    
    public BHDescriptionLabel getLcurrency4() {
	if (lcurrency4 == null) {
	    lcurrency4 = new BHDescriptionLabel("currency");
	}
	return lcurrency4;
    }
    
    public BHDescriptionLabel getLcurrency5() {
	if (lcurrency5 == null) {
	    lcurrency5 = new BHDescriptionLabel("currency");
	}
	return lcurrency5;
    }
    
    public BHDescriptionLabel getLcurrency6() {
	if (lcurrency6 == null) {
	    lcurrency6 = new BHDescriptionLabel("currency");
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



    public BHDescriptionLabel getLfcf() {
	if (lfcf == null) {
	    lfcf = new BHDescriptionLabel(DTODirectInput.Key.FCF.toString());
	}
	return lfcf;
    }

    public BHTextField getTffcf() {
	if (tffcf == null) {
	    tffcf = new BHTextField(DTODirectInput.Key.FCF);
	}
	return tffcf;
    }

    public BHDescriptionLabel getLliabilities() {
	if (lliabilities == null) {
	    lliabilities = new BHDescriptionLabel(DTODirectInput.Key.LIABILITIES);
	}
	return lliabilities;
    }

    public BHTextField getTfliabilities() {
	if (tfliabilities == null) {
	    tfliabilities = new BHTextField(DTODirectInput.Key.LIABILITIES);
//	    tfliabilities.setText("bla");
	}
	return tfliabilities;
    }

    public BHDescriptionLabel getLcurrency1() {
	if (lcurrency1 == null) {
	    lcurrency1 = new BHDescriptionLabel("currency");
	}
	return lcurrency1;
    }

    public BHDescriptionLabel getLcurrency2() {
	if (lcurrency2 == null) {
	    lcurrency2 = new BHDescriptionLabel("currency");
	}
	return lcurrency2;
    }

    /**
     * Test main method.
     */
    public static void main(String args[]) {

	JFrame test = new JFrame("Test for ViewPeriodData1");
	test.setContentPane(new DirectInputForm("2009"));
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
