package org.bh.gui.swing;

import javax.swing.*;
import javax.swing.tree.*;

/**
 * 
 * BHTreeBar shows the tree with projects, scenarios and periods 
 *
 * <p>
 * This class extends the Swing <code>JTree</code> to create the tree on the left side
 * of the screen to show all projects, scenarios and periods.
 *
 * @author Tietze.Patrick
 * @version 0.1, 2009/12/03
 *
 */

public class BHTreeBar extends JTree{
    
    private static final long serialVersionUID = 1L;
    static DefaultMutableTreeNode root;
    DefaultMutableTreeNode project1;
    DefaultMutableTreeNode project2;
    static DefaultMutableTreeNode project3;
    DefaultTreeModel model;
    

    public BHTreeBar(){
	
		root = new DefaultMutableTreeNode("BusinessHorizon");
		
		project1 = new DefaultMutableTreeNode("Project 1");
		project2 = new DefaultMutableTreeNode("Project 2");
		
		project1.add(new DefaultMutableTreeNode("Szenario1"));
		project1.add(new DefaultMutableTreeNode("Szenario2"));
		project1.add(new DefaultMutableTreeNode("Szenario3"));
		project1.add(new DefaultMutableTreeNode("Szenario4"));
		
		project2.add(new DefaultMutableTreeNode("Szenario1"));
		project2.add(new DefaultMutableTreeNode("Szenario2"));
		project2.add(new DefaultMutableTreeNode("Szenario3"));
		project2.add(new DefaultMutableTreeNode("Szenario4"));
		
		root.add(project1);
		root.add(project2);
		
		model = new DefaultTreeModel(root);
			//model.setRoot(project1);
		
		setModel(model);
		setEditable(true);
		getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		setShowsRootHandles(true);
		
			//putClientProperty("JTree.lineStyle", "Angled");
		
    }
    
    public static void addTreeNode(){
			//	project3 = new DefaultMutableTreeNode("Project 3");
			//	project3.add(new DefaultMutableTreeNode("Szenario1"));
			//	root.add(project3);
			//	model = new DefaultTreeModel(root);
    }
    
    public static void addNode(){
    		//	DefaultMutableTreeNode test = new DefaultMutableTreeNode("Test");
	
    }
    	   
}
