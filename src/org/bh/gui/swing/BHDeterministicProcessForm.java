package org.bh.gui.swing;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.bh.data.DTOScenario;
import org.bh.data.types.IValue;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * This class contains the form for stochastic processes
 * 
 * @author Anton Kharitonov
 * @version 0.1, 05.01.2010
 * 
 */
public class BHDeterministicProcessForm extends JPanel {
	
	//Labels
	private BHDescriptionLabel linterval;
	private BHDescriptionLabel ldcfMethod;
	
	//Components
	private JCheckBox chbinterval;
	private JTable tperioddata;
	private BHComboBox cbdcfMethod;

	CellConstraints cons; 
	
	ITranslator translator = Services.getTranslator();

	/**
	 * Constructor.
	 */
	public BHDeterministicProcessForm() {
		this.initialize();
	}

	/**
	 * Initialize method.
	 */
	private void initialize() {
		FormLayout layout;
		//TODO rowDef überarbeiten
		String colDef = "4px,right:pref,4px,max(80px;pref),pref:grow,4px";
		String rowDef = "4px,p,4px,p,4px,p,4px";
		
		layout = new FormLayout(colDef, rowDef);
		this.setLayout(layout);

		cons = new CellConstraints();
		
		this.add(this.getlDCFmethod(), cons.xywh(2, 2, 1, 1));
		this.add(this.getcbDCFmethod(), cons.xywh(4, 2, 1, 1));
		this.add(this.getLinterval(), cons.xywh(2, 4, 1, 1));
		this.add(this.getChbinterval(), cons.xywh(4, 4, 1, 1));
		
	}

	// TODO add missing label keys and translations, change hard coded values to keys
	
	public BHDescriptionLabel getLinterval() {
		if (linterval == null) {
			linterval = new BHDescriptionLabel("IntervalArithmetic");
		}
		return linterval;
	}

	public JCheckBox getChbinterval() {
		if (chbinterval == null) {
			chbinterval = new JCheckBox();
		}
		return chbinterval;
	}

	public JTable getTperioddata(IValue[][] data) {
		//TODO hartgecodete Strings raus!
		String[] columnnames = { "Periode", "Fremdkapital", "FCF" };
		this.tperioddata = new JTable(data, columnnames);
		//TODO Thiele.Klaus Farben der Tabelle...?
		this.tperioddata.setGridColor(new Color(0, 0, 0));
		this.tperioddata.setPreferredScrollableViewportSize(new Dimension(
					30, 30));
		return tperioddata;
	}
	
	
	
	public void setPeriodTable(IValue[][] data) throws Exception{
		if(data.length > 0)
			if(data[0].length != 3)
				throw new Exception("PeriodTableData: Data array has wrong length!");
		
		this.add(new JScrollPane(this.getTperioddata(data)), cons.xywh(2, 6, 4, 1));
	}
	
	
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
	 * Test main method.
	 */
	public static void main(String args[]) {

		JFrame test = new JFrame("Test for ViewStochasticData3");
		test.setContentPane(new BHDeterministicProcessForm());
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
