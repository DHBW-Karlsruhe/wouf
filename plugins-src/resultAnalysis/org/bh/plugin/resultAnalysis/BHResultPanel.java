package org.bh.plugin.resultAnalysis;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import org.bh.calculation.IShareholderValueCalculator;
import org.bh.gui.chart.BHChartFactory;
import org.bh.gui.swing.BHDescriptionLabel;
import org.bh.gui.swing.BHDescriptionTextArea;
import org.bh.gui.swing.BHValueLabel;
import org.bh.platform.i18n.BHTranslator;
import org.jfree.chart.ChartPanel;
/**
 * 
 * Class to create one ResultPanel with two different Charts and Descriptions 
 *
 * <p>
 * This class creates a <code>JPanel</code> with the Results. There are two different
 * charts and the descriptions. 
 *
 * @author Lars.Zuckschwerdt
 * @version 1.0, 30.12.2009
 *
 */
public class BHResultPanel extends JPanel{
	
	private JPanel northPanel;
	private JPanel westPanel;
	private JPanel centerPanel;
	private JPanel eastPanel;
	private JPanel southPanel;
	private BHDescriptionLabel headlineLabel;
	private BHDescriptionLabel descriptionLabel;
	private ChartPanel lineChartLabel;
	private ChartPanel pieChartLabel;
	private BHDescriptionTextArea lineChartTextArea;
	private BHDescriptionTextArea pieChartTextArea;
	private BHValueLabel shareholderValue;
	
	
	final BHTranslator translator = BHTranslator.getInstance();
	/**
	 * Constructor
	 */
	public BHResultPanel(){
		this.initialize();
	}
	
	/**
	 * Initialize method
	 */
	public void initialize(){
		
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
		
		northPanel = new JPanel();
		northPanel.setLayout(new BorderLayout());
		westPanel = new JPanel();
		centerPanel = new JPanel();
		eastPanel = new JPanel();
		southPanel = new JPanel();
			
			/**
			 * Headline Label
			 */
			headlineLabel = new BHDescriptionLabel("headlineLabel");
			headlineLabel.setFont(UIManager.getFont("defaultFont"));
			headlineLabel.setText(translator.translate("Rheadline"));
			headlineLabel.setHorizontalAlignment(getWidth()/2);
			
			/**
			 * Description under Headline Label
			 */
			descriptionLabel = new BHDescriptionLabel("descriptionLabel");
			descriptionLabel.setFont(UIManager.getFont("defaultFont"));
			descriptionLabel.setText(translator.translate("Rdescription"));
			descriptionLabel.setHorizontalAlignment(getWidth()/2);
			
			northPanel.add(headlineLabel, BorderLayout.NORTH);
			northPanel.add(descriptionLabel, BorderLayout.SOUTH);
		
			this.add(northPanel, BorderLayout.NORTH);
			
		this.add(westPanel, BorderLayout.WEST);
		
			/**
			 * Create a TableLayout for the centerPanel
			 */
			double border = 10;

        	double size[][] =
        		{{border, TableLayoutConstants.FILL, TableLayoutConstants.FILL, border},  // Columns
        		 {border, TableLayoutConstants.FILL, TableLayoutConstants.FILL, border}}; // Rows
        		
       		centerPanel.setLayout(new TableLayout(size));
       		
       		/**
       		 * Creates the default LineChart and add it on a Label 
       		 */
       		lineChartLabel = new ChartPanel(BHChartFactory.getLineChart("TestChart", "XAxis", "YAxis",  "LineChart"));
       		lineChartLabel.setFont(UIManager.getFont("defaultFont"));
       		
       		/**
       		 * Creates the description and add it on a Label
       		 */
       		lineChartTextArea = new BHDescriptionTextArea("RlineChartText", 5, 5);
       		
       		
       		/**
       		 * creates the default PieChart
       		 */
       		pieChartLabel = new ChartPanel(BHChartFactory.getPieChart("TestPieChart", "XAxis", "YAxis", "PieChart"));
       		pieChartLabel.setFont(UIManager.getFont("defaultFont"));
       		
       		
       		pieChartTextArea = new BHDescriptionTextArea("RpieChartText", 5, 5);
       		
       		/**
       		 * add the Content into centerPanel
       		 */
       		//centerPanel.add(lineChartTextArea, "1,1");
       		centerPanel.add(lineChartLabel, "2,1");
       		centerPanel.add(pieChartLabel, "1,2");
       		centerPanel.add(pieChartTextArea, "2,2");
       		
       		shareholderValue = new BHValueLabel(IShareholderValueCalculator.SHAREHOLDER_VALUE);
       		centerPanel.add(shareholderValue, "1,1");
       		
       		this.add(centerPanel, BorderLayout.CENTER);
        	
		this.add(eastPanel, BorderLayout.EAST);
		this.add(southPanel, BorderLayout.SOUTH);
		
	}
	
	/**
     * Test main method.
     */
    public static void main(String args[]) {

	JFrame test = new JFrame("Test for ResultPanel");
	test.setContentPane(new BHResultPanel());
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