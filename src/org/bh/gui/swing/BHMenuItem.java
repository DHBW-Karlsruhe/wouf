package org.bh.gui.swing;

import java.awt.event.ActionEvent;

import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import org.bh.data.types.IValue;
import org.bh.platform.i18n.BHTranslator;

/**
 * BHMenuItem to create and display new menu items in the menu bar.
 * 
 * <p>
 * This class extends the Swing <code>JMenuItem</code> to display menu items in
 * the menu bar
 * 
 * @author Tietze.Patrick
 * @version 0.1, 2009/12/16
 * 
 */

public class BHMenuItem extends JMenuItem implements IBHComponent {

	private String key;
	private int[] validateRules;
	static BHTranslator translator = BHTranslator.getInstance();

	/**
	 * create the new menu item
	 * 
	 * @param key
	 * @param eventKey
	 *            : Dec number ASCII
	 * @param eventAction
	 * @param actionCommand
	 */

	public BHMenuItem(String key, int eventKey, String actionCommand) {
		super(translator.translate(key));
		this.key = key;
		if (eventKey != 0) {
			this.setMnemonic(eventKey);
			int metakey = 0;
			if ("Mac OS X".equals(System.getProperty("os.name"))) {
				metakey = ActionEvent.META_MASK;
			} else {
				metakey = ActionEvent.CTRL_MASK;
			}
			this.setAccelerator(KeyStroke.getKeyStroke(eventKey, metakey));
		}
		this.setActionCommand(actionCommand);
		// this.addActionListener(this);

	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public int[] getValidateRules() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public boolean isTypeValid() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void setValidateRules(int[] validateRules) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	public IValue getValue() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void setValue(IValue value) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
