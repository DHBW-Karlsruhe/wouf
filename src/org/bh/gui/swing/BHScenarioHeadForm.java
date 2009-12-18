package org.bh.gui.swing;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.bh.data.DTOScenario;
import org.bh.gui.swing.BHLabel;
import org.bh.gui.swing.BHTextField;
import org.bh.platform.i18n.BHTranslator;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Visual class ViewHeadData1.
 * 
 * Created with Mogwai FormMaker 0.6.
 */
public class BHScenarioHeadForm extends JPanel {

    private BHLabel lscenname;
    private BHLabel lscendescript;
    private BHLabel lequityyield;
    private BHLabel ldeptyield;
    private BHLabel ltradetax;
    private BHLabel lcorporatetax;
    private BHLabel lbaseyear;
    private BHTextField tfscenname;
    private BHTextField tfscendescript;
    private BHTextField tfequityyeild;
    private BHTextField tfdeptyield;
    private BHTextField tftradetax;
    private BHTextField tfcorporatetax;
    private BHTextField tfbaseyear;
    private BHLabel lpercentequity;
    private BHLabel lpercentdept;
    private BHLabel lpercenttrade;
    private BHLabel lpercentcorporate;
    final BHTranslator translator = BHTranslator.getInstance();

    /**
     * Constructor.
     */
    public BHScenarioHeadForm() {
	this.initialize();
    }

    /**
     * Initialize method.
     */
    private void initialize() {

	String rowDef = "2dlu,p,2dlu,p,2dlu,p,2dlu,p,10dlu,p,2dlu,p,2dlu";
	String colDef = "2dlu:grow(0.3),2dlu,right:pref,2dlu,pref,max(40dlu;pref),2dlu,left:5dlu,12dlu:grow(0.4),pref,2dlu,pref,2dlu,right:pref,2dlu,pref,pref,2dlu,pref,2dlu,pref,2dlu:grow(0.3)";

	FormLayout layout = new FormLayout(colDef, rowDef);
	this.setLayout(layout);

	layout.setColumnGroups(new int[][] { { 6, 17 } });

	CellConstraints cons = new CellConstraints();

	this.add(this.getlscenName(), cons.xywh(3, 4, 1, 1));
	this.add(this.getlscenDescript(), cons.xywh(3, 6, 1, 1));
	this.add(this.getlequityYield(), cons.xywh(3, 10, 1, 1));
	this.add(this.getldeptYield(), cons.xywh(3, 12, 1, 1));
	this.add(this.getltradeTax(), cons.xywh(14, 10, 1, 1));
	this.add(this.getlcorporateTax(), cons.xywh(14, 12, 1, 1));
	this.add(this.getlbaseYear(), cons.xywh(3, 8, 1, 1));
	this.add(this.gettfscenName(), cons.xywh(6, 4, 4, 1));
	this.add(this.gettfscenDescript(), cons.xywh(6, 6, 14, 1));
	this.add(this.gettfequityYeild(), cons.xywh(6, 10, 1, 1));
	this.add(this.gettfdeptYield(), cons.xywh(6, 12, 1, 1));
	this.add(this.gettftradeTax(), cons.xywh(17, 10, 1, 1));
	this.add(this.gettfcorporateTax(), cons.xywh(17, 12, 1, 1));
	this.add(this.gettfbaseYear(), cons.xywh(6, 8, 1, 1));
	this.add(this.getlpercentEquity(), cons.xywh(8, 10, 1, 1));
	this.add(this.getlpercentDept(), cons.xywh(8, 12, 1, 1));
	this.add(this.getlpercentTrade(), cons.xywh(19, 10, 1, 1));
	this.add(this.getlpercentCorporate(), cons.xywh(19, 12, 1, 1));

    }

    /**
     * Getter method for component lscenName.
     * 
     * @return the initialized component
     */
    public BHLabel getlscenName() {

	if (this.lscenname == null) {
	    this.lscenname = new BHLabel(DTOScenario.Key.NAME.toString(),
		    "Szenarioname");

	}

	return this.lscenname;
    }

    /**
     * Getter method for component lscenDescript.
     * 
     * @return the initialized component
     */
    public BHLabel getlscenDescript() {

	if (this.lscendescript == null) {
	    this.lscendescript = new BHLabel(
		    DTOScenario.Key.COMMENT.toString(), "Beschreibung");
	}

	return this.lscendescript;
    }

    /**
     * Getter method for component lequityYield.
     * 
     * @return the initialized component
     */
    public BHLabel getlequityYield() {

	if (this.lequityyield == null) {
	    this.lequityyield = new BHLabel(DTOScenario.Key.REK.toString(),
		    "Renditeforderung Eigenkapital");
	}

	return this.lequityyield;
    }

    /**
     * Getter method for component ldeptYield.
     * 
     * @return the initialized component
     */
    public BHLabel getldeptYield() {

	if (this.ldeptyield == null) {
	    this.ldeptyield = new BHLabel(DTOScenario.Key.RFK.toString(),
		    "Renditeforderung Fremdkapital");
	}

	return this.ldeptyield;
    }

