package org.bh.plugin.swing;

import java.awt.Component;
import java.util.List;

import org.apache.log4j.Logger;
import org.bh.controller.IPeriodController;
import org.bh.controller.InputController;
import org.bh.data.DTOKeyPair;
import org.bh.data.DTOPeriod;
import org.bh.data.IPeriodicalValuesDTO;
import org.bh.gui.ValidationMethods;
import org.bh.gui.View;
import org.bh.gui.ViewException;
import org.bh.platform.Services;
import org.bh.plugin.gcc.data.DTOGCCBalanceSheet;
import org.bh.plugin.gcc.data.DTOGCCProfitLossStatementTotalCost;

/**
 * 
 * @author Robert Vollmer
 */
public class PLSTotalCostController implements IPeriodController {
	private static final Logger log = Logger.getLogger(PLSTotalCostController.class);
	private static final String GUI_KEY = "gcc_input_totalcost";
	private static final int PRIORITY = 50;

	private static final List<DTOKeyPair> STOCHASTIC_KEYS;
	static {
		STOCHASTIC_KEYS = Services.getStochasticKeysFromEnum(DTOGCCBalanceSheet
				.getUniqueIdStatic(), DTOGCCBalanceSheet.Key.values());

		STOCHASTIC_KEYS.addAll(Services.getStochasticKeysFromEnum(
				DTOGCCProfitLossStatementTotalCost.getUniqueIdStatic(),
				DTOGCCProfitLossStatementTotalCost.Key.values()));
	}

	@Override
	public String getGuiKey() {
		return GUI_KEY;
	}

	@Override
	public int getGuiPriority() {
		return PRIORITY;
	}

	@Override
	public List<DTOKeyPair> getStochasticKeys() {
		return STOCHASTIC_KEYS;
	}

	@Override
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
			View bsView = new View(new BHBalanceSheetForm(), new ValidationMethods());
			InputController bsController = new InputController(bsView, bs);
			bsController.loadAllToView();
			
			View plsView = new View(new BHPLSTotalCostForm(), new ValidationMethods());
			InputController plsController = new InputController(plsView, pls);
			plsController.loadAllToView();
			
			return new GCCCombinedForm(bsView.getViewPanel(), plsView.getViewPanel());
		} catch (ViewException e) {
			log.error("Could not create view", e);
			return null;
		}
	}
}
