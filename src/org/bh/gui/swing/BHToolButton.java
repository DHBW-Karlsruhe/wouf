package org.bh.gui.swing;

import java.net.URL;

import javax.swing.ImageIcon;

import org.apache.log4j.Logger;
import org.bh.gui.swing.comp.BHButton;
import org.bh.platform.PlatformKey;

/**
 * 
 * BHToolButton to display buttons on tool bar 
 *
 * <p>
 * This class extends the Swing <code>JButton</code> to display buttons with special icons
 * on screen.
 *
 * @author Tietze.Patrick
 * @author Schmalzhaf.Alexander
 * 
 * 
 * @version 0.1, 2009/12/16
 *
 */

public final class BHToolButton extends BHButton{
	private static final long serialVersionUID = 5224665479917197926L;
	private static final Logger log = Logger.getLogger(BHToolButton.class);
    public String buttonName;
    
    /**
     * Standard constructor for <code>BHToolButton</code>.
     * 
     * @param key
     * @param eventKey
     * @param imageName
     */
    public BHToolButton(PlatformKey key, int eventKey, String imageName){
    	super(key);
    	
    	//Look for the image.
    	String imgLocation = "/org/bh/images/buttons/" + imageName + ".png";
		 
        URL imageURL = BHToolBar.class.getResource(imgLocation);
        
        if (imageURL != null) {                      
            //image found
        	this.setText("");
            setIcon(new ImageIcon(imageURL, ""));
        } else {                                     
        	log.error("Resource not found: " + imgLocation);
        } 
    }

	public void setText(String text) {
		// do not allow setting a text for this button
	}
}
