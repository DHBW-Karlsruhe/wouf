/*******************************************************************************
 * Copyright 2011: Matthias Beste, Hannes Bischoff, Lisa Doerner, Victor Guettler, Markus Hattenbach, Tim Herzenstiel, Günter Hesse, Jochen Hülß, Daniel Krauth, Lukas Lochner, Mark Maltring, Sven Mayer, Benedikt Nees, Alexandre Pereira, Patrick Pfaff, Yannick Rödl, Denis Roster, Sebastian Schumacher, Norman Vogel, Simon Weber 
 *
 * Copyright 2010: Anna Aichinger, Damian Berle, Patrick Dahl, Lisa Engelmann, Patrick Groß, Irene Ihl, Timo Klein, Alena Lang, Miriam Leuthold, Lukas Maciolek, Patrick Maisel, Vito Masiello, Moritz Olf, Ruben Reichle, Alexander Rupp, Daniel Schäfer, Simon Waldraff, Matthias Wurdig, Andreas Wußler
 *
 * Copyright 2009: Manuel Bross, Simon Drees, Marco Hammel, Patrick Heinz, Marcel Hockenberger, Marcus Katzor, Edgar Kauz, Anton Kharitonov, Sarah Kuhn, Michael Löckelt, Heiko Metzger, Jacqueline Missikewitz, Marcel Mrose, Steffen Nees, Alexander Roth, Sebastian Scharfenberger, Carsten Scheunemann, Dave Schikora, Alexander Schmalzhaf, Florian Schultze, Klaus Thiele, Patrick Tietze, Robert Vollmer, Norman Weisenburger, Lars Zuckschwerdt
 *
 * Copyright 2008: Camil Bartetzko, Tobias Bierer, Lukas Bretschneider, Johannes Gilbert, Daniel Huser, Christopher Kurschat, Dominik Pfauntsch, Sandra Rath, Daniel Weber
 *
 * This program is free software: you can redistribute it and/or modify it un-der the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FIT-NESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package org.bh.gui.swing.tree;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.dnd.DnDConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.bh.data.DTO;
import org.bh.data.DTOPeriod;
import org.bh.data.DTOProject;
import org.bh.data.DTOScenario;
import org.bh.gui.swing.BHMainFrame;
import org.bh.gui.swing.misc.Icons;
import org.bh.platform.IPlatformListener;
import org.bh.platform.PlatformEvent;
import org.bh.platform.Services;
import org.bh.platform.i18n.BHTranslator;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * The Tree visualizing the file contents.
 * 
 * <p>
 * 
 * This class provides the Tree, that shows the contents of a file.
 * 
 * @author Tietze.Patrick
 * @author Thiele.Klaus
 * @author Zuckschwerdt.Lars
 * 
 * @version 1.0, 22.01.2010
 * 
 */
@SuppressWarnings("serial")
public class BHTree extends JTree {

    /**
     * icon for project nodes.
     */
    public static ImageIcon PROJECT_ICON = Services.createImageIcon("/org/bh/images/tree/project.png");

    /**
     * icon for scenario nodes.
     */
    public static ImageIcon SCENARIO_ICON = Services.createImageIcon("/org/bh/images/tree/scenario.png");

    /**
     * icon for period nodes.
     */
    public static ImageIcon PERIOD_ICON = Services.createImageIcon("/org/bh/images/tree/period.png");

    /**
     * right-click popup for tree nodes with functions like 'remove' etc.
     */
    private JPopupMenu projectPopup = new BHTreePopup(BHTreePopup.Type.PROJECT);

    private JPopupMenu defaultPopup = new BHTreePopup(BHTreePopup.Type.PERIOD);

    /**
     * Constructor
     */
    public BHTree() {
	// set settings
	this.setEditable(true);
	this.setRootVisible(false);
	this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
	BHTreeCellRenderer tcr = new BHTreeCellRenderer();
	this.setCellRenderer(tcr);

	Services.addPlatformListener(new BHTreeValidationListener(this));

	// Drag and Drop
	new BHTreeTransferHandler(this, DnDConstants.ACTION_COPY_OR_MOVE);

	this.addMouseListener(new PopupListener());

    }

    /**
     * fixed minimum width.
     */
    @Override
    public Dimension getMinimumSize() {
	return new Dimension(UIManager.getInt("BHTree.minimumWidth"), super.getMinimumSize().height);
    }



