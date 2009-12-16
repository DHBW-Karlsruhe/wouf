package org.bh.gui.swing;

import java.awt.event.*;
import javax.swing.*;

/*
 * This class contains all contents of the menu bar
 * 
 * @author Patrick Tietze
 * @version 0.1, 03/12/2009
 * 
 */

public class BHMenuBar extends JMenuBar implements ActionListener{
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    public BHMenuBar(){
        /*
         * create menu bar
         */
	
        //create menu --> File
        JMenu menuDatei = new JMenu("Datei");
        menuDatei.setMnemonic(KeyEvent.VK_D);
        add(menuDatei);
        
        //create menu --> Extras
        JMenu menuExtras = new JMenu("Extras");
        menuExtras.setMnemonic(KeyEvent.VK_E);
        add(menuExtras);
    
        //create menu --> Help
        JMenu menuHilfe = new JMenu("Hilfe");
        menuHilfe.setMnemonic(KeyEvent.VK_H);
        add(menuHilfe);
        
        /*
         * create menu items --> file
         */
        
        //file -> new
        JMenuItem menuDateiNeu = new JMenuItem("Neu");
        menuDateiNeu.setMnemonic(KeyEvent.VK_N);
        menuDateiNeu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
        menuDateiNeu.setActionCommand("neu");
        menuDateiNeu.addActionListener(this);
        menuDatei.add(menuDateiNeu);
        
        //file -> open
        JMenuItem menuDateiOpen = new JMenuItem("�ffnen");
        menuDateiOpen.setMnemonic(KeyEvent.VK_O);
        menuDateiOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.ALT_MASK));
        menuDateiOpen.setActionCommand("�ffnen");
        menuDateiOpen.addActionListener(this);
        menuDatei.add(menuDateiOpen);
        
        //file -> save
        JMenuItem menuDateiSpeichern = new JMenuItem("Speichern");
        menuDateiSpeichern.setMnemonic(KeyEvent.VK_S);
        menuDateiSpeichern.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
        menuDateiSpeichern.setActionCommand("speichern");
        menuDateiSpeichern.addActionListener(this);
        menuDatei.add(menuDateiSpeichern);  
        
        //file -> save as
        JMenuItem menuDateiSpeichernUnter = new JMenuItem("Speichern unter...");
        menuDateiSpeichernUnter.setActionCommand("speichernUnter");
        menuDateiSpeichernUnter.addActionListener(this);
        menuDatei.add(menuDateiSpeichernUnter);   
        
        //file -> import
        JMenuItem menuDateiImport = new JMenuItem("Import");
        menuDateiImport.setMnemonic(KeyEvent.VK_I);
        menuDateiImport.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.ALT_MASK));
        menuDateiImport.setActionCommand("import");
        menuDateiImport.addActionListener(this);
        menuDatei.add(menuDateiImport);  
        
        //file -> export
        JMenuItem menuDateiExport = new JMenuItem("Export");
        menuDateiExport.setMnemonic(KeyEvent.VK_E);
        menuDateiExport.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
        menuDateiExport.setActionCommand("export");
        menuDateiExport.addActionListener(this);
        menuDatei.add(menuDateiExport);  
                
        //file -> exit
        JMenuItem menuDateiBeenden = new JMenuItem("Beenden");
        menuDateiBeenden.setMnemonic(KeyEvent.VK_X);
        menuDateiBeenden.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.ALT_MASK));
        menuDateiBeenden.setActionCommand("beenden");
        menuDateiBeenden.addActionListener(this);
        menuDatei.add(menuDateiBeenden);
        
        /*
         * create menu items --> extras
         */
        
        //extras -> preferences
        JMenuItem menuExtrasEinstellungen = new JMenuItem("Einstellungen");
        menuExtrasEinstellungen.setMnemonic(KeyEvent.VK_E);
        menuExtrasEinstellungen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
        menuExtrasEinstellungen.setActionCommand("einstellungen");
        menuExtrasEinstellungen.addActionListener(this);
        menuExtras.add(menuExtrasEinstellungen);  
        
        //extras -> install plugin
        JMenuItem menuExtrasPlugin = new JMenuItem("Plugin installieren");
        menuExtrasPlugin.setMnemonic(KeyEvent.VK_P);
        menuExtrasPlugin.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
        menuExtrasPlugin.setActionCommand("plugin");
        menuExtrasPlugin.addActionListener(this);
        menuExtras.add(menuExtrasPlugin);  
        
        /*
         * create menu items --> help
         */
        
        //help -> HowTo
        JMenuItem menuHilfeHowTo = new JMenuItem("HowTo");
        menuHilfeHowTo.setMnemonic(KeyEvent.VK_H);
        menuHilfeHowTo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.ALT_MASK));
        menuHilfeHowTo.setActionCommand("howto");
        menuHilfeHowTo.addActionListener(this);
        menuHilfe.add(menuHilfeHowTo);  
        
        //help -> about
        JMenuItem menuHilfeUeber = new JMenuItem("�ber...");
        menuHilfeUeber.setMnemonic(KeyEvent.VK_U);
        menuHilfeUeber.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.ALT_MASK));
        menuHilfeUeber.setActionCommand("�ber");
        menuHilfeUeber.addActionListener(this);
        menuHilfe.add(menuHilfeUeber);  
       
    
    }
    
    //ActionListener
    public void actionPerformed(ActionEvent e) {
	if ("neu".equals(e.getActionCommand())) {
            
        } else if ("�ffnen".equals(e.getActionCommand())){
        	
        } else {
            
        }
    }

}