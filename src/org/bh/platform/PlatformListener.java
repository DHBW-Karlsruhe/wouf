package org.bh.platform;

import java.util.EventListener;

/**
 * The listener interface for receiving platform events.
 * 
 * @author Robert Vollmer
 * @version 1.0, 20.12.2009
 * 
 */
public interface PlatformListener extends EventListener {
	/**
	 * Invoked when the platform fires an event.
	 */
	public void platformEvent(PlatformEvent e);
}
