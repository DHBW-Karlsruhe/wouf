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

import org.bh.data.DTOProject;

import org.bh.gui.swing.tree.BHTreePopup;

/**
 * <short_description>
 *
 * <p>
 * <detailed_description>
 *
 * @author simon
 * @version 1.0, 18.01.2012
 *
 */
public class BHBDTree extends JTree {

    private JPopupMenu projectPopup = new BHBDTreePopup(BHBDTreePopup.Type.PROJECT);

    private JPopupMenu defaultPopup = new BHBDTreePopup(BHBDTreePopup.Type.PERIOD);
	/**
	 * 
	 */
    
    
    void showPopup(MouseEvent e, BHBDTreePopup.Type nodeType) {

    	switch (nodeType) {
    	case PROJECT:
    	    projectPopup.show(e.getComponent(), e.getX(), e.getY());
    	    break;

    	case SCENARIO:

    	case PERIOD:
    	    defaultPopup.show(e.getComponent(), e.getX(), e.getY());
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
	 */
	public BHBDTree(Hashtable<?, ?> value) {
		super(value);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param root
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
	 * @param asksAllowsChildren
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
    		if (((BHBusinessDataTreeNode) selPath.getLastPathComponent()).getUserObject() instanceof DTOProject) {
    		    showPopup(e, BHBDTreePopup.Type.PROJECT);
    		} else {
    		    showPopup(e, BHBDTreePopup.Type.PERIOD);
    		}
    	    }
    	}

    	@Override
    	public void mousePressed(MouseEvent e) {
    	    TreePath selPath = BHBDTree.this.getPathForLocation(e.getX(), e.getY());
    	    if (selPath != null && e.isPopupTrigger()) {
    		BHBDTree.this.setSelectionPath(selPath);
    		if (((BHBusinessDataTreeNode) selPath.getLastPathComponent()).getUserObject() instanceof DTOProject) {
    		    showPopup(e, BHBDTreePopup.Type.PROJECT);
    		} else {
    		    showPopup(e, BHBDTreePopup.Type.PERIOD);
    		}
    	    }
    	}
        };

}
