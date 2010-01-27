package org.bh.gui.view;

import java.util.EventListener;

public interface IViewListener extends EventListener {
	/**
	 * Invoked when the view fires an event.
	 * 
	 * @param e
	 *            The event.
	 */
	public void viewEvent(ViewEvent e);
}
