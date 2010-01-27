package org.bh.plugin.directinput;

import java.awt.Component;
import java.util.List;

import org.apache.log4j.Logger;
import org.bh.controller.IPeriodController;
import org.bh.controller.InputController;
import org.bh.data.DTOKeyPair;
import org.bh.data.DTOPeriod;
import org.bh.data.IPeriodicalValuesDTO;
import org.bh.gui.view.View;
import org.bh.gui.view.ViewException;
import org.bh.platform.Services;
import org.bh.validation.ValidationMethods;

public class DirectInputController implements IPeriodController {
	private static final String GUI_KEY = "Direct_Input";
	private static final int PRIORITY = 100;
	private static final List<DTOKeyPair> STOCHASTIC_KEYS = Services
			.getStochasticKeysFromEnum(DTODirectInput.getUniqueIdStatic(),
					DTODirectInput.Key.values());

	private static final Logger log = Logger.getLogger(DirectInputController.class);

	@Override
	public Component editDTO(DTOPeriod period) {
		IPeriodicalValuesDTO model = period.getPeriodicalValuesDTO(DTODirectInput.getUniqueIdStatic());
		if (model == null) {
			model = new DTODirectInput();
			period.removeAllChildren();
			period.addChild(model);
		}

		try {
			boolean intervalArithmetic = period.getScenario().isIntervalArithmetic();
			
			View view = new View(new DirectInputForm(intervalArithmetic), new ValidationMethods());
			InputController controller = new InputController(view, model);
			controller.loadAllToView();
			return view.getViewPanel();
		} catch (ViewException e) {
			log.error("Could not create view", e);
			return null;
		}
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
}
