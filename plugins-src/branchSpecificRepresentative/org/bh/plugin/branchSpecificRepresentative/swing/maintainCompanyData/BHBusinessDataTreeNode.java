package org.bh.plugin.branchSpecificRepresentative.swing.maintainCompanyData;


import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;

import org.bh.controller.InputController;
import org.bh.data.DTO;
import org.bh.data.DTOAccessException;
import org.bh.data.DTOBranch;
import org.bh.data.DTOBusinessData;
import org.bh.data.DTOCompany;
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
 * @author Simon Weber
 * @version 1.0, 16.02.2012
 *
 */
@SuppressWarnings("serial")
public class BHBusinessDataTreeNode extends DefaultMutableTreeNode{
	
	private InputController controller;
	private JScrollPane resultPane;
	private int dividerLocation;
	
	public BHBusinessDataTreeNode(Object dto){
		super(dto);
	}
	
	@Override
	public String toString(){
		if(this.getUserObject() instanceof DTOBusinessData){
			try{
				return "Businessdata";
			}catch(DTOAccessException e){
				return "";
			}
			
		}else if(this.getUserObject() instanceof DTOPeriod){
			try{
				return ((StringValue)((DTO<?>)userObject).get(DTOPeriod.Key.NAME)).getString();
			}catch(DTOAccessException e){
				return "";
			}
		}else if(this.getUserObject() instanceof DTOBranch){
			try{
				return ((StringValue)((DTO<?>)userObject).get(DTOBranch.Key.BRANCH_KEY_MAIN_CATEGORY)).getString()
				+  ((StringValue)((DTO<?>)userObject).get(DTOBranch.Key.BRANCH_KEY_MID_CATEGORY)).getString()
				+  ((StringValue)((DTO<?>)userObject).get(DTOBranch.Key.BRANCH_KEY_SUB_CATEGORY)).getString();
			}catch(DTOAccessException e){
				return "";
			}
			
		}else if(this.getUserObject() instanceof DTOCompany){
			try{
				return ((StringValue)((DTO<?>)userObject).get(DTOCompany.Key.NAME)).getString();
			}catch(DTOAccessException e){
				return "";
			}
		
	}
		
		return "Type not known - must be implemented in BHBusinessDataTreeNode";
	}
	
	
}

