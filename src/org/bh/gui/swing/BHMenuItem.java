package org.bh.gui.swing;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import org.bh.data.types.IValue;
import org.bh.platform.Services;
import org.bh.platform.actionkeys.PlatformActionKey;

/**
 * BHMenuItem to create and display new menu items in the menu bar.
 * 
 * <p>
 * This class extends the Swing <code>JMenuItem</code> to display menu items in
 * the menu bar
 * 
 * @author Tietze.Patrick
 * @version 0.1, 2009/12/16
 * 
 */

public class BHMenuItem extends JMenuItem implements IBHComponent {

	private String key;
	private PlatformActionKey actionKey;
	private int[] validateRules;
	private static List<BHMenuItem> platformMenuItems = new ArrayList<BHMenuItem>();
    private String inputHint;

	
	
	/**
	 * create the new menu item
	 * 
	 * @param key
	 * @param eventKey
	 *            : Dec number ASCII
	 * @param eventAction
	 * @param actionCommand
	 * @param isPlatformItem if set false MenuItem is not included in platformMenuItems-List
	 */
	public BHMenuItem(String key, int eventKey, PlatformActionKey actionKey, Boolean isPlatformItem, String inputHint) {
		super(Services.getTranslator().translate(key));
		this.key = key;
		if (eventKey != 0) {
			this.setMnemonic(eventKey);
			int metakey = 0;
			if ("Mac OS X".equals(System.getProperty("os.name"))) {
				metakey = ActionEvent.META_MASK;
			} else {
				metakey = ActionEvent.CTRL_MASK;
			}
			this.setAccelerator(KeyStroke.getKeyStroke(eventKey, metakey));
		}
		this.actionKey = actionKey;
		
		this.inputHint = inputHint;
		
		if(isPlatformItem)
			platformMenuItems.add(this);
	}
	

	/**
	 * create the new menu item
	 * -Constructor for regular use (w/o forPlatform parameter)
	 * 
	 */
	public BHMenuItem(String key, int eventKey, PlatformActionKey actionKey, String inputHint) {
		this(key, eventKey, actionKey, true, inputHint);
	}
	

	@Override
	public String getKey() {
		return key;
	}

	
	@Override
	public int[] getValidateRules() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void setValidateRules(int[] validateRules) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	public IValue getValue() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void setValue(IValue value) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    
	public String getInputHint() {
		return this.inputHint;
	}
	
	public PlatformActionKey getActionKey(){
		return this.actionKey;
	}
	
        
        
	/**
	 * Method to get all MenuItems that are generated for platforms 
	 * (i.e. that ones which have forPlatform = false not set)
	 */
	public static List<BHMenuItem> getPlatformMenuItems(){
		return platformMenuItems;
	}
}


