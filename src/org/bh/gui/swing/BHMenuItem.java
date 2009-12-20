package org.bh.gui.swing;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import org.bh.platform.i18n.BHTranslator;

/**
 * BHMenuItem to create and display new menu items in the menu bar.
 * 
 * <p>
 * This class extends the Swing <code>JMenuItem</code> to display menu items
 * in the menu bar
 * 
 * @author Tietze.Patrick
 * @version 0.1, 2009/12/16
 * 
 */

public class BHMenuItem extends JMenuItem implements IBHComponent{

	private String key;
    private int[] validateRules;
    static BHTranslator translator = BHTranslator.getInstance();
    
    /**
     * create the new menu item
     * @param key
     * @param eventKey: Dec number ASCII
     * @param eventAction
     * @param actionCommand
     */

    public BHMenuItem(String key, int eventKey, String actionCommand){
    	super(translator.translate(key));
    	this.key = key;
    	if(eventKey != 0){
    		this.setMnemonic(eventKey);
    		this.setAccelerator(KeyStroke.getKeyStroke(eventKey, ActionEvent.ALT_MASK));
    	}
    	this.setActionCommand(actionCommand);
    	//this.addActionListener(this);
    	
    	
    }
	
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public int[] getValidateRules() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public boolean isTypeValid() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public void setValidateRules(int[] validateRules) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

}
