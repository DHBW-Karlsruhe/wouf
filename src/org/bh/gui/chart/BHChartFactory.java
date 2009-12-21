package org.bh.gui.chart;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.DefaultXYDataset;

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
    
	public static enum Type{
    	CHARTTYPE;
    }
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
    public static JFreeChart getLineChart(String title, String XAxis, String YAxis, Class<?> type, Plot plot, String key){
	
    	BHLineChart chart = new BHLineChart(title, XAxis, YAxis, dimDataset(YAxis, XAxis, type), plot, key);
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
    public static JFreeChart getXYAreaChart(String title, String xAxis, String yAxis, String seriesKey, double[][] data, String key, XYPlot plot){
    	
    	BHxyAreaChart chart = new BHxyAreaChart(title, xAxis, yAxis, dimDataset(seriesKey, data), key, plot);
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
    public static JFreeChart getHistogramChart(String title, String xAxis, String yAxis, String datasetKey, double[] values, int bins, double minimum, double maximum, String key, Plot plot){
    	
    	BHHistogramChart chart = new BHHistogramChart(title, xAxis, yAxis, dimDataset(datasetKey, values, bins, minimum, maximum), key, plot);
    	return chart.getChart();
    }
    
    /**
     * method to create a empty DefaultCategoryDataset
     * 
     * @param column
     * 		String column
     * @param row
     * 		String row
     * @param type
     * 		Class type
     * @return
     * 		DefaultCategoryDataset dataset
     */
    private static Dataset dimDataset(Comparable<String> column, Comparable<String> row, Class<?> type){
    		
    	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    	dataset.addValue(null, row, column);
    	return dataset;
    }
    
    /**
     * method to create a empty DefaultXYDataset
     * 
     * @param seriesKey
     * 		String seriesKey
     * @param data
     * 		double[][] data
     * @return
     * 		DefaultXYDataset dataset
     */
    private static Dataset dimDataset(java.lang.Comparable<String> seriesKey, double[][] data){
    	
    	DefaultXYDataset dataset = new DefaultXYDataset();
    	dataset.addSeries(seriesKey, data);
    	return dataset;
    }
    
    /**
     * method to create a empty HistogramDataset
     * 
     * @param key
     * 		String key
     * @param values
     * 		double[] values
     * @param bins
     * 		int bins
     * @param minimum
     * 		double minimum
     * @param maximum
     * 		double maximum
     * @return
     * 		HistogramDataset dataset
     */
    private static Dataset dimDataset(java.lang.Comparable<String> key, double[] values, int bins, double minimum, double maximum){
    	
    	HistogramDataset dataset = new HistogramDataset();
    	dataset.addSeries(key, values, bins, minimum, maximum);
    	return dataset;
    }
}