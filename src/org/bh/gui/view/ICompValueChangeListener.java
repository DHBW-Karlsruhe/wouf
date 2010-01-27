package org.bh.gui.view;

import java.util.EventListener;

import org.bh.gui.IBHModelComponent;

public interface ICompValueChangeListener extends EventListener {
	/**
	 * Invoked when the value of a component has changed.
	 * 
	 * @param e
	 *            The component whose value has changed.
	 */
	public void compValueChanged(IBHModelComponent comp);
}
