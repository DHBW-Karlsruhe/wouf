package org.bh.gui.swing;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import org.bh.platform.Services;
import org.bh.platform.actionkeys.PlatformActionKey;
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
		menuFile.add(new BHMenuItem(PlatformActionKey.FILENEW, 78));
		menuFile.add(new BHMenuItem(PlatformActionKey.FILEOPEN, 79));
		menuFile.add(new BHMenuItem(PlatformActionKey.FILECLOSE));
		menuFile.add(new BHMenuItem(PlatformActionKey.FILESAVE, 83));
		menuFile.add(new BHMenuItem(PlatformActionKey.FILESAVEAS));
		menuFile.add(new BHMenuItem(PlatformActionKey.FILEQUIT, 88));


		/**
		 * create menu items --> project
		 **/
		menuProject.add(new BHMenuItem(PlatformActionKey.PROJECTCREATE));
		menuProject.add(new BHMenuItem(PlatformActionKey.PROJECTRENAME ));
		menuProject.add(new BHMenuItem(PlatformActionKey.PROJECTDUPLICATE));
		menuProject.add(new BHMenuItem(PlatformActionKey.PROJECTIMPORT));
		menuProject.add(new BHMenuItem(PlatformActionKey.PROJECTEXPORT));
		menuProject.add(new BHMenuItem(PlatformActionKey.PROJECTREMOVE));


		/**
		 * create menu items --> scenario
		 **/
		menuScenario.add(new BHMenuItem(PlatformActionKey.SCENARIOCREATE));
		menuScenario.add(new BHMenuItem(PlatformActionKey.SCENARIORENAME));
		menuScenario.add(new BHMenuItem(PlatformActionKey.SCENARIODUPLICATE));
		menuScenario.add(new BHMenuItem(PlatformActionKey.SCENARIOMOVE));
		menuScenario.add(new BHMenuItem(PlatformActionKey.SCENARIOREMOVE));

		
		/**
		 * create menu items --> Bilanz & GuV
		 **/
		menuBilanzGuV.add(new BHMenuItem(PlatformActionKey.BILANZGUVSHOW, 66));
		menuBilanzGuV.add(new BHMenuItem(PlatformActionKey.BILANZGUVCREATE));
		menuBilanzGuV.add(new BHMenuItem(PlatformActionKey.BILANZGUVIMPORT));
		menuBilanzGuV.add(new BHMenuItem(PlatformActionKey.BILANZGUVREMOVE));

		
		/**
		 * create menu items --> options
		 **/
		menuOptions.add(new BHMenuItem(PlatformActionKey.OPTIONSCHANGE, 80));
		
		
		/**
		 * create menu items --> options
		 **/
		menuHelp.add(new BHMenuItem(PlatformActionKey.HELPUSERHELP, 72));
		menuHelp.add(new BHMenuItem(PlatformActionKey.HELPMATHHELP));
		menuHelp.add(new BHMenuItem(PlatformActionKey.HELPINFO, 112));
	}
}