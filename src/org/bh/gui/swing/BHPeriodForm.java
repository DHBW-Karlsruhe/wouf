package org.bh.gui.swing;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
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
 * @version 0.1, 13.01.2010
 * 
 */
@SuppressWarnings("serial")
public class BHPeriodForm extends JPanel {
	
	
	public enum Key {
		/**
		 * 
		 */
		PERIOD_HEADDATA;

		public String toString() {
		    return getClass().getName() + "." + super.toString();
		}
	}
	
	private JPanel pperiod;
	private JPanel pvalues;

	ITranslator translator = Services.getTranslator();

	/**
	 * Constructor
	 */
	public BHPeriodForm() {
//		super(new FormLayout("4px,pref:grow,4px", "4px,fill:200px:grow,4px,pref,4px"));
		this.initialize();
	}

	/**
	 * Initialize method.
	 */
	private void initialize() {
		
		String colDef = "4px,pref:grow,4px";
		String rowDef = "4px,p,14px,p,4px";
		
		FormLayout layout = new FormLayout(colDef, rowDef);
		this.setLayout(layout);

		CellConstraints cons = new CellConstraints();
		
		this.add(this.getPperiod(), cons.xywh(2, 2, 1, 1));
		this.add(this.getPvalues(), cons.xywh(2, 4, 1, 1));
		
	}

	
	private JPanel getPvalues() {
		if (pvalues == null){
			pvalues = new JPanel();
		}
		return pvalues;
	}
	
	public void setPvalues(JPanel periodValues) {
		this.pvalues = periodValues;
		this.initialize();
	}

	public JPanel getProcessForm(){
		
		return pvalues;
	}
	
	public JPanel getPperiod() {
		if (pperiod == null) {
			pperiod = new BHPeriodHeadForm();
			//TODO add locale change handler
			pperiod.setBorder(BorderFactory
					.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),translator.translate(BHPeriodForm.Key.PERIOD_HEADDATA)));
		}
		return pperiod;
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
