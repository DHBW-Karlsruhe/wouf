package org.bh.gui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * BHContent delivers the BH background with the BH background logo 
 *
 *
 * @author Tietze.Patrick
 * @version 0.1, 16.12.2009
 *
 */


public class BHContent extends JPanel{

    public JLabel logo;
    
    /**
     * currently  only test contents are available in this class
     */
      
    public BHContent(){
		setLayout(new BorderLayout());
		setMinimumSize(new Dimension(100, 100));
		setBackground(Color.white);
		
		logo = new JLabel(new ImageIcon(BHContent.class.getResource("/org/bh/images/background.jpg")));
		
		add(logo, BorderLayout.CENTER);	
		
	}
}
