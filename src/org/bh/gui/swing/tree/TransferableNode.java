package org.bh.gui.swing.tree;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.util.Arrays;

import javax.swing.tree.DefaultMutableTreeNode;
 
public class TransferableNode implements Transferable {
	public static final DataFlavor NODE_FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType, "Node");
	private DefaultMutableTreeNode node;
	private DataFlavor[] flavors = { NODE_FLAVOR };
 
	public TransferableNode(DefaultMutableTreeNode nd) {
		node = nd;
	}  
 
	public synchronized Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
		if (flavor == NODE_FLAVOR) {
			return node;
		}
		throw new UnsupportedFlavorException(flavor);			
	}
 
	public DataFlavor[] getTransferDataFlavors() {
		return flavors;
	}
 
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return Arrays.asList(flavors).contains(flavor);
	}
}
