package org.bh.gui.swing;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.bh.gui.ValidationMethods;
import org.bh.gui.ViewException;

public class Test extends JFrame {
	
	Container c;
	JPanel viewtestpanel;
	ValidationMethods valMeth;
	
	public Test() throws ViewException {
		c = getContentPane();
		
		ViewTest viewtest = new ViewTest();
		viewtestpanel = viewtest.getViewPanel();
		
		c.add(viewtestpanel);
	}
	
	public static void main(String[] args) throws ViewException {
		Test t = new Test();
		t.setTitle("Test");
		t.pack();
		t.setVisible(true);
		t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
