package org.bh.gui.swing;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.event.MouseInputAdapter;

import org.bh.data.types.IValue;
import org.bh.platform.PlatformKey;
import org.bh.platform.i18n.BHTranslator;

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
public class BHButton extends JButton implements IBHComponent,IBHAction {

	private String key;
	private PlatformKey platformKey;
	private int[] validateRules;

	private static List<IBHAction> platformItems = new ArrayList<IBHAction>();
	BHTranslator translator = BHTranslator.getInstance(); 
	
	public static final Boolean ISPLATFORMBUTTON = true;

	

	/**
	 * Secondary constructor for platform buttons (are added to
	 * platformButtons-list and uses ENUM Platform Key instead of String for key)
	 * 
	 * @param key
	 * @param isPlatformButton
	 *            adds button to platformbutton-list
	 */
	public BHButton(PlatformKey platformKey, Boolean isPlatformButton) {
		super();
		this.setProperties();
		this.platformKey = platformKey;
		this.key = platformKey.toString();
		//set Text of Button
		this.setText(translator.translate(key.toString()));
		
		//set ToolTip if available
		this.setToolTipText((translator.translateToolTip(key.toString())));
		
		if (isPlatformButton)
			platformItems.add(this);
	}
	
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
	
	public BHButton(Boolean noUse){
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
	
	
	public PlatformKey getPlatformKey(){
		
		return this.platformKey;
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

	/**
	 * set properties of instance.
	 */
	private void setProperties() {
		this.putClientProperty("JComponent.sizeVariant", IBHComponent.MINI); // Minibutton
	}


	@Override
	public List<IBHAction> getPlatformItems() {
		return this.platformItems;
	}


	@Override
	public Boolean isPlatformItem() {
		if(this.platformKey != null)
			return true;
		return false;
	}
	
	@Override
	public String getBHToolTip() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}
	
	
	
	
	
	class BHToolTipListener extends MouseInputAdapter{
    	
    	public void mouseEntered(MouseEvent e){
    		
    	}
    	
    	public void mouseExited(MouseEvent e){
    		
    		
    	}
    	
    }



}
