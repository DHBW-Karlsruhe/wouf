package org.bh.gui.chart;

import java.util.List;

import javax.swing.UIManager;

import org.bh.platform.IPlatformListener;
import org.bh.platform.PlatformEvent;
import org.bh.platform.Services;
import org.bh.platform.PlatformEvent.Type;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.Dataset;
import org.jfree.data.statistics.HistogramDataset;

/**
 * BHHistogramChart to create a Histogramchart
 * 
 * <p>
 * Histogramchart is created and modified but currently unused in Business Horizon
 * 
 * @author Lars
 * @version 0.1, 17.12.2009
 * 
 */
@SuppressWarnings("serial")
public class BHHistogramChart extends BHChart implements IBHAddValue, IPlatformListener {

	private HistogramDataset dataset;

	protected BHHistogramChart(
			final Dataset dataset, final String key) {
                super(key);
		this.dataset = (HistogramDataset) dataset;

		chart = ChartFactory.createHistogram(null
                        , translator.translate(key.concat(BHChart.DIMX))
                        , translator.translate(key.concat(BHChart.DIMY))
                        , this.dataset
                        , PlotOrientation.VERTICAL, true, true, false);
		chart.getXYPlot().setForegroundAlpha(0.75f);
		if ("Nimbus".equals(UIManager.getLookAndFeel().getName())) {
			chart.setBackgroundPaint(UIManager.getColor("Chart.background"));
		}
		
		reloadText();
		Services.addPlatformListener(this);
	}

	/**
	 * method to add a series into an empty HistogramDataset
	 */

	public final void addSeries(final Comparable<String> key, final double[] values, int bins,
			final double minimum, final double maximum) {
		this.dataset.addSeries(key, values, bins, minimum, maximum);
                chart.fireChartChanged();
	}

	@Override
	public void addValue(final Number value, Comparable<String> columnKey) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void addValue(final Number value, final Comparable rowKey, Comparable<String> columnKey) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void addValues(List<?> list) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	public final void addSeries(final Comparable<String> seriesKey, final double[][] data, Integer amountOfValues, Integer average) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
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
	public void addSeries(Comparable<String> seriesKey, double[][] data) {
	    // TODO Auto-generated method stub
	    throw new UnsupportedOperationException("This method has not been implemented");
	}

	@Override
	public void removeSeries(int number) {
	    // TODO Auto-generated method stub
	    throw new UnsupportedOperationException("This method has not been implemented");
	}
	
}
