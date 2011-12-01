/*******************************************************************************
 * Copyright 2011: Matthias Beste, Hannes Bischoff, Lisa Doerner, Victor Guettler, Markus Hattenbach, Tim Herzenstiel, Günter Hesse, Jochen Hülß, Daniel Krauth, Lukas Lochner, Mark Maltring, Sven Mayer, Benedikt Nees, Alexandre Pereira, Patrick Pfaff, Yannick Rödl, Denis Roster, Sebastian Schumacher, Norman Vogel, Simon Weber 
 *
 * Copyright 2010: Anna Aichinger, Damian Berle, Patrick Dahl, Lisa Engelmann, Patrick Groß, Irene Ihl, Timo Klein, Alena Lang, Miriam Leuthold, Lukas Maciolek, Patrick Maisel, Vito Masiello, Moritz Olf, Ruben Reichle, Alexander Rupp, Daniel Schäfer, Simon Waldraff, Matthias Wurdig, Andreas Wußler
 *
 * Copyright 2009: Manuel Bross, Simon Drees, Marco Hammel, Patrick Heinz, Marcel Hockenberger, Marcus Katzor, Edgar Kauz, Anton Kharitonov, Sarah Kuhn, Michael Löckelt, Heiko Metzger, Jacqueline Missikewitz, Marcel Mrose, Steffen Nees, Alexander Roth, Sebastian Scharfenberger, Carsten Scheunemann, Dave Schikora, Alexander Schmalzhaf, Florian Schultze, Klaus Thiele, Patrick Tietze, Robert Vollmer, Norman Weisenburger, Lars Zuckschwerdt
 *
 * Copyright 2008: Camil Bartetzko, Tobias Bierer, Lukas Bretschneider, Johannes Gilbert, Daniel Huser, Christopher Kurschat, Dominik Pfauntsch, Sandra Rath, Daniel Weber
 *
 * This program is free software: you can redistribute it and/or modify it un-der the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT-NESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package org.bh.platform;

import java.util.EventObject;

/**
 * Instances of this class are used to notify {@link IPlatformListener}s about
 * events which occured somewhere in the application and which are of importance
 * for the whole application.
 * 
 * @author Robert Vollmer
 * @author Marco Hammel
 */
public class PlatformEvent extends EventObject {
	private static final long serialVersionUID = 6392511944744301979L;

	private Type eventType;

	public static enum Type {
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
		/**
		 * save
		 */
		SAVE,
		/**
		 * save as
		 */
		SAVEAS,
		/**
		 * save all
		 */
		SAVEALL,
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
		return getClass().getName() + " [type=" + eventType + ", source="
				+ source + "]";
	}

}
