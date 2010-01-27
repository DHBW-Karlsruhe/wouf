package org.bh.gui.swing.tree;

import java.awt.AlphaComposite;
import java.awt.Graphics;
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

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.bh.data.DTOPeriod;
import org.bh.data.DTOProject;
import org.bh.data.DTOScenario;

public class BHTreeTransferHandler implements DragGestureListener, DragSourceListener, DropTargetListener {

    private DragSource dragSource; 
    private static DefaultMutableTreeNode draggedNode;
    private DefaultMutableTreeNode draggedNodeParent;
    private static BufferedImage image = null;
    private boolean drawImage;
    private BHTree tree;
    private JPanel dragLine;

    protected BHTreeTransferHandler(BHTree tree, int action, boolean drawIcon) {
	this.tree = tree;
	drawImage = drawIcon;
	dragSource = new DragSource();
	dragSource.createDefaultDragGestureRecognizer(tree, action, this);
	
	new DropTarget(tree, action, this);

	// init dragLine
	class dragLine extends JPanel {
	    @Override
	    public void paint(Graphics g) {
		// dragLine consists of one bar...
		super.paint(g);
		g.fillRoundRect(0, 0, 120, 4, 2, 2);

	    }
	}
	dragLine = new dragLine();
    }

    protected BHTreeTransferHandler(BHTree tree, int action) {
	this(tree, action, false);
    }

    /* Methods for DragSourceListener */
    public void dragDropEnd(DragSourceDropEvent dsde) {
	if (dsde.getDropSuccess() && dsde.getDropAction() == DnDConstants.ACTION_MOVE && draggedNodeParent != null) {
	    ((DefaultTreeModel) tree.getModel()).nodeStructureChanged(draggedNodeParent);
	}
	tree.remove(dragLine);
    }

    public final void dragEnter(DragSourceDragEvent dsde) {
	int action = dsde.getDropAction();
	if (action == DnDConstants.ACTION_COPY) {
	    dsde.getDragSourceContext().setCursor(DragSource.DefaultCopyDrop);
	} else {
	    if (action == DnDConstants.ACTION_MOVE) {
		dsde.getDragSourceContext().setCursor(DragSource.DefaultMoveDrop);
	    } else {
		dsde.getDragSourceContext().setCursor(DragSource.DefaultMoveNoDrop);
	    }
	}
    }

    public final void dragOver(DragSourceDragEvent dsde) {
	int action = dsde.getDropAction();
	if (action == DnDConstants.ACTION_COPY) {
	    dsde.getDragSourceContext().setCursor(DragSource.DefaultCopyDrop);
	} else {
	    if (action == DnDConstants.ACTION_MOVE) {
		dsde.getDragSourceContext().setCursor(DragSource.DefaultMoveDrop);
	    } else {
		dsde.getDragSourceContext().setCursor(DragSource.DefaultMoveNoDrop);
	    }
	}
    }

