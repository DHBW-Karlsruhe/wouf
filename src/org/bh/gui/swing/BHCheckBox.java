package org.bh.gui.swing;
import javax.swing.JCheckBox;

import org.apache.log4j.Logger;
import org.bh.data.types.IValue;
import org.bh.data.types.StringValue;
import org.bh.gui.CompValueChangeManager;
import org.bh.gui.swing.BHTextField;
import org.bh.gui.swing.IBHModelComponent;
import org.bh.platform.PlatformEvent;
import org.bh.platform.Services;
import org.bh.validation.ValidationRule;

/**
 * 
 * <p>
 * This class extends the Swing <code>JCheckBox</code> .
 * 
 * @author Kharitonov.Anton
 * @version 0.1, 2010/01/07
 * 
 */

// TODO Hints setzen!!! Noch werden für Textfields keine Hints erzeugt
public class BHCheckBox extends JCheckBox implements IBHComponent {
	/**
	 * unique key to identify Label.
	 */
	Object key;

	private ValidationRule[] validationRules = new ValidationRule[0];
	private String inputHint;
	private boolean changeListenerEnabled = true;
	private final CompValueChangeManager valueChangeManager = new CompValueChangeManager();
	private static final Logger log = Logger.getLogger(BHTextField.class);


	// TODO Konsoliedieren der Konstruktoren (nicht alle notwendig)


	/**
	 * Constructor to create new <code>BHCheckBox</code>. with key based text
	 * 
	 * @param key
	 *            unique key
	 */
	public BHCheckBox(String key) {
	    super();
	    this.key = key;
	}

	/**
	 * Constructor to create new <code>BHCheckBox</code>. with key based text
	 * 
	 * @param key
	 *            unique key
	 */
	public BHCheckBox(Object key) {
		super();
		this.key = key;
	}

	@Override
	public String getKey() {
		return key.toString();
	}

	public void setInputHint(String inputHint) {
		this.inputHint = inputHint;
	}

	// TODO ins Interface aufnehmen?
	public String getInputHint() {
		return this.inputHint;
	}

//	/**
//	 * set properties of instance.
//	 */
//	private void setProperties() {
//		Services.addPlatformListener(this);
//	}

	public String getBHHint() {

	    throw new UnsupportedOperationException("This method has not been implemented");
	}

	public void reloadText() {
	}

	public void platformEvent(PlatformEvent e) {
	    // TODO Auto-generated method stub
	    throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public String getHint() {
	    // TODO Auto-generated method stub
	    throw new UnsupportedOperationException("This method has not been implemented");
	}
}