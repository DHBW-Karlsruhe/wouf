package org.bh.gui.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

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

public class BHToolButton extends JButton implements MouseListener, ActionListener{
    
    public String toolTip;
    public String buttonName;
	
    
    public BHToolButton(String imageName,String actionCommand,String toolTipText,String altText){
	
	//Look for the image.
        String imgLocation = "/toolbarButtonGraphics/general/" + imageName + ".gif";
		//String imgLocation = "images/3D-blue/"+imageName+".png";
        
        URL imageURL = BHToolBar.class.getResource(imgLocation);
        toolTip = toolTipText;
        buttonName = altText;
        
        //Create and initialize the button.
        setActionCommand(actionCommand);
        	//setToolTipText(toolTipText);
        	//this.toolTipText = toolTipText;
        
        addActionListener(this);
        addMouseListener(this);
        
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
    
    
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		BHStatusBar.setToolTip(getToolTip());
		//BHStatusBar.setValidationToolTip(new JLabel("Test"));
	}
	@Override
	public void mouseExited(MouseEvent e) {
		BHStatusBar.setToolTip("");
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}
	
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		String cmd = actionEvent.getActionCommand();
	        
	    // Handle each button.
	    if (cmd.equals("addP")) { //add project button clicked
	    	System.out.println("add project");
	    	BHTree.addProject("New Project " + BHTreeBar.getNodeSuffix());
	    } else if(cmd.equals("addS")){
	    	BHTree.addScenario("New Scenario");
        } else if (cmd.equals("remove")) {
            //Remove button clicked
            BHTree.removeCurrentNode();
        } else if (cmd.equals("delete")) {
            //Clear button clicked.
            BHTree.clear();
        }
	       
	 }
}
