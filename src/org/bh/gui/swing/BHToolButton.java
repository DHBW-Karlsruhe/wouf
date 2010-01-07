package org.bh.gui.swing;

import java.net.URL;

import javax.swing.ImageIcon;

import org.apache.log4j.Logger;
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

public class BHToolButton extends BHButton{
	private static final long serialVersionUID = 5224665479917197926L;
	private static final Logger log = Logger.getLogger(BHToolButton.class);
    public String buttonName;
    
    public BHToolButton(PlatformKey key, String imageName){
    	super(key);
    	
    	//Look for the image.
        //String imgLocation = "/toolbarButtonGraphics/general/" + imageName + ".gif";
    	String imgLocation = "/org/bh/images/buttons/" + imageName + ".png";
		//String imgLocation = "images/3D-blue/"+imageName+".png";
        
        URL imageURL = BHToolBar.class.getResource(imgLocation);
        
        //setIcon(new ImageIcon(imgLocation));
        //setPreferredSize(new Dimension(25, 25));
        
        if (imageURL != null) {                      
            //image found
        	this.setText("");
            setIcon(new ImageIcon(imageURL, ""));
        } else {                                     
        	log.error("Resource not found: " + imgLocation);
        }    
    }

	@Override
	public void setText(String text) {
		// do not allow setting a text for this button
	}
   
}
