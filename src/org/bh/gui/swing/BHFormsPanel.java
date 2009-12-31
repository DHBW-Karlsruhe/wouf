package org.bh.gui.swing;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Visual class ViewTabedPane3.
 * 
 * Created with Mogwai FormMaker 0.6.
 */
public class BHFormsPanel extends JPanel {

	private JTabbedPane view_tabedpane;
	private JPanel view_tabprocessdata;
	private JPanel view_tabperioddata;
	private JPanel view_scenarioHeadData;
	private JCheckBox chbDirectInput;
	private BHLabel lDirectInput;

	/**
	 * Constructor.
	 */
	public BHFormsPanel() {
		this.initialize();
	}

	/**
	 * Constructor.
	 */
	public BHFormsPanel(JPanel scenarioHeadData, JPanel processInputForm,
			JPanel periodInputFormSet) {
		this.view_tabprocessdata = processInputForm;
		this.view_tabperioddata = periodInputFormSet;
		this.view_scenarioHeadData = scenarioHeadData;

		String rowDef = "4px,p,20px,p,4px";
		String colDef = "4px,pref:grow,4px";

		FormLayout layout = new FormLayout(colDef, rowDef);
		this.setLayout(layout);

		CellConstraints cons = new CellConstraints();

		this.add(this.view_scenarioHeadData, cons.xywh(2, 2, 1, 1));
		// this.add(this.getView_TabedPane(this.view_tabprocessdata,
		// this.view_tabperioddata), cons.xywh(2, 4, 1, 1));
	}

	/**
	 * Initialize method.
	 */
	private void initialize() {
		JPanel panel = new BHScenarioHeadForm();

		String rowDef = "4px,p,20px,p,4px";
		String colDef = "4px,pref:grow,4px";

		FormLayout layout = new FormLayout(colDef, rowDef);
		this.setLayout(layout);

		CellConstraints cons = new CellConstraints();

		this.add(panel, cons.xywh(2, 2, 1, 1));
		// this.add(this.getView_TabedPane(), cons.xywh(2, 4, 1, 1));

	}

	/**
	 * Getter method for component View_TabedPane.
	 * 
	 * @return the initialized component
	 */
	public JTabbedPane getView_TabedPane() {

		if (this.view_tabedpane == null) {
			this.view_tabedpane = new JTabbedPane();
			this.view_tabedpane.addTab("Prozessdaten", this
					.getView_Processdata());
			this.view_tabedpane
					.addTab("Perioden", this.getView_TabPerioddata());

			this.view_tabedpane.setName("View_TabedPane");
			this.view_tabedpane.setSelectedIndex(0);
			// this.view_tabedpane
			// .addChangeListener(new javax.swing.event.ChangeListener() {
			// public void stateChanged(javax.swing.event.ChangeEvent e) {
			// handleView_TabedPaneStateChanged();
			// }
			// });
		}

		return this.view_tabedpane;
	}

	/**
	 * Getter method for component View_TabedPane.
	 * 
	 * @return the initialized component
	 */
	public JTabbedPane getView_TabedPane(JPanel processInputForm,
			JPanel periodInputFormSet) {

		if (this.view_tabedpane == null) {
			this.view_tabedpane = new JTabbedPane();
			if (this.view_tabprocessdata != null) {
				this.view_tabedpane.addTab("Prozessdaten", processInputForm);
			}
			if (this.view_tabperioddata != null) {
				this.view_tabedpane.addTab("Perioden", periodInputFormSet);
			}

			this.view_tabedpane.setName("View_TabedPane");
			this.view_tabedpane.setSelectedIndex(0);
		}

		return this.view_tabedpane;
	}

	/**
	 * Getter method for component View_TabHeaddata.
	 * 
	 * @return the initialized component
	 */
	public JPanel getView_Processdata() {

		if (this.view_tabprocessdata == null) {
			this.view_tabprocessdata = new JPanel();

			String rowDef = "4px,p,4px";
			String colDef = "4px,pref:grow,4px";

			FormLayout layout = new FormLayout(colDef, rowDef);
			this.view_tabprocessdata.setLayout(layout);

			CellConstraints cons = new CellConstraints();

			this.view_tabprocessdata.setName("View_TabHeaddata");

			this.view_tabprocessdata.add(new BHStochasticInputForm(), cons
					.xywh(2, 2, 1, 1));
		}

		return this.view_tabprocessdata;
	}

	/**
	 * Getter method for component View_TabPerioddata.
	 * 
	 * @return the initialized component
	 */
	public JPanel getView_TabPerioddata() {

		if (this.view_tabperioddata == null) {
			this.view_tabperioddata = new JPanel();

			String rowDef = "4px,p,4px,p,4px";
			String colDef = "4px,p,4px,pref:grow,4px";

			FormLayout layout = new FormLayout(colDef, rowDef);
			this.view_tabperioddata.setLayout(layout);

			CellConstraints cons = new CellConstraints();

			this.view_tabperioddata.setName("View_TabPerioddata");
			this.view_tabperioddata.setVisible(false);

			this.view_tabperioddata.add(getLDirectInput(), cons
					.xywh(2, 2, 1, 1));
			this.view_tabperioddata.add(getChbDirectInput(), cons.xywh(4, 2, 1,
					1));
			
			//TODO Kharitonov.Anthon Abhängigkeit zum PluginUI...?
			//ggf. Klasse im gui.swing-Paket löschen
			this.view_tabperioddata.add(new BHPeriodInputForm("2009"), cons
					.xywh(2, 4, 3, 1));

		}

		return this.view_tabperioddata;
	}

	/**
	 * Getter method for component chbcalculateQuest.
	 * 
	 * @return the initialized component
	 */
	public JCheckBox getChbDirectInput() {

		if (this.chbDirectInput == null) {
			this.chbDirectInput = new JCheckBox();
			this.chbDirectInput.setName("chbcalculateQuest");
			this.chbDirectInput.setSelected(true);
			// this.chbDirectInput.addActionListener(new ActionListener() {
			// public void actionPerformed(ActionEvent e) {
			// handlechbcalculateQuestActionPerformed(e.getActionCommand());
			// }
			// });
			// this.chbDirectInput.addChangeListener(new
			// javax.swing.event.ChangeListener() {
			// public void stateChanged(javax.swing.event.ChangeEvent e) {
			// handlechbcalculateQuestStateChanged();
			// }
			// });
		}

		return this.chbDirectInput;
	}

	/**
	 * Getter method for component Component_61.
	 * 
	 * @return the initialized component
	 */
	public JLabel getLDirectInput() {

		if (this.lDirectInput == null) {
			this.lDirectInput = new BHLabel("");
			this.lDirectInput.setText("Direkteingabe");
		}

		return this.lDirectInput;
	}

	/**
	 * Test main method.
	 */
	public static void main(String args[]) {

		JFrame test = new JFrame("Test for ViewTabedPane3");
		test.setContentPane(new BHFormsPanel());
		test.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		test.pack();
		test.show();
	}
}
