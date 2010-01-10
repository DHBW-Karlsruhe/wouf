package org.bh.gui.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.bh.data.DTOScenario;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;
import org.bh.validation.VRIsBetween;
import org.bh.validation.VRIsInteger;
import org.bh.validation.VRMandatory;
import org.bh.validation.ValidationRule;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * This class contains the form for head-data
 * 
 * @author Anton Kharitonov
 * @author Patrick Heinz
 * @version 0.3, 01.01.2010
 * 
 */
public class BHScenarioHeadIntervalForm extends JPanel {

	private BHDescriptionLabel lscenname;
	private BHDescriptionLabel lscendescript;
	private BHDescriptionLabel lequityyield;
	private BHDescriptionLabel ldeptyield;
	private BHDescriptionLabel ltradetax;
	private BHDescriptionLabel lcorporatetax;
	private BHDescriptionLabel lbaseyear;

	private BHTextField tfscenname;
	private BHTextField tfscendescript;
	private BHTextField tfbaseyear;
	
	private JLabel lminpercentequity;
	private JLabel lminpercentdept;
	private JLabel lminpercenttrade;
	private JLabel lminpercentcorporate;
	private JLabel lmaxpercentequity;
	private JLabel lmaxpercentdept;
	private JLabel lmaxpercenttrade;
	private JLabel lmaxpercentcorporate;

	private BHDescriptionLabel ldcfMethod;
	private BHComboBox cbdcfMethod;
	private BHDescriptionLabel lprocess;
	private JComboBox cbprocess;
	private BHDescriptionLabel ldirect;
	private JComboBox cbdirect;

	ITranslator translator = Services.getTranslator();
	private BHTextField tfminequityyield;
	private BHTextField tfmindeptyield;
	private BHTextField tfmintradetax;
	private BHTextField tfmincorporatetax;
	private BHTextField tfmaxdeptyield;
	private BHTextField tfmaxcorporatetax;
	private BHTextField tfmaxequityyield;
	private BHTextField tfmaxtradetax;
	
	private JLabel lmin;
	private JLabel lmax;
	private JLabel lmin1;
	private JLabel lmax1;

	/**
	 * Constructor.
	 */
	public BHScenarioHeadIntervalForm() {
		this.initialize();
	}

