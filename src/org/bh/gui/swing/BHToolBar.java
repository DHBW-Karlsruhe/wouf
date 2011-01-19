/*******************************************************************************
 * Copyright 2010: Anna Aichinger, Damian Berle, Patrick Dahl, Lisa Engelmann, Patrick Groß, Irene Ihl, Timo Klein, Alena Lang, Miriam Leuthold, Lukas Maciolek, Patrick Maisel, Vito Masiello, Moritz Olf, Ruben Reichle, Alexander Rupp, Daniel Schäfer, Simon Waldraff, Matthias Wurdig, Andreas Wußler
 *
 * Copyright 2009: Manuel Bross, Simon Drees, Marco Hammel, Patrick Heinz, Marcel Hockenberger, Marcus Katzor, Edgar Kauz, Anton Kharitonov, Sarah Kuhn, Michael Löckelt, Heiko Metzger, Jacqueline Missikewitz, Marcel Mrose, Steffen Nees, Alexander Roth, Sebastian Scharfenberger, Carsten Scheunemann, Dave Schikora, Alexander Schmalzhaf, Florian Schultze, Klaus Thiele, Patrick Tietze, Robert Vollmer, Norman Weisenburger, Lars Zuckschwerdt
 *
 * Copyright 2008: Camil Bartetzko, Tobias Bierer, Lukas Bretschneider, Johannes Gilbert, Daniel Huser, Christopher Kurschat, Dominik Pfauntsch, Sandra Rath, Daniel Weber
 *
 * This program is free software: you can redistribute it and/or modify it un-der the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT-NESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package org.bh.gui.swing;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JToolBar;

import org.bh.gui.swing.comp.BHButton;
import org.bh.platform.PlatformController;
import org.bh.platform.PlatformKey;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * 
 * BHToolBar offers quick access to the main functions of Business Horizon
 *
 * <p>
 * This class offers the user quick access to important and frequently used functions.
 * The icons on the buttons are self explanatory and facilitate the work
 *
 * @author Tietze.Patrick
 * @version 0.1, 2009/12/03
 * @version 1.0, 2010/01/27
 *
 */


@SuppressWarnings("serial")
public final class BHToolBar extends JToolBar implements MouseListener{

	boolean shown = true;
	boolean fixed = true;
	
	private BHButton Bopen, Bsave, Bproject, Bscenario, Bperiod, Bdelete; 
	JLabel showHide;
	private JLabel separator1, separator2;
	
	CellConstraints cons;
	
    
    public BHToolBar(int width, int height) {
    	
	addMouseListener(this);
    	
		
	//paint background
	setOpaque(true);
	
	//don't allow to relocate the bar
	setFloatable(false);
		
	String rowDef = "p";
	String colDef = "65px,65px,6px,65px,65px,65px,6px,65px,fill:0px:grow,fill:30px";
	setLayout(new FormLayout(colDef, rowDef));
	cons = new CellConstraints();
	
	if (PlatformController.preferences.get("showToolbar", "true").equals("true")) {
		fixed = true;
		shown = true;
		createToolBar();
		showToolBar();
		
	} else {
		fixed = false;
		shown = false;
		createToolBar();
		hideToolBar();
	}
		
    }
    
    public void hideToolBar(){

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
		
		if (fixed)
			showHide.setIcon(new ImageIcon(BHToolBar.class.getResource("/org/bh/images/buttons/Bshow.png"), ""));
		else
			showHide.setIcon(new ImageIcon(BHToolBar.class.getResource("/org/bh/images/buttons/Bhide.png"), ""));
		
		showHide.addMouseListener(new LabelListener());
		
		separator1 = new JLabel("");
		separator1.setIcon(new ImageIcon(BHToolBar.class.getResource("/org/bh/images/buttons/Separator.png"), ""));
		separator1.addMouseListener(this);
		
		separator2 = new JLabel("");
		separator2.setIcon(new ImageIcon(BHToolBar.class.getResource("/org/bh/images/buttons/Separator.png"), ""));
		separator2.addMouseListener(this);
		
		add(Bopen,  cons.xywh(1, 1, 1, 1));
		add(Bsave,  cons.xywh(2, 1, 1, 1));
		add(separator1,  cons.xywh(3, 1, 1, 1));
		add(Bproject,  cons.xywh(4, 1, 1, 1));
		add(Bscenario,  cons.xywh(5, 1, 1, 1));
		add(Bperiod,  cons.xywh(6, 1, 1, 1));
		add(separator2,  cons.xywh(7, 1, 1, 1));
		add(Bdelete,  cons.xywh(8, 1, 1, 1));
		add(showHide, cons.xywh(10, 1, 1, 1, "right,top"));
				
		addMouseListener(this);
		
    }
    
        /**
         * methods to disable buttons in BHToolBar
         */
        public void disableProjectButton(){
    		Bproject.setEnabled(false);
        }
        public void enableProjectButton(){
    		Bproject.setEnabled(true);
        }
        public void disableScenarioButton(){
    		Bscenario.setEnabled(false);
        }
        public void enableScenarioButton(){
    		Bscenario.setEnabled(true);
        }
        public void disablePeriodButton(){
    		Bperiod.setEnabled(false);
        }
        public void enablePeriodButton(){
    		Bperiod.setEnabled(true);
        }
        
        
	/**
	 *  Listener for Sliding ToolBar
	 */
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
		public void mouseClicked(MouseEvent arg0) {
			if(shown){
				showHide.setIcon(new ImageIcon(BHToolBar.class.getResource("/org/bh/images/buttons/Bhide.png"), ""));
				fixed = false;
				shown = false;
				
				// save toolbar state to preferences
				PlatformController.preferences.put("showToolbar", "false");
			}else {
				showHide.setIcon(new ImageIcon(BHToolBar.class.getResource("/org/bh/images/buttons/Bshow.png"), ""));
				fixed = true;
				shown = true;
				showToolBar();
				
				// save toolbar state to preferences
				PlatformController.preferences.put("showToolbar", "true");
			}
		}

		public void mouseEntered(MouseEvent arg0) {
			if(!shown)
				showToolBar();
		}

		public void mouseExited(MouseEvent arg0) {
			if(!fixed)
				hideToolBar();
		}

		public void mousePressed(MouseEvent arg0) {
			// not necessary
		}

		public void mouseReleased(MouseEvent arg0) {
			// not necessary
		}
		
	}
}

