package org.bh.gui.swing;

import javax.swing.JFrame;

/**
 * This abstract class should be implemented by every popup.
 *
 * <p>
 * This abstract class should be implemented by every popup.
 *
 * @author Yannick Rödl
 * @version 1.0, 12.12.2011
 *
 */

@SuppressWarnings("serial")
public abstract class BHPopupFrame extends JFrame {

	public BHPopupFrame(){
		super();
		
		this.setSize(400, 400);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
}
