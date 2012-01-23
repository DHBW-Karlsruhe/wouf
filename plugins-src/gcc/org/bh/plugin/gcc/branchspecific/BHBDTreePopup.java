package org.bh.plugin.gcc.branchspecific;

import javax.swing.JPopupMenu;

import org.bh.gui.swing.BHMenuItem;
import org.bh.gui.swing.tree.BHTreePopup;
import org.bh.gui.swing.tree.BHTreePopup.Type;
import org.bh.platform.PlatformKey;

/**
 * <short_description>
 *
 * <p>
 * <detailed_description>
 *
 * @author simon
 * @version 1.0, 18.01.2012
 *
 */
public class BHBDTreePopup extends JPopupMenu {


	
	public enum Type{
		PROJECT,
		SCENARIO,
		PERIOD;
	}
	
	public BHBDTreePopup(Type type){
		
		
		this.add(new BHMenuItem(PlatformKey.POPUPADD));
		this.add(new BHMenuItem(PlatformKey.POPUPDUPLICATE));
		if(type == Type.PROJECT)
			this.add(new BHMenuItem(PlatformKey.POPUPEXPORT));
		this.addSeparator();
		this.add(new BHMenuItem(PlatformKey.POPUPREMOVE));
		
	}

}
