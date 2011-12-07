/*******************************************************************************
 * Copyright 2011: Matthias Beste, Hannes Bischoff, Lisa Doerner, Victor Guettler, Markus Hattenbach, Tim Herzenstiel, Günter Hesse, Jochen Hülß, Daniel Krauth, Lukas Lochner, Mark Maltring, Sven Mayer, Benedikt Nees, Alexandre Pereira, Patrick Pfaff, Yannick Rödl, Denis Roster, Sebastian Schumacher, Norman Vogel, Simon Weber 
 *
 * Copyright 2010: Anna Aichinger, Damian Berle, Patrick Dahl, Lisa Engelmann, Patrick Groß, Irene Ihl, Timo Klein, Alena Lang, Miriam Leuthold, Lukas Maciolek, Patrick Maisel, Vito Masiello, Moritz Olf, Ruben Reichle, Alexander Rupp, Daniel Schäfer, Simon Waldraff, Matthias Wurdig, Andreas Wußler
 *
 * Copyright 2009: Manuel Bross, Simon Drees, Marco Hammel, Patrick Heinz, Marcel Hockenberger, Marcus Katzor, Edgar Kauz, Anton Kharitonov, Sarah Kuhn, Michael Löckelt, Heiko Metzger, Jacqueline Missikewitz, Marcel Mrose, Steffen Nees, Alexander Roth, Sebastian Scharfenberger, Carsten Scheunemann, Dave Schikora, Alexander Schmalzhaf, Florian Schultze, Klaus Thiele, Patrick Tietze, Robert Vollmer, Norman Weisenburger, Lars Zuckschwerdt
 *
 * Copyright 2008: Camil Bartetzko, Tobias Bierer, Lukas Bretschneider, Johannes Gilbert, Daniel Huser, Christopher Kurschat, Dominik Pfauntsch, Sandra Rath, Daniel Weber
 *
 * This program is free software: you can redistribute it and/or modify it un-der the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT-NESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package org.bh.platform;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;
import org.bh.controller.IDataExchangeController;
import org.bh.data.DTOPeriod;
import org.bh.data.DTOProject;
import org.bh.data.DTOScenario;
import org.bh.data.types.StringValue;
import org.bh.gui.IBHAction;
import org.bh.gui.swing.BHAboutBox;
import org.bh.gui.swing.BHContent;
import org.bh.gui.swing.BHMainFrame;
import org.bh.gui.swing.BHOptionDialog;
import org.bh.gui.swing.BHOptionPane;
import org.bh.gui.swing.BHStatusBar;
import org.bh.gui.swing.comp.BHComboBox;
import org.bh.gui.swing.forms.BHHelpDebugDialog;
import org.bh.gui.swing.importexport.BHDataExchangeDialog;
import org.bh.gui.swing.tree.BHTreeNode;
import org.bh.platform.PlatformController.BHTreeModel;
import org.bh.platform.i18n.BHTranslator;
import org.bh.platform.i18n.ITranslator;

/**
 * The PlatformActionListener handles all actions that are fired by a button
 * etc. of the platform.
 */
class PlatformActionListener implements ActionListener {

	private static final Logger log = Logger
			.getLogger(PlatformActionListener.class);

	BHMainFrame bhmf;
	ProjectRepositoryManager projectRepoManager;
	PlatformController pC;
	ITranslator trans = BHTranslator.getInstance();

	IDataExchangeController dataExchangeCntrl;

	public PlatformActionListener(BHMainFrame bhmf,
			ProjectRepositoryManager projectRepoManager,
			PlatformController platformController) {
		this.bhmf = bhmf;
		this.projectRepoManager = projectRepoManager;
		this.pC = platformController;
	}