    /**
     * Getter method for component ltradeTax.
     * 
     * @return the initialized component
     */
    public BHLabel getltradeTax() {

	if (this.ltradetax == null) {
	    this.ltradetax = new BHLabel(DTOScenario.Key.BTAX.toString(),
		    "Gewerbesteuersatz");
	}

	return this.ltradetax;
    }

    /**
     * Getter method for component lcorporateTax.
     * 
     * @return the initialized component
     */
    public BHLabel getlcorporateTax() {

	if (this.lcorporatetax == null) {
	    this.lcorporatetax = new BHLabel(DTOScenario.Key.CTAX.toString(),
		    "Körperschaftssteuer und Solidaritätszuschlag");
	}

	return this.lcorporatetax;
    }

    /**
     * Getter method for component lbaseYear.
     * 
     * @return the initialized component
     */
    public BHLabel getlbaseYear() {

	if (this.lbaseyear == null) {
	    this.lbaseyear = new BHLabel("baseyear","Basisjahr");
	}

	return this.lbaseyear;
    }

    /**
     * Getter method for component tfscenName.
     * 
     * @return the initialized component
     */
    public BHTextField gettfscenName() {

	if (this.tfscenname == null) {
	    this.tfscenname = new BHTextField(DTOScenario.Key.NAME.toString(),
		    "");
	}

	return this.tfscenname;
    }

    /**
     * Getter method for component tfscenDescript.
     * 
     * @return the initialized component
     */
    public BHTextField gettfscenDescript() {

	if (this.tfscendescript == null) {
	    this.tfscendescript = new BHTextField(DTOScenario.Key.COMMENT
		    .toString(), "");
	}

	return this.tfscendescript;
    }

    /**
     * Getter method for component tfequityYeild.
     * 
     * @return the initialized component
     */
    public BHTextField gettfequityYeild() {

	if (this.tfequityyeild == null) {
	    this.tfequityyeild = new BHTextField(
		    DTOScenario.Key.REK.toString(), "");
	}

	return this.tfequityyeild;
    }

    /**
     * Getter method for component tfdeptYield.
     * 
     * @return the initialized component
     */
    public BHTextField gettfdeptYield() {

	if (this.tfdeptyield == null) {
	    this.tfdeptyield = new BHTextField(DTOScenario.Key.RFK.toString(),
		    "");
	}

	return this.tfdeptyield;
    }

    /**
     * Getter method for component tftradeTax.
     * 
     * @return the initialized component
     */
    public BHTextField gettftradeTax() {

	if (this.tftradetax == null) {
	    this.tftradetax = new BHTextField(DTOScenario.Key.BTAX.toString(),
		    "");
	}

	return this.tftradetax;
    }

    /**
     * Getter method for component tfcorporateTax.
     * 
     * @return the initialized component
     */
    public BHTextField gettfcorporateTax() {

	if (this.tfcorporatetax == null) {
	    this.tfcorporatetax = new BHTextField(DTOScenario.Key.CTAX
		    .toString(), "");
	}

	return this.tfcorporatetax;
    }

    /**
     * Getter method for component tfbaseYear.
     * 
     * @return the initialized component
     */
    public BHTextField gettfbaseYear() {

	if (this.tfbaseyear == null) {
	    this.tfbaseyear = new BHTextField("", "");
	}

	return this.tfbaseyear;
    }

    /**
     * Getter method for component lpercentEquity.
     * 
     * @return the initialized component
     */
    public BHLabel getlpercentEquity() {

	if (this.lpercentequity == null) {
	    this.lpercentequity = new BHLabel("", "%");
	}

	return this.lpercentequity;
    }

    /**
     * Getter method for component lpercentDept.
     * 
     * @return the initialized component
     */
    public BHLabel getlpercentDept() {

	if (this.lpercentdept == null) {
	    this.lpercentdept = new BHLabel("", "%");
	}

	return this.lpercentdept;
    }

    /**
     * Getter method for component lpercentTrade.
     * 
     * @return the initialized component
     */
    public BHLabel getlpercentTrade() {

	if (this.lpercenttrade == null) {
	    this.lpercenttrade = new BHLabel("", "%");
	}

	return this.lpercenttrade;
    }

    /**
     * Getter method for component lpercentCorporate.
     * 
     * @return the initialized component
     */
    public BHLabel getlpercentCorporate() {

	if (this.lpercentcorporate == null) {
	    this.lpercentcorporate = new BHLabel("", "%");
	}

	return this.lpercentcorporate;
    }

    // /**
    // * Test main method.
    // */
    // public static void main(String args[]) {
    //
    // JFrame test=new JFrame("Test for ViewHeadData1");
    // test.setContentPane(new BHScenarioHeadForm());
    // test.addWindowListener(new WindowAdapter() {
    // public void windowClosing(WindowEvent e) {
    // System.exit(0);
    // }
    // });
    // test.pack();
    // test.show();
    // }
}
