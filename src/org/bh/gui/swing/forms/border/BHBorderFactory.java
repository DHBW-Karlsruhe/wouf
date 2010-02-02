package org.bh.gui.swing.forms.border;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.bh.platform.Services;

public class BHBorderFactory {

	static BHBorderFactory instance = null;

	private BHBorderFactory() {

	}

	public static BHBorderFactory getInstacnce() {
		if (instance == null) {
			instance = new BHBorderFactory();
		}
		return instance;
	}

	public TitledBorder createTitledBorder(Border border, Object key) {
		BHTitledBorder tb = new BHTitledBorder(border, key);
		Services.addPlatformListener(tb);
		return tb;
	}
	
	public Border createEtchedBorder(int type)    {
		return BorderFactory.createEtchedBorder(type);
	}
	
	public Border createEtchedBorder()    {
		return BorderFactory.createEtchedBorder();
	}

	public Border createTitledBorder(Border border,
			Object key, int position, int justification) {
		return new BHTitledBorder(border, key, position,justification);
	}
}