    public final void dropActionChanged(DragSourceDragEvent dsde) {
	int action = dsde.getDropAction();
	if (action == DnDConstants.ACTION_COPY) {
	    dsde.getDragSourceContext().setCursor(DragSource.DefaultCopyDrop);
	} else {
	    if (action == DnDConstants.ACTION_MOVE) {
		dsde.getDragSourceContext().setCursor(DragSource.DefaultMoveDrop);
	    } else {
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
	    draggedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
	    draggedNodeParent = (DefaultMutableTreeNode) draggedNode.getParent();
	    if (drawImage) {
		Rectangle pathBounds = tree.getPathBounds(path);
		
		JComponent lbl = (JComponent) tree.getCellRenderer().getTreeCellRendererComponent(tree, draggedNode, false, tree.isExpanded(path),
			((DefaultTreeModel) tree.getModel()).isLeaf(path.getLastPathComponent()), 0, false);
		
		lbl.setBounds(pathBounds);
		image = new BufferedImage(lbl.getWidth(), lbl.getHeight(), java.awt.image.BufferedImage.TYPE_INT_ARGB_PRE);
		
		Graphics2D graphics = image.createGraphics();
		
		graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		
		lbl.setOpaque(false);
		lbl.paint(graphics); 
		graphics.dispose();
	    }
	    dragSource.startDrag(dge, DragSource.DefaultMoveNoDrop, image, new Point(0, 0), new TransferableNode(draggedNode), this);
	}
    }

    /* Methods for DropTargetListener */

    public final void dragEnter(DropTargetDragEvent dtde) {
	Point pt = dtde.getLocation();
	int action = dtde.getDropAction();

	if (canPerformAction(tree, draggedNode, action, pt)) {
	    dtde.acceptDrag(action);
	} else {
	    dtde.rejectDrag();
	}
    }

    public final void dragExit(DropTargetEvent dte) {
	tree.remove(dragLine);
    }

    public final void dragOver(DropTargetDragEvent dtde) {
	Point pt = dtde.getLocation();
	int action = dtde.getDropAction();

	if (canPerformAction(tree, draggedNode, action, pt)) {

	    if (dragLine.getParent() == null) {
		tree.add(dragLine);
	    }

	    /*
	     * get Position for dragLine
	     */

	    int targetRow = 0;
	    Point targetPos;

	    BHTreeNode targetBHNode = (BHTreeNode) tree.getPathForLocation(dtde.getLocation().x, dtde.getLocation().y).getLastPathComponent();
	    // Scenario is target
	    if (targetBHNode.getUserObject() instanceof DTOScenario) {
		// Scenario is dragged
		if (((BHTreeNode) draggedNode).getUserObject() instanceof DTOScenario) {
		    targetRow = tree.getRowForLocation(dtde.getLocation().x, dtde.getLocation().y);
		    targetPos = tree.getRowBounds(targetRow).getLocation();
		    if (dtde.getLocation().y < targetPos.y + tree.getRowBounds(targetRow).height / 2) {
			dragLine.setBounds(targetPos.x, targetPos.y - 3, 120, 4);
		    } else {
			dragLine.setBounds(targetPos.x, targetPos.y + tree.getRowBounds(targetRow).height - 1, 120, 4);
		    }

		    // Period is dragged
		} else if (((BHTreeNode) draggedNode).getUserObject() instanceof DTOPeriod) {
		    tree.remove(dragLine);
		    tree.repaint();
		}

	    } else

	    // Period is target
	    if (targetBHNode.getUserObject() instanceof DTOPeriod) {
		targetRow = tree.getRowForLocation(dtde.getLocation().x, dtde.getLocation().y);
		targetPos = tree.getRowBounds(targetRow).getLocation();
		if (dtde.getLocation().y < targetPos.y + tree.getRowBounds(targetRow).height / 2) {
		    dragLine.setBounds(targetPos.x, targetPos.y - 3, 120, 4);
		}
		if (dtde.getLocation().y > targetPos.y + tree.getRowBounds(targetRow).height / 2) {
		    dragLine.setBounds(targetPos.x, targetPos.y + tree.getRowBounds(targetRow).height - 1, 120, 4);
		}
	    } else

	    // Project is target
	    if (targetBHNode.getUserObject() instanceof DTOProject) {
		tree.remove(dragLine);
		tree.repaint();
	    }

	    dtde.acceptDrag(action);

	} else {
	    dtde.rejectDrag();
	    if (dragLine.getParent() != null) {
		tree.remove(dragLine);
		tree.repaint();
	    }

	}
    }

    public final void dropActionChanged(DropTargetDragEvent dtde) {
	Point pt = dtde.getLocation();
	int action = dtde.getDropAction();
	if (canPerformAction(tree, draggedNode, action, pt)) {
	    dtde.acceptDrag(action);
	} else {
	    dtde.rejectDrag();
	}
    }

    public final void drop(DropTargetDropEvent dtde) {
	if (dragLine.getParent() != null) {
	    tree.remove(dragLine);
	    tree.repaint();
	}
	try {
	    int action = dtde.getDropAction();
	    Transferable transferable = dtde.getTransferable();
	    Point pt = dtde.getLocation();
	    if (transferable.isDataFlavorSupported(TransferableNode.NODE_FLAVOR) && canPerformAction(tree, draggedNode, action, pt)) {
		TreePath pathTarget = tree.getPathForLocation(pt.x, pt.y);
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) transferable.getTransferData(TransferableNode.NODE_FLAVOR);
		DefaultMutableTreeNode newParentNode = (DefaultMutableTreeNode) pathTarget.getLastPathComponent();
		if (executeDrop(tree, node, newParentNode, action, dtde.getLocation())) {
		    dtde.acceptDrop(action);
		    dtde.dropComplete(true);
		    return;
		}
	    }
	    dtde.rejectDrop();
	    dtde.dropComplete(false);
	} catch (Exception e) {
	    // System.out.println(e);
	    dtde.rejectDrop();
	    dtde.dropComplete(false);
	}
    }

