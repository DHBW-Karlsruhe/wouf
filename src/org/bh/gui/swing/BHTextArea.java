package org.bh.gui.swing;

import javax.swing.JTextArea;

import org.bh.platform.PlatformEvent;
import org.bh.platform.Services;

/**
 * BHTextArea to display simple text fields at screen.
 * 
 * <p>
 * This class extends the Swing <code>JTextArea</code> to display simple text
 * fields in Business Horizon.
 * 
 * @author Lars
 * @version 0.1, 2009/12/29
 * 
 */
public class BHTextArea extends JTextArea implements IBHComponent {
	/**
	 * unique key to identify Label.
	 */
	private String key;


	private int[] validateRules;
	private String inputHint;
	
	public BHTextArea(String key, String wert, int row, int column) {
		super(wert, row, column);
		this.setProperties();
		this.key = key;
		this.setEditable(false);
		this.setWrapStyleWord(true);
		this.setLineWrap(true);
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

	public void setInputHint(String inputHint) {
		this.inputHint = inputHint;
	}
	
	public String getInputHint() {
		return this.inputHint;
	}

        public String getValue() {
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
