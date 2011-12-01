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

import java.awt.Component;
import java.util.List;

import org.apache.log4j.Logger;
import org.bh.controller.IPeriodController;
import org.bh.controller.InputController;
import org.bh.data.DTOKeyPair;
import org.bh.data.DTOPeriod;
import org.bh.data.IPeriodicalValuesDTO;
import org.bh.data.types.Calculable;
import org.bh.data.types.DoubleValue;
import org.bh.gui.view.View;
import org.bh.gui.view.ViewException;
import org.bh.platform.Services;

public class RandomDirectInputController implements IPeriodController {
	private static final String GUI_KEY = "Random_Values";
	private static final int PRIORITY = 0;
	private static final List<DTOKeyPair> STOCHASTIC_KEYS =Services
			.getStochasticKeysFromEnum(DTODirectInput.getUniqueIdStatic(),
					DTODirectInput.Key.values());
	
	private static final Logger log = Logger.getLogger(RandomDirectInputController.class);

	@Override
	public Component editDTO(DTOPeriod period) {
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
			View view = new View(new RandomDirectInputForm());
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
