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

import javax.swing.JPanel;

import org.bh.data.DTOPeriod;
import org.bh.gui.swing.comp.BHDescriptionLabel;
import org.bh.gui.swing.comp.BHTextField;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;
import org.bh.validation.VRMandatory;
import org.bh.validation.ValidationRule;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * This class contains the form for head-data
 * 
 * @author Anton Kharitonov
 * @author Patrick Heinz
 * @version 0.1, 13.01.2010
 * 
 */
@SuppressWarnings("serial")
public final class BHPeriodHeadForm extends JPanel {

	private BHDescriptionLabel lperiodname;
	private BHTextField tfperiodname;

	ITranslator translator = Services.getTranslator();

	public BHPeriodHeadForm() {
		this.initialize();
	}
		
	public void initialize(){

		String rowDef = "4px,p,4px";
		String colDef = "4px,right:pref,4px,max(150px;pref):grow(0.3),4px:grow";

		FormLayout layout = new FormLayout(colDef, rowDef);
		this.setLayout(layout);

		CellConstraints cons = new CellConstraints();

		this.add(this.getLperiodname(), cons.xywh(2, 2, 1, 1));
		this.add(this.getTfperiodname(), cons.xywh(4, 2, 1, 1));

	}
	
	
	public BHDescriptionLabel getLperiodname() {
		if (lperiodname == null) {
			lperiodname = new BHDescriptionLabel(DTOPeriod.Key.NAME);
			lperiodname.setVisible(true);
		}
		return lperiodname;
	}

	/**
	 * Getter method for component tfprojectname.
	 * 
	 * @return BHTextField
	 */
	public BHTextField getTfperiodname() {
		if (tfperiodname == null) {
			tfperiodname = new BHTextField(DTOPeriod.Key.NAME, false);
			ValidationRule[] rules = { VRMandatory.INSTANCE };
			tfperiodname.setValidationRules(rules);
		}
		return tfperiodname;
	}


}

