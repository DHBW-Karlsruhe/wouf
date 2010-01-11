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
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.general.Dataset;
import org.jfree.data.xy.DefaultXYDataset;

/**
 * class to create BHXYAreaChart
 * 
 * <p>
 * class to create the <code>JFreeChart</code> BHxyAreaChart
 * 
 * @author Lars
 * @version 0.1, 17.12.2009
 * 
 */
@SuppressWarnings("serial")
public class BHxyAreaChart extends JFreeChart implements IBHComponent,
		IBHAddValue, IPlatformListener {
	ITranslator translator = BHTranslator.getInstance();
	private String key;
	private String inputHint;
	private JFreeChart chart;
	private DefaultXYDataset dataset;

	protected BHxyAreaChart(final String title, final String xAxis, final String yAxis,
			final Dataset dataset, final String key, final XYPlot plot) {
		super(plot);
		this.key = key;
		this.dataset = (DefaultXYDataset) dataset;

		chart = ChartFactory.createXYAreaChart(title, xAxis, yAxis,
				this.dataset, PlotOrientation.VERTICAL, true, true, false);
		if ("Nimbus".equals(UIManager.getLookAndFeel().getName())) {
			chart.setBackgroundPaint(UIManager.getColor("desktop"));
		}
		
		reloadText();
		Services.addPlatformListener(this);
	}

	/**
	 * method to get the <code>BHxyAreaChart</code>
	 * 
	 * @return <code>JFreeChart</code> chart
	 */
	public final JFreeChart getChart() {
		return chart;
	}

	/**
	 * returns unique ID
	 * 
	 */
	@Override
	public final String getKey() {
		return key;
	}

	/**
	 * method to add a series into an empty dataset
	 */
	@Override
	public final void addSeries(final Comparable<String> seriesKey, final double[][] data) {
		this.dataset.addSeries(seriesKey, data);
		fireChartChanged();
	}

	@Override
	public final void addSeries(Comparable<String> key, double[] values, int bins,
			final double minimum, double maximum) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void addValue(Number value, Comparable<String> columnKey) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public final void addValue(Number value, int rowKey, Comparable<String> columnKey) {
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
