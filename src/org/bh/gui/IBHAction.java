package org.bh.gui;

import java.awt.event.ActionListener;

import org.bh.platform.PlatformKey;

public interface IBHAction {
	PlatformKey getPlatformKey();
	
	boolean isPlatformItem();
	
	void addActionListener(ActionListener al);
	
}
