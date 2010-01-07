package org.bh.plugin.xmldataexchange.xmlexport;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.bh.gui.swing.BHDescriptionTextArea;
import org.bh.plugin.xmldataexchange.XMLDataExchangeCheckNode;
import org.bh.plugin.xmldataexchange.XMLDataExchangeCheckRenderer;

public class XMLDataExportPanel extends JPanel {

	public XMLDataExportPanel() {
		super();
		
		setLayout(new BorderLayout());
		
		// Create description section
		JPanel panDescr = new JPanel();
		panDescr.setForeground(Color.white);
		panDescr.setLayout(new BorderLayout());
		
				
		BHDescriptionTextArea lblDescr = new BHDescriptionTextArea("DXMLExportDescription");		
		lblDescr.setFocusable(false);
		
		Border marginBorder = BorderFactory.createEmptyBorder(15, 5, 15, 5);
		Border whiteBorder = BorderFactory.createLineBorder(Color.white);
		Border grayBorder = BorderFactory.createLineBorder(Color.gray);
		Border outerBorder = BorderFactory.createCompoundBorder(whiteBorder, grayBorder);		
		
		lblDescr.setBorder(BorderFactory.createCompoundBorder(outerBorder, marginBorder));
		panDescr.add(lblDescr, BorderLayout.CENTER);
		panDescr.setVisible(true);
		add(panDescr, BorderLayout.NORTH);
		
		XMLDataExchangeCheckNode node1 = new XMLDataExchangeCheckNode("Project");
		XMLDataExchangeCheckNode node2 = new XMLDataExchangeCheckNode("Node 2");
		XMLDataExchangeCheckNode node4 = new XMLDataExchangeCheckNode("Node 2");
		node1.add(node2);
		node1.add(node4);
		
		JTree tree = new JTree(node1);
		tree.setCellRenderer(new XMLDataExchangeCheckRenderer());
		tree.getSelectionModel().setSelectionMode(
			      TreeSelectionModel.SINGLE_TREE_SELECTION
			    );
		tree.addMouseListener(new NodeSelectionListener(tree));
		
		add(tree);
		
		
		
		/*		
		BHLabel lblPath = new BHDescriptionLabel("exportPath");
		BHTextField txtPath = new BHTextField("txtExportPath", "                              ");
		txtPath.setEnabled(false);
		
		BHButton btnChooseFile = new BHButton("btnExportChooseFile");
		BHButton btnDoExport = new BHButton("btnExport");
		
		add(lblPath);
		add(txtPath);
		add(btnChooseFile);
		add(btnDoExport);*/
	}
	
	
	class NodeSelectionListener extends MouseAdapter {
		JTree tree;
	    
	    NodeSelectionListener(JTree tree) {
	      this.tree = tree;
	    }
	    
	    public void mouseClicked(MouseEvent e) {
	    	int x = e.getX();
	        int y = e.getY();
	        int row = tree.getRowForLocation(x, y);
	        TreePath  path = tree.getPathForRow(row);
	        //TreePath  path = tree.getSelectionPath();
	        if (path != null) {
	        	XMLDataExchangeCheckNode node = (XMLDataExchangeCheckNode)path.getLastPathComponent();
	        	boolean isSelected = ! (node.isSelected());
	        	node.setSelected(isSelected);
	        	((DefaultTreeModel)tree.getModel()).nodeChanged(node);
	        }
	        
	          
	    }
	}

}