    public boolean canPerformAction(BHTree target, DefaultMutableTreeNode draggedNode, int action, Point location) {

	// check if everything's allright with BHTreeNodes...
	if (draggedNode instanceof BHTreeNode) {

	    // wrap node (to get UserObjects)
	    BHTreeNode draggedBHNode = (BHTreeNode) draggedNode;

	    // get target and set selection if possible
	    TreePath targetPath = target.getPathForLocation(location.x, location.y);
	    if (targetPath == null) {
		return false;
	    }

	    // check if source and target are equal
	    if (targetPath.getLastPathComponent().equals(draggedNode)) {
		return false;
	    }

	    if (draggedBHNode.getUserObject() instanceof DTOPeriod) {

		/*
		 * Allow to Move Period to other Scenario
		 */
		if (action == DnDConstants.ACTION_MOVE) {
		    if (((BHTreeNode) targetPath.getLastPathComponent()).getUserObject() instanceof DTOPeriod) {
			if (((DTOScenario) ((BHTreeNode) ((BHTreeNode) targetPath.getLastPathComponent()).getParent()).getUserObject()).get(DTOScenario.Key.PERIOD_TYPE).toString().equalsIgnoreCase(
				((DTOScenario) ((BHTreeNode) draggedBHNode.getParent()).getUserObject()).get(DTOScenario.Key.PERIOD_TYPE).toString())) {
			    target.setSelectionPath(targetPath);
			    return true;
			}

		    } else if (((BHTreeNode) targetPath.getLastPathComponent()).getUserObject() instanceof DTOScenario) {
			if (((DTOScenario) ((BHTreeNode) targetPath.getLastPathComponent()).getUserObject()).get(DTOScenario.Key.PERIOD_TYPE).toString().equalsIgnoreCase(
				((DTOScenario) ((BHTreeNode) draggedBHNode.getParent()).getUserObject()).get(DTOScenario.Key.PERIOD_TYPE).toString())) {
			    target.setSelectionPath(targetPath);
			    return true;
			}
		    }

		}
	    } else if (draggedBHNode.getUserObject() instanceof DTOScenario) {

		/*
		 * Allow to Move Scenario to other Project
		 */
		if (action == DnDConstants.ACTION_MOVE) {
		    if (((BHTreeNode) targetPath.getLastPathComponent()).getUserObject() instanceof DTOScenario
			    || ((BHTreeNode) targetPath.getLastPathComponent()).getUserObject() instanceof DTOProject) {
			target.setSelectionPath(targetPath);
			return true;
		    }
		}
	    }
	}
	// otherwise no DnD is possible
	return false;

    }