    public BHTreeNode getNodeForDto(DTO<?> dto) {

	// check Projects
	BHTreeNode tempProjectNode;
	for (int i = 0; i < ((MutableTreeNode) ((DefaultTreeModel) this.getModel()).getRoot()).getChildCount(); i++) {
	    tempProjectNode = (BHTreeNode) ((MutableTreeNode) ((DefaultTreeModel) this.getModel()).getRoot()).getChildAt(i);
	    if (tempProjectNode.getUserObject() == dto) {
		return tempProjectNode;
	    }

	    // check Scenarios...
	    BHTreeNode tempScenarioNode;
	    for (int y = 0; y < tempProjectNode.getChildCount(); y++) {
		tempScenarioNode = (BHTreeNode) tempProjectNode.getChildAt(y);
		if (tempScenarioNode.getUserObject() == dto) {
		    return tempScenarioNode;
		}

		// check Periods
		BHTreeNode tempPeriodNode;
		for (int z = 0; z < tempScenarioNode.getChildCount(); z++) {
		    tempPeriodNode = (BHTreeNode) tempScenarioNode.getChildAt(z);
		    if (tempPeriodNode.getUserObject() == dto) {
			return tempPeriodNode;
		    }

		    // check PeriodChilds
		    for (int a = 0; a < tempPeriodNode.getChildCount(); a++) {

		    }
		}

	    }
	}
	// after all, return null;
	return null;
    }

    /**
     * 
     * shows Popup for tree node if event was a popuptrigger; otherwise, nothing
     * happens
     * 
     * 
     * @param e
     */
    void showPopup(MouseEvent e, BHTreePopup.Type nodeType) {

	switch (nodeType) {
	case PROJECT:
	    projectPopup.show(e.getComponent(), e.getX(), e.getY());
	    break;

	case SCENARIO:

	case PERIOD:
	    defaultPopup.show(e.getComponent(), e.getX(), e.getY());
	    break;
	}
    }

    /**
     * Provides a <code>TreeCellRenderer</code> for the BHTree.
     * 
     * 
     * @author Thiele.Klaus
     * @version 1.0, 2009/12/29
     * 
     */    public class BHTreeCellRenderer implements TreeCellRenderer {

	/**
	 * provides a <code>Component</code> for each node in the tree. Is
	 * called by Swing internally.
	 */
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {

	    // Downcast just for determination of the type.
	    DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;

	    // Determine icon
	    ImageIcon icon = null;

	    if (node.getUserObject() instanceof DTOProject) {

		icon = BHTree.PROJECT_ICON;

	    } else if (node.getUserObject() instanceof DTOScenario) {
		if (((DTOScenario) node.getUserObject()).isValid(true)) {
		    icon = BHTree.SCENARIO_ICON;
		} else
		    icon = Icons.WARNING_ICON;

	    } else if (node.getUserObject() instanceof DTOPeriod) {
		if (((DTOPeriod) node.getUserObject()).isValid(true)) {
		    icon = BHTree.PERIOD_ICON;
		} else
		    icon = Icons.WARNING_ICON;

	    }

	    // create visualisation of treeNode
	    JLabel treeCell;
	    JPanel treeCellPanel = new JPanel();

	    String rowDef = "p";
	    String colDef = "p,p,p";
	    CellConstraints cons = new CellConstraints();
	    FormLayout layout = new FormLayout(colDef, rowDef);
	    treeCellPanel.setLayout(layout);
	    treeCellPanel.setOpaque(false);

	    // when Period, write index onto visualisation
	    if (node.getUserObject() instanceof DTOPeriod) {
		DTOPeriod periodDto = (DTOPeriod) node.getUserObject();
		int idx = periodDto.getScenario().getChildren().indexOf(periodDto);
		if (!periodDto.getScenario().isDeterministic()) {
		    idx = idx - periodDto.getScenario().getChildrenSize() + 1;
			
		}

		// Index label
		JLabel idxLabel = new JLabel("t=" + idx + ": ");
		idxLabel.setForeground(Color.RED);
		idxLabel.setFont(new Font(idxLabel.getFont().getName(), idxLabel.getFont().getStyle(), idxLabel.getFont().getSize() - 3));

		// content label
		treeCell = new JLabel(value.toString(), SwingConstants.LEFT);

		treeCellPanel.add(new JLabel(icon), cons.xy(1, 1));
		treeCellPanel.add(idxLabel, cons.xy(2, 1));
		treeCellPanel.add(treeCell, cons.xy(3, 1));

	    } else
	    // when scenario write scenario type as idx
	    if (node.getUserObject() instanceof DTOScenario) {
		DTOScenario scenarioDto = (DTOScenario) node.getUserObject();
		JLabel idxLabel;
		if(scenarioDto.isDeterministic()){
		    idxLabel = new JLabel("["+BHTranslator.getInstance().translate("deterministic_letter")+"] ");
		}else{
		    idxLabel = new JLabel("["+BHTranslator.getInstance().translate("stochastic_letter")+"] ");
		}

		// Index label
		idxLabel.setForeground(Color.RED);
		idxLabel.setFont(new Font(idxLabel.getFont().getName(), idxLabel.getFont().getStyle(), idxLabel.getFont().getSize() - 3));

		// content label
		treeCell = new JLabel(value.toString(), SwingConstants.LEFT);

		treeCellPanel.add(new JLabel(icon), cons.xy(1, 1));
		treeCellPanel.add(idxLabel, cons.xy(2, 1));
		treeCellPanel.add(treeCell, cons.xy(3, 1));
		
		
		// for periods
	    } else {

		// content label
		treeCell = new JLabel(value.toString(), icon, SwingConstants.LEFT);

		treeCellPanel.add(treeCell, cons.xy(1, 1));
	    }

	    // return TreeCell
	    treeCellPanel.setPreferredSize(new Dimension((int) treeCellPanel.getPreferredSize().getWidth(), UIManager.getInt("BHTree.nodeheight")));
	    return treeCellPanel;
	}
    }

