package org.bh.gui.swing;

import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.bh.platform.PlatformController;
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

//TODO Hints setzen!!! Noch werden für Textfields keine Hints erzeugt
public class BHTextField extends JTextField implements IBHComponent {
	/**
	 * unique key to identify Label.
	 */
	Object key;


	private int[] validateRules;
	private String inputHint;


	/**
	 * Constructor to create new <code>BHTextField</code>. Defined for the use with unkeyed text
	 * 
	 * @param key
	 *            unique key
	 * @param value
	 *            default value
	 */
	public BHTextField(String key, String value) {
		super(value);
		this.setProperties();
		Logger.getLogger(PlatformController.class).debug("Textfield-Key: "+key);
		this.key = key;
	}
	
	//TODO Konsoliedieren der Konstruktoren (nicht alle notwendig)
	/**
	 * Constructor to create new <code>BHTextField</code>. Defined for the use with unkeyed text
	 * 
	 * @param key
	 *            unique key
	 * @param value
	 *            default value
	 */
	public BHTextField(Object key, String value) {
		super(value);
		this.setProperties();
		Logger.getLogger(PlatformController.class).debug("Textfield-Key: "+key);
		this.key = key;
	}
	

	/**
	 * Constructor to create new <code>BHTextField</code>. with key based text
	 * 
	 * @param key
	 *            unique key
	 */
	public BHTextField(String key) {
		//super(Services.getTranslator().translate(key));
		this.setProperties();
		this.key = key;
	}
	
	/**
	 * Constructor to create new <code>BHTextField</code>. with key based text
	 * 
	 * @param key
	 *            unique key
	 */
	public BHTextField(Object key) {
		//super(Services.getTranslator().translate(key));
		this.setProperties();
		this.key = key.toString();
	}

	public String getKey() {
		return key.toString();
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

	public void setInputHint(String inputHint) {
		this.inputHint = inputHint;
	}
	
	public String getInputHint() {
		return this.inputHint;
	}

        public String getValue() {
            if(this.getText() == null){
                return "";
            }
            return this.getText();
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
		throw new UnsupportedOperationException("This method has not been implemented");
	}
	
	/**
	 * Handle PlatformEvents
	 */
	@Override
	public void platformEvent(PlatformEvent e) {
	}
	
	/**
	 * Reset Text if necessary.
	 */
	@Override
	public void resetText() {
	}

}
