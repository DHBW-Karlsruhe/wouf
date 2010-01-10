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
import org.bh.validation.VRIsDouble;
import org.bh.validation.VRIsInteger;
import org.bh.validation.VRIsPositive;
import org.bh.validation.VRMandatory;
import org.bh.validation.ValidationRule;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * This class contains the form for interval head-data
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
	
	private BHFocusTraversalPolicy focusPolicy;

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
		
		Vector<Component> order = new Vector<Component>(11);
        order.add(this.gettfscenName());
        order.add(this.gettfbaseYear());
        order.add(this.gettfscenDescript());
        order.add(this.gettfminequityyield());
        order.add(this.gettfmaxequityyield());
        order.add(this.gettfmindeptyield());
        order.add(this.gettfmaxdeptyield());
        order.add(this.gettfmintradetax());
        order.add(this.gettfmaxtradetax());
        order.add(this.gettfmincorporatetax());
        order.add(this.gettfmaxcorporatetax());
        
this.setFocusPolicy(order);
		
		this.setFocusTraversalPolicy(this.getFocusPolicy());
		this.setFocusTraversalPolicyProvider(true);

	}

	// TODO add missing label keys etc. and translations, change hard coded
	// values to keys
	
	public void setFocusPolicy(Vector<Component> order) {
		this.focusPolicy = new BHFocusTraversalPolicy(order);
}