	/**
	 * Initialize method.
	 */
	private void initialize() {

		String rowDef = "4px,p,4px,p,4px,p,4px,p,20px,p,4px,p,4px,p,4px,p,4px";
		String colDef = "4px,4px,right:pref,4px,pref,max(80px;pref),4px,left:pref,4px,pref,4px,left:pref,24px:grow,pref,4px,pref,4px,right:pref,4px,pref,pref,4px,pref,4px,pref,4px,left:pref,4px";

		FormLayout layout = new FormLayout(colDef, rowDef);
		this.setLayout(layout);

		layout.setColumnGroups(new int[][] { { 6, 10, 21, 25 } });

		CellConstraints cons = new CellConstraints();

		this.add(this.getlscenName(), cons.xywh(3, 4, 1, 1));
		this.add(this.getlbaseYear(), cons.xywh(18, 4, 1, 1));
		this.add(this.gettfbaseYear(), cons.xywh(21, 4, 1, 1));
		
		this.add(this.getLmin(), cons.xywh(6, 10, 1, 1, "center, center"));
		this.add(this.getLmax(), cons.xywh(10, 10, 1, 1, "center, center"));
		this.add(this.getLmin1(), cons.xywh(21, 10, 1, 1, "center, center"));
		this.add(this.getLmax1(), cons.xywh(25, 10, 1, 1, "center, center"));

		this.add(this.getlscenDescript(), cons.xywh(3, 6, 1, 1));
		this.add(this.getlequityYield(), cons.xywh(3, 12, 1, 1));
		this.add(this.getldeptYield(), cons.xywh(3, 14, 1, 1));
		this.add(this.getltradeTax(), cons.xywh(18, 12, 1, 1));
		this.add(this.getlcorporateTax(), cons.xywh(18, 14, 1, 1));
		this.add(this.gettfscenName(), cons.xywh(6, 4, 8, 1));
		this.add(this.gettfscenDescript(), cons.xywh(6, 6, 20, 1));
		
		this.add(this.gettfminequityyield(), cons.xywh(6, 12, 1, 1));
		this.add(this.gettfmindeptyield(), cons.xywh(6, 14, 1, 1));
		this.add(this.gettfmintradetax(), cons.xywh(21, 12, 1, 1));
		this.add(this.gettfmincorporatetax(), cons.xywh(21, 14, 1, 1));
		
		this.add(this.gettfmaxequityyield(), cons.xywh(10, 12, 1, 1));
		this.add(this.gettfmaxdeptyield(), cons.xywh(10, 14, 1, 1));
		this.add(this.gettfmaxtradetax(), cons.xywh(25, 12, 1, 1));
		this.add(this.gettfmaxcorporatetax(), cons.xywh(25, 14, 1, 1));
		
		this.add(this.getlminpercentEquity(), cons.xywh(8, 12, 1, 1));
		this.add(this.getlminpercentDept(), cons.xywh(8, 14, 1, 1));
		this.add(this.getlminpercentTrade(), cons.xywh(23, 12, 1, 1));
		this.add(this.getlminpercentCorporate(), cons.xywh(23, 14, 1, 1));
		
		this.add(this.getlmaxpercentEquity(), cons.xywh(12, 12, 1, 1));
		this.add(this.getlmaxpercentDept(), cons.xywh(12, 14, 1, 1));
		this.add(this.getlmaxpercentTrade(), cons.xywh(27, 12, 1, 1));
		this.add(this.getlmaxpercentCorporate(), cons.xywh(27, 14, 1, 1));
		
		// this.add(this.getLprocess(), cons.xywh(3, 16, 1, 1));
		// this.add(this.getCbprocess(), cons.xywh(6, 16, 3, 1));
		// this.add(this.getlDCFmethod(), cons.xywh(3, 14, 1, 1));
		// this.add(this.getcbDCFmethod(), cons.xywh(6, 14, 3, 1));
		// this.add(this.getLdirect(), cons.xywh(14, 14, 1, 1));
		// this.add(this.getCbdirect(), cons.xywh(17, 14, 3, 1));
	}

	// TODO add missing label keys etc. and translations, change hard coded
	// values to keys
	
	public BHTextField gettfminequityyield() {
		if (this.tfminequityyield == null) {
			this.tfminequityyield = new BHTextField(IBHComponent.MINVALUE + "_"
					+ DTOScenario.Key.REK);
		}
		return this.tfminequityyield;
	}

	public JLabel getLmin() {
		if(this.lmin == null){
			this.lmin = new JLabel(translator.translate("min"));
		}
		return lmin;
	}

	public JLabel getLmax() {
		if(this.lmax == null){
			this.lmax = new JLabel(translator.translate("max"));
		}
		return lmax;
	}
	
	public JLabel getLmin1() {
		if(this.lmin1 == null){
			this.lmin1 = new JLabel(translator.translate("min"));
		}
		return lmin1;
	}

	public JLabel getLmax1() {
		if(this.lmax1 == null){
			this.lmax1 = new JLabel(translator.translate("max"));
		}
		return lmax1;
	}

	public BHTextField gettfmindeptyield() {
		if (this.tfmindeptyield == null) {
			this.tfmindeptyield = new BHTextField(IBHComponent.MINVALUE + "_"
					+ DTOScenario.Key.RFK);
		}
		return this.tfmindeptyield;
	}

	public BHTextField gettfmintradetax() {
		if (this.tfmintradetax == null) {
			this.tfmintradetax = new BHTextField(IBHComponent.MINVALUE + "_"
					+ DTOScenario.Key.BTAX);
		}
		return this.tfmintradetax;
	}

	public BHTextField gettfmincorporatetax() {
		if (this.tfmincorporatetax == null) {
			this.tfmincorporatetax = new BHTextField(IBHComponent.MINVALUE
					+ "_" + DTOScenario.Key.CTAX);
		}
		return this.tfmincorporatetax;
	}

	public BHTextField gettfmaxequityyield() {
		if (this.tfmaxequityyield == null) {
			this.tfmaxequityyield = new BHTextField(IBHComponent.MAXVALUE + "_"
					+ DTOScenario.Key.REK);
		}
		return this.tfmaxequityyield;
	}

