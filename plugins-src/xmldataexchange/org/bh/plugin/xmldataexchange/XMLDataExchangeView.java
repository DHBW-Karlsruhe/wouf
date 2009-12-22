package org.bh.plugin.xmldataexchange;

import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;

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

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub
		/*throw new UnsupportedOperationException(
				"This method has not been implemented");*/
	}

}
