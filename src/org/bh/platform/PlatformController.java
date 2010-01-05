package org.bh.platform;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;
import org.bh.controller.InputController;
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
import org.bh.gui.swing.BHTextField;
import org.bh.gui.swing.BHTreeNode;
import org.bh.gui.swing.IBHAction;
import org.bh.gui.swing.IBHModelComponent;

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

	DTO<?> projectModel;
	DTO<?> scenarioModel;
	BHProjectView projectView;
	BHScenarioView scenarioView;

	/**
	 * Reference to a preference object which allows platform independent
	 * 
	 * @author Marcus Katzor
	 */
	public static Preferences preferences;

	/**
	 * Path to the properties file
	 * 
	 * @author Marcus Katzor
	 */
	private static final String propertiesFilePath = "";

	/**
	 * Logging
	 */
	private static final Logger log = Logger
			.getLogger(PlatformController.class);

	public PlatformController() {

		/**
		 * Try to load properties
		 * 
		 * @author Marcus Katzor
		 */
		log.debug("Loading properties");
		preferences = Preferences.userNodeForPackage(PlatformController.class);

		/*------------------------------------
		 * Fill Repo (sample DTOs)
		 * -----------------------------------
		 */

		/*
		 * DTOProject project1 = new DTOProject(); DTOScenario scenario1 = new
		 * DTOScenario(true); DTOPeriod period1 = new DTOPeriod();
		 * 
		 * 
		 * StringValue projectName = new StringValue("project1");
		 * project1.put(DTOProject.Key.NAME, projectName);
		 * 
		 * scenario1.addChild(period1); StringValue periodIdent = new
		 * StringValue("Periode 1"); period1.put(DTOPeriod.Key.NAME,
		 * periodIdent);
		 * 
		 * project1.addChild(scenario1); StringValue scenarioName = new
		 * StringValue("Scenario1"); scenario1.put(DTOScenario.Key.NAME,
		 * scenarioName);
		 * 
		 * projectRepoManager.addProject(project1);
		 */

		/*------------------------------------
		 * start mainFrame
		 * -----------------------------------
		 */
		bhmf = new BHMainFrame();

		/*------------------------------------
		 * fill Project/Scenario/Period-Tree
		 * -----------------------------------
		 */
		setupTree(bhmf, projectRepoManager);

		/*------------------------------------
		 * Add EventHandler to Platform-Items
		 * -----------------------------------
		 */
		PlatformActionListener pal = new PlatformActionListener(bhmf,
				projectRepoManager, this);

		// Add ActionListener to Toolbar-buttons
		for (IBHAction item : BHButton.getPlatformItems()) {
			item.addActionListener(pal);
		}

		// Add ActionListener to the menu-items
		for (IBHAction item : BHMenuItem.getPlatformItems()) {
			item.addActionListener(pal);
		}

	}

	/*------------------------------------
	 * Methods for Tree-Handling
	 * -----------------------------------
	 */
	protected void setupTree(BHMainFrame bhmf,
			ProjectRepositoryManager projectRepoManager) {

		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(
				"BusinessHorizon");

		List<DTOProject> repoList = projectRepoManager.getRepositoryList();

		for (DTOProject project : repoList) {

			// create project...
			BHTreeNode projectNode = new BHTreeNode(project);

			// ...and add scenarios...
			BHTreeNode scenarioNode;
			for (DTOScenario scenario : project.getChildren()) {
				scenarioNode = new BHTreeNode(scenario);
				projectNode.add(scenarioNode);

				// if periods are available - add them!
				BHTreeNode periodNode;
				for (DTOPeriod period : scenario.getChildren()) {
					periodNode = new BHTreeNode(period);
					scenarioNode.add(periodNode);
				}
			}

			// in the end, add all to rootNode
			rootNode.add(projectNode);
		}

		bhmf.getBHTree().setTreeModel(new BHTreeModel(rootNode));
		bhmf.getBHTree()
				.addTreeSelectionListener(new BHTreeSelectionListener());

		Services.addPlatformListener(new DataChangedListener());
	}

	// TODO Schmalzhaf.Alexander Javadoc schreiben
	/**
	 * 
	 * <short_description>
	 * 
	 * <p>
	 * <detailed_description>
	 * 
	 * @author 001
	 * @version 1.0, 04.01.2010
	 * 
	 */
	class BHTreeSelectionListener implements TreeSelectionListener {

		@Override
		public void valueChanged(TreeSelectionEvent tse) {
			DTO<?> selection = (DTO<?>) ((BHTreeNode) tse.getPath()
					.getLastPathComponent()).getUserObject();
			if (selection instanceof DTOProject) {
				try {
					projectView = new BHProjectView(new BHProjectInputForm());
					projectModel = selection;
					bhmf.addContentForms(projectView.getViewPanel());
					((BHTextField) projectView
							.getBHModelComponent(DTOProject.Key.NAME))
							.addFocusListener(new SaveListener(projectModel));

					InputController.loadAllToView(projectModel, projectView);

				} catch (ViewException e) {
					e.printStackTrace();
				}

			} else if (selection instanceof DTOScenario) {
				try {
					scenarioView = new BHScenarioView(new BHScenarioHeadForm());
					scenarioModel = selection;
					bhmf.addContentForms(scenarioView.getViewPanel());
					((BHTextField) scenarioView
							.getBHModelComponent(DTOScenario.Key.NAME))
							.addFocusListener(new SaveListener(scenarioModel));

					InputController.loadAllToView(scenarioModel, scenarioView);

				} catch (ViewException e) {
					e.printStackTrace();
				}

			} else if (selection instanceof DTOPeriod) {
				// TODO Schmalzhaf.Alexander muss noch implementiert werden
			}
		}
	}

	// TODO Schmalzhaf.Alexander Javadoc schreiben
	/**
	 * 
	 * <short_description>
	 * 
	 * <p>
	 * <detailed_description>
	 * 
	 * @author 001
	 * @version 1.0, 04.01.2010
	 * 
	 */
	class DataChangedListener implements IPlatformListener {

		public void platformEvent(PlatformEvent e) {
			if (e.getEventType() != PlatformEvent.Type.DATA_CHANGED)
				return;

			if (e.getSource() == projectModel) {
				InputController.loadAllToView(projectModel, projectView);
			} else if (e.getSource() == scenarioModel) {
				InputController.loadAllToView(scenarioModel, scenarioView);
			}

			if (e.getSource() instanceof DTOProject
					|| e.getSource() instanceof DTOScenario
					|| e.getSource() instanceof DTOPeriod) {
				bhmf.getBHTree().updateUI();
			}
		}
	}

	// TODO Schmalzhaf.Alexander Javadoc schreiben
	/**
	 * 
	 * <short_description>
	 * 
	 * <p>
	 * <detailed_description>
	 * 
	 * @author 001
	 * @version 1.0, 04.01.2010
	 * 
	 */
	class BHTreeModel extends DefaultTreeModel {

		public BHTreeModel(TreeNode root) {
			super(root);
		}

		@Override
		public void valueForPathChanged(TreePath path, Object newValue) {
			DTO<?> tempDTO = (DTO<?>) ((BHTreeNode) path.getLastPathComponent())
					.getUserObject();
			if (tempDTO instanceof DTOProject) {
				tempDTO.put(DTOProject.Key.NAME, new StringValue(newValue
						.toString()));
			} else if (tempDTO instanceof DTOScenario) {
				tempDTO.put(DTOScenario.Key.NAME, new StringValue(newValue
						.toString()));
			} else if (tempDTO instanceof DTOPeriod) {
				tempDTO.put(DTOPeriod.Key.NAME, new StringValue(newValue
						.toString()));
			}
		}
	}

	class SaveListener implements FocusListener {
		private DTO<?> model;

		public SaveListener(DTO<?> model) {
			this.model = model;
		}

		@Override
		public void focusGained(FocusEvent fEvent) {
			// do nothing!
		}

		@Override
		public void focusLost(FocusEvent fEvent) {
			// update DTO
			InputController.saveToModel((IBHModelComponent) fEvent.getSource(),
					model);
		}
	}
}
