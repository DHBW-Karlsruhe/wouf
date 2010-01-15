package org.bh.gui.swing;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.dnd.DnDConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.bh.data.DTO;
import org.bh.data.DTOPeriod;
import org.bh.data.DTOProject;
import org.bh.data.DTOScenario;
import org.bh.platform.IPlatformListener;
import org.bh.platform.PlatformEvent;
import org.bh.platform.PlatformKey;
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
public class BHTree extends JTree{
	
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
	

	/**
	 * icon for project nodes with error.
	 */
	public static ImageIcon ERROR_ICON = Services.createImageIcon("/org/bh/images/tree/error.png", Services.getTranslator().translate("error"));

	/**
	 * Node that holds all projects (which contain scenarios). 
	 * This node is not visible on GUI. It's only technically necessary.
	 */

	protected DefaultMutableTreeNode rootNode;
	protected DefaultTreeModel treeModel;

	DefaultTreeModel model;

	public BHTree() {
		// set settings
		this.setEditable(true);
		this.setDragEnabled(true);
		this.setRootVisible(false);
		this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		this.setShowsRootHandles(true);
		this.setCellRenderer(new BHTreeCellRenderer());
		
		Services.addPlatformListener(new BHTreeValidationListener(this));

		new DefaultTreeTransferHandler(this, DnDConstants.ACTION_COPY_OR_MOVE);

		this.addMouseListener(new MouseAdapter(){
			@Override
            public void mouseReleased(MouseEvent e) {
                showPopup(e);
            }
			@Override
			public void mouseClicked(MouseEvent e){
				showPopup(e);
			}
        });

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
	
	public BHTreeNode getNodeForDto(DTO<?> dto){
		
		//check Projects
		BHTreeNode tempProjectNode;
		for(int i = 0; i < ((MutableTreeNode)((DefaultTreeModel)this.getModel()).getRoot()).getChildCount();i++){
			tempProjectNode = (BHTreeNode)((MutableTreeNode)((DefaultTreeModel)this.getModel()).getRoot()).getChildAt(i);
			if(tempProjectNode.getUserObject() == dto){
				return tempProjectNode;
			}
				
			//check Scenarios...
			BHTreeNode tempScenarioNode;
			for(int y = 0; y < tempProjectNode.getChildCount();y++){
				tempScenarioNode = (BHTreeNode)tempProjectNode.getChildAt(y);
				if(tempScenarioNode.getUserObject() == dto){
					return tempScenarioNode;
				}
						
				//check Periods
				BHTreeNode tempPeriodNode;
				for(int z = 0; z< tempScenarioNode.getChildCount();z++){
					tempPeriodNode = (BHTreeNode)tempScenarioNode.getChildAt(z);
					if(tempPeriodNode.getUserObject() == dto){
						return tempPeriodNode;
					}
				}
			}
		}
		//after all, return null;
		return null;	
	}
			
	
	void showPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            JPopupMenu neu = new JPopupMenu();
            
            neu.add(new BHMenuItem(PlatformKey.TOOLBARREMOVE));
            neu.add(new BHMenuItem(PlatformKey.TOOLBARADDPER));
            
            neu.show(e.getComponent(), e.getX(), e.getY());
            
            //int selRow = BHTree.this.getRowForLocation(e.getX(), e.getY());
            TreePath selPath = BHTree.this.getPathForLocation(e.getX(), e.getY());   
            if( selPath != null )
                        BHTree.this.setSelectionPath(selPath);
        }
        
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
				if(((DTOScenario)node.getUserObject()).isValid(true)){
					icon = BHTree.SCENARIO_ICON;
				}
				else icon = BHTree.ERROR_ICON;

			}
			else if (node.getUserObject() instanceof DTOPeriod) {
				if(((DTOPeriod)node.getUserObject()).isValid(true)){
					icon = BHTree.PERIOD_ICON;
				}
				else icon = BHTree.ERROR_ICON;

			}
			
			// return TreeCell
			JLabel treeCell = new JLabel(value.toString(), icon, SwingConstants.LEFT);
			treeCell.setPreferredSize(new Dimension((int) treeCell.getPreferredSize().getWidth(), UIManager.getInt("BHTree.nodeheight"))); 
			return treeCell;
		}
	}
	
	public class BHTreeValidationListener implements IPlatformListener{
		
		BHTree tree;
		
		public BHTreeValidationListener(BHTree tree){
			this.tree = tree;
		}
		
		public void platformEvent(PlatformEvent e) {
			if(e.getEventType() == PlatformEvent.Type.DATA_CHANGED){
				tree.revalidate();
			}
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
	public BHTreeNode addProject(DTOProject newProject){
		//create new Node
		BHTreeNode newProjectNode = new BHTreeNode(newProject);
		
		//insert Node into Tree
		((DefaultTreeModel)this.getModel()).insertNodeInto(
				newProjectNode, 
				(DefaultMutableTreeNode)this.getModel().getRoot(), 
				((DefaultMutableTreeNode)this.getModel().getRoot()).getChildCount()
		);
		
		//are there Children?
		for(DTOScenario scenario : newProject.getChildren()){
			this.addScenario(scenario, newProjectNode);
		}
		
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
	public BHTreeNode addScenario(DTOScenario newScenario, BHTreeNode parentNode){
		//create new Node
		BHTreeNode newScenarioNode = new BHTreeNode(newScenario);
		//add Node to Tree
		((DefaultTreeModel)this.getModel()).insertNodeInto(
				newScenarioNode, parentNode, parentNode.getChildCount()
		);
		
		//are there Children?
		for(DTOPeriod period : newScenario.getChildren()){
			this.addPeriod(period, newScenarioNode);
		}
		
		return newScenarioNode;
		
	}
	
	
	/**
	 * 
	 */
	public BHTreeNode addScenarioAtCurrentPos(DTOScenario newScenario){
		return this.addScenario(newScenario, (BHTreeNode)(this.getSelectionPath().getPathComponent(1)));
	}
	
	
	
	
	
	/**
	 * method to add a new PeriodNode
	 * 
	 * @param newPeriod
	 * 		DTOPeriod
	 * @param bhmf
	 * 		BusinessHorizon MainFrame
	 * @return
	 * 		BHTreeNode
	 */
	public BHTreeNode addPeriod(DTOPeriod newPeriod, BHTreeNode parentNode){
		//create new Node
		BHTreeNode newPeriodNode = new BHTreeNode(newPeriod);
		
		//add Node to Tree
		((DefaultTreeModel)this.getModel()).insertNodeInto(
				newPeriodNode, parentNode, parentNode.getChildCount()
		);
		return newPeriodNode;
	}
	
	/**
	 * 
	 * @param newPeriod
	 * @return
	 */
	public BHTreeNode addPeriodAtCurrentPos(DTOPeriod newPeriod){
		return this.addPeriod(newPeriod, (BHTreeNode)(this.getSelectionPath().getPathComponent(2)));
	}
	
	/**
	 * 
	 * removes all children of tree and DTO
	 * 
	 * @param tempDto
	 */
	public void removeAllPeriods(DTOScenario tempDto){
		
		BHTreeNode tempNode = this.getNodeForDto(tempDto);
		//remove from TreeNode
		for(int i = 0; i < tempNode.getChildCount(); i++) {
			((DefaultTreeModel)this.getModel()).removeNodeFromParent((BHTreeNode)tempNode.getChildAt(i));
		}
		
		//remove from DTO
		tempDto.removeAllChildren();
	}
	
	/**
	 * remove all children of tree and DTO
	 * 
	 * @param scenarioNode
	 */
	public void removeAllPeriods(BHTreeNode scenarioNode){
		if(scenarioNode.getUserObject() instanceof DTOScenario){
			for(int i = 0; i < scenarioNode.getChildCount(); i++) {
				((DefaultTreeModel)this.getModel()).removeNodeFromParent((BHTreeNode)scenarioNode.getChildAt(i));
			}
			
			//remove from DTO
			((DTOScenario)scenarioNode.getUserObject()).removeAllChildren();
		}
	}
	
	/**
	 * method to duplicate a ScenarioNode
	 * 
	 * @param newScenario
	 * 		DTOScenario
	 * @param bhmf
	 * 		BusinessHorizon MainFrame
	 * @param parentNode
	 * 		BHTreeNode
	 * @return
	 * 		newScenarioNode
	 */
	public BHTreeNode duplicateScenarioNode(DTOScenario newScenario, BHMainFrame bhmf, BHTreeNode parentNode){
		BHTreeNode newScenarioNode = new BHTreeNode(newScenario);
		((DefaultTreeModel)bhmf.getBHTree().getModel()).insertNodeInto(newScenarioNode, parentNode, parentNode.getChildCount());
		parentNode.add(newScenarioNode);
		return newScenarioNode;
	}
	/**
	 * method to duplicate a PeriodNode
	 * 
	 * @param newPeriod
	 * 		DTOPeriod
	 * @param bhmf
	 * 		BusinessHorizon MainFrame
	 * @param parentNode
	 * 		BHTreeNode
	 * @return
	 * 		newPeriodNode
	 */
	public BHTreeNode duplicatePeriodNode(DTOPeriod newPeriod, BHMainFrame bhmf, BHTreeNode parentNode){
		BHTreeNode newPeriodNode = new BHTreeNode(newPeriod);
		((DefaultTreeModel)bhmf.getBHTree().getModel()).insertNodeInto(newPeriodNode, parentNode, parentNode.getChildCount());
		return newPeriodNode;
	}
	
	public static DefaultMutableTreeNode makeDeepCopy(DefaultMutableTreeNode node) {
		DefaultMutableTreeNode copy = new DefaultMutableTreeNode(node.getUserObject());
		for (Enumeration e = node.children(); e.hasMoreElements();) {	
			copy.add(makeDeepCopy((DefaultMutableTreeNode)e.nextElement()));
		}
		return(copy);
	}
	
	/*
	 * expand all nodes
	 * 
	 * @author Loeckelt.Michael
	 */
	public void expandAll () {
		for (int i = 0; i < this.getRowCount(); i++)  {
	         this.expandRow(i);
		}
	}
	
	
	
	/*
	 * collapse all nodes
	 * 
	 * @author Loeckelt.Michael
	 */
	public void collapseAll () {
		for (int i = this.getRowCount() - 1; i >= 0; i--) {
	         this.collapseRow(i);
		}
	}
}

	

