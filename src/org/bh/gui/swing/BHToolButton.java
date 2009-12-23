package org.bh.gui.swing;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import org.bh.platform.Services;

/**
 * 
 * BHToolButton to display buttons on tool bar 
 *
 * <p>
 * This class extends the Swing <code>JButton</code> to display buttons with special icons
 * on screen.
 *
 * @author Tietze.Patrick
 * @version 0.1, 2009/12/16
 *
 */

public class BHToolButton extends BHButton{
    
    public String toolTip;
    public String buttonName;
    JFileChooser fc;
	
   
    
    public BHToolButton(String imageName,String actionCommand,String toolTipText,String altText){
	
	
    	super(actionCommand, toolTipText);
    	
    	//Look for the image.
        String imgLocation = "/toolbarButtonGraphics/general/" + imageName + ".gif";
		//String imgLocation = "images/3D-blue/"+imageName+".png";
        
        URL imageURL = BHToolBar.class.getResource(imgLocation);
        
        toolTip = toolTipText;
        
        buttonName = altText;
        
        
        //setIcon(new ImageIcon(imgLocation));
        //setPreferredSize(new Dimension(25, 25));
        
        if (imageURL != null) {                      
            //image found
            setIcon(new ImageIcon(imageURL, altText));
        } else {                                     
            //no image found
            setText(altText);
            System.err.println("Resource not found: "+ imgLocation);
        }    
    }
    
    public String getToolTip(){
	    return toolTip;
	}

}
