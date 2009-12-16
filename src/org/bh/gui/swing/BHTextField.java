package org.bh.gui.swing;

import javax.swing.JTextField;

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

}
