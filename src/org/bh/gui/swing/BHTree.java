package org.bh.gui.swing;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import org.bh.data.DTOPeriod;
import org.bh.data.DTOProject;
import org.bh.data.DTOScenario;
import org.bh.platform.Services;

public class BHTree extends JTree {
	public static ImageIcon PROJECT_ICON = Services.createImageIcon("/org/bh/images/tree/project.jpg", Services.getTranslator().translate("project"));
	public static ImageIcon SCENARIO_ICON = Services.createImageIcon("/org/bh/images/tree/scenario.jpg", Services.getTranslator().translate("scenario"));
	public static ImageIcon PERIOD_ICON = Services.createImageIcon("/org/bh/images/tree/period.jpg", Services.getTranslator().translate("period"));
	
	protected DefaultMutableTreeNode rootNode;
	protected DefaultTreeModel treeModel;

	DefaultTreeModel model;

	public BHTree() {

		// set settings
		this.setEditable(true);
		this.setRootVisible(false);
		this.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		this.setShowsRootHandles(true);
		this.setCellRenderer(new BHTreeCellRenderer());
	}

	public void setTreeModel(DefaultTreeModel treeModel) {
		this.setModel(treeModel);
		// TODO Find out, if reload is necessary...
	}
	/**
	 * Provides a <code>TreeCellRenderer</code> for the BHTree.
	 *
	 *
	 * @author klaus
	 * @version 1.0, Dec 30, 2009
	 *
	 */
	public class BHTreeCellRenderer implements TreeCellRenderer {
		
		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean selected, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {

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
			else {
				icon = null;
			}
			
			// return TreeCell
			return new JLabel(value.toString(), icon, SwingConstants.LEFT);
		}
	}

}
