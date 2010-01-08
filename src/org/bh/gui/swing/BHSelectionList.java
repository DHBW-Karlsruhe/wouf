package org.bh.gui.swing;

import java.awt.Color;
import java.awt.Component;

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
public class BHSelectionList extends JList {
	
	/**
	 * Default Constructor
	 * @param elements elements to be used.
	 */
	public BHSelectionList(Object[] elements) {
		super(elements);
		this.setCellRenderer(new BHScenarioSelectionRenderer());
	}
	
	public void setSelectedAll() {
		this.setSelectionInterval(0, this.getModel().getSize()-1);
	}
	
	public void setSelectedNothing() {
		this.setSelectedIndex(-1);
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
			this.setSelected(isSelected);
			
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
