package org.bh.gui.chart;

import java.util.Iterator;
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
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;

@SuppressWarnings("serial")
public class BHPieChart extends JFreeChart implements IBHComponent,
	IBHAddValue, IPlatformListener {
	ITranslator translator = BHTranslator.getInstance();

	private String key;
	private String inputHint;
	private JFreeChart chart;
	private DefaultPieDataset dataset;
	private static Plot plot = new CategoryPlot();;

	protected BHPieChart(String title, final Dataset dataset, String key) {
		super(plot);
		this.key = key;
		this.dataset = (DefaultPieDataset) dataset;

		chart = ChartFactory.createPieChart(title, this.dataset, true, true,
				false);
		if ("Nimbus".equals(UIManager.getLookAndFeel().getName())) {
			chart.setBackgroundPaint(UIManager.getColor("desktop"));
		}
		
		reloadText();
		Services.addPlatformListener(this);
	}
	/**
	 * returns the created Chart of the <code>BHPieChart</code>
	 * 
	 * @return JFreeChart PieChart
	 */

	public JFreeChart getChart() {
		return chart;
	}
	/**
	 * returns a unique Key
	 * 
	 * @return String key
	 */
	@Override
	public String getKey() {
		return this.key;
	}

	@Override
	public final void addValue(Number value, Comparable<String> columnKey) {
		this.dataset.setValue(columnKey, value);
		fireChartChanged();
	}

	@Override
	public void addValues(List<?> list) {
		Iterator<?> it = list.iterator();

		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j <= i; j++) {
				while (it.hasNext()) {
					this.dataset.setValue((String) list.get(j), (Number) list
							.get(i));
					fireChartChanged();
				}
			}
		}
		// for(int i=0; i<list.size()+1; i++){
		// this.dataset.setValue(columnKey, (Number)list.get(i));
		// fireChartChanged();
		// }
	}

	@Override
	public void addValue(Number value, int rowKey, Comparable<String> columnKey) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
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
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	@Override
	public String getHint() {
		return inputHint;
	}
	
	/**
	 * Handle PlatformEvents
	 */
	@Override
	public void platformEvent(PlatformEvent e) {
		if(e.getEventType()== Type.LOCALE_CHANGED){
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
