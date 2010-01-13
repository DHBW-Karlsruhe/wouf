package org.bh.gui.swing;

import javax.swing.tree.DefaultMutableTreeNode;

import org.bh.controller.InputController;
import org.bh.data.DTO;
import org.bh.data.DTOPeriod;
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
	private InputController controller;
	
	
	public BHTreeNode(Object ob){
		super(ob);
	}
	
	@Override
	public String toString(){
		if(this.getUserObject() instanceof DTOProject){
			return ((StringValue)((DTO<?>)userObject).get(DTOProject.Key.NAME)).getString();
		}else if(this.getUserObject() instanceof DTOScenario){
			return ((StringValue)((DTO<?>)userObject).get(DTOScenario.Key.NAME)).getString();
		}else if(this.getUserObject() instanceof DTOPeriod){
			return ((StringValue)((DTO<?>)userObject).get(DTOPeriod.Key.NAME)).getString();
		}
		
		return "Type not known - must be implemented in BHTreeNode";
	}
	
	public void setView(View view){
		this.view = view;	
	}
	
	public View getView(){
		return this.view;
	}
	
	public void setController(InputController controller){
		this.controller = controller;
	}
	
	public InputController getController(){
		return controller;
	}
	
	
}
