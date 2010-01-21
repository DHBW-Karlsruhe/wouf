package org.bh.gui.chart;

import java.util.Iterator;
import java.util.List;

import javax.swing.UIManager;

import org.bh.platform.IPlatformListener;
import org.bh.platform.PlatformEvent;
import org.bh.platform.Services;
import org.bh.platform.PlatformEvent.Type;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.ui.TextAnchor;

	/**
	 * 
	 * BHBarChart to create the BarChart
	 * 
	 * <p>
	 * BarChart is created and modified
	 * 
	 * @author Lars
	 * @version 0.1, 16.12.2009
	 * @author Marcc Hammel
         * @version 0.2 16.01.2010
	 */
	@SuppressWarnings("serial")
	public class BHBarChart extends BHChart implements IBHAddValue, IPlatformListener {

		private DefaultCategoryDataset dataset;

		protected BHBarChart(final String key, Dataset dataset, boolean legend, boolean tooltips) {
			super(key);

			this.dataset = (DefaultCategoryDataset) dataset;

			chart = ChartFactory.createBarChart(translator.translate(key)
                                , translator.translate(key.concat(BHChart.DIMX))
                                , translator.translate(key.concat(BHChart.DIMY))
                                , this.dataset
                                , PlotOrientation.VERTICAL, legend, tooltips, false);

			if ("Nimbus".equals(UIManager.getLookAndFeel().getName())) {
				chart.setBackgroundPaint(UIManager.getColor("Chart.background"));
			}
			
			CategoryItemRenderer renderer = chart.getCategoryPlot().getRenderer();
			renderer.setBaseItemLabelGenerator(new BHChartLabelGenerator());
			
			//Labels im 45 Grad Winkel mit maximal 5 Zeilen anzeigen
			final CategoryAxis domainAxis = chart.getCategoryPlot().getDomainAxis();
			domainAxis.setMaximumCategoryLabelLines(5);
	        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
			
			final ItemLabelPosition p = new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER, TextAnchor.CENTER, -Math.PI / 2.0);
			renderer.setBasePositiveItemLabelPosition(p);
			
			renderer.setBaseItemLabelsVisible(true);
			
			BarRenderer barRenderer = (BarRenderer)chart.getCategoryPlot().getRenderer();
			barRenderer.setDrawBarOutline(false);
			barRenderer.setMaximumBarWidth(0.1);
			barRenderer.setBarPainter(new StandardBarPainter());
			barRenderer.setShadowVisible(false);
			
			barRenderer.setToolTipGenerator(new CategoryToolTipGenerator() {

				@Override
				public String generateToolTip(CategoryDataset dataset, int row,
						int column) {
					return Services.numberToString(dataset.getValue(row, column).doubleValue());
				}
				
				
			});
			
			reloadText();
			Services.addPlatformListener(this);
		}

		
		@Override
		public final void addValue(Number value, Comparable row, Comparable<String> columnKey) {
			if(this.dataset.getColumnKeys().indexOf(columnKey) == -1){
				this.dataset.addValue(value, row, columnKey);
			}else{
				this.dataset.addValue(value, row, columnKey+"'");
			}
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
		public void addSeries(Comparable<String> seriesKey, double[][] data, Integer amountOfValues, Integer average) {
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
