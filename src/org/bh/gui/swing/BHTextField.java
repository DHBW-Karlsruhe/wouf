package org.bh.gui.swing;

import javax.swing.JTextField;

import org.bh.data.types.IValue;

/**
 * BHTextField to display simple input fields at screen.
 * 
 * <p>
 * This class extends the Swing <code>JTextField</code> to display simple input
 * fields in Business Horizon.
 * 
 * @author Thiele.Klaus
 * @version 0.1, 2009/12/13
 * 
 */
public class BHTextField extends JTextField implements IBHComponent {
	/**
	 * unique key to identify Label.
	 */
	private String key;


	private int[] validateRules;
	private String inputHint;

	/**
	 * Constructor to create new <code>BHTextField</code>.
	 * 
	 * @param key
	 *            unique key
	 * @param value
	 *            default value
	 */
	public BHTextField(String key, String value, String inputHint) {
		super(value);
		this.setProperties();
		this.key = key;
		this.inputHint = inputHint;
	}

	/**
	 * Constructor to create new <code>BHTextField</code>.
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

	/**
	 * Constructor to create new <code>BHTextField</code>.
	 * 
	 * @param key
	 *            unique key
	 */
	public BHTextField(String key) {
		super();
		this.setProperties();
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public int[] getValidateRules() {
		return validateRules;
	}

	public void setValidateRules(int[] validateRules) {
		this.validateRules = validateRules;
	}

	public boolean isTypeValid() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public IValue getValue() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void setValue(IValue value) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	public void setInputHint(String inputHint) {
		this.inputHint = inputHint;
	}
	
	public String getInputHint() {
		return this.inputHint;
	}

	/**
	 * set properties of instance.
	 */
	private void setProperties() {
		this.putClientProperty("JComponent.sizeVariant", IBHComponent.MINI); // Minitextfield
	}

}
