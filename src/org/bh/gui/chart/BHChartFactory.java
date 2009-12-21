package org.bh.gui.chart;

import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.statistics.*;

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
         * @param key
         * @return created LineChart
	 */
    public static JFreeChart getLineChart(String title, String XAxis, String YAxis, CategoryDataset dataset, Plot plot, String key){
	
    	BHLineChart chart = new BHLineChart(title, XAxis, YAxis, dataset, plot, key);
    	return chart.getChart();
	
    }
    /**
	 * method to create the <code>BHxyAreaChart</code>
	 * 
	 * @param title
	 * 			<code>String</code> title for the <code>BHxyAreaChart</code>
	 * @param xAxis
	 * 			<code>String</code> xAxis for the xAxis Label of <code>BHxyAreaChart</code>
	 * @param yAxis
	 * 			<code>String</code> yAxis for the yAxis Label of <code>BHxyAreaChart</code>
	 * @param dataset
	 * 			<code>XYDataset</code> to fill the <code>BHxyAreaChart</code> with data
	 * @param key
	 * 			<code>String</code> key
	 * @param plot
     * 			<code>Plot</code> plot to render the Chart
     * @return
	 * 
	 */
    public static JFreeChart getXYAreaChart(String title, String xAxis, String yAxis, XYDataset dataset, String key, XYPlot plot){
    	
    	BHxyAreaChart chart = new BHxyAreaChart(title, xAxis, yAxis, dataset, key, plot);
    	return chart.getChart();
    }
    
    /**
	 * method to create the <code>BHHistogramChart</code>
	 * 
	 * @param title
	 * 			<code>String</code> title for the <code>BHHistogramChart</code>
	 * @param xAxis
	 * 			<code>String</code> xAxis for the xAxis Label of <code>BHHistogramChart</code>
	 * @param yAxis
	 * 			<code>String</code> yAxis for the yAxis Label of <code>BHHistogramChart</code>
	 * @param dataset
	 * 			<code>HistogramDataset</code> to fill the <code>BHHistogramChart</code> with data
	 * @param key
	 * 			<code>String</code> key
	 * @param plot
     * 			<code>Plot</code> plot to render the Chart
     * @return
	 * 
	 */
    public static JFreeChart getHistogramChart(String title, String xAxis, String yAxis, HistogramDataset dataset, String key, Plot plot){
    	
    	BHHistogramChart chart = new BHHistogramChart(title, xAxis, yAxis, dataset, key, plot);
    	return chart.getChart();
    }

    private static Dataset dimDataset(Comparable column, Comparable row, class type){

    }
}