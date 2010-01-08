package org.bh.platform;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;
import org.bh.calculation.IShareholderValueCalculator;
import org.bh.controller.InputController;
import org.bh.data.DTO;
import org.bh.data.DTOAccessException;
import org.bh.data.DTOPeriod;
import org.bh.data.DTOProject;
import org.bh.data.DTOScenario;
import org.bh.data.IDTO;
import org.bh.data.types.Calculable;
import org.bh.data.types.StringValue;
import org.bh.gui.IDeterministicResultAnalyser;
import org.bh.gui.View;
import org.bh.gui.ViewException;
import org.bh.gui.swing.BHButton;
import org.bh.gui.swing.BHComboBox;
import org.bh.gui.swing.BHMainFrame;
import org.bh.gui.swing.BHMenuItem;
import org.bh.gui.swing.BHProjectInputForm;
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

	private BHMainFrame bhmf;
	private ProjectRepositoryManager projectRepoManager = new ProjectRepositoryManager();

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
	 * Logging
	 */
	private static final Logger log = Logger
			.getLogger(PlatformController.class);

	public PlatformController() {
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
		Services.setBHMainFrame(bhmf);

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
		public void valueChanged(final TreeSelectionEvent tse) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					if (tse.getPath().getLastPathComponent() instanceof BHTreeNode) {
						DTO<?> selection = (DTO<?>) ((BHTreeNode) tse.getPath()
								.getLastPathComponent()).getUserObject();
						if (selection instanceof DTOProject) {
							try {
								View view = new BHProjectView(
										new BHProjectInputForm());
								IDTO<?> model = selection;
								controller = new InputController(view, model);
								bhmf.setContentForm(view.getViewPanel());
								controller.loadAllToView();

							} catch (ViewException e) {
								e.printStackTrace();
							}

						} else if (selection instanceof DTOScenario) {
							try {
								View view = null;
								IDTO<?> model = selection;

								// find out if stochastic process was chosen at
								// init of strategy
								try {
									selection
											.get(DTOScenario.Key.STOCHASTIC_PROCESS);
									// when value is set -> next command will be
									// processed (else: not, but Exception)
									view = new BHScenarioView(
											new BHScenarioForm(
													BHScenarioForm.Type.STOCHASTIC));
								} catch (DTOAccessException e) {
									// Answer: no
									view = new BHScenarioView(
											new BHScenarioForm(
													BHScenarioForm.Type.DETERMINISTIC));
								}

								bhmf.setContentForm(view.getViewPanel());
								controller = new InputController(view, model);

								BHComboBox cbDcfMethod = (BHComboBox) view
										.getBHComponent(DTOScenario.Key.DCF_METHOD);
								Collection<IShareholderValueCalculator> dcfMethods = Services
										.getDCFMethods().values();
								ArrayList<BHComboBox.Item> items = new ArrayList<BHComboBox.Item>();
								for (IShareholderValueCalculator dcfMethod : dcfMethods) {
									items.add(new BHComboBox.Item(dcfMethod
											.getGuiKey(), new StringValue(
											dcfMethod.getUniqueId())));
								}
								cbDcfMethod.setSorted(true);
								cbDcfMethod.setValueList(items
										.toArray(new BHComboBox.Item[0]));

								// FIXME
								((BHButton) view
										.getBHComponent(PlatformKey.CALCSHAREHOLDERVALUE))
										.addActionListener(new ActionListener() {
											@Override
											public void actionPerformed(
													ActionEvent e) {
												DTOScenario scenario = (DTOScenario) controller
														.getModel();
												Map<String, Calculable[]> result = scenario
														.getDCFMethod()
														.calculate(scenario);
												JPanel panel = new JPanel();
												bhmf.setCharts(panel);
												for (IDeterministicResultAnalyser analyser : PluginManager
														.getInstance()
														.getServices(
																IDeterministicResultAnalyser.class)) {
													analyser.setResult(result, panel);
													break;
												}
											}
										});

								controller.loadAllToView();

							} catch (ViewException e) {
								e.printStackTrace();
							}

						} else if (selection instanceof DTOPeriod) {
							Services.startPeriodEditing((DTOPeriod) selection);
						}
					}

				}
			});
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
}
