package org.bh.gui.swing;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
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
public class BHScenarioForm extends JPanel {
	
	public enum Type{
		/**
		 * 
		 */
		STOCHASTIC,
		
		/**
		 * 
		 */
		DETERMINISTIC;
	}
	
	public enum Key {
		/**
		 * 
		 */
		SCENARIO_HEADDATA,
		
		/**
		 * 
		 */
		PROCESS_DATA;

		public String toString() {
		    return getClass().getName() + "." + super.toString();
		}
	}
	
	private JPanel pscenario;
	private JPanel pprocess;
	private BHButton bcalculate;
	private JPanel topPanel;
	private JPanel bottomPanel;


	ITranslator translator = Services.getTranslator();

	/**
	 * Constructor
	 */
	public BHScenarioForm(BHScenarioForm.Type type) {
		super(new GridLayout(2, 1));
		this.initialize(type);
	}

	/**
	 * Initialize method.
	 */
	private void initialize(BHScenarioForm.Type type) {
		
		/*
		 * Structure of ScenarioForm:
		 * The scenario form consists of 2 areas: 
		 * a big one top which shows all fields and a smaller one bottom
		 * which contains A) the button to start calculations and B) a short Info-Bar 
		 * with shortview of calculated UW with a button to start "slider view" (which holds
		 * all analysis information).
		 */
		
		topPanel = new JPanel();
		bottomPanel = new JPanel();
		
		/*
		 * build topPanel
		 */
		String colDef = "4px,pref:grow,4px";
		String rowDef = "4px,p,14px,p,14px,p,14px,p,4px";
		
		FormLayout topLayout = new FormLayout(colDef, rowDef);
		topPanel.setLayout(topLayout);

		CellConstraints topCons = new CellConstraints();
		
		topPanel.add(this.getPscenario(), topCons.xywh(2, 2, 1, 1));
		topPanel.add(this.getPprocess(type), topCons.xywh(2, 4, 1, 1));
		
		//add topPanel to ScenarioForm
		this.add(topPanel);

		
		
		/*
		 * build bottomPanel
		 */
		colDef = "4px,pref:grow,4px";
		rowDef = "4px,p,4px,p,4px";
		
		FormLayout bottomLayout = new FormLayout(colDef, rowDef);
		bottomPanel.setLayout(bottomLayout);

		CellConstraints bottomCons = new CellConstraints();
		bottomPanel.add(this.getBcalculate(), bottomCons.xywh(2, 2, 1, 1, "right, bottom"));
		
		//add topPanel to ScenarioForm
		this.add(bottomPanel);
		
	}

	// TODO @Anton add missing keys etc. and translations, change hard coded
	// values to keys
	// TODO Check after "OK" if there is a DCF method chosen
	
	private JPanel getPprocess(BHScenarioForm.Type type) {
		switch (type){
		case STOCHASTIC:
			pprocess = new BHStochasticProcessForm();
			Logger.getLogger(BHScenarioForm.class).debug("--StochasticProcessForm is chosen");
			break;
			
		case DETERMINISTIC:
			pprocess = new BHDeterministicProcessForm();
			Logger.getLogger(BHScenarioForm.class).debug("--DeterministicProcessForm is chosen");
			break;
			
		default:
			pprocess = new BHDeterministicProcessForm();
		}
		pprocess.setBorder(BorderFactory
				.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),translator.translate(BHScenarioForm.Key.PROCESS_DATA)));
		return pprocess;
	}
	
	public JPanel getProcessForm(){
		return pprocess;
	}
	
	private JPanel getPscenario() {
		if (pscenario == null) {
			pscenario = new BHScenarioHeadForm();
			//TODO String raus!
			pscenario.setBorder(BorderFactory
					.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),translator.translate(BHScenarioForm.Key.SCENARIO_HEADDATA)));
		}
		return pscenario;
	}

	public BHButton getBcalculate() {
		if (this.bcalculate == null) {
			this.bcalculate = new BHButton(PlatformKey.CALCSHAREHOLDERVALUE);
		}
		return bcalculate;
	}
	
	

	
//	// TODO remove main later
//	/**
//	 * Test main method.
//	 */
//	@SuppressWarnings("deprecation")
//	public static void main(String args[]) {
//
//		JFrame test = new JFrame("Test for ViewHeadData1");
//		test.setContentPane(new BHScenarioHeadForm());
//		test.addWindowListener(new WindowAdapter() {
//			@Override
//			public void windowClosing(WindowEvent e) {
//				System.exit(0);
//			}
//		});
//		test.pack();
//		test.show();
//	}
}
