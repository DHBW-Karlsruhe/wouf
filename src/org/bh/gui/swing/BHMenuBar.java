package org.bh.gui.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;

/**
 * BHMenuBar to display a menu bar in screen.
 * 
 * <p>
 * This class extends the Swing <code>JMenuBar</code> to display a menu bar on
 * the screen.
 * 
 * To use the shortcut keys, you should use the constant field values shown on
 * http://java.sun.com/j2se/1.4.2/docs/api/constant-values.html. If no shortcut
 * is necessary use '0' as the key.
 * 
 * @author Tietze.Patrick
 * @version 0.1, 2009/12/16
 * 
 */

public class BHMenuBar extends JMenuBar{

	ITranslator translator = Services.getTranslator();
	List<BHMenuItem> menuItems = new ArrayList<BHMenuItem>();
	
	
	public BHMenuBar() {

		/**
		 * create the menu bar with all the items
		 **/

		// create menu --> File
		JMenu menuFile = new JMenu(translator.translate("Mfile"));
		// menuFile.setMnemonic(KeyEvent.VK_D);
		add(menuFile);

		// create menu --> Project
		JMenu menuProject = new JMenu(translator.translate("Mproject"));
		// menuProject.setMnemonic(KeyEvent.VK_P);
		add(menuProject);

		// create menu --> Scenario
		JMenu menuScenario = new JMenu(translator.translate("Mscenario"));
		// menuScenario.setMnemonic(KeyEvent.VK_S);
		add(menuScenario);

		// create menu --> Bilanz & GuV
		JMenu menuBilanzGuV = new JMenu(translator
				.translate("MbalanceProfitLoss"));
		// menuBilanzGuV.setMnemonic(KeyEvent.VK_B);
		add(menuBilanzGuV);

		// create menu --> Options
		JMenu menuOptions = new JMenu(translator.translate("Moptions"));
		// menuOptions.setMnemonic(KeyEvent.VK_O);
		add(menuOptions);

		// create menu --> Help
		JMenu menuHelp = new JMenu(translator.translate("Mhelp"));
		// menuHelp.setMnemonic(KeyEvent.VK_H);
		add(menuHelp);

		/**
		 * create menu items --> file
		 **/
		
		BHMenuItem tempItem; 
		
		tempItem = new BHMenuItem("Mnew", 78, "new");
			menuItems.add(tempItem);
			menuFile.add(tempItem);
		tempItem = new BHMenuItem("Mopen", 79, "open");
			menuItems.add(tempItem);
			menuFile.add(tempItem);
		tempItem = new BHMenuItem("Mclose", 0, "close");
			menuItems.add(tempItem);
			menuFile.add(tempItem);
		tempItem = new BHMenuItem("Msave", 83, "save");
			menuItems.add(tempItem);
			menuFile.add(tempItem);
		tempItem = new BHMenuItem("MsaveAs", 0, "saveAs");
			menuItems.add(tempItem);
			menuFile.add(tempItem);
		tempItem = new BHMenuItem("Mquit", 88, "quit");
			menuItems.add(tempItem);
			menuFile.add(tempItem);


		/**
		 * create menu items --> project
		 **/
		
		tempItem = new BHMenuItem("Mcreate", 0, "create");
			menuItems.add(tempItem);
			menuProject.add(tempItem);
		tempItem = new BHMenuItem("Mrename", 0, "rename");
			menuItems.add(tempItem);
			menuProject.add(tempItem);
		tempItem = new BHMenuItem("Mduplicate", 0, "duplicate");
			menuItems.add(tempItem);
			menuProject.add(tempItem);
		tempItem = new BHMenuItem("Mimport", 0, "import");
			menuItems.add(tempItem);
			menuProject.add(tempItem);
		tempItem = new BHMenuItem("Mexport", 0, "export");
			menuItems.add(tempItem);
			menuProject.add(tempItem);
		tempItem = new BHMenuItem("Mremove", 0, "remove");
			menuItems.add(tempItem);
			menuProject.add(tempItem);


		/**
		 * create menu items --> scenario
		 **/
		tempItem = new BHMenuItem("Mcreate", 0, "create");
			menuItems.add(tempItem);
			menuScenario.add(tempItem);
		tempItem = new BHMenuItem("Mrename", 0, "rename");
			menuItems.add(tempItem);
			menuScenario.add(tempItem);
		tempItem = new BHMenuItem("Mduplicate", 0, "duplicate");
			menuItems.add(tempItem);
			menuScenario.add(tempItem);
		tempItem = new BHMenuItem("Mmove", 0, "move");
			menuItems.add(tempItem);
			menuScenario.add(tempItem);
		tempItem = new BHMenuItem("Mremove", 0, "remove");
			menuItems.add(tempItem);
			menuScenario.add(tempItem);

		/**
		 * create menu items --> Bilanz & GuV
		 **/
		tempItem = new BHMenuItem("Mshow", 66, "show");
			menuItems.add(tempItem);
			menuBilanzGuV.add(tempItem);
		tempItem = new BHMenuItem("Mcreate", 0, "create");
			menuItems.add(tempItem);
			menuBilanzGuV.add(tempItem);
		tempItem = new BHMenuItem("Mimport", 0, "import");
			menuItems.add(tempItem);
			menuBilanzGuV.add(tempItem);
		tempItem = new BHMenuItem("Medit", 0, "edit");
			menuItems.add(tempItem);
			menuBilanzGuV.add(tempItem);
		tempItem = new BHMenuItem("Mremove", 0, "remove");
			menuItems.add(tempItem);
			menuBilanzGuV.add(tempItem);


		/**
		 * create menu items --> options
		 **/
		tempItem = new BHMenuItem("Mchange", 80, "change");
			menuItems.add(tempItem);
			menuOptions.add(tempItem);
			
			
		/**
		 * create menu items --> options
		 **/
			
		tempItem = new BHMenuItem("MuserHelp", 72, "userHelp");
			menuItems.add(tempItem);
			menuHelp.add(tempItem);
		tempItem = new BHMenuItem("MmathHelp", 0, "mathHelp");
			menuItems.add(tempItem);
			menuHelp.add(tempItem);
		tempItem = new BHMenuItem("Minfo", 112, "about");
			menuItems.add(tempItem);
			menuHelp.add(tempItem);
	}
	
	public List<BHMenuItem> getBHMenuItems(){
		//TODO ausprogrammieren: Muss alle Kinder, Kindeskinder, ... zurückgeben
		return menuItems;
	}

}