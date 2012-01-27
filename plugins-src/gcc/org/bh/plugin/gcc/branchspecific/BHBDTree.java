/*******************************************************************************
 * Copyright 2011: Matthias Beste, Hannes Bischoff, Lisa Doerner, Victor Guettler, Markus Hattenbach, Tim Herzenstiel, Günter Hesse, Jochen Hülß, Daniel Krauth, Lukas Lochner, Mark Maltring, Sven Mayer, Benedikt Nees, Alexandre Pereira, Patrick Pfaff, Yannick Rödl, Denis Roster, Sebastian Schumacher, Norman Vogel, Simon Weber 
 *
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
package org.bh.plugin.gcc.branchspecific;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.bh.data.DTOBranch;
import org.bh.data.DTOCompany;
import org.bh.data.DTOProject;

import org.bh.gui.swing.tree.BHTreePopup;

/**
 * <short_description>
 *
 *Business Data Tree to maintain branch specific representatives
 * <detailed_description>
 *
 * @author simon
 * @version 1.0, 18.01.2012
 *
 */
public class BHBDTree extends JTree {

    

    
	/**
     * Controls Popup in the tree
     * 
     * @param nodeType
     * @return Type of the Node Object
     */
    void showPopup(MouseEvent e, BHBDTreePopup.Type nodeType) {

    	switch (nodeType) {

    	case BRANCH:
    		JPopupMenu branchPopup = new BHBDTreePopup(BHBDTreePopup.Type.BRANCH, this);
    	    branchPopup.show(e.getComponent(), e.getX(), e.getY());
    	    break;

    	case COMPANY:
    		JPopupMenu companyPopup = new BHBDTreePopup(BHBDTreePopup.Type.COMPANY, this);
    		companyPopup.show(e.getComponent(), e.getX(), e.getY());
    	    break;
    	    
    	case PERIOD:
    		JPopupMenu periodPopup = new BHBDTreePopup(BHBDTreePopup.Type.PERIOD, this);
    	    periodPopup.show(e.getComponent(), e.getX(), e.getY());
    	    break;
    	}
        }
    
    
    
	public BHBDTree() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param value
	 */
	public BHBDTree(Object[] value) {
		super(value);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param value
	 */
	public BHBDTree(Vector<?> value) {
		super(value);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param value
	 * constructor
	 */
	public BHBDTree(Hashtable<?, ?> value) {
		super(value);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param root
	 * the root node
	 */
	public BHBDTree(TreeNode root) {
		super(root);
		this.addMouseListener(new PopupListener());
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param newModel
	 */
	public BHBDTree(TreeModel newModel) {
		super(newModel);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param root
	 * the root node
	 * @param asksAllowsChildren
	 * default true
	 */
	public BHBDTree(TreeNode root, boolean asksAllowsChildren) {
		super(root, asksAllowsChildren);
		// TODO Auto-generated constructor stub
	}
	
    class PopupListener extends MouseAdapter {

    	@Override
    	public void mouseReleased(MouseEvent e) {
    	    TreePath selPath = BHBDTree.this.getPathForLocation(e.getX(), e.getY());
    	    if (selPath != null && e.isPopupTrigger()) {
    		if (((BHBusinessDataTreeNode) selPath.getLastPathComponent()).getUserObject() instanceof DTOBranch) {
    		    showPopup(e, BHBDTreePopup.Type.BRANCH);
    		}  else if (((BHBusinessDataTreeNode) selPath.getLastPathComponent()).getUserObject() instanceof DTOCompany) {
    		    showPopup(e, BHBDTreePopup.Type.COMPANY);
    		} else{
    		    showPopup(e, BHBDTreePopup.Type.PERIOD);
    		}
    	    }
    	}

    	@Override
    	public void mousePressed(MouseEvent e) {
    	    TreePath selPath = BHBDTree.this.getPathForLocation(e.getX(), e.getY());
    	    if (selPath != null && e.isPopupTrigger()) {
    		BHBDTree.this.setSelectionPath(selPath);
    		if (((BHBusinessDataTreeNode) selPath.getLastPathComponent()).getUserObject() instanceof DTOBranch) {
    		    showPopup(e, BHBDTreePopup.Type.BRANCH);
    		    	
    		} else if (((BHBusinessDataTreeNode) selPath.getLastPathComponent()).getUserObject() instanceof DTOCompany) {
    		    showPopup(e, BHBDTreePopup.Type.COMPANY);
    		} else{
    		    showPopup(e, BHBDTreePopup.Type.PERIOD);
    		}
    	    }
    	}
        };

}
