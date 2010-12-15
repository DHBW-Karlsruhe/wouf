package org.bh.gui.swing.importexport;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.border.Border;

import org.bh.gui.swing.comp.BHButton;
import org.bh.gui.swing.comp.BHDescriptionLabel;
import org.bh.gui.swing.comp.BHDescriptionTextArea;
import org.bh.gui.swing.comp.BHSelectionList;
import org.bh.gui.swing.comp.BHTextField;

@SuppressWarnings("serial")
public class BHDefaultProjectImportPanel extends JPanel {
	
	private BHTextField txtPath = null;
	
	private BHSelectionList secList = null;

	private BHButton btnImport;	
	GridBagConstraints d;
	public BHDefaultProjectImportPanel() {
		super();
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
		
		// Back 
		BHButton btnBack = new BHButton("Bback");
		
		// Start Import
		btnImport = new BHButton("Mimport");
		btnImport.setEnabled(false);
		btnImport.setAlignmentX(Component.RIGHT_ALIGNMENT);		
		
		buttonPanel.add(btnCancel);
		buttonPanel.add(btnBack);
		buttonPanel.add(btnImport);
		
		
		actionPanel.add(buttonPanel, BorderLayout.CENTER);
		return actionPanel;
	}


	private JPanel createSelectionArea() {
		
		// Create panel on wich the selection list and the file chooser area
		// will be placed
		JPanel listPanel = new JPanel(new BorderLayout());
		listPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 15, 15));
		
		
		// Create list with all available scenarios of the given project
		secList = new BHSelectionList(null);		
		
		// An extra panel for file selection
		JPanel fileSelectionPanel = new JPanel();	
		fileSelectionPanel.setBorder(BorderFactory.createEmptyBorder(0,0,7,0));
		fileSelectionPanel.setLayout(new GridBagLayout());
		d = new GridBagConstraints();
		// Small label for instruction
		BHDescriptionLabel lblselImportPath = new BHDescriptionLabel("DImportPathSelection");	
		
		d.fill = GridBagConstraints.HORIZONTAL;
		d.gridx = 0;
		d.gridy = 0;
		d.weightx = 0.7;
		d.weighty = 0.5;
		d.ipady = 10;
		fileSelectionPanel.add(lblselImportPath, d);
		
		// Text field which will show the chosen path
		txtPath = new BHTextField("DTxtExportImportPath", "");
		d.fill = GridBagConstraints.HORIZONTAL;
		d.gridx = 0;
		d.gridy = 1;
		d.weightx = 0.7;
		d.weighty = 0.5;
		d.ipady = 0;
		fileSelectionPanel.add(txtPath, d);
		
		// Button to start the file choosing dialog
		BHButton btnChooseFile = new BHButton("Bbrowse");	
		d.fill = GridBagConstraints.HORIZONTAL;
		d.gridx = 1;
		d.gridy = 1;
		d.weightx = 0.3;
		d.weighty = 0.5;
		fileSelectionPanel.add(btnChooseFile, d);	
		
		listPanel.add(fileSelectionPanel, BorderLayout.NORTH);
		listPanel.add(secList, BorderLayout.CENTER);
		
		return listPanel;
	}


	private JPanel createDescriptionPanel() {
		// Create description section
		JPanel panDescr = new JPanel();
		panDescr.setForeground(Color.white);
		panDescr.setLayout(new BorderLayout());
		
		// Text area with description
		BHDescriptionTextArea lblDescr = new BHDescriptionTextArea("DXMLImportDescription");		
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
	
	public BHButton getBtnImport() {
		return btnImport;
	}



}
