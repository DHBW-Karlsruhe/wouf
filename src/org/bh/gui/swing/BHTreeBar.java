package org.bh.gui.swing;

import java.awt.Dimension;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
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

public class BHTreeBar extends JTree implements TreeModelListener{
    
    DefaultMutableTreeNode root;
    static DefaultMutableTreeNode project1;
    static DefaultMutableTreeNode project2;
    static DefaultMutableTreeNode project3;
    DefaultTreeModel model;
    
    private static int newNodeSuffix = 1;

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
		model.addTreeModelListener(this);

			//model.setRoot(project1);
		
		setModel(model);
		
		setEditable(true);
		getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		setShowsRootHandles(true);
		setPreferredSize(new Dimension(200, 400));
		
			//putClientProperty("JTree.lineStyle", "Angled");
		
    }
    
//    public BHTreeBar addTreeNode(String treeNode){
//    	add(new DefaultMutableTreeNode());
//		new DefaultMutableTreeNode(tree);
//		this.add(new DefaultMutableTreeNode(treeNode));
//		add(project3);
//		model = new DefaultTreeModel(root);
//    }
    
    public static int getNodeSuffix(){
    	return newNodeSuffix++;
    }
    

    
    //public static void addScenario(String parent, String scenario){
//    	 int startRow = 0;
//    	 TreePath path = getNextMatch(parent, startRow, Position.Bias.Forward);
//    	 MutableTreeNode node = (MutableTreeNode)path.getLastPathComponent();
//    	    
//    	    // Create new node
//    	 model.insertNodeInto(new DefaultMutableTreeNode(scenario), node, 0);
    //}
    public DefaultMutableTreeNode addObject(Object child) {
        DefaultMutableTreeNode parentNode = null;
        TreePath parentPath = getSelectionPath();

        if (parentPath == null) {
            //There is no selection. Default to the root node.
            parentNode = root;
        } else {
            parentNode = (DefaultMutableTreeNode)
                         (parentPath.getLastPathComponent());
        }

        return addObject(parentNode, child, true);
    }
   
    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
                                            Object child,
                                            boolean shouldBeVisible) {
        DefaultMutableTreeNode childNode =
                new DefaultMutableTreeNode(child);
        
        model.insertNodeInto(childNode, parent,
                                 parent.getChildCount());

        //Make sure the user can see the lovely new node.
        if (shouldBeVisible) {
            scrollPathToVisible(new TreePath(childNode.getPath()));
        }
        return childNode;
    }

    
    /**
     * TreeModelListener
     * @param e
     */

	public void treeNodesChanged(TreeModelEvent e) {
        DefaultMutableTreeNode node;
        node = (DefaultMutableTreeNode)(e.getTreePath().getLastPathComponent());

        /*
         * If the event lists children, then the changed
         * node is the child of the node we have already
         * gotten.  Otherwise, the changed node and the
         * specified node are the same.
         */
        try {
            int index = e.getChildIndices()[0];
            node = (DefaultMutableTreeNode)
                   (node.getChildAt(index));
        } catch (NullPointerException exc) {}

        System.out.println("The user has finished editing the node.");
        System.out.println("New value: " + node.getUserObject());
    }
    public void treeNodesInserted(TreeModelEvent e) {
    }
    public void treeNodesRemoved(TreeModelEvent e) {
    }
    public void treeStructureChanged(TreeModelEvent e) {
    }

}
