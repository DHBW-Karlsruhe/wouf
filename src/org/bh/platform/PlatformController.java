package org.bh.platform;

import java.util.List;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.bh.data.DTO;
import org.bh.data.DTOPeriod;
import org.bh.data.DTOProject;
import org.bh.data.DTOScenario;
import org.bh.data.types.StringValue;
import org.bh.gui.ViewException;
import org.bh.gui.swing.BHButton;
import org.bh.gui.swing.BHMainFrame;
import org.bh.gui.swing.BHMenuItem;
import org.bh.gui.swing.BHProjectInputForm;
import org.bh.gui.swing.BHProjectView;
import org.bh.gui.swing.BHScenarioHeadForm;
import org.bh.gui.swing.BHScenarioView;
import org.bh.gui.swing.BHTreeNode;
import org.bh.gui.swing.IBHAction;

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
		PlatformActionListener pal = new PlatformActionListener(bhmf, projectRepoManager, this);

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
	protected void setupTree(BHMainFrame bhmf, ProjectRepositoryManager projectRepoManager){
		
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("BusinessHorizon");
		
		List<DTOProject> repoList = projectRepoManager.getRepositoryList();
		
		for(DTOProject project : repoList){
			
			//create project...
			BHTreeNode projectNode = new BHTreeNode(project);
			
			//...and add scenarios...
			BHTreeNode scenarioNode;
			for(DTOScenario scenario : project.getChildren()){
				scenarioNode = new BHTreeNode(scenario);
				projectNode.add(scenarioNode);
				
				//if periods are available - add them!
				BHTreeNode periodNode;
				for(DTOPeriod period : scenario.getChildren()){
					periodNode = new BHTreeNode(period);
					scenarioNode.add(periodNode);
				}
			}
			
			//in the end, add all to rootNode
			rootNode.add(projectNode);	
		}
		
		bhmf.getBHTree().setTreeModel(new BHTreeModel(rootNode));
		bhmf.getBHTree().addTreeSelectionListener(new BHTreeSelectionListener());
	}
	
	
	class BHTreeSelectionListener implements TreeSelectionListener{

		@Override
		public void valueChanged(TreeSelectionEvent tse) {
			DTO<?> selection = (DTO<?>)((BHTreeNode)tse.getPath().getLastPathComponent()).getUserObject();
			if(selection instanceof DTOProject){
				try {
					BHProjectView proView = new BHProjectView(new BHProjectInputForm());
					bhmf.addContentForms(proView.getViewPanel());
				} catch (ViewException e) {
					e.printStackTrace();
				}
			
			}else if(selection instanceof DTOScenario){
				try {
					BHScenarioView scenarioView = new BHScenarioView(new BHScenarioHeadForm());
					bhmf.addContentForms(scenarioView.getViewPanel());
				} catch (ViewException e) {
					e.printStackTrace();
				}
				
			}else if(selection instanceof DTOPeriod){
				
			}
		}
		
	}
	
	
	class BHTreeModel extends DefaultTreeModel{
		
		public BHTreeModel(TreeNode root) {
			super(root);
		}
		
		
		public void valueForPathChanged(TreePath path, Object newValue) {
			((DTO<?>)((BHTreeNode)path.getLastPathComponent()).getUserObject()).put(DTOProject.Key.NAME, new StringValue(newValue.toString()));
		}
	}
	
}