	public BHTextField gettfmaxdeptyield() {
		if (this.tfmaxdeptyield == null) {
			this.tfmaxdeptyield = new BHTextField(IBHComponent.MAXVALUE + "_"
					+ DTOScenario.Key.RFK);
		}
		return this.tfmaxdeptyield;
	}

	public BHTextField gettfmaxtradetax() {
		if (this.tfmaxtradetax == null) {
			this.tfmaxtradetax = new BHTextField(IBHComponent.MAXVALUE + "_"
					+ DTOScenario.Key.BTAX);
		}
		return this.tfmaxtradetax;
	}

	public BHTextField gettfmaxcorporatetax() {
		if (this.tfmaxcorporatetax == null) {
			this.tfmaxcorporatetax = new BHTextField(IBHComponent.MAXVALUE
					+ "_" + DTOScenario.Key.CTAX);
		}
		return this.tfmaxcorporatetax;
	}

	public BHDescriptionLabel getLprocess() {
		if (this.lprocess == null) {
			this.lprocess = new BHDescriptionLabel("Berechnungsart");
		}
		return lprocess;
	}

	public JComboBox getCbprocess() {
		if (this.cbprocess == null) {
			this.cbprocess = new JComboBox();
		}
		return cbprocess;
	}

	public BHDescriptionLabel getlDCFmethod() {
		if (this.ldcfMethod == null)
			this.ldcfMethod = new BHDescriptionLabel(DTOScenario.Key.DCF_METHOD);
		return this.ldcfMethod;
	}

	public BHComboBox getcbDCFmethod() {
		if (this.cbdcfMethod == null) {
			this.cbdcfMethod = new BHComboBox(DTOScenario.Key.DCF_METHOD);
		}
		return this.cbdcfMethod;
	}

	public BHDescriptionLabel getLdirect() {
		if (this.ldirect == null) {
			this.ldirect = new BHDescriptionLabel("direct");
		}
		return ldirect;
	}

	public JComboBox getCbdirect() {
		if (this.cbdirect == null) {
			this.cbdirect = new JComboBox();
		}
		return cbdirect;
	}

	/**
	 * Getter method for component lscenName.
	 * 
	 * @return the initialized component
	 */
	public BHDescriptionLabel getlscenName() {

		if (this.lscenname == null) {
			this.lscenname = new BHDescriptionLabel(DTOScenario.Key.NAME);
		}

		return this.lscenname;
	}

	/**
	 * Getter method for component lscenDescript.
	 * 
	 * @return the initialized component
	 */
	public BHDescriptionLabel getlscenDescript() {

		if (this.lscendescript == null) {
			this.lscendescript = new BHDescriptionLabel(DTOScenario.Key.COMMENT);
		}

		return this.lscendescript;
	}

	/**
	 * Getter method for component lequityYield.
	 * 
	 * @return the initialized component
	 */
	public BHDescriptionLabel getlequityYield() {

		if (this.lequityyield == null) {
			this.lequityyield = new BHDescriptionLabel(DTOScenario.Key.REK);
		}

		return this.lequityyield;
	}

	/**
	 * Getter method for component ldeptYield.
	 * 
	 * @return the initialized component
	 */
	public BHDescriptionLabel getldeptYield() {

		if (this.ldeptyield == null) {
			this.ldeptyield = new BHDescriptionLabel(DTOScenario.Key.RFK);
		}

		return this.ldeptyield;
	}

	/**
	 * Getter method for component ltradeTax.
	 * 
	 * @return the initialized component
	 */
	public BHDescriptionLabel getltradeTax() {

		if (this.ltradetax == null) {
			this.ltradetax = new BHDescriptionLabel(DTOScenario.Key.BTAX);
		}

		return this.ltradetax;
	}

	/**
	 * Getter method for component lcorporateTax.
	 * 
	 * @return the initialized component
	 */
	public BHDescriptionLabel getlcorporateTax() {

		if (this.lcorporatetax == null) {
			this.lcorporatetax = new BHDescriptionLabel(DTOScenario.Key.CTAX);
		}

		return this.lcorporatetax;
	}

