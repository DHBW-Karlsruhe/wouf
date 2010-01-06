package org.bh.gui.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;

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
public class BHScenarioHeadForm extends JPanel {
	
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
	
	
	private JPanel pscenario;
	private JPanel pprocess;
	private BHButton bcalculate;
	public int processChoice;




	ITranslator translator = Services.getTranslator();

	/**
	 * Constructor
	 */
	public BHScenarioHeadForm(BHScenarioHeadForm.Type type) {
		this.initialize(type);
	}

	/**
	 * Initialize method.
	 */
	private void initialize(BHScenarioHeadForm.Type type) {

		String rowDef = "4px,p,14px,p,14px,p,14px,p,4px";
		String colDef = "4px,pref:grow,4px";

		FormLayout layout = new FormLayout(colDef, rowDef);
		this.setLayout(layout);

		CellConstraints cons = new CellConstraints();
		
		this.add(this.getPscenario(), cons.xywh(2, 2, 1, 1));
		this.add(this.getPprocess(type), cons.xywh(2, 4, 1, 1));
		this.add(this.getBcalculate(), cons.xywh(2, 8, 1, 1, "right, bottom"));
	}

	// TODO @Anton add missing keys etc. and translations, change hard coded
	// values to keys
	// TODO Check after "OK" if there is a DCF method chosen
	
	public JPanel getPprocess(BHScenarioHeadForm.Type type) {
		switch (type){
		case STOCHASTIC:
			pprocess = new BHStochasticProcessForm();
			break;
			
		case DETERMINISTIC:
			pprocess = new BHDeterministicProcessForm();
			break;
			
		default:
			pprocess = new BHDeterministicProcessForm();
		}
		
		return pprocess;
	}

	public JPanel getPscenario() {
		if (pscenario == null) {
			pscenario = new BHScenarioForm();
			pscenario.setBorder(BorderFactory
					.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),"Szenariokopfdaten"));
		}
		return pscenario;
	}

	public BHButton getBcalculate() {
		if (this.bcalculate == null) {
			this.bcalculate = new BHButton("TempCalcUw");
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
