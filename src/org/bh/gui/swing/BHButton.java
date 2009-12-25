package org.bh.gui.swing;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import org.bh.data.types.IValue;

/**
 * BHButton to display buttons at screen.
 * 
 * <p>
 * This class extends the Swing <code>JButton</code> to display simple buttons
 * in Business Horizon.
 * 
 * @author Thiele.Klaus
 * @version 0.1, 2009/12/13
 * 
 */
public class BHButton extends JButton implements IBHComponent {

	private String key;
	private int[] validateRules;
	private String inpuHint;

	private static List<BHButton> platformButtons = new ArrayList<BHButton>();

	public static final Boolean ISPLATFORMBUTTON = true;

	/**
	 * Standard constructor to create buttons NOT for platform
	 * 
	 * @param key
	 */
	public BHButton(String key, String inputHint) {
		super();
		this.setProperties();
		this.key = key;
		this.inpuHint = inputHint;
	}

	public BHButton(String key) {
		super();
		this.setProperties();
		this.key = key;
	}

	/**
	 * Secondary constructor for platform buttons (are added to
	 * platformButtons-list)
	 * 
	 * @param key
	 * @param isPlatformButton
	 *            adds button to platformbutton-list
	 */
	public BHButton(String key, Boolean isPlatformButton, String inputHint) {
		super();
		this.setProperties();
		this.key = key;
		this.inpuHint = inputHint;

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

	public String getInputHint() {
		return this.inpuHint;
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

}
