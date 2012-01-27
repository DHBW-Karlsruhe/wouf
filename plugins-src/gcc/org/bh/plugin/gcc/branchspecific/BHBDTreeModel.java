package org.bh.plugin.gcc.branchspecific;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.bh.data.DTO;
import org.bh.data.DTOBranch;
import org.bh.data.DTOCompany;
import org.bh.data.DTOPeriod;
import org.bh.data.types.StringValue;

/**
 * <short_description>
 *
 * model for the tree to show branch specific representatives
 * <detailed_description>
 *
 * @author simon
 * @version 1.0, 18.01.2012
 *
 */
public class BHBDTreeModel extends DefaultTreeModel {



	public BHBDTreeModel(TreeNode root) {
		super(root);
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		BHBusinessDataTreeNode activeNode = (BHBusinessDataTreeNode) path.getLastPathComponent();
		DTO<?> tempDTO = (DTO<?>) activeNode.getUserObject();
		
		Object key = null;
		if (tempDTO instanceof DTOCompany) {
			key = DTOCompany.Key.NAME;
		} else if (tempDTO instanceof DTOBranch) {

	
		} else if (tempDTO instanceof DTOPeriod) {
			key = DTOPeriod.Key.NAME;
		}

		if (key != null) {
			tempDTO.put(key, new StringValue(newValue.toString()));
			if (activeNode.getController() != null) {
				activeNode.getController().loadToView(key);
			}
		}
	}
}