package org.bh.gui.chart;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;

import javax.swing.UIManager;

import org.apache.log4j.Logger;
import org.bh.platform.IPlatformListener;
import org.bh.platform.PlatformEvent;
import org.bh.platform.Services;
import org.bh.platform.PlatformEvent.Type;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * 
 * BHLineChart to create the LineChart
 * 
 * <p>
 * LineChart is created and modified
 * 
 * @author Lars
 * @version 0.1, 16.12.2009
 * @update 23.12.2010 Timo Klein
 * 
 */
public class BHLineChart extends BHChart implements IBHAddValue, IPlatformListener {

	private XYSeriesCollection dataset;
	private static final Logger log = Logger.getLogger(BHLineChart.class);

	protected BHLineChart(Dataset dataset, final String key) {
		super(key);
		dataset = new XYSeriesCollection();
		this.dataset = (XYSeriesCollection) dataset;

		chart = ChartFactory.createXYLineChart(null
                        , translator.translate(key.concat(BHChart.DIMX))
                        , translator.translate(key.concat(BHChart.DIMY))
                        , this.dataset
                        , PlotOrientation.VERTICAL, true, true, false);
		
		final XYPlot plot = chart.getXYPlot();
		final NumberAxis rangeAxis = (NumberAxis) plot.getDomainAxis();
       rangeAxis.setTickUnit(new NumberTickUnit(1.0));
       plot.getRenderer().setSeriesPaint(0, Color.RED);
                
		if ("Nimbus".equals(UIManager.getLookAndFeel().getName())) {
			chart.setBackgroundPaint(UIManager.getColor("Chart.background"));
		}
		
		reloadText();
		Services.addPlatformListener(this);
	}

	@Override
	public final void addValue(Number value, Comparable row, Comparable<String> columnKey) {  //auskommentiert bei Timo Klein
//		if(this.dataset.getColumnKeys().indexOf(columnKey) == -1){
//			this.dataset.addValue(value, row, columnKey);
//		}else{
//			this.dataset.addValue(value, row, columnKey+"'");
//		}
//		chart.fireChartChanged();
	}

	@Override
	public void addValues(List<?> list) { //auskommentiert bei Timo Klein
//		Iterator<?> it = list.iterator();
//
//		for (int i = 0; i < list.size(); i++) {
//			for (int j = 0; j <= i; j++) {
//				while (it.hasNext()) {
//					this.dataset.addValue((Number) list.get(i), i,
//							(String) list.get(j));
//					chart.fireChartChanged();
//				}
//			}
//		}
	}

	@Override
	public final void addValue(Number value, Comparable<String> columnKey) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public final void addSeries(Comparable<String> key, double[] values, int bins,
			final double minimum, final double maximum) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	//Timo Klein 23.12
	public void addSeries(Comparable<String> seriesKey, double[][] data, Integer amountOfValues, Integer average) {
//		XYSeries series = new XYSeries(translator.translate(seriesKey));
//		for (int i = 0; i < data.length; i++) {
//			//series.add(data[i][0], data[i][1]);
//			series.add(3.0,2.0);
//			series.add(4.0, 1.0);
//		}
//		final XYSeriesCollection dataset = new XYSeriesCollection();
//		dataset.addSeries(series);
//		//dataset.setIntervalWidth(0.0);
//
//		final XYPlot plot = chart.getXYPlot();
//		final NumberAxis axis2 = new NumberAxis(
//				translator
//						.translate("org.bh.plugin.stochasticResultAnalysis.BHStochasticResultController$ChartKeys.DISTRIBUTION_CHART.Y2"));
//		double upper = (average / amountOfValues.doubleValue() * 100) * 1.047;
//		double lower = 0.0;
//		axis2.setRange(lower, upper);
//
//		plot.setRangeAxis(1, axis2);
//		plot.mapDatasetToRangeAxis(1, 1);
//
//		final NumberAxis axis = (NumberAxis) plot.getDomainAxis();
//		axis.setAutoRange(true);
//
//		chart.fireChartChanged();
	}

	
	/**
	 * Handle PlatformEvents
	 */
	@Override
	public void platformEvent(PlatformEvent e) {
		if(e.getEventType() == Type.LOCALE_CHANGED){
			this.reloadText();
		}
	}

	@Override
	//timo Klein
	public void addSeries(Comparable<String> seriesKey, double[][] data) {	
		XYSeries series = new XYSeries(seriesKey);
		for (int i = 0; i < data.length; i++) {
			series.add(data[i][0], data[i][1]);
		}
		dataset.addSeries(series);
		chart.fireChartChanged();
	}

	@Override
	//Timo Klein
	public void removeSeries(int number) {
		try {
			dataset.removeSeries(number);
			chart.fireChartChanged();
		} catch (IllegalArgumentException e) {
			log.info(number + " not in Series List");
		}
	}
	
}