    public class BHTreeValidationListener implements IPlatformListener {

	BHTree tree;

	public BHTreeValidationListener(BHTree tree) {
	    this.tree = tree;
	}

	public void platformEvent(PlatformEvent e) {
	    if (e.getEventType() == PlatformEvent.Type.DATA_CHANGED) {
		tree.revalidate();
	    }
	}

    }

    /**
     * method to create a new ProjectNode
     * 
     * @param newProject
     *            DTOProject
     * @param bhmf
     *            BusinessHoriozon MainFrame
     * @return BHTreeNode
     */
    public BHTreeNode addProject(DTOProject newProject) {
	// create new Node
	BHTreeNode newProjectNode = new BHTreeNode(newProject);

	// insert Node into Tree
	((DefaultTreeModel) this.getModel()).insertNodeInto(newProjectNode, (DefaultMutableTreeNode) this.getModel().getRoot(), ((DefaultMutableTreeNode) this.getModel().getRoot()).getChildCount());

	// are there Children?
	for (DTOScenario scenario : newProject.getChildren()) {
	    this.addScenario(scenario, newProjectNode);
	}

	return newProjectNode;
    }

    /**
     * method to create a new ScenarioNode
     * 
     * @param newScenario
     *            DTOScenario
     * @param bhmf
     *            BusinessHoriozon MainFrame
     * @return BHTreeNode
     */
    public BHTreeNode addScenario(DTOScenario newScenario, BHTreeNode parentNode) {
	// create new Node
	BHTreeNode newScenarioNode = new BHTreeNode(newScenario);
	// add Node to Tree
	((DefaultTreeModel) this.getModel()).insertNodeInto(newScenarioNode, parentNode, parentNode.getChildCount());

	// are there Children?
	//if yes, add them in correct order
	for (DTOPeriod period : newScenario.getChildren()) {
	    this.addPeriod(period, newScenarioNode, newScenario.isDeterministic());
	}
	this.expandRow(this.getRowForPath(new TreePath(newScenarioNode.getPath())));
	return newScenarioNode;
    }

    public BHTreeNode addScenarioAtCurrentPos(DTOScenario newScenario) {
	return this.addScenario(newScenario, (BHTreeNode) (this.getSelectionPath().getPathComponent(1)));
    }

    /**
     * method to add a new PeriodNode
     * 
     * @param newPeriod
     *            DTOPeriod
     * @param bhmf
     *            BusinessHorizon MainFrame
     * @return BHTreeNode
     */
    public BHTreeNode addPeriod(DTOPeriod newPeriod, BHTreeNode parentNode){
	return this.addPeriod(newPeriod, parentNode,true);
    }
    
    public BHTreeNode addPeriod(DTOPeriod newPeriod, BHTreeNode parentNode, boolean addLast) {
	// create new Node
	BHTreeNode newPeriodNode = new BHTreeNode(newPeriod);

	// add Node to Tree
	if(addLast){
	    ((DefaultTreeModel) this.getModel()).insertNodeInto(newPeriodNode, parentNode,parentNode.getChildCount());
	}else{
	    ((DefaultTreeModel) this.getModel()).insertNodeInto(newPeriodNode, parentNode,0);
	}
	return newPeriodNode;
	
    }
    
    

    /**
     * 
     * @param newPeriod
     * @return
     */
    public BHTreeNode addPeriodAtCurrentPos(DTOPeriod newPeriod) {
	return this.addPeriod(newPeriod, (BHTreeNode) (this.getSelectionPath().getPathComponent(2)));
    }

    /**
     * 
     * removes all children of tree and DTO
     * 
     * @param tempDto
     */
    public void removeAllPeriods(DTOScenario tempDto) {

	BHTreeNode tempNode = this.getNodeForDto(tempDto);

	// remove from TreeNode
	tempNode.removeAllChildren();

	// remove from DTO
	tempDto.removeAllChildren();
    }

