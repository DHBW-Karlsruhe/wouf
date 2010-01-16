package org.bh.gui.chart;

import java.util.Iterator;
import java.util.List;

import javax.swing.UIManager;

import org.bh.platform.IPlatformListener;
import org.bh.platform.PlatformEvent;
import org.bh.platform.Services;
import org.bh.platform.PlatformEvent.Type;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;

	/**
	 * 
	 * BHwaterfallChart to create the waterfallChart
	 * 
	 * <p>
	 * waterfallChart is created and modified
	 * 
	 * @author Lars
	 * @version 0.1, 16.12.2009
	 * 
	 */
	@SuppressWarnings("serial")
	public class BHwaterfallChart extends BHChart implements IBHAddValue, IPlatformListener {
		
		private DefaultCategoryDataset dataset;

		protected BHwaterfallChart(
				final Dataset dataset, final String key) {
			super(key);
			this.dataset = (DefaultCategoryDataset) dataset;

			chart = ChartFactory.createWaterfallChart(translator.translate(key)
                                , translator.translate(key.concat(BHChart.DIMX))
                                , translator.translate(key.concat(BHChart.DIMY))
                                , this.dataset
                                , PlotOrientation.VERTICAL, true, true, false);

			if ("Nimbus".equals(UIManager.getLookAndFeel().getName())) {
				chart.setBackgroundPaint(UIManager.getColor("Chart.background"));
			}
			CategoryItemRenderer renderer = chart.getCategoryPlot().getRenderer();
			renderer.setBaseItemLabelGenerator(new BHChartLabelGenerator());
			renderer.setBaseItemLabelsVisible(true);
			
			reloadText();
			Services.addPlatformListener(this);
		}

		@Override
		public final void addValue(Number value, Comparable row, Comparable<String> columnKey) {

			this.dataset.addValue(value, row, columnKey);
			chart.fireChartChanged();
		}

		@Override
		public void addValues(List<?> list) {
			Iterator<?> it = list.iterator();

			for (int i = 0; i < list.size(); i++) {
				for (int j = 0; j <= i; j++) {
					while (it.hasNext()) {
						this.dataset.addValue((Number) list.get(i), i,
								(String) list.get(j));
						chart.fireChartChanged();
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
