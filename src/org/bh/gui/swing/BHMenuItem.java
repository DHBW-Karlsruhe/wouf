package org.bh.gui.swing;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import org.bh.platform.IPlatformListener;
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
public class BHMenuItem extends JMenuItem implements IBHComponent, IBHAction, IPlatformListener {
	private static final long serialVersionUID = 457483046895655665L;
	private PlatformKey key;
	private static List<IBHAction> platformItems = new ArrayList<IBHAction>();

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
	public BHMenuItem(PlatformKey key, int eventKey, boolean isPlatformItem) {
		super();

		this.key = key;
		reloadText();
		
		Services.addPlatformListener(this);
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
	public PlatformKey getPlatformKey() {
		return this.key;
	}

	@Override
	public boolean isPlatformItem() {
		return (this.key != null);
	}

	/**
	 * Method to get all MenuItems that are generated for platforms (i.e. that
	 * ones which have forPlatform = false not set)
	 */
	public static List<IBHAction> getPlatformItems() {
		return platformItems;
	}

	@Override
	public String getBHHint() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void platformEvent(PlatformEvent e) {
		if (e.getEventType() == Type.LOCALE_CHANGED) {
			this.reloadText();
		}
	}

	/**
	 * Reset Text if necessary.
	 */
	protected void reloadText() {
		this.setText(Services.getTranslator().translate(key.toString()));
	}
}
