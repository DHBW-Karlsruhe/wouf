package org.bh.gui.swing.importexport;

import info.clearthought.layout.TableLayout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.bh.data.IDTO;
import org.bh.gui.IBHComponent;
import org.bh.gui.swing.comp.BHButton;
import org.bh.gui.swing.comp.BHDescriptionLabel;
import org.bh.gui.swing.comp.BHDescriptionTextArea;
import org.bh.gui.swing.comp.BHSelectionList;
import org.bh.gui.swing.comp.BHTextField;
import org.bh.platform.PlatformController;

@SuppressWarnings("serial")
public class BHDefaultProjectExportPanel extends JPanel implements ActionListener {
	
	private BHSelectionList secList;
	private BHTextField txtPath;

	private IDTO<?> model;
	private BHButton btnExport;
	
	String fileDesc;
	String fileExt;

	public BHDefaultProjectExportPanel(IDTO<?> model, String fileDesc, String fileExt)
	{
		setLayout(new BorderLayout());
		this.model = model;
		this.fileDesc = fileDesc;
		this.fileExt = fileExt;
		
		JPanel descrPanel = createDescriptionPanel();		
		JPanel selPanel = createSelectionArea();		
		JPanel actionPanel = createActionArea();
						
		add(descrPanel, BorderLayout.NORTH);
		add(selPanel, BorderLayout.CENTER);
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
		
		// Start export
		btnExport = new BHButton("Mexport");
		btnExport.setAlignmentX(Component.RIGHT_ALIGNMENT);		
		btnExport.setEnabled(false);
		
		buttonPanel.add(btnCancel);
		buttonPanel.add(btnBack);
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
		Object[] children = null;
		if (model.getChildren() != null)
			children = model.getChildren().toArray();
		
		secList = new BHSelectionList(children);		
		Border secListBorder = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIManager.getColor("controlDkShadow")),
				BorderFactory.createEmptyBorder(5,5,10,5));
		secList.setBorder(secListBorder);	
		
		// An extra panel for file selection
		JPanel fileSelectionPanel = new JPanel();	
		fileSelectionPanel.setBorder(BorderFactory.createEmptyBorder(7,0,0,0));
		// 2 columns, 2 rows
		//double size[][] = {{0.7, 0.3}, {0.5, 0.5}};
		//fileSelectionPanel.setLayout(new TableLayout(size));
		fileSelectionPanel.setLayout(new GridBagLayout());
		// Small label for instruction
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		BHDescriptionLabel lblselExportPath = new BHDescriptionLabel("DExportPathSelection");		
		fileSelectionPanel.add(lblselExportPath, c);
		
		// Text field which will show the chosen path
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		txtPath = new BHTextField("DTxtExportImportPath", "");
		txtPath.setEditable(false);
		fileSelectionPanel.add(txtPath, c);
		
		// Button to start the file choosing dialog
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		BHButton btnChooseFile = new BHButton("Bbrowse");
		btnChooseFile.addActionListener(this);
		fileSelectionPanel.add(btnChooseFile, c);
		
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
		BHDescriptionTextArea lblDescr = new BHDescriptionTextArea("DExportDescription");		
		lblDescr.setFocusable(false);
		lblDescr.setToolTipText(null);
		
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


	public BHSelectionList getSecList() {
		return secList;
	}


	public BHTextField getTxtPath() {
		return txtPath;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		IBHComponent comp =  (IBHComponent) e.getSource();
		if (comp.getKey().equals("Bbrowse"))
		{
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			
			String strDefDir = PlatformController.preferences.get("lastExportDirectory", null);
			if (strDefDir != null)
			{
				File defDir = new File(strDefDir);
				fileChooser.setCurrentDirectory(defDir);
			}		
			
			fileChooser.setFileFilter(new FileNameExtensionFilter(fileDesc, fileExt));
			
			// add dummy filename
			File dummyFile = new File("businesshorizon_export." + fileExt);
			fileChooser.setSelectedFile(dummyFile);
			
			int returnVal = fileChooser.showSaveDialog(this);		
			
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				PlatformController.preferences.put("lastExportDirectory", fileChooser.getSelectedFile().getParent()); 
				String filePath = fileChooser.getSelectedFile().getAbsolutePath();
				if(!filePath.endsWith("." + fileExt)) {
					filePath = filePath + "." + fileExt;
				}
				txtPath.setText(filePath);
				btnExport.setEnabled(true);
			}
			
		}
	}

}
