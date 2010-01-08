package org.bh.gui.swing;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.bh.data.DTOScenario;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;
import org.bh.validation.VRIsBetween;
import org.bh.validation.VRIsDouble;
import org.bh.validation.VRIsInteger;
import org.bh.validation.VRIsPositive;
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
public class BHScenarioHeadForm extends JPanel {

	private BHDescriptionLabel lscenname;
	private BHDescriptionLabel lscendescript;
	private BHDescriptionLabel lequityyield;
	private BHDescriptionLabel ldeptyield;
	private BHDescriptionLabel ltradetax;
	private BHDescriptionLabel lcorporatetax;
	private BHDescriptionLabel lbaseyear;

	private BHTextField tfscenname;
	private BHTextField tfscendescript;
	private BHTextField tfequityyield;
	private BHTextField tfdeptyield;
	private BHTextField tftradetax;
	private BHTextField tfcorporatetax;
	private BHTextField tfbaseyear;
	private JLabel lpercentequity;
	private JLabel lpercentdept;
	private JLabel lpercenttrade;
	private JLabel lpercentcorporate;

	private BHDescriptionLabel ldcfMethod;
	private BHComboBox cbdcfMethod;
	private BHDescriptionLabel lprocess;
	private JComboBox cbprocess;
	private BHDescriptionLabel ldirect;
	private JComboBox cbdirect;
	
	

	ITranslator translator = Services.getTranslator();

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

		String rowDef = "4px,p,4px,p,4px,p,4px,p,20px,p,4px,p,20px,p,4px,p,4px";
		String colDef = "4px,4px,right:pref,4px,pref,max(80px;pref),4px,left:pref,24px:grow,pref,4px,pref,4px,right:pref,4px,pref,pref,4px,pref,4px,pref,4px";

		FormLayout layout = new FormLayout(colDef, rowDef);
		this.setLayout(layout);

		layout.setColumnGroups(new int[][] { { 6, 17 } });

		CellConstraints cons = new CellConstraints();

		this.add(this.getlscenName(), cons.xywh(3, 4, 1, 1));
		this.add(this.getlbaseYear(), cons.xywh(14, 4, 1, 1));
		this.add(this.gettfbaseYear(), cons.xywh(17, 4, 1, 1));

		this.add(this.getlscenDescript(), cons.xywh(3, 6, 1, 1));
		this.add(this.getlequityYield(), cons.xywh(3, 10, 1, 1));
		this.add(this.getldeptYield(), cons.xywh(3, 12, 1, 1));
		this.add(this.getltradeTax(), cons.xywh(14, 10, 1, 1));
		this.add(this.getlcorporateTax(), cons.xywh(14, 12, 1, 1));
		this.add(this.gettfscenName(), cons.xywh(6, 4, 4, 1));
		this.add(this.gettfscenDescript(), cons.xywh(6, 6, 12, 1));
		this.add(this.gettfequityYeild(), cons.xywh(6, 10, 1, 1));
		this.add(this.gettfdeptYield(), cons.xywh(6, 12, 1, 1));
		this.add(this.gettftradeTax(), cons.xywh(17, 10, 1, 1));
		this.add(this.gettfcorporateTax(), cons.xywh(17, 12, 1, 1));
		this.add(this.getlpercentEquity(), cons.xywh(8, 10, 1, 1));
		this.add(this.getlpercentDept(), cons.xywh(8, 12, 1, 1));
		this.add(this.getlpercentTrade(), cons.xywh(19, 10, 1, 1));
		this.add(this.getlpercentCorporate(), cons.xywh(19, 12, 1, 1));
		//this.add(this.getLprocess(), cons.xywh(3, 16, 1, 1));
		//this.add(this.getCbprocess(), cons.xywh(6, 16, 3, 1));
		this.add(this.getlDCFmethod(), cons.xywh(3, 14, 1, 1));
		this.add(this.getcbDCFmethod(), cons.xywh(6, 14, 3, 1));
		//this.add(this.getLdirect(), cons.xywh(14, 14, 1, 1));
		//this.add(this.getCbdirect(), cons.xywh(17, 14, 3, 1));
		
	}

	// TODO add missing label keys etc. and translations, change hard coded
	// values to keys

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
			this.tfscendescript = new BHTextField(DTOScenario.Key.COMMENT, false);
		}
		return this.tfscendescript;
	}

	/**
	 * Getter method for component tfequityYeild.
	 * 
	 * @return the initialized component
	 */
	public BHTextField gettfequityYeild() {

		if (this.tfequityyield == null) {
			this.tfequityyield = new BHTextField(DTOScenario.Key.REK);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE };
			tfequityyield.setValidationRules(rules);
		}
		return this.tfequityyield;
	}

	/**
	 * Getter method for component tfdeptYield.
	 * 
	 * @return the initialized component
	 */
	public BHTextField gettfdeptYield() {

		if (this.tfdeptyield == null) {
			this.tfdeptyield = new BHTextField(DTOScenario.Key.RFK);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE };
			tfdeptyield.setValidationRules(rules);
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
			this.tftradetax = new BHTextField(DTOScenario.Key.BTAX);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE,
					VRIsBetween.BETWEEN0AND100 };
			tftradetax.setValidationRules(rules);
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
			this.tfcorporatetax = new BHTextField(DTOScenario.Key.CTAX);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE,
					VRIsBetween.BETWEEN0AND100 };
			tfcorporatetax.setValidationRules(rules);
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
	public JLabel getlpercentEquity() {

		if (this.lpercentequity == null) {
			this.lpercentequity = new JLabel("%");
		}
		return this.lpercentequity;
	}

	/**
	 * Getter method for component lpercentDept.
	 * 
	 * @return the initialized component
	 */
	public JLabel getlpercentDept() {

		if (this.lpercentdept == null) {
			this.lpercentdept = new JLabel("%");
		}
		return this.lpercentdept;
	}

	/**
	 * Getter method for component lpercentTrade.
	 * 
	 * @return the initialized component
	 */
	public JLabel getlpercentTrade() {

		if (this.lpercenttrade == null) {
			this.lpercenttrade = new JLabel("%");
		}
		return this.lpercenttrade;
	}

	/**
	 * Getter method for component lpercentCorporate.
	 * 
	 * @return the initialized component
	 */
	public JLabel getlpercentCorporate() {

		if (this.lpercentcorporate == null) {
			this.lpercentcorporate = new JLabel("%");
		}
		return this.lpercentcorporate;
	}
	
	

	// TODO remove main later
	/**
	 * Test main method.
	 */
	@SuppressWarnings("deprecation")
	public static void main(String args[]) {

		JFrame test = new JFrame("Test for ViewHeadData1");
		test.setContentPane(new BHScenarioHeadForm());
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
