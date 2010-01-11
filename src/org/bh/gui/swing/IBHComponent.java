package org.bh.gui.swing;


/**
 * Every Swing element which shall use a defined DTO key must implement this
 * interface.
 * 
 * @author Marco Hammel
 * @author Thiele.Klaus
 * 
 * @version 0.2, 2009/12/25
 */
public interface IBHComponent {
	/**
	 * Have to be used by instancing a BHComponent representing a minimum of a
	 * intervall.
	 */
	String MINVALUE = "MIN_";
	/**
	 * Have to be used by instancing a BHComponent representing a maximum of a
	 * intervall.
	 */
	String MAXVALUE = "MAX_";

	/**
	 * Constant for Nimbus Look&Feel: Size for JComponent is mini.
	 */
	String MINI = "mini";

	/**
	 * No check for the DTO constants USe the DTO Enums to be conform.
	 * 
	 * @return value of a DTO key
	 */
	String getKey();

	/**
	 * Must return the Input Hint text of a component.
	 * 
	 * @return BHHint
	 */
	public String getInputHint();
}
