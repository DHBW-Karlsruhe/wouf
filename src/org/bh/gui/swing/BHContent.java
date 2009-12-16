package org.bh.gui.swing;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.*;

/**
 * 
 * BHContent delivers the contents (forms and chart) to show on screen. 
 *
 * <p>
 * This class gets the forms, filled out by the user, and the generated charts
 * and show both on the main screen.
 *
 * @author Tietze.Patrick
 * @version 0.1, 16.12.2009
 *
 */

public class BHContent extends JPanel{

    private static final long serialVersionUID = 1L;
  
    public JLabel chart, forms;
    public JSplitPane paneV;
    int formPanelHeight = 500;
    
    /**
     * currently  only test contents are available in this class
     */
    
    
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
