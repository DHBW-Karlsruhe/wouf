package org.bh.gui.swing;

import javax.swing.JTextField;

import org.bh.data.types.IValue;
import org.bh.data.types.StringValue;
import org.bh.platform.PlatformEvent;
import org.bh.platform.Services;

/**
 * BHTextField to display simple input fields at screen.
 * 
 * <p>
 * This class extends the Swing <code>JTextField</code> to display simple input
 * fields in Business Horizon.
 * 
 * @author Thiele.Klaus
 * @author Marco Hammel
 * @version 0.1, 2009/12/13
 * 
 */

// TODO Hints setzen!!! Noch werden für Textfields keine Hints erzeugt
public class BHTextField extends JTextField implements IBHModelComponent {
	/**
	 * unique key to identify Label.
	 */
	Object key;

	private int[] validateRules;
	private String inputHint;

	/**
	 * Constructor to create new <code>BHTextField</code>. Defined for the use
	 * with unkeyed text
	 * 
	 * @param key
	 *            unique key
	 * @param value
	 *            default value
	 */
	public BHTextField(String key, String value) {
		super(value);
		this.setProperties();
		this.key = key;
	}

	// TODO Konsoliedieren der Konstruktoren (nicht alle notwendig)
	/**
	 * Constructor to create new <code>BHTextField</code>. Defined for the use
	 * with unkeyed text
	 * 
	 * @param key
	 *            unique key
	 * @param value
	 *            default value
	 */
	public BHTextField(Object key, String value) {
		this(key.toString(), value);
	}

	/**
	 * Constructor to create new <code>BHTextField</code>. with key based text
	 * 
	 * @param key
	 *            unique key
	 */
	public BHTextField(String key) {
		this(key, "");
	}

	/**
	 * Constructor to create new <code>BHTextField</code>. with key based text
	 * 
	 * @param key
	 *            unique key
	 */
	public BHTextField(Object key) {
		this(key.toString());
	}

	@Override
	public String getKey() {
		return key.toString();
	}

	@Override
	public int[] getValidateRules() {
		return validateRules;
	}

	@Override
	public void setValidateRules(int[] validateRules) {
		this.validateRules = validateRules;
	}

	public boolean isTypeValid() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void setInputHint(String inputHint) {
		this.inputHint = inputHint;
	}

	public String getInputHint() {
		return this.inputHint;
	}

	@Override
	public IValue getValue() {
		return new StringValue(this.getText());
	}

	/**
	 * set properties of instance.
	 */
	private void setProperties() {
		Services.addPlatformListener(this);
		this.putClientProperty("JComponent.sizeVariant", IBHComponent.MINI); // Minitextfield
	}

	@Override
	public String getBHHint() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void platformEvent(PlatformEvent e) {
	}

	@Override
	public void reloadText() {
		// nothing to do
	}

	@Override
	public void setValue(IValue value) {
		if (value != null)
			this.setText(value.toString());
		else
			this.setText("");
	}

}
