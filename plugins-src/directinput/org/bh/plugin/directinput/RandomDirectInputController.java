package org.bh.plugin.directinput;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.bh.controller.IPeriodController;
import org.bh.controller.InputController;
import org.bh.data.DTOKeyPair;
import org.bh.data.DTOPeriod;
import org.bh.data.IPeriodicalValuesDTO;
import org.bh.data.types.Calculable;
import org.bh.data.types.DoubleValue;
import org.bh.gui.View;
import org.bh.gui.ViewException;

// TODO Testklasse, wird später entfernt

public class RandomDirectInputController implements IPeriodController {
	private static final String GUI_KEY = "Random_Values";
	private static final int PRIORITY = 0;
	private static final List<DTOKeyPair> STOCHASTIC_KEYS = new ArrayList<DTOKeyPair>();
	
	private static final Logger log = Logger.getLogger(RandomDirectInputController.class);

	@Override
	public void editDTO(DTOPeriod period, JPanel panel) {
		IPeriodicalValuesDTO model = period.getPeriodicalValuesDTO(DTODirectInput.getUniqueIdStatic());
		if (model == null) {
			model = new DTODirectInput();
			
			Calculable liabilities = new DoubleValue(Math.random() * 200 + 1000);
			model.put(DTODirectInput.Key.LIABILITIES, liabilities);
			Calculable fcf = new DoubleValue(Math.random() * 20 + 100);
			model.put(DTODirectInput.Key.FCF, fcf);
			
			period.removeAllChildren();
			period.addChild(model);
		}

		try {
			View view = new RandomDirectInputView();
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
