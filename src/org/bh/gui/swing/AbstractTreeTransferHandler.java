package org.bh.gui.swing;
import java.awt.AlphaComposite;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.image.BufferedImage;

import javax.swing.CellRendererPane;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.bh.gui.swing.BHTreePopup.Type;
 
public abstract class AbstractTreeTransferHandler implements DragGestureListener, DragSourceListener, DropTargetListener {
 
	private DragSource dragSource; // dragsource

//TODO Patrick T. --> brauchen wir die?
//	private DropTarget dropTarget; //droptarget
//	private BHMainFrame bhmf;
	
	private static DefaultMutableTreeNode draggedNode; 
	private DefaultMutableTreeNode draggedNodeParent; 
	private static BufferedImage image = null; //buff image
	private boolean drawImage;
	private BHTree tree;
 
	protected AbstractTreeTransferHandler(BHTree tree, int action, boolean drawIcon) {
		this.tree = tree;
	    drawImage = drawIcon;
		dragSource = new DragSource();
		dragSource.createDefaultDragGestureRecognizer(tree, action, this);
		//TODO Patrick s.o.
		//		dropTarget = new DropTarget(tree, action, this);
		new DropTarget(tree, action, this);
		
	}
 
	/* Methods for DragSourceListener */
	public void dragDropEnd(DragSourceDropEvent dsde) {
		if (dsde.getDropSuccess() && dsde.getDropAction()==DnDConstants.ACTION_MOVE && draggedNodeParent != null) {
			((DefaultTreeModel)tree.getModel()).nodeStructureChanged(draggedNodeParent);				
		}
	}
	public final void dragEnter(DragSourceDragEvent dsde)  {
		int action = dsde.getDropAction();
		if (action == DnDConstants.ACTION_COPY)  {
			dsde.getDragSourceContext().setCursor(DragSource.DefaultCopyDrop);
		} 
		else {
			if (action == DnDConstants.ACTION_MOVE) {
				dsde.getDragSourceContext().setCursor(DragSource.DefaultMoveDrop);
			} 
			else {
				dsde.getDragSourceContext().setCursor(DragSource.DefaultMoveNoDrop);
			}
		}
	}
	public final void dragOver(DragSourceDragEvent dsde) {
		int action = dsde.getDropAction();
		if (action == DnDConstants.ACTION_COPY) {
			dsde.getDragSourceContext().setCursor(DragSource.DefaultCopyDrop);
		} 
		else  {
			if (action == DnDConstants.ACTION_MOVE) {
				dsde.getDragSourceContext().setCursor(DragSource.DefaultMoveDrop);
			} 
			else  {
				dsde.getDragSourceContext().setCursor(DragSource.DefaultMoveNoDrop);
			}
		}
	}
	public final void dropActionChanged(DragSourceDragEvent dsde)  {
		int action = dsde.getDropAction();
		if (action == DnDConstants.ACTION_COPY) {
			dsde.getDragSourceContext().setCursor(DragSource.DefaultCopyDrop);
		}
		else  {
			if (action == DnDConstants.ACTION_MOVE) {
				dsde.getDragSourceContext().setCursor(DragSource.DefaultMoveDrop);
			} 
			else {
				dsde.getDragSourceContext().setCursor(DragSource.DefaultMoveNoDrop);
			}
		}
	}
	public final void dragExit(DragSourceEvent dse) {
	   dse.getDragSourceContext().setCursor(DragSource.DefaultMoveNoDrop);
	}	
		
