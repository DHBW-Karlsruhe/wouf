package org.bh.gui.chart;

import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.CategoryDataset;

/**
 * 
 * BHChartFactory class to create the charts 
 *
 *
 * @author Lars
 * @version 0.1, 16.12.2009
 *
 */

public class BHChartFactory{
    
	/**
	 * Method to create a LineChart
	 * 
	 * @param title
	 * 			Title of the LineChart
	 * @param XAxis
	 * 			Title of the XAxis
	 * @param YAxis
	 * 			Title of the YAxis
	 * @param dataset
	 * 			CategoryDataset needed to create a Chart
	 * @param plot
	 * 			CategoryPlot of the Chart
	 * @param ID
	 * 			default ID
	 * @return created LineChart
	 */
    public static JFreeChart getLineChart(String title, String XAxis, String YAxis, CategoryDataset dataset, Plot plot, int ID){
	
	BHLineChart chart = new BHLineChart(title, XAxis, YAxis, dataset, plot, ID);
	return chart.getChart();
	
    }
}