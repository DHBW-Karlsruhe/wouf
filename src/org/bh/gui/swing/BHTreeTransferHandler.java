package org.bh.gui.swing;
import java.awt.Point;
import java.awt.dnd.DnDConstants;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
 
public class BHTreeTransferHandler extends AbstractTreeTransferHandler {
 
	public BHTreeTransferHandler(BHTree tree, int action) {
		super(tree, action, true);
	}
 
	@Override
	public boolean canPerformAction(BHTree target, DefaultMutableTreeNode draggedNode, int action, Point location) {
		TreePath pathTarget = target.getPathForLocation(location.x, location.y);
		if (pathTarget == null) {
			target.setSelectionPath(null);
			return(false);
		}
		target.setSelectionPath(pathTarget);
		if(action == DnDConstants.ACTION_COPY) {
			return(true);
		}
		else
		if(action == DnDConstants.ACTION_MOVE) {	
			DefaultMutableTreeNode parentNode =(DefaultMutableTreeNode)pathTarget.getLastPathComponent();				
			if (draggedNode.isRoot() || parentNode == draggedNode.getParent() || draggedNode.isNodeDescendant(parentNode)) {					
				return(false);	
			}
			return(true);				 
		}
		else {		
			return(false);	
		}
	}
 
	@Override
	public boolean executeDrop(BHTree target, DefaultMutableTreeNode draggedNode, DefaultMutableTreeNode newParentNode, int action) { 
		if (action == DnDConstants.ACTION_COPY) {
			DefaultMutableTreeNode newNode = BHTree.makeDeepCopy(draggedNode);
			((DefaultTreeModel)target.getModel()).insertNodeInto(newNode,newParentNode,newParentNode.getChildCount());
			TreePath treePath = new TreePath(newNode.getPath());
			target.scrollPathToVisible(treePath);
			target.setSelectionPath(treePath);	
			return(true);
		}
		if (action == DnDConstants.ACTION_MOVE) {
			draggedNode.removeFromParent();
			((DefaultTreeModel)target.getModel()).insertNodeInto(draggedNode,newParentNode,newParentNode.getChildCount());
			TreePath treePath = new TreePath(draggedNode.getPath());
			target.scrollPathToVisible(treePath);
			target.setSelectionPath(treePath);
			return(true);
		}
		return(false);
	}
}
