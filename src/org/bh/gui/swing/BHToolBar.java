package org.bh.gui.swing;

import javax.swing.*;
import org.bh.platform.actionkeys.PlatformActionKey;

/**
 * 
 * BHToolBar offers quick access to functions
 *
 * <p>
 * This class offers the user quick access to important and frequently used functions.
 * The icons on the buttons are self explanatory and facilitate the work
 *
 * @author Tietze.Patrick
 * @version 0.1, 2009/12/03
 *
 */


public class BHToolBar extends JToolBar{
    
    BHToolButton bNew, bOpen, bSave, bAddP, bAddS, bRemove, bDelete, bFind;

    
    //HelpSet and HelpBroker are necessary for user help
    //javax.help.HelpSet helpSet = null;
    //javax.help.HelpBroker helpBroker = null;

    //JileChooser allows file loading
    //JFileChooser fc;
    
    public BHToolBar(int width, int height) {
	
		//paint background
		setOpaque(true);
		
		
		//don't allow to relocate the bar
		setFloatable(false);
		
		setSize(width, height);
		
		bNew = new BHToolButton(PlatformActionKey.TOOLBARNEW, "New24");
		bOpen = new BHToolButton(PlatformActionKey.TOOLBAROPEN, "Open24");
		bSave = new BHToolButton(PlatformActionKey.TOOLBARSAVE, "Save24");
		bAddP = new BHToolButton(PlatformActionKey.TOOLBARADDP, "Add24");
		bAddS = new BHToolButton(PlatformActionKey.TOOLBARADDS, "Edit24");
		bRemove = new BHToolButton(PlatformActionKey.TOOLBARREMOVE, "Remove24");
		bDelete = new BHToolButton(PlatformActionKey.TOOLBARDELETE, "Delete24");
		bFind = new BHToolButton(PlatformActionKey.TOOLBARFIND, "Find24");
		
			//example of combo box in tool bar
			//lable = new BHLabel("Methode: ", "value");
			//String methods[] = {"Berechnungsmethode 1", "Berechnungsmethode 2", "Berechnungsmethode 3"};
			//comboBox = new JComboBox(methods);
		
			//FilChooser
			//fc = new JFileChooser();
			//fc.setSize(300, 200);
	  
		add(bNew);
		add(bOpen);
		add(bSave);
		
		addSeparator();
		
		add(bAddP);
		add(bAddS);
		add(bRemove);
		add(bDelete);
		
		addSeparator();
		
		add(bFind);
    }
}

