package org.bh.gui.swing.forms;

import javax.swing.JTable;

import org.bh.data.types.IValue;
import org.bh.platform.IPlatformListener;
import org.bh.platform.PlatformEvent;
import org.bh.platform.PlatformEvent.Type;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;

/**
 * 
 * <p>
 * This class extends the Swing <code>JTable</code> so it's translatable.
 * 
 * @author Maisel.Patrick
 * @version 0.1, 2010/12/20
 * 
 */

@SuppressWarnings("serial")
public class BHTable extends JTable implements IPlatformListener {

	static final ITranslator translator = Services.getTranslator();

	/**
	 * unique key [] to identify column names
	 */
	String[] key;
	int length;
	Object[][] data;

	public BHTable(Object[][] data, final String[] key) {
		super(data, key);
		this.data = data;
		this.key = key;
		this.length = key.length;
		Services.addPlatformListener(this);
		this.reloadText();
	}

	@Override
	public void platformEvent(PlatformEvent e) {
		if (e.getEventType() == Type.LOCALE_CHANGED) {
			reloadText();
		}
	}

	protected void reloadText() {
		for (int i = 0; i <= this.length - 1; i++) {
			this.getColumnModel().getColumn(i)
					.setHeaderValue(translator.translate(this.key[i]));
		}

	}
}