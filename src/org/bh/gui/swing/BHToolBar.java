package org.bh.gui.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import javax.swing.*;


/*
 * This class contains all contents of the toolbar
 * jlfgr-1_0.jar is a collection of toolbar button graphics
 * 
 * @author Patrick Tietze
 * @version 0.1, 03/12/2009
 * 
 */


public class BHToolBar extends JToolBar{
   

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
    static final private String OPEN = "open";
    static final private String NEW = "new";
    static final private String SAVE = "save";
    static final private String ADD = "Add";
    static final private String EDIT = "Edit";
    static final private String FIND = "find";
        
    JLabel lable;
    JComboBox comboBox;
    
    JButton bNew, bOpen, bSave, bAdd, bEdit, bFind;
    
//    javax.help.HelpSet helpSet = null;
//    javax.help.HelpBroker helpBroker = null;
//
//    JFileChooser fc;
    
    public BHToolBar(int width, int height) {
	
	//paint background
	setOpaque(true);
	//don't allow to relocate the bar
	setFloatable(false);
	
	setSize(width, height);
	
	bNew = new ToolButton("New24", NEW, "Create a new project", "New");
	bOpen = new ToolButton("Open24", OPEN, "Open an excisting project","Open");
	bSave = new ToolButton("Save24", SAVE, "Save project", "Save");
	bAdd = new ToolButton("Add24", ADD, "Add a project", "Add");
	bEdit = new ToolButton("Edit24", EDIT, "Edit a project", "Edit");
	bFind = new ToolButton("Find24", FIND, "Find project", "Find");
	
	lable = new JLabel("Methode: ");
	String methods[] = {"Berechnungsmethode 1", "Berechnungsmethode 2", "Berechnungsmethode 3"};
	comboBox = new JComboBox(methods);

//	fc = new JFileChooser();
//	fc.setSize(300, 200);
  
	add(bNew);
	add(bOpen);
	add(bSave);
	
	addSeparator();
	
	add(bAdd);
	add(bEdit);
	add(bFind);
	
	addSeparator();
	
	add(lable);
	add(comboBox);
    }

    class ToolButton extends JButton implements MouseListener, ActionListener{

	
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	String toolTip;
	String buttonName;
	

	public ToolButton(String imageName,String previous,String toolTipText,String altText){
            //Look for the image.
            String imgLocation = "/toolbarButtonGraphics/general/" + imageName + ".gif";
	    //String imgLocation = "images/3D-blue/"+imageName+".png";
            
            URL imageURL = BHToolBar.class.getResource(imgLocation);
            toolTip = toolTipText;
            buttonName = altText;
            
            //Create and initialize the button.
            setActionCommand(previous);
            //setToolTipText(toolTipText);
            //this.toolTipText = toolTipText;
            
            addActionListener(this);
            addMouseListener(this);
            
            //setIcon(new ImageIcon(imgLocation));
            
            //setPreferredSize(new Dimension(25, 25));
            
            if (imageURL != null) {                      //image found
            setIcon(new ImageIcon(imageURL, altText));
            } else {                                     //no image found
            setText(altText);
            System.err.println("Resource not found: "
            + imgLocation);
            }
            
     
        }
	
	public String getToolTip(){
	    return toolTip;
	}
	
	public String getButtonName(){
	    return buttonName;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	    
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	    //System.out.println(getToolTip());
	    BHStatusBar.setToolTip(getToolTip());
	
	}

	@Override
	public void mouseExited(MouseEvent e) {
	    BHStatusBar.setToolTip("");
	}

	@Override
	public void mousePressed(MouseEvent e) {
	   
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	    
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	    
//	   URL hsURL = HelpSet.findHelpSet(null, "BHHelp.hs");
//	   System.out.println(hsURL);
//	     try {
//		helpSet = new HelpSet(null, hsURL );
//		helpBroker = helpSet.createHelpBroker();
//		helpBroker.enableHelpOnButton(this, "index", helpSet);
//		
//		     
//		
//	    } catch (HelpSetException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	    }
//	    
	    //Handle open button action.
	    
	    if (e.getSource() == bOpen) {
	        //fc.showOpenDialog(this);
	   } 
	     
	}

    }
}

