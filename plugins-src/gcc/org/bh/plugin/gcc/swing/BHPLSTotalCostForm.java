/*******************************************************************************
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
package org.bh.plugin.gcc.swing;

import java.awt.Component;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import org.bh.gui.IBHComponent;
import org.bh.gui.swing.comp.BHDescriptionLabel;
import org.bh.gui.swing.comp.BHTextField;
import org.bh.gui.swing.forms.BHFocusTraversalPolicy;
import org.bh.gui.swing.forms.border.BHBorderFactory;
import org.bh.platform.i18n.BHTranslator;
import org.bh.platform.i18n.ITranslator;
import org.bh.plugin.gcc.data.DTOGCCProfitLossStatementTotalCost;
import org.bh.validation.VRIsDouble;
import org.bh.validation.VRIsGreaterThan;
import org.bh.validation.VRMandatory;
import org.bh.validation.ValidationRule;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * This class contains the total cost form for the plugin
 * 
 * @author Anton Kharitonov
 * @author Patrick Heinz
 * @author Patrick Maisel
 * @version 0.1, 08.01.2010
 * @version 0.2, 16.12.2010
 * 
 *          0.2: Form wurde komplett übersetzbar gemacht von Patrick Maisel
 */
@SuppressWarnings("serial")
public class BHPLSTotalCostForm extends JPanel {

	private BHTextField tfUE;
	private BHTextField tfABSCH;
	private BHTextField tfSBA;
	private BHTextField tfSBE;
	private BHTextField tfMA;
	private BHTextField tfPA;
	private BHTextField tfZA;
	private BHTextField tfAAE;
	private BHTextField tfUEmax;
	private BHTextField tfABSCHmax;
	private BHTextField tfSBAmax;
	private BHTextField tfSBEmax;
	private BHTextField tfAAEmax;
	private BHTextField tfAAEmin;
	private BHTextField tfMAmin;
	private BHTextField tfMAmax;
	private BHTextField tfPAmin;
	private BHTextField tfPAmax;
	private BHTextField tfZAmin;
	private BHTextField tfZAmax;
	private BHTextField tfUEmin;
	private BHTextField tfABSCHmin;
	private BHTextField tfSBAmin;
	private BHTextField tfSBEmin;

	private BHDescriptionLabel lUE;
	private BHDescriptionLabel lABSCH;
	private BHDescriptionLabel lSBA;
	private BHDescriptionLabel lSBE;
	private BHDescriptionLabel lMA;
	private BHDescriptionLabel lPA;
	private BHDescriptionLabel lAAE;
	private BHDescriptionLabel lZA;
	// Hilfslabel ... wird ständig überschrieben, aber ist nicht kritisch, da es
	// nur für die Anzeige auf dem Panel wichtig ist
	private BHDescriptionLabel MU;

	private JLabel lmin;
	private JLabel lmax;

	final ITranslator translator = BHTranslator.getInstance();

	public enum Key {

		PLS_TotalCost;

		public String toString() {
			return getClass().getName() + "." + super.toString();
		}

	}

	public BHPLSTotalCostForm(boolean intervalArithmetic) {
		this.initialize(intervalArithmetic);
	}

