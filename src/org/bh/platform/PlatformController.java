package org.bh.platform;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.event.*;
import javax.swing.tree.*;
import org.bh.data.*;
import org.bh.data.types.StringValue;
import org.bh.gui.swing.*;

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
		period1.put(DTOPeriod.Key.NAME, periodIdent);
		
		project1.addChild(scenario1);
		StringValue scenarioName = new StringValue("Scenario1");
		scenario1.put(DTOScenario.Key.NAME, scenarioName);
		
		projectRepoManager.addProject(project1);
		
		
		/*------------------------------------
		 * start mainFrame
		 * -----------------------------------
		 */
		bhmf = new BHMainFrame(Services.getTranslator().translate("title"));
		
		
		/*------------------------------------
		 * fill Project/Scenario/Period-Tree
		 * -----------------------------------
		 */
		setupTree(bhmf, projectRepoManager);
		
		
		
		/*------------------------------------
		 * Add EventHandler to Platform-Items
		 * -----------------------------------
		 */
		PlatformActionListener pal = new PlatformActionListener();

		//Add ActionListener to Toolbar-buttons
		for (IBHAction item : (new BHButton(true)).getPlatformItems()) {
			item.addActionListener(pal);
		}

		//Add ActionListener to the menu-items
		for (IBHAction item : (new BHMenuItem()).getPlatformItems()) {
			item.addActionListener(pal);
		}
	}
	
	
	/*------------------------------------
	 * Methods for Tree-Handling
	 * -----------------------------------
	 */
	private void setupTree(BHMainFrame bhmf, ProjectRepositoryManager projectRepoManager){
		
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("BusinessHorizon");
		
		List<DTOProject> repoList = projectRepoManager.getRepositoryList();
		
		for(DTOProject project : repoList){
			
			//create project...
			BHNode projectNode = new BHNode(project);
			
			//...and add scenarios...
			BHNode scenarioNode;
			for(DTOScenario scenario : project.getChildren()){
				scenarioNode = new BHNode(scenario);
				projectNode.add(scenarioNode);
				
				//if periods are available - add them!
				BHNode periodNode;
				for(DTOPeriod period : scenario.getChildren()){
					periodNode = new BHNode(period);
					scenarioNode.add(periodNode);
				}
			}
			
			//in the end, add all to rootNode
			rootNode.add(projectNode);	
		}
		
		bhmf.getBHTree().setTreeModel(new BHTreeModel(rootNode));
		bhmf.getBHTree().getModel().addTreeModelListener(new BHTreeModelListener());
		bhmf.getBHTree().addTreeSelectionListener(new BHTreeSelectionListener());
	}
	
	
	class BHTreeSelectionListener implements TreeSelectionListener{

		@Override
		public void valueChanged(TreeSelectionEvent tse) {
			DTO selection = (DTO)((BHNode)tse.getPath().getLastPathComponent()).getUserObject();
			if(selection instanceof DTOProject){
				bhmf.addContentForms(new BHProjectInputForm());
			
			}else if(selection instanceof DTOScenario){
				System.out.println("Scenario");
				bhmf.addContentForms(new BHFormsPanel());
				
			}else if(selection instanceof DTOPeriod){
				System.out.println("Periode");
				
			}
			
		}
		
	}
	
	
	class BHTreeModel extends DefaultTreeModel{
		
		public BHTreeModel(TreeNode root) {
			super(root);
		}
		
		
		public void valueForPathChanged(TreePath path, Object newValue) {
			((DTO)((BHNode)path.getLastPathComponent()).getUserObject()).put(DTOProject.Key.NAME, new StringValue(newValue.toString()));
		}
		
	}
	
	
	/**
	 * 
	 * Class which provides the toString-Method of Nodes wich appropriate information.
	 *
	 * <p>
	 * -
	 *
	 * @author Alexander Schmalzhaf
	 * @version 1.0, 28.12.2009
	 *
	 */
	class BHNode extends DefaultMutableTreeNode{
		BHNode(Object ob){
			super(ob);
		}
		
		public String toString(){
			return ((StringValue)((DTO)userObject).get(DTOProject.Key.NAME)).getString();
		}
	}
	
	
	/*
	 * ----------------------------------------------------------------------------
	 */
	
	class BHTreeModelListener implements TreeModelListener{
		
		@Override
		public void treeNodesChanged(TreeModelEvent e) {
			System.out.println("Text geändert");
		}

		@Override
		public void treeNodesInserted(TreeModelEvent e) {
			
		}

		@Override
		public void treeNodesRemoved(TreeModelEvent e) {
			
		}

		@Override
		public void treeStructureChanged(TreeModelEvent e) {
			
		}
		
	}
	
	
	/**
	 * The PlatformActionListener handles all actions that are fired by a button
	 * etc. of the platform.
	 */
	class PlatformActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent aEvent) {

			// get actionKey of fired action
			PlatformKey actionKey = ((IBHAction) aEvent.getSource()).getPlatformKey();;

			//do right action...
			switch (actionKey) {
			
			case FILENEW:
				
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
				
			case FILESAVE:
				//TODO Prüfen und ggf. implementieren!
				break;
				
			case FILESAVEAS:
				//TODO Prüfen und ggf. implementieren!
				break;
				
			case FILEQUIT:
				//TODO Prüfen und ggf. implementieren!
				break;
				
			case PROJECTCREATE:
				//TODO Prüfen und ggf. implementieren!
				break;
				
			case PROJECTRENAME:
				//TODO Prüfen und ggf. implementieren!
				break;
				
			case PROJECTDUPLICATE:
				//TODO Prüfen und ggf. implementieren!
				break;
				
			case PROJECTIMPORT:
				//TODO Prüfen und ggf. implementieren!
				break;
				
			case PROJECTEXPORT:
				//TODO Prüfen und ggf. implementieren!
				break;
				
			case PROJECTREMOVE:
				//TODO Prüfen und ggf. implementieren!
				break;
				
			case SCENARIOCREATE:
				//TODO Prüfen und ggf. implementieren!
				break;
				
			case SCENARIORENAME:
				//TODO Prüfen und ggf. implementieren!
				break;
				
			case SCENARIODUPLICATE:
				//TODO Prüfen und ggf. implementieren!
				break;
				
			case SCENARIOMOVE:
				//TODO Prüfen und ggf. implementieren!
				break;
				
			case SCENARIOREMOVE:
				//TODO Prüfen und ggf. implementieren!
				break;
				
			case BILANZGUVSHOW:
				//TODO Prüfen und ggf. implementieren!
				break;
				
			case BILANZGUVCREATE:
				//TODO Prüfen und ggf. implementieren!
				break;
				
			case BILANZGUVIMPORT:
				//TODO Prüfen und ggf. implementieren!
				break;
				
			case BILANZGUVREMOVE:
				//TODO Prüfen und ggf. implementieren!
				break;
				
			case OPTIONSCHANGE:
				//TODO Prüfen und ggf. implementieren!
				break;
				
			case HELPUSERHELP:
				//TODO Prüfen und ggf. implementieren!
				break;
				
			case HELPMATHHELP:
				//TODO Prüfen und ggf. implementieren!
				break;
				
			case HELPINFO:
				//TODO Prüfen und ggf. implementieren!
				break;
				
			case TOOLBAROPEN: 
				//TODO Prüfen und ggf. implementieren!
				break;
				
			case TOOLBARSAVE: 
				//TODO Prüfen und ggf. implementieren!
				break; 
				
			case TOOLBARADDPRO:
				//Create new project
				DTOProject newProject = new DTOProject();
				//TODO hardgecodeder String raus! AS
				newProject.put(DTOProject.Key.NAME, new StringValue("neues Projekt"));
				//add it to DTO-Repository
				projectRepoManager.addProject(newProject);
				//and create a Node for tree on gui
				BHNode newProjectNode = new BHNode(newProject);
				((DefaultTreeModel)bhmf.getBHTree().getModel()).insertNodeInto(
						newProjectNode, 
						(DefaultMutableTreeNode)bhmf.getBHTree().getModel().getRoot(), 
						((DefaultMutableTreeNode)bhmf.getBHTree().getModel().getRoot()).getChildCount()
				);
				
				//last steps: unfold tree to new element, set focus and start editing
				bhmf.getBHTree().scrollPathToVisible(new TreePath(newProjectNode.getPath()));
				bhmf.getBHTree().startEditingAtPath(new TreePath(newProjectNode.getPath()));
				
				break;
				
			case TOOLBARADDS: 
				//If a path is selected...
				if(bhmf.getBHTree().getSelectionPath() != null){
					//...create new scenario
					DTOScenario newScenario = new DTOScenario();
					//TODO hardgecodeder String raus! AS
					newScenario.put(DTOScenario.Key.NAME, new StringValue("neues Scenario"));
					
					//...add it to DTO-Repository
					((DTOProject)((BHNode)bhmf.getBHTree().getSelectionPath().getPathComponent(1)).getUserObject()).addChild(newScenario);
					
					//...and insert it into GUI-Tree
					BHNode newScenarioNode = new BHNode(newScenario);
					((BHTreeModel)bhmf.getBHTree().getModel()).insertNodeInto(
							newScenarioNode, 
							(BHNode)(bhmf.getBHTree().getSelectionPath().getPathComponent(1)), 
							((BHNode) bhmf.getBHTree().getSelectionPath().getPathComponent(1)).getChildCount()
					);
					
					//last steps: unfold tree to new element, set focus and start editing
					bhmf.getBHTree().scrollPathToVisible(new TreePath(newScenarioNode.getPath()));
					bhmf.getBHTree().startEditingAtPath(new TreePath(newScenarioNode.getPath()));
				}
				
								
				break;
			
			case TOOLBARADDPER:
				//If a scenario or a period is selected...
				if(bhmf.getBHTree().getSelectionPath()!=null && bhmf.getBHTree().getSelectionPath().getPathCount()>2){
					//...create new period
					DTOPeriod newPeriod = new DTOPeriod();
					//TODO hardgecodeder String raus! AS
					newPeriod.put(DTOPeriod.Key.NAME, new StringValue("neue Periode"));
					
					//...add it to DTO-Repository
					((DTOScenario)((BHNode)bhmf.getBHTree().getSelectionPath().getPathComponent(2)).getUserObject()).addChild(newPeriod);
					
					//...and insert it into GUI-Tree
					BHNode newPeriodNode = new BHNode(newPeriod);
					((BHTreeModel)bhmf.getBHTree().getModel()).insertNodeInto(
							newPeriodNode,
							(BHNode)(bhmf.getBHTree().getSelectionPath().getPathComponent(2)), 
							((BHNode) bhmf.getBHTree().getSelectionPath().getPathComponent(2)).getChildCount()
					);
					
					//last steps: unfold tree to new element, set focus and start editing
					bhmf.getBHTree().scrollPathToVisible(new TreePath(newPeriodNode.getPath()));
					bhmf.getBHTree().startEditingAtPath(new TreePath(newPeriodNode.getPath()));
				}
				
				
				break;
				
			case TOOLBARREMOVE:
				
				TreePath currentSelection = bhmf.getBHTree().getSelectionPath();
				//is a node selected?
				if (currentSelection != null) {
					//remove node from GUI...
					 BHNode currentNode = (BHNode)bhmf.getBHTree().getSelectionPath().getLastPathComponent();
					 ((BHTreeModel) bhmf.getBHTree().getModel()).removeNodeFromParent(currentNode);
					 
					 //..and from data model
					 if(currentNode.getUserObject() instanceof DTOProject){
						 projectRepoManager.removeProject((DTOProject) currentNode.getUserObject());
					 }else if(currentNode.getUserObject() instanceof DTOScenario){
						 //((DTOScenario)currentNode.getUserObject())
					 }else if(currentNode.getUserObject() instanceof DTOPeriod){
						 
					 }
				}
				
				
				
				break;
				
			default:
				System.out.println("Was anderes, und zwar: "+actionKey.getActionKey());
				break;
			}
		}

	}
}
