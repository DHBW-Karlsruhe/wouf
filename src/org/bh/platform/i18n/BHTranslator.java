package org.bh.platform.i18n;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.JComponent;

import org.apache.log4j.Logger;

/**
 * Implements the <code>ITranslator</code> Interface for Translation of all i18n
 * relevant keys in Business Horizon.
 * 
 * @author Thiele.Klaus
 * @version 0.1, 2009/12/12
 * @version 0.2, 2010/01/02
 * 
 */
public class BHTranslator implements ITranslator {
	
	/**
	 * Parameter for short text. 
	 */
	public static final int SHORT = 0;
	
	/**
	 * Parameter for regular text. 
	 */
	public static final int REGULAR = 1;
	
	/**
	 * Parameter for long text. 
	 */
	public static final int LONG = 2;
	
	/**
	 * private Logging instance for log.
	 */
	private static final Logger LOG = Logger.getLogger(BHTranslator.class);

	/**
	 * Constant for used <code>ResourceBundle</code>.
	 */
	private static final String BUNDLE = "BHGUIKeys";

	/**
	 * Available <code>Locale</code>s. Only provided if corresponding properties
	 * file is provided.
	 */
	private static final Locale[] availableLocales = {}; //{ Locale.US, Locale.GERMANY };

	/**
	 * Singleton instance.
	 */
	private static BHTranslator instance;

	/**
	 * <code>ResourceBundle</code> for access to the properties file.
	 */
	private ResourceBundle bundle;

	/**
	 * Registered <code>PropertyChangeListener</code>.
	 */
	private ArrayList<PropertyChangeListener> listener;

	/**
	 * Default Constructor which instantiates the <code>BHTranslator</code> with
	 * the default <code>Locale</code>.
	 */
	private BHTranslator() {
		this(Locale.getDefault());
 	}

	/**
	 * Alternative Constructor.
	 * 
	 * @param locale
	 *            locale to be used to instantiate the <code>BHTranslator</code>
	 */
	private BHTranslator(Locale locale) {
		Locale.setDefault(locale);
		JComponent.setDefaultLocale(locale);
		this.bundle = ResourceBundle.getBundle(BHTranslator.BUNDLE);
		this.listener = new ArrayList<PropertyChangeListener>();
		LOG.debug("Translator initialized with Locale " + Locale.getDefault());
	}

	/**
	 * Returns the Singelton instance of <code>BHTranslator</code>.
	 * 
	 * @return the instance.
	 */
	public static BHTranslator getInstance() {
		if (BHTranslator.instance == null) {
			BHTranslator.instance = new BHTranslator();
		}
		return instance;
	}

	/**
	 * Core method to translate keys into domain-specific String to be used in
	 * user facing screen texts.
	 * 
	 * @param key
	 *            key to be translated
	 * @return translated <code>String</code>
	 */
	@Override
	public String translate(Object key) {
		try {
			return this.bundle.getString(key.toString());
		} catch (MissingResourceException e) {
			LOG.error("Could not translate key \"" + key + "\"", e);
			return key.toString();
		}
	}

	/**
	 * Special method for dedicated version of keys.
	 * 
	 * @param key
	 *            key to be translated
	 * @param texttype
	 * @return translated <code>String</code>
	 */
	public String translate(Object key, int type) {
		switch (type) {
		
		case SHORT:
			try {
				return this.translate(key + "_short");
			} catch (MissingResourceException e) {
				return this.translate(key);
			}

		case REGULAR:
			return this.translate(key);

		case LONG:
			try {
				return this.translate(key + "_long");
			} catch (MissingResourceException e) {
				return this.translate(key);
			}

		default:
			return this.translate(key);
		}

	}
	

	/**
	 * Sets a new <code>Locale</code> for further translation.
	 * 
	 * @param locale
	 *            locale to be set
	 */
	@Override
	public void setLocale(Locale locale) {
		Locale l = Locale.getDefault();
		Locale.setDefault(locale);
		JComponent.setDefaultLocale(locale);
		this.bundle = ResourceBundle.getBundle(BHTranslator.BUNDLE);
		this.firePropertyChange("Locale", l, Locale.getDefault());
	}

	/**
	 * Returns the currently used <code>Locale</code> for translation.
	 * 
	 * @return the currently used <code>Locale</code>
	 */
	@Override
	public Locale getLocale() {
		return Locale.getDefault();
	}

	/**
	 * Checks if key is available for translation.
	 * 
	 * @param key
	 *            key to be checked
	 * @return true if contains else false.
	 */
	@Override
	public boolean containsKey(Object key) {
		return this.bundle.containsKey(key.toString());
	}

	/**
	 * Provides all available languages.
	 */
	@Override
	public Locale[] getAvaiableLocales() {
		return BHTranslator.availableLocales;
	}

	/**
	 * Adds a PropertyChangeListener.
	 */
	@Override
	public void addPropertyChangeListener(PropertyChangeListener l) {
		this.listener.add(l);
	}

	/**
	 * Remove a PropertyChangeListener.
	 * 
	 * @param l Listener to be added.
	 */
	@Override
	public void removePropertyChangeListener(PropertyChangeListener l) {
		this.listener.remove(l);
	}

	/**
	 * Fire new PropertyChangeEvent to all registered PropertyChangeListeners.
	 * 
	 * @param key
	 *            property that has changed
	 * @param oldValue
	 *            old value
	 * @param newValue
	 *            new value
	 */
	private void firePropertyChange(String key, Object oldValue, Object newValue) {
		LOG.debug("BHTranslator: Property changed: " + key + ": " + newValue);

		// for each listener call propertyChange with proper attributes
		for (PropertyChangeListener l : this.listener) {
			l.propertyChange(new PropertyChangeEvent(this, key, oldValue,newValue));
		}
		//Services.firePlatformEvent(new PlatformEvent(BHTranslator.class, PlatformEvent.Type.))
	}
}