	@Override
	public void actionPerformed(ActionEvent aEvent) {

		// get actionKey of fired action
		PlatformKey actionKey = ((IBHAction) aEvent.getSource())
				.getPlatformKey();

		// do right action...
		switch (actionKey) {

		/*
		 * Clear current workspace
		 * 
		 * @author Michael Löckelt
		 */
		case FILENEW:
			log.debug("handling FILENEW event");
			this.fileNew();
			break;

		/*
		 * Open a workspace
		 * 
		 * @author Michael Löckelt
		 */
		case FILEOPEN:
			log.debug("handling FILEOPEN event");
			this.fileOpen();
			break;

		/*
		 * Save the whole workspace using the already defined filepath or ask
		 * for new path
		 * 
		 * @author Michael Löckelt
		 */
		case FILESAVE:
			log.debug("handling FILESAVE event");
			Services.firePlatformEvent(new PlatformEvent(
					PlatformActionListener.class, PlatformEvent.Type.SAVE));
			break;

		/*
		 * Save the whole workspace - incl. filepath save dialog
		 * 
		 * @author Michael Löckelt
		 */
		case FILESAVEAS:
			log.debug("handling FILESAVEAS event");
			Services.firePlatformEvent(new PlatformEvent(
					PlatformActionListener.class, PlatformEvent.Type.SAVEAS));
			break;

		/*
		 * Clear workspace - same like filenew
		 * 
		 * @author Michael Löckelt
		 */
		case FILECLOSE:
			log.debug("handling FILECLOSE event");
			this.fileNew();
			break;

		case FILEQUIT:
			log.debug("handling FILEQUIT event");
			bhmf.dispose();
			break;

		case PROJECTCREATE:
			this.createProject();
			break;

		case PROJECTDUPLICATE:
			this.duplicateProject();
			break;

		// TODO Katzor.Marcus
		case PROJECTIMPORT:

			BHDataExchangeDialog importDialog = new BHDataExchangeDialog(bhmf,
					true);
			importDialog.setAction(IImportExport.IMP_PROJECT);
			importDialog.setDescription(BHTranslator.getInstance().translate(
					"DXMLImportDescription"));
			importDialog.setVisible(true);
			break;
		case PROJECTEXPORT:
			this.projectExport();
			break;
		case PROJECTREMOVE:
		    	this.removeElement(PlatformKey.PROJECTREMOVE);
			break;

		case SCENARIOCREATE:
			this.createScenario();
			break;

		case SCENARIODUPLICATE:
			this.duplicateScenario();
			break;

		case SCENARIOREMOVE:
		    	this.removeElement(PlatformKey.SCENARIOREMOVE);
			break;

		case PERIODCREATE:
			this.createPeriod();
			break;

		case PERIODDUPLICATE:
			this.duplicatePeriod();
			break;

		case PERIODREMOVE:
			this.removeElement(PlatformKey.PERIODREMOVE);
			break;

		case OPTIONSCHANGE:
			new BHOptionDialog(bhmf, false);
			break;

		case HELPUSERHELP:

			openUserHelp("user");
			break;

		case HELPMATHHELP:

			openUserHelp("mathe");
			break;

		case HELPINFO:
			new BHAboutBox(bhmf);
			break;
		
		case HELPDEBUG:
			new BHHelpDebugDialog(bhmf, false);
			break;

		case TOOLBARNEW:
			log.debug("handling FILENEW event");
			this.fileNew();
			break;

		case TOOLBAROPEN:
			log.debug("handling TOOLBAROPEN event");
			this.fileOpen();
			break;

		case TOOLBARSAVE:
			log.debug("handling TOOLBARSAVE event");
			Services.firePlatformEvent(new PlatformEvent(
					PlatformActionListener.class, PlatformEvent.Type.SAVE));
			break;

		case TOOLBARADDPRO:
			this.createProject();
			break;

		case TOOLBARADDS:
			this.createScenario();
			break;

		case TOOLBARADDPER:
			this.createPeriod();
			break;

		case TOOLBARREMOVE:
			this.removeElement();
			break;
			
		case POPUPREMOVE:
			this.removeElement();
			break;
		case POPUPADD:
			this.popupAdd();
			break;
		case POPUPDUPLICATE:
			this.popupDuplicate();
			break;
		case POPUPEXPORT:
			this.popupExport();
			break;
		}
	}