	/**
	 * Getter method for component lbaseYear.
	 * 
	 * @return the initialized component
	 */
	public BHDescriptionLabel getlbaseYear() {

		if (this.lbaseyear == null) {
			this.lbaseyear = new BHDescriptionLabel(DTOScenario.Key.IDENTIFIER);
		}

		return this.lbaseyear;
	}

	// Here do the getters for the textfields begin

	/**
	 * Getter method for component tfscenName.
	 * 
	 * @return the initialized component
	 */
	public BHTextField gettfscenName() {

		if (this.tfscenname == null) {
			this.tfscenname = new BHTextField(DTOScenario.Key.NAME, false);
			ValidationRule[] rules = { VRMandatory.INSTANCE };
			tfscenname.setValidationRules(rules);
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
			this.tfscendescript = new BHTextField(DTOScenario.Key.COMMENT,
					false);
		}
		return this.tfscendescript;
	}

	/**
	 * Getter method for component tfbaseYear.
	 * 
	 * @return the initialized component
	 */
	public BHTextField gettfbaseYear() {

		if (this.tfbaseyear == null) {
			this.tfbaseyear = new BHTextField(DTOScenario.Key.IDENTIFIER);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsInteger.INSTANCE, VRIsBetween.BETWEEN1900AND2100 };
			tfbaseyear.setValidationRules(rules);
		}
		return this.tfbaseyear;
	}

	/**
	 * Getter method for component lpercentEquity.
	 * 
	 * @return the initialized component
	 */
	public JLabel getlminpercentEquity() {

		if (this.lminpercentequity == null) {
			this.lminpercentequity = new JLabel("%");
		}
		return this.lminpercentequity;
	}

	/**
	 * Getter method for component lpercentDept.
	 * 
	 * @return the initialized component
	 */
	public JLabel getlminpercentDept() {

		if (this.lminpercentdept == null) {
			this.lminpercentdept = new JLabel("%");
		}
		return this.lminpercentdept;
	}

	/**
	 * Getter method for component lpercentTrade.
	 * 
	 * @return the initialized component
	 */
	public JLabel getlminpercentTrade() {

		if (this.lminpercenttrade == null) {
			this.lminpercenttrade = new JLabel("%");
		}
		return this.lminpercenttrade;
	}

	/**
	 * Getter method for component lminpercentCorporate.
	 * 
	 * @return the initialized component
	 */
	public JLabel getlminpercentCorporate() {

		if (this.lminpercentcorporate == null) {
			this.lminpercentcorporate = new JLabel("%");
		}
		return this.lminpercentcorporate;
	}
	
	/**
	 * Getter method for component lpercentEquity.
	 * 
	 * @return the initialized component
	 */
	public JLabel getlmaxpercentEquity() {

		if (this.lmaxpercentequity == null) {
			this.lmaxpercentequity = new JLabel("%");
		}
		return this.lmaxpercentequity;
	}

	/**
	 * Getter method for component lpercentDept.
	 * 
	 * @return the initialized component
	 */
	public JLabel getlmaxpercentDept() {

		if (this.lmaxpercentdept == null) {
			this.lmaxpercentdept = new JLabel("%");
		}
		return this.lmaxpercentdept;
	}

	/**
	 * Getter method for component lpercentTrade.
	 * 
	 * @return the initialized component
	 */
	public JLabel getlmaxpercentTrade() {

		if (this.lmaxpercenttrade == null) {
			this.lmaxpercenttrade = new JLabel("%");
		}
		return this.lmaxpercenttrade;
	}

	/**
	 * Getter method for component lmaxpercentCorporate.
	 * 
	 * @return the initialized component
	 */
	public JLabel getlmaxpercentCorporate() {

		if (this.lmaxpercentcorporate == null) {
			this.lmaxpercentcorporate = new JLabel("%");
		}
		return this.lmaxpercentcorporate;
	}

	// TODO remove main later
	/**
	 * Test main method.
	 */
	@SuppressWarnings("deprecation")
	public static void main(String args[]) {

		JFrame test = new JFrame("Test for ViewHeadData1");
		test.setContentPane(new BHScenarioHeadIntervalForm());
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
