package org.bh.gui.swing;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;

public class BHTableCellRenderer implements TableCellRenderer {

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		JLabel label = new JLabel(); 
		
		// seperation between String and Integer to design cells individually
		// strings usually left aligned, integers right aligned
		if(value instanceof String) {

			label = new JLabel((String) value);
			label.setOpaque(true);
			
			// sets distance to cell border
			Border b = BorderFactory.createEmptyBorder(1, 1, 1, 1);
			label.setBorder(b);
			label.setFont(table.getFont());
			label.setForeground(table.getForeground());
			label.setBackground(table.getBackground());
			
			// different colors for selected cell, selected row and not selected rows
			if(hasFocus) {
				label.setBackground(Color.RED);
				label.setForeground(Color.white);
				label.setHorizontalAlignment(JLabel.LEFT);
			}
			else if(isSelected) {
				label.setBackground(Color.LIGHT_GRAY);
				label.setHorizontalAlignment(JLabel.LEFT);
			}
			else { // (!isSelected)
				column = table.convertColumnIndexToModel(column);
				label.setHorizontalAlignment(JLabel.LEFT);
			}
		}
		else if(value instanceof Integer) {
			label = new JLabel(value.toString());
			label.setOpaque(true);
			
			// sets distance to cell border
			Border b = BorderFactory.createEmptyBorder(1, 1, 1, 1);
			label.setBorder(b);
			label.setFont(table.getFont());
			label.setForeground(table.getForeground());
			label.setBackground(table.getBackground());
			// different colors for selected cell, selected row and not selected rows
			if(hasFocus) {
				label.setBackground(Color.DARK_GRAY);
				label.setForeground(Color.white);
				label.setHorizontalAlignment(JLabel.RIGHT);
			}
			else if(isSelected) {
				label.setBackground(Color.LIGHT_GRAY);
				label.setHorizontalAlignment(JLabel.RIGHT);
			}
			else { // (!isSelected)
				column = table.convertColumnIndexToModel(column);
				label.setHorizontalAlignment(JLabel.RIGHT);
			}
		}
		return label;
	}
}
