package org.bh.gui.chart;

import java.util.Iterator;
import java.util.List;

import javax.swing.UIManager;

import org.bh.gui.swing.IBHComponent;
import org.bh.platform.IPlatformListener;
import org.bh.platform.PlatformEvent;
import org.bh.platform.PlatformEvent.Type;
import org.bh.platform.i18n.BHTranslator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;

	/**
	 * 
	 * BHBarChart to create the BarChart
	 * 
	 * <p>
	 * BarChart is created and modified
	 * 
	 * @author Lars
	 * @version 0.1, 16.12.2009
	 * 
	 */
	public class BHBarChart extends JFreeChart implements IBHComponent,
			IBHAddValue, IPlatformListener {
		BHTranslator translator = BHTranslator.getInstance();

		private String key;
		private JFreeChart chart;
		private DefaultCategoryDataset dataset;
		private static Plot plot = new CategoryPlot();

		protected BHBarChart(String title, final String XAxis, final String YAxis,
				final Dataset dataset, final String key) {
			super(plot);
			this.key = key;
			this.dataset = (DefaultCategoryDataset) dataset;

			chart = ChartFactory.createLineChart(title, XAxis, YAxis, this.dataset,
					PlotOrientation.VERTICAL, true, true, false);
			plot.setNoDataMessage(translator.translate("noDataAvailable"));
			if ("Nimbus".equals(UIManager.getLookAndFeel().getName())) {
				chart.setBackgroundPaint(UIManager.getColor("desktop"));
			}
		}

		/**
		 * returns the created Chart of the <code>BHBarChart</code>
		 * 
		 * @return JFreeChart BarChart
		 */

		public JFreeChart getChart() {
			return chart;
		}

		/**
		 * Returns the unique ID of the <code>BHBarChart</code>.
		 * 
		 * @return id unique identifier.
		 */
		@Override
		public String getKey() {
			return key;
		}

		@Override
		public final void addValue(Number value, int row, Comparable<String> columnKey) {

			this.dataset.addValue(value, dataset.getRowKey(row), columnKey);
			fireChartChanged();
		}

		@Override
		public void addValues(List<?> list) {
			Iterator<?> it = list.iterator();

			for (int i = 0; i < list.size(); i++) {
				for (int j = 0; j <= i; j++) {
					while (it.hasNext()) {
						this.dataset.addValue((Number) list.get(i), i,
								(String) list.get(j));
						fireChartChanged();
					}
				}
			}
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
		public void addSeries(Comparable<String> seriesKey, double[][] data) {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException(
					"This method has not been implemented");
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
			if(e.getEventType() == Type.LOCALE_CHANGED){
				this.reloadText();
			}
		}
		
		/**
		 * Reloads Text if necessary.
		 */
		protected void reloadText() {
			this.chart.getPlot().setNoDataMessage(translator.translate("noDataAvailable"));
		}
}
