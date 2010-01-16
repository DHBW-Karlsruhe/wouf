package org.bh.gui.swing;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
@SuppressWarnings("serial")
public class BHDeterministicProcessForm extends JPanel {
	
	//Labels
	private BHDescriptionLabel linterval;
	private BHDescriptionLabel ldcfMethod;
	
	//Components
	private BHCheckBox chbinterval;
	private JTable tperioddata;
	private BHComboBox cbdcfMethod;
	private JScrollPane tablePane;
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
		String colDef = "4px,right:pref,4px,max(80px;pref),4px:grow(0.2),pref:grow,4px";
		String rowDef = "4px,p,4px,p,4px,p,4px";
		
		layout = new FormLayout(colDef, rowDef);
		this.setLayout(layout);

		cons = new CellConstraints();
		
		this.add(this.getlDCFmethod(), cons.xywh(2, 2, 1, 1));
		this.add(this.getcbDCFmethod(), cons.xywh(4, 2, 1, 1));
//		this.add(this.getLinterval(), cons.xywh(2, 4, 1, 1));
		this.add(this.getChbinterval(), cons.xywh(6, 2, 1, 1));
		
	}

	// TODO add missing label keys and translations, change hard coded values to keys
	
//	public BHDescriptionLabel getLinterval() {
//		if (linterval == null) {
//			linterval = new BHDescriptionLabel("IntervalArithmetic");
//		}
//		return linterval;
//	}

	public BHCheckBox getChbinterval() {
		if (chbinterval == null) {
			chbinterval = new BHCheckBox(DTOScenario.Key.INTERVAL_ARITHMETIC);
			chbinterval.setText(translator.translate(DTOScenario.Key.INTERVAL_ARITHMETIC));
		}
		return chbinterval;
	}

	public JTable getTperioddata(IValue[][] data) {
		//TODO hartgecodete Strings raus!
		String[] columnnames = { "Periode", "Fremdkapital", "FCF" };
		this.tperioddata = new JTable(data, columnnames) {
			// always make the table as large as necessary 
			@Override
			public Dimension getPreferredScrollableViewportSize() {
				return getPreferredSize();
			}
		};
		this.tperioddata.setEnabled(false);
		this.tperioddata.getTableHeader().setReorderingAllowed(false);
		//TODO Thiele.Klaus Farben der Tabelle...? --> BHTableCellRender
//		this.tperioddata.setDefaultRenderer(Object.class, new BHTableCellRenderer());
		this.tperioddata.setGridColor(Color.RED);
		return this.tperioddata;
	}
	
	
	
	public void setPeriodTable(IValue[][] data) {
		if(data.length > 0)
			if(data[0].length != 3)
				throw new Error("PeriodTableData: Data array has wrong length!");
		
		if(tablePane != null)
			this.remove(tablePane);
		
		tablePane = new JScrollPane(this.getTperioddata(data));
		
		this.add(tablePane, cons.xywh(2, 6, 6, 1));
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
		test.setVisible(true);
	}

}
