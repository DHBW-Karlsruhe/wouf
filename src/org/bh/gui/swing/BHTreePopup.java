package org.bh.gui.swing;

import javax.swing.JPopupMenu;

import org.bh.platform.PlatformKey;


/**
 * 
 * PopupMenu für right-clicked tree nodes
 *
 * <p>
 * This popup provides some additional functions or a rather fast way to existing functions 
 * connected to the right-clicked node
 *
 * @author Zuckschwerdt.Lars
 * @author Schmalzhaf.Alexander
 * @version 1.0, 17.01.2010
 *
 */
public class BHTreePopup extends JPopupMenu{
	
	public enum Type{
		PROJECT,
		SCENARIO,
		PERIOD;
	}
	
	public BHTreePopup(Type type){
		
		
		this.add(new BHMenuItem(PlatformKey.POPUPADD));
		this.add(new BHMenuItem(PlatformKey.POPUPDUPLICATE));
		if(type == Type.PROJECT)
			this.add(new BHMenuItem(PlatformKey.POPUPEXPORT));
		this.addSeparator();
		this.add(new BHMenuItem(PlatformKey.POPUPREMOVE));
		
	}
	
	
	

}
