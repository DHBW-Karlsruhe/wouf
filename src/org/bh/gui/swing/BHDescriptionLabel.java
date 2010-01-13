package org.bh.gui.swing;

import javax.swing.JLabel;

import org.bh.platform.IPlatformListener;
import org.bh.platform.PlatformEvent;
import org.bh.platform.Services;
import org.bh.platform.PlatformEvent.Type;

/**
 * Label which displays the translation of a key.
 * 
 * @author Thiele.Klaus
 * @author Marco Hammel
 * @author Robert Vollmer
 * @version 0.2, 2010/01/07
 * 
 */
public class BHDescriptionLabel extends JLabel implements IBHComponent, IPlatformListener {
	private static final long serialVersionUID = 2194119858505365723L;
	
	/**
	 * unique key to identify Label.
	 */
	private String key;

	/**
	 * Constructor to create new <code>BHLabel</code>.
	 * 
	 * @param key
	 *            Translation key
	 */
	public BHDescriptionLabel(Object key) {
		this.key = key.toString();
		
		reloadText();
		Services.addPlatformListener(this);
	}

	/**
	 * Returns the unique ID of the <code>BHLabel</code>.
	 * 
	 * @return id unique identifier.
	 */
	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getHint() {
		return "";
	}

	/**
	 * Handle PlatformEvents
	 */
	@Override
	public void platformEvent(PlatformEvent e) {
		if (e.getEventType() == Type.LOCALE_CHANGED) {
			reloadText();
		}
	}

	/**
	 * Reloads text if necessary.
	 */
	protected void reloadText() {
		this.setText(Services.getTranslator().translate(key));
	}
}
