package org.bh.gui.chart;

import java.awt.Component;
import java.util.List;

import javax.swing.UIManager;

import org.bh.data.types.IValue;
import org.bh.gui.swing.IBHComponent;
import org.bh.platform.i18n.BHTranslator;
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
public class BHxyAreaChart extends JFreeChart implements IBHComponent,
		IBHAddValue {
	BHTranslator translator = BHTranslator.getInstance();
	private String key;
	private JFreeChart chart;
	private DefaultXYDataset dataset;
	private String inputHint;

	protected BHxyAreaChart(final String title, final String xAxis, final String yAxis,
			final Dataset dataset, final String key, final XYPlot plot) {
		super(plot);
		this.key = key;
		this.dataset = (DefaultXYDataset) dataset;

		chart = ChartFactory.createXYAreaChart(title, xAxis, yAxis,
				this.dataset, PlotOrientation.VERTICAL, true, true, false);
		plot.setNoDataMessage(translator.translate("noDataAvailable"));
		if ("Nimbus".equals(UIManager.getLookAndFeel().getName())) {
			chart.setBackgroundPaint(UIManager.getColor("desktop"));
		}
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

	public final String getKey() {
		return key;
	}

	/**
	 * method to add a series into an empty dataset
	 */

	public final void addSeries(final Comparable<String> seriesKey, final double[][] data) {
		this.dataset.addSeries(seriesKey, data);
		fireChartChanged();
	}

	/* Specified by interface/super class. */

	public final Component add(final Component comp) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	/* Specified by interface/super class. */

	public final int[] getValidateRules() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	/* Specified by interface/super class. */

	public final boolean isTypeValid() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	/* Specified by interface/super class. */

	public void setValidateRules(int[] validateRules) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	public IValue getValue() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public final void setValue(IValue value) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public String getInputHint() {
		return this.inputHint;
	}

	public final void addSeries(Comparable<String> key, double[] values, int bins,
			final double minimum, double maximum) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	public void addValue(Number value, Comparable<String> columnKey) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	public final void addValue(Number value, int rowKey, Comparable<String> columnKey) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	public void addValues(List<?> list) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public String getBHHint() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This method has not been implemented");
	}

}
