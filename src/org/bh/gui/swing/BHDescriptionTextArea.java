package org.bh.gui.swing;

import javax.swing.JTextArea;

import org.bh.platform.IPlatformListener;
import org.bh.platform.PlatformEvent;
import org.bh.platform.Services;
import org.bh.platform.PlatformEvent.Type;

/**
 * BHTextArea to display simple text fields at screen.
 * 
 * <p>
 * This class extends the Swing <code>JTextArea</code> to display simple text
 * fields in Business Horizon.
 * 
 * @author Lars
 * @author Robert
 * @version 0.2, 2010/01/07
 * 
 */
public class BHDescriptionTextArea extends JTextArea implements IBHComponent, IPlatformListener {
	private static final long serialVersionUID = -3524100441367520993L;
	/**
	 * unique key to identify Label.
	 */
	private String key;
	
	public BHDescriptionTextArea(String key, int row, int column) {
		super(row, column);
		this.key = key;
		this.setEditable(false);
		this.setWrapStyleWord(true);
		this.setLineWrap(true);
		reloadText();
	}
	
	public BHDescriptionTextArea(String key) {
		this(key, 0, 0);
	}

	public String getKey() {
		return key;
	}

	@Override
	public String getBHHint() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public void platformEvent(PlatformEvent e) {
		if (e.getEventType() == Type.LOCALE_CHANGED) {
			reloadText();
		}
	}
	
	protected void reloadText() {
		this.setText(Services.getTranslator().translate(key));
	}
}
