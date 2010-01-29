package org.bh.gui.chart;

import java.util.Iterator;
import java.util.List;

import javax.swing.UIManager;

import org.bh.platform.IPlatformListener;
import org.bh.platform.PlatformEvent;
import org.bh.platform.Services;
import org.bh.platform.PlatformEvent.Type;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.axis.SubCategoryAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.GroupedStackedBarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.KeyToGroupMap;
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
 * @author Marco Hammel
 * @version 0.2 16.01.2010
 */
public class BHBarChart extends BHChart implements IBHAddGroupValue,
		IPlatformListener {

	private DefaultCategoryDataset dataset;
	private GroupedStackedBarRenderer groupRenderer;
	private KeyToGroupMap groupMap;
	private String[] categories;
	private CategoryPlot plot;
	private SubCategoryAxis domainAxis;

	protected BHBarChart(final String key, Dataset dataset, boolean legend,
			boolean tooltips) {
		super(key);

		this.dataset = (DefaultCategoryDataset) dataset;

		chart = ChartFactory.createBarChart(null,
				translator.translate(key.concat(BHChart.DIMX)), translator
						.translate(key.concat(BHChart.DIMY)), this.dataset,
				PlotOrientation.VERTICAL, legend, tooltips, false);

		if ("Nimbus".equals(UIManager.getLookAndFeel().getName())) {
			chart.setBackgroundPaint(UIManager.getColor("Chart.background"));
		}

		CategoryItemRenderer renderer = chart.getCategoryPlot().getRenderer();
		renderer.setBaseItemLabelGenerator(new BHChartLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);

		final ItemLabelPosition p = new ItemLabelPosition(
				ItemLabelAnchor.CENTER, TextAnchor.CENTER, TextAnchor.CENTER,
				-Math.PI / 2.0);
		renderer.setBasePositiveItemLabelPosition(p);
		renderer.setBaseNegativeItemLabelPosition(p);

		BarRenderer barRenderer = (BarRenderer) chart.getCategoryPlot()
				.getRenderer();
		barRenderer.setDrawBarOutline(false);
		barRenderer.setMaximumBarWidth(0.1);
		barRenderer.setBarPainter(new StandardBarPainter());
		barRenderer.setShadowVisible(false);

		reloadText();
		Services.addPlatformListener(this);

		this.plot = chart.getCategoryPlot();
		this.groupRenderer = new GroupedStackedBarRenderer();

		reloadText();
		Services.addPlatformListener(this);
	}

	@Override
	public final void addValue(Number value, Comparable row,
			Comparable<String> columnKey) {

		this.dataset.addValue(value, row, columnKey);
		chart.fireChartChanged();
	}

	@Override
	public void addSeries(Comparable<String> seriesKey, double[][] data,
			Integer amountOfValues, Integer average) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	public void addValue(Number value, Comparable row,
			Comparable<String> columnKey, int catIdx) {
		this.addValue(value, row, columnKey);
		groupMap.mapKeyToGroup(row, categories[catIdx]);
		this.groupRenderer.setSeriesToGroupMap(groupMap);
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
	public final void addSeries(Comparable<String> key, double[] values,
			int bins, final double minimum, final double maximum) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(
				"This method has not been implemented");
	}

	// @Override
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
		if (e.getEventType() == Type.LOCALE_CHANGED) {
			this.reloadText();
		}

	}

	public void setDefaultGroupSettings(String[] categories) {
		this.categories = categories;
		this.groupMap = new KeyToGroupMap(categories[0]);

		this.domainAxis = new SubCategoryAxis("");
		// this.domainAxis.setCategoryMargin(0.05);
		for (String s : categories) {
			this.domainAxis.addSubCategory(translator.translate(s));
		}

		this.plot.setDomainAxis(domainAxis);
		this.groupRenderer.setItemMargin(0.0);
		this.plot.setRenderer(groupRenderer);
	}

	@Override
	public void removeSeries(int number) {
	    // TODO Auto-generated method stub
	    throw new UnsupportedOperationException("This method has not been implemented");
	}
}
