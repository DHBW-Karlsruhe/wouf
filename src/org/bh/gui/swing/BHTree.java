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

/**
 * The Tree visualizing the file contents.
 * 
 * <p>
 * 
 * This class provides the Tree, that shows the contents of a file.
 *
 * @author unknown
 * @author Thiele.Klaus
 * 
 * @version 0.2, 2009/12/30
 *
 */
public class BHTree extends JTree {
	
	/**
	 * icon for project nodes.
	 */
	public static ImageIcon PROJECT_ICON = Services.createImageIcon("/org/bh/images/tree/project.jpg", Services.getTranslator().translate("project"));
	
	/**
	 * icon for scenario nodes.
	 */
	public static ImageIcon SCENARIO_ICON = Services.createImageIcon("/org/bh/images/tree/scenario.jpg", Services.getTranslator().translate("scenario"));
	
	/**
	 * icon for period nodes.
	 */
	public static ImageIcon PERIOD_ICON = Services.createImageIcon("/org/bh/images/tree/period.jpg", Services.getTranslator().translate("period"));
	
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
			return new JLabel(value.toString(), icon, SwingConstants.LEFT);
		}
	}

}