	/*
	 * new file
	 * 
	 * @author Loeckelt.Michael
	 */
	private void fileNew() {
		if (ProjectRepositoryManager.isChanged()) {

			int i = BHOptionPane.showConfirmDialog(bhmf, Services
					.getTranslator().translate("Psave", ITranslator.LONG),
					Services.getTranslator().translate("Psave"),
					JOptionPane.YES_NO_CANCEL_OPTION);

			if (i == JOptionPane.YES_OPTION || i == JOptionPane.NO_OPTION) {
				if (i == JOptionPane.YES_OPTION)
					Services.firePlatformEvent(new PlatformEvent(
							BHMainFrame.class, PlatformEvent.Type.SAVE));

				if (i == JOptionPane.NO_OPTION)
					Logger
							.getLogger(getClass())
							.debug(
									"Existing changes but no save wish - clear project list");

				projectRepoManager.clearProjectList();
				pC.setupTree(bhmf, projectRepoManager);
				PlatformController.preferences.remove("path");

				ProjectRepositoryManager.setChanged(false);
				bhmf.resetTitle();

			} else if (i == JOptionPane.CANCEL_OPTION) {

			}

		} else {
			Logger.getLogger(getClass()).debug(
					"No changes - clear project list");
			projectRepoManager.clearProjectList();
			pC.setupTree(bhmf, projectRepoManager);
			PlatformController.preferences.remove("path");

			ProjectRepositoryManager.setChanged(false);
			bhmf.resetTitle();
		}
		
		// fix ticket #85
		PlatformController.getInstance().getMainFrame().getBHTree().setSelectionPath(null);
		bhmf.setContentForm(new BHContent());
	}

