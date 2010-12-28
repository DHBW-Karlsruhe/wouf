package org.bh.gui.chart;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import org.bh.data.DTOScenario;
import org.bh.gui.swing.comp.BHCheckBox;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;
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
 * @version 1.0
 * @update 23.12.2010 Timo Klein
 * 
 */
public class BHChartFactory {

    static ITranslator translator = Services.getTranslator();
    
    private static final Logger log = Logger.getLogger(BHChartFactory.class);
    
    /**
     * create a LineChart with an empty default dataset
     * @param key
     * @return BHChartPanel contaning semantic LineChart
     * @see BHLineChart
     */
    public static BHChartPanel getLineChart(final Object key) {

        BHLineChart chart = new BHLineChart(dimDataset(getAxes(key)[0], getAxes(key)[1]), key.toString());
        return new BHChartPanel(key, chart.getChart(), chart.getClass(), chart);
    }

    /**
     * create a waterfall chart with an empty default dataset
     * @param key
     * @return BHChartPanel contaning semantic WaterfallChart
     * @see BHwaterfallChart
     */
    public static BHChartPanel getWaterfallChart( final Object key, boolean legend, boolean tooltips) {

        BHwaterfallChart chart = new BHwaterfallChart(dimDataset(getAxes(key)[0], getAxes(key)[1]), key.toString(), legend, tooltips);
        return new BHChartPanel(key, chart.getChart(), chart.getClass(), chart);
    }

    /**
     * create a bar chart with an empty default dataset
     * @param key
     * @return BHChartPanel contaning semantic BarChart
     * @see BHBarChart
     */
    public static BHChartPanel getBarChart( final Object key, boolean legend, boolean tooltips) {

        BHBarChart chart = new BHBarChart(key.toString(), dimDataset(getAxes(key)[0], getAxes(key)[1]), legend, tooltips);
        return new BHChartPanel(key, chart.getChart(), chart.getClass(), chart);
    }

    /**
     * create a stacked bar chart with an empty default dataset
     * @param key
     * @return BHChartPanel contaning semantic StackedBarChar
     * @see BHstackedBarChart
     */
    public static BHChartPanel getStackedBarChart( final Object key, boolean legend, boolean tooltips) {

        BHstackedBarChart chart = new BHstackedBarChart(dimDataset(getAxes(key)[0], getAxes(key)[1]), key.toString(), legend, tooltips);
        return new BHChartPanel(key, chart.getChart(), chart.getClass(), chart);
    }

    /**
     * create a stacked pie chart with an empty default dataset
     * @param key
     * @return BHChartPanel contaning semantic PieChart
     * @see BHPieChart
     */
    public static BHChartPanel getPieChart( final Object key) {

        BHPieChart chart = new BHPieChart(dimDataset(), key.toString());
        return new BHChartPanel(key, chart.getChart(), chart.getClass(), chart);
    }

    /**
     * create a xy area chart
     * @param seriesKey defining a series for the chart
     * @param data double[][] x to y relationsship
     * @param key
     * @return BHChartPanel contaning semantic XYAreaChart
     * @see BHxyAreaChart
     */
    public static BHChartPanel getXYAreaChart( final String seriesKey, final double[][] data, final Object key) {

        BHxyAreaChart chart = new BHxyAreaChart(
                dimDataset(seriesKey, data), key.toString());
        return new BHChartPanel(key, chart.getChart(), chart.getClass(), chart);
    }
    /**
     * create a XY bar chart chart with an empty default dataset
     * @param key
     * @return BHChartPanel contaning semantic XYBarChart
     * @see BHXYBarChart
     */
    public static BHChartPanel getXYBarChart(Object key) {

        BHXYBarChart chart = new BHXYBarChart(key.toString());   	
        return new BHChartPanel(key, chart.getChart(), chart.getClass(), chart);
    }

    /**
     * create a histogramm chart with an empty default dataset
     * @param key
     * @return BHChartPanel contaning semantic HistogrammChart
     * @see BHHistogrammChart
     */
    public static BHChartPanel getHistogramChart(final Object key) {

//        BHHistogramChart chart = new BHHistogramChart(
//                dimDataset(datasetKey, values, bins, minimum, maximum), key.toString());
        
        BHHistogramChart chart = new BHHistogramChart(histogrammDataset(), key.toString());
        
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
        //dataset.addValue(null, row, column);
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
        //dataset.addSeries(seriesKey, data);
        return dataset;
    }

    /**
     * deliver a initial histogramm dataset
     * @return Datasat type HistogramDataset
     * @see HistogramDataset
     */
    private static Dataset histogrammDataset(){
    	return new HistogramDataset();
    }

    private static Dataset dimDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        return dataset;
    }
    /**
     * get main axes description definition of a chart (x,y)
     * @param key
     * @return String[]
     */
    private static String[] getAxes(Object key) {
        String[] axes = new String[2];
        axes[0] = translator.translate(key.toString().concat(BHChart.DIMX));
        axes[1] = translator.translate(key.toString().concat(BHChart.DIMY));
        return axes;
    }
    /**
     * initialize chart instances for performance increasing
     */
    public static void initialInit() {
    	SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				log.debug("initial Init of Charts started");
				JPanel initPanel = new JPanel();
				initPanel.add(getLineChart(init.INIT.toString()));
				initPanel.add(getWaterfallChart(init.INIT.toString(), true, true));
				initPanel.add(getBarChart(init.INIT.toString(), true, true));
				initPanel.add(getStackedBarChart(init.INIT.toString(), true, true));
				initPanel.add(getPieChart(init.INIT.toString()));
				log.debug("initial Init of Charts completed");
			}
    	});
    }
	
    private enum init{
		INIT;
		
		@Override
		public String toString() {
			return getClass().getName() + "." + super.toString();
		}
	}
}
