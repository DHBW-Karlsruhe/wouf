package org.bh.gui.swing;

import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import org.bh.platform.PlatformEvent;
import org.bh.platform.Services;
import org.bh.platform.PlatformEvent.Type;

/**
 * BHLabel to display Labels at screen.
 * 
 * <p>
 * This class extends the Swing <code>JLabel</code> to display labels in
 * Business Horizon.
 * 
 * @author Thiele.Klaus
 * @author Marco Hammel
 * @version 0.1, 2009/12/13
 * 
 */
public class BHLabel extends JLabel implements IBHComponent {
	/**
	 * unique key to identify Label.
	 */
	private String key;
	private int[] validateRules;
	private String inputHint;

	/**
	 * Constructor to create new <code>BHLabel</code>.
	 * 
	 * @param key
	 *            default key
	 * @param value
	 *            default value
	 * @param inputHint
	 *            default inputHint
	 */
	public BHLabel(String key, String value, String inputHint) {
		super(value);
		this.setProperties();
		this.key = key;
		this.inputHint = inputHint;
	}

	/**
	 * Constructor to create new <code>BHLabel</code>.
	 * 
	 * @param key
	 *            default key
	 * @param value
	 *            default value
	 */
	public BHLabel(String key, String value) {
		super(value);
		this.setProperties();
		this.key = key;
	}

	/**
	 * Constructor to create new <code>BHLabel</code>.
	 * 
	 * @param key
	 *            default key
	 */
	public BHLabel(String key) {
		super(Services.getTranslator().translate(key));
		this.setProperties();
		this.key = key;
	}
	
	/**
	 * Constructor to create new <code>BHLabel</code>.
	 * 
	 * @param key
	 *            default key
	 */
	public BHLabel(Object key) {
		super(Services.getTranslator().translate(key));
		this.setProperties();
		this.key = key.toString();
	}


	/**
	 * Returns the unique ID of the <code>BHLabel</code>.
	 * 
	 * @return id unique identifier.
	 */

	public String getKey() {
		return key;
	}

	public int[] getValidateRules() {
		return validateRules;
	}

	public void setValidateRules(int[] validateRules) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	public boolean isTypeValid() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * set properties of instance.
	 */
	private void setProperties() {
		Services.addPlatformListener(this);
		this.putClientProperty("JComponent.sizeVariant", IBHComponent.MINI); // Minilabel
	}

	@Override
	public String getBHHint() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	public String getValue() {
		if (this.getText() == null) {
			return "";
		}
		return this.getText();
	}

	public void mouseClicked(MouseEvent e) {
		Services.getBHstatusBar().openToolTipPopup();
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
