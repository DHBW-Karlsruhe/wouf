package org.bh.gui.swing;

import java.util.Map;

import org.bh.data.DTOScenario;
import org.bh.gui.View;
import org.bh.gui.ViewException;

public class BHDashBoardPanelView extends View {
	public BHDashBoardPanelView() throws ViewException{
		super(new BHDashBoardPanel());
	}
}