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

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.bh.controller.InputController;
import org.bh.data.DTO;
import org.bh.data.DTOPeriod;
import org.bh.data.IPeriodicalValuesDTO;
import org.bh.gui.swing.importexport.BHDataExchangeDialog;
import org.bh.gui.swing.importexport.BHDataExchangeDialog.ImportListener;
import org.bh.gui.view.View;
import org.bh.gui.view.ViewException;
import org.bh.platform.IImportExport;
import org.bh.platform.PlatformController;
import org.bh.plugin.gcc.PLSTotalCostController;
import org.bh.plugin.gcc.data.DTOGCCBalanceSheet;
import org.bh.plugin.gcc.data.DTOGCCProfitLossStatementTotalCost;
import org.bh.plugin.gcc.swing.BHBalanceSheetForm;
import org.bh.plugin.gcc.swing.BHPLSTotalCostForm;
import org.bh.plugin.gcc.swing.GCCCombinedForm;
import org.bh.validation.ValidationMethods;

/**
 * <short_description>
 *Controls tc view
 * <p>
 * <detailed_description>
 *
 * @author simon
 * @version 1.0, 18.01.2012
 *
 */
public class BHBDTotalCostController extends PLSTotalCostController {

	/**
	 * empty constructor - leave it empty
	 */
	public BHBDTotalCostController() {
		// TODO Auto-generated constructor stub
	}

	
	/**
     * Controls the TC View
     * 
     * @param period
     * period for TC method
     */
	public Component editDTO(DTOPeriod period) {
		IPeriodicalValuesDTO bs = period.getPeriodicalValuesDTO(DTOGCCBalanceSheet.getUniqueIdStatic());
		IPeriodicalValuesDTO pls = period.getPeriodicalValuesDTO(DTOGCCProfitLossStatementTotalCost.getUniqueIdStatic());
		if (bs == null || pls == null) {
			bs = new DTOGCCBalanceSheet();
			pls = new DTOGCCProfitLossStatementTotalCost();
			period.removeAllChildren();
			period.addChild(bs);
			period.addChild(pls);
		}

		try {
			boolean intervalArithmetic = false;
			JPanel jp = new JPanel();
			View bsView = new View(new BHBalanceSheetForm(intervalArithmetic), new ValidationMethods());
			final InputController bsController = new InputController(bsView, bs);
			bsController.loadAllToView();
			
			View plsView = new View(new BHPLSTotalCostForm(intervalArithmetic), new ValidationMethods());
			final InputController plsController = new InputController(plsView, pls);
			plsController.loadAllToView();
			
			
			JSplitPane combinedForm = new JSplitPane(JSplitPane.VERTICAL_SPLIT,bsView.getViewPanel(), plsView.getViewPanel());
			final IPeriodicalValuesDTO finalBS = bs;
			final IPeriodicalValuesDTO finalPLS = pls;
			
			jp.add(combinedForm);
			
			return jp;
		} catch (ViewException e) {
			//log.error("Could not create view", e);
			return null;
		}
	}
	
}
