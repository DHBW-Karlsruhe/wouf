package org.bh.plugin.swing;

import org.bh.gui.ValidationMethods;
import org.bh.gui.View;
import org.bh.gui.ViewException;

public class ViewTest extends View {

	public ViewTest() throws ViewException {
		super(new BHBalanceSheetForm(), new ValidationBHBalanceSheetForm());
	}
}

