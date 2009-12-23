package org.bh.gui.swing;

import java.awt.Dimension;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

public class BHTree extends JTree {

    protected DefaultMutableTreeNode rootNode;
    protected DefaultTreeModel treeModel;
    
    static DefaultMutableTreeNode project1;
    static DefaultMutableTreeNode project2;
    static DefaultMutableTreeNode project3;
    
    DefaultTreeModel model;

    public BHTree(DefaultTreeModel treeModel) {
        
    	//set settings
    	this.setEditable(true);
        this.setRootVisible(false);
        this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        this.setShowsRootHandles(true);
        //this.setPreferredSize(new Dimension(200, 400));
    	
    	this.treeModel = treeModel;
        
    	//if TreeModel is null - let's create some fake data :-)
    	if(treeModel == null){
    		rootNode = new DefaultMutableTreeNode("BusinessHorizon");
    		
    		project1 = new DefaultMutableTreeNode("Project 1");
    		project2 = new DefaultMutableTreeNode("Project 2");
    		
    		project1.add(new DefaultMutableTreeNode("Dummy-Szenario1"));
    		project1.add(new DefaultMutableTreeNode("Dummy-Szenario2"));
    		project1.add(new DefaultMutableTreeNode("Dummy-Szenario3"));
    		project1.add(new DefaultMutableTreeNode("Dummy-Szenario4"));
    		
    		project2.add(new DefaultMutableTreeNode("Dummy-Szenario1"));
    		project2.add(new DefaultMutableTreeNode("Dummy-Szenario2"));
    		project2.add(new DefaultMutableTreeNode("Dummy-Szenario3"));
    		project2.add(new DefaultMutableTreeNode("Dummy-Szenario4"));
    		
    		rootNode.add(project1);
    		rootNode.add(project2);
    		this.treeModel = new DefaultTreeModel(rootNode);
    	}
    	
		setModel(this.treeModel);
        
    }

	public void refresh(DefaultTreeModel treeModel){
    	this.setModel(treeModel);
    	//TODO Find out, if reload is necessary...
    	treeModel.reload();
    }
    
}
