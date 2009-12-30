package org.bh.gui.swing;

import javax.swing.JTextArea;

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

	/**
	 * set properties of instance.
	 */
	private void setProperties() {
		this.putClientProperty("JComponent.sizeVariant", IBHComponent.MINI); // Minitextfield
	}

	@Override
	public String getBHToolTip() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

}
