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
 *
 * <p>
 * <detailed_description>
 *
 * @author simon
 * @version 1.0, 18.01.2012
 *
 */
public class BHBDTotalCostController extends PLSTotalCostController {

	/**
	 * 
	 */
	public BHBDTotalCostController() {
		// TODO Auto-generated constructor stub
	}

	
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