	public void initialize(boolean intervalArithmetic) {
		String rowDef = "4px,p,4px,p,4px,p,4px,p,4px,p,4px,p,4px";
		String colDef = "4px,right:pref,4px,max(100px;pref):grow,4px,pref,4px";
		if (intervalArithmetic) {
			rowDef = "4px,p," + rowDef;
			colDef += ",max(100px;pref):grow,4px,pref,4px";
		}
		colDef = colDef + colDef.replaceFirst("4px", "");
		FormLayout layout = new FormLayout(colDef, rowDef);
		this.setLayout(layout);

		CellConstraints cons = new CellConstraints();

		if (!intervalArithmetic) {

			layout.setColumnGroups(new int[][] { { 4, 10 } });

			this.add(this.getLUE(), cons.xywh(2, 2, 1, 1));
			this.add(this.getLSBE(), cons.xywh(2, 4, 1, 1));
			this.add(this.getLAAE(), cons.xywh(2, 6, 1, 1));
			this.add(this.getLMA(), cons.xywh(8, 2, 1, 1));
			this.add(this.getLPA(), cons.xywh(8, 4, 1, 1));
			this.add(this.getLABSCH(), cons.xywh(8, 6, 1, 1));
			this.add(this.getLSBA(), cons.xywh(2, 8, 1, 1));
			this.add(this.getLZA(), cons.xywh(2, 10, 1, 1));
			// Das Adden von JLabels für die Währungsanzeige wurde durch die
			// Methode getMU() ersetzt. Dadurch wird das Formular übersetzbar
			this.add(this.getTfUE(), cons.xywh(4, 2, 1, 1));
			this.add(this.getMU(), cons.xywh(6, 2, 1, 1));
			this.add(this.getTfSBE(), cons.xywh(4, 4, 1, 1));
			this.add(this.getMU(), cons.xywh(6, 4, 1, 1));
			this.add(this.getTfAAE(), cons.xywh(4, 6, 1, 1));
			this.add(this.getMU(), cons.xywh(6, 6, 1, 1));
			this.add(this.getTfMA(), cons.xywh(10, 2, 1, 1));
			this.add(this.getMU(), cons.xywh(12, 2, 1, 1));
			this.add(this.getTfPA(), cons.xywh(10, 4, 1, 1));
			this.add(this.getMU(), cons.xywh(12, 4, 1, 1));
			this.add(this.getTfABSCH(), cons.xywh(10, 6, 1, 1));
			this.add(this.getMU(), cons.xywh(12, 6, 1, 1));
			this.add(this.getTfSBA(), cons.xywh(4, 8, 1, 1, "fill, default"));
			this.add(this.getMU(), cons.xywh(6, 8, 1, 1));
			this.add(this.getTfZA(), cons.xywh(4, 10, 1, 1, "fill, default"));
			this.add(this.getMU(), cons.xywh(6, 10, 1, 1));

			layout.insertColumn(8, ColumnSpec.decode("10px"));
			layout.insertColumn(9, ColumnSpec.decode("4px"));
			this.add(new JSeparator(SwingConstants.VERTICAL),
					cons.xywh(8, 2, 1, 5));

			layout.insertRow(8, RowSpec.decode("10px"));
			layout.insertRow(9, RowSpec.decode("4px"));
			this.add(new JSeparator(SwingConstants.HORIZONTAL),
					cons.xywh(2, 8, 13, 1));

			Vector<Component> order = new Vector<Component>(16);
			order.add(this.getTfUE());
			order.add(this.getTfSBE());
			order.add(this.getTfAAE());
			order.add(this.getTfMA());
			order.add(this.getTfPA());
			order.add(this.getTfABSCH());
			order.add(this.getTfSBA());
			order.add(this.getTfZA());

			BHFocusTraversalPolicy fpolicy = new BHFocusTraversalPolicy(order);

			this.setFocusTraversalPolicy(fpolicy);
			this.setFocusTraversalPolicyProvider(true);

		} else {
			layout.setColumnGroups(new int[][] { { 4, 8 } });

			this.add(this.getLUE(), cons.xywh(2, 4, 1, 1));
			this.add(this.getLSBE(), cons.xywh(2, 6, 1, 1));
			this.add(this.getLAAE(), cons.xywh(2, 8, 1, 1));
			this.add(this.getLMA(), cons.xywh(12, 4, 1, 1));
			this.add(this.getLPA(), cons.xywh(12, 6, 1, 1));
			this.add(this.getLABSCH(), cons.xywh(12, 8, 1, 1));
			this.add(this.getLSBA(), cons.xywh(2, 10, 1, 1));
			this.add(this.getLZA(), cons.xywh(2, 12, 1, 1));

			// this.add(new JSeparator(SwingConstants.VERTICAL),
			// cons.xywh(6, 2,
			// 1, 9));
			this.add(this.getLmin(), cons.xywh(4, 2, 1, 1, "center,default"));
			this.add(this.getLmax(), cons.xywh(8, 2, 1, 1, "center,default"));
			this.add(new JLabel(translator.translate("min")),
					cons.xywh(14, 2, 1, 1, "center,default"));
			this.add(new JLabel(translator.translate("max")),
					cons.xywh(18, 2, 1, 1, "center,default"));

			this.add(this.getTfUEmin(), cons.xywh(4, 4, 1, 1));
			this.add(this.getMU(), cons.xywh(6, 4, 1, 1));
			this.add(this.getTfSBEmin(), cons.xywh(4, 6, 1, 1));
			this.add(this.getMU(), cons.xywh(6, 6, 1, 1));
			this.add(this.getTfAAEmin(), cons.xywh(4, 8, 1, 1));
			this.add(this.getMU(), cons.xywh(6, 8, 1, 1));
			this.add(this.getTfMAmin(), cons.xywh(14, 4, 1, 1));
			this.add(this.getMU(), cons.xywh(16, 4, 1, 1));

			this.add(this.getTfPAmin(), cons.xywh(14, 6, 1, 1));
			this.add(this.getMU(), cons.xywh(16, 6, 1, 1));

			this.add(this.getTfABSCHmin(), cons.xywh(14, 8, 1, 1));
			this.add(this.getMU(), cons.xywh(16, 8, 1, 1));

			this.add(this.getTfSBAmin(), cons.xywh(4, 10, 1, 1));
			this.add(this.getMU(), cons.xywh(6, 10, 1, 1));

			this.add(this.getTfZAmin(), cons.xywh(4, 12, 1, 1));
			this.add(this.getMU(), cons.xywh(6, 12, 1, 1));

			this.add(this.getTfUEmax(), cons.xywh(8, 4, 1, 1));
			this.add(this.getMU(), cons.xywh(10, 4, 1, 1));

			this.add(this.getTfSBEmax(), cons.xywh(8, 6, 1, 1));
			this.add(this.getMU(), cons.xywh(10, 6, 1, 1));

			this.add(this.getTfAAEmax(), cons.xywh(8, 8, 1, 1));
			this.add(this.getMU(), cons.xywh(10, 8, 1, 1));

			this.add(this.getTfMAmax(), cons.xywh(18, 4, 1, 1));
			this.add(this.getMU(), cons.xywh(20, 4, 1, 1));

			this.add(this.getTfPAmax(), cons.xywh(18, 6, 1, 1));
			this.add(this.getMU(), cons.xywh(20, 6, 1, 1));

			this.add(this.getTfABSCHmax(), cons.xywh(18, 8, 1, 1));
			this.add(this.getMU(), cons.xywh(20, 8, 1, 1));

			this.add(this.getTfSBAmax(), cons.xywh(8, 10, 1, 1));
			this.add(this.getMU(), cons.xywh(10, 10, 1, 1));

			this.add(this.getTfZAmax(), cons.xywh(8, 12, 1, 1));
			this.add(this.getMU(), cons.xywh(10, 12, 1, 1));

			layout.insertColumn(12, ColumnSpec.decode("10px"));
			layout.insertColumn(13, ColumnSpec.decode("4px"));
			this.add(new JSeparator(SwingConstants.VERTICAL),
					cons.xywh(12, 4, 1, 5));

			layout.insertRow(10, RowSpec.decode("10px"));
			layout.insertRow(11, RowSpec.decode("4px"));
			this.add(new JSeparator(SwingConstants.HORIZONTAL),
					cons.xywh(2, 10, 21, 1));
			/*
			 * this Vector sets the order of focus moovement.
			 */
			Vector<Component> order = new Vector<Component>(16);
			order.add(this.getTfUEmin());
			order.add(this.getTfUEmax());
			order.add(this.getTfSBEmin());
			order.add(this.getTfSBEmax());
			order.add(this.getTfAAEmin());
			order.add(this.getTfAAEmax());
			order.add(this.getTfMAmin());
			order.add(this.getTfMAmax());
			order.add(this.getTfPAmin());
			order.add(this.getTfPAmax());
			order.add(this.getTfABSCHmin());
			order.add(this.getTfABSCHmax());
			order.add(this.getTfSBAmin());
			order.add(this.getTfSBAmax());
			order.add(this.getTfZAmin());
			order.add(this.getTfZAmax());

			BHFocusTraversalPolicy fpolicy = new BHFocusTraversalPolicy(order);

			this.setFocusTraversalPolicy(fpolicy);
			this.setFocusTraversalPolicyProvider(true);
		}

		this.setBorder(BHBorderFactory.getInstacnce().createTitledBorder(
				BHBorderFactory.getInstacnce().createEtchedBorder(
						EtchedBorder.LOWERED),
				BHPLSTotalCostForm.Key.PLS_TotalCost));
	}

