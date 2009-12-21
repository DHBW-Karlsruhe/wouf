package org.bh.gui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.*;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * 
 * BHContent delivers the contents (forms and chart) to show on screen. 
 *
 * <p>
 * This class gets the forms, filled out by the user, and the generated charts
 * and show both on the main screen.
 *
 * @author Tietze.Patrick
 * @version 0.1, 16.12.2009
 *
 */

public class BHContent extends JPanel{

    private static final long serialVersionUID = 1L;
  
    public JLabel chart, forms, logo;
    public JSplitPane paneV;
    int formPanelHeight = 500;
    
    BHFormsPanel formsPanel;
    
    /**
     * currently  only test contents are available in this class
     */
    
    
    public BHContent(){
		setLayout(new BorderLayout());
		setMinimumSize(new Dimension(100, 100));
		setBackground(Color.white);
		
		logo = new JLabel(new ImageIcon(BHSplashScreen.class.getResource("/org/bh/images/background.jpg")));
		
		add(logo, BorderLayout.CENTER);
		
//		/**
//		 * Test chart start
//		 */
//		String title = "Test";
//		String XAxis = "XAchse";
//		String YAxis = "YAchse";
//		Plot plot = new CategoryPlot();
//		String ID = "1";
//		
//		
//		JFreeChart chart = BHChartFactory.getLineChart(title, XAxis, YAxis, createDataset(), plot, ID);
//		
//		final ChartPanel chartPanel = new ChartPanel(chart);
//	    chartPanel.setPreferredSize(new Dimension(500, 270));
//	    JPanel neu = new JPanel();
//	    /**
//	     * Test chart end
//	     */
//
//	    
//	    //create the forms panel
//		formsPanel = new BHFormsPanel();
//		
//		//build the plit pane with forms panel and chart included
//		paneV = new JSplitPane(JSplitPane.VERTICAL_SPLIT, formsPanel, neu.add(chartPanel));
//		paneV.setOneTouchExpandable(true);
//		paneV.setDividerLocation(formPanelHeight);
//	
//		add(paneV, BorderLayout.CENTER);
		
		
	}
    
    private static CategoryDataset createDataset() {
        
        // row keys...
        final String series1 = "First";
        final String series2 = "Second";
        final String series3 = "Third";

        // column keys...
        final String type1 = "Type 1";
        final String type2 = "Type 2";
        final String type3 = "Type 3";
        final String type4 = "Type 4";
        final String type5 = "Type 5";
        final String type6 = "Type 6";
        final String type7 = "Type 7";
        final String type8 = "Type 8";

        // create the dataset...
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(1.0, series1, type1);
        dataset.addValue(4.0, series1, type2);
        dataset.addValue(3.0, series1, type3);
        dataset.addValue(5.0, series1, type4);
        dataset.addValue(5.0, series1, type5);
        dataset.addValue(7.0, series1, type6);
        dataset.addValue(7.0, series1, type7);
        dataset.addValue(8.0, series1, type8);

        dataset.addValue(5.0, series2, type1);
        dataset.addValue(7.0, series2, type2);
        dataset.addValue(6.0, series2, type3);
        dataset.addValue(8.0, series2, type4);
        dataset.addValue(4.0, series2, type5);
        dataset.addValue(4.0, series2, type6);
        dataset.addValue(2.0, series2, type7);
        dataset.addValue(1.0, series2, type8);

        dataset.addValue(4.0, series3, type1);
        dataset.addValue(3.0, series3, type2);
        dataset.addValue(2.0, series3, type3);
        dataset.addValue(3.0, series3, type4);
        dataset.addValue(6.0, series3, type5);
        dataset.addValue(3.0, series3, type6);
        dataset.addValue(4.0, series3, type7);
        dataset.addValue(3.0, series3, type8);

        return dataset;               
    }
    
    public static void setFormPanel(){
    	
    }
}
