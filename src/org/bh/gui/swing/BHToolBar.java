package org.bh.gui.swing;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

import org.bh.platform.i18n.BHTranslator;


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
   

    private static final long serialVersionUID = 1L;
    
    static final private String OPEN = "open";
    static final private String NEW = "new";
    static final private String SAVE = "save";
    static final private String ADD = "Add";
    static final private String EDIT = "Edit";
    static final private String FIND = "find";
        
    BHLabel lable;
    JComboBox comboBox;
    
    BHToolButton bNew, bOpen, bSave, bAdd, bEdit, bFind;
    
    BHTranslator translator = BHTranslator.getInstance(); 

    
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
		
		bNew = new BHToolButton("New24", NEW, translator.translate("Tnew"), "New");
		bOpen = new BHToolButton("Open24", OPEN, translator.translate("Topen"),"Open");
		bSave = new BHToolButton("Save24", SAVE, translator.translate("Tsave"), "Save");
		bAdd = new BHToolButton("Add24", ADD, translator.translate("TaddP"), "Add");
		bEdit = new BHToolButton("Edit24", EDIT, translator.translate("TaddS"), "Edit");
		bFind = new BHToolButton("Find24", FIND, translator.translate("Tfind"), "Find");
		
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
		
		add(bAdd);
		add(bEdit);
		add(bFind);
		
			//addSeparator();
			//		
			//add(lable);
			//add(comboBox);
    }
}

