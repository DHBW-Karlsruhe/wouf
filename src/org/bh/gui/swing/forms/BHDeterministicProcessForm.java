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
package org.bh.gui.swing.forms;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.bh.data.DTOScenario;
import org.bh.data.types.IValue;
import org.bh.gui.swing.comp.BHCheckBox;
import org.bh.gui.swing.comp.BHComboBox;
import org.bh.gui.swing.comp.BHDescriptionLabel;
import org.bh.gui.swing.comp.BHTable;
import org.bh.platform.IPlatformListener;
import org.bh.platform.PlatformEvent;
import org.bh.platform.Services;
import org.bh.platform.PlatformEvent.Type;
import org.bh.platform.i18n.ITranslator;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * This class contains the form for stochastic processes
 * 
 * @author Anton Kharitonov
 * @author Patrick Maisel
 * @version 0.1, 05.01.2010
 * @version 0.2, 21.12.2010 - Änderung des Tables tperioddata zum BHTable zwecks
 *          Übersetzbarkeit
 * 
 */
@SuppressWarnings("serial")
public final class BHDeterministicProcessForm extends JPanel {

	// Labels
	private BHDescriptionLabel ldcfMethod;

	// Components
	private BHCheckBox chbinterval;
	private BHTable tperioddata;
	private BHComboBox cbdcfMethod;
	private JScrollPane tablePane;
	CellConstraints cons;

	ITranslator translator = Services.getTranslator();

	/**
	 * Constructor.
	 */
	public BHDeterministicProcessForm() {
		this.initialize();
	}

	/**
	 * Initialize method.
	 */
	private void initialize() {
		FormLayout layout;
		String colDef = "4px,right:pref,4px,max(80px;pref),4px:grow(0.2),pref:grow,4px";
		String rowDef = "4px,p,4px,p,4px,p,4px";

		layout = new FormLayout(colDef, rowDef);
		this.setLayout(layout);

		cons = new CellConstraints();

		this.add(this.getlDCFmethod(), cons.xywh(2, 2, 1, 1));
		this.add(this.getcbDCFmethod(), cons.xywh(4, 2, 1, 1));
		this.add(this.getChbinterval(), cons.xywh(6, 2, 1, 1));

	}

	// public BHDescriptionLabel getLinterval() {
	// if (linterval == null) {
	// linterval = new BHDescriptionLabel("IntervalArithmetic");
	// }
	// return linterval;
	// }

	public BHCheckBox getChbinterval() {
		if (chbinterval == null) {
			chbinterval = new BHCheckBox(DTOScenario.Key.INTERVAL_ARITHMETIC);
			chbinterval.setText(translator
					.translate(DTOScenario.Key.INTERVAL_ARITHMETIC));
		}
		return chbinterval;
	}

	public JTable getTperioddata(IValue[][] data) {
		String[] columnnames = { "Period",
				"org.bh.data.DTOPeriod$Key.LIABILITIES",
				"org.bh.data.DTOPeriod$Key.FCF" };
		// this.tperioddata = new JTable(data, columnnames) {
		// wird jetzt als BHTable erstellt, damit ist es nun übersetzbar
		DefaultTableModel tableModel = new DefaultTableModel(data, columnnames);
		
		this.tperioddata = new BHTable(tableModel, DTOScenario.Key.DETERMINISTIC_PERIODS){
			// always make the table as large as necessary
			@Override
			public Dimension getPreferredScrollableViewportSize() {
				return getPreferredSize();
			}
		};
		this.tperioddata.setEnabled(false);
		this.tperioddata.getTableHeader().setReorderingAllowed(false);
		this.tperioddata.setGridColor(Color.RED);
		return this.tperioddata;
	}

	public void setPeriodTable(IValue[][] data) {
		if (data.length > 0)
			if (data[0].length != 3)
				throw new Error("PeriodTableData: Data array has wrong length!");

		if (tablePane != null)
			this.remove(tablePane);

		tablePane = new JScrollPane(this.getTperioddata(data));

		this.add(tablePane, cons.xywh(2, 6, 6, 1));
	}

	public BHDescriptionLabel getlDCFmethod() {
		if (this.ldcfMethod == null)
			this.ldcfMethod = new BHDescriptionLabel(DTOScenario.Key.DCF_METHOD);
		return this.ldcfMethod;
	}

	public BHComboBox getcbDCFmethod() {
		if (this.cbdcfMethod == null) {
			this.cbdcfMethod = new BHComboBox(DTOScenario.Key.DCF_METHOD);
		}
		return this.cbdcfMethod;
	}

}
