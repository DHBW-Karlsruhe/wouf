package org.bh.platform;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.bh.gui.swing.BHButton;
import org.bh.gui.swing.BHMainFrame;
import org.bh.platform.i18n.BHTranslator;

public class PlatformController {

	
	public PlatformController(){
		
		//start mainFrame
		BHMainFrame bhmf = new BHMainFrame(BHTranslator.getInstance().translate("title"));
		
		
		
		//handle events...
		
		

		class generalListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Action geht raus");
			}
			
		}
		
		//toolbar
		for(Component c : bhmf.toolBar.getComponents() ){
			((BHButton) c).addActionListener(new generalListener());
		}
	}
	
	
	
	
	
//	@Override
//	public void mouseEntered(MouseEvent e) {
//		this.bhStatusBar.setToolTip(getToolTip());
//		//BHStatusBar.setValidationToolTip(new JLabel("Test"));
//	}
//	@Override
//	public void mouseExited(MouseEvent e) {
//		this.bhStatusBar.setToolTip("");
//	}
//	
//	
//	
//	@Override
//	public void actionPerformed(ActionEvent actionEvent) {
//		String cmd = actionEvent.getActionCommand();
//	        
//	    // Handle each button.
//	    if (cmd.equals("addP")) { //add project button clicked
//	    	System.out.println("add project");
//	    	BHTree.addProject("New Project " + BHTree.getNodeSuffix());
//	    	BHMainFrame.addContentForms(new BHFormsPanel());
//	    	
//	    } else if(cmd.equals("addS")){
//	    	BHTree.addScenario("New Scenario");
//	    	BHMainFrame.addContentFormsAndChart(new BHFormsPanel(), new JPanel());
//        } else if (cmd.equals("remove")) {
//            //Remove button clicked
//            BHTree.removeCurrentNode();
//            
//        } else if (cmd.equals("delete")) {
//            //Clear button clicked.
//            BHTree.clear();
//            BHTree.setNodeSuffix();
//        } else if(cmd.equals("open")){
//        	fc = new JFileChooser();
//        	fc.setPreferredSize(new Dimension(600,400));
//        	fc.showOpenDialog(this);
//        	
//        }
//	       
//	    //Action of Tree
//	    public static DefaultMutableTreeNode addScenario(Object child) {
//	    	
//	        TreePath parentPath = tree.getSelectionPath();        
//	        System.out.println(parentPath);
//	        
//	        DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)(parentPath.getPathComponent(1));
//	        
//	        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
//	        
//	        //It is key to invoke this on the TreeModel, and NOT DefaultMutableTreeNode
//	        treeModel.insertNodeInto(childNode, parentNode, parentNode.getChildCount());
//
//	        //Make sure the user can see the lovely new node.
//	        tree.scrollPathToVisible(new TreePath(childNode.getPath()));
//	        
//	        return childNode; 
//	    }
//	    
//	    public static DefaultMutableTreeNode addProject(Object child) {
//	    	
//	        DefaultMutableTreeNode parentNode = rootNode;
//	        
//	        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
//	       
//	        //It is key to invoke this on the TreeModel, and NOT DefaultMutableTreeNode
//	        treeModel.insertNodeInto(childNode, parentNode, parentNode.getChildCount());
//
//	        //Make sure the user can see the lovely new node.
//	        tree.scrollPathToVisible(new TreePath(childNode.getPath()));
//	        
//	        return childNode; 
//	    }
//	    
//	    /** Remove the currently selected node. */
//	    public static void removeCurrentNode() {
//	        TreePath currentSelection = tree.getSelectionPath();
//	        if (currentSelection != null) {
//	            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
//	                         (currentSelection.getLastPathComponent());
//	            MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
//	            if (parent != null) {
//	                treeModel.removeNodeFromParent(currentNode);
//	                return;
//	            }
//	        } 
//
//	        // Either there was no selection, or the root was selected.
//	        toolkit.beep();
//	    }
//	    
//	    //TreeListener
//	    class MyTreeModelListener implements TreeModelListener {
//	        public void treeNodesChanged(TreeModelEvent e) {
//	            DefaultMutableTreeNode node;
//	            node = (DefaultMutableTreeNode)(e.getTreePath().getLastPathComponent());
//
//	            /*
//	             * If the event lists children, then the changed
//	             * node is the child of the node we've already
//	             * gotten.  Otherwise, the changed node and the
//	             * specified node are the same.
//	             */
//
//	                int index = e.getChildIndices()[0];
//	                node = (DefaultMutableTreeNode)(node.getChildAt(index));
//
//	            System.out.println("The user has finished editing the node.");
//	            System.out.println("New value: " + node.getUserObject());
//	        }
//	        public void treeNodesInserted(TreeModelEvent e) {
//	        }
//	        public void treeNodesRemoved(TreeModelEvent e) {
//	        }
//	        public void treeStructureChanged(TreeModelEvent e) {
//	        }
//	    }
//	    
//	    
//	 }
	
}