	/* Methods for DragGestureListener */
	public final void dragGestureRecognized(DragGestureEvent dge) {
		TreePath path = tree.getSelectionPath(); 
		if (path != null) { 
			draggedNode = (DefaultMutableTreeNode)path.getLastPathComponent();
			draggedNodeParent = (DefaultMutableTreeNode)draggedNode.getParent();
			if (drawImage) {
				Rectangle pathBounds = tree.getPathBounds(path); //getpathbounds of selectionpath
				JComponent lbl = (JComponent)tree.getCellRenderer().getTreeCellRendererComponent(tree, draggedNode, false , tree.isExpanded(path),((DefaultTreeModel)tree.getModel()).isLeaf(path.getLastPathComponent()), 0,false);//returning the label
				lbl.setBounds(pathBounds);//setting bounds to lbl
				image = new BufferedImage(lbl.getWidth(), lbl.getHeight(), java.awt.image.BufferedImage.TYPE_INT_ARGB_PRE);//buffered image reference passing the label's ht and width
				Graphics2D graphics = image.createGraphics();//creating the graphics for buffered image
				graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));	//Sets the Composite for the Graphics2D context
				lbl.setOpaque(false);
				lbl.paint(graphics); //painting the graphics to label
				graphics.dispose();				
			}
			dragSource.startDrag(dge, DragSource.DefaultMoveNoDrop , image, new Point(0,0), new TransferableNode(draggedNode), this);			
		}	 
	}
 
	/* Methods for DropTargetListener */
 
	public final void dragEnter(DropTargetDragEvent dtde) {
		Point pt = dtde.getLocation();
		int action = dtde.getDropAction();
		
		if (canPerformAction(tree, draggedNode, action, pt)) {
			dtde.acceptDrag(action);			
		}
		else {
			dtde.rejectDrag();
		}
	}
 
	public final void dragExit(DropTargetEvent dte) {
		
	}
 
	public final void dragOver(DropTargetDragEvent dtde) {
		Point pt = dtde.getLocation();
		int action = dtde.getDropAction();
		
		if (canPerformAction(tree, draggedNode, action, pt)) {
			
			//get element for dragging...
			for(Component c : tree.getComponents()){
				
			}
			
			//Component d = c.getComponentAt(dtde.getLocation());
			
			//CellRendererPane crp = (CellRendererPane)c.getComponentAt(dtde.getLocation());
			
			//System.out.println("---"+d.getComponentAt(dtde.getLocation()).getClass().getName());
			
				//paint a line on the drag-node
				
				//c.getLocation();
				//tree.getGraphics().drawLine(d.getLocation().x,d.getLocation().y, d.getLocation().x+120, d.getLocation().y );
			
			
			dtde.acceptDrag(action);
			
		}
		else {
			dtde.rejectDrag();
		}
	}
 
	public final void dropActionChanged(DropTargetDragEvent dtde) {
		Point pt = dtde.getLocation();
		int action = dtde.getDropAction();
		if (canPerformAction(tree, draggedNode, action, pt)) {
			dtde.acceptDrag(action);
		}
		else {
			dtde.rejectDrag();
		}
	}
 
	public final void drop(DropTargetDropEvent dtde) {
		try {
			int action = dtde.getDropAction();
			Transferable transferable = dtde.getTransferable();
			Point pt = dtde.getLocation();
			if (transferable.isDataFlavorSupported(TransferableNode.NODE_FLAVOR) && canPerformAction(tree, draggedNode, action, pt)) {
				TreePath pathTarget = tree.getPathForLocation(pt.x, pt.y);
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) transferable.getTransferData(TransferableNode.NODE_FLAVOR);
				DefaultMutableTreeNode newParentNode =(DefaultMutableTreeNode)pathTarget.getLastPathComponent();
				if (executeDrop(tree, node, newParentNode, action)) {
					dtde.acceptDrop(action);				
					dtde.dropComplete(true);
					return;					
				}
			}
			dtde.rejectDrop();
			dtde.dropComplete(false);
		}		
		catch (Exception e) {	
			//System.out.println(e);
			dtde.rejectDrop();
			dtde.dropComplete(false);
		}	
	}
 
	public abstract boolean canPerformAction(BHTree target, DefaultMutableTreeNode draggedNode, int action, Point location);
 
	public abstract boolean executeDrop(BHTree tree, DefaultMutableTreeNode draggedNode, DefaultMutableTreeNode newParentNode, int action);
}
