package org.bh.gui.swing;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import org.bh.data.DTOProject;
import org.bh.data.IDTO;
import org.bh.platform.IImportExport;
import org.bh.platform.i18n.BHTranslator;


public class BHProjectDataExchangeDialog extends JDialog implements ActionListener {

	/**
	 * First panel with the format selection
	 */
	private JPanel formatSelectionPanel = new JPanel();
			
	/**
	 * Key for the format selection panel
	 */
	final static String FORMAT_CHOOSER_PANEL = "formatChooser";
	
	/**
	 * Panel on which the plugin can implement its components
	 */
	private JPanel pluginPanel = new JPanel();
	
	/**
	 * Key for the plugin panel
	 */
	final static String PLUGIN_PANEL = "pluginPanel";
	
	/**
	 * Description label 
	 */
	private BHDescriptionTextArea formatChooseDescr = null;
	
	/**
	 * List which contains available file formats
	 */
	private JList availFormatsList = null;
	
	/**
	 * Indicates which panel is currently visible
	 */
	private String visiblePanel = FORMAT_CHOOSER_PANEL;
	
	/**
	 * ActionListener of the plug-in
	 */
	private ActionListener pluginActionListener = null;
	
	/**
	 * Data to be exported / imported
	 */
	private IDTO<?> model = null;
	
	/**
	 * Determines the action to be performed after clicking on continue
	 * Possible values are listed in IImportExport
	 */
	private int action = -1;

	/**
	 * Panel with CardLayout
	 */
	private JPanel mainPanel;
	
	/**
	 * Creates a dialog with which project can be imported and
	 * exported.
	 * 
	 * After initializing you have to call
	 * setAction();
	 * setModel();
	 * setAvailablePlugins();
	 * 
	 * @param owner
	 * @param modal
	 */
	public BHProjectDataExchangeDialog(Frame owner, boolean modal) {
		super(owner, modal);
		
		// Set some properties
		CardLayout layout = new CardLayout();
		mainPanel = new JPanel(layout);
		
		setSize(400, 500);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		
		// Create format selection panel
		createFormatChooserPanel();
				
		mainPanel.add(formatSelectionPanel, FORMAT_CHOOSER_PANEL);
		mainPanel.add(pluginPanel, PLUGIN_PANEL);
		
		add(mainPanel);
		addActionListener(mainPanel);		
	}	
	
	/**
	 * Creates the panel in which the user can select the 
	 * export format
	 */
	private void createFormatChooserPanel() {
		formatSelectionPanel.setLayout(new BorderLayout());
		
		JPanel descrPanel = createDescriptionPanel();
		
		JPanel formatListPanel = createAvailFormatsListPanel();
		
		JPanel actionPanel = createFormatSelActionPanel();
		
		formatSelectionPanel.add(descrPanel, BorderLayout.NORTH);
		formatSelectionPanel.add(formatListPanel, BorderLayout.CENTER);
		formatSelectionPanel.add(actionPanel, BorderLayout.SOUTH);			
	}	
	
	/**
	 * Creates a panel in which the description label
	 * will be created
	 * @return
	 */
	private JPanel createDescriptionPanel() {
		// Create description section
		JPanel panDescr = new JPanel();
		panDescr.setForeground(Color.white);
		panDescr.setLayout(new BorderLayout());
		
		// Text area with description
		formatChooseDescr = new BHDescriptionTextArea("DProjectDataExchangeDescr");		
		formatChooseDescr.setFocusable(false);
		
		// Create border for that textarea
		Border marginBorder = BorderFactory.createEmptyBorder(15, 5, 15, 5);
		Border whiteBorder = BorderFactory.createLineBorder(Color.white);
		Border grayBorder = BorderFactory.createLineBorder(UIManager.getColor("controlDkShadow"));
		Border outerBorder = BorderFactory.createCompoundBorder(whiteBorder, grayBorder);		
		
		// Set border
		formatChooseDescr.setBorder(BorderFactory.createCompoundBorder(outerBorder, marginBorder));
		
		panDescr.add(formatChooseDescr, BorderLayout.CENTER);		
		return panDescr;
	}
	
	/**
	 * Creates the panel in which the buttons for 
	 * navigation will be created
	 * @return
	 */
	private JPanel createFormatSelActionPanel() {
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
		BHButton btnExport = new BHButton("Bcontinue");
		btnExport.setAlignmentX(Component.RIGHT_ALIGNMENT);		
		
		buttonPanel.add(btnCancel);
		buttonPanel.add(btnExport);
		
		
		actionPanel.add(buttonPanel, BorderLayout.CENTER);
		return actionPanel;
	}
	
