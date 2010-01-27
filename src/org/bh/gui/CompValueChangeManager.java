package org.bh.gui;

import javax.swing.event.EventListenerList;

import org.bh.gui.view.ICompValueChangeListener;

public class CompValueChangeManager {
	private final EventListenerList listeners = new EventListenerList();
	
	public void addCompValueChangeListener(ICompValueChangeListener l) {
		listeners.add(ICompValueChangeListener.class, l);
	}

	public void removeCompValueChangeListener(ICompValueChangeListener l) {
		listeners.remove(ICompValueChangeListener.class, l);
	}

	public void fireCompValueChangeEvent(IBHModelComponent comp) {
		for (ICompValueChangeListener l : listeners.getListeners(ICompValueChangeListener.class))
			l.compValueChanged(comp);
	}
}
