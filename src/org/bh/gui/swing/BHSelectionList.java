package org.bh.gui.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

/**
 * List for selecting Scenarios for Import / Export
 *
 * @author Thiele.Klaus
 * @version 0.5, 2010/01/08
 *
 */
public class BHSelectionList extends JList implements MouseListener {
	
	/**
	 * Default Constructor
	 * @param elements elements to be used.
	 */
	public BHSelectionList(Object[] elements) {
		super();
		for (Object element : elements) {
			((DefaultListModel) this.getModel()).addElement(new ListElement(element));
		}
		this.setCellRenderer(new BHScenarioSelectionRenderer());
	}
	
	/**
	 * handle mouse click for selection.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {

		// Get index of item clicked
		int index = this.locationToIndex(e.getPoint());
		ListElement item = (ListElement) this.getModel().getElementAt(index);
		 
		// Toggle selected state
		item.isSelected =!item.isSelected;
		if (item.isSelected) {
			this.addSelectionInterval(index, index);
		}
		else {
			this.removeSelectionInterval(index, index);
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

	public class ListElement {
		
		/**
		 * Default for selection of ListElement.
		 */
		public static final boolean DEFAULT = true;

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
		 * @param isSelected
		 * @param value
		 */
		public ListElement(Object value) {
			this.isSelected = ListElement.DEFAULT;
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
	public class BHScenarioSelectionRenderer extends JCheckBox implements ListCellRenderer {

		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			
			this.setEnabled(list.isEnabled());
			
			this.setSelected(((ListElement) value).isSelected);
			
			if (index % 2 == 0) {
				this.setBackground(UIManager.getColor("control"));
			} else {
				this.setBackground(Color.WHITE);
			}
			
			this.setText(value.toString());
			return this;
		}
	}
}
