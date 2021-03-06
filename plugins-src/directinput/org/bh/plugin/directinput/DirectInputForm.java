/*******************************************************************************
 * Copyright 2011: Matthias Beste, Hannes Bischoff, Lisa Doerner, Victor Guettler, Markus Hattenbach, Tim Herzenstiel, Günter Hesse, Jochen Hülß, Daniel Krauth, Lukas Lochner, Mark Maltring, Sven Mayer, Benedikt Nees, Alexandre Pereira, Patrick Pfaff, Yannick Rödl, Denis Roster, Sebastian Schumacher, Norman Vogel, Simon Weber * : Anna Aichinger, Damian Berle, Patrick Dahl, Lisa Engelmann, Patrick Groß, Irene Ihl, Timo Klein, Alena Lang, Miriam Leuthold, Lukas Maciolek, Patrick Maisel, Vito Masiello, Moritz Olf, Ruben Reichle, Alexander Rupp, Daniel Schäfer, Simon Waldraff, Matthias Wurdig, Andreas Wußler
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
package org.bh.plugin.directinput;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import org.bh.controller.Controller;
import org.bh.data.DTOPeriod;
import org.bh.gui.IBHComponent;
import org.bh.gui.swing.comp.BHDescriptionLabel;
import org.bh.gui.swing.comp.BHTextField;
import org.bh.gui.swing.forms.border.BHBorderFactory;
import org.bh.platform.i18n.ITranslator;
import org.bh.validation.VRIsDouble;
import org.bh.validation.VRIsGreaterThan;
import org.bh.validation.VRIsPositive;
import org.bh.validation.VRMandatory;
import org.bh.validation.ValidationRule;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Visual class ViewPeriodData1.
 * 
 * 
 */
