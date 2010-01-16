package org.bh.gui.swing;

import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.bh.data.DTOScenario;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * This class contains the form for stochastic processes
 * 
 * @author Anton Kharitonov
 * @author Patrick Heinz
 * @version 0.2, 03.01.2010
 * 
 */
public class BHStochasticInputForm extends JPanel {

	private BHDescriptionLabel lstochprocess;
	private BHDescriptionLabel ldcfMethod;
	
	private BHComboBox cbstochprocess;
	private BHComboBox cbdcfMethod;
	
	private BHDescriptionLabel lStochasticKeys;
	private BHSelectionList liStochasticKeys;
	
	private BHButton bCalcParameters;
	private BHButton bResetParameters;
	private Component pParameters;
	
	ITranslator translator = Services.getTranslator();
	
	public enum Key {
		CALC_PARAMETERS,
		RESET_PARAMETERS;
		
		@Override
		public String toString() {
		    return getClass().getName() + "." + super.toString();
		}
	}

	/**
	 * Constructor.
	 */
	public BHStochasticInputForm() {
		this.initialize();
	}

	/**
	 * Initialize method.
	 */
	private void initialize() {

		String colDef = "4px,p,4px,p,4px,p,4px,p,0px:grow,4px";
		String rowDef = "4px,p,4px,p,4px,80px,10px,p,4px,p,4px";
		

		FormLayout layout = new FormLayout(colDef, rowDef);
		this.setLayout(layout);

		CellConstraints cons = new CellConstraints();

		this.add(this.getlDCFmethod(), cons.xywh(2, 2, 1, 1));
		this.add(this.getcbDCFmethod(), cons.xywh(4, 2, 1, 1));
		this.add(this.getlstochProcess(), cons.xywh(6, 2, 1, 1));
		this.add(this.getcbstochProcess(), cons.xywh(8, 2, 1, 1));
		
		this.add(this.getlStochasticKeysList(), cons.xywh(2, 4, 8, 1));
		this.add(new JScrollPane(this.getliStochasticKeysList()), cons.xywh(2, 6, 8, 1));
		
		this.add(this.getbCalcParameters(), cons.xywh(2, 8, 8, 1));
		this.add(this.getbResetParameters(), cons.xywh(2, 8, 8, 1));
		this.getbResetParameters().setVisible(false);
	}

	// TODO add missing label keys and translations, change hard coded values to
	// keys
	
	//TODO Overwork Javadoc
	/**
	 * Getter method for component lDCFchoise.
	 * 
	 * @return the initialized component
	 */
	public BHDescriptionLabel getlDCFmethod() {
		if (this.ldcfMethod == null)
			this.ldcfMethod = new BHDescriptionLabel(DTOScenario.Key.DCF_METHOD);
		return this.ldcfMethod;
	}
	
	
	public BHComboBox getcbDCFmethod() {
		if (this.cbdcfMethod == null) {
			this.cbdcfMethod = new BHComboBox(DTOScenario.Key.DCF_METHOD);
		}
		return this.cbdcfMethod;
	}
	
	
	public BHComboBox getcbstochProcess() {
		if (this.cbstochprocess == null) {
			this.cbstochprocess = new BHComboBox(DTOScenario.Key.STOCHASTIC_PROCESS);
		}
		return this.cbstochprocess;
	}
	/**
	 * Getter method for component lstochProcess.
	 * 
	 * @return the initialized component
	 */
	public BHDescriptionLabel getlstochProcess() {

		if (this.lstochprocess == null) {
			this.lstochprocess = new BHDescriptionLabel(DTOScenario.Key.STOCHASTIC_PROCESS);
		}

		return this.lstochprocess;
	}
	
	public BHDescriptionLabel getlStochasticKeysList() {
		if (lStochasticKeys == null) {
			lStochasticKeys = new BHDescriptionLabel(DTOScenario.Key.STOCHASTIC_KEYS);
		}
		return lStochasticKeys;
	}
	
	public BHSelectionList getliStochasticKeysList() {
		if (liStochasticKeys == null) {
			liStochasticKeys = new BHSelectionList(DTOScenario.Key.STOCHASTIC_KEYS);
			liStochasticKeys.setDefaultValue(false);
		}
		return liStochasticKeys;
	}
	
	public BHButton getbCalcParameters() {
		if (bCalcParameters == null) {
			bCalcParameters = new BHButton(Key.CALC_PARAMETERS);
		}
		return bCalcParameters;
	}
	
	public BHButton getbResetParameters() {
		if (bResetParameters == null) {
			bResetParameters = new BHButton(Key.RESET_PARAMETERS);
		}
		return bResetParameters;
	}
	
	public void setParametersPanel(Component component) {
		removeParametersPanel();
		pParameters = component;
		CellConstraints cons = new CellConstraints();
		add(pParameters, cons.xywh(2, 10, 8, 1));
		revalidate();
	}
	
	public void removeParametersPanel() {
		if (pParameters != null) {
			remove(pParameters);
			revalidate();
		}
	}
	// TODO remove
//	/**
//	 * Test main method.
//	 */
//	public static void main(String args[]) {
//
//		JFrame test = new JFrame("Test for ViewStochasticData3");
//		test.setContentPane(new BHStochasticInputForm());
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
