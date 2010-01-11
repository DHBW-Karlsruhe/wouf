package org.bh.gui.swing;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import org.bh.platform.IPlatformListener;
import org.bh.platform.PlatformEvent;
import org.bh.platform.PlatformKey;
import org.bh.platform.Services;
import org.bh.platform.PlatformEvent.Type;
import org.bh.platform.i18n.ITranslator;

/**
 * BHButton to display buttons at screen.
 * 
 * <p>
 * This class extends the Swing <code>JButton</code> to display simple buttons
 * in Business Horizon.
 * 
 * @author Thiele.Klaus
 * @author Schmalzhaf.Alexander
 * @author Robert
 * 
 * @version 0.1, 2009/12/13
 * @version 0.1.1 2009/12/26
 * 
 */
public class BHButton extends JButton implements IBHComponent, IBHAction, IPlatformListener {
	private static final long serialVersionUID = -8018664370176080809L;
	private static List<IBHAction> platformItems = new ArrayList<IBHAction>();
	private static ITranslator translator = Services.getTranslator();

	private String key;
	private PlatformKey platformKey;
	private String inputHint;

	/**
	 * Secondary constructor for platform buttons (are added to
	 * platformButtons-list and uses ENUM Platform Key instead of String for
	 * key)
	 * 
	 * @param key
	 */
	public BHButton(PlatformKey platformKey) {
		this(platformKey.toString());
		this.platformKey = platformKey;
		platformItems.add(this);
	}

	/**
	 * Standard constructor to create buttons NOT for platform
	 * 
	 * @param key
	 */
	public BHButton(String key) {
		super();
		this.key = key;

		reloadText();
		Services.addPlatformListener(this);
	}

	/**
	 * Standard constructor to create buttons NOT for platform
	 * 
	 * @param key
	 */
	public BHButton(Object key) {
		this(key.toString());
	}

	/**
	 * return the key for value mapping
	 * 
	 * @return
	 */
	@Override
	public String getKey() {
		return key;
	}

	@Override
	public PlatformKey getPlatformKey() {
		return this.platformKey;
	}

	public static List<IBHAction> getPlatformItems() {
		return platformItems;
	}

	@Override
	public boolean isPlatformItem() {
		return (this.platformKey != null);
	}

	@Override
	public String getInputHint() {
		return inputHint;
	}
	

	@Override
	public void platformEvent(PlatformEvent e) {
		if (e.getEventType() == Type.LOCALE_CHANGED) {
			this.reloadText();
		}
	}

	protected void reloadText() {
		this.setText(translator.translate(key));
		inputHint = translator.translate(key, ITranslator.LONG);
		setToolTipText(inputHint);
	}

	/**
	 * 
	 * This MouseListener provides Buttons with the ability to show their texts
	 * in StatusBar
	 * 
	 * @author Alexander Schmalzhaf
	 * @version 1.0, 29.12.2009
	 * 
	 */
	/*
	class BHToolTipListener extends MouseInputAdapter {
		@Override
		public void mouseEntered(MouseEvent e) {
			if (!toolTip.isEmpty())
				Services.getBHstatusBar().setHint(toolTip);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (!toolTip.isEmpty())
				Services.getBHstatusBar().removeHint();
		}
	}
	*/
}
