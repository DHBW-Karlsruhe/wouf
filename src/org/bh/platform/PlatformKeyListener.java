package org.bh.platform;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * 
 * The PlatformKeyListener handles all actions that are fired by a keyboard key
 *
 * @author Tietze.Patrick
 * @version 1.0, 13.01.2010
 *
 */
public class PlatformKeyListener implements KeyListener{

	PlatformActionListener pal;
	
	public PlatformKeyListener(PlatformActionListener pal) {
		this.pal = pal;
	}
	
	public void keyPressed(KeyEvent e) {

		if(e.getKeyCode() == KeyEvent.VK_F5){				
			pal.createProject();
		} else if(e.getKeyCode() == KeyEvent.VK_F6){		
			pal.createScenario();
		} else if(e.getKeyCode() == KeyEvent.VK_F7){		
			pal.createPeriod();
		} else if(e.getKeyCode() == KeyEvent.VK_F1){		
			pal.openUserHelp("userhelp");
		} else if(e.getKeyCode() == KeyEvent.VK_INSERT){	
			pal.fileOpen();
		}
	}

	public void keyReleased(KeyEvent e) {
		//not necessary
	}

	public void keyTyped(KeyEvent e) {
		//not necessary
	}

}
