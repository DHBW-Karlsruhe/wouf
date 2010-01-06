/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bh.platform;

import java.util.EventObject;

import org.apache.log4j.Logger;

/**
 * 
 * @author Marco Hammel
 */
public class PlatformEvent extends EventObject {
	private static final long serialVersionUID = 6392511944744301979L;
	private static final Logger log = Logger.getLogger(PlatformEvent.class);

	private Type eventType;

	public static enum Type {
		/**
		 * plugin should call saveAll method
		 */
		SAVEALL,
		/**
		 * plugin should put the dto copy back to ui
		 */
		GETCOPY,
		/**
		 * Platform has been loaded completely
		 */
		PLATFORM_LOADING_COMPLETED,
		/**
		 * 
		 */
		DATA_CHANGED,
		/**
		 * The Locale has changed
		 */
		LOCALE_CHANGED,
	}

	/**
	 * Creates a new platform event, but does not fire it yet.
	 * 
	 * @param source
	 *            Must not be null.
	 * @param type
	 *            The type of the event.
	 */
	public PlatformEvent(Object source, Type type) {
		super(source);
		eventType = type;
		log.debug("Platform Event from " + source);
	}

	/**
	 * Returns the event type.
	 * 
	 * @return The event type.
	 */
	public Type getEventType() {
		return eventType;
	}

	@Override
	public String toString() {
		return getClass().getName() + " [source=" + source + ", type="
				+ eventType + "]";
	}

}
