package org.bh.plugin.xmldataexchange;

import javax.swing.JPanel;

import org.bh.gui.BHValidityEngine;
import org.bh.gui.View;
import org.bh.gui.ViewException;
import org.bh.platform.i18n.ITranslator;

public class XMLDataExchangeView extends View {

	protected XMLDataExchangeView(JPanel viewPanel, BHValidityEngine validator,
			ITranslator translator) throws ViewException {
		super(viewPanel);
		
	}
}
