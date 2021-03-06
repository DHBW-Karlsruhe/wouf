/*******************************************************************************
 * Copyright 2011: Matthias Beste, Hannes Bischoff, Lisa Doerner, Victor Guettler, Markus Hattenbach, Tim Herzenstiel, Günter Hesse, Jochen Hülß, Daniel Krauth, Lukas Lochner, Mark Maltring, Sven Mayer, Benedikt Nees, Alexandre Pereira, Patrick Pfaff, Yannick Rödl, Denis Roster, Sebastian Schumacher, Norman Vogel, Simon Weber 
 *
 * Copyright 2010: Anna Aichinger, Damian Berle, Patrick Dahl, Lisa Engelmann, Patrick Groß, Irene Ihl, Timo Klein, Alena Lang, Miriam Leuthold, Lukas Maciolek, Patrick Maisel, Vito Masiello, Moritz Olf, Ruben Reichle, Alexander Rupp, Daniel Schäfer, Simon Waldraff, Matthias Wurdig, Andreas Wußler
 *
 * Copyright 2009: Manuel Bross, Simon Drees, Marco Hammel, Patrick Heinz, Marcel Hockenberger, Marcus Katzor, Edgar Kauz, Anton Kharitonov, Sarah Kuhn, Michael Löckelt, Heiko Metzger, Jacqueline Missikewitz, Marcel Mrose, Steffen Nees, Alexander Roth, Sebastian Scharfenberger, Carsten Scheunemann, Dave Schikora, Alexander Schmalzhaf, Florian Schultze, Klaus Thiele, Patrick Tietze, Robert Vollmer, Norman Weisenburger, Lars Zuckschwerdt
 *
 * Copyright 2008: Camil Bartetzko, Tobias Bierer, Lukas Bretschneider, Johannes Gilbert, Daniel Huser, Christopher Kurschat, Dominik Pfauntsch, Sandra Rath, Daniel Weber
 *
 * This program is free software: you can redistribute it and/or modify it un-der the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT-NESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package org.bh.gui.swing.forms;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import org.bh.data.DTOScenario;
import org.bh.gui.IBHComponent;
import org.bh.gui.swing.comp.BHComboBox;
import org.bh.gui.swing.comp.BHDescriptionLabel;
import org.bh.gui.swing.comp.BHPercentTextField;
import org.bh.gui.swing.comp.BHTextField;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;
import org.bh.validation.VRIsDouble;
import org.bh.validation.VRIsGreaterThan;
import org.bh.validation.VRIsInteger;
import org.bh.validation.VRIsLowerThan;
import org.bh.validation.VRIsPositive;
import org.bh.validation.VRMandatory;
import org.bh.validation.ValidationRule;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * This class contains the form for interval head-data of a scenario
 * 
 * @author Anton Kharitonov
 * @author Patrick Heinz
 * @version 1.0, 22.01.2010
 * 
 */
@SuppressWarnings("serial")
public final class BHScenarioHeadIntervalForm extends JPanel {
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
	
	private BHDescriptionLabel lPeriodType;
	private BHComboBox cmbPeriodType;

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

		String rowDef = "4px,p,4px,p,4px,p,4px,p,20px,p,4px,p,4px,p,20px,p,4px";
		String colDef = "4px,right:pref,4px,max(80px;pref),4px,left:pref,4px,pref,4px,pref,4px,left:pref,24px,4px,right:pref,4px,pref,4px,left:pref,4px,pref,4px,pref,4px,left:pref,4px:grow,pref,4px,80px,4px";

		FormLayout layout = new FormLayout(colDef, rowDef);
		this.setLayout(layout);

		layout.setColumnGroups(new int[][] { { 4, 10, 17, 23 } });

		CellConstraints cons = new CellConstraints();

		this.add(this.getlscenName(), cons.xywh(2, 4, 1, 1));
		this.add(this.gettfscenName(), cons.xywh(4, 4, 12, 1));
		this.add(this.getlbaseYear(), cons.xywh(27, 4, 1, 1, "right, default"));
		this.add(this.gettfbaseYear(), cons.xywh(29, 4, 1, 1));
		this.add(this.getlscenDescript(), cons.xywh(2, 6, 1, 1));
		this.add(this.gettfscenDescript(), cons.xywh(4, 6, 26, 1));

		this.add(new JSeparator(SwingConstants.VERTICAL), cons
				.xywh(8, 10, 1, 5));
		this.add(this.getLmin(), cons.xywh(4, 10, 1, 1, "center, default"));
		this.add(this.getLmax(), cons.xywh(10, 10, 1, 1, "center, default"));
		this.add(new JSeparator(SwingConstants.VERTICAL), cons.xywh(21, 10, 1,
				5));
		this.add(this.getLmin1(), cons.xywh(17, 10, 1, 1, "center, default"));
		this.add(this.getLmax1(), cons.xywh(23, 10, 1, 1, "center, default"));

