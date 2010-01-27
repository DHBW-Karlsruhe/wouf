package org.bh.gui.view;

import org.bh.gui.swing.*;
import org.bh.gui.view.View;
import org.bh.gui.view.ViewException;

public class BHDashBoardPanelView extends View {
	public BHDashBoardPanelView() throws ViewException{
		super(new BHDashBoardPanel());
	}
}