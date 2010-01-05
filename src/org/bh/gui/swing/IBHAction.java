package org.bh.gui.swing;

import java.awt.event.ActionListener;

import org.bh.platform.PlatformKey;

public interface IBHAction {
	public PlatformKey getPlatformKey();
	
	public boolean isPlatformItem();
	
	public void addActionListener(ActionListener al);
	
}
