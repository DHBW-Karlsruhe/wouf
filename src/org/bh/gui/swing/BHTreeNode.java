package org.bh.gui.swing;

import javax.swing.tree.DefaultMutableTreeNode;
import org.bh.data.DTO;
import org.bh.data.DTOProject;
import org.bh.data.DTOScenario;
import org.bh.data.types.StringValue;
import org.bh.gui.View;
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
	
	private View view;
	
	public BHTreeNode(Object ob){
		super(ob);
	}
	
	@Override
	public String toString(){
		if(this.getUserObject() instanceof DTOProject){
			return ((StringValue)((DTO<?>)userObject).get(DTOProject.Key.NAME)).getString();
		}else if(this.getUserObject() instanceof DTOScenario){
			return ((StringValue)((DTO<?>)userObject).get(DTOScenario.Key.NAME)).getString();
		}
		return "";
	}
	
	
	
}
