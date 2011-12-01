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
package org.bh.gui.swing.comp;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.Border;

import org.bh.data.types.IValue;
import org.bh.data.types.ObjectValue;
import org.bh.gui.CompValueChangeManager;
import org.bh.gui.IBHModelComponent;
import org.bh.validation.ValidationRule;

/**
 * List for selecting Scenarios for Import / Export
 * 
 * @author Thiele.Klaus
 * @author Katzor.Marcus
 * @version 0.6, 2010/01/09
 * 
 */
@SuppressWarnings("serial")
public class BHSelectionList extends JList implements MouseListener,
		IBHModelComponent {

	private final String key;
	boolean defaultValue = true;
	private List<Object> selectedItems = new ArrayList<Object>();
	private final CompValueChangeManager valueChangeManager = new CompValueChangeManager();
	protected ValidationRule[] validationRules = new ValidationRule[0];

	/**
	 * Default Constructor
	 * 
	 * @param elements
	 *            elements to be used.
	 */
	public BHSelectionList(Object key, Object[] elements) {
		super();
		this.key = key.toString();

		if (elements != null)
			setModel(elements);

		setCellRenderer(new BHScenarioSelectionRenderer());
		setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		addMouseListener(this);

		Border border = BorderFactory.createCompoundBorder(BorderFactory
				.createLineBorder(UIManager.getColor("controlDkShadow")),
				BorderFactory.createEmptyBorder(5, 5, 10, 5));
		setBorder(border);
	}

	public BHSelectionList(Object key) {
		this(key, null);
	}

	public BHSelectionList(Object[] elements) {
		this("", elements);
	}

	/**
	 * handle mouse click for selection.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {

		// Get index of item clicked
		int index = this.locationToIndex(e.getPoint());

		if (getModel() != null) {

			ListElement item = (ListElement) this.getModel()
					.getElementAt(index);

			// Toggle selected state
			item.isSelected = !item.isSelected;

			if (item.isSelected && !selectedItems.contains(item.value)) {
				selectedItems.add(item.value);
			} else if (!item.isSelected) {
				selectedItems.remove(item.value);
			}
			valueChangeManager.fireCompValueChangeEvent(this);
		}
		// Repaint cell
		this.repaint(this.getCellBounds(index, index));
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	public void setModel(Object[] elements) {
		DefaultListModel model = new DefaultListModel();
		selectedItems = new ArrayList<Object>();
		for (Object element : elements) {
			model.addElement(new ListElement(element));
			if (defaultValue)
				selectedItems.add(element);
		}
		super.setModel(model);
	}

	public List<Object> getSelectedItems() {
		return selectedItems;
	}

	public class ListElement {
		/**
		 * selection element.
		 */
		boolean isSelected;

		/**
		 * wrapped value.
		 */
		Object value;

		/**
		 * Default constructor.
		 * 
		 * @param isSelected
		 * @param value
		 */
		public ListElement(Object value) {
			this.isSelected = defaultValue;
			this.value = value;
		}

		/**
		 * delegate to wrapped value.
		 */
		@Override
		public String toString() {
			return value.toString();
		}
	}

	/**
	 * Renderer for the List
	 * 
	 * @author Thiele.Klaus
	 * @version 0.5, 2010/01/08
	 * 
	 */
	public class BHScenarioSelectionRenderer extends JCheckBox implements
			ListCellRenderer {

		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {

			this.setEnabled(list.isEnabled());
			this.setSelected(((ListElement) value).isSelected);
			
			if (index % 2 != 0) {
				this.setBackground(UIManager.getColor("List.selectionBackground"));
				this.setOpaque(true);
			} else {
				this.setOpaque(false);
			}
			
			this.setText(value.toString());

			return this;
		}
	}

	@Override
	public ValidationRule[] getValidationRules() {
		return validationRules;
	}

	@Override
	public IValue getValue() {
		return new ObjectValue(selectedItems);
	}

	@Override
	public CompValueChangeManager getValueChangeManager() {
		return valueChangeManager;
	}

	@Override
	public void setValidationRules(ValidationRule[] validationRules) {
		this.validationRules = validationRules;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setValue(IValue value) {
		if (value == null) {
			valueChangeManager.fireCompValueChangeEvent(this);
			return;
		}
		
		selectedItems = (List<Object>) ((ObjectValue)value).getObject();
		for (int i = 0; i < getModel().getSize(); i++) {
			ListElement element = (ListElement) getModel().getElementAt(i);
			element.isSelected = selectedItems.contains(element.value);
		}
		repaint();
	}

	@Override
	public String getHint() {
		return "";
	}

	@Override
	public String getKey() {
		return key;
	}

	public boolean getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(boolean defaultValue) {
		this.defaultValue = defaultValue;
	}
}
