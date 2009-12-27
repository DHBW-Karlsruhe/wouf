package org.bh.platform;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.bh.data.DTOPeriod;
import org.bh.data.DTOProject;
import org.bh.data.DTOScenario;
import org.bh.data.types.StringValue;
import org.bh.gui.swing.*;
import org.bh.platform.i18n.BHTranslator;

/**
 * The Platform Controller handles a) start up of the application b) main
 * application flow c) all events which are fired by platform controls (e.g.
 * toolbar-button klicks or menu klicks)
 * 
 * 
 * 
 * @author Alexander Schmalzhaf
 * @author Patrick Tietze
 * 
 * @version 0.1 2009/12/22 Alexander Schmalzhaf
 */

public class PlatformController {

	private BHMainFrame bhmf;
	private ProjectRepositoryManager projectRepoManager = new ProjectRepositoryManager();

	public PlatformController() {
		
		/*------------------------------------
		 * Fill Repo (sample DTOs)
		 * -----------------------------------
		 */
		
		
		DTOProject project1 = new DTOProject();
		DTOScenario scenario1 = new DTOScenario(true);
		DTOPeriod period1 = new DTOPeriod();
		
		
		StringValue projectName = new StringValue("project1");
		project1.put(DTOProject.Key.NAME, projectName);
		
		scenario1.addChild(period1);
		StringValue periodIdent = new StringValue("Periode 1");
		period1.put(DTOPeriod.Key.IDENTIFIER, periodIdent);
		
		project1.addChild(scenario1);
		StringValue scenarioName = new StringValue("Scenario1");
		scenario1.put(DTOScenario.Key.NAME, scenarioName);
		
		
		
		
		
		projectRepoManager.addProject(project1);
		
		// start mainFrame
		bhmf = new BHMainFrame(BHTranslator.getInstance().translate("title"));
		
		
		//fill Tree
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("BusinessHorizon");
		
		List<DTOProject> repoList = projectRepoManager.getRepositoryList();
		
		for(DTOProject project : repoList){
			
			//create project...
			DefaultMutableTreeNode projectNode = new DefaultMutableTreeNode(((StringValue)project.get(DTOProject.Key.NAME)).getString());
			
			//and add scenarios...
			DefaultMutableTreeNode scenarioNode;
			for(DTOScenario scenario : project.getChildren()){
				scenarioNode = new DefaultMutableTreeNode(((StringValue)scenario.get(DTOScenario.Key.NAME)).getString());
				projectNode.add(scenarioNode);
				
				//if periods are available - add them!
				DefaultMutableTreeNode periodNode;
				for(DTOPeriod period : scenario.getChildren()){
					periodNode = new DefaultMutableTreeNode(((StringValue)period.get(DTOPeriod.Key.IDENTIFIER)).getString());
				}
				
				
			}
			
			
			//in the end, add all to rootNode
			rootNode.add(projectNode);
			
		}
		
//		DefaultMutableTreeNode project1;
//	    DefaultMutableTreeNode project2;
//		
//	    
//		
//		project1 = new DefaultMutableTreeNode("Project 1");
//		project2 = new DefaultMutableTreeNode("Project 2");
//		
//		project1.add(new DefaultMutableTreeNode("Dummy-Szenario1"));
//		project1.add(new DefaultMutableTreeNode("Dummy-Szenario2"));
//		project1.add(new DefaultMutableTreeNode("Dummy-Szenario3"));
//		project1.add(new DefaultMutableTreeNode("Dummy-Szenario4"));
//		
//		project2.add(new DefaultMutableTreeNode("Dummy-Szenario1"));
//		project2.add(new DefaultMutableTreeNode("Dummy-Szenario2"));
//		project2.add(new DefaultMutableTreeNode("Dummy-Szenario3"));
//		project2.add(new DefaultMutableTreeNode("Dummy-Szenario4"));
//		
//		rootNode.add(project1);
//		rootNode.add(project2);
		DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
		
		bhmf.BHTree.refresh(treeModel);
		
		
		// handle events...

		PlatformActionListener pal = new PlatformActionListener();

		// add ActionListener to...
		// .. the toolbar
		for (IBHAction item : (new BHButton()).getPlatformItems()) {
			item.addActionListener(pal);
		}

		// ...the menu
		for (IBHAction item : (new BHMenuItem()).getPlatformItems()) {
			item.addActionListener(pal);
		}

	}

	
	
	/*
	 * ----------------------------------------------------------------------------
	 */
	