	// Here do the getters for the textfields begin

	public BHTextField getTfUE() {
		if (tfUE == null) {
			tfUE = new BHTextField(DTOGCCProfitLossStatementTotalCost.Key.UE);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfUE.setValidationRules(rules);
		}
		return tfUE;
	}

	public BHTextField getTfABSCH() {
		if (tfABSCH == null) {
			tfABSCH = new BHTextField(
					DTOGCCProfitLossStatementTotalCost.Key.ABSCH);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfABSCH.setValidationRules(rules);
		}
		return tfABSCH;
	}

	public BHTextField getTfSBA() {
		if (tfSBA == null) {
			tfSBA = new BHTextField(DTOGCCProfitLossStatementTotalCost.Key.SBA);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfSBA.setValidationRules(rules);
		}
		return tfSBA;
	}

	public BHTextField getTfSBE() {
		if (tfSBE == null) {
			tfSBE = new BHTextField(DTOGCCProfitLossStatementTotalCost.Key.SBE);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfSBE.setValidationRules(rules);
		}
		return tfSBE;
	}

	public BHTextField getTfMA() {
		if (tfMA == null) {
			tfMA = new BHTextField(DTOGCCProfitLossStatementTotalCost.Key.MA);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfMA.setValidationRules(rules);
		}
		return tfMA;
	}

