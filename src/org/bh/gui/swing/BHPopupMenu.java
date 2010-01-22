package org.bh.gui.swing;

import javax.swing.JPopupMenu;
import javax.swing.border.BevelBorder;
/**
 * 
 * Class to create the Contextmenu
 *
 * <p>
 * new PopupMenu for Contextmenu is created
 *
 * @author Lars.Zuckschwerdt
 * @version 1.0, 22.01.2010
 *
 */
public class BHPopupMenu extends JPopupMenu{
	JPopupMenu popup;
	public BHPopupMenu(){
		popup = new JPopupMenu();
		popup.setLabel("Justification");
	    popup.setBorder(new BevelBorder(BevelBorder.RAISED));	    
    };
}
