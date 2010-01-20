package org.bh.gui.swing;
import java.awt.Point;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.bh.data.DTOPeriod;
import org.bh.data.DTOProject;
import org.bh.data.DTOScenario;
 

//TODO Schmalzhaf.Alexander
/**
 * 
 * <short_description>
 *
 * <p>
 * <detailed_description>
 *
 * @author Schmalzhaf.Alexander
 * @version 1.0, 18.01.2010
 *
 */
public class BHTreeTransferHandler extends AbstractTreeTransferHandler {
 
	public BHTreeTransferHandler(BHTree tree, int action) {
		super(tree, action, false);
	}
 
	@Override
	public boolean canPerformAction(BHTree target, DefaultMutableTreeNode draggedNode, int action, Point location) {
		
		//check if everything's allright with BHTreeNodes...
		if(draggedNode instanceof BHTreeNode){
			
			//wrap node (to get UserObjects)
			BHTreeNode draggedBHNode = (BHTreeNode) draggedNode;
			
			//get target and set selection if possible
			TreePath targetPath = target.getPathForLocation(location.x, location.y);
			if (targetPath == null) {
				return false;
			}
			
			
			
			if(draggedBHNode.getUserObject() instanceof DTOPeriod){
				
				/*
				 * Allow to Move Period to other Scenario
				 */
				if(action == DnDConstants.ACTION_MOVE){
					if(((BHTreeNode)targetPath.getLastPathComponent()).getUserObject() instanceof DTOScenario){
						target.setSelectionPath(targetPath);
						return true;
					}
				}
				
			} else if(draggedBHNode.getUserObject() instanceof DTOScenario){
				
				/*
				 * Allow to Move Scenario to other Project
				 */
				if(action == DnDConstants.ACTION_MOVE){
					if(((BHTreeNode)targetPath.getLastPathComponent()).getUserObject() instanceof DTOProject){
						target.setSelectionPath(targetPath);
						return true;
					}
				}
			} 
		}
		
		//otherwise no DnD is possible
		return false;
		
	}
 
	@Override
	public boolean executeDrop(BHTree target, DefaultMutableTreeNode draggedNode, DefaultMutableTreeNode newParentNode, int action) { 

		//check if everything's allright with BHTreeNodes...
		if(draggedNode instanceof BHTreeNode){
			
			//wrap node (to get UserObjects)
			BHTreeNode draggedBHNode = (BHTreeNode) draggedNode;
			
			
			if(draggedBHNode.getUserObject() instanceof DTOPeriod){
			
				//handle move of Period
				if(action == DnDConstants.ACTION_MOVE){
					//remove from UI...
					draggedBHNode.removeFromParent();
					//...and on DTO basis
					DTOPeriod periodDto = (DTOPeriod)draggedBHNode.getUserObject();
					periodDto.getScenario().removeChild(periodDto);
					
					//add to new UI-Node...
					((DefaultTreeModel)target.getModel()).insertNodeInto(draggedBHNode,newParentNode,newParentNode.getChildCount());
					//...and to DTO structure
					((DTOScenario)((BHTreeNode)newParentNode).getUserObject()).addChild(periodDto); 
					
					//do selection
					TreePath treePath = new TreePath(draggedNode.getPath());
					target.scrollPathToVisible(treePath);
					target.setSelectionPath(treePath);
					
					return true;
				}
				
				
			}
			/*
			else if(draggedBHNode.getUserObject() instanceof DTOScenario){
				
				//handle move of Scenario
				if(action == DnDConstants.ACTION_MOVE){
					//remove from UI...
					draggedBHNode.removeFromParent();
					//...and on DTO basis
					DTOScenario scenarioDto = (DTOScenario)draggedBHNode.getUserObject();
					//TODO
					
					//add to new UI-Node...
					((DefaultTreeModel)target.getModel()).insertNodeInto(draggedBHNode,newParentNode,newParentNode.getChildCount());
					//...and to DTO structure
					
					//((DTOScenario)((BHTreeNode)newParentNode).getUserObject()).addChild(scenarioDto); 
					
					//do selection
					TreePath treePath = new TreePath(draggedNode.getPath());
					target.scrollPathToVisible(treePath);
					target.setSelectionPath(treePath);
					
					//TODO
					return false;
				}
			}
			*/
		}
		
		return false;
		
//		if (action == DnDConstants.ACTION_COPY) {
//			DefaultMutableTreeNode newNode = BHTree.makeDeepCopy(draggedNode);
//			((DefaultTreeModel)target.getModel()).insertNodeInto(newNode,newParentNode,newParentNode.getChildCount());
//			TreePath treePath = new TreePath(newNode.getPath());
//			target.scrollPathToVisible(treePath);
//			target.setSelectionPath(treePath);	
//			return(true);
//		}

	}

//	@Override
//	public void dragGestureRecognized(DragGestureEvent dge){
//		System.out.println("-------drag");
//	}

}
