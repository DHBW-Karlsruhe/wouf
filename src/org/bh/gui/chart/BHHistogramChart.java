package org.bh.gui.chart;

import java.awt.Component;
import java.util.List;

import javax.swing.UIManager;

import org.bh.gui.swing.IBHComponent;
import org.bh.platform.PlatformEvent;
import org.bh.platform.Services;
import org.bh.platform.i18n.BHTranslator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.Dataset;
import org.jfree.data.statistics.HistogramDataset;

/**
 * BHHistogramChart to create a Histogramchart
 * 
 * <p>
 * Histogramchart is created and modified
 * 
 * @author Lars
 * @version 0.1, 17.12.2009
 * 
 */
public class BHHistogramChart extends JFreeChart implements IBHComponent,
		IBHAddValue {
	BHTranslator translator = BHTranslator.getInstance();

	private String key;
	private JFreeChart chart;
	private HistogramDataset dataset;
	private String inputHint;

	protected BHHistogramChart(final String title, final String xAxis, final String yAxis,
			final Dataset dataset, final String key, final Plot plot) {
		super(plot);
		Services.addPlatformListener(this);
		this.key = key;
		this.dataset = (HistogramDataset) dataset;

		chart = ChartFactory.createHistogram(title, xAxis, yAxis, this.dataset,
				PlotOrientation.VERTICAL, true, true, false);
		chart.getXYPlot().setForegroundAlpha(0.75f);
		plot.setNoDataMessage(translator.translate("noDataAvailable"));
		if ("Nimbus".equals(UIManager.getLookAndFeel().getName())) {
			chart.setBackgroundPaint(UIManager.getColor("desktop"));
		}
	}

	/**
	 * method to get the <code>JFreeChart</code> BHHistogramChart
	 * 
	 * @return <code>JFreeChart</code> chart
	 */
	public final JFreeChart getChart() {
		return chart;
	}

	/**
	 * returns key of BHHistogramChart
	 */

	public final String getKey() {
		return key;
	}

	/**
	 * method to add a series into an empty HistogramDataset
	 */

	public final void addSeries(final Comparable<String> key, final double[] values, int bins,
			final double minimum, final double maximum) {
		this.dataset.addSeries(key, values, bins, minimum, maximum);
	}

	/* Specified by interface/super class. */
	@Override
	public final Component add(final Component comp) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	public final boolean isTypeValid() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void addValue(final Number value, Comparable<String> columnKey) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void addValue(final Number value, final int rowKey, Comparable<String> columnKey) {
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

	public final void addSeries(final Comparable<String> seriesKey, final double[][] data) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	public String getInputHint() {
		return this.inputHint;
	}

	@Override
	public String getBHHint() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}
	
	/**
	 * Handle PlatformEvents
	 */
	@Override
	public void platformEvent(PlatformEvent e) {
		// TODO Zuckschwert.Lars
	}
	
	/**
	 * Reset Text if necessary.
	 */
	@Override
	public void reloadText() {
		// TODO Zuckschwert.Lars
	}

}
