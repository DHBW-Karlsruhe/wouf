package org.bh.gui.swing;

import java.awt.Color;
import javax.swing.*;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/*
 * This class contains all contents of the footer
 * 
 * @author Patrick Tietze
 * @version 0.1, 05/12/2009
 * 
 */

public class BHStatusBar extends JPanel{
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    JLabel bh;
    static JLabel lToolTip;
    CellConstraints cons;
    
    public BHStatusBar(String toolTip){
	setBackground(Color.white);
	
	//setLayout to the status bar
	String rowDef="p";
	String colDef="0:grow(0.4),0:grow(0.2),0:grow(0.4)";
	setLayout(new FormLayout(colDef,rowDef));
	cons = new CellConstraints();
	
	//create tool tip label
	lToolTip = new JLabel();
	lToolTip.setToolTipText(toolTip);
		
	//create BH logo label
	bh = new JLabel(new ImageIcon("images/bh-logo.jpg"));
	
	//add components to panel
	add(lToolTip, cons.xywh(1,1,1,1));
	add(bh, cons.xywh(2,1,1,1));

    }
    public static void setToolTip(String toolTip){
	lToolTip.setText(toolTip);
	//test.repaint();
	lToolTip.revalidate();

    }
   
}