    /**
     * remove all children of tree and DTO
     * 
     * @param scenarioNode
     */
    public void removeAllPeriods(BHTreeNode scenarioNode) {
	if (scenarioNode.getUserObject() instanceof DTOScenario) {
	    for (int i = 0; i < scenarioNode.getChildCount(); i++) {
		((DefaultTreeModel) this.getModel()).removeNodeFromParent((BHTreeNode) scenarioNode.getChildAt(i));
	    }

	    // remove from DTO
	    ((DTOScenario) scenarioNode.getUserObject()).removeAllChildren();
	}
    }

    public ArrayList<BHTreeNode> getProjectNodes() {
	ArrayList<BHTreeNode> results = new ArrayList<BHTreeNode>();
	for (int i = 0; i < ((DefaultMutableTreeNode) this.getModel().getRoot()).getChildCount(); i++) {
	    results.add((BHTreeNode) ((DefaultMutableTreeNode) this.getModel().getRoot()).getChildAt(i));
	}
	return results;
    }

    public ArrayList<BHTreeNode> getScenarioNodes() {
	ArrayList<BHTreeNode> results = new ArrayList<BHTreeNode>();
	for (BHTreeNode projectNode : getProjectNodes()) {
	    for (int y = 0; y < projectNode.getChildCount(); y++) {
		results.add((BHTreeNode) projectNode.getChildAt(y));
	    }

	}
	return results;
    }

    /**
     * method to duplicate a ScenarioNode
     * 
     * @param newScenario
     *            DTOScenario
     * @param bhmf
     *            BusinessHorizon MainFrame
     * @param parentNode
     *            BHTreeNode
     * @return newScenarioNode
     */
    public BHTreeNode duplicateScenarioNode(DTOScenario newScenario, BHMainFrame bhmf, BHTreeNode parentNode) {
	BHTreeNode newScenarioNode = new BHTreeNode(newScenario);
	((DefaultTreeModel) bhmf.getBHTree().getModel()).insertNodeInto(newScenarioNode, parentNode, parentNode.getChildCount());
	parentNode.add(newScenarioNode);
	return newScenarioNode;
    }

    /**
     * method to duplicate a PeriodNode
     * 
     * @param newPeriod
     *            DTOPeriod
     * @param bhmf
     *            BusinessHorizon MainFrame
     * @param parentNode
     *            BHTreeNode
     * @return newPeriodNode
     */
    public BHTreeNode duplicatePeriodNode(DTOPeriod newPeriod, BHMainFrame bhmf, BHTreeNode parentNode) {
	BHTreeNode newPeriodNode = new BHTreeNode(newPeriod);
	((DefaultTreeModel) bhmf.getBHTree().getModel()).insertNodeInto(newPeriodNode, parentNode, parentNode.getChildCount());
	return newPeriodNode;
    }

    public static DefaultMutableTreeNode makeDeepCopy(DefaultMutableTreeNode node) {
	DefaultMutableTreeNode copy = new DefaultMutableTreeNode(node.getUserObject());
	for (Enumeration<?> e = node.children(); e.hasMoreElements();) {
	    copy.add(makeDeepCopy((DefaultMutableTreeNode) e.nextElement()));
	}
	return (copy);
    }

    /*
     * expand all nodes
     * 
     * @author Loeckelt.Michael
     */
    public void expandAll() {
	for (int i = 0; i < this.getRowCount(); i++) {
	    this.expandRow(i);
	}
    }

    /*
     * collapse all nodes
     * 
     * @author Loeckelt.Michael
     */
    public void collapseAll() {
	for (int i = this.getRowCount() - 1; i >= 0; i--) {
	    this.collapseRow(i);
	}
    }

    class PopupListener extends MouseAdapter {

	@Override
	public void mouseReleased(MouseEvent e) {
	    TreePath selPath = BHTree.this.getPathForLocation(e.getX(), e.getY());
	    if (selPath != null && e.isPopupTrigger()) {
		if (((BHTreeNode) selPath.getLastPathComponent()).getUserObject() instanceof DTOProject) {
		    showPopup(e, BHTreePopup.Type.PROJECT);
		} else {
		    showPopup(e, BHTreePopup.Type.PERIOD);
		}
	    }
	}

	@Override
	public void mousePressed(MouseEvent e) {
	    TreePath selPath = BHTree.this.getPathForLocation(e.getX(), e.getY());
	    if (selPath != null && e.isPopupTrigger()) {
		BHTree.this.setSelectionPath(selPath);
		if (((BHTreeNode) selPath.getLastPathComponent()).getUserObject() instanceof DTOProject) {
		    showPopup(e, BHTreePopup.Type.PROJECT);
		} else {
		    showPopup(e, BHTreePopup.Type.PERIOD);
		}
	    }
	}
    };

}
