/*******************************************************************************
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