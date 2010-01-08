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
	private JTabbedPane tabbedPane;
	



	ITranslator translator = Services.getTranslator();

	/**
	 * Constructor
	 */
	public BHScenarioForm(BHScenarioForm.Type type) {
		super(new GridLayout(1, 1));
		this.initialize(type);
	}

	/**
	 * Initialize method.
	 */
	private void initialize(BHScenarioForm.Type type) {

		//create TabPane
		tabbedPane = new JTabbedPane();

		
		//create JPanel for first Tab
		JPanel generalTabPanel = new JPanel();
		String rowDef = "4px,p,14px,p,14px,p,14px,p,4px";
		String colDef = "4px,pref:grow,4px";

		FormLayout layout = new FormLayout(colDef, rowDef);
		generalTabPanel.setLayout(layout);

		CellConstraints cons = new CellConstraints();
		
		generalTabPanel.add(this.getPscenario(), cons.xywh(2, 2, 1, 1));
		generalTabPanel.add(this.getPprocess(type), cons.xywh(2, 4, 1, 1));
		generalTabPanel.add(this.getBcalculate(), cons.xywh(2, 8, 1, 1, "right, bottom"));
		
		//add generalTabPanel to TabPane
		tabbedPane.addTab("Allgemeine Daten", generalTabPanel);
		
		//add TabPane to ScenarioForm
		this.add(tabbedPane);
		
	}

	// TODO @Anton add missing keys etc. and translations, change hard coded
	// values to keys
	// TODO Check after "OK" if there is a DCF method chosen
	
	public JPanel getPprocess(BHScenarioForm.Type type) {
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
	
	public JPanel getPscenario() {
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
	
//	@Override
//	public void resize(Dimension d){
//		super.resize(d);
//		tabbedPane.setPreferredSize(new Dimension(this.getSize().height-15,this.getSize().width-15));
//	}
	

	
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
