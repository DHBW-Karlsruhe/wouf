package org.bh.gui.swing;

import javax.swing.*;

import org.bh.platform.PlatformKey;

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
    
    BHToolButton bOpen, bSave, bAddPro, bAddS, bAddPer, bRemoveS, bRemovePer;

    
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
		
		bOpen = new BHToolButton(PlatformKey.TOOLBAROPEN, "Bopen");
		bSave = new BHToolButton(PlatformKey.TOOLBARSAVE, "Bsave");
		bAddPro = new BHToolButton(PlatformKey.TOOLBARADDPRO, "BnewProject");
		bAddS = new BHToolButton(PlatformKey.TOOLBARADDS, "BnewScenario2");
		bAddPer = new BHToolButton(PlatformKey.TOOLBARADDPER, "BnewPeriod2");
		bRemoveS = new BHToolButton(PlatformKey.TOOLBARREMOVES, "BdeleteScenario2");
		bRemovePer = new BHToolButton(PlatformKey.TOOLBARREMOVEPER, "BdeletePeriod");
		
		//old Buttons from library jlfgr-1_0.jar
//		bNew = new BHToolButton(PlatformKey.TOOLBARNEW, "Bnew");
//		bOpen = new BHToolButton(PlatformKey.TOOLBAROPEN, "Open24");
//		bSave = new BHToolButton(PlatformKey.TOOLBARSAVE, "Save24");
//		bAddP = new BHToolButton(PlatformKey.TOOLBARADDP, "Add24");
//		bAddS = new BHToolButton(PlatformKey.TOOLBARADDS, "Edit24");
//		bRemove = new BHToolButton(PlatformKey.TOOLBARREMOVE, "Remove24");
//		bDelete = new BHToolButton(PlatformKey.TOOLBARDELETE, "Delete24");

			//example of combo box in tool bar
			//lable = new BHLabel("Methode: ", "value");
			//String methods[] = {"Berechnungsmethode 1", "Berechnungsmethode 2", "Berechnungsmethode 3"};
			//comboBox = new JComboBox(methods);
		
			//FilChooser
			//fc = new JFileChooser();
			//fc.setSize(300, 200);
	  
		add(bOpen);
		add(bSave);
		
		addSeparator();
		
		add(bAddPro);
		add(bAddS);
		add(bAddPer);
		
		addSeparator();
		
		add(bRemoveS);
		add(bRemovePer);
		
		
	
    }
}

