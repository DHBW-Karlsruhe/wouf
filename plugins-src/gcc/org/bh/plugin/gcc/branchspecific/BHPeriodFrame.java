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
package org.bh.plugin.gcc.branchspecific;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import org.bh.data.DTOCompany;
import org.bh.data.DTOPeriod;
import org.bh.data.IPeriodicalValuesDTO;
import org.bh.gui.swing.comp.BHDescriptionLabel;
import org.bh.gui.swing.comp.BHTextField;
import org.bh.gui.swing.forms.BHPeriodHeadForm;
import org.bh.plugin.gcc.data.DTOGCCProfitLossStatementCostOfSales;
import org.bh.plugin.gcc.data.DTOGCCProfitLossStatementTotalCost;
import org.bh.validation.VRIsBetween;
import org.bh.validation.VRIsInteger;
import org.bh.validation.VRMandatory;
import org.bh.validation.ValidationRule;

/**
 * <short_description>
 * 
 * this class creates a view with balance sheet and p&l-statement
 * <detailed_description>
 * 
 * @author simon
 * @version 1.0, 18.01.2012
 * 
 */
public class BHPeriodFrame {
	
	public enum Name{
		PERIOD;
	}
	DTOPeriod period;

	/**
	 * 
	 * creates a Frame to show period data
	 *            
	 **/
	public BHPeriodFrame(DTOPeriod period) {
		this.period = period;
	}

	public JPanel getFrame() {
		JPanel p = null;

		IPeriodicalValuesDTO myProfitStatement = period.getChild(1);
		// TotalCost Method
		if (myProfitStatement.getUniqueId() == DTOGCCProfitLossStatementTotalCost
				.getUniqueIdStatic()) {
			BHBDTotalCostController i = new BHBDTotalCostController();
			p = ((JPanel) i.editDTO(period));

		}

		// CostOfSales Method
		if (myProfitStatement.getUniqueId() == DTOGCCProfitLossStatementCostOfSales
				.getUniqueIdStatic()) {
			BHBDCostOfSalesController r = new BHBDCostOfSalesController();
			p = ((JPanel) r.editDTO(period));
		}
		return p;
	}
}
