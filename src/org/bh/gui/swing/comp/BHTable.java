package org.bh.gui.swing.comp;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.bh.gui.IBHComponent;
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
public class BHTable extends JTable implements IPlatformListener, IBHComponent {

	static final ITranslator translator = Services.getTranslator();

	/**
	 * unique key [] to identify column names
	 */
	String key;
	DefaultTableModel tableModel;
	Object[][] data;

	public BHTable(DefaultTableModel tableModel, Object key) {
		super(tableModel);
		this.tableModel = tableModel;
		this.data = data;
		this.key = key.toString();
		Services.addPlatformListener(this);
		this.reloadText();
	}
	public BHTable(Object key) {
		super();
		this.key = key.toString();
	}

	@Override
	public void platformEvent(PlatformEvent e) {
		if (e.getEventType() == Type.LOCALE_CHANGED) {
			reloadText();
		}
	}

	protected void reloadText() {
		for (int i = 0; i < this.tableModel.getColumnCount(); i++) {
			this.getColumnModel().getColumn(i)
					.setHeaderValue(translator.translate(this.tableModel.getColumnName(i)));
		}

	}
	public void setTableModel(DefaultTableModel tableModel){
		super.setModel(tableModel);
	}
	
	public String getKey() {
		return key;
	}
	@Override
	public String getHint() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}
}