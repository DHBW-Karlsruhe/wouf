package org.bh.platform;

/**
 * This class wraps a displayable plugin and provides a <code>toString</code>
 * method which returns the translated description of the plugin.
 * 
 * @author Robert Vollmer
 * @version 1.0, 21.12.2009
 * 
 */
public class DisplayablePluginWrapper<T extends IDisplayablePlugin> implements
		Comparable<DisplayablePluginWrapper<?>> {
	protected final T plugin;
	protected final String translatedKey;

	public DisplayablePluginWrapper(T plugin) {
		this.plugin = plugin;
		translatedKey = Services.getTranslator().translate(plugin.getGuiKey());
	}

	@Override
	public String toString() {
		return translatedKey;
	}

	public T getPlugin() {
		return plugin;
	}

	@Override
	public int compareTo(DisplayablePluginWrapper<?> o) {
		return translatedKey.compareTo(o.translatedKey);
	}

	@Override
	public int hashCode() {
		return plugin.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return plugin.equals(obj);
	}
}
