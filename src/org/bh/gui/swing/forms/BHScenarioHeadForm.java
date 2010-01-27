package org.bh.gui.swing.forms;

import java.awt.Component;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.bh.data.DTOScenario;
import org.bh.gui.swing.comp.BHComboBox;
import org.bh.gui.swing.comp.BHDescriptionLabel;
import org.bh.gui.swing.comp.BHPercentTextField;
import org.bh.gui.swing.comp.BHTextField;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;
import org.bh.validation.VRIsDouble;
import org.bh.validation.VRIsInteger;
import org.bh.validation.VRIsLowerThan;
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
 * @version 1.0, 22.01.2010
 * 
 */
@SuppressWarnings("serial")
public final class BHScenarioHeadForm extends JPanel {
	private BHDescriptionLabel lscenname;
	private BHDescriptionLabel lscendescript;
	private BHDescriptionLabel lequityyield;
	private BHDescriptionLabel ldeptyield;
	private BHDescriptionLabel ltradetax;
	private BHDescriptionLabel lcorporatetax;

	private BHTextField tfscenname;
	private BHTextField tfscendescript;
	//fields show percentage as %
	private BHTextField tfequityyield;
	private BHTextField tfdeptyield;
	private BHTextField tftradetax;
	private BHTextField tfcorporatetax;
        
	private JLabel lpercentequity;
	private JLabel lpercentdept;
	private JLabel lpercenttrade;
	private JLabel lpercentcorporate;
	
	private BHDescriptionLabel lPeriodType;
	private BHComboBox cmbPeriodType;

	private BHFocusTraversalPolicy focusPolicy;

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

		String rowDef = "4px,p,4px,p,4px,p,4px,p,20px,p,4px,p,4px,p,20px,p,4px";
		String colDef = "4px,4px,right:pref,4px,pref,max(80px;default),4px,left:pref,24px,pref,4px,pref,4px,right:pref,4px,pref,pref,4px,pref,4px:grow,pref,4px,80px,4px";

		FormLayout layout = new FormLayout(colDef, rowDef);
		this.setLayout(layout);

		layout.setColumnGroups(new int[][] { { 6, 17 } });

		CellConstraints cons = new CellConstraints();

		this.add(this.getlscenName(), cons.xywh(3, 4, 1, 1));
		this.add(this.gettfscenName(), cons.xywh(6, 4,12, 1));
		this.add(this.getlscenDescript(), cons.xywh(3, 6, 1, 1));
		this.add(this.gettfscenDescript(), cons.xywh(6, 6, 18, 1));

		this.add(this.getlequityYield(), cons.xywh(3, 12, 1, 1));
		this.add(this.getldeptYield(), cons.xywh(3, 14, 1, 1));
		this.add(this.getltradeTax(), cons.xywh(14, 12, 1, 1));
		this.add(this.getlcorporateTax(), cons.xywh(14, 14, 1, 1));
		this.add(this.gettfequityYield(), cons.xywh(6, 12, 1, 1));
		this.add(this.gettfdeptYield(), cons.xywh(6, 14, 1, 1));
		this.add(this.gettftradeTax(), cons.xywh(17, 12, 1, 1));
		this.add(this.gettfcorporateTax(), cons.xywh(17, 14, 1, 1));
		this.add(this.getlpercentEquity(), cons.xywh(8, 12, 1, 1));
		this.add(this.getlpercentDept(), cons.xywh(8, 14, 1, 1));
		this.add(this.getlpercentTrade(), cons.xywh(19, 12, 1, 1));
		this.add(this.getlpercentCorporate(), cons.xywh(19, 14, 1, 1));
		
		this.add(this.getlPeriodType(), cons.xywh(3, 16, 1, 1));
		this.add(this.getCmbPeriodType(), cons.xywh(6, 16, 12, 1, "left, default"));

		Vector<Component> order = new Vector<Component>();
		order.add(this.gettfscenName());
		order.add(this.gettfscenDescript());
		order.add(this.gettfequityYield());
		order.add(this.gettfdeptYield());
		order.add(this.gettftradeTax());
		order.add(this.gettfcorporateTax());
		order.add(this.getCmbPeriodType());

		this.setFocusPolicy(order);

