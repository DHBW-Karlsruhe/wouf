package org.bh.platform;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;
import org.bh.controller.InputController;
import org.bh.data.DTO;
import org.bh.data.DTOAccessException;
import org.bh.data.DTOPeriod;
import org.bh.data.DTOProject;
import org.bh.data.DTOScenario;
import org.bh.data.IDTO;
import org.bh.data.IPeriodicalValuesDTO;
import org.bh.data.types.IValue;
import org.bh.data.types.StringValue;
import org.bh.gui.ValidationMethods;
import org.bh.gui.View;
import org.bh.gui.ViewException;
import org.bh.gui.swing.BHButton;
import org.bh.gui.swing.BHContent;
import org.bh.gui.swing.BHDataExchangeDialog;
import org.bh.gui.swing.BHDeterministicProcessForm;
import org.bh.gui.swing.BHMainFrame;
import org.bh.gui.swing.BHMenuItem;
import org.bh.gui.swing.BHProjectForm;
import org.bh.gui.swing.BHProjectView;
import org.bh.gui.swing.BHScenarioForm;
import org.bh.gui.swing.BHScenarioView;
import org.bh.gui.swing.BHTreeNode;
import org.bh.gui.swing.IBHAction;
import org.bh.platform.PlatformEvent.Type;

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

	/**
	 * Instance for Singleton; PlugIns can get access to all parts of Platform
	 * through that
	 */
	private static PlatformController singletonInstance;

	BHMainFrame bhmf;
	private ProjectRepositoryManager projectRepoManager = ProjectRepositoryManager
			.getInstance();

	InputController controller = null;

	/**
	 * Reference to a preference object which allows platform independent
	 * 
	 * @author Marcus Katzor
	 */
	public static Preferences preferences = Preferences
			.userNodeForPackage(PlatformController.class);

	/**
	 * Path to the properties file
	 * 
	 * @author Marcus Katzor
	 */
	private static final String propertiesFilePath = "";

	/**
	 * PlatformPersistenceManager Instance
	 * 
	 * @author Loeckelt.Michael
	 */
	public static PlatformPersistenceManager platformPersistenceManager;

	/**
	 * PlatformactionListener
	 */
	public PlatformActionListener pal;

	/**
	 * Logging
	 */
	private static final Logger log = Logger
			.getLogger(PlatformController.class);

	public static PlatformController getInstance() {
		if (singletonInstance == null) {
			singletonInstance = new PlatformController();
		}
		return singletonInstance;
	}

	private PlatformController() {

		/*------------------------------------
		 * start mainFrame
		 * -----------------------------------
		 */
		bhmf = new BHMainFrame();
		Services.setBHMainFrame(bhmf);

		// create PlatformU...
		// TODO naming
		PlatformUserDialog.init(bhmf);

		/*------------------------------------
		 * fill Project/Scenario/Period-Tree
		 * -----------------------------------
		 */
		setupTree(bhmf, projectRepoManager);

		/*------------------------------------
		 * Add EventHandler to Platform-Items
		 * -----------------------------------
		 */
		pal = new PlatformActionListener(bhmf, projectRepoManager, this);

		// Add ActionListener to Toolbar-buttons
		for (IBHAction item : BHButton.getPlatformItems()) {
			item.addActionListener(pal);
		}

		// Add ActionListener to the menu-items
		for (IBHAction item : BHMenuItem.getPlatformItems()) {
			item.addActionListener(pal);
		}

		/*
		 * Create a new Persistence instance
		 * 
		 * @author Loeckelt.Michael
		 */
		platformPersistenceManager = new PlatformPersistenceManager(bhmf,
				projectRepoManager);

		// Fire event.
		Services.firePlatformEvent(new PlatformEvent(this,
				Type.PLATFORM_LOADING_COMPLETED));

		platformPersistenceManager.lastEditedFile();

		// rebuild Tree
		setupTree(bhmf, projectRepoManager);
		bhmf.getBHTree().expandAll();

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

		// avoid to sum number of listeners -- done in context with ticket #85
		if (bhmf.getBHTree().getTreeSelectionListeners().length < 3) {
			bhmf.getBHTree().addTreeSelectionListener(
					new BHTreeSelectionListener());
		}

		Services.addPlatformListener(new DataChangedListener());

	}

	public void addProject(DTOProject newProject) {
		projectRepoManager.addProject(newProject);

		// and create a Node for tree on gui
		BHTreeNode newProjectNode = bhmf.getBHTree().addProject(newProject);

		// last steps: unfold tree to new element, set focus and start editing
		bhmf.getBHTree().scrollPathToVisible(
				new TreePath(newProjectNode.getPath()));
		bhmf.getBHTree().startEditingAtPath(
				new TreePath(newProjectNode.getPath()));
	}
	
	public BHDataExchangeDialog createBalanceSheetAndPLSExchangeDialog()
	{
		// Create data exchange dialog
		return new BHDataExchangeDialog(
				bhmf, true);
		
	}
	

	/*------------------------------------
	 * Subclasses
	 * -----------------------------------
	 */

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
		public void valueChanged(final TreeSelectionEvent tse) {
			if (tse.getNewLeadSelectionPath() == null) {
				bhmf.setContentForm(new BHContent());
			} else {
				//save divider location of split pane
				BHTreeNode oldActiveNode;
				if(tse.getOldLeadSelectionPath() != null){
					TreePath tp = tse.getOldLeadSelectionPath();
					oldActiveNode = (BHTreeNode)tp.getLastPathComponent();
					oldActiveNode.setDividerLocation(bhmf.getVDividerLocation());
				}
				
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						if (tse.getPath().getLastPathComponent() instanceof BHTreeNode) {
							BHTreeNode selectedNode = (BHTreeNode) tse
									.getPath().getLastPathComponent();
							DTO<?> selectedDto = (DTO<?>) selectedNode
									.getUserObject();
							if (selectedDto instanceof DTOProject) {
								try {
									View view = new BHProjectView(
											new BHProjectForm());

									IDTO<?> model = selectedDto;
									controller = new ProjectController(view,
											model);
									
									//TODO START VIEW
									
									//set content and result (=dashboard) if available
									bhmf.setContentForm(view.getViewPanel());
									JScrollPane resultPane;
									if ((resultPane = selectedNode
											.getResultPane()) != null) {
										bhmf.setResultForm(resultPane);
										bhmf.setVDividerLocation(selectedNode.getDividerLocation());
									}else{
										bhmf.removeResultForm();
									}
									
									//TODO START VIEW
									
									controller.loadAllToView();

								} catch (ViewException e) {
									e.printStackTrace();
								}

							} else if (selectedDto instanceof DTOScenario) {
								try {

									DTOScenario model = (DTOScenario) selectedDto;

									// check if controller is already there...
									if ((controller = selectedNode
											.getController()) == null) {

										// if not, create view at first

										View view;
										// find out if stochastic process was
										// chosen
										// at init of strategy
										if (!model.isDeterministic()) {
											view = new BHScenarioView(
													new BHScenarioForm(
															BHScenarioForm.Type.STOCHASTIC,
															model
																	.isIntervalArithmetic()),
													new ValidationMethods());
										} else {
											view = new BHScenarioView(
													new BHScenarioForm(
															BHScenarioForm.Type.DETERMINISTIC,
															model
																	.isIntervalArithmetic()),
													new ValidationMethods());
										}

										// create controller
										controller = new ScenarioController(
												view, model, bhmf);
										selectedNode.setController(controller);
									}

									if (model.isDeterministic()) {
										// if scenario is deterministic, an
										// overview
										// table is provided
										((BHDeterministicProcessForm) ((BHScenarioForm) controller
												.getViewPanel())
												.getProcessForm())
												.setPeriodTable(PlatformController.this
														.prepareScenarioTableData(model));
									}
									
									//set content and result panels to bhmf
									JScrollPane resultPane;
									
									bhmf.setContentForm(controller
											.getViewPanel());
									
									if ((resultPane = selectedNode
											.getResultPane()) != null) {
										bhmf.setResultForm(resultPane);
										bhmf.setVDividerLocation(selectedNode.getDividerLocation());	
									}else{
										bhmf.removeResultForm();
									}

								} catch (ViewException e) {
									e.printStackTrace();
								}

							} else if (selectedDto instanceof DTOPeriod) {
								bhmf.removeResultForm();
								Services
										.startPeriodEditing((DTOPeriod) selectedDto);
							}
						}

					}
				});
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

			if (e.getSource() instanceof DTOProject
					|| e.getSource() instanceof DTOScenario
					|| e.getSource() instanceof DTOPeriod
					|| e.getSource() instanceof IPeriodicalValuesDTO) {

				for (BHTreeNode projectNode : bhmf.getBHTree()
						.getProjectNodes()) {
					if (((DTOProject) projectNode.getUserObject())
							.isMeOrChild(e.getSource())) {
						bhmf.getBHTree().updateUI();
						break;
					}
				}
			}

			if (e.getSource() instanceof DTO<?>) {
				// check if data has changed and remove result panel from
				// scenario then...
				ArrayList<BHTreeNode> scenarioNodes = bhmf.getBHTree()
						.getScenarioNodes();
				if (!scenarioNodes.isEmpty()) {
					for (BHTreeNode scenarioNode : scenarioNodes) {
						if (((DTOScenario) scenarioNode.getUserObject())
								.isMeOrChild(e.getSource())
								&& scenarioNode.getResultPane() != null) {
							scenarioNode.setResultPane(null);

							// throw away present screen, if scenario is on
							// screen
							TreePath tp = bhmf.getBHTree().getSelectionPath();
							if (tp.getPathCount() == 3) {
								bhmf.moveOutResultForm();

							}
						}
					}
				}

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

			Object key = null;
			if (tempDTO instanceof DTOProject) {
				key = DTOProject.Key.NAME;
			} else if (tempDTO instanceof DTOScenario) {
				key = DTOScenario.Key.NAME;
			} else if (tempDTO instanceof DTOPeriod) {
				key = DTOPeriod.Key.NAME;
			}

			if (key != null) {
				tempDTO.put(key, new StringValue(newValue.toString()));
				if (controller != null && tempDTO == controller.getModel()) {
					controller.loadToView(key);
				}
			}
		}
	}

	public BHMainFrame getMainFrame() {
		return this.bhmf;
	}

	IValue[][] prepareScenarioTableData(DTOScenario scenarioDto) {
		@SuppressWarnings("unchecked")
		List<DTOPeriod> periods = (List<DTOPeriod>) scenarioDto.getChildren();

		IValue[][] periodData = new IValue[periods.size()][3];

		// transform data
		for (int i = 0; i < periods.size(); i++) {
			DTOPeriod period = periods.get(i);

			try {
				periodData[i][0] = period.get(DTOPeriod.Key.NAME);
			} catch (DTOAccessException dtoae) {
				log.error("Cannot get name for period table", dtoae);
			}

			if (!period.isValid(true))
				continue;

			try {
				periodData[i][1] = period.getLiabilities();
			} catch (DTOAccessException dtoae) {
				log.error("Cannot get liabilities for period table", dtoae);
			}
			try {
				periodData[i][2] = period.getFCF();
			} catch (DTOAccessException dtoae) {
				if (i > 0) {
					log.error("Cannot get FCF for period table", dtoae);
				}
			}
		}

		return periodData;
	}
}
