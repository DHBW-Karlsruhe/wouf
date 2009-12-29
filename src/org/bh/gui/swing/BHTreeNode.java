package org.bh.gui.swing;

import javax.swing.tree.DefaultMutableTreeNode;
import org.bh.data.DTO;
import org.bh.data.DTOProject;
import org.bh.data.types.StringValue;
/**
 * 
 * Special TreeNode for use in Business Horizon.
 * Necessary because of non existing toString() of DTOs. 
 * This class makes it possible that Nodes' (= DTOs) names can be changed in the tree.
 *
 * @author Alexander Schmalzhaf
 * @version 1.0, 30.12.2009
 *
 */
public class BHTreeNode extends DefaultMutableTreeNode{
	public BHTreeNode(Object ob){
		super(ob);
	}
	
	@Override
	public String toString(){
		return ((StringValue)((DTO<?>)userObject).get(DTOProject.Key.NAME)).getString();
	}
}