		this.setFocusTraversalPolicy(this.getFocusPolicy());
		this.setFocusTraversalPolicyProvider(true);
	}

	public void setFocusPolicy(Vector<Component> order) {
		this.focusPolicy = new BHFocusTraversalPolicy(order);
	}

	public BHFocusTraversalPolicy getFocusPolicy() {
		if (this.focusPolicy == null) {
			this.focusPolicy = new BHFocusTraversalPolicy(null);
		}

		return this.focusPolicy;
	}

	/**
	 * Getter method for component lscenName.
	 * 
	 * @return BHDescriptionLabel
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
	 * @return BHDescriptionLabel
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
	 * @return BHDescriptionLabel
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
	 * @return BHDescriptionLabel
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
	 * @return BHDescriptionLabel
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
	 * @return BHDescriptionLabel
	 */
	public BHDescriptionLabel getlcorporateTax() {

		if (this.lcorporatetax == null) {
			this.lcorporatetax = new BHDescriptionLabel(DTOScenario.Key.CTAX);
		}

		return this.lcorporatetax;
	}

	

	// Here do the getters for the textfields begin

	/**
	 * Getter method for component tfscenName.
	 * 
	 * @return BHTextField
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
	 * @return BHTextField
	 */
	public BHTextField gettfscenDescript() {

		if (this.tfscendescript == null) {
			this.tfscendescript = new BHTextField(DTOScenario.Key.COMMENT,
					false);
		}
		return this.tfscendescript;
	}

	/**
	 * Getter method for component tfequityYeild.
	 * 
	 * @return BHTextField
	 */
	public BHTextField gettfequityYield() {

		if (this.tfequityyield == null) {
			this.tfequityyield = new BHPercentTextField(DTOScenario.Key.REK);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE };
			tfequityyield.setValidationRules(rules);
		}
		return this.tfequityyield;
	}

	/**
	 * Getter method for component tfdeptYield.
	 * 
	 * @return BHTextField
	 */
	public BHTextField gettfdeptYield() {

		if (this.tfdeptyield == null) {
			this.tfdeptyield = new BHPercentTextField(DTOScenario.Key.RFK);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE };
			tfdeptyield.setValidationRules(rules);
		}
		return this.tfdeptyield;
	}

	/**
	 * Getter method for component tftradeTax.
	 * 
	 * @return BHTextField
	 */
	public BHTextField gettftradeTax() {

		if (this.tftradetax == null) {
			this.tftradetax = new BHPercentTextField(DTOScenario.Key.BTAX);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE,
					new VRIsLowerThan(100, true) };
			tftradetax.setValidationRules(rules);
		}
		return this.tftradetax;
	}

	/**
	 * Getter method for component tfcorporateTax.
	 * 
	 * @return BHTextField
	 */
	public BHTextField gettfcorporateTax() {

		if (this.tfcorporatetax == null) {
			this.tfcorporatetax = new BHPercentTextField(DTOScenario.Key.CTAX);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE,
					new VRIsLowerThan(100, true) };
			tfcorporatetax.setValidationRules(rules);
		}
		return this.tfcorporatetax;
	}

	/**
	 * Getter method for component lpercentEquity.
	 * 
	 * @return JLabel
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
	 * @return JLabel
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
	 * @return JLabel
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
	 * @return JLabel
	 */
	public JLabel getlpercentCorporate() {

		if (this.lpercentcorporate == null) {
			this.lpercentcorporate = new JLabel("%");
		}
		return this.lpercentcorporate;
	}

	/**
	 * Getter method for component lPeriodType.
	 * 
	 * @return BHDescriptionLabel
	 */
	public BHDescriptionLabel getlPeriodType() {
		if (lPeriodType == null) {
			lPeriodType = new BHDescriptionLabel(DTOScenario.Key.PERIOD_TYPE);
		}
		return lPeriodType;
	}

	/**
	 * Getter method for component CmbPeriodType.
	 * 
	 * @return BHComboBox
	 */
	public BHComboBox getCmbPeriodType() {
		if (cmbPeriodType == null) {
			cmbPeriodType = new BHComboBox(DTOScenario.Key.PERIOD_TYPE);
		}
		return cmbPeriodType;
	}
}