	/**
	 * Creates the panel in which the list with export formats
	 * will be created
	 * @return
	 */
	private JPanel createAvailFormatsListPanel() {
		// Create panel on wich the selection list and the file chooser area
		// will be placed
		JPanel listPanel = new JPanel(new BorderLayout());
		listPanel.setBorder(BorderFactory.createEmptyBorder(20, 15, 15, 15));	
		
		availFormatsList = new JList();
		availFormatsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Border listBorder = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIManager.getColor("controlDkShadow")),
				BorderFactory.createEmptyBorder(5,5,10,5));
		availFormatsList.setBorder(listBorder);
		
		listPanel.add(availFormatsList, BorderLayout.CENTER);		
		
		return listPanel;
	}

	/**
	 * Sets the available plug-ins.
	 * @param plugins
	 */
	public void setAvailablePlugins(Map<String, IImportExport> plugins)
	{
		DefaultListModel model = new DefaultListModel();
		if (plugins != null && plugins.size() > 0)
			for (IImportExport plugin : plugins.values())
				model.addElement(plugin);
		else
			model.addElement(BHTranslator.getInstance().translate("DNoDataExchangeFormatsFound"));
		availFormatsList.setModel(model);
	}
	
	/**
	 * Sets the description of the file chooser panel
	 * @param description The text shown in the header of the dialog
	 */
	public void setDescription(String description)
	{		
		formatChooseDescr.setText(description);		
	}	
	
	/**
	 * Creates a default project export panel as plug-in panel
	 */
	public void setDefaulExportProjectPanel()
	{
		setPluginPanel(new BHDefaultProjectExportPanel(model));
	}
	

	/**
	 * Adds for each button an action listener to this component
	 * @param panel
	 */
	private void addActionListener(JPanel panel)
	{
		for (Component comp : panel.getComponents())
		{
			if (comp instanceof JPanel)
				addActionListener((JPanel) comp);			
			else if (comp instanceof BHButton)
				((JButton)comp).addActionListener(this);
		}
	}
		
	
	/**
	 * An action has been performed
	 * If the format selection panel is visible this method
	 * handles the action
	 * If the plug-in panel is visible the event will be deleted
	 * to the registered ActionListener of the plug-in
	 */
	@Override
	public void actionPerformed(ActionEvent e) {		
		if (visiblePanel.equals(FORMAT_CHOOSER_PANEL))
		{
			IBHComponent comp =  (IBHComponent) e.getSource();
			// Continue has been pressed -> show Plugin panel
			if (comp.getKey().equals("Bcontinue"))
			{
				if (availFormatsList.getSelectedValue() != null && model != null)
				{
					if (action == IImportExport.EXP_PROJECT)
						((IImportExport)availFormatsList.getSelectedValue()).exportProject((DTOProject) model, this);
											
				}
				showPluginPanel();				
			}				
			else if (comp.getKey().equals("Bcancel"))
			{
				dispose();
			}
		}
		else
		{
			if (pluginActionListener != null)
				pluginActionListener.actionPerformed(e);
		}		
	}
	
	/**
	 * Adds an ActionListener which will be called if the plug-in 
	 * panel is visible
	 * @param pluginActionListener
	 */
	public void setPluginActionListener(ActionListener pluginActionListener) {
		this.pluginActionListener = pluginActionListener;
	}

	
	public IDTO<?> getModel() {
		return model;
	}

	public void setModel(IDTO<?> model) {
		this.model = model;
	}

	public int getAction() {
		return action;
	}

	/**
	 * Sets the action which will be called after clicking on
	 * "continue"
	 * @param action
	 */
	public void setAction(int action) {
		this.action = action;
	}
	
	/**
	 * Changes to the plug-in panel
	 */
	public void showPluginPanel()
	{
		((CardLayout)mainPanel.getLayout()).show(mainPanel, PLUGIN_PANEL);
	}
	
	/**
	 * Sets the plug-in panel
	 * @param pluginPanel
	 */
	public void setPluginPanel(JPanel pluginPanel) {
		if (pluginPanel != null)
		{
			mainPanel.remove(pluginPanel);
			this.pluginPanel = pluginPanel;
			mainPanel.add(pluginPanel, PLUGIN_PANEL);
			addActionListener(pluginPanel);
		}
		
	}
	
	
}