public BHFocusTraversalPolicy getFocusPolicy() {
	if (this.focusPolicy == null){			
		this.focusPolicy = new BHFocusTraversalPolicy(null);
	}
	
	return this.focusPolicy;
}

	/**
	 * Getter method for component lmin.
	 * 
	 * @return JLabel
	 */
	public JLabel getLmin() {
		if (this.lmin == null) {
			this.lmin = new JLabel(translator.translate("min"));
		}
		return lmin;
	}

	/**
	 * Getter method for component lmax.
	 * 
	 * @return JLabel
	 */
	public JLabel getLmax() {
		if (this.lmax == null) {
			this.lmax = new JLabel(translator.translate("max"));
		}
		return lmax;
	}

	/**
	 * Getter method for component lmin1.
	 * 
	 * @return JLabel
	 */
	public JLabel getLmin1() {
		if (this.lmin1 == null) {
			this.lmin1 = new JLabel(translator.translate("min"));
		}
		return lmin1;
	}

	/**
	 * Getter method for component lmax1.
	 * 
	 * @return JLabel
	 */
	public JLabel getLmax1() {
		if (this.lmax1 == null) {
			this.lmax1 = new JLabel(translator.translate("max"));
		}
		return lmax1;
	}

	/**
	 * Getter method for component lprocess.
	 * 
	 * @return BHDescriptionLabel
	 */
	public BHDescriptionLabel getLprocess() {
		if (this.lprocess == null) {
			this.lprocess = new BHDescriptionLabel("Berechnungsart");
		}
		return lprocess;
	}

	/**
	 * Getter method for component cbprocess.
	 * 
	 * @return JComboBox
	 */
	public JComboBox getCbprocess() {
		if (this.cbprocess == null) {
			this.cbprocess = new JComboBox();
		}
		return cbprocess;
	}

	/**
	 * Getter method for component ldcfMethod.
	 * 
	 * @return BHDescriptionLabel
	 */
	public BHDescriptionLabel getlDCFmethod() {
		if (this.ldcfMethod == null)
			this.ldcfMethod = new BHDescriptionLabel(DTOScenario.Key.DCF_METHOD);
		return this.ldcfMethod;
	}

	/**
	 * Getter method for component cbdcfMethod.
	 * 
	 * @return BHComboBox
	 */
	public BHComboBox getcbDCFmethod() {
		if (this.cbdcfMethod == null) {
			this.cbdcfMethod = new BHComboBox(DTOScenario.Key.DCF_METHOD);
		}
		return this.cbdcfMethod;
	}

	/**
	 * Getter method for component ldirect.
	 * 
	 * @return BHDescriptionLabel
	 */
	public BHDescriptionLabel getLdirect() {
		if (this.ldirect == null) {
			this.ldirect = new BHDescriptionLabel("direct");
		}
		return ldirect;
	}

	/**
	 * Getter method for component cbdirect.
	 * 
	 * @return JComboBox
	 */
	public JComboBox getCbdirect() {
		if (this.cbdirect == null) {
			this.cbdirect = new JComboBox();
		}
		return cbdirect;
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

	/**
	 * Getter method for component lbaseYear.
	 * 
	 * @return BHDescriptionLabel
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
	 * Getter method for component tfbaseYear.
	 * 
	 * @return BHTextField
	 */
	public BHTextField gettfbaseYear() {

		if (this.tfbaseyear == null) {
			this.tfbaseyear = new BHTextField(DTOScenario.Key.IDENTIFIER);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsInteger.INSTANCE };
			tfbaseyear.setValidationRules(rules);
		}
		return this.tfbaseyear;
	}

	/**
	 * Getter method for component tfminequityyield.
	 * 
	 * @return BHTextField
	 */
	public BHTextField gettfminequityyield() {
		if (this.tfminequityyield == null) {
			this.tfminequityyield = new BHTextField(IBHComponent.MINVALUE + "_"
					+ DTOScenario.Key.REK);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE };
			tfminequityyield.setValidationRules(rules);
		}
		return this.tfminequityyield;
	}

	/**
	 * Getter method for component tfmindeptyield.
	 * 
	 * @return BHTextField
	 */
	public BHTextField gettfmindeptyield() {
		if (this.tfmindeptyield == null) {
			this.tfmindeptyield = new BHTextField(IBHComponent.MINVALUE + "_"
					+ DTOScenario.Key.RFK);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE };
			tfmindeptyield.setValidationRules(rules);
		}
		return this.tfmindeptyield;
	}

	/**
	 * Getter method for component tfmintradetax.
	 * 
	 * @return BHTextField
	 */
	public BHTextField gettfmintradetax() {
		if (this.tfmintradetax == null) {
			this.tfmintradetax = new BHTextField(IBHComponent.MINVALUE + "_"
					+ DTOScenario.Key.BTAX);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE };
			tfmintradetax.setValidationRules(rules);
		}
		return this.tfmintradetax;
	}

	/**
	 * Getter method for component tfmincorporatetax.
	 * 
	 * @return BHTextField
	 */
	public BHTextField gettfmincorporatetax() {
		if (this.tfmincorporatetax == null) {
			this.tfmincorporatetax = new BHTextField(IBHComponent.MINVALUE
					+ "_" + DTOScenario.Key.CTAX);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE };
			tfmincorporatetax.setValidationRules(rules);
		}
		return this.tfmincorporatetax;
	}

	/**
	 * Getter method for component tfmaxequityyield.
	 * 
	 * @return BHTextField
	 */
	public BHTextField gettfmaxequityyield() {
		if (this.tfmaxequityyield == null) {
			this.tfmaxequityyield = new BHTextField(IBHComponent.MAXVALUE + "_"
					+ DTOScenario.Key.REK);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE };
			tfmaxequityyield.setValidationRules(rules);
		}
		return this.tfmaxequityyield;
	}

	/**
	 * Getter method for component tfmaxdeptyield.
	 * 
	 * @return BHTextField
	 */
	public BHTextField gettfmaxdeptyield() {
		if (this.tfmaxdeptyield == null) {
			this.tfmaxdeptyield = new BHTextField(IBHComponent.MAXVALUE + "_"
					+ DTOScenario.Key.RFK);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE };
			tfmaxdeptyield.setValidationRules(rules);
		}
		return this.tfmaxdeptyield;
	}

	/**
	 * Getter method for component tfmaxtradetax.
	 * 
	 * @return BHTextField
	 */
	public BHTextField gettfmaxtradetax() {
		if (this.tfmaxtradetax == null) {
			this.tfmaxtradetax = new BHTextField(IBHComponent.MAXVALUE + "_"
					+ DTOScenario.Key.BTAX);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE };
			tfmaxtradetax.setValidationRules(rules);
		}
		return this.tfmaxtradetax;
	}

	/**
	 * Getter method for component tfmaxcorporatetax.
	 * 
	 * @return BHTextField
	 */
	public BHTextField gettfmaxcorporatetax() {
		if (this.tfmaxcorporatetax == null) {
			this.tfmaxcorporatetax = new BHTextField(IBHComponent.MAXVALUE
					+ "_" + DTOScenario.Key.CTAX);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE };
			tfmaxcorporatetax.setValidationRules(rules);
		}
		return this.tfmaxcorporatetax;
	}

	/**
	 * Getter method for component lpercentEquity.
	 * 
	 * @return JLabel
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
	 * @return JLabel
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
	 * @return JLabel
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
	 * @return JLabel
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
	 * @return JLabel
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
	 * @return JLabel
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
	 * @return JLabel
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
	 * @return JLabel
	 */
	public JLabel getlmaxpercentCorporate() {

		if (this.lmaxpercentcorporate == null) {
			this.lmaxpercentcorporate = new JLabel("%");
		}
		return this.lmaxpercentcorporate;
	}

	public static class BHFocusTraversalPolicy extends FocusTraversalPolicy {
		Vector<Component> order;

		public BHFocusTraversalPolicy(Vector<Component> order) {
			this.order = new Vector<Component>(order.size());
			this.order.addAll(order);
		}

		public Component getComponentAfter(Container focusCycleRoot,
				Component aComponent) {
			int idx = (order.indexOf(aComponent) + 1) % order.size();
			return order.get(idx);
		}

		public Component getComponentBefore(Container focusCycleRoot,
				Component aComponent) {
			int idx = order.indexOf(aComponent) - 1;
			if (idx < 0) {
				idx = order.size() - 1;
			}
			return order.get(idx);
		}

		public Component getDefaultComponent(Container focusCycleRoot) {
			return order.get(0);
		}

		public Component getLastComponent(Container focusCycleRoot) {
			return order.lastElement();
		}

		public Component getFirstComponent(Container focusCycleRoot) {
			return order.get(0);
		}
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