	public BHTextField getTfPA() {
		if (tfPA == null) {
			tfPA = new BHTextField(DTOGCCProfitLossStatementTotalCost.Key.PA);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfPA.setValidationRules(rules);
		}
		return tfPA;
	}

	public BHTextField getTfAAE() {
		if (tfAAE == null) {
			tfAAE = new BHTextField(DTOGCCProfitLossStatementTotalCost.Key.AAE);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfAAE.setValidationRules(rules);
		}
		return tfAAE;
	}

	public BHTextField getTfZA() {
		if (tfZA == null) {
			tfZA = new BHTextField(DTOGCCProfitLossStatementTotalCost.Key.ZA);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfZA.setValidationRules(rules);
		}
		return tfZA;
	}

	public BHTextField getTfUEmax() {
		if (tfUEmax == null) {
			tfUEmax = new BHTextField(IBHComponent.MAXVALUE
					+ DTOGCCProfitLossStatementTotalCost.Key.UE);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE,
					new VRIsGreaterThan(getTfUEmin(), true) };
			tfUEmax.setValidationRules(rules);
		}
		return tfUEmax;
	}

	public BHTextField getTfABSCHmax() {
		if (tfABSCHmax == null) {
			tfABSCHmax = new BHTextField(IBHComponent.MAXVALUE
					+ DTOGCCProfitLossStatementTotalCost.Key.ABSCH);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE,
					new VRIsGreaterThan(getTfABSCHmin(), true) };
			tfABSCHmax.setValidationRules(rules);
		}
		return tfABSCHmax;
	}

	public BHTextField getTfSBAmax() {
		if (tfSBAmax == null) {
			tfSBAmax = new BHTextField(IBHComponent.MAXVALUE
					+ DTOGCCProfitLossStatementTotalCost.Key.SBA);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE,
					new VRIsGreaterThan(getTfSBAmin(), true) };
			tfSBAmax.setValidationRules(rules);
		}
		return tfSBAmax;
	}

	public BHTextField getTfSBEmax() {
		if (tfSBEmax == null) {
			tfSBEmax = new BHTextField(IBHComponent.MAXVALUE
					+ DTOGCCProfitLossStatementTotalCost.Key.SBE);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE,
					new VRIsGreaterThan(getTfSBEmin(), true) };
			tfSBEmax.setValidationRules(rules);
		}
		return tfSBEmax;
	}

	public BHTextField getTfAAEmax() {
		if (tfAAEmax == null) {
			tfAAEmax = new BHTextField(IBHComponent.MAXVALUE
					+ DTOGCCProfitLossStatementTotalCost.Key.AAE);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE,
					new VRIsGreaterThan(getTfAAEmin(), true) };
			tfAAEmax.setValidationRules(rules);
		}
		return tfAAEmax;
	}

	public BHTextField getTfPAmax() {
		if (tfPAmax == null) {
			tfPAmax = new BHTextField(IBHComponent.MAXVALUE
					+ DTOGCCProfitLossStatementTotalCost.Key.PA);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE,
					new VRIsGreaterThan(getTfPAmin(), true) };
			tfPAmax.setValidationRules(rules);
		}
		return tfPAmax;
	}

	public BHTextField getTfMAmax() {
		if (tfMAmax == null) {
			tfMAmax = new BHTextField(IBHComponent.MAXVALUE
					+ DTOGCCProfitLossStatementTotalCost.Key.MA);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE,
					new VRIsGreaterThan(getTfMAmin(), true) };
			tfMAmax.setValidationRules(rules);
		}
		return tfMAmax;
	}

	public BHTextField getTfZAmax() {
		if (tfZAmax == null) {
			tfZAmax = new BHTextField(IBHComponent.MAXVALUE
					+ DTOGCCProfitLossStatementTotalCost.Key.ZA);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE,
					new VRIsGreaterThan(getTfZAmin(), true) };
			tfZAmax.setValidationRules(rules);
		}
		return tfZAmax;
	}

	public BHTextField getTfUEmin() {
		if (tfUEmin == null) {
			tfUEmin = new BHTextField(IBHComponent.MINVALUE
					+ DTOGCCProfitLossStatementTotalCost.Key.UE);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfUEmin.setValidationRules(rules);
		}
		return tfUEmin;
	}

	public BHTextField getTfABSCHmin() {
		if (tfABSCHmin == null) {
			tfABSCHmin = new BHTextField(IBHComponent.MINVALUE
					+ DTOGCCProfitLossStatementTotalCost.Key.ABSCH);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfABSCHmin.setValidationRules(rules);
		}
		return tfABSCHmin;
	}

	public BHTextField getTfSBAmin() {
		if (tfSBAmin == null) {
			tfSBAmin = new BHTextField(IBHComponent.MINVALUE
					+ DTOGCCProfitLossStatementTotalCost.Key.SBA);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfSBAmin.setValidationRules(rules);
		}
		return tfSBAmin;
	}

	public BHTextField getTfSBEmin() {
		if (tfSBEmin == null) {
			tfSBEmin = new BHTextField(IBHComponent.MINVALUE
					+ DTOGCCProfitLossStatementTotalCost.Key.SBE);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfSBEmin.setValidationRules(rules);
		}
		return tfSBEmin;
	}

	public BHTextField getTfAAEmin() {
		if (tfAAEmin == null) {
			tfAAEmin = new BHTextField(IBHComponent.MINVALUE
					+ DTOGCCProfitLossStatementTotalCost.Key.AAE);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfAAEmin.setValidationRules(rules);
		}
		return tfAAEmin;
	}

	public BHTextField getTfPAmin() {
		if (tfPAmin == null) {
			tfPAmin = new BHTextField(IBHComponent.MINVALUE
					+ DTOGCCProfitLossStatementTotalCost.Key.PA);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfPAmin.setValidationRules(rules);
		}
		return tfPAmin;
	}

	public BHTextField getTfMAmin() {
		if (tfMAmin == null) {
			tfMAmin = new BHTextField(IBHComponent.MINVALUE
					+ DTOGCCProfitLossStatementTotalCost.Key.MA);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfMAmin.setValidationRules(rules);
		}
		return tfMAmin;
	}

	public BHTextField getTfZAmin() {
		if (tfZAmin == null) {
			tfZAmin = new BHTextField(IBHComponent.MINVALUE
					+ DTOGCCProfitLossStatementTotalCost.Key.ZA);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfZAmin.setValidationRules(rules);
		}
		return tfZAmin;
	}

	// Here do the getters for the labels begin

	// Getter-Methode, damit die Währungslabels dynamisch übersetzbar werden
	public BHDescriptionLabel getMU() {
		MU = new BHDescriptionLabel("currency");

		return MU;
	}

	public BHDescriptionLabel getLUE() {
		if (lUE == null) {
			lUE = new BHDescriptionLabel(
					DTOGCCProfitLossStatementTotalCost.Key.UE);
		}
		return lUE;
	}

	public BHDescriptionLabel getLABSCH() {
		if (lABSCH == null) {
			lABSCH = new BHDescriptionLabel(
					DTOGCCProfitLossStatementTotalCost.Key.ABSCH);
		}
		return lABSCH;
	}

	public BHDescriptionLabel getLSBA() {
		if (lSBA == null) {
			lSBA = new BHDescriptionLabel(
					DTOGCCProfitLossStatementTotalCost.Key.SBA);
		}
		return lSBA;
	}

	public BHDescriptionLabel getLSBE() {
		if (lSBE == null) {
			lSBE = new BHDescriptionLabel(
					DTOGCCProfitLossStatementTotalCost.Key.SBE);
		}
		return lSBE;
	}

	public BHDescriptionLabel getLMA() {
		if (lMA == null) {
			lMA = new BHDescriptionLabel(
					DTOGCCProfitLossStatementTotalCost.Key.MA);
		}
		return lMA;
	}

	public BHDescriptionLabel getLPA() {
		if (lPA == null) {
			lPA = new BHDescriptionLabel(
					DTOGCCProfitLossStatementTotalCost.Key.PA);
		}
		return lPA;
	}

	public BHDescriptionLabel getLAAE() {
		if (lAAE == null) {
			lAAE = new BHDescriptionLabel(
					DTOGCCProfitLossStatementTotalCost.Key.AAE);
		}
		return lAAE;
	}

	public BHDescriptionLabel getLZA() {
		if (lZA == null) {
			lZA = new BHDescriptionLabel(
					DTOGCCProfitLossStatementTotalCost.Key.ZA);
		}
		return lZA;
	}

	public JLabel getLmax() {
		if (lmax == null) {
			lmax = new BHDescriptionLabel(translator.translate("max"));
		}
		return lmax;
	}

	public JLabel getLmin() {
		if (lmin == null) {
			lmin = new BHDescriptionLabel(translator.translate("min"));
		}
		return lmin;
	}
}
