package org.bh.gui.swing;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.bh.gui.swing.BHLabel;
import org.bh.gui.swing.BHTextField;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Visual class ViewStochasticData3.
 * 
 * Created with Mogwai FormMaker 0.6.
 */
public class BHStochasticInputForm extends JPanel {

    private BHLabel ldcfchoise;
    private JComboBox cbdcfchoise;
    private BHLabel lstochprocess;
    private BHLabel lrange;
    private BHTextField tfrange;
    private BHLabel lprobab;
    private BHTextField tfprobab;
    private BHLabel lpercentprobab;

    private JComboBox cbstochprocess;

    /**
     * Constructor.
     */
    public BHStochasticInputForm() {
	this.initialize();
    }

    /**
     * Initialize method.
     */
    private void initialize() {

	String rowDef = "2dlu,p,10dlu,p,2dlu,p,12dlu,p,2dlu,p,2dlu";
	String colDef = "2dlu:grow(0.3),2dlu,right:pref,2dlu,pref,max(40dlu;pref),2dlu,left:5dlu,12dlu:grow(0.4),pref,2dlu,pref,2dlu,right:pref,2dlu,pref,pref,2dlu,pref,2dlu,pref,2dlu:grow(0.3)";

	FormLayout layout = new FormLayout(colDef, rowDef);
	this.setLayout(layout);

	CellConstraints cons = new CellConstraints();

	this.add(this.getlDCFchoise(), cons.xywh(3, 2, 1, 1));
	this.add(this.getcbDCFchoise(), cons.xywh(6, 2, 3, 1));
	this.add(this.getlstochProcess(), cons.xywh(3, 6, 1, 1));
	this.add(this.getlrange(), cons.xywh(3, 8, 1, 1));
	this.add(this.gettfrange(), cons.xywh(6, 8, 1, 1));
	this.add(this.getlprobab(), cons.xywh(3, 10, 1, 1));
	this.add(this.gettfprobab(), cons.xywh(6, 10, 1, 1));
	this.add(this.getlpercentProbab(), cons.xywh(8, 10, 1, 1));
	this.add(this.getcbstochProcess(), cons.xywh(6, 6, 3, 1));

    }

    /**
     * Getter method for component lDCFchoise.
     * 
     * @return the initialized component
     */
    public BHLabel getlDCFchoise() {

	if (this.ldcfchoise == null) {
	    this.ldcfchoise = new BHLabel("","Discounted Cashflow Verfahren");
	}

	return this.ldcfchoise;
    }

    /**
     * Getter method for component cbDCFchoise.
     * 
     * @return the initialized component
     */
    public JComboBox getcbDCFchoise() {

	if (this.cbdcfchoise == null) {
	    this.cbdcfchoise = new JComboBox();
	}

	return this.cbdcfchoise;
    }

    /**
     * Getter method for component lstochProcess.
     * 
     * @return the initialized component
     */
    public BHLabel getlstochProcess() {

	if (this.lstochprocess == null) {
	    this.lstochprocess = new BHLabel("","Sochastischer Prozess:");
	}

	return this.lstochprocess;
    }

    /**
     * Getter method for component lrange.
     * 
     * @return the initialized component
     */
    public BHLabel getlrange() {

	if (this.lrange == null) {
	    this.lrange = new BHLabel("","Schrittweite");
	}

	return this.lrange;
    }

    /**
     * Getter method for component tfrange.
     * 
     * @return the initialized component
     */
    public BHTextField gettfrange() {

	if (this.tfrange == null) {
	    this.tfrange = new BHTextField("","");
	}

	return this.tfrange;
    }

    /**
     * Getter method for component lprobab.
     * 
     * @return the initialized component
     */
    public BHLabel getlprobab() {

	if (this.lprobab == null) {
	    this.lprobab = new BHLabel("","Wahrscheinlichkeit");
	}

	return this.lprobab;
    }

    /**
     * Getter method for component tfprobab.
     * 
     * @return the initialized component
     */
    public BHTextField gettfprobab() {

	if (this.tfprobab == null) {
	    this.tfprobab = new BHTextField("","");
	}

	return this.tfprobab;
    }

    /**
     * Getter method for component lpercentProbab.
     * 
     * @return the initialized component
     */
    public BHLabel getlpercentProbab() {

	if (this.lpercentprobab == null) {
	    this.lpercentprobab = new BHLabel("", "%");
	}

	return this.lpercentprobab;
    }

    /**
     * Getter method for component cbstochProcess.
     * 
     * @return the initialized component
     */
    public JComboBox getcbstochProcess() {

	if (this.cbstochprocess == null) {
	    this.cbstochprocess = new JComboBox();
	    this.cbstochprocess.setName("cbstochProcess");
	}

	return this.cbstochprocess;
    }

//    /**
//     * Test main method.
//     */
//    public static void main(String args[]) {
//
//	JFrame test = new JFrame("Test for ViewStochasticData3");
//	test.setContentPane(new BHStochasticInputForm());
//	test.addWindowListener(new WindowAdapter() {
//	    public void windowClosing(WindowEvent e) {
//		System.exit(0);
//	    }
//	});
//	test.pack();
//	test.show();
//    }
}
