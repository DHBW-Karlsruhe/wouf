package org.bh.gui.chart;

import java.util.List;

import javax.swing.UIManager;

import org.bh.gui.swing.IBHComponent;
import org.bh.platform.IPlatformListener;
import org.bh.platform.PlatformEvent;
import org.bh.platform.Services;
import org.bh.platform.PlatformEvent.Type;
import org.bh.platform.i18n.BHTranslator;
import org.bh.platform.i18n.ITranslator;
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
@SuppressWarnings("serial")
public class BHHistogramChart extends JFreeChart implements IBHComponent,
		IBHAddValue, IPlatformListener {
	ITranslator translator = BHTranslator.getInstance();

	private String key;
	private String inputHint;
	private JFreeChart chart;
	private HistogramDataset dataset;

	protected BHHistogramChart(final String title, final String xAxis, final String yAxis,
			final Dataset dataset, final String key, final Plot plot) {
		super(plot);
		this.key = key;
		this.dataset = (HistogramDataset) dataset;

		chart = ChartFactory.createHistogram(title, xAxis, yAxis, this.dataset,
				PlotOrientation.VERTICAL, true, true, false);
		chart.getXYPlot().setForegroundAlpha(0.75f);
		if ("Nimbus".equals(UIManager.getLookAndFeel().getName())) {
			chart.setBackgroundPaint(UIManager.getColor("desktop"));
		}
		
		reloadText();
		Services.addPlatformListener(this);
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

	@Override
	public String getInputHint() {
		return inputHint;
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
	
	/**
	 * Reloads Text if necessary.
	 */
	protected void reloadText() {
		this.chart.getPlot().setNoDataMessage(translator.translate("noDataAvailable"));
		inputHint = Services.getTranslator().translate(key, ITranslator.LONG);
	}
}
