package org.bh.gui.swing;

import java.awt.event.*;
import javax.swing.*;

/**
 * BHMenuBar to display a menu bar in screen.
 * 
 * <p>
 * This class extends the Swing <code>JMenuBar</code> to display a menu bar
 * on the screen.
 * 
 * @author Tietze.Patrick
 * @version 0.1, 2009/12/16
 * 
 */

public class BHMenuBar extends JMenuBar implements ActionListener{
    
    private static final long serialVersionUID = 1L;

    public BHMenuBar(){
        
    	/**
         * create menu bar
         **/
	
        //create menu --> File
        JMenu menuFile = new JMenu("Datei");
        //menuFile.setMnemonic(KeyEvent.VK_D);
        add(menuFile);
        
        //create menu --> Project
        JMenu menuProject = new JMenu("Project");
        //menuProject.setMnemonic(KeyEvent.VK_P);
        add(menuProject);
    
        //create menu --> Scenario
        JMenu menuScenario = new JMenu("Szenario");
        //menuScenario.setMnemonic(KeyEvent.VK_S);
        add(menuScenario);
        
        //create menu --> Bilanz & GuV
        JMenu menuBilanzGuV = new JMenu("Bilanz & GuV");
        //menuBilanzGuV.setMnemonic(KeyEvent.VK_B);
        add(menuBilanzGuV);
        
        //create menu --> Options
        JMenu menuOptions = new JMenu("Optionen");
        //menuOptions.setMnemonic(KeyEvent.VK_O);
        add(menuOptions);
        
        //create menu --> Help
        JMenu menuHelp = new JMenu("Hilfe");
        //menuHelp.setMnemonic(KeyEvent.VK_H);
        add(menuHelp);
        
        /**
         * create menu items --> file
         **/
                
        menuFile.add(new BHMenuItem("key", 1, 12, "new"));
        menuFile.add(new BHMenuItem("key", 1, 12, "open"));
        menuFile.add(new BHMenuItem("key", 1, 12, "close"));
        menuFile.add(new BHMenuItem("key", 1, 12, "save"));
        menuFile.add(new BHMenuItem("key", 1, 12, "saveAs"));
        menuFile.add(new BHMenuItem("key", 1, 12, "close"));
           
       
        /**
         * create menu items --> project
         **/
        
        menuProject.add(new BHMenuItem("key", 1, 12, "create"));
        menuProject.add(new BHMenuItem("key", 1, 12, "rename"));
        menuProject.add(new BHMenuItem("key", 1, 12, "duplicate"));
        menuProject.add(new BHMenuItem("key", 1, 12, "create"));
        menuProject.add(new BHMenuItem("key", 1, 12, "import"));
        menuProject.add(new BHMenuItem("key", 1, 12, "export"));
        menuProject.add(new BHMenuItem("key", 1, 12, "delete"));
          
        /**
         * create menu items --> scenario
         **/
        
        menuScenario.add(new BHMenuItem("key", 1, 12, "create"));
        menuScenario.add(new BHMenuItem("key", 1, 12, "rename"));
        menuScenario.add(new BHMenuItem("key", 1, 12, "duplicate"));
        menuScenario.add(new BHMenuItem("key", 1, 12, "move"));
        menuScenario.add(new BHMenuItem("key", 1, 12, "delete"));
      
        /**
         * create menu items --> Bilanz & GuV
         **/
        
        menuBilanzGuV.add(new BHMenuItem("key", 1, 12, "show"));
        menuBilanzGuV.add(new BHMenuItem("key", 1, 12, "create"));
        menuBilanzGuV.add(new BHMenuItem("key", 1, 12, "import"));
        menuBilanzGuV.add(new BHMenuItem("key", 1, 12, "edit"));
        menuBilanzGuV.add(new BHMenuItem("key", 1, 12, "delete"));
              
        /**
         * create menu items --> options
         **/
      
        menuOptions.add(new BHMenuItem("key", 1, 12, "change"));
        
        /**
         * create menu items --> options
         **/
      
        menuHelp.add(new BHMenuItem("key", 1, 12, "userHelp"));
        menuHelp.add(new BHMenuItem("key", 1, 12, "mathHelp"));
        menuHelp.add(new BHMenuItem("key", 1, 12, "about"));
        
      
    }
    

    //ActionListener
    public void actionPerformed(ActionEvent e) {
	if ("new".equals(e.getActionCommand())) {
            
        } else if ("open".equals(e.getActionCommand())){
        	
        } else {
            
        }
    }

}