		this.add(this.getlequityYield(), cons.xywh(2, 12, 1, 1));
		this.add(this.getldeptYield(), cons.xywh(2, 14, 1, 1));
		this.add(this.getltradeTax(), cons.xywh(15, 12, 1, 1));
		this.add(this.getlcorporateTax(), cons.xywh(15, 14, 1, 1));

		this.add(this.gettfminequityyield(), cons.xywh(4, 12, 1, 1));
		this.add(this.gettfmindeptyield(), cons.xywh(4, 14, 1, 1));
		this.add(this.gettfmintradetax(), cons.xywh(17, 12, 1, 1));
		this.add(this.gettfmincorporatetax(), cons.xywh(17, 14, 1, 1));

		this.add(this.gettfmaxequityyield(), cons.xywh(10, 12, 1, 1));
		this.add(this.gettfmaxdeptyield(), cons.xywh(10, 14, 1, 1));
		this.add(this.gettfmaxtradetax(), cons.xywh(23, 12, 1, 1));
		this.add(this.gettfmaxcorporatetax(), cons.xywh(23, 14, 1, 1));

		this.add(this.getlminpercentEquity(), cons.xywh(6, 12, 1, 1));
		this.add(this.getlminpercentDept(), cons.xywh(6, 14, 1, 1));
		this.add(this.getlminpercentTrade(), cons.xywh(19, 12, 1, 1));
		this.add(this.getlminpercentCorporate(), cons.xywh(19, 14, 1, 1));

		this.add(this.getlmaxpercentEquity(), cons.xywh(12, 12, 1, 1));
		this.add(this.getlmaxpercentDept(), cons.xywh(12, 14, 1, 1));
		this.add(this.getlmaxpercentTrade(), cons.xywh(25, 12, 1, 1));
		this.add(this.getlmaxpercentCorporate(), cons.xywh(25, 14, 1, 1));
		
		this.add(this.getlPeriodType(), cons.xywh(2, 16, 1, 1));
		this.add(this.getCmbPeriodType(), cons.xywh(4, 16, 12, 1, "left, default"));
		/*
		 * this Vector sets the order of focus moovement.
		 */
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
			this.tfbaseyear = new BHTextField(DTOScenario.Key.IDENTIFIER, false);
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
			this.tfminequityyield = new BHPercentTextField(IBHComponent.MINVALUE
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
			this.tfmindeptyield = new BHPercentTextField(IBHComponent.MINVALUE
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
			this.tfmintradetax = new BHPercentTextField(IBHComponent.MINVALUE
					+ DTOScenario.Key.BTAX);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE,
					new VRIsLowerThan(100, true) };
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
			this.tfmincorporatetax = new BHPercentTextField(IBHComponent.MINVALUE
					+ DTOScenario.Key.CTAX);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE,
					new VRIsLowerThan(100, true) };
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
			this.tfmaxequityyield = new BHPercentTextField(IBHComponent.MAXVALUE
					+ DTOScenario.Key.REK);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE,
					new VRIsGreaterThan(gettfminequityyield(), true) };
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
			this.tfmaxdeptyield = new BHPercentTextField(IBHComponent.MAXVALUE
					+ DTOScenario.Key.RFK);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE,
					new VRIsGreaterThan(gettfmindeptyield(), true) };
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
			this.tfmaxtradetax = new BHPercentTextField(IBHComponent.MAXVALUE
					+ DTOScenario.Key.BTAX);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE,
					new VRIsGreaterThan(gettfmintradetax(), true),
					new VRIsLowerThan(100, true) };
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
			this.tfmaxcorporatetax = new BHPercentTextField(IBHComponent.MAXVALUE
					+ DTOScenario.Key.CTAX);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE,
					new VRIsGreaterThan(gettfmaxcorporatetax(), true),
					new VRIsLowerThan(100, true) };
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

	public static class BHFocusTraversalPolicy extends FocusTraversalPolicy {
		Vector<Component> order;

		public BHFocusTraversalPolicy(Vector<Component> order) {
			this.order = new Vector<Component>(order.size());
			this.order.addAll(order);
		}

		@Override
		public Component getComponentAfter(Container focusCycleRoot,
				Component aComponent) {
			int idx = (order.indexOf(aComponent) + 1) % order.size();
			return order.get(idx);
		}

		@Override
		public Component getComponentBefore(Container focusCycleRoot,
				Component aComponent) {
			int idx = order.indexOf(aComponent) - 1;
			if (idx < 0) {
				idx = order.size() - 1;
			}
			return order.get(idx);
		}

		@Override
		public Component getDefaultComponent(Container focusCycleRoot) {
			return order.get(0);
		}

		@Override
		public Component getLastComponent(Container focusCycleRoot) {
			return order.lastElement();
		}

		@Override
		public Component getFirstComponent(Container focusCycleRoot) {
			return order.get(0);
		}
	}
}
