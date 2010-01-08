package org.bh.plugin.directinput;

import java.util.List;

import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.bh.controller.IPeriodController;
import org.bh.controller.InputController;
import org.bh.data.DTOKeyPair;
import org.bh.data.DTOPeriod;
import org.bh.data.IPeriodicalValuesDTO;
import org.bh.gui.View;
import org.bh.gui.ViewException;
import org.bh.platform.Services;

public class DirectInputController implements IPeriodController {
	private static final String GUI_KEY = "Direct_Input";
	private static final int PRIORITY = 100;
	private static final List<DTOKeyPair> STOCHASTIC_KEYS = Services
			.getStochasticKeysFromEnum(DTODirectInput.getUniqueIdStatic(),
					DTODirectInput.Key.values());

	private static final Logger log = Logger.getLogger(DirectInputController.class);

	@Override
	public void editDTO(DTOPeriod period, JPanel panel) {
		IPeriodicalValuesDTO model = period.getPeriodicalValuesDTO(DTODirectInput.getUniqueIdStatic());
		if (model == null) {
			model = new DTODirectInput();
			period.removeAllChildren();
			period.addChild(model);
		}

		try {
			View view = new DirectInputView();
			panel.add(view.getViewPanel());
			InputController controller = new InputController(view, model);
			controller.loadAllToView();
		} catch (ViewException e) {
			log.error("Could not create view", e);
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
