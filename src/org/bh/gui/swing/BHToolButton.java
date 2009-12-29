package org.bh.gui.swing;

import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.event.MouseInputAdapter;

import org.bh.platform.PlatformKey;
import org.bh.platform.i18n.BHTranslator;

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
    
    public String buttonName;
    JFileChooser fc;
    BHTranslator translator = BHTranslator.getInstance(); 
    
    
    public BHToolButton(PlatformKey key, String imageName){
	
	
    	super(key, BHButton.ISPLATFORMBUTTON);

    	
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
            //no image found
            System.err.println("Resource not found: "+ imgLocation);
        }    
    }
   
}
