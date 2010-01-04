package org.bh.gui.swing;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import org.bh.platform.PlatformEvent;
import org.bh.platform.PlatformKey;
import org.bh.platform.Services;
import org.bh.platform.PlatformEvent.Type;

/**
 * BHMenuItem to create and display new menu items in the menu bar.
 * 
 * <p>
 * This class extends the Swing <code>JMenuItem</code> to display menu items in
 * the menu bar
 * 
 * @author Tietze.Patrick
 * @author Marco.Hammel
 * @version 0.1, 2009/12/16
 * 
 */
public class BHMenuItem extends JMenuItem implements IBHComponent, IBHAction {

	private PlatformKey key;
	private int[] validateRules;
	private static List<IBHAction> platformItems = new ArrayList<IBHAction>();
	private String inputHint;

	/**
	 * Creates a new MenuItem (to be used in regular menus)
	 * 
	 * @param key
	 *            key for action handling and texts
	 * @param eventKey
	 *            shortcut button for keyboard-addicted users
	 * @param isPlatformItem
	 *            Menu Item will be placed in platform list if true
	 */
	public BHMenuItem(PlatformKey key, int eventKey, Boolean isPlatformItem) {
		super(Services.getTranslator().translate(key.toString()));
		Services.addPlatformListener(this);
		this.key = key;
		if (eventKey != 0) {
			this.setMnemonic(eventKey);

			int metakey = 0;
			if ("Mac OS X".equals(System.getProperty("os.name"))) {
				metakey = ActionEvent.META_MASK;
			} else {
				metakey = ActionEvent.CTRL_MASK;
			}

			if (key == PlatformKey.FILESAVEAS) {
				metakey |= ActionEvent.SHIFT_MASK;
			}

			this.setAccelerator(KeyStroke.getKeyStroke(eventKey, metakey));
		}

		if (isPlatformItem) {
			platformItems.add(this);
		}

	}

	/**
	 * Creates a new Menu Item (item is automatically added to platform list)
	 * 
	 * @param key
	 *            key for action handling and texts
	 * @param eventKey
	 *            shortcut button for keyboard-adicted users
	 */
	public BHMenuItem(PlatformKey key, int eventKey) {
		this(key, eventKey, true);
	}

	/**
	 * Creates a new Menu Item (item is automatically added to platform list and
	 * no eventKey for keyboard-shortcut is set)
	 * 
	 * @param key
	 *            key for action handling and texts
	 */
	public BHMenuItem(PlatformKey key) {
		this(key, 0, true);
	}

	public BHMenuItem() {
		super();
	}

	@Override
	public String getKey() {
		return key.toString();
	}

	@Override
	public int[] getValidateRules() {
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

	public String getInputHint() {
		return this.inputHint;
	}

	public PlatformKey getPlatformKey() {
		return this.key;
	}

	@Override
	public Boolean isPlatformItem() {
		if (this.platformKey != null)
			return true;
		return false;
	}

	/**
	 * Method to get all MenuItems that are generated for platforms (i.e. that
	 * ones which have forPlatform = false not set)
	 */
	@Override
	public List<IBHAction> getPlatformItems() {
		return platformItems;
	}

	public String getValue() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String getBHHint() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	/**
	 * Handle PlatformEvents
	 */
	@Override
	public void platformEvent(PlatformEvent e) {
		if (e.getEventType() == Type.LOCALE_CHANGED) {
			this.resetText();
		}
	}

	/**
	 * Reset Text if necessary.
	 */
	@Override
	public void resetText() {
		this.setText(Services.getTranslator().translate(key.toString()));
	}
}
