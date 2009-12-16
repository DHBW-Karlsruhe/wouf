package org.bh.gui.swing;

import javax.swing.JLabel;

/**
 * BHLabel to display Labels at screen.
 * 
 * <p>
 * This class extends the Swing <code>JLabel</code> to display labels in
 * Business Horizon.
 * 
 * @author Thiele.Klaus
 * @version 0.1, 2009/12/13
 * 
 */
public class BHLabel extends JLabel implements IBHComponent {
	/**
	 * unique key to identify Label.
	 */
	private String key;
	private int[] validateRules;

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
		this.key = key;
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

	@Override
	public void setValidateRules(int[] validateRules) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

}
