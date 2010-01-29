package org.bh.gui.swing.tree;

import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;

import org.bh.controller.InputController;
import org.bh.data.DTO;
import org.bh.data.DTOAccessException;
import org.bh.data.DTOPeriod;
import org.bh.data.DTOProject;
import org.bh.data.DTOScenario;
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
@SuppressWarnings("serial")
public class BHTreeNode extends DefaultMutableTreeNode{
	
	private InputController controller;
	private JScrollPane resultPane;
	private int dividerLocation;
	
	public BHTreeNode(Object ob){
		super(ob);
	}
	
	@Override
	public String toString(){
		if(this.getUserObject() instanceof DTOProject){
			try{
				return ((StringValue)((DTO<?>)userObject).get(DTOProject.Key.NAME)).getString();
			}catch(DTOAccessException e){
				return "";
			}
			
		}else if(this.getUserObject() instanceof DTOScenario){
			try{
				return ((StringValue)((DTO<?>)userObject).get(DTOScenario.Key.NAME)).getString();
			}catch(DTOAccessException e){
				return "";
			}
			
		}else if(this.getUserObject() instanceof DTOPeriod){
			try{
				return ((StringValue)((DTO<?>)userObject).get(DTOPeriod.Key.NAME)).getString();
			}catch(DTOAccessException e){
				return "";
			}
		}
		
		return "Type not known - must be implemented in BHTreeNode";
	}
	
	public void setResultPane(JScrollPane resultPane){
		this.resultPane = resultPane;
	}
	
	public JScrollPane getResultPane(){
		return this.resultPane;
	}
	
	public void setController(InputController controller){
		this.controller = controller;
	}
	
	public InputController getController(){
		return controller;
	}
	
	public void setDividerLocation(int dividerLocation){
		this.dividerLocation = dividerLocation;
	}
	
	public int getDividerLocation(){
		return this.dividerLocation;
	}
}
