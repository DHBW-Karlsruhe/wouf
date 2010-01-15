package org.bh.gui.swing;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

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
	
	ITranslator translator = Services.getTranslator();

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

		String colDef = "4px,p,4px,p,4px,p,4px,p,4px";
		String rowDef = "4px,p,4px,p,4px,p,4px";
		

		FormLayout layout = new FormLayout(colDef, rowDef);
		this.setLayout(layout);

		CellConstraints cons = new CellConstraints();

		this.add(this.getlDCFmethod(), cons.xywh(2, 2, 1, 1));
		this.add(this.getcbDCFmethod(), cons.xywh(4, 2, 1, 1));
		this.add(this.getlstochProcess(), cons.xywh(6, 2, 1, 1));
		this.add(this.getcbstochProcess(), cons.xywh(8, 2, 1, 1));
		
		this.add(this.getlStochasticKeysList(), cons.xywh(2, 4, 7, 1));
		this.add(this.getliStochasticKeysList(), cons.xywh(2, 6, 7, 1));
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
			liStochasticKeys = new BHSelectionList(DTOScenario.Key.STOCHASTIC_KEYS, new Integer[] {1,2,3,4,5,6,7,8,9});
			liStochasticKeys.setLayoutOrientation(JList.VERTICAL_WRAP);
			liStochasticKeys.setVisibleRowCount(3);
		}
		return liStochasticKeys;
	}

	/**
	 * Test main method.
	 */
	public static void main(String args[]) {

		JFrame test = new JFrame("Test for ViewStochasticData3");
		test.setContentPane(new BHStochasticInputForm());
		test.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		test.pack();
		test.show();
	}
}
