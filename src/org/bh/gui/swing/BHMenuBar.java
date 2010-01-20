package org.bh.gui.swing;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import org.bh.BusinessHorizon;
import org.bh.platform.PlatformKey;
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
	
	
	public BHMenuBar() {

		/**
		 * create the menu bar with all the items
		 **/

		// create menu --> File
		JMenu menuFile = new JMenu(translator.translate("Mfile"));
		menuFile.setMnemonic(KeyEvent.VK_D);
		add(menuFile);

		// create menu --> Project
		JMenu menuProject = new JMenu(translator.translate("Mproject"));
		menuProject.setMnemonic(KeyEvent.VK_P);
		add(menuProject);

		// create menu --> Scenario
		JMenu menuScenario = new JMenu(translator.translate("Mscenario"));
		menuScenario.setMnemonic(KeyEvent.VK_S);
		add(menuScenario);

		// create menu --> Period
		JMenu menuPeriod = new JMenu(translator.translate("Mperiod"));
		menuPeriod.setMnemonic(KeyEvent.VK_E);
		add(menuPeriod);

		// create menu --> Options
		JMenu menuOptions = new JMenu(translator.translate("Moptions"));
		menuOptions.setMnemonic(KeyEvent.VK_E);
		add(menuOptions);

		// create menu --> Help
		JMenu menuHelp = new JMenu(translator.translate("Mhelp"));
		menuHelp.setMnemonic(KeyEvent.VK_H);
		add(menuHelp);
		
		//DummyMenuItem to realize the function of the "delete"-Key
		JMenu menuTemp = new JMenu();
		add(menuTemp);
		menuTemp.add(new BHMenuItem(PlatformKey.TOOLBARREMOVE, 127));

		/**
		 * create menu items --> file
		 **/
		menuFile.add(new BHMenuItem(PlatformKey.FILENEW, 78)); // N
		menuFile.add(new BHMenuItem(PlatformKey.FILEOPEN, 79)); // O
		menuFile.add(new BHMenuItem(PlatformKey.FILESAVE, 83)); // S
		menuFile.add(new BHMenuItem(PlatformKey.FILESAVEAS, 83)); // S
		menuFile.addSeparator();
		menuFile.add(new BHMenuItem(PlatformKey.FILECLOSE, 87)); // W
		menuFile.add(new BHMenuItem(PlatformKey.FILEQUIT, 81));  // Q


		/**
		 * create menu items --> project
		 **/
		menuProject.add(new BHMenuItem(PlatformKey.PROJECTCREATE, 114)); //F5
		menuProject.add(new BHMenuItem(PlatformKey.PROJECTDUPLICATE));
		menuProject.addSeparator();
		menuProject.add(new BHMenuItem(PlatformKey.PROJECTIMPORT));
		menuProject.add(new BHMenuItem(PlatformKey.PROJECTEXPORT));
		menuProject.addSeparator();
		menuProject.add(new BHMenuItem(PlatformKey.PROJECTREMOVE));


		/**
		 * create menu items --> scenario
		 **/
		menuScenario.add(new BHMenuItem(PlatformKey.SCENARIOCREATE, 115)); //F6
		menuScenario.add(new BHMenuItem(PlatformKey.SCENARIODUPLICATE));
		menuScenario.addSeparator();
		menuScenario.add(new BHMenuItem(PlatformKey.SCENARIOREMOVE));

		/**
		 * create menu items --> scenario
		 **/
		menuPeriod.add(new BHMenuItem(PlatformKey.PERIODCREATE, 116)); //F7
		menuPeriod.add(new BHMenuItem(PlatformKey.PERIODDUPLICATE));
		menuPeriod.addSeparator();
		menuPeriod.add(new BHMenuItem(PlatformKey.PERIODREMOVE));
		
		
		/**
		 * create menu items --> options
		 **/
		menuOptions.add(new BHMenuItem(PlatformKey.OPTIONSCHANGE, 80));
		
		
		/**
		 * create menu items --> help
		 **/
		menuHelp.add(new BHMenuItem(PlatformKey.HELPUSERHELP, 112)); //F1
		menuHelp.add(new BHMenuItem(PlatformKey.HELPMATHHELP));
		menuHelp.addSeparator();
		if (BusinessHorizon.DEBUG) 
			menuHelp.add(new BHMenuItem(PlatformKey.HELPDEBUG));
		menuHelp.add(new BHMenuItem(PlatformKey.HELPINFO));
	}
}