@SuppressWarnings("serial")
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
	private BHDescriptionLabel lname;
	private BHTextField tfmaxliabilities;
	private BHTextField tfminliabilities;
	private BHTextField tfmaxfcf;
	private BHTextField tfminfcf;
	private BHTextField tfname;

	ITranslator translator = Controller.getTranslator();

	public enum Key {
		/**
		 * 
		 */
		PERIOD_DIRECT;

		public String toString() {
			return getClass().getName() + "." + super.toString();
		}
	}

	/**
	 * Constructor.
	 */
	public DirectInputForm(boolean intervalArithmetic) {
		this.initialize(intervalArithmetic);
	}

	public void initialize(boolean intervalArithmetic) {
		String rowDef = "4px,p,4px,p,4px,p,4px,p,4px";
		String colDef = "4px,right:pref,4px,max(100px;pref),4px,pref,4px";
		if (intervalArithmetic) {
			rowDef = "4px,p," + rowDef;
			colDef += ",max(100px;pref),4px,pref,4px";
		}
		FormLayout layout = new FormLayout(colDef, rowDef);
		this.setLayout(layout);

		CellConstraints cons = new CellConstraints();

		this.add(this.getLfcf(), cons.xywh(2, 4, 1, 1));
		this.add(this.getLliabilities(), cons.xywh(2, 6, 1, 1));

		if (!intervalArithmetic) {

			this.add(this.getTffcf(), cons.xywh(4, 4, 1, 1));
			this.add(this.getLcurrency1(), cons.xywh(6, 4, 1, 1));
			this.add(this.getTfliabilities(), cons.xywh(4, 6, 1, 1));
			this.add(this.getLcurrency2(), cons.xywh(6, 6, 1, 1));
		} else {
			layout.setColumnGroups(new int[][] { { 4, 10 } });

			// this.add(new JSeparator(SwingConstants.VERTICAL), cons.xywh(8, 2,
			// 1,
			// 6));
			this.add(this.getLmin(), cons.xywh(4, 2, 1, 1));
			// this.add(new JSeparator(SwingConstants.VERTICAL), cons.xywh(14,
			// 2, 1,
			// 6));
			this.add(this.getLmax(), cons.xywh(8, 2, 1, 1));

			this.add(this.getTfminfcf(), cons.xywh(4, 4, 1, 1));
			this.add(this.getLcurrency3(), cons.xywh(6, 4, 1, 1));
			this.add(this.getTfmaxfcf(), cons.xywh(8, 4, 1, 1));
			this.add(this.getLcurrency4(), cons.xywh(10, 4, 1, 1));

			this.add(this.getTfminliabilities(), cons.xywh(4, 6, 1, 1));
			this.add(this.getLcurrency5(), cons.xywh(6, 6, 1, 1));
			this.add(this.getTfmaxliabilities(), cons.xywh(8, 6, 1, 1));
			this.add(this.getLcurrency6(), cons.xywh(10, 6, 1, 1));
		}

		this.setBorder(BHBorderFactory.getInstacnce().createTitledBorder(
				BHBorderFactory.getInstacnce().createEtchedBorder(
						EtchedBorder.LOWERED),
				DirectInputForm.Key.PERIOD_DIRECT));
	}

	public BHDescriptionLabel getLname() {
		if (lname == null) {
			lname = new BHDescriptionLabel(DTOPeriod.Key.NAME);
		}
		return lname;
	}

	public BHTextField getTfname() {
		if (tfname == null) {
			tfname = new BHTextField(DTOPeriod.Key.NAME);
		}
		return tfname;
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
			tfmaxliabilities = new BHTextField(IBHComponent.MAXVALUE
					+ DTODirectInput.Key.LIABILITIES);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE,
					new VRIsGreaterThan(getTfminliabilities(), true) };
			tfmaxliabilities.setValidationRules(rules);
		}
		return tfmaxliabilities;
	}

	public BHTextField getTfminliabilities() {
		if (tfminliabilities == null) {
			tfminliabilities = new BHTextField(IBHComponent.MINVALUE
					+ DTODirectInput.Key.LIABILITIES);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE };
			tfminliabilities.setValidationRules(rules);
		}
		return tfminliabilities;
	}

	public BHTextField getTfmaxfcf() {
		if (tfmaxfcf == null) {
			tfmaxfcf = new BHTextField(IBHComponent.MAXVALUE
					+ DTODirectInput.Key.FCF);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE,
					new VRIsGreaterThan(getTfminfcf(), true) };
			tfmaxfcf.setValidationRules(rules);
		}
		return tfmaxfcf;
	}

	public BHTextField getTfminfcf() {
		if (tfminfcf == null) {
			tfminfcf = new BHTextField(IBHComponent.MINVALUE
					+ DTODirectInput.Key.FCF);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tfminfcf.setValidationRules(rules);
		}
		return tfminfcf;
	}

	public BHDescriptionLabel getLfcf() {
		if (lfcf == null) {
			lfcf = new BHDescriptionLabel(DTODirectInput.Key.FCF);
		}
		return lfcf;
	}

	public BHTextField getTffcf() {
		if (tffcf == null) {
			tffcf = new BHTextField(DTODirectInput.Key.FCF);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE };
			tffcf.setValidationRules(rules);
		}
		return tffcf;
	}

	public BHDescriptionLabel getLliabilities() {
		if (lliabilities == null) {
			lliabilities = new BHDescriptionLabel(
					DTODirectInput.Key.LIABILITIES);
		}
		return lliabilities;
	}

	public BHTextField getTfliabilities() {
		if (tfliabilities == null) {
			tfliabilities = new BHTextField(DTODirectInput.Key.LIABILITIES);
			ValidationRule[] rules = { VRMandatory.INSTANCE,
					VRIsDouble.INSTANCE, VRIsPositive.INSTANCE };
			tfliabilities.setValidationRules(rules);
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
	//
	// /**
	// * Test main method.
	// */
	// public static void main(String args[]) {
	//
	// JFrame test = new JFrame("Test for ViewPeriodData1");
	// test.setContentPane(new DirectInputForm(true));
	// test.addWindowListener(new WindowAdapter() {
	// @Override
	// public void windowClosing(WindowEvent e) {
	// System.exit(0);
	// }
	// });
	// test.pack();
	// test.show();
	// }
}
