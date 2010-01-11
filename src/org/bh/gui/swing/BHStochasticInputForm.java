package org.bh.gui.swing;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.bh.data.DTOScenario;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;
import org.bh.validation.VRIsBetween;
import org.bh.validation.VRIsInteger;
import org.bh.validation.VRIsPositive;
import org.bh.validation.VRMandatory;
import org.bh.validation.ValidationRule;

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
	
	private JComboBox cbstochprocess;
	private BHComboBox cbdcfMethod;
	
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

		String colDef = "2dlu:grow(0.3),2dlu,right:pref,2dlu,pref,max(40dlu;pref),2dlu,left:5dlu,12dlu:grow(0.4),pref,2dlu,pref,2dlu,right:pref,2dlu,pref,pref,2dlu,pref,2dlu,pref,2dlu:grow(0.3)";
		String rowDef = "2dlu,p,10dlu,p,2dlu,p,12dlu,p,2dlu,p,2dlu";
		

		FormLayout layout = new FormLayout(colDef, rowDef);
		this.setLayout(layout);

		CellConstraints cons = new CellConstraints();

		this.add(this.getlDCFmethod(), cons.xywh(3, 2, 1, 1));
		this.add(this.getcbDCFmethod(), cons.xywh(6, 2, 3, 1));
		this.add(this.getlstochProcess(), cons.xywh(3, 6, 1, 1));
//		this.add(this.getlrange(), cons.xywh(3, 8, 1, 1));
//		this.add(this.gettfrange(), cons.xywh(6, 8, 1, 1));
//		this.add(this.getlprobab(), cons.xywh(3, 10, 1, 1));
//		this.add(this.gettfprobab(), cons.xywh(6, 10, 1, 1));
//		this.add(this.getlpercentProbab(), cons.xywh(8, 10, 1, 1));
		this.add(this.getcbstochProcess(), cons.xywh(6, 6, 3, 1));

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


	/**
	 * Getter method for component cbstochProcess.
	 * 
	 * @return the initialized component
	 */
	public JComboBox getcbstochProcess() {

		if (this.cbstochprocess == null) {
			this.cbstochprocess = new JComboBox();
			this.cbstochprocess.setName("cbstochProcess");
		}

		return this.cbstochprocess;
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
