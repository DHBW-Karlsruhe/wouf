package org.bh.gui.swing;

import java.awt.event.ActionListener;
import java.util.List;

import org.bh.platform.PlatformKey;

public interface IBHAction {
	
	PlatformKey platformKey = null;
	static List<?> platformItems = null;
	
	public PlatformKey getPlatformKey();
	
	public Boolean isPlatformItem();
	
	public List<IBHAction> getPlatformItems();
	
	public void addActionListener(ActionListener al);
	
}
