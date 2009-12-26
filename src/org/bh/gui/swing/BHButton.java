package org.bh.gui.swing;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import org.bh.data.types.IValue;
import org.bh.platform.actionkeys.PlatformActionKey;

/**
 * BHButton to display buttons at screen.
 * 
 * <p>
 * This class extends the Swing <code>JButton</code> to display simple buttons
 * in Business Horizon.
 * 
 * @author Thiele.Klaus
 * @author Schmalzhaf.Alexander
 * 
 * @version 0.1, 2009/12/13
 * @version 0.1.1 2009/12/26
 * 
 */
public class BHButton extends JButton implements IBHComponent {

	private String key;
	private PlatformActionKey platformKey;
	private int[] validateRules;

	private static List<BHButton> platformButtons = new ArrayList<BHButton>();

	public static final Boolean ISPLATFORMBUTTON = true;

	/**
	 * Standard constructor to create buttons NOT for platform
	 * 
	 * @param key
	 */
	public BHButton(String key) {
		super();
		this.setProperties();
		this.key = key;
		//TODO get INPUT-HINT out of properties-File (querstions? Ask Alex)
	}


	/**
	 * Secondary constructor for platform buttons (are added to
	 * platformButtons-list and uses ENUM Platform Key instead of String for key)
	 * 
	 * @param key
	 * @param isPlatformButton
	 *            adds button to platformbutton-list
	 */
	public BHButton(PlatformActionKey platformKey, Boolean isPlatformButton) {
		super();
		this.setProperties();
		this.platformKey = platformKey;
		this.key = platformKey.toString();
		//TODO get INPUT-HINT out of properties-File (querstions? Ask Alex)
		
		if (isPlatformButton)
			platformButtons.add(this);
	}

	/**
	 * set the rules for the JGoodies validation
	 * 
	 * @param validateRules
	 */
	public void setValidateRules(int[] validateRules) {
		this.validateRules = validateRules;
	}

	/**
	 * return the key for value mapping
	 * 
	 * @return
	 */
	public String getKey() {
		return key;
	}
	
	
	public PlatformActionKey getPlatformKey(){
		
		return this.platformKey;
	}
	
	
	public Boolean isPlatformButton(){
		if(this.platformKey != null)
			return true;
		
		return false;
	}
	
	/**
	 * return the rules for the validation engine
	 * 
	 * @return
	 */
	public int[] getValidateRules() {
		return validateRules;
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

	public static List<BHButton> getPlatformButtons() {
		return platformButtons;
	}

	/**
	 * set properties of instance.
	 */
	private void setProperties() {
		this.putClientProperty("JComponent.sizeVariant", IBHComponent.MINI); // Minibutton
	}


	@Override
	public String getInputHint() {
		// TODO this method must be implemented well (Questions? Ask Alex)
		return null;
	}

}
