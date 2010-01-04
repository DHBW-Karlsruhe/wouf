package org.bh.gui.swing;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import org.bh.data.DTOPeriod;
import org.bh.data.DTOProject;
import org.bh.data.DTOScenario;
import org.bh.platform.Services;

/**
 * The Tree visualizing the file contents.
 * 
 * <p>
 * 
 * This class provides the Tree, that shows the contents of a file.
 *
 * @author Tietze.Patrick
 * @author Thiele.Klaus
 * 
 * @version 0.2, 2009/12/30
 *
 */
public class BHTree extends JTree {
	
	/**
	 * icon for project nodes.
	 */
	public static ImageIcon PROJECT_ICON = Services.createImageIcon("/org/bh/images/tree/project.png", Services.getTranslator().translate("project"));
	
	/**
	 * icon for scenario nodes.
	 */
	public static ImageIcon SCENARIO_ICON = Services.createImageIcon("/org/bh/images/tree/scenario.png", Services.getTranslator().translate("scenario"));
	
	/**
	 * icon for period nodes.
	 */
	public static ImageIcon PERIOD_ICON = Services.createImageIcon("/org/bh/images/tree/period.png", Services.getTranslator().translate("period"));
	
	protected DefaultMutableTreeNode rootNode;
	protected DefaultTreeModel treeModel;

	DefaultTreeModel model;

	public BHTree() {

		// set settings
		this.setEditable(true);
		this.setRootVisible(false);
		this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		this.setShowsRootHandles(true);
		this.setCellRenderer(new BHTreeCellRenderer());
	}

	/**
	 * fixed minimum width.
	 */
	@Override
	public Dimension getMinimumSize() {
		return new Dimension(UIManager.getInt("BHTree.minimumWidth"), super.getMinimumSize().height);
	}

	public void setTreeModel(DefaultTreeModel treeModel) {
		this.setModel(treeModel);
		// TODO Find out, if reload is necessary...
	}
	
	/**
	 * Provides a <code>TreeCellRenderer</code> for the BHTree.
	 *
	 *
	 * @author Thiele.Klaus
	 * @version 1.0, 2009/12/29
	 *
	 */
	public class BHTreeCellRenderer implements TreeCellRenderer {
		 
		/**
		 * provides a <code>Component</code> for each node in the tree. Is called by Swing internally.
		 */
		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean selected, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {

			//Downcast just for determination of the type.
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			
			//Determine icon
			ImageIcon icon = null; 
						
			if (node.getUserObject() instanceof DTOProject) {
				icon = BHTree.PROJECT_ICON;
			}
			else if (node.getUserObject() instanceof DTOScenario) {
				icon = BHTree.SCENARIO_ICON;
			}
			else if (node.getUserObject() instanceof DTOPeriod) {
				icon = BHTree.PERIOD_ICON;
			}
			
			// return TreeCell
			JLabel treeCell = new JLabel(value.toString(), icon, SwingConstants.LEFT);
			treeCell.setPreferredSize(new Dimension((int) treeCell.getPreferredSize().getWidth(), UIManager.getInt("BHTree.nodeheight"))); 
			return treeCell;
		}
	}
	/**
	 * method to create a new ProjectNode
	 * 
	 * @param newProject
	 * 		DTOProject
	 * @param bhmf
	 * 		BusinessHoriozon MainFrame
	 * @return
	 * 		BHTreeNode
	 */
	public BHTreeNode addProjectNode(DTOProject newProject, BHMainFrame bhmf){
		BHTreeNode newProjectNode = new BHTreeNode(newProject);
		((DefaultTreeModel)bhmf.getBHTree().getModel()).insertNodeInto(
				newProjectNode, 
				(DefaultMutableTreeNode)bhmf.getBHTree().getModel().getRoot(), 
				((DefaultMutableTreeNode)bhmf.getBHTree().getModel().getRoot()).getChildCount()
		);
		return newProjectNode;
	}
	/**
	 * method to create a new ScenarioNode
	 * 
	 * @param newScenario
	 * 		DTOScenario
	 * @param bhmf
	 * 		BusinessHoriozon MainFrame
	 * @return
	 * 		BHTreeNode
	 */
	public BHTreeNode addScenarioNode(DTOScenario newScenario, BHMainFrame bhmf){
		BHTreeNode newScenarioNode = new BHTreeNode(newScenario);
		((DefaultTreeModel)bhmf.getBHTree().getModel()).insertNodeInto(
				newScenarioNode, 
				(BHTreeNode)(bhmf.getBHTree().getSelectionPath().getPathComponent(1)), 
				((BHTreeNode) bhmf.getBHTree().getSelectionPath().getPathComponent(1)).getChildCount()
		);
		return newScenarioNode;
	}
	public BHTreeNode duplicateScenarioNode(DTOScenario newScenario, BHMainFrame bhmf, BHTreeNode parentNode){
		BHTreeNode newScenarioNode = new BHTreeNode(newScenario);
		((DefaultTreeModel)bhmf.getBHTree().getModel()).insertNodeInto(newScenarioNode, parentNode, parentNode.getChildCount());
		return newScenarioNode;
	}

}
