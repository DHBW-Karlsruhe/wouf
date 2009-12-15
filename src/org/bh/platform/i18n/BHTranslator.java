package org.bh.platform.i18n;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.bh.platform.PluginManager;

/**
 * Implements the <code>ITranslator</code> Interface for Translation of all i18n
 * relevant keys in Business Horizon.
 * 
 * @author Thiele.Klaus
 * @version 0.1, 2009/12/12
 * 
 */
public class BHTranslator implements ITranslator {

    /**
     * private Logging instance for log.
     */
    private static final Logger log = Logger.getLogger(PluginManager.class);
    
    /**
     * Constant for used <code>ResourceBundle</code>.
     */
    private static final String BUNDLE = "BHGUIKeys";

    /**
     * Available <code>Locale</code>s. Only provided if corresponding properties
     * file is provided.
     */
    private static final Locale[] locales = { Locale.US, Locale.GERMANY };

    /**
     * Currently used <code>Locale</code>.
     */
    private Locale locale;

    /**
     * <code>ResourceBundle</code> for access to the properties file.
     */
    private ResourceBundle bundle;

    /**
     * 
     */
    private ArrayList<PropertyChangeListener> listener;

    /**
     * Default Constructor which instantiates the <code>BHTranslator</code> with
     * the default locale.
     */
    public BHTranslator() {
	this(Locale.getDefault());
    }

    /**
     * Alternative Constructor.
     * 
     * @param l
     *            locale to be used to instantiate the <code>BHTranslator</code>
     */
    public BHTranslator(Locale l) {
	this.locale = l;
	this.bundle = ResourceBundle
		.getBundle(BHTranslator.BUNDLE, this.locale);
	this.listener = new ArrayList<PropertyChangeListener>();
	log.debug("Translator initialized with Locale " + this.locale);
    }

    /**
     * Core method to translate keys into domain-specific String to be used in
     * user facing screen texts.
     * 
     * @param key
     *            key to be translated
     * @return translated <code>String</code>
     */
    public String translate(String key) {
	return this.bundle.getString(key);
    }

    /**
     * Sets a new <code>Locale</code> for further translation.
     * 
     * @param locale
     *            locale to be set
     */
    public void setLocale(Locale locale) {
	Locale l = this.locale;
	this.locale = locale;
	this.bundle = ResourceBundle.getBundle(BHTranslator.BUNDLE, locale);
	this.firePropertyChange("Locale", l, this.locale);
    }

    /**
     * Returns the currently used <code>Locale</code> for translation.
     * 
     * @return the currently used <code>Locale</code>
     */
    public Locale getLocale() {
	return this.locale;
    }

    /**
     * Checks if key is available for translation.
     * 
     * @param key
     *            key to be checked
     * @return true if contains else false.
     */
    public boolean containsKey(String key) {
	return this.bundle.containsKey(key);
    }

    /**
     * Provides all available languages.
     */
    public Locale[] getAvaiableLocales() {
	return BHTranslator.locales;
    }

    /**
     * add a PropertyChangeListener.
     */
    public void addPropertyChangeListener(PropertyChangeListener l) {
	this.listener.add(l);
    }

    /**
     * Remove a PropertyChangeListener.
     */
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
	log.debug("BHTranslator: Property changed: " + key + ": " + newValue);

	// for each listener call propertyChange with proper attributes
	for (PropertyChangeListener l : this.listener) {
	    l.propertyChange(new PropertyChangeEvent(this, key, oldValue,
		    newValue));
	}
    }
}