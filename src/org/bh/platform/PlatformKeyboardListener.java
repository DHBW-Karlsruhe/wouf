package org.bh.platform;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.bh.gui.swing.BHMainFrame;

public class PlatformKeyboardListener implements KeyListener{
	
	

	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == (char) KeyEvent.VK_DELETE){
			System.out.println("TESCHD");
			
		}
	}

	public void keyReleased(KeyEvent e) {
		//Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent e) {
		//Auto-generated method stub
		
	}

}
