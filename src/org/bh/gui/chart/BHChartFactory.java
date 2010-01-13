package org.bh.gui.chart;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;
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
 * @author Marco Hammel
 * @version 0.2 11.01.2010
 * 
 */

public class BHChartFactory {

	/**
	 * Method to create a LineChart
	 * 
	 * @param title
	 *            Title of the LineChart
	 * @param XAxis
	 *            Title of the XAxis
	 * @param YAxis
	 *            Title of the YAxis
	 * @param dataset
	 *            CategoryDataset needed to create a Chart
	 * @param plot
	 *            CategoryPlot of the Chart
	 * @param key
	 * @return created LineChart
	 */
	public static BHChartPanel getLineChart(final String title, final String XAxis,
			final String YAxis, final Object key) {

		BHLineChart chart = new BHLineChart(title, XAxis, YAxis, dimDataset(
				YAxis, XAxis), key.toString());
		return new BHChartPanel(key, chart.getChart(), chart.getClass(), chart);

	}
	
	//TODO Lars.Zuckschwerdt JavaDoc
	/**
	 * 
	 * @param title
	 * @param XAxis
	 * @param YAxis
	 * @param key
	 * @return
	 */
	public static BHChartPanel getWaterfallChart(final String title,  final String XAxis,
			final String YAxis, final Object key){
		
		BHwaterfallChart chart = new BHwaterfallChart(title, XAxis, YAxis, dimDataset(
				YAxis, XAxis), key.toString());
		return new BHChartPanel(key, chart.getChart(), chart.getClass(), chart);
	}
	//TODO Lars.Zuckschwerdt JavaDoc
	/**
	 * 
	 * @param title
	 * @param XAxis
	 * @param YAxis
	 * @param key
	 * @return
	 */
	public static BHChartPanel getBarChart(final String title,  final String XAxis,
			final String YAxis, final Object key){
		
		BHBarChart chart = new BHBarChart(title, XAxis, YAxis, dimDataset(
				YAxis, XAxis), key.toString());
		return new BHChartPanel(key, chart.getChart(), chart.getClass(), chart);
	}
	
	//TODO Lars.Zuckschwerdt JavaDoc
	/**
	 * 
	 * @param title
	 * @param XAxis
	 * @param YAxis
	 * @param key
	 * @return
	 */
	public static BHChartPanel getStackedBarChart(final String title,  final String XAxis,
			final String YAxis, final Object key){
		
		BHstackedBarChart chart = new BHstackedBarChart(title, XAxis, YAxis, dimDataset(
				YAxis, XAxis), key.toString());
		return new BHChartPanel(key, chart.getChart(), chart.getClass(), chart);
	}
	/**
	 * method to create the <code>BHpieChart</code>
	 * 
	 * @param title
	 * 		<code>String</code> title for the <code>BHPieChart</code>
	 * @param XAxis
	 * 		<code>String</code> xAxis for the xAxis Label of
	 *      <code>BHPieChart</code>
	 * @param YAxis
	 * 		<code>String</code> yAxis for the yAxis Label of
	 *      <code>BHPieChart</code>
	 * @param key
	 * 		<code>String</code> key
	 * @return
	 */
	public static BHChartPanel getPieChart(final String title, final String XAxis, final String YAxis, final Object key){
		BHPieChart chart = new BHPieChart(title, dimDataset(), key.toString());
		
		return new BHChartPanel(key, chart.getChart(), chart.getClass(), chart);
	}
	/**
	 * method to create the <code>BHxyAreaChart</code>
	 * 
	 * @param title
	 *            <code>String</code> title for the <code>BHxyAreaChart</code>
	 * @param xAxis
	 *            <code>String</code> xAxis for the xAxis Label of
	 *            <code>BHxyAreaChart</code>
	 * @param yAxis
	 *            <code>String</code> yAxis for the yAxis Label of
	 *            <code>BHxyAreaChart</code>
	 * @param dataset
	 *            <code>XYDataset</code> to fill the <code>BHxyAreaChart</code>
	 *            with data
	 * @param key
	 *            <code>String</code> key
	 * @param plot
	 *            <code>Plot</code> plot to render the Chart
	 * @return
	 * 
	 */
	public static BHChartPanel getXYAreaChart(final String title, final String xAxis,
			final String yAxis, final String seriesKey, final double[][] data, final Object key,
			final XYPlot plot) {

		BHxyAreaChart chart = new BHxyAreaChart(title, xAxis, yAxis,
				dimDataset(seriesKey, data), key.toString(), plot);
		return new BHChartPanel(key, chart.getChart(), chart.getClass(), chart);
	}

	/**
	 * method to create the <code>BHHistogramChart</code>
	 * 
	 * @param title
	 *            <code>String</code> title for the
	 *            <code>BHHistogramChart</code>
	 * @param xAxis
	 *            <code>String</code> xAxis for the xAxis Label of
	 *            <code>BHHistogramChart</code>
	 * @param yAxis
	 *            <code>String</code> yAxis for the yAxis Label of
	 *            <code>BHHistogramChart</code>
	 * @param dataset
	 *            <code>HistogramDataset</code> to fill the
	 *            <code>BHHistogramChart</code> with data
	 * @param key
	 *            <code>String</code> key
	 * @param plot
	 *            <code>Plot</code> plot to render the Chart
	 * @return
	 * 
	 */
	public static BHChartPanel getHistogramChart(final String title, final String xAxis,
			final String yAxis, final String datasetKey, final double[] values, final int bins,
			final double minimum, final double maximum, final Object key, final Plot plot) {

		BHHistogramChart chart = new BHHistogramChart(title, xAxis, yAxis,
				dimDataset(datasetKey, values, bins, minimum, maximum), key.toString(),
				plot);
		return new BHChartPanel(key, chart.getChart(), chart.getClass(), chart);
	}

	/**
	 * method to create a empty DefaultCategoryDataset
	 * 
	 * @param column
	 *            String column
	 * @param row
	 *            String row
	 * @param type
	 *            Class type
	 * @return DefaultCategoryDataset dataset
	 */
	private static Dataset dimDataset(final Comparable<String> column,
			final Comparable<String> row) {

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(null, row, column);
		return dataset;
	}

	/**
	 * method to create a empty DefaultXYDataset
	 * 
	 * @param seriesKey
	 *            String seriesKey
	 * @param data
	 *            double[][] data
	 * @return DefaultXYDataset dataset
	 */
	private static Dataset dimDataset(final java.lang.Comparable<String> seriesKey,
			final double[][] data) {

		DefaultXYDataset dataset = new DefaultXYDataset();
		dataset.addSeries(seriesKey, data);
		return dataset;
	}

	/**
	 * method to create a empty HistogramDataset
	 * 
	 * @param key
	 *            String key
	 * @param values
	 *            double[] values
	 * @param bins
	 *            int bins
	 * @param minimum
	 *            double minimum
	 * @param maximum
	 *            double maximum
	 * @return HistogramDataset dataset
	 */
	private static Dataset dimDataset(final java.lang.Comparable<String> key,
			final double[] values, final int bins, final double minimum, final double maximum) {

		HistogramDataset dataset = new HistogramDataset();
		dataset.addSeries(key, values, bins, minimum, maximum);
		return dataset;
	}
	private static Dataset dimDataset(){
		DefaultPieDataset dataset = new DefaultPieDataset();
		
		return dataset;
	}
}