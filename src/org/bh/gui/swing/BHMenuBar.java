package org.bh.gui.swing;

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
		menuFile.add(new BHMenuItem("Mnew", 78, "new", "HINT"));
		menuFile.add(new BHMenuItem("Mopen", 79, "open", "HINT"));
		menuFile.add(new BHMenuItem("Mclose", 0, "close", "HINT"));
		menuFile.add(new BHMenuItem("Msave", 83, "save", "HINT"));
		menuFile.add(new BHMenuItem("MsaveAs", 0, "saveAs", "HINT"));
		menuFile.add(new BHMenuItem("Mquit", 88, "quit", "HINT"));


		/**
		 * create menu items --> project
		 **/
		menuProject.add(new BHMenuItem("Mcreate", 0, "create", "HINT"));
		menuProject.add(new BHMenuItem("Mrename", 0, "rename", "HINT"));
		menuProject.add(new BHMenuItem("Mduplicate", 0, "duplicate", "HINT"));
		menuProject.add(new BHMenuItem("Mimport", 0, "import", "HINT"));
		menuProject.add(new BHMenuItem("Mexport", 0, "export", "HINT"));
		menuProject.add(new BHMenuItem("Mremove", 0, "remove", "HINT"));


		/**
		 * create menu items --> scenario
		 **/
		menuScenario.add(new BHMenuItem("Mcreate", 0, "create", "HINT"));
		menuScenario.add(new BHMenuItem("Mrename", 0, "rename", "HINT"));
		menuScenario.add(new BHMenuItem("Mduplicate", 0, "duplicate", "HINT"));
		menuScenario.add(new BHMenuItem("Mmove", 0, "move", "HINT"));
		menuScenario.add(new BHMenuItem("Mremove", 0, "remove", "HINT"));

		
		/**
		 * create menu items --> Bilanz & GuV
		 **/
		menuBilanzGuV.add(new BHMenuItem("Mshow", 66, "show", "HINT"));
		menuBilanzGuV.add(new BHMenuItem("Mcreate", 0, "create", "HINT"));
		menuBilanzGuV.add(new BHMenuItem("Mimport", 0, "import", "HINT"));
		menuBilanzGuV.add(new BHMenuItem("Mremove", 0, "remove", "HINT"));

		
		/**
		 * create menu items --> options
		 **/
		menuOptions.add(new BHMenuItem("Mchange", 80, "change", "HINT"));
		
		
		/**
		 * create menu items --> options
		 **/
		menuHelp.add(new BHMenuItem("MuserHelp", 72, "userHelp", "HINT"));
		menuHelp.add(new BHMenuItem("MmathHelp", 0, "mathHelp", "HINT"));
		menuHelp.add(new BHMenuItem("Minfo", 112, "about", "HINT"));
		menuHelp.add(new BHMenuItem("MuserHelp", 72, "userHelp", "HINT"));
	}
}