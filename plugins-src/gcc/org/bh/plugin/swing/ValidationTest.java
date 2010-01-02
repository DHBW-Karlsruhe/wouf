package org.bh.plugin.swing;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.bh.gui.ViewException;

public class ValidationTest extends JFrame {
	
	Container c;
	JPanel viewtestpanel;
	ValidationBHBalanceSheetForm valMeth;
	
	public ValidationTest() throws ViewException {
		c = getContentPane();
		
		ViewTest viewtest = new ViewTest();
		viewtestpanel = viewtest.getViewPanel();
		
		c.add(viewtestpanel);
	}
	
	public static void main(String[] args) throws ViewException {
		ValidationTest t = new ValidationTest();
		t.setTitle("Test");
		t.pack();
		t.setVisible(true);
		t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