	/*
	 * new file
	 * 
	 * @author Loeckelt.Michael
	 */
	protected void fileOpen() {
		// create a open-dialog
		int returnVal = bhmf.getChooser().showOpenDialog(bhmf);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			log.debug("You chose to open this file: "
					+ bhmf.getChooser().getSelectedFile().getName());

			// open already provided file
			PlatformController.platformPersistenceManager.openFile(bhmf
					.getChooser().getSelectedFile());

			// rebuild Tree
			pC.setupTree(bhmf, projectRepoManager);

			bhmf.getBHTree().expandAll();
			
			// fix ticket #85
			PlatformController.getInstance().getMainFrame().getBHTree().setSelectionPath(null);
			bhmf.setContentForm(new BHContent());
			
		}
	}
	protected void popupExport(){
		TreePath currentSelection = bhmf.getBHTree().getSelectionPath();
		
		if(currentSelection != null){
			BHTreeNode currentNode = (BHTreeNode) bhmf.getBHTree()
					.getSelectionPath().getLastPathComponent();
			//add a new node to data model...
			if(currentNode.getUserObject() instanceof DTOProject){
				this.projectExport();
			}else {
				
			}
		}
	}
	protected void popupDuplicate(){
		TreePath currentSelection = bhmf.getBHTree().getSelectionPath();
		//is a node selected?
		if(currentSelection != null){
			BHTreeNode currentNode = (BHTreeNode) bhmf.getBHTree()
					.getSelectionPath().getLastPathComponent();
			//add a new node to data model...
			if(currentNode.getUserObject() instanceof DTOProject){
				this.duplicateProject();
			}else if(currentNode.getUserObject() instanceof DTOScenario){
				this.duplicateScenario();
			}else if(currentNode.getUserObject() instanceof DTOPeriod){
				this.duplicatePeriod();
			}
		}
	}
	protected void popupAdd(){
		TreePath currentSelection = bhmf.getBHTree().getSelectionPath();
		//is a node selected?
		if(currentSelection != null){
			BHTreeNode currentNode = (BHTreeNode) bhmf.getBHTree()
					.getSelectionPath().getLastPathComponent();
			//add a new node to data model...
			if(currentNode.getUserObject() instanceof DTOProject){
				this.createScenario();
			}else if(currentNode.getUserObject() instanceof DTOScenario){
				this.createPeriod();
			}
		}else {
			this.createProject();
		}
	}
	
	protected void removeElement(){
	    if(((DefaultMutableTreeNode) bhmf.getBHTree().getSelectionPath().getLastPathComponent()).getUserObject() instanceof DTOProject){
		this.removeElement(PlatformKey.PROJECTREMOVE);
	    } else if(((DefaultMutableTreeNode) bhmf.getBHTree().getSelectionPath().getLastPathComponent()).getUserObject() instanceof DTOScenario){
		this.removeElement(PlatformKey.SCENARIOREMOVE);
	    } else if(((DefaultMutableTreeNode) bhmf.getBHTree().getSelectionPath().getLastPathComponent()).getUserObject() instanceof DTOPeriod){
		this.removeElement(PlatformKey.PERIODREMOVE);
	    } 
	}
	
	protected void removeElement(PlatformKey key){
	    int choice;
	    switch (key){
	    case PROJECTREMOVE:
		choice = BHOptionPane.showConfirmDialog(bhmf, Services
			.getTranslator().translate("Pproject_delete"), Services
			.getTranslator().translate("Pdelete"),
			JOptionPane.YES_NO_OPTION);
		
		if (choice == JOptionPane.YES_OPTION) {
			// find out current selected node
			BHTreeNode currentNode = (BHTreeNode) bhmf.getBHTree()
				.getSelectionPath().getPathComponent(1);

			projectRepoManager.removeProject((DTOProject) currentNode.getUserObject());

			// ... and from GUI and select other node or empty screen
			TreePath tp = new TreePath(currentNode.getPreviousNode()
					.getPath());
			bhmf.getBHTree().setSelectionPath(tp);
			
			bhmf.getBHTree().setSelectionPath(null);

			((BHTreeModel) bhmf.getBHTree().getModel())
					.removeNodeFromParent(currentNode);
		    }
		break;
		
	    case SCENARIOREMOVE:
		choice = BHOptionPane.showConfirmDialog(bhmf, Services
			.getTranslator().translate("Pscenario_delete"), Services
			.getTranslator().translate("Pdelete"),
			JOptionPane.YES_NO_OPTION);
		
		if (choice == JOptionPane.YES_OPTION) {
			// find out current selected node
			BHTreeNode currentNode = (BHTreeNode) bhmf.getBHTree()
					.getSelectionPath().getPathComponent(2);

			((DTOProject) ((BHTreeNode) currentNode.getParent())
						.getUserObject())
						.removeChild((DTOScenario) currentNode
								.getUserObject());

			// ... and from GUI and select other node or empty screen
			TreePath tp = new TreePath(currentNode.getPreviousNode()
					.getPath());
			bhmf.getBHTree().setSelectionPath(tp);
			
			((BHTreeModel) bhmf.getBHTree().getModel())
					.removeNodeFromParent(currentNode);
		    }
		break;
		
	    case PERIODREMOVE:
		choice = BHOptionPane.showConfirmDialog(bhmf, Services
			.getTranslator().translate("Pperiod_delete"), Services
			.getTranslator().translate("Pdelete"),
			JOptionPane.YES_NO_OPTION);
		
		if (choice == JOptionPane.YES_OPTION) {
			// find out current selected node
			BHTreeNode currentNode = (BHTreeNode) bhmf.getBHTree()
					.getSelectionPath().getPathComponent(3);

			((DTOScenario) ((BHTreeNode) currentNode.getParent())
						.getUserObject())
						.removeChild((DTOPeriod) currentNode
								.getUserObject());

			// ... and from GUI and select other node or empty screen
			TreePath tp = new TreePath(currentNode.getPreviousNode()
					.getPath());
			bhmf.getBHTree().setSelectionPath(tp);
			
			((BHTreeModel) bhmf.getBHTree().getModel())
					.removeNodeFromParent(currentNode);
		    }
		break;
	    }
		
	}   

	protected void handleRemove(int choice){
	    
	    if (choice == JOptionPane.YES_OPTION) {
		// find out current selected node
		BHTreeNode currentNode = (BHTreeNode) bhmf.getBHTree()
				.getSelectionPath().getLastPathComponent();

		// remove node from data model...
		if (currentNode.getUserObject() instanceof DTOProject) {
			projectRepoManager.removeProject((DTOProject) currentNode
					.getUserObject());

		} else if (currentNode.getUserObject() instanceof DTOScenario) {
			((DTOProject) ((BHTreeNode) currentNode.getParent())
					.getUserObject())
					.removeChild((DTOScenario) currentNode
							.getUserObject());

		} else if (currentNode.getUserObject() instanceof DTOPeriod) {
			((DTOScenario) ((BHTreeNode) currentNode.getParent())
					.getUserObject())
					.removeChild((DTOPeriod) currentNode
							.getUserObject());
		}

		// ... and from GUI and select other node or empty screen
		TreePath tp = new TreePath(currentNode.getPreviousNode()
				.getPath());
		bhmf.getBHTree().setSelectionPath(tp);
		if (bhmf.getBHTree().getSelectionPath().getPathCount() == 1)
			bhmf.getBHTree().setSelectionPath(null);

		((BHTreeModel) bhmf.getBHTree().getModel())
				.removeNodeFromParent(currentNode);
	    }
	}
	

	protected void createProject() {
		// Create new project
		DTOProject newProject = new DTOProject();
		newProject.put(DTOProject.Key.NAME, new StringValue(BHTranslator.getInstance().translate("project_new")));
		// add it to DTO-Repository and Tree
		PlatformController.getInstance().addProject(newProject);

	}

	protected void createScenario() {
		// If a path is selected...
		if (bhmf.getBHTree().getSelectionPath() != null) {
			// check kind of scenario: deterministic or stochastic?
			ArrayList<BHComboBox.Item> itemsList = new ArrayList<BHComboBox.Item>();
			itemsList.add(new BHComboBox.Item("deterministic", new StringValue(
					BHTranslator.getInstance().translate("deterministic"))));
			itemsList.add(new BHComboBox.Item("stochastic", new StringValue(
				BHTranslator.getInstance().translate("stochastic"))));
			BHComboBox.Item res = (BHComboBox.Item) JOptionPane
					.showInputDialog(bhmf,
							trans.translate("choose_scenario_type", BHTranslator.LONG),
						    trans.translate("choose_scenario_type"),
							JOptionPane.QUESTION_MESSAGE, null, itemsList
									.toArray(), null);

			if (res == null)
				return;

			// ...create new scenario
			DTOScenario newScenario = new DTOScenario(res.getKey()
					.equalsIgnoreCase("deterministic"));
			newScenario.put(DTOScenario.Key.NAME, new StringValue(
				BHTranslator.getInstance().translate("scenario_new")));

			// ...set Basis (IDENTIFIER) of scenario -> naming of periods
			newScenario.put(DTOScenario.Key.IDENTIFIER, new StringValue(""
					+ Calendar.getInstance().get(Calendar.YEAR)));

			// ...add it to DTO-Repository
			((DTOProject) ((BHTreeNode) bhmf.getBHTree().getSelectionPath()
					.getPathComponent(1)).getUserObject())
					.addChild(newScenario);

			// ...and insert it into GUI-Tree
			BHTreeNode newScenarioNode = bhmf.getBHTree()
					.addScenarioAtCurrentPos(newScenario);

			// last steps: unfold tree to new element, set focus and start
			// editing
			bhmf.getBHTree().scrollPathToVisible(
					new TreePath(newScenarioNode.getPath()));
			bhmf.getBHTree().setSelectionPath(new TreePath(newScenarioNode.getPath()));
		} else {
			BHStatusBar.getInstance().setHint(
					BHTranslator.getInstance().translate("EisSelectProject"),
					true);
		}

	}

	protected void createPeriod() {
		// If a scenario or a period is selected...
		if (bhmf.getBHTree().getSelectionPath() != null
				&& bhmf.getBHTree().getSelectionPath().getPathCount() > 2) {

			DTOScenario scenario = ((DTOScenario) ((BHTreeNode) bhmf
					.getBHTree().getSelectionPath().getPathComponent(2))
					.getUserObject());

			// ...create new period
			DTOPeriod newPeriod = new DTOPeriod();

			// ...set name of period
			String periodName = "";
			if (scenario.getChildren().isEmpty()) {
				try {
					periodName = BHTranslator.getInstance().translate("period")
							+ " "
							+ scenario.get(DTOScenario.Key.IDENTIFIER)
									.toString();
				} catch (Exception e) {
					// Do nothing;
				}
			} else {
				//get reference period to orient index
				//-> depends on sort of scenario
				DTOPeriod refPeriod;
				int periodDifference;
				if(scenario.isDeterministic()){
					refPeriod = scenario.getLastChild();
					periodDifference = 1;
				}else{
					refPeriod = scenario.getFirstChild();
					periodDifference = -1;
				}
				
				
				try {
					// get number of last Period and add 1.
					periodName = ""
							+ (Integer.parseInt(((StringValue) refPeriod
									.get(DTOPeriod.Key.NAME)).getString()) + periodDifference);
				} catch (Exception e) {
					try {
						// get number and Text of last Period and add 1.
						String lastPeriodName = ((StringValue) refPeriod
								.get(DTOPeriod.Key.NAME)).getString();
						int tempNum = Integer.parseInt(lastPeriodName
								.substring(getNumPos(lastPeriodName)));

						periodName = lastPeriodName.substring(0,
								getNumPos(lastPeriodName))
								+ (tempNum + periodDifference);

					} catch (Exception e1) {
						periodName = BHTranslator.getInstance().translate("period_new");
					}
				}
			}
			newPeriod.put(DTOPeriod.Key.NAME, new StringValue(periodName));

			// ...add it to DTO-Repository
			DTOScenario scenarioDto = (DTOScenario) ((BHTreeNode) bhmf.getBHTree().getSelectionPath()
				.getPathComponent(2)).getUserObject();
			scenarioDto.addChild(newPeriod,scenarioDto.isDeterministic());

			// ...and insert it into GUI-Tree
			BHTreeNode newPeriodNode = bhmf.getBHTree().addPeriodAtCurrentPos(
					newPeriod);

			// last steps: unfold tree to new element, set focus 
			bhmf.getBHTree().scrollPathToVisible(
					new TreePath(newPeriodNode.getPath()));
			bhmf.getBHTree().setSelectionPath(new TreePath(newPeriodNode.getPath()));
		}
	}
	
	private void duplicateProject() {
		TreePath currentDuplicateProjectSelection = bhmf.getBHTree()
				.getSelectionPath();
		if (currentDuplicateProjectSelection != null) {
			// Access to selected project
			BHTreeNode duplicateProjectNode = (BHTreeNode) bhmf.getBHTree()
					.getSelectionPath().getLastPathComponent();

			// zu kopierendes Project in eigene Variable
			DTOProject duplicateProject = (DTOProject) duplicateProjectNode
					.getUserObject();

			// neues DTOProject mit Referenz auf den Klon

			DTOProject newProject = (DTOProject) duplicateProject.clone();

			// new name after duplication
			String duplicateProjectName = bhmf.getBHTree().getSelectionPath()
					.getPathComponent(1).toString();
			newProject.put(DTOProject.Key.NAME, new StringValue(
					duplicateProjectName + " (2)"));
			
			//add into Tree and Repository (directly possible for Projects)
			PlatformController.getInstance().addProject(newProject);


		} else {
			BHStatusBar.getInstance().setHint(
					BHTranslator.getInstance().translate("EisSelectProject"),
					true);
		}
	}

	private void duplicateScenario() {
		TreePath currentDuplicateScenarioSelection = bhmf.getBHTree()
				.getSelectionPath();
		if (currentDuplicateScenarioSelection != null) {
			// Access to selected scenario
			BHTreeNode duplicateScenarioNode = (BHTreeNode) bhmf.getBHTree()
					.getSelectionPath().getLastPathComponent();

			// zu kopierendes Project in eigene Variable
			DTOScenario duplicateScenario = (DTOScenario) duplicateScenarioNode
					.getUserObject();

			// neues DTOProject mit Referenz auf den Klon
			DTOScenario newScenario = (DTOScenario) duplicateScenario.clone();

			// new name after duplication
			String duplicateScenarioName = bhmf.getBHTree().getSelectionPath()
					.getPathComponent(2).toString();
			newScenario.put(DTOScenario.Key.NAME, new StringValue(
					duplicateScenarioName + " (2)"));
			
			//add new Scenario into Tree...
			BHTreeNode node = bhmf.getBHTree().addScenarioAtCurrentPos(newScenario);
			
			//...and into Project DTO
			((DTOProject)((BHTreeNode)node.getParent()).getUserObject()).addChild(newScenario);
			
		} else {
			BHStatusBar.getInstance().setHint(
					BHTranslator.getInstance().translate("EisSelectScenario"),
					true);
		}
	}

	private void duplicatePeriod() {
		
		TreePath currentDuplicatePeriodSelection = bhmf.getBHTree()
				.getSelectionPath();
		if (currentDuplicatePeriodSelection != null) {
			// Access to selected period
			BHTreeNode duplicatePeriodNode = (BHTreeNode) bhmf.getBHTree()
					.getSelectionPath().getLastPathComponent();

			// copy the period to a temp period
			DTOPeriod duplicatePeriod = (DTOPeriod) duplicatePeriodNode
					.getUserObject();

			String duplicatePeriodName = bhmf.getBHTree().getSelectionPath()
					.getPathComponent(3).toString();

			// new DTOPeriod object with reference to the clone
			DTOPeriod newPeriod = (DTOPeriod) duplicatePeriod.clone();

			newPeriod.put(DTOPeriod.Key.NAME, new StringValue(
					duplicatePeriodName + " (2)"));
			
			//add Period into Tree...
			BHTreeNode newNode = bhmf.getBHTree().addPeriodAtCurrentPos(newPeriod);
			
			//...and into Scenario DTO
			((DTOScenario)((BHTreeNode)newNode.getParent()).getUserObject()).addChild(newPeriod);
			
		} else {
			BHStatusBar.getInstance().setHint(
					BHTranslator.getInstance().translate("EisSelectScenario"),
					true);
		}
	}
	protected void projectExport(){
		// Get selected node
		if (bhmf.getBHTree().getSelectionPath() != null) {
			BHTreeNode selectedNode = (BHTreeNode) bhmf.getBHTree()
					.getSelectionPath().getPathComponent(1);
			// Get DTOProject
			if (selectedNode.getUserObject() instanceof DTOProject) {

				// Create data exchange dialog
				BHDataExchangeDialog dialog = new BHDataExchangeDialog(
						bhmf, true);
				dialog.setAction(IImportExport.EXP_PROJECT);
				dialog.setModel( selectedNode.getUserObject());
				dialog.setDescription(BHTranslator.getInstance().translate(
						"DExpFileFormatSel"));
				dialog.setVisible(true);
			} 
		} 
		else
		{
			BHStatusBar.getInstance().setHint(BHTranslator.getInstance()
					.translate("DXMLProjectExportNoSelection"));			
		}
	}
	protected void openUserHelp(String help) {
		log.debug("HELPUSERHELP gefeuert");
		JFrame frame = new JFrame();
		frame.setTitle(BHTranslator.getInstance().translate("MuserHelpDialog"));
		frame.setIconImage(bhmf.getIconImage());
		frame.setMinimumSize(new Dimension(950,700));
		frame.getContentPane().add(new BHHelpSystem(help));
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/**
	 * Method to get Position on numeric value in a string Necessary for naming
	 * of Periods
	 * 
	 * @param s
	 * @return
	 */
	private int getNumPos(String s) {
		int[] numbers = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

		int start = s.length();

		for (int i : numbers) {
			if (s.indexOf("" + i) < start && s.indexOf("" + i) > -1)
				start = s.indexOf("" + i);
		}
		return start;

	}

}
