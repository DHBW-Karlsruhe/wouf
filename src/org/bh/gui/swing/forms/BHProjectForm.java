package org.bh.gui.swing.forms;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

import org.bh.gui.swing.comp.BHButton;
import org.bh.gui.swing.comp.BHDescriptionLabel;
import org.bh.gui.swing.forms.border.BHBorderFactory;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * This class contains the form for projects
 * 
 * @author Anton Kharitonov
 * @author Patrick Heinz
 * @version 0.4, 04.01.2010
 * 
 */
@SuppressWarnings("serial")
public final class BHProjectForm extends JPanel {
	
	public enum Key {
		/**
		 * 
		 */
		PROJECT_HEADDATA,

		
		CANNOT_CALCULATE_HINT,
		
		CALCULATING_IMAGE;

		@Override
		public String toString() {
		    return getClass().getName() + "." + super.toString();
		}
	}
	
	private JPanel pproject;
	private BHButton bcalculateDashBoard;
	private BHDescriptionLabel lCannotCalculateHint;
	private BHDescriptionLabel lCalculatingImage;
	private JPanel topPanel;
	private JPanel bottomPanel;


	ITranslator translator = Services.getTranslator();

	/**
	 * Constructor
	 */
	public BHProjectForm() {
		super(new FormLayout("4px,pref:grow,4px", "4px,fill:0px:grow,4px,pref,4px"));
		this.initialize();
	}

	/**
	 * Initialize method.
	 */
	private void initialize() {
		
		/*
		 * Structure of ProjectForm:
		 * The scenario form consists of 2 areas: 
		 * a big one top which shows all fields and a smaller one bottom
		 * which contains the button to show Dashboard. 
		 */
		
		topPanel = new JPanel();
		bottomPanel = new JPanel();
		
		/*
		 * build topPanel
		 */
		String colDef = "4px,pref:grow,4px";
		String rowDef = "4px,p,14px,p,4px";
		
		FormLayout topLayout = new FormLayout(colDef, rowDef);
		topPanel.setLayout(topLayout);

		CellConstraints cons = new CellConstraints();
		
		topPanel.add(this.getPproject(), cons.xywh(2, 2, 1, 1));
		JScrollPane scrollPane = new JScrollPane(topPanel);
		
		//add topPanel to ProjectForm
		this.add(scrollPane, cons.xywh(2, 2, 1, 1));

		
		
		/*
		 * build bottomPanel
		 */
		colDef = "4px,4px:grow,4px,min,4px,right:pref,4px";
		rowDef = "4px,p,4px";
		
		FormLayout bottomLayout = new FormLayout(colDef, rowDef);
		bottomPanel.setLayout(bottomLayout);

		bottomPanel.add(this.getCannotCalculateHint(), cons.xywh(2, 2, 1, 1));
		bottomPanel.add(this.getCalculatingImage(), cons.xywh(4, 2, 1, 1));
		bottomPanel.add(this.getBcalculateDashBoard(), cons.xywh(6, 2, 1, 1));
		
		//add topPanel to ScenarioForm
		this.add(bottomPanel, cons.xywh(2, 4, 1, 1));
		
	}

	
	private JPanel getPproject() {
		if (pproject == null) {
			pproject = new BHProjectInputForm();
			pproject.setBorder(BHBorderFactory.getInstacnce()
					.createTitledBorder(BHBorderFactory.getInstacnce().createEtchedBorder(EtchedBorder.LOWERED),BHProjectForm.Key.PROJECT_HEADDATA));
		}
		return pproject;
	}

	public BHButton getBcalculateDashBoard() {
		if (this.bcalculateDashBoard == null) {
			this.bcalculateDashBoard = new BHButton(BHScenarioForm.Key.CALCDASHBOARD);
		}
		return bcalculateDashBoard;
	}
	
	public BHDescriptionLabel getCannotCalculateHint() {
		if (lCannotCalculateHint == null) {
			lCannotCalculateHint = new BHDescriptionLabel(Key.CANNOT_CALCULATE_HINT);
			lCannotCalculateHint.setVisible(false);
		}
		return lCannotCalculateHint;
	}
	
	public BHDescriptionLabel getCalculatingImage() {
		if (lCalculatingImage == null) {
			lCalculatingImage = new BHDescriptionLabel(Key.CALCULATING_IMAGE);
		}
		return lCalculatingImage;
	}

}
