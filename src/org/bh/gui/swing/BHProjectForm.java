package org.bh.gui.swing;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

import org.apache.log4j.Logger;
import org.bh.platform.PlatformKey;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * This class contains the form for head-data
 * 
 * @author Anton Kharitonov
 * @author Patrick Heinz
 * @version 0.4, 04.01.2010
 * 
 */
@SuppressWarnings("serial")
public class BHProjectForm extends JPanel {
	
	public enum Key {
		/**
		 * 
		 */
		PROJECT_HEADDATA,

		
		CANNOT_CALCULATE_HINT;

		public String toString() {
		    return getClass().getName() + "." + super.toString();
		}
	}
	
	private JPanel pproject;
//	private JPanel pprocess;
	private BHButton bcalculate;
	private BHDescriptionLabel lCannotCalculateHint;
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
		
//		/*
//		 * Structure of ProjectForm:
//		 * The scenario form consists of 2 areas: 
//		 * a big one top which shows all fields and a smaller one bottom
//		 * which contains A) the button to start calculations and B) a short Info-Bar 
//		 * with shortview of calculated UW with a button to start "slider view" (which holds
//		 * all analysis information).
//		 */
		
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
//		topPanel.add(this.getPprocess(), cons.xywh(2, 4, 1, 1));
		JScrollPane scrollPane = new JScrollPane(topPanel);
		
		//add topPanel to ScenarioForm
		this.add(scrollPane, cons.xywh(2, 2, 1, 1));

		
		
		/*
		 * build bottomPanel
		 */
		colDef = "4px,4px:grow,4px,right:pref,4px";
		rowDef = "4px,p,4px";
		
		FormLayout bottomLayout = new FormLayout(colDef, rowDef);
		bottomPanel.setLayout(bottomLayout);

		bottomPanel.add(this.getCannotCalculateHint(), cons.xywh(2, 2, 1, 1));
		bottomPanel.add(this.getBcalculate(), cons.xywh(4, 2, 1, 1));
		
		//add topPanel to ScenarioForm
		this.add(bottomPanel, cons.xywh(2, 4, 1, 1));
		
	}

	
	private JPanel getPproject() {
		if (pproject == null) {
			pproject = new BHProjectInputForm();
			//TODO add locale change handler
			pproject.setBorder(BorderFactory
					.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),translator.translate(BHProjectForm.Key.PROJECT_HEADDATA)));
		}
		return pproject;
	}

	public BHButton getBcalculate() {
		if (this.bcalculate == null) {
			this.bcalculate = new BHButton(PlatformKey.CALCDASHBOARD);
		}
		return bcalculate;
	}
	
	public BHDescriptionLabel getCannotCalculateHint() {
		if (lCannotCalculateHint == null) {
			lCannotCalculateHint = new BHDescriptionLabel(Key.CANNOT_CALCULATE_HINT);
			lCannotCalculateHint.setVisible(false);
		}
		return lCannotCalculateHint;
	}

}
