package org.bh.gui.swing;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

public class BHTree extends JTree {

    protected DefaultMutableTreeNode rootNode;
    protected DefaultTreeModel treeModel;
    
    
    DefaultTreeModel model;

    public BHTree() {
        
    	//set settings
    	this.setEditable(true);
        this.setRootVisible(false);
        this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        this.setShowsRootHandles(true);
    }

    
    
	public void setTreeModel(DefaultTreeModel treeModel){
    	this.setModel(treeModel);
    	//TODO Find out, if reload is necessary...
    }
    
}
