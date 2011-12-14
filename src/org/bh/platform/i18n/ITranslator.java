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
package org.bh.platform.i18n;

import java.beans.PropertyChangeListener;
import java.util.Locale;

/**
 * Defines the <code>Interface</code> for a Translation instance that can be
 * used to translate keys into domain-specific <code>String</code>s.
 * 
 * @author Thiele.Klaus
 * @versio 1.0, 2010/01/22
 * 
 */
public interface ITranslator {
	/**
	 * Parameter for short text translation.
	 */
	public static final int SHORT = 0;
	/**
	 * Parameter for regular text translation.
	 */
	public static final int REGULAR = 1;
	/**
	 * Parameter for long text translation.
	 */
	public static final int LONG = 2;

	/**
	 * Parameter for Mnemonic Key Character.
	 */
	public static final int MNEMONIC = 3;

	/**
	 * Method to return currently used Locale for Translation.
	 * 
	 * @return currently used <code>Locale</code>
	 * 
	 * 
	 */
	
	
	Locale getLocale();

	/**
	 * Sets a new locale to be used for further translations.
	 * 
	 * @param locale
	 *            locale to be set
	 */
	void setLocale(Locale locale);

	/**
	 * Core method to translate key to domain-specific <code>String</code>.
	 * 
	 * @param key
	 *            key to be translated
	 * @return translated <code>String</code>
	 */
	String translate(Object key);

	/**
	 * Alternate method for specific keys.
	 * 
	 * @param key
	 *            key to be translated.
	 * @param type
	 *            specific type of key.
	 * @return translated <code>String</code>
	 */
	String translate(Object key, int type);

	/**
	 * Checks if key is available for translation.
	 * 
	 * @param key
	 *            key to be checked
	 * @return true if contains else false.
	 */
	boolean containsKey(Object key);

	/**
	 * Returns the available Languages as <code>Locale</code>.
	 * 
	 * @return available languages
	 */
	Locale[] getAvailableLocales();

	/**
	 * Add a PropertyChangeListener.
	 * 
	 * @param l
	 *            listener to be added
	 */
	void addPropertyChangeListener(PropertyChangeListener l);

	/**
	 * remove a PropertyChangeListener.
	 * 
	 * @param l
	 *            listener to be removed
	 */
	void removePropertyChangeListener(PropertyChangeListener l);
}