	/**
	 * The PlatformActionListener handles all actions that are fired by a button
	 * etc. of the platform.
	 */
	class PlatformActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent aEvent) {

			// get actionKey of fired action
			PlatformKey actionKey = null;
			if (((IBHComponent) aEvent.getSource()) instanceof BHMenuItem)
				actionKey = ((BHMenuItem) aEvent.getSource()).getPlatformKey();

			if (((IBHComponent) aEvent.getSource()) instanceof BHButton)
				actionKey = ((BHButton)aEvent.getSource()).getPlatformKey();


			switch (actionKey) {
			case FILENEW:
				System.out.println("FILENEW gefeuert");
				break;
			case FILEOPEN:
				System.out.println("FILEOPEN gefeuert");
				int returnVal = bhmf.getChooser().showOpenDialog(bhmf);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					System.out.println("You chose to open this file: "
							+ bhmf.getChooser().getSelectedFile().getName());
					// TODO @Loeckelt.Michael: Tu es !
				}
				break;
			default:
				System.out.println("Was anderes, und zwar: "+actionKey.toString());
				break;
			}
		}

	}

	// @Override
	// public void mouseEntered(MouseEvent e) {
	// this.bhStatusBar.setToolTip(getToolTip());
	// //BHStatusBar.setValidationToolTip(new JLabel("Test"));
	// }
	// @Override
	// public void mouseExited(MouseEvent e) {
	// this.bhStatusBar.setToolTip("");
	// }
	//	
	//	
	//	
	// @Override
	// public void actionPerformed(ActionEvent actionEvent) {
	// String cmd = actionEvent.getActionCommand();
	//	        
	// // Handle each button.
	// if (cmd.equals("addP")) { //add project button clicked
	// System.out.println("add project");
	// BHTree.addProject("New Project " + BHTree.getNodeSuffix());
	// BHMainFrame.addContentForms(new BHFormsPanel());
	//	    	
	// } else if(cmd.equals("addS")){
	// BHTree.addScenario("New Scenario");
	// BHMainFrame.addContentFormsAndChart(new BHFormsPanel(), new JPanel());
	// } else if (cmd.equals("remove")) {
	// //Remove button clicked
	// BHTree.removeCurrentNode();
	//            
	// } else if (cmd.equals("delete")) {
	// //Clear button clicked.
	// BHTree.clear();
	// BHTree.setNodeSuffix();
	// } else if(cmd.equals("open")){
	// fc = new JFileChooser();
	// fc.setPreferredSize(new Dimension(600,400));
	// fc.showOpenDialog(this);
	//        	
	// }
	//	       
	// //Action of Tree
	// public static DefaultMutableTreeNode addScenario(Object child) {
	//	    	
	// TreePath parentPath = tree.getSelectionPath();
	// System.out.println(parentPath);
	//	        
	// DefaultMutableTreeNode parentNode =
	// (DefaultMutableTreeNode)(parentPath.getPathComponent(1));
	//	        
	// DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
	//	        
	// //It is key to invoke this on the TreeModel, and NOT
	// DefaultMutableTreeNode
	// treeModel.insertNodeInto(childNode, parentNode,
	// parentNode.getChildCount());
	//
	// //Make sure the user can see the lovely new node.
	// tree.scrollPathToVisible(new TreePath(childNode.getPath()));
	//	        
	// return childNode;
	// }
	//	    
	// public static DefaultMutableTreeNode addProject(Object child) {
	//	    	
	// DefaultMutableTreeNode parentNode = rootNode;
	//	        
	// DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
	//	       
	// //It is key to invoke this on the TreeModel, and NOT
	// DefaultMutableTreeNode
	// treeModel.insertNodeInto(childNode, parentNode,
	// parentNode.getChildCount());
	//
	// //Make sure the user can see the lovely new node.
	// tree.scrollPathToVisible(new TreePath(childNode.getPath()));
	//	        
	// return childNode;
	// }
	//	    
	// /** Remove the currently selected node. */
	// public static void removeCurrentNode() {
	// TreePath currentSelection = tree.getSelectionPath();
	// if (currentSelection != null) {
	// DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
	// (currentSelection.getLastPathComponent());
	// MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
	// if (parent != null) {
	// treeModel.removeNodeFromParent(currentNode);
	// return;
	// }
	// }
	//
	// // Either there was no selection, or the root was selected.
	// toolkit.beep();
	// }
	//	    
	// //TreeListener
	// class MyTreeModelListener implements TreeModelListener {
	// public void treeNodesChanged(TreeModelEvent e) {
	// DefaultMutableTreeNode node;
	// node = (DefaultMutableTreeNode)(e.getTreePath().getLastPathComponent());
	//
	// /*
	// * If the event lists children, then the changed
	// * node is the child of the node we've already
	// * gotten. Otherwise, the changed node and the
	// * specified node are the same.
	// */
	//
	// int index = e.getChildIndices()[0];
	// node = (DefaultMutableTreeNode)(node.getChildAt(index));
	//
	// System.out.println("The user has finished editing the node.");
	// System.out.println("New value: " + node.getUserObject());
	// }
	// public void treeNodesInserted(TreeModelEvent e) {
	// }
	// public void treeNodesRemoved(TreeModelEvent e) {
	// }
	// public void treeStructureChanged(TreeModelEvent e) {
	// }
	// }
	//	    
	//	    
	// }

}
