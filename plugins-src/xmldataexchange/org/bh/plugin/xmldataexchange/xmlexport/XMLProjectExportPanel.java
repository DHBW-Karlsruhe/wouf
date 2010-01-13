package org.bh.plugin.xmldataexchange.xmlexport;

import info.clearthought.layout.TableLayout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.border.Border;

import org.bh.data.IDTO;
import org.bh.gui.swing.BHButton;
import org.bh.gui.swing.BHDescriptionLabel;
import org.bh.gui.swing.BHDescriptionTextArea;
import org.bh.gui.swing.BHSelectionList;
import org.bh.gui.swing.BHTextField;

public class XMLProjectExportPanel extends JPanel {
	
	public static final String KEY = "DXMLProjectExport";	

	private List<IDTO<?>> model = null;
	
	private BHTextField txtPath = null;
	
	private BHSelectionList secList = null;
	
	public XMLProjectExportPanel(List<IDTO<?>> model) {
		super();		
		this.model = model;		
		setLayout(new BorderLayout());
		
		// Create description area
		JPanel panDescr = createDescriptionPanel();	
		// Create selection area
		JPanel listPanel = createSelectionArea();	
		// Create action area (buttons)
		JPanel actionPanel = createActionArea();
		
		// Add all panels to the dialog
		add(panDescr, BorderLayout.NORTH);
		add(listPanel, BorderLayout.CENTER);
		add(actionPanel, BorderLayout.SOUTH);
		
	}

	
	private JPanel createActionArea() {
		// Panel on which the Buttons will be placed
		JPanel actionPanel = new JPanel(new BorderLayout());
		// Add seperator for optical seperation
		actionPanel.add(new JSeparator(), BorderLayout.NORTH);
		
		// Button panel for adding an empty border as margin
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.add(Box.createHorizontalGlue());
		
		// Cancel 
		BHButton btnCancel = new BHButton("Bcancel");
		
		// Start export
		BHButton btnExport = new BHButton("Mexport");
		btnExport.setAlignmentX(Component.RIGHT_ALIGNMENT);		
		
		buttonPanel.add(btnCancel);
		buttonPanel.add(btnExport);
		
		
		actionPanel.add(buttonPanel, BorderLayout.CENTER);
		return actionPanel;
	}


	private JPanel createSelectionArea() {
		
		// Create panel on wich the selection list and the file chooser area
		// will be placed
		JPanel listPanel = new JPanel(new BorderLayout());
		listPanel.setBorder(BorderFactory.createEmptyBorder(20, 15, 15, 15));
		
		
		// Create list with all available scenarios of the given project
		secList = new BHSelectionList(model.get(0).getChildren().toArray());		
		
		// An extra panel for file selection
		JPanel fileSelectionPanel = new JPanel();	
		fileSelectionPanel.setBorder(BorderFactory.createEmptyBorder(7,0,0,0));
		// 2 columns, 2 rows
		double size[][] = 
		{{0.7, 0.3}, {0.5, 0.5}};
		fileSelectionPanel.setLayout(new TableLayout(size));
			
		// Small label for instruction
		BHDescriptionLabel lblselExportPath = new BHDescriptionLabel("DXMLExportPathSelection");		
		fileSelectionPanel.add(lblselExportPath, "0,0");
		
		// Text field which will show the chosen path
		txtPath = new BHTextField("DXMLTxtExportImportPath", "");
		fileSelectionPanel.add(txtPath, "0,1");
		
		// Button to start the file choosing dialog
		BHButton btnChooseFile = new BHButton("Bbrowse");
		fileSelectionPanel.add(btnChooseFile, "1,1");
		
		listPanel.add(secList, BorderLayout.CENTER);
		listPanel.add(fileSelectionPanel, BorderLayout.SOUTH);
		return listPanel;
	}


	private JPanel createDescriptionPanel() {
		// Create description section
		JPanel panDescr = new JPanel();
		panDescr.setForeground(Color.white);
		panDescr.setLayout(new BorderLayout());
		
		// Text area with description
		BHDescriptionTextArea lblDescr = new BHDescriptionTextArea("DXMLExportDescription");		
		lblDescr.setFocusable(false);
		
		// Create border for that textarea
		Border marginBorder = BorderFactory.createEmptyBorder(15, 5, 15, 5);
		Border whiteBorder = BorderFactory.createLineBorder(Color.white);
		Border grayBorder = BorderFactory.createLineBorder(UIManager.getColor("controlDkShadow"));
		Border outerBorder = BorderFactory.createCompoundBorder(whiteBorder, grayBorder);		
		
		// Set border
		lblDescr.setBorder(BorderFactory.createCompoundBorder(outerBorder, marginBorder));
		
		panDescr.add(lblDescr, BorderLayout.CENTER);		
		return panDescr;
	}


	public BHTextField getTxtPath() {
		return txtPath;
	}


	public BHSelectionList getSecList() {
		return secList;
	}	

}

