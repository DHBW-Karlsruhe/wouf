package org.bh.gui.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPopupMenu;
import javax.swing.border.BevelBorder;

public class BHPopupMenu extends JPopupMenu{
	JPopupMenu popup;
	public BHPopupMenu(){
		popup = new JPopupMenu();
	    ActionListener menuListener = new ActionListener() {
	        public void actionPerformed(ActionEvent event) {
	            System.out.println("Popup menu item ["
	                + event.getActionCommand() + "] was pressed.");
	          };
	    };
	   
	    popup.setLabel("Justification");
	    popup.setBorder(new BevelBorder(BevelBorder.RAISED));	    
    };
}
