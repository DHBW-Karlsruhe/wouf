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
