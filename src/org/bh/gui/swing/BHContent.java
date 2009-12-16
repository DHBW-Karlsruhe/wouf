package org.bh.gui.swing;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.*;

/*
 * This class contains all contents 
 * 
 * @author Patrick Tietze
 * @version 0.1, 06/12/2009
 * 
 */


public class BHContent extends JPanel{
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
   
    public JLabel chart, forms;
    public JSplitPane paneV;
    int formPanelHeight = 500;
    
    public BHContent(){
	setLayout(new BorderLayout());
	
	//chart1 = new BHChart1("Comparison", "Which operating system are you using?");
	   
	chart = new JLabel();
	chart.setText("TESTTESTTEST");
	forms = new JLabel();
	forms.setText("FORMSFORMSFORMS");
	
	paneV = new JSplitPane(JSplitPane.VERTICAL_SPLIT, forms, chart);
	paneV.setOneTouchExpandable(true);
	paneV.setDividerLocation(formPanelHeight);
	       
	
	setBackground(Color.white);
	add(paneV, BorderLayout.CENTER);
    }
}
