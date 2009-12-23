package org.bh.gui.swing;

import javax.swing.JLabel;
import org.bh.data.types.IValue;

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
		this.key = key;
		this.inputHint = inputHint;
	}
	
	/**
	 * Constructor to create new <code>BHLabel</code>.
	 * 
	 * @param key
	 *            default key
	 */
	public BHLabel(String key) {
		super();
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

	public void setValidateRules(int[] validateRules) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

        public boolean isTypeValid() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public IValue getValue() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public  void setValue(IValue value){
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public String getInputHint() {
            return this.inputHint;
        }



}
