package org.bh.gui.chart;

import java.util.List;

import javax.swing.UIManager;

import org.bh.platform.IPlatformListener;
import org.bh.platform.PlatformEvent;
import org.bh.platform.Services;
import org.bh.platform.PlatformEvent.Type;
import org.jfree.chart.ChartFactory;
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
public class BHxyAreaChart extends BHChart implements IBHAddValue, IPlatformListener {
	
	private DefaultXYDataset dataset;

	protected BHxyAreaChart(
			final Dataset dataset, final String key) {
		super(key);
		this.dataset = (DefaultXYDataset) dataset;

		chart = ChartFactory.createXYAreaChart(translator.translate(key)
                        , translator.translate(key.concat(BHChart.DIMX))
                        , translator.translate(key.concat(BHChart.DIMY))
                        ,this.dataset
                        , PlotOrientation.VERTICAL, true, true, false);

		if ("Nimbus".equals(UIManager.getLookAndFeel().getName())) {
			chart.setBackgroundPaint(UIManager.getColor("Chart.background"));
		}
		
		reloadText();
		Services.addPlatformListener(this);
	}

	/**
	 * method to add a series into an empty dataset
	 */
	@Override
	public final void addSeries(final Comparable<String> seriesKey, final double[][] data) {
		this.dataset.addSeries(seriesKey, data);
		chart.fireChartChanged();
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
	public final void addValue(Number value, Comparable rowKey, Comparable<String> columnKey) {
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

	/**
	 * Handle PlatformEvents
	 */
	@Override
	public void platformEvent(PlatformEvent e) {
		if(e.getEventType() == Type.LOCALE_CHANGED){
			this.reloadText();
		}
	}
	
}
