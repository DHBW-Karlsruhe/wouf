package org.bh.gui.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import org.bh.platform.PlatformKey;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * 
 * BHToolBar offers quick access to functions
 *
 * <p>
 * This class offers the user quick access to important and frequently used functions.
 * The icons on the buttons are self explanatory and facilitate the work
 *
 * @author Tietze.Patrick
 * @version 0.1, 2009/12/03
 *
 */


public class BHToolBar extends JToolBar implements MouseListener{

	private boolean shown = false;
	private boolean fixed = true;
	private int width;
	private int height;
	private BHButton Bnew, Bopen, Bsave, Bproject, Bscenario, Bperiod, Bdelete; 
	private JLabel showHide,separator1, separator2;
	
	CellConstraints cons;
	
    
    public BHToolBar(int width, int height) {
    	this.width = width;
    	this.height = height;
    	addMouseListener(this);
    	
		//paint background
		setOpaque(true);
		
		//don't allow to relocate the bar
		setFloatable(false);
		
		//setSize(width, height);
		
		
		String rowDef = "p";
		String colDef = "65px,65px,65px,6px,65px,65px,65px,6px,65px,fill:0px:grow,fill:30px";
		setLayout(new FormLayout(colDef, rowDef));
		cons = new CellConstraints();
		
		createToolBar();
		
    }
    
    public void hideToolBar(){

    	Bnew.setVisible(false);
    	Bopen.setVisible(false);
    	Bsave.setVisible(false);
    	separator1.setVisible(false);
    	Bproject.setVisible(false);
    	Bscenario.setVisible(false);
    	Bperiod.setVisible(false);
    	separator2.setVisible(false);
    	Bdelete.setVisible(false);
    	    	      
    	repaint();
    	
    	shown = false;
    }
    
    public void showToolBar(){
   	
    	Bnew.setVisible(true);
    	Bopen.setVisible(true);
    	Bsave.setVisible(true);
    	separator1.setVisible(true);
    	Bproject.setVisible(true);
    	Bscenario.setVisible(true);
    	Bperiod.setVisible(true);
    	separator2.setVisible(true);
    	Bdelete.setVisible(true);
    	
    	repaint();
				
		shown = true;
    }
    
    public void createToolBar(){
    	
    	Bnew = new BHToolButton(PlatformKey.TOOLBARNEW, 0, "BnewWorkspace");
    	Bnew.addMouseListener(this);
    	Bopen = new BHToolButton(PlatformKey.TOOLBAROPEN, 0, "Bopen");
    	Bopen.addMouseListener(this);
		Bsave = new BHToolButton(PlatformKey.TOOLBARSAVE, 0,"Bsave");
		Bsave.addMouseListener(this);
		Bproject = new BHToolButton(PlatformKey.TOOLBARADDPRO, 113, "BnewProject");
		Bproject.addMouseListener(this);
		Bscenario = new BHToolButton(PlatformKey.TOOLBARADDS, 114, "BnewScenario");
		Bscenario.addMouseListener(this);
		Bperiod = new BHToolButton(PlatformKey.TOOLBARADDPER, 115, "BnewPeriod");
		Bperiod.addMouseListener(this);
		Bdelete = new BHToolButton(PlatformKey.TOOLBARREMOVE, 0, "Bdelete");
		Bdelete.addMouseListener(this);

		showHide = new JLabel("");
		showHide.setIcon(new ImageIcon(BHToolBar.class.getResource("/org/bh/images/buttons/Bshow.png"), ""));
		showHide.addMouseListener(new LabelListener());
		
		separator1 = new JLabel("");
		separator1.setIcon(new ImageIcon(BHToolBar.class.getResource("/org/bh/images/buttons/Separator.png"), ""));
		separator1.addMouseListener(this);
		
		separator2 = new JLabel("");
		separator2.setIcon(new ImageIcon(BHToolBar.class.getResource("/org/bh/images/buttons/Separator.png"), ""));
		separator2.addMouseListener(this);
		
		add(Bnew,  cons.xywh(1, 1, 1, 1));
		add(Bopen,  cons.xywh(2, 1, 1, 1));
		add(Bsave,  cons.xywh(3, 1, 1, 1));
		add(separator1,  cons.xywh(4, 1, 1, 1));
		add(Bproject,  cons.xywh(5, 1, 1, 1));
		add(Bscenario,  cons.xywh(6, 1, 1, 1));
		add(Bperiod,  cons.xywh(7, 1, 1, 1));
		add(separator2,  cons.xywh(8, 1, 1, 1));
		add(Bdelete,  cons.xywh(9, 1, 1, 1));
		add(showHide, cons.xywh(11, 1, 1, 1, "right,top"));
				
		addMouseListener(this);
    			
		shown = true;
    }

	/**
	 *  Listener for Sliding ToolBar
	 */
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// not necessary
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		if(!shown)
			showToolBar();
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		if(!fixed)
			hideToolBar();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// not necessary
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		//not necessary
	}
	
	/**
	 * 
	 * The listener class for the toolbar label
	 *
	 * <p>
	 * This class provides a MouseListener for the label on the ToolBar to fix or slide the ToolBar
	 *
	 * @author Tietze.Patrick
	 * @version 1.0, 13.01.2010
	 *
	 */
	class LabelListener implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent arg0) {
			if(shown){
				showHide.setIcon(new ImageIcon(BHToolBar.class.getResource("/org/bh/images/buttons/Bhide.png"), ""));
				fixed = false;
				shown = false;
				
			}else {
				showHide.setIcon(new ImageIcon(BHToolBar.class.getResource("/org/bh/images/buttons/Bshow.png"), ""));
				fixed = true;
				shown = true;
				showToolBar();
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			if(!shown)
				showToolBar();
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			if(!fixed)
				hideToolBar();
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// not necessary
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// not necessary
		}
		
	}
}