    public boolean executeDrop(BHTree target, DefaultMutableTreeNode draggedNode, DefaultMutableTreeNode newParentNode, int action, Point location) {

	// check if everything's allright with BHTreeNodes...
	if (draggedNode instanceof BHTreeNode) {

	    // wrap node (to get UserObjects)
	    BHTreeNode draggedBHNode = (BHTreeNode) draggedNode;

	    if (draggedBHNode.getUserObject() instanceof DTOPeriod) {

		if (action == DnDConstants.ACTION_MOVE) {
		    DTOPeriod periodDto = (DTOPeriod) draggedBHNode.getUserObject();

		    if (((BHTreeNode) newParentNode).getUserObject() instanceof DTOScenario) {
			// remove and add to new UI-Node...
			draggedBHNode.removeFromParent();
			((DefaultTreeModel) target.getModel()).insertNodeInto(draggedBHNode, newParentNode, newParentNode.getChildCount());
			// ...and from and to DTO structure
			periodDto.getScenario().removeChild(periodDto);
			((DTOScenario) ((BHTreeNode) newParentNode).getUserObject()).addChild(periodDto);
		    } else if (((BHTreeNode) newParentNode).getUserObject() instanceof DTOPeriod) {
			// find out position of new node/DTO
			try {
			    int targetRow = target.getRowForLocation(location.x, location.y);
			    int targetIdx = 0;
			    int offset = 0;

			    if (targetRow == -1) {
				// get last position of children
				targetIdx = ((BHTreeNode) ((BHTreeNode) target.getSelectionPath().getLastPathComponent()).getParent()).getIndex((BHTreeNode) target.getSelectionPath()
					.getLastPathComponent());

			    } else {

				Point targetPos = tree.getRowBounds(targetRow).getLocation();

				// add over selected node
				if (location.y < targetPos.y + tree.getRowBounds(targetRow).height / 2) {
				    if (!((DTOScenario) ((BHTreeNode) ((BHTreeNode) target.getSelectionPath().getLastPathComponent()).getParent()).getUserObject()).isDeterministic()) {
					offset = 0;
				    } else {
					offset = 0;
				    }

				}

				// add below selected node
				if (location.y > targetPos.y + tree.getRowBounds(targetRow).height / 2) {
				    if (!((DTOScenario) ((BHTreeNode) ((BHTreeNode) target.getSelectionPath().getLastPathComponent()).getParent()).getUserObject()).isDeterministic()) {
					offset = 1;
				    } else {
					offset = 1;
				    }
				}

				// handle diff of "dragUp" vs. "dragDown"
				if (target.getRowForPath(target.getSelectionPath()) > target.getRowForPath(new TreePath(draggedNode.getPath()))
					&& ((BHTreeNode) ((BHTreeNode) target.getSelectionPath().getLastPathComponent()).getParent()) == draggedBHNode.getParent()) {
				    targetIdx -= 1;
				}

				targetIdx += ((BHTreeNode) ((BHTreeNode) target.getSelectionPath().getLastPathComponent()).getParent()).getIndex((BHTreeNode) target.getSelectionPath()
					.getLastPathComponent())
					+ offset;

			    }


			    // ...and to DTO structure
			    periodDto.getScenario().removeChild(periodDto);
			    int dtoIdx = ((DTOScenario) ((BHTreeNode) newParentNode.getParent()).getUserObject()).getChildren().indexOf(((BHTreeNode) newParentNode).getUserObject());
			    if (((DTOScenario) ((BHTreeNode) ((BHTreeNode) target.getSelectionPath().getLastPathComponent()).getParent()).getUserObject()).isDeterministic()) {
				dtoIdx += offset;
			    } else {
				dtoIdx += Math.abs(offset-1) ;
				
			    }
			    
			    ((DTOScenario) ((BHTreeNode) newParentNode.getParent()).getUserObject()).addChildToPosition(periodDto, dtoIdx);
			    // add to new UI-Node...
			    draggedBHNode.removeFromParent();
			    ((DefaultTreeModel) target.getModel()).insertNodeInto(draggedBHNode, (BHTreeNode) newParentNode.getParent(), targetIdx);

			} catch (Exception e) {
			    e.printStackTrace();
			}
		    }

		    // do selection
		    TreePath treePath = new TreePath(draggedNode.getPath());
		    target.scrollPathToVisible(treePath);
		    target.setSelectionPath(treePath);

		    return true;
		}

	    } else

	    if (draggedBHNode.getUserObject() instanceof DTOScenario) {
		// handle move of Period
		if (action == DnDConstants.ACTION_MOVE) {
		    DTOScenario scenarioDto = (DTOScenario) draggedBHNode.getUserObject();

		    // Remove from DTO basis...
		    ((DTOProject) ((BHTreeNode) draggedBHNode.getParent()).getUserObject()).removeChild(scenarioDto);
		    // ...and from UI
		    draggedBHNode.removeFromParent();

		    // add to new UI-Node...
		    if (((BHTreeNode) newParentNode).getUserObject() instanceof DTOProject) {
			((DefaultTreeModel) target.getModel()).insertNodeInto(draggedBHNode, newParentNode, newParentNode.getChildCount());
			// ...and to DTO structure
			((DTOProject) ((BHTreeNode) newParentNode).getUserObject()).addChild(scenarioDto);
		    } else if (((BHTreeNode) newParentNode).getUserObject() instanceof DTOScenario) {
			((DefaultTreeModel) target.getModel()).insertNodeInto(draggedBHNode, (BHTreeNode) newParentNode.getParent(), ((BHTreeNode) newParentNode).getParent().getChildCount());
			// ...and to DTO structure
			((DTOProject) ((BHTreeNode) newParentNode.getParent()).getUserObject()).addChild(scenarioDto);
		    }

		}
	    }

	}

	return false;

    }

}
