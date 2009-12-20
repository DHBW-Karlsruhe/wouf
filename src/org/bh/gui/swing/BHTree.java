package org.bh.gui.swing;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;

public class BHTree extends JPanel {
    protected static DefaultMutableTreeNode rootNode;
    protected static DefaultTreeModel treeModel;
    protected static JTree tree;
    private static Toolkit toolkit = Toolkit.getDefaultToolkit();
    
    private static int newNodeSuffix = 1;

    public BHTree() {
        super(new GridLayout(1,0));
        
        rootNode = new DefaultMutableTreeNode("Business Horizon Workspace");
        treeModel = new DefaultTreeModel(rootNode);
        treeModel.addTreeModelListener(new MyTreeModelListener());
        tree = new JTree(treeModel);
        tree.setEditable(true);
        tree.setRootVisible(false);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);
        tree.setPreferredSize(new Dimension(200, 400));
        

        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane);
        
    }
    
    public static int getNodeSuffix(){
    	return newNodeSuffix++;
    }
    
    public static void setNodeSuffix(){
    	newNodeSuffix = 1;
    }

    /** Remove all nodes except the root node. */
    public static void clear() {
        rootNode.removeAllChildren();
        treeModel.reload();
    }

    /** Remove the currently selected node. */
    public static void removeCurrentNode() {
        TreePath currentSelection = tree.getSelectionPath();
        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
                         (currentSelection.getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
            if (parent != null) {
                treeModel.removeNodeFromParent(currentNode);
                return;
            }
        } 

        // Either there was no selection, or the root was selected.
        toolkit.beep();
    }

    /**
     * add new project to root.
     */
    public static DefaultMutableTreeNode addProject(Object child) {
    	
        DefaultMutableTreeNode parentNode = rootNode;
        
        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
       
        //It is key to invoke this on the TreeModel, and NOT DefaultMutableTreeNode
        treeModel.insertNodeInto(childNode, parentNode, parentNode.getChildCount());

        //Make sure the user can see the lovely new node.
        tree.scrollPathToVisible(new TreePath(childNode.getPath()));
        
        return childNode; 
    }
    
    /** 
     * add scenario to the currently selected project. 
     */
    public static DefaultMutableTreeNode addScenario(Object child) {
    	
        TreePath parentPath = tree.getSelectionPath();        
        System.out.println(parentPath);
        
        DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)(parentPath.getPathComponent(1));
        
        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
        
        //It is key to invoke this on the TreeModel, and NOT DefaultMutableTreeNode
        treeModel.insertNodeInto(childNode, parentNode, parentNode.getChildCount());

        //Make sure the user can see the lovely new node.
        tree.scrollPathToVisible(new TreePath(childNode.getPath()));
        
        return childNode; 
    }
    
    

//    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child) {
//        return addObject(parent, child, false);
//    }
//
//    public static DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child, boolean shouldBeVisible) {
//        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
//
//        if (parent == null) {
//            parent = rootNode;
//        }
//	
//        //It is key to invoke this on the TreeModel, and NOT DefaultMutableTreeNode
//        treeModel.insertNodeInto(childNode, parent, parent.getChildCount());
//
//        //Make sure the user can see the lovely new node.
//        if (shouldBeVisible) {
//            tree.scrollPathToVisible(new TreePath(childNode.getPath()));
//        }
//        return childNode;
//    }

    class MyTreeModelListener implements TreeModelListener {
        public void treeNodesChanged(TreeModelEvent e) {
            DefaultMutableTreeNode node;
            node = (DefaultMutableTreeNode)(e.getTreePath().getLastPathComponent());

            /*
             * If the event lists children, then the changed
             * node is the child of the node we've already
             * gotten.  Otherwise, the changed node and the
             * specified node are the same.
             */

                int index = e.getChildIndices()[0];
                node = (DefaultMutableTreeNode)(node.getChildAt(index));

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
}
