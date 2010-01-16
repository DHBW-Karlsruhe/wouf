package org.bh.gui.chart;

import java.util.List;

import javax.swing.UIManager;

import org.bh.platform.IPlatformListener;
import org.bh.platform.PlatformEvent;
import org.bh.platform.Services;
import org.bh.platform.i18n.ITranslator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class BHXYBarChart extends BHChart implements IBHAddValue,
		IPlatformListener {
	
	private XYSeriesCollection dataset;
	
	public BHXYBarChart(String key){
		super(key);
		ITranslator translator = Services.getTranslator();
		this.dataset = new XYSeriesCollection(new XYSeries(""));

		chart = ChartFactory.createXYBarChart(
				translator.translate(key),
				translator.translate(key.concat(BHChart.DIMX)), 
				false, 
				translator.translate(key.concat(BHChart.DIMY)), 
				dataset, 
				PlotOrientation.VERTICAL, true, true, false);

		if ("Nimbus".equals(UIManager.getLookAndFeel().getName())) {
			chart.setBackgroundPaint(UIManager.getColor("Chart.background"));
		}

		reloadText();
		Services.addPlatformListener(this);
	}

	@Override
	public void addSeries(Comparable<String> key, double[] values, int bins,
			double minimum, double maximum) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void addSeries(Comparable<String> seriesKey, double[][] data) {
		XYSeries series = new XYSeries(seriesKey);
		for(int i = 0; i < data.length; i++){
			series.add(data[i][0], data[i][1]);
		}
		dataset.addSeries(series);
		chart.fireChartChanged();
	}

	@Override
	public void addValue(Number value, Comparable<String> columnKey) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public void addValue(Number value, Comparable rowKey,
			Comparable<String> columnKey) {
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
	public void platformEvent(PlatformEvent e) {
	}